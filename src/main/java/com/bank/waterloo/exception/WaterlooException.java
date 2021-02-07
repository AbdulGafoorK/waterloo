package com.bank.waterloo.exception;

public class WaterlooException extends RuntimeException {
    private String message;

    public WaterlooException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
