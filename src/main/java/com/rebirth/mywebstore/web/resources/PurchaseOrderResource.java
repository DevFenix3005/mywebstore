package com.rebirth.mywebstore.web.resources;

import com.rebirth.mywebstore.domain.models.PurchaseOrder;
import com.rebirth.mywebstore.services.PurchaseOrderService;
import com.rebirth.mywebstore.services.dtos.PurchaseOrderSchema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/purchaseOrder")
public class PurchaseOrderResource extends BaseResource {


    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    public PurchaseOrderResource(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping()
    public ResponseEntity<List<PurchaseOrderSchema.PurchaseOrderDto>> getOrders() {
        List<PurchaseOrderSchema.PurchaseOrderDto> orders = this.purchaseOrderService.fetchAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PurchaseOrderSchema.PurchaseOrderDto> getOrderById(@PathVariable("orderId") Long orderId) {
        PurchaseOrderSchema.PurchaseOrderDto orderDto = this.purchaseOrderService.fetchById(orderId);
        return ResponseEntity.ok(orderDto);
    }

    @PostMapping()
    public ResponseEntity<PurchaseOrderSchema.PurchaseOrderDto> postOrder(
            @Valid @RequestBody PurchaseOrderSchema.PurchaseOrderCreateDto orderCreateDto, Errors errors) {
        this.checkError(errors, PurchaseOrder.class.getTypeName());
        PurchaseOrderSchema.PurchaseOrderDto orderDto = this.purchaseOrderService.insert(orderCreateDto);
        URI uri = this.generateUri(orderDto.getPurchaseOrderId());
        return ResponseEntity.created(uri).body(orderDto);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<PurchaseOrderSchema.PurchaseOrderDto> updateOrder(
            @PathVariable("orderId") Long orderId,
            @Valid @RequestBody PurchaseOrderSchema.PurchaseOrderUpdateDto updateDto,
            Errors errors) {
        this.checkError(errors, PurchaseOrder.class.getTypeName());
        PurchaseOrderSchema.PurchaseOrderDto orderDto = this.purchaseOrderService.update(orderId, updateDto);
        return ResponseEntity.ok(orderDto);
    }

}
