package com.rebirth.mywebstore.services.mappers;

import com.rebirth.mywebstore.domain.models.Customer;
import com.rebirth.mywebstore.services.dtos.CustomerSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifyAt", ignore = true)
    @Mapping(target = "addressList", ignore = true)
    @Mapping(target = "purchaseOrders", ignore = true)
    @Mapping(target = "birthday", source = "birthday", dateFormat = "yyyy-MM-dd")
    Customer userCreateDtoToUser(CustomerSchema.CustomerCreateDto userCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifyAt", ignore = true)
    @Mapping(target = "addressList", ignore = true)
    @Mapping(target = "purchaseOrders", ignore = true)
    @Mapping(target = "name", source = "name", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "lastname", source = "lastname", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "birthday", source = "birthday", dateFormat = "yyyy-MM-dd", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "email", source = "email", nullValuePropertyMappingStrategy = IGNORE)
    void updateUser(CustomerSchema.CustomerUpdateDto userUpdateDto, @MappingTarget Customer customer);


    @Mapping(target = "birthday", source = "birthday", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "customerId", source = "id")
    CustomerSchema.CustomerDto userToUserDto(Customer customer);

}
