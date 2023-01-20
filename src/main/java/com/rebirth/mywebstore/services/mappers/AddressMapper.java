package com.rebirth.mywebstore.services.mappers;

import com.rebirth.mywebstore.domain.models.Address;
import com.rebirth.mywebstore.services.CustomerService;
import com.rebirth.mywebstore.services.dtos.AddressSchema;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CustomerService.class})
public interface AddressMapper {

    @Mapping(target = "customer", source = "customerId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifyAt", ignore = true)
    @Mapping(target = "orderList", ignore = true)
    Address addressCreatedDtoToAddress(AddressSchema.AddressCreatedDto addressCreatedDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifyAt", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "orderList", ignore = true)
    @Mapping(target = "street", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "city", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "state", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "country", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "zipcode", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAddress(@MappingTarget Address address, AddressSchema.AddressUpdatedDto addressUpdatedDto);

    @Mapping(target = "addressId", source = "id")
    AddressSchema.AddressDto addressToAddressDto(Address address);

}
