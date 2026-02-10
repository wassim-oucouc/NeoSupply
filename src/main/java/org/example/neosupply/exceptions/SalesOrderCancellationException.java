package org.example.neosupply.exceptions;


public class SalesOrderCancellationException extends RuntimeException{

    public SalesOrderCancellationException(String message)
    {
        super(message);
    }
}
