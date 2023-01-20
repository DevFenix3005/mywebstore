package com.rebirth.mywebstore.services.mappers;

import com.rebirth.mywebstore.domain.models.PurchaseOrderProduct;
import com.rebirth.mywebstore.services.PurchaseOrderService;
import com.rebirth.mywebstore.services.ProductService;
import com.rebirth.mywebstore.services.dtos.PurchaseOrderProductSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {
                ProductService.class,
                PurchaseOrderService.class
        })
public interface PurchaseOrderProductMapper {

    @Mapping(target = "purchaseOrder", source = "orderId")
    @Mapping(target = "product", source = "productId")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifyAt", ignore = true)
    @Mapping(target = "currentUnitPrice", ignore = true)
    PurchaseOrderProduct orderProductCreateToOrderProduct(PurchaseOrderProductSchema.PurchaseOrderProductCreatedDto orderProductCreatedDto);


    @Mapping(target = "orderProductId", source = "id")
    @Mapping(target = "currentUnitPrice", source = "currentUnitPrice")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "total", expression = "java(currentUnitPrice * quantity)")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "orderCode", source = "purchaseOrder.orderCode")
    PurchaseOrderProductSchema.PurchaseOrderProductDto orderProductToOrderProductDto(PurchaseOrderProduct purchaseOrderProduct);


}
