package com.example.basedomains.exception;

public class NoEmptyCheckpointException extends  PackageSystemException{

    public NoEmptyCheckpointException() {
        super("No se puede ejecutar la solicitud, el punto de control tiene paquetes por procesar.");
    }
}
