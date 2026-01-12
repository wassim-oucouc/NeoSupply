package org.example.neosupply.exceptions;

public class ClientNotFoundException extends RuntimeException{
    
    public ClientNotFoundException(String message)
    {
        super(message);
    }
}
