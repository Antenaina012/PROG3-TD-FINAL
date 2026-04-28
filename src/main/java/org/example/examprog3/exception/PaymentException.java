package org.example.examprog3.exception;

public class PaymentException extends RuntimeException{
    public PaymentException(String message){
        super(message);
    }
}