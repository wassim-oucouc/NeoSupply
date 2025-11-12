package org.example.neosupply.service.impl;


import org.example.neosupply.dto.response.SalesOrderLineDtoResponse;
import org.example.neosupply.entity.SalesOrderLine;
import org.example.neosupply.mapper.SalesOrderLineMapper;
import org.example.neosupply.repository.SalesOrderLineRepository;
import org.example.neosupply.service.SalesOrderLineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesOrderLineServiceImpl implements SalesOrderLineService {

    public SalesOrderLineRepository salesOrderLineRepository;
    public SalesOrderLineMapper salesOrderLineMapper;

    public SalesOrderLineServiceImpl(SalesOrderLineRepository salesOrderLineRepository,SalesOrderLineMapper salesOrderLineMapper)
    {
        this.salesOrderLineRepository = salesOrderLineRepository;
        this.salesOrderLineMapper = salesOrderLineMapper;
    }


    public List<SalesOrderLineDtoResponse> getSalesOrderLineByProductId(Long productId)
    {
        return this.salesOrderLineRepository.getSalesOrderLineByProduct_Id(productId).stream().map(salesOrderLineMapper::toDtoResponse).toList();
    }


}
