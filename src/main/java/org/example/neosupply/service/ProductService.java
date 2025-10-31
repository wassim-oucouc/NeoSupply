package org.example.neosupply.service;


import org.example.neosupply.dto.request.ProductDTO;
import org.example.neosupply.dto.response.ProductDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {


    public ProductDtoResponse createProduct(ProductDTO productDTO);
    public ProductDtoResponse updateProductById(ProductDTO productDTO,Long id);
    public void deleteProductById(Long id);
    public ProductDtoResponse findProductById(Long id);
    public List<ProductDtoResponse> getAllProducts();

}
