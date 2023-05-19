package com.example.basedomains.exception;

public class EmptyRouteException extends PackageSystemException{

    public EmptyRouteException() {
        super("No se puede crear una ruta sin puntos de control");
    }
}
