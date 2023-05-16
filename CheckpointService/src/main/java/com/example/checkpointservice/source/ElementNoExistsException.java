package com.example.checkpointservice.source;

public class ElementNoExistsException extends PackageSystemException {

    public ElementNoExistsException() {
        super("No se puede ejecutar la solicitud, el recurso no existe.");
    }
}
