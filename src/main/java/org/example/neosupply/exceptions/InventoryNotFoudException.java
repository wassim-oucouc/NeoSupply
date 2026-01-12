package org.example.neosupply.exceptions;

public class InventoryNotFoudException extends RuntimeException{

    public InventoryNotFoudException(String message)
    {
        super(message);
    }
}
