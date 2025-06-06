package com.example.backend.global;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{

    private final ResponseStatus status;
    public GlobalException(ResponseStatus status) {
        this.status = status;
    }
}
