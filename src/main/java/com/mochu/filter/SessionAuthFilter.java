package com.mochu.filter;

import com.mochu.service.IUserService;
import com.mochu.service.impl.UserSecurityService;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
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
public class SessionAuthFilter extends ConcurrentSessionFilter {

    public SessionAuthFilter(SessionRegistry sessionRegistry) {
        super(sessionRegistry);
    }

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private UserSecurityService userDetailsService;

    private IUserService userService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            response.setStatus(401);
            response.sendRedirect("/401");

            return;
        }

        chain.doFilter(req, res);
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
