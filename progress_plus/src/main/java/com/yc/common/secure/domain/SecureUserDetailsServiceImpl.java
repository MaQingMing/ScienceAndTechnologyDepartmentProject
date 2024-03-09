package com.yc.common.secure.domain;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yc.entity.Governuser;
import com.yc.entity.Systemuser;
import com.yc.mapper.GovernuserMapper;
import com.yc.mapper.SystemuserMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: model_project
 * @description:  Security 加载用户信息服务
 * @author: 作者 huchaojie
 * @create: 2023-06-11 14:46
 */
@Service
public class SecureUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private GovernuserMapper governuserMapper;

    @Resource
    private SystemuserMapper systemuserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.startsWith("governuser_")){
            String[] strings = username.split("_");
            Governuser governuser = governuserMapper.selectUserInfoAndRole(strings[1]);
            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
            SimpleGrantedAuthority authority;
            if (StringUtils.isNotBlank(governuser.getRolecode()) && StringUtils.isNotEmpty(governuser.getRolecode())) {
                String[] split = governuser.getRolecode().split(",");
                if (split.length>0) {
                    for (String roleCode : split) {
                        if (StringUtils.isNotBlank(roleCode) && StringUtils.isNotEmpty(roleCode)) {
                            authority = new SimpleGrantedAuthority(roleCode);
                            authorityList.add(authority);
                        }
                    }
                }
            }
            governuser.setAuthorities(authorityList);
            return governuser;
        }else if (username.startsWith("systemuser_")){
            String[] strings = username.split("_");
            Systemuser systemuser = systemuserMapper.selectUserInfoAndRole(strings[1]);
            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
            SimpleGrantedAuthority authority;
            if (StringUtils.isNotBlank(systemuser.getRolecode()) && StringUtils.isNotEmpty(systemuser.getRolecode())) {
                String[] split = systemuser.getRolecode().split(",");
                if (split.length>0) {
                    for (String roleCode : split) {
                        if (StringUtils.isNotBlank(roleCode) && StringUtils.isNotEmpty(roleCode)) {
                            authority = new SimpleGrantedAuthority(roleCode);
                            authorityList.add(authority);
                        }
                    }
                }
            }
            systemuser.setAuthorities(authorityList);
            return systemuser;
        }else {
            return new Systemuser();
        }
    }

}
