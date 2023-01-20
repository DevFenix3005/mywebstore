package com.rebirth.mywebstore.services.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

public class AddressSchema {
    private AddressSchema() {
    }

    private static class AddressBase implements Serializable {
        protected final String street;
        protected final String city;
        protected final String state;
        protected final String country;
        protected final String zipcode;

        public AddressBase(String street, String city, String state, String country, String zipcode) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.country = country;
            this.zipcode = zipcode;
        }

        @NotBlank
        @Length(min = 1, max = 90)
        public String getStreet() {
            return street;
        }

        @NotBlank
        @Length(min = 1, max = 90)
        public String getCity() {
            return city;
        }

        @NotBlank
        @Length(min = 1, max = 90)
        public String getState() {
            return state;
        }

        @NotBlank
        @Length(min = 1, max = 90)
        public String getCountry() {
            return country;
        }

        @NotBlank
        @Length(min = 1, max = 90)
        public String getZipcode() {
            return zipcode;
        }
    }

    public static class AddressCreatedDto extends AddressBase {
        private final Long customerId;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public AddressCreatedDto(
                @JsonProperty("street") String street,
                @JsonProperty("city") String city,
                @JsonProperty("state") String state,
                @JsonProperty("country") String country,
                @JsonProperty("zipcode") String zipcode,
                @JsonProperty("customerId") Long customerId
        ) {
            super(street, city, state, country, zipcode);
            this.customerId = customerId;
        }

        public Long getCustomerId() {
            return customerId;
        }

        @NotBlank
        @Length(min = 1, max = 90)
        @NotNull
        @Override
        public String getStreet() {
            return street;
        }

        @NotBlank
        @Length(min = 1, max = 90)
        @NotNull
        @Override
        public String getCity() {
            return city;
        }

        @NotBlank
        @Length(min = 1, max = 90)
        @NotNull
        @Override
        public String getState() {
            return state;
        }

        @NotBlank
        @Length(min = 1, max = 90)
        @NotNull
        @Override
        public String getCountry() {
            return country;
        }

        @NotBlank
        @Length(min = 1, max = 90)
        @NotNull
        @Override
        public String getZipcode() {
            return zipcode;
        }

    }

    public static class AddressUpdatedDto extends AddressBase {

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public AddressUpdatedDto(
                @JsonProperty("street") String street,
                @JsonProperty("city") String city,
                @JsonProperty("state") String state,
                @JsonProperty("country") String country,
                @JsonProperty("zipcode") String zipcode) {
            super(street, city, state, country, zipcode);
        }
    }

    public static class AddressDto extends AddressBase {
        private final Long addressId;

        public AddressDto(String street, String city, String state, String country, String zipcode, Long addressId) {
            super(street, city, state, country, zipcode);
            this.addressId = addressId;
        }

        public Long getAddressId() {
            return addressId;
        }
    }


}
