package com.chq.project.admin.system.service;

import com.chq.project.admin.system.model.PermissionModel;
import com.chq.project.admin.system.model.RoleModel;
import com.chq.project.admin.system.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CHQ
 * @Description 实现了UserDetailsService接口，这个接口很简单就一个方法 UserDetails loadUserByUsername(String var1)
 * @date 2019/1/28
 */
@Service
public class CustomUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 返回一个用户基本信息和该用户拥有的权限功能，作为后续权限认证的依据
     *
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserModel user = userService.getByUsername(s);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (null != user) {
            for (RoleModel role : user.getRoleList()) {
                for (PermissionModel perm : role.getPermissionList()) {
                    //此处将权限信息添加到 GrantedAuthority 对象中，在后面进行权限验证时会使用GrantedAuthority 对象。
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(perm.getPermCode());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("登录异常:" + s + "不存在");
        }
    }


}
