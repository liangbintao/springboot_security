package com.mochu.conf;

import com.mochu.constant.AuthorityConstant;
import com.mochu.filter.SessionAuthFilter;
import com.mochu.filter.SessionCookieFilter;
import com.mochu.filter.SessionHeaderFilter;
import com.mochu.service.IUserService;
import com.mochu.service.impl.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityService userDetailsService;

    @Autowired
    private IUserService userService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/*", "/html/**", "/login/**", "/api/**", "/resources/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        SessionCookieFilter sessionCookieFilter = new SessionCookieFilter(new SessionRegistryImpl());
        sessionCookieFilter.setUserDetailsService(userDetailsService);
        sessionCookieFilter.setUserService(userService);

        http.addFilterAt(sessionCookieFilter, SessionCookieFilter.class);

        SessionHeaderFilter headerFilter = new SessionHeaderFilter(new SessionRegistryImpl());
        headerFilter.setUserDetailsService(userDetailsService);
        headerFilter.setUserService(userService);

        http.addFilterAt(headerFilter, SessionHeaderFilter.class);

        SessionAuthFilter sessionAuthFilter = new SessionAuthFilter(new SessionRegistryImpl());
        http.addFilterAt(sessionAuthFilter, SessionAuthFilter.class);

        http.csrf().disable();

        http.authorizeRequests().and().headers().frameOptions().disable().and().logout().permitAll();

        http.authorizeRequests().antMatchers("/user/**").hasAuthority(AuthorityConstant.USER);
    }
}
