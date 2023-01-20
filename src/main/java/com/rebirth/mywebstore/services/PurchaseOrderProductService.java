package com.rebirth.mywebstore.services;

import com.rebirth.mywebstore.services.dtos.PurchaseOrderProductSchema;

public interface PurchaseOrderProductService
        extends BaseService<Long,
        PurchaseOrderProductSchema.PurchaseOrderProductCreatedDto,
        PurchaseOrderProductSchema.PurchaseOrderProductUpdateDto,
        PurchaseOrderProductSchema.PurchaseOrderProductDto> {
}
