package org.example.neosupply.service;


import org.example.neosupply.dto.request.SalesOrderDTO;
import org.example.neosupply.dto.response.SalesOrderDtoResponse;
import org.springframework.stereotype.Service;

@Service
public interface SalesOrderService {

    public SalesOrderDtoResponse createSalesOrder(SalesOrderDTO salesOrderDTO);

}
