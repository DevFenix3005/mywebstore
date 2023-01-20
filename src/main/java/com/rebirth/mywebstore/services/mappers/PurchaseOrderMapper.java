package com.rebirth.mywebstore.services.mappers;

import com.rebirth.mywebstore.domain.models.PurchaseOrder;
import com.rebirth.mywebstore.services.AddressService;
import com.rebirth.mywebstore.services.CustomerService;
import com.rebirth.mywebstore.services.dtos.PurchaseOrderSchema;
import com.rebirth.mywebstore.services.mappers.helpers.OrderMapperHelper;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CustomerService.class, AddressService.class, CustomerMapper.class, AddressMapper.class, OrderMapperHelper.class}
)
public interface PurchaseOrderMapper {

    @Mapping(target = "customer", source = "customerId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifyAt", ignore = true)
    @Mapping(target = "orderCode", ignore = true)
    @Mapping(target = "openingDate", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "generatedPoints", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "products", ignore = true)
    PurchaseOrder purchaseOrderCreateDtoToPurchaseOrder(PurchaseOrderSchema.PurchaseOrderCreateDto purchaseOrderCreateDto);

    @Mapping(target = "state", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "address", source = "addressId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifyAt", ignore = true)
    @Mapping(target = "orderCode", ignore = true)
    @Mapping(target = "openingDate", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "generatedPoints", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateOrder(@MappingTarget PurchaseOrder purchaseOrder, PurchaseOrderSchema.PurchaseOrderUpdateDto purchaseOrderUpdateDto);


    @Mapping(target = "purchaseOrderId", source = "id")
    @Mapping(target = "products", source = "products")
    @Mapping(target = "address", source = "address")
    PurchaseOrderSchema.PurchaseOrderDto purchaseOrderToPurchaseOrderDto(PurchaseOrder purchaseOrder);


}
