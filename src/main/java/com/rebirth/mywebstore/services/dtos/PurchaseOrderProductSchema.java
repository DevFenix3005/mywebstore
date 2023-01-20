package com.rebirth.mywebstore.services.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.util.UUID;

public class PurchaseOrderProductSchema {

    private PurchaseOrderProductSchema() {
    }

    public static class PurchaseOrderProductBase implements Serializable {
        protected final Long orderId;
        protected final Long productId;
        protected final Integer quantity;

        public PurchaseOrderProductBase(Long orderId, Long productId, Integer quantity) {
            this.orderId = orderId;
            this.productId = productId;
            this.quantity = quantity;
        }

        @Positive
        public Long getProductId() {
            return productId;
        }

        @Positive
        public Long getOrderId() {
            return orderId;
        }

        @Positive
        public Integer getQuantity() {
            return quantity;
        }
    }

    public static class PurchaseOrderProductCreatedDto extends PurchaseOrderProductBase {

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public PurchaseOrderProductCreatedDto(
                @JsonProperty("orderId") Long orderId,
                @JsonProperty("productId") Long productId,
                @JsonProperty("quantity") Integer quantity) {
            super(orderId, productId, quantity);
        }

        @NotNull
        @Positive
        @Override
        public Long getProductId() {
            return productId;
        }

        @NotNull
        @Positive
        @Override
        public Long getOrderId() {
            return orderId;
        }

        @NotNull
        @Positive
        @Override
        public Integer getQuantity() {
            return quantity;
        }


    }

    public static class PurchaseOrderProductUpdateDto extends PurchaseOrderProductBase {
        public PurchaseOrderProductUpdateDto(Long orderId, Long productId, Integer quantity) {
            super(orderId, productId, quantity);
        }
    }

    public static class PurchaseOrderProductDto implements Serializable {

        private final Long orderProductId;
        private final Float currentUnitPrice;
        private final Integer quantity;
        private final Float total;
        private final String productName;
        private final UUID orderCode;

        public PurchaseOrderProductDto(Long orderProductId,
                                       Integer quantity,
                                       Float currentUnitPrice,
                                       Float total,
                                       String productName,
                                       UUID orderCode) {
            this.orderProductId = orderProductId;
            this.quantity = quantity;
            this.currentUnitPrice = currentUnitPrice;
            this.total = total;
            this.productName = productName;
            this.orderCode = orderCode;
        }

        public Long getOrderProductId() {
            return orderProductId;
        }

        public Float getCurrentUnitPrice() {
            return currentUnitPrice;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public Float getTotal() {
            return total;
        }

        public String getProductName() {
            return productName;
        }

        public UUID getOrderCode() {
            return orderCode;
        }
    }

}
