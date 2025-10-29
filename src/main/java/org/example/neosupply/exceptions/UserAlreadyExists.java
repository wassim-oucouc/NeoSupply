package org.example.neosupply.exceptions;

public class UserAlreadyExists extends RuntimeException{

    public UserAlreadyExists(String message)
    {
        super(message);
    }
}
