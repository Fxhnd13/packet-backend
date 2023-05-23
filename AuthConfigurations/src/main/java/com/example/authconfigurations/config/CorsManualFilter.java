package com.example.authconfigurations.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CorsManualFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(response.getHeader("Access-Control-Allow-Origin")==null) response.setHeader("Access-Control-Allow-Origin", getOriginFromHeader(request));
        if(response.getHeader("Access-Control-Allow-Methods")==null) response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        if(response.getHeader("Access-Control-Max-Age")==null) response.setHeader("Access-Control-Max-Age", "3600");
        if(response.getHeader("Access-Control-Allow-Headers")==null) response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
        if(response.getHeader("Access-Control-Expose-Headers")==null) response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
        if(response.getHeader("Access-Control-Allow-Credentials")==null) response.addHeader("Access-Control-Allow-Credentials","true");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private String getOriginFromHeader(HttpServletRequest request){
        String clientOrigin = request.getHeader("origin");
        return (clientOrigin == null)? "*":clientOrigin;
    }
}
