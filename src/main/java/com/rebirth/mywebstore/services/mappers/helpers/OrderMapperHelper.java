package com.rebirth.mywebstore.services.mappers.helpers;

import com.google.common.collect.Lists;
import com.rebirth.mywebstore.domain.models.PurchaseOrderProduct;
import com.rebirth.mywebstore.domain.models.Product;
import com.rebirth.mywebstore.services.dtos.ProductSchema;
import com.rebirth.mywebstore.services.mappers.ProductMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapperHelper {

    private final ProductMapper productMapper;

    public OrderMapperHelper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<ProductSchema.ProductDto> orderProductsToProductDtos(List<PurchaseOrderProduct> purchaseOrderProducts) {

        List<ProductSchema.ProductDto> productDtoList = Lists.newArrayList();
        for (PurchaseOrderProduct purchaseOrderProduct : purchaseOrderProducts) {
            Integer orderProductQuantity = purchaseOrderProduct.getQuantity();
            Product product = purchaseOrderProduct.getProduct();
            ProductSchema.ProductDto productDto = this.productMapper.productToProductDto(product);
            productDto.setQuantity(orderProductQuantity);
            productDtoList.add(productDto);
        }
        return productDtoList;

    }

}
