package com.yc.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.common.utils.Encrypt;
import com.yc.entity.Governuser;
import com.yc.entity.Permission;
import com.yc.entity.Role;
import com.yc.entity.Systemuser;
import com.yc.mapper.GovernuserMapper;
import com.yc.mapper.SystemuserMapper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserService extends ServiceImpl<GovernuserMapper, Governuser> {

    @Autowired
    private LogService logService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RedisTemplate redisTemplate;

    @Value("${initial-password.check}")
    private boolean checkPwd;

    @Resource
    private GovernuserMapper governuserMapper;
    @Resource
    private SystemuserMapper systemuserMapper;

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param identity
     * @return
     */
    public String login(String username, String password, String identity, HttpServletRequest request) {
        String pwd = Encrypt.md5AndSha(password);
        if (identity.equals("true")) {
            //管理员登录
            LambdaQueryWrapper eq = Wrappers.<Governuser>lambdaQuery().eq(Governuser::getUsername, username);
            Long governuserCount = governuserMapper.selectCount(eq);
            if (governuserCount  <= 0) {
                //账号不存在请联系管理员,添加账号信息!
                throw new InternalAuthenticationServiceException("Account does not exist. Please contact the administrator!");
            }
            Map<String, Object> login = governuserMapper.login(username, pwd);
            if (login==null){
                //账号或密码错误或身份不匹配
                throw new BadCredentialsException(" Invalid username or password ");
            }
            String status = String.valueOf(login.get("status"));
            if (status == null) {
                request.getSession().setAttribute("status", null);
            } else {
                request.getSession().setAttribute("status", status);
            }
            if(Objects.nonNull(login.get("tid"))){
                request.getSession().setAttribute("USERTID", login.get("tid"));
                request.getSession().setAttribute("tname", login.get("tname"));
            }
            request.getSession().setAttribute("governuser", login);
            request.getSession().setAttribute("isAdmin", true);
            redisTemplate.opsForHash().put("USERLOGINTOKEN", String.valueOf(login.get("id")), request.getSession().getId());
            redisTemplate.expire("USERLOGINTOKEN", 80, TimeUnit.MINUTES);
            logService.log("用户 " + login.get("username") + "登录", (String) login.get("nickname"), 1);
            return "governuser_" + username;
        } else {
            //普通用户登录
            LambdaQueryWrapper wrapper = Wrappers.<Systemuser>lambdaQuery().eq(Systemuser::getUsername, username);
            Long systemuserCount = systemuserMapper.selectCount(wrapper);
            if (systemuserCount <= 0) {
                //账号不存在请联系管理员,添加账号信息!
                throw new InternalAuthenticationServiceException("Account does not exist. Please contact the administrator!");
            }
            Map<String, Object> login = systemuserMapper.login(username, pwd);
            if (login == null) {
                //账号或密码错误或身份不匹配
                throw new UsernameNotFoundException(" Invalid username or password ");
            }
            Integer tid =(Integer) login.get("tid");
            if (tid != null) {
                String status = String.valueOf(login.get("status"));
                if (status == null) {
                    request.getSession().setAttribute("status", null);
                } else {
                    request.getSession().setAttribute("status", login.get("status"));
                }
                request.getSession().setAttribute("tname", login.get("tname"));
                request.getSession().setAttribute("USERTID", login.get("tid"));
            }
            request.getSession().setAttribute("systemuser", login);
            request.getSession().setAttribute("isAdmin", false);
            redisTemplate.opsForHash().put("USERLOGINTOKEN",String.valueOf(login.get("id")) , request.getSession().getId());
            redisTemplate.expire("USERLOGINTOKEN", 80, TimeUnit.MINUTES);
            logService.log("用户 " +login.get("username") + "登录", (String) login.get("nickname"), 1);
            return "systemuser_" + username;
        }
    }

    /**
     * 设置权限
     *
     * @param userId
     * @return
     */
    public List<Permission> getPermissions(Integer userId) {
        Governuser byId = getById(userId);
        List<Permission> permissions = new ArrayList<>();
        List<Long> role = byId.getRole();
        if (role != null) {
            for (Object roleId : role) {
                Role realRole = roleService.getById((int) roleId);
                if (CollUtil.isNotEmpty(realRole.getPermission())) {
                    for (Object permissionId : realRole.getPermission()) {
                        Permission permission = permissionService.getById((int) permissionId);
                        if (permission != null && permissions.stream().noneMatch(p -> p.getPath().equals(permission.getPath()))) {
                            permissions.add(permission);
                        }
                    }
                }
            }
            byId.setPermission(permissions);
        }
        return permissions;
    }

}