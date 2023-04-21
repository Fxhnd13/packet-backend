package com.example.basedomain.auth.jwt;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthFilter implements Filter {

    private final JwtAuthenticationFilter filter;

    public AuthFilter(JwtAuthenticationFilter filter){
        this.filter = filter;
    }

    @Override
    public void init(FilterConfig config) throws ServletException{
        this.filter.init(config);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        this.filter.doFilterInternal((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain);
    }

    @Override
    public void destroy(){
        this.filter.destroy();
    }
}
