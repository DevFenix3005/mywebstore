package com.rebirth.mywebstore.web.resources;

import com.rebirth.mywebstore.domain.models.PurchaseOrderProduct;
import com.rebirth.mywebstore.services.PurchaseOrderProductService;
import com.rebirth.mywebstore.services.dtos.PurchaseOrderProductSchema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/purchaseOrderProduct")
public class PurchaseOrderProductResource extends BaseResource {

    private final PurchaseOrderProductService purchaseOrderProductService;

    @Autowired
    public PurchaseOrderProductResource(PurchaseOrderProductService purchaseOrderProductService) {
        this.purchaseOrderProductService = purchaseOrderProductService;
    }

    @PostMapping()
    public ResponseEntity<PurchaseOrderProductSchema.PurchaseOrderProductDto> postOrderProduct(
            @RequestBody @Valid PurchaseOrderProductSchema.PurchaseOrderProductCreatedDto orderProductCreatedDto
    ) {
        PurchaseOrderProductSchema.PurchaseOrderProductDto orderProductDto = this.purchaseOrderProductService.insert(orderProductCreatedDto);
        URI orderProductDtoURI = this.generateUri(orderProductDto.getOrderProductId());
        return ResponseEntity.created(orderProductDtoURI).body(orderProductDto);
    }

    @PutMapping("{orderProductId}")
    public ResponseEntity<PurchaseOrderProductSchema.PurchaseOrderProductDto> updateOrderProduct(
            @PathVariable("orderProductId") Long orderProductId,
            @RequestBody @Valid PurchaseOrderProductSchema.PurchaseOrderProductUpdateDto orderProductUpdateDto,
            Errors errors
    ) {
        this.checkError(errors, PurchaseOrderProduct.class.getTypeName());
        PurchaseOrderProductSchema.PurchaseOrderProductDto orderProductDto = this.purchaseOrderProductService.update(orderProductId, orderProductUpdateDto);
        return ResponseEntity.ok(orderProductDto);
    }

}
