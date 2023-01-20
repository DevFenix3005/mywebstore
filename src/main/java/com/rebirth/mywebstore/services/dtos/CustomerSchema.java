package com.rebirth.mywebstore.services.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;

public class CustomerSchema {

    private CustomerSchema() {
    }

    private static class CustomerDtoBase implements Serializable {

        @NotBlank
        @Length(min = 1, max = 45)
        protected final String name;
        @NotBlank
        @Length(min = 1, max = 45)
        protected final String lastname;

        @NotBlank
        @Pattern(regexp = "^(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})$", flags = {Pattern.Flag.DOTALL})
        protected final String birthday;
        @NotBlank
        @Length(min = 1, max = 90)
        @Pattern(regexp = "^(?<id>\\w+)@(?<site>\\w+)\\.(?<termination>\\w+)$", flags = {Pattern.Flag.DOTALL})
        protected final String email;

        public CustomerDtoBase(String name, String lastname, String birthday, String email) {
            this.name = name;
            this.lastname = lastname;
            this.birthday = birthday;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getLastname() {
            return lastname;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getEmail() {
            return email;
        }
    }

    public static class CustomerCreateDto extends CustomerDtoBase {

        public CustomerCreateDto(String name, String lastname, String birthday, String email) {
            super(name, lastname, birthday, email);
        }

        @NotBlank
        @NotNull
        @Length(min = 1, max = 45)
        @Override
        public String getName() {
            return name;
        }

        @NotBlank
        @NotNull
        @Length(min = 1, max = 45)
        @Override
        public String getLastname() {
            return lastname;
        }

        @NotBlank
        @NotNull
        @Pattern(regexp = "^(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})$", flags = {Pattern.Flag.DOTALL})
        @Override
        public String getBirthday() {
            return birthday;
        }

        @NotNull
        @NotBlank
        @Length(min = 1, max = 90)
        @Email(regexp = "^(?<id>\\w+)@(?<site>\\w+)\\.(?<termination>\\w+)$", flags = {Pattern.Flag.DOTALL})
        @Override
        public String getEmail() {
            return email;
        }
    }

    public static class CustomerUpdateDto extends CustomerDtoBase {
        public CustomerUpdateDto(String name, String lastname, String birthday, String email) {
            super(name, lastname, birthday, email);
        }
    }

    public static class CustomerDto extends CustomerDtoBase {
        private final Long customerId;

        public CustomerDto(Long customerId, String name, String lastname, String birthday, String email) {
            super(name, lastname, birthday, email);
            this.customerId = customerId;
        }

        public Long getCustomerId() {
            return customerId;
        }
    }


    public static class CustomerInfo implements Serializable {

        private final String name;
        private final String lastname;
        private final String email;
        private final LocalDate birthday;
        private final Long totalAwardPoints;

        public CustomerInfo(String name, String lastname, String email, LocalDate birthday, Long totalAwardPoints) {
            this.name = name;
            this.lastname = lastname;
            this.email = email;
            this.birthday = birthday;
            this.totalAwardPoints = totalAwardPoints;
        }

        public String getName() {
            return name;
        }

        public String getLastname() {
            return lastname;
        }

        public String getEmail() {
            return email;
        }

        public LocalDate getBirthday() {
            return birthday;
        }

        public Long getTotalAwardPoints() {
            return totalAwardPoints;
        }
    }


}
