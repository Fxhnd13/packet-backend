package com.example.checkpointservice.source;

public class RequiredFieldException extends PackageSystemException {

    public RequiredFieldException() {
        super("Existen campos obligatorios no porporcionados");
    }

}
