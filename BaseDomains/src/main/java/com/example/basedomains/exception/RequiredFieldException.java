package com.example.basedomains.exception;

public class RequiredFieldException extends PackageSystemException {

    public RequiredFieldException() {
        super("Existen campos obligatorios no proporcionados");
    }

}
