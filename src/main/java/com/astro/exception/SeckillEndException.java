package com.astro.exception;

public class SeckillEndException extends SeckillException{

    public SeckillEndException(String message) {
        super(message);
    }

    public SeckillEndException(String message, Throwable cause) {
        super(message, cause);
    }
}
