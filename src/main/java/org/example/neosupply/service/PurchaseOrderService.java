package org.example.neosupply.service;


import org.example.neosupply.dto.request.PurchaseOrderDTO;
import org.example.neosupply.dto.response.PurchaseOrderDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PurchaseOrderService {

    public PurchaseOrderDtoResponse receivePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO);
    public List<PurchaseOrderDtoResponse> getAllPurchaseOrders();
    public PurchaseOrderDtoResponse findPurchaseOrderById(Long id);
    public PurchaseOrderDtoResponse approvePurchaseOrder(Long id,Long warehouseId);
    public PurchaseOrderDtoResponse cancelPurchaseOrder(Long id);
}
