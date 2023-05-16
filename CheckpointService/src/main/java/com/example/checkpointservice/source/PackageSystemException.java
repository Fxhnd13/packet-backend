package com.example.checkpointservice.source;

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
