package org.example.examprog3.exeption;

public class InsufficientSponsorCount extends RuntimeException{
    public InsufficientSponsorCount(String message) {
        super(message);
    }
}
