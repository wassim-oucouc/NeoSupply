package org.example.neosupply.exceptions;

public class ProductNotFoundException extends RuntimeException{


    public ProductNotFoundException(String message)
    {
        super(message);
    }
}
