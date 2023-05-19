package com.example.basedomains.exception;

public class NoEmptyRouteException  extends  PackageSystemException{

    public NoEmptyRouteException() {
        super("No se puede ejecutar la solicitud, la ruta tiene paquetes por procesar.");
    }
}
