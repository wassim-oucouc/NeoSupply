package org.example.neosupply.service.impl;


import org.example.neosupply.dto.request.ProductDTO;
import org.example.neosupply.dto.response.ProductDtoResponse;
import org.example.neosupply.entity.Product;
import org.example.neosupply.mapper.ProductMapper;
import org.example.neosupply.repository.ProductRepository;
import org.example.neosupply.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDtoResponse createProduct(ProductDTO productDTO)
    {
        Product product = this.productMapper.toEntity(productDTO);
        this.productRepository.save(product);
        return this.productMapper.toDtoResponse(product);
    }
}
