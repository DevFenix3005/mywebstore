package com.rebirth.mywebstore.services;

import com.rebirth.mywebstore.domain.models.Address;
import com.rebirth.mywebstore.services.dtos.AddressSchema;

public interface AddressService
        extends BaseService<Long, AddressSchema.AddressCreatedDto, AddressSchema.AddressUpdatedDto, AddressSchema.AddressDto> {

    Address findAddressById(Long addressId);

}
