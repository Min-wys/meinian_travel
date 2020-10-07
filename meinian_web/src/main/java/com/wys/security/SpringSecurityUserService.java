package com.wys.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wys.pojo.Permission;
import com.wys.pojo.Role;
import com.wys.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.security
 * @Author: WangYongShuai
 * @Description: 安全校验
 * @Date: 2020/10/7 21:29
 * @Version: 1.0
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户
        com.wys.pojo.User user = userService.findUserByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }

        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        UserDetails userDetails = new User(username,user.getPassword(),list);


        return userDetails;
    }
}
