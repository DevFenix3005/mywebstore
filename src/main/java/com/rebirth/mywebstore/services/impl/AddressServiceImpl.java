package com.rebirth.mywebstore.services.impl;

import com.rebirth.mywebstore.domain.models.Address;
import com.rebirth.mywebstore.domain.repositories.AddressRepository;
import com.rebirth.mywebstore.exceptions.ResourceNotFoundException;
import com.rebirth.mywebstore.services.AddressService;
import com.rebirth.mywebstore.services.dtos.AddressSchema;
import com.rebirth.mywebstore.services.mappers.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private static final String ADDRESS_404 = "ADDRESS-404";
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressSchema.AddressDto fetchById(Long id) {
        return this.addressRepository.findById(id)
                .map(this.addressMapper::addressToAddressDto)
                .orElseThrow(() -> new ResourceNotFoundException(Address.class.getTypeName(), id.toString(), ADDRESS_404));
    }

    @Override
    public List<AddressSchema.AddressDto> fetchAll() {
        return this.addressRepository.findAll()
                .stream().map(this.addressMapper::addressToAddressDto)
                .toList();
    }

    @Override
    public AddressSchema.AddressDto insert(AddressSchema.AddressCreatedDto model) {
        Address address = this.addressMapper.addressCreatedDtoToAddress(model);
        address = this.addressRepository.save(address);
        return this.addressMapper.addressToAddressDto(address);
    }

    @Override
    public AddressSchema.AddressDto update(Long id, AddressSchema.AddressUpdatedDto model) {

        return this.addressRepository.findById(id)
                .map(address -> {
                    this.addressMapper.updateAddress(address, model);
                    this.addressRepository.save(address);
                    return this.addressMapper.addressToAddressDto(address);
                })
                .orElseThrow(() -> new ResourceNotFoundException(Address.class.getTypeName(), id.toString(), ADDRESS_404));

    }

    @Override
    public void delete(Long key) {
        throw new UnsupportedOperationException("No implemented yet");

    }

    @Override
    public Address findAddressById(Long addressId) {
        return this.addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException(Address.class.getTypeName(), addressId.toString(), ADDRESS_404));
    }
}
