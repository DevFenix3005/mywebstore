package com.rebirth.mywebstore.services.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rebirth.mywebstore.domain.enums.OrderState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class PurchaseOrderSchema {

    private PurchaseOrderSchema() {
    }

    public static class PurchaseOrderCreateDto implements Serializable {
        protected final Long customerId;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public PurchaseOrderCreateDto(@JsonProperty("customerId") Long customerId) {
            this.customerId = customerId;
        }

        @Positive
        public Long getCustomerId() {
            return customerId;
        }

    }

    public static class PurchaseOrderUpdateDto implements Serializable {
        private final String state;
        private final Long addressId;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public PurchaseOrderUpdateDto(@JsonProperty("state") String state, @JsonProperty("addressId") Long addressId) {
            this.state = state;
            this.addressId = addressId;
        }

        @NotNull
        @NotBlank
        @Pattern(regexp = "^(CART|PURCHASED|SENT|RECEIVED)$")
        public String getState() {
            return state;
        }

        public Long getAddressId() {
            return addressId;
        }
    }

    public static class PurchaseOrderDto implements Serializable {

        private final Long purchaseOrderId;
        private final UUID orderCode;
        private final String openingDate;
        private final OrderState state;
        private final Float total;
        private final Integer generatedPoints;
        private final CustomerSchema.CustomerDto customer;

        private final AddressSchema.AddressDto address;
        private final List<ProductSchema.ProductDto> products;

        public PurchaseOrderDto(Long purchaseOrderId,
                                UUID orderCode,
                                String openingDate,
                                OrderState state,
                                Float total,
                                Integer generatedPoints,
                                CustomerSchema.CustomerDto customer,
                                AddressSchema.AddressDto address,
                                List<ProductSchema.ProductDto> products) {
            this.purchaseOrderId = purchaseOrderId;
            this.orderCode = orderCode;
            this.openingDate = openingDate;
            this.state = state;
            this.total = total;
            this.generatedPoints = generatedPoints;
            this.customer = customer;
            this.address = address;
            this.products = products;
        }

        public Long getPurchaseOrderId() {
            return purchaseOrderId;
        }

        public UUID getOrderCode() {
            return orderCode;
        }

        public String getOpeningDate() {
            return openingDate;
        }

        public OrderState getState() {
            return state;
        }

        public Float getTotal() {
            return total;
        }

        public Integer getGeneratedPoints() {
            return generatedPoints;
        }

        public CustomerSchema.CustomerDto getCustomer() {
            return customer;
        }

        public AddressSchema.AddressDto getAddress() {
            return address;
        }

        public List<ProductSchema.ProductDto> getProducts() {
            return products;
        }
    }


}
