package org.example.examprog3.exception;

public class InsufficientSponsorCount extends RuntimeException{
    public InsufficientSponsorCount(String message) {
        super(message);
    }
}
