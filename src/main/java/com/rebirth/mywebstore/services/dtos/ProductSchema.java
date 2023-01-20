package com.rebirth.mywebstore.services.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

public class ProductSchema implements Serializable {

    private static class ProductBase implements Serializable {
        protected final String name;
        protected final Float price;
        protected final String description;
        protected final String image;

        public ProductBase(String name, Float price, String description, String image) {
            this.name = name;
            this.price = price;
            this.description = description;
            this.image = image;
        }

        @NotBlank
        @Length(min = 1, max = 150)
        public String getName() {
            return name;
        }

        @Digits(integer = 6, fraction = 3)
        public Float getPrice() {
            return price;
        }

        @NotBlank
        @Length(min = 1, max = 300)
        public String getDescription() {
            return description;
        }

        @NotBlank
        public String getImage() {
            return image;
        }
    }

    public static class ProductCreateDto extends ProductBase {

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public ProductCreateDto(
                @JsonProperty("name") String name,
                @JsonProperty("price") Float price,
                @JsonProperty("description") String description,
                @JsonProperty("image") String image) {
            super(name, price, description, image);
        }

        @NotBlank
        @NotNull
        @Length(min = 1, max = 150)
        @Override
        public String getName() {
            return name;
        }

        @NotNull
        @Digits(integer = 6, fraction = 3)
        @Override
        public Float getPrice() {
            return price;
        }

        @NotBlank
        @NotNull
        @Length(min = 1, max = 300)
        @Override
        public String getDescription() {
            return description;
        }

        @NotNull
        @NotBlank
        @Override
        public String getImage() {
            return image;
        }
    }

    public static class ProductUpdateDto extends ProductBase {
        public ProductUpdateDto(String name, Float price, String description, String image) {
            super(name, price, description, image);
        }
    }

    public static class ProductDto extends ProductBase {

        private final Long productId;
        private Integer quantity;

        public ProductDto(Long productId, String name, Float price, String description, String image) {
            super(name, price, description, image);
            this.productId = productId;
        }

        public Long getProductId() {
            return productId;
        }


        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }


}
