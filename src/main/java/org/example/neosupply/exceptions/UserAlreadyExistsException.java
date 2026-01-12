package org.example.neosupply.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String message)
    {
        super(message);
    }
}
