package org.example.neosupply.service;


import org.example.neosupply.dto.request.SupplierDTO;
import org.example.neosupply.dto.response.SupplierDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplierService {

    public SupplierDtoResponse createSupplier(SupplierDTO supplierDTO);
    public SupplierDtoResponse updateSupplier(Long id,SupplierDTO supplierDTO);
    public void deleteSupplierById(Long id);
    public List<SupplierDtoResponse> getAllSuppliers();
    public SupplierDtoResponse findSupplierById(Long id);
}
