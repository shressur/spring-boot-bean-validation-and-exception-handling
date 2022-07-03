package com.example.springbootexceptionhandling.exception;

public class CustomerNotFoundException extends RuntimeException{
    //generate constructor
    public CustomerNotFoundException(String message) {

        super(message);
    }
}
