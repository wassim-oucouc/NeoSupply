package org.example.neosupply.controller;


import org.example.neosupply.dto.request.SalesOrderDTO;
import org.example.neosupply.dto.response.SalesOrderDtoResponse;
import org.example.neosupply.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalesOrderController {

    private SalesOrderService salesOrderService;

    @Autowired
    public SalesOrderController(SalesOrderService salesOrderService)
    {
        this.salesOrderService = salesOrderService;
    }

    @PostMapping("/salesorder/create")
    public ResponseEntity<SalesOrderDtoResponse> createSalesOrder(@RequestBody  SalesOrderDTO salesOrderDTO)
    {
        return ResponseEntity.ok().body(this.salesOrderService.createSalesOrder(salesOrderDTO));
    }

    @PutMapping("/salesorder/")


}
