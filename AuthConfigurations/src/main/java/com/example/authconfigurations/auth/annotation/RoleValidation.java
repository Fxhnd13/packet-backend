package com.example.authconfigurations.auth.annotation;

import java.lang.annotation.*;

/**
 * @implNote Anotación para validar los roles de los usuarios
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleValidation {

    /**
     * @implNote Roles que se deben validar
     */
    String[] value();

}
