package com.example.basedomains.exception;
public class NotANumberException extends PackageSystemException{

    public NotANumberException() {
        super("No se proporciono un valor valido para el campo numerico.");
    }
}
