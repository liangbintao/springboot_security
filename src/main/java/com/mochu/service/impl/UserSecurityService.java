package com.mochu.service.impl;

import com.mochu.po.AuthorityPo;
import com.mochu.po.UserPo;
import com.mochu.po.UserSecurityPo;
import com.mochu.service.IAuthorityService;
import com.mochu.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IAuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserPo usersPo = userService.getUserByUsername(username);

        if (usersPo == null) {
            System.out.println("loadUserByUsername " + username + " 用户不存在");
            throw new UsernameNotFoundException("用户名不存在");
        }

        UserSecurityPo po = new UserSecurityPo();
        po.setUserPo(usersPo);
        List<AuthorityPo> authorities = authorityService.getAuthoritiesByUsername(usersPo.getUsername());
        if (authorities == null) {
            System.out.println("loadUserByUsername " + username + " 权限不存在");
        }
        po.setAuthorityList(authorities);

        return null;
    }
}
