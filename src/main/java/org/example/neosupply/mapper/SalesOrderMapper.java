package org.example.neosupply.mapper;

import org.example.neosupply.dto.request.SalesOrderDTO;
import org.example.neosupply.dto.request.SalesOrderLineDTO;
import org.example.neosupply.dto.response.SalesOrderDtoResponse;
import org.example.neosupply.dto.response.UserDtoResponse;
import org.example.neosupply.dto.response.WarehouseDtoResponse;
import org.example.neosupply.entity.*;
import org.example.neosupply.exceptions.ClientNotFoundException;
import org.example.neosupply.exceptions.WarehouseNotFoundException;
import org.example.neosupply.repository.UserRepository;
import org.example.neosupply.repository.WarehouseRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses = {SalesOrderLineMapper.class, ProductMapper.class})
public abstract class SalesOrderMapper {

    @Autowired
    protected UserRepository clientRepository;

    @Autowired
    protected WarehouseRepository warehouseRepository;

    @Autowired
    protected UserMapper clientMapper;

    @Autowired
    protected WarehouseMapper warehouseMapper;

    @Autowired
    protected SalesOrderLineMapper salesOrderLineMapper;



    @Mapping(source = "clientId", target = "client", qualifiedByName = "mapClient")
    @Mapping(source = "warehouseId", target = "warehouse", qualifiedByName = "mapWarehouse")
    @Mapping(source = "salesOrderLineDTOS", target = "salesOrderLines")
    public abstract SalesOrder toEntity(SalesOrderDTO salesOrderDTO);

    @Mapping(source = "client", target = "clientDtoResponse", qualifiedByName = "mapClientDtoResponse")
    @Mapping(source = "warehouse", target = "warehouseDtoResponse", qualifiedByName = "mapWarehouseDtoResponse")
    @Mapping(source = "salesOrderLines" , target = "salesOrderLineDtoResponses")
    public abstract SalesOrderDtoResponse toDtoResponse(SalesOrder salesOrder);


    @Named("mapClient")
    protected Users mapClient(Long id) {
        if (id == null) return null;
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with ID: " + id));
    }

    @Named("mapWarehouse")
    protected Warehouse mapWarehouse(Long id) {
        if (id == null) return null;
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found with ID: " + id));
    }

    @Named("mapClientDtoResponse")
    protected UserDtoResponse mapClientDtoResponse(Users client) {
        if (client == null) return null;
        return clientMapper.toDtoResponse(client);
    }

    @Named("mapWarehouseDtoResponse")
    protected WarehouseDtoResponse mapWarehouseDtoResponse(Warehouse warehouse) {
        if (warehouse == null) return null;
        return warehouseMapper.toDtoResponse(warehouse);
    }

    @Named("toEntityList")
    protected List<SalesOrderLine> map(List<SalesOrderLineDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(this.salesOrderLineMapper::toEntity)
                .collect(Collectors.toList());
    }
}
