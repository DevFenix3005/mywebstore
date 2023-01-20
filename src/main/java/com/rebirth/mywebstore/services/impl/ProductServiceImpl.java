package com.rebirth.mywebstore.services.impl;

import com.rebirth.mywebstore.domain.models.Product;
import com.rebirth.mywebstore.domain.repositories.ProductRepository;
import com.rebirth.mywebstore.exceptions.ResourceNotFoundException;
import com.rebirth.mywebstore.services.ProductService;
import com.rebirth.mywebstore.services.dtos.ProductSchema;
import com.rebirth.mywebstore.services.mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductSchema.ProductDto fetchById(Long id) {
        return this.productRepository.findById(id)
                .map(this.productMapper::productToProductDto)
                .orElseThrow(() -> new ResourceNotFoundException(Product.class.getTypeName(), id.toString(), "PRODUCT-404"));
    }

    @Override
    public List<ProductSchema.ProductDto> fetchAll() {
        return this.productRepository.findAll()
                .stream()
                .map(this.productMapper::productToProductDto)
                .toList();
    }

    @Override
    public ProductSchema.ProductDto insert(ProductSchema.ProductCreateDto model) {
        Product product = this.productMapper.productCreateToProduct(model);
        product = this.productRepository.save(product);
        return this.productMapper.productToProductDto(product);
    }

    @Override
    public ProductSchema.ProductDto update(Long key, ProductSchema.ProductUpdateDto model) {
        return null;
    }

    @Override
    public void delete(Long key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Product getProductById(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Product.class.getTypeName(), id.toString(), "PRODUCT-404"));
    }
}
