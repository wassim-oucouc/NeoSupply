package org.example.neosupply.exceptions;

public class WarehouseNotFoundException extends RuntimeException{

    public WarehouseNotFoundException(String message)
    {
        super(message);
    }
}
