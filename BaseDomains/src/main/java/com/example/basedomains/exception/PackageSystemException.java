package com.example.basedomains.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public  class PackageSystemException extends Exception{

    private Error error;
    public PackageSystemException(String message){
        this.error = new Error(message);
    }

}
