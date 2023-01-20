package com.rebirth.mywebstore.services;

import com.rebirth.mywebstore.domain.models.Product;
import com.rebirth.mywebstore.services.dtos.ProductSchema;

public interface ProductService
        extends BaseService<Long, ProductSchema.ProductCreateDto, ProductSchema.ProductUpdateDto, ProductSchema.ProductDto> {

    Product getProductById(Long id);

}
