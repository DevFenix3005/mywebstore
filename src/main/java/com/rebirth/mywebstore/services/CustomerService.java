package com.rebirth.mywebstore.services;

import com.rebirth.mywebstore.domain.models.Customer;
import com.rebirth.mywebstore.services.dtos.CustomerSchema;

import java.time.Month;

public interface CustomerService extends BaseService<Long, CustomerSchema.CustomerCreateDto, CustomerSchema.CustomerUpdateDto, CustomerSchema.CustomerDto> {

    CustomerSchema.CustomerDto fetchByEmail(String email);

    Customer getUserById(Long id);

    CustomerSchema.CustomerInfo fetchUserInfoById(Long userId, Month month);

}
