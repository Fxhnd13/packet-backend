package com.example.basedomain.auth.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleInterceptor {

    @Around("@annotation(com.example.basedomain.auth.annotation.RoleValidation) && @annotation(annotation))")
    public Object roleValidation(ProceedingJoinPoint joinPoint, RoleValidation annotation) throws Throwable {

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
                throw new AccessDeniedException("El usuario no tiene permisos suficientes para acceder a este recurso");
            }
        }
        return joinPoint.proceed();
    }

}
