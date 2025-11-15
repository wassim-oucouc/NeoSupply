package org.example.neosupply.exceptions;

public class SupplierNotFoundException extends RuntimeException{

    public SupplierNotFoundException(String message)
    {
        super(message);
    }
}
