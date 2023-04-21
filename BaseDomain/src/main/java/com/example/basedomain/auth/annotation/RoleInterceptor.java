package com.example.basedomain.auth.annotation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

public class RoleInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RoleValidation annotation = handlerMethod.getMethodAnnotation(RoleValidation.class);

        if (annotation != null) {
            String[] roles = annotation.value();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean hasRole = false;

            for(GrantedAuthority authority : authentication.getAuthorities()){
                String role = authority.getAuthority();

                for(String allowedRole : roles){
                    if(allowedRole.equals(role)){
                        hasRole = true;
                        break;
                    }
                }

                if(hasRole) break;
            }

            if(!hasRole){
                response.sendError(HttpStatus.FORBIDDEN.value(), "No tiene permisos suficientes para acceder a este recurso.");
                return false;
            }
        }
        return true;
    }

}
