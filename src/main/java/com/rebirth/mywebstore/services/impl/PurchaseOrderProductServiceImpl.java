package com.rebirth.mywebstore.services.impl;

import com.rebirth.mywebstore.domain.models.PurchaseOrderProduct;
import com.rebirth.mywebstore.domain.repositories.PurchaseOrderProductRepository;
import com.rebirth.mywebstore.services.PurchaseOrderProductService;
import com.rebirth.mywebstore.services.dtos.PurchaseOrderProductSchema;
import com.rebirth.mywebstore.services.mappers.PurchaseOrderProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderProductServiceImpl implements PurchaseOrderProductService {

    private final PurchaseOrderProductRepository purchaseOrderProductRepository;
    private final PurchaseOrderProductMapper purchaseOrderProductMapper;

    public PurchaseOrderProductServiceImpl(PurchaseOrderProductRepository purchaseOrderProductRepository, PurchaseOrderProductMapper purchaseOrderProductMapper) {
        this.purchaseOrderProductRepository = purchaseOrderProductRepository;
        this.purchaseOrderProductMapper = purchaseOrderProductMapper;
    }


    @Override
    public PurchaseOrderProductSchema.PurchaseOrderProductDto fetchById(Long id) {
        return null;
    }

    @Override
    public List<PurchaseOrderProductSchema.PurchaseOrderProductDto> fetchAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public PurchaseOrderProductSchema.PurchaseOrderProductDto insert(PurchaseOrderProductSchema.PurchaseOrderProductCreatedDto model) {
        PurchaseOrderProduct purchaseOrderProduct = this.purchaseOrderProductMapper.orderProductCreateToOrderProduct(model);
        purchaseOrderProduct = this.purchaseOrderProductRepository.save(purchaseOrderProduct);
        return this.purchaseOrderProductMapper.orderProductToOrderProductDto(purchaseOrderProduct);
    }

    @Override
    public PurchaseOrderProductSchema.PurchaseOrderProductDto update(Long key, PurchaseOrderProductSchema.PurchaseOrderProductUpdateDto model) {
        return null;
    }

    @Override
    public void delete(Long key) {
        throw new UnsupportedOperationException();
    }
}
