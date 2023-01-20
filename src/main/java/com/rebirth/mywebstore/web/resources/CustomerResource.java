package com.rebirth.mywebstore.web.resources;

import com.rebirth.mywebstore.domain.models.Customer;
import com.rebirth.mywebstore.services.CustomerService;
import com.rebirth.mywebstore.services.dtos.CustomerSchema;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Month;

@RestController
@RequestMapping("/customer")
public class CustomerResource extends BaseResource {

    private final CustomerService customerService;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/{email}")
    public ResponseEntity<CustomerSchema.CustomerDto> getCustomerByEmail(@PathVariable("email") String email) {
        CustomerSchema.CustomerDto userDto = customerService.fetchByEmail(email);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping(path = "/{userId}/info")
    public ResponseEntity<CustomerSchema.CustomerInfo> getCustomerInfoByMonth(
            @PathVariable("userId") Long userId,
            @RequestParam(name = "month") Month month) {
        CustomerSchema.CustomerInfo userDto = customerService.fetchUserInfoById(userId, month);
        return ResponseEntity.ok(userDto);
    }


    @PostMapping()
    public ResponseEntity<CustomerSchema.CustomerDto> postUser(@RequestBody @Valid CustomerSchema.CustomerCreateDto userDtoCreateDto, Errors errors) {
        this.checkError(errors, Customer.class.getTypeName());
        CustomerSchema.CustomerDto userDto = customerService.insert(userDtoCreateDto);
        URI uri = this.generateUri(userDto.getCustomerId());
        return ResponseEntity.created(uri).body(userDto);
    }


}
