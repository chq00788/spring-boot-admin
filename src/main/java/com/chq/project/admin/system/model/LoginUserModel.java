package com.chq.project.admin.system.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * 登录用户信息
 *
 * @author CHQ
 * @Description
 * @date 2019/1/28
 */
public class LoginUserModel implements Serializable, UserDetails {

    /**
     * 主键
     */
    private Integer id;

    private String username;

    private String password;

    private String realName;

    private Integer userType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    /**
     * 判断帐号是否已经过期
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * 判断帐号是否已被锁定
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     * 判断用户凭证是否已经过期
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * 判断用户帐号是否已启用
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return false;
    }
}
