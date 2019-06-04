package com.mochu.filter;

import com.mochu.service.IUserService;
import com.mochu.service.impl.UserSecurityService;
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
public class SessionHeaderFilter extends ConcurrentSessionFilter {

    public SessionHeaderFilter(SessionRegistry sessionRegistry) {
        super(sessionRegistry);
    }

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private UserSecurityService userDetailsService;

    private IUserService userService;

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        try {

            String authorization = request.getHeader("Authorization");
            if (StrUtil.isNullStr(authorization)) {
                chain.doFilter(request, response);
                return;
            }

            String session = authorization.replace("session_", "");
            String sessionid = StrUtil.base64decode(session);
            if (StrUtil.isNullStr(sessionid)) {
                response.setStatus(401);
                return;
            }

            String username = userService.getUsernameBySession(sessionid);
            if (StrUtil.isNullStr(username)) {
                response.setStatus(401);
                return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!userDetails.isEnabled()) {
                response.setStatus(401);
                return;
            }

            login(request, userDetails);

        } catch (Exception e) {
            e.printStackTrace();
        }

        chain.doFilter(request, response);
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
