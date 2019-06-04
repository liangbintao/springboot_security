package com.mochu.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
// 重点
@WebFilter(filterName = "baseFilter", urlPatterns = "/*")
public class BaseFilter implements Filter {

//	@Autowired
//	private AppService appService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        /**
         * HttpServletRequest request = (HttpServletRequest) req;
         *
         *
         * String url = request.getRequestURI(); if (url.startsWith("/app") ||
         * !Systems.isOnline()) { filterChain.doFilter(req, res); return; }
         *
         *
         * LogUtil.log(url); String accesstoken = request.getHeader("Accesstoken");
         * boolean valid = appService.checkToken(accesstoken); if (!valid) {
         * response.setStatus(403); return; }
         *
         */

        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}