package org.example.examprog3.exeption;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientSponsorCount extends RuntimeException {
    public InsufficientSponsorCount(String message) { super(message); }
}
