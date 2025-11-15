package org.example.neosupply.service.impl;

import org.example.neosupply.dto.request.SupplierDTO;
import org.example.neosupply.dto.response.SupplierDtoResponse;
import org.example.neosupply.entity.Supplier;
import org.example.neosupply.exceptions.InventoryNotFoudException;
import org.example.neosupply.exceptions.SupplierNotFoundException;
import org.example.neosupply.mapper.SupplierMapper;
import org.example.neosupply.repository.SupplierRepository;
import org.example.neosupply.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository,SupplierMapper supplierMapper)
    {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    public SupplierDtoResponse createSupplier(SupplierDTO supplierDTO)
    {
        Supplier supplier =  this.supplierMapper.toEntity(supplierDTO);
         this.supplierRepository.save(supplier);
       return  this.supplierMapper.toDtoResponse(supplier);
    }
    public SupplierDtoResponse updateSupplier(Long id,SupplierDTO supplierDTO)
    {
        Supplier supplier =  this.supplierMapper.toEntity(supplierDTO);
        Supplier supplierFound = this.supplierRepository.findSupplierById(id).orElseThrow(() -> new SupplierNotFoundException("supplier not found with id : " + id));
        return this.supplierMapper.toDtoResponse(supplierFound);
    }
    public void deleteSupplierById(Long id)
    {
        Supplier supplier =  this.supplierRepository.findSupplierById(id).orElseThrow(() -> new  SupplierNotFoundException("supplier not found with id : " + id));
        this.supplierRepository.delete(supplier);
    }

    public List<SupplierDtoResponse> getAllSuppliers()
    {
        return this.supplierRepository.findAll().stream().map(supplierMapper::toDtoResponse).toList();
    }

    public SupplierDtoResponse findSupplierById(Long id)
    {
        Supplier supplier =  this.supplierRepository.findSupplierById(id).orElseThrow(() -> new  SupplierNotFoundException("supplier not found with id : " + id));
        return this.supplierMapper.toDtoResponse(supplier);
    }
}
