package org.example.examprog3.exeption;

public class PaymentException extends RuntimeException{
    public PaymentException(String message){
        super(message);
    }
}
