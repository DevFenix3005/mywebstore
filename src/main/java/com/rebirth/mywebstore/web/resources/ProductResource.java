package com.rebirth.mywebstore.web.resources;

import com.rebirth.mywebstore.domain.models.Product;
import com.rebirth.mywebstore.services.ProductService;
import com.rebirth.mywebstore.services.dtos.ProductSchema;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductResource extends BaseResource {

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductSchema.ProductDto> getProductById(
            @PathVariable("productId") Long productId
    ) {
        ProductSchema.ProductDto product = this.productService.fetchById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping()
    public ResponseEntity<List<ProductSchema.ProductDto>> getProducts() {
        List<ProductSchema.ProductDto> product = this.productService.fetchAll();
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductSchema.ProductDto> postProduct(
            @Valid @RequestBody ProductSchema.ProductCreateDto productCreateDto,
            Errors errors
    ) {
        this.checkError(errors, Product.class.getTypeName());
        ProductSchema.ProductDto productDto = this.productService.insert(productCreateDto);
        URI uri = this.generateUri(productDto.getProductId());
        return ResponseEntity.created(uri)
                .body(productDto);
    }

}
