package org.example.neosupply.exceptions;

import org.springframework.data.relational.core.sql.In;


public class InventoryMovementNotFoundException extends RuntimeException{


    public InventoryMovementNotFoundException(String message)
    {
        super(message);
    }
}
