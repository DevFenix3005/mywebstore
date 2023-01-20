package com.rebirth.mywebstore.services.impl;

import com.google.common.collect.Maps;
import com.rebirth.mywebstore.domain.enums.OrderState;
import com.rebirth.mywebstore.domain.models.Address;
import com.rebirth.mywebstore.domain.models.Customer;
import com.rebirth.mywebstore.domain.models.PurchaseOrder;
import com.rebirth.mywebstore.domain.models.PurchaseOrderProduct;
import com.rebirth.mywebstore.domain.repositories.PurchaseOrderRepository;
import com.rebirth.mywebstore.exceptions.ResourceBadCreationException;
import com.rebirth.mywebstore.exceptions.ResourceNotFoundException;
import com.rebirth.mywebstore.services.PurchaseOrderService;
import com.rebirth.mywebstore.services.dtos.PurchaseOrderSchema;
import com.rebirth.mywebstore.services.mappers.PurchaseOrderMapper;
import com.rebirth.mywebstore.services.utils.OperationsUtilComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private static final String ORDER_404 = "ORDER-404";

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;

    private final OperationsUtilComponent operationsUtilComponent;

    @Autowired
    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderMapper purchaseOrderMapper, OperationsUtilComponent operationsUtilComponent) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.operationsUtilComponent = operationsUtilComponent;
    }

    @Override
    public PurchaseOrderSchema.PurchaseOrderDto fetchById(Long id) {
        return this.purchaseOrderRepository.findById(id)
                .map(this.purchaseOrderMapper::purchaseOrderToPurchaseOrderDto)
                .orElseThrow(() -> new ResourceNotFoundException(PurchaseOrder.class.getTypeName(), id.toString(), ORDER_404));
    }

    @Override
    public List<PurchaseOrderSchema.PurchaseOrderDto> fetchAll() {
        return this.purchaseOrderRepository.findAll()
                .stream().map(this.purchaseOrderMapper::purchaseOrderToPurchaseOrderDto)
                .toList();
    }

    @Override
    public PurchaseOrderSchema.PurchaseOrderDto insert(PurchaseOrderSchema.PurchaseOrderCreateDto model) {
        PurchaseOrder purchaseOrder = this.purchaseOrderMapper.purchaseOrderCreateDtoToPurchaseOrder(model);
        purchaseOrder = this.purchaseOrderRepository.save(purchaseOrder);
        return this.purchaseOrderMapper.purchaseOrderToPurchaseOrderDto(purchaseOrder);
    }

    @Override
    public PurchaseOrderSchema.PurchaseOrderDto update(Long id, PurchaseOrderSchema.PurchaseOrderUpdateDto model) {
        return this.purchaseOrderRepository.findById(id)
                .map(order -> {
                    this.purchaseOrderMapper.updateOrder(order, model);

                    OrderState orderState = order.getState();
                    switch (orderState) {
                        case PURCHASED -> {
                            Address address = order.getAddress();
                            if (address == null) {
                                Map<String, Object> errors = Maps.newHashMap();
                                errors.put("address", "Error not assigned in this order");
                                throw new ResourceBadCreationException(Address.class.getTypeName(), ORDER_404, errors);
                            }

                            Customer customerOrder = order.getCustomer();
                            Customer customerAddress = address.getCustomer();

                            if (!customerOrder.equals(customerAddress)) {
                                Map<String, Object> errors = Maps.newHashMap();
                                errors.put("address", "This address has a different user assigned");
                                throw new ResourceBadCreationException(Address.class.getTypeName(), ORDER_404, errors);
                            }

                            List<PurchaseOrderProduct> productsInOrder = order.getProducts();
                            Float purchaseAmount = productsInOrder.stream().map(orderProduct -> {
                                Integer q = orderProduct.getQuantity();
                                Float price = orderProduct.getCurrentUnitPrice();
                                BigDecimal bigDecimal = new BigDecimal(q)
                                        .multiply(BigDecimal.valueOf(price));
                                return bigDecimal.floatValue();
                            }).reduce(0f, Float::sum);

                            order.setTotal(purchaseAmount);
                            int points = this.operationsUtilComponent.calculateAwardsPoints(purchaseAmount.intValue());
                            if (points > 0) {
                                order.setGeneratedPoints(points);
                            }
                        }
                        default -> throw new UnsupportedOperationException("This isn't implemented yet");
                    }
                    order = this.purchaseOrderRepository.save(order);
                    return this.purchaseOrderMapper.purchaseOrderToPurchaseOrderDto(order);
                })
                .orElseThrow(() -> new ResourceNotFoundException(PurchaseOrder.class.getTypeName(), id.toString(), ORDER_404));
    }

    @Override
    public void delete(Long key) {
        this.purchaseOrderRepository.deleteById(key);
    }

    @Override
    public PurchaseOrder getOrderById(Long id) {
        return this.purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PurchaseOrder.class.getTypeName(), id.toString(), ORDER_404));
    }

}
