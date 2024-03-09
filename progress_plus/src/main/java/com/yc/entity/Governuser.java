package com.yc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.common.handler.ListHandler;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

 /**
 * 管理员表;
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
 @Data
@ApiModel(value = "管理员表",description = "")
@TableName(value = "governuser",autoResultMap = true)
public class Governuser extends BaseEntity implements UserDetails {
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id ;
    /** 用户名 */
    @ApiModelProperty(name = "用户名",notes = "")
    private String username ;
    /** 密码 */
    @ApiModelProperty(name = "密码",notes = "")
    private String password ;
    /** 昵称 */
    @ApiModelProperty(name = "昵称",notes = "")
    private String nickname ;
    /** 手机号 */
    @ApiModelProperty(name = "手机号",notes = "")
    private String phone ;
    /** 所属部门 */
    @ApiModelProperty(name = "所属部门",notes = "")
    private Integer tid ;
    /**
     * 权限
     */
    @TableField(typeHandler = ListHandler.class)
    private List<Long> role;

    @TableField(exist = false)
    private List<Permission> permission;

    @TableField(exist = false)
    private Integer identity;

    /**
     * 权限代码
     */
    @TableField(exist = false)
    private String rolecode;

    /**
     * 角色列表
     */
    @TableField(exist = false)
    private String roleIds;

    /**
     * 角色列表
     */
    @TableField(exist = false)
    private Set<GrantedAuthority> authorities;

    /**
     * 角色列表
     */
    @TableField(exist = false, update = "role")
    private String roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        if(authorities!=null){
            this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        }
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());
        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }
        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
        @Override
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            }
            if (g1.getAuthority() == null) {
                return 1;
            }
            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }
 }