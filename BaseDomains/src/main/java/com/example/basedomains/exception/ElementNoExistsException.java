package com.example.basedomains.exception;

public class ElementNoExistsException extends PackageSystemException {

    public ElementNoExistsException() {
        super("No se puede ejecutar la solicitud, el recurso no existe.");
    }
}
