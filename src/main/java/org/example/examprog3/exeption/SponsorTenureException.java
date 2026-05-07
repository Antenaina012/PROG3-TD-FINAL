package org.example.examprog3.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SponsorTenureException extends RuntimeException {
    public SponsorTenureException(String message) {
        super(message);
    }
}
