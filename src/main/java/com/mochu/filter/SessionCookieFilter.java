package com.mochu.filter;

import com.mochu.service.IUserService;
import com.mochu.service.impl.UserSecurityService;
import com.mochu.util.CookieUtil;
import com.mochu.util.StrUtil;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Data
public class SessionCookieFilter extends ConcurrentSessionFilter {

    public SessionCookieFilter(SessionRegistry sessionRegistry) {
        super(sessionRegistry);
    }

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private UserSecurityService userDetailsService;

    private IUserService userService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        try {

            String session = CookieUtil.getSession(request);

            if (!StrUtil.isNullStr(session)) {
                UserDetails userDetails = loadUserDetails(session);
                if (userDetails == null) {
                    CookieUtil.deleteSession(response);
                    response.setStatus(401);
                    response.sendRedirect("/login.html");
                    return;
                }
                login(request, userDetails);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        chain.doFilter(request, response);

    }

    private UserDetails loadUserDetails(String session) {
        String username = userService.getUsernameBySession(session);
        if (username == null) {
            return null;

        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!userDetails.isEnabled()) {
            return null;
        }

        return userDetails;
    }

    /**
     * 用户名登录
     *
     * @param request
     */
    private void login(HttpServletRequest request, UserDetails userDetails) {

        Authentication rememberMeAuth = createSuccessfulAuthentication(request, userDetails);

        AuthenticationManager authenticationManager = new AuthenticationManager() {

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {

                return authentication;
            }
        };
        rememberMeAuth = authenticationManager.authenticate(rememberMeAuth);
        SecurityContextHolder.getContext().setAuthentication(rememberMeAuth);

    }

    @Override
    public void destroy() {
    }

    protected Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails user) {
        RememberMeAuthenticationToken auth = new RememberMeAuthenticationToken("SpringSecured", user,
                user.getAuthorities());
        auth.setDetails(authenticationDetailsSource.buildDetails(request));

        return auth;
    }
}
