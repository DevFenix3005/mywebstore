package com.rebirth.mywebstore.services;

import com.rebirth.mywebstore.domain.models.PurchaseOrder;
import com.rebirth.mywebstore.services.dtos.PurchaseOrderSchema;

public interface PurchaseOrderService
        extends BaseService<Long,
        PurchaseOrderSchema.PurchaseOrderCreateDto,
        PurchaseOrderSchema.PurchaseOrderUpdateDto,
        PurchaseOrderSchema.PurchaseOrderDto> {

    PurchaseOrder getOrderById(Long id);

}
