package com.rebirth.mywebstore.web.resources;

import com.rebirth.mywebstore.domain.models.Address;
import com.rebirth.mywebstore.services.AddressService;
import com.rebirth.mywebstore.services.dtos.AddressSchema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/address")
public class AddressResource extends BaseResource {

    private final AddressService addressService;

    @Autowired
    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{addressId}")
    public AddressSchema.AddressDto getAddressById(@PathVariable("addressId") Long id) {
        return this.addressService.fetchById(id);
    }

    @PostMapping
    public ResponseEntity<AddressSchema.AddressDto> postAddresById(
            @Valid @RequestBody AddressSchema.AddressCreatedDto addressCreatedDto,
            Errors errors
    ) {
        this.checkError(errors, Address.class.getTypeName());
        AddressSchema.AddressDto address = this.addressService.insert(addressCreatedDto);
        URI addressUri = this.generateUri(address.getAddressId());
        return ResponseEntity.created(addressUri).body(address);
    }



}
