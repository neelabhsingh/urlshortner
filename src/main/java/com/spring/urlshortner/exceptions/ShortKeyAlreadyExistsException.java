package com.spring.urlshortner.exceptions;

public class ShortKeyAlreadyExistsException extends RuntimeException {
    public ShortKeyAlreadyExistsException(String message) {
        super(message);
    }
}