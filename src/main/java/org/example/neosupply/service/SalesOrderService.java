package org.example.neosupply.service;


import org.example.neosupply.dto.request.SalesOrderDTO;
import org.example.neosupply.dto.response.SalesOrderDtoResponse;
import org.example.neosupply.entity.SalesOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SalesOrderService {

    public SalesOrderDtoResponse createSalesOrder(SalesOrderDTO salesOrderDTO);
    public SalesOrderDtoResponse approveSalesOrder(Long salesOrderId, Long carrierId);
    public Page<SalesOrder> getSalesOrderAll(Pageable pageable);

}
