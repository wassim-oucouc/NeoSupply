package org.example.neosupply.exceptions;



public class PurchaseOrderNotFoundException extends RuntimeException{

    public PurchaseOrderNotFoundException(String message)
    {
        super(message);
    }
}
