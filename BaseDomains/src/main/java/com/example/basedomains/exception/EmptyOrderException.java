package com.example.basedomains.exception;

public class EmptyOrderException extends PackageSystemException{

    public EmptyOrderException() {
        super("No se puede registrar una orden vacia.");
    }
}
