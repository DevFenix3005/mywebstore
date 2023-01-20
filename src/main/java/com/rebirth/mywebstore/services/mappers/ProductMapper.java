package com.rebirth.mywebstore.services.mappers;

import com.rebirth.mywebstore.domain.models.Product;
import com.rebirth.mywebstore.services.dtos.ProductSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifyAt", ignore = true)
    @Mapping(target = "purchaseOrders", ignore = true)
    Product productCreateToProduct(ProductSchema.ProductCreateDto productCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifyAt", ignore = true)
    @Mapping(target = "purchaseOrders", ignore = true)
    void productUpdating(@MappingTarget Product product, ProductSchema.ProductUpdateDto productUpdateDto);

    @Mapping(target = "productId", source = "id")
    @Mapping(target = "quantity", defaultValue = "0", ignore = true)
    ProductSchema.ProductDto productToProductDto(Product product);

}
