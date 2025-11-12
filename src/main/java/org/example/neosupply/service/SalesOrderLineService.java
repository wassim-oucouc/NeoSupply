package org.example.neosupply.service;

import org.example.neosupply.dto.response.SalesOrderLineDtoResponse;
import org.example.neosupply.entity.SalesOrderLine;

import java.util.List;

public interface SalesOrderLineService {

    public List<SalesOrderLineDtoResponse> getSalesOrderLineByProductId(Long productId);
}
