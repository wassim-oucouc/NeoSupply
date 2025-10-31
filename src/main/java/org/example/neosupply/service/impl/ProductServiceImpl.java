package org.example.neosupply.service.impl;


import org.example.neosupply.dto.request.ProductDTO;
import org.example.neosupply.dto.response.ProductDtoResponse;
import org.example.neosupply.entity.Inventory;
import org.example.neosupply.entity.Product;
import org.example.neosupply.exceptions.ProductNotFoundException;
import org.example.neosupply.mapper.ProductMapper;
import org.example.neosupply.repository.ProductRepository;
import org.example.neosupply.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
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

    public ProductDtoResponse updateProductById(ProductDTO productDTO,Long id)
    {
        Product product = this.productRepository.findById(id).orElseThrow(() ->  new ProductNotFoundException("product not exists"));

            product.setId(id);
            product.setName(productDTO.getName());
            product.setSku(productDTO.getSku());
            product.setPrice(productDTO.getPrice());
            product.setActive(productDTO.isActive());
            product.setDescription(productDTO.getDescription());
            this.productRepository.save(product);

            return this.productMapper.toDtoResponse(product);
    }

    public void deleteProductById(Long id)
    {
        this.productRepository.deleteById(id);
    }

    public ProductDtoResponse findProductById(Long id)
    {
      Product product =  this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("product not exists"));

        return  this.productMapper.toDtoResponse(product);
    }

    public List<ProductDtoResponse> getAllProducts()
    {
        return this.productRepository.findAll().stream().map(productMapper::toDtoResponse).toList();
    }
}
