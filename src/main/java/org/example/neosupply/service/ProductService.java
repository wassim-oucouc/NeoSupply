package org.example.neosupply.service;


import org.example.neosupply.dto.request.ProductDTO;
import org.example.neosupply.dto.response.ProductDtoResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {


    public ProductDtoResponse createProduct(ProductDTO productDTO);
}
