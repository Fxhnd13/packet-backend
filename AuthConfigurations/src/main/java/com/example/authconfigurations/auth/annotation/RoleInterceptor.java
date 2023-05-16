package com.example.authconfigurations.auth.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @implNote Clase que interviene en la ejecución de los métodos anotados con @RoleValidation
 */
@Aspect
@Component
public class RoleInterceptor {

    /**
     * @implNote Método que se ejecuta antes de la ejecución del método anotado con @RoleValidation
     * @param joinPoint Punto de unión de la ejecución del método
     * @param annotation Anotación que contiene los roles permitidos para acceder al método
     * @throws Throwable Excepción que se lanza si el usuario no tiene permisos suficientes para acceder al método
     */
    @Around("@annotation(com.example.authconfigurations.auth.annotation.RoleValidation) && @annotation(annotation))")
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
