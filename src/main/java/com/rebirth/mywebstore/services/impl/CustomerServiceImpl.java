package com.rebirth.mywebstore.services.impl;

import com.rebirth.mywebstore.domain.models.Customer;
import com.rebirth.mywebstore.domain.repositories.CustomerRepository;
import com.rebirth.mywebstore.exceptions.ResourceBadCreationException;
import com.rebirth.mywebstore.exceptions.ResourceNotFoundException;
import com.rebirth.mywebstore.services.CustomerService;
import com.rebirth.mywebstore.services.dtos.CustomerSchema;
import com.rebirth.mywebstore.services.mappers.CustomerMapper;
import com.rebirth.mywebstore.services.utils.OperationsUtilComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String USER_404 = "CUSTOMER-404";
    private static final String USER_400 = "CUSTOMER-400";
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    private final OperationsUtilComponent operationsUtilComponent;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, OperationsUtilComponent operationsUtilComponent) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.operationsUtilComponent = operationsUtilComponent;
    }

    @Override
    public CustomerSchema.CustomerDto fetchById(Long id) {
        Optional<Customer> user = customerRepository.findById(id);
        return user.map(this.customerMapper::userToUserDto)
                .orElseThrow(() -> new ResourceNotFoundException(Customer.class.getTypeName(), id.toString(), USER_404));
    }

    @Override
    public CustomerSchema.CustomerDto fetchByEmail(String email) {
        return customerRepository.findUserByEmail(email)
                .map(this.customerMapper::userToUserDto)
                .orElseThrow(() -> new ResourceNotFoundException("Email", Customer.class.getTypeName(), email, USER_404));
    }

    @Override
    public Customer getUserById(Long id) {
        return this.customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Customer.class.getTypeName(), id.toString(), USER_404));
    }

    @Override
    public CustomerSchema.CustomerInfo fetchUserInfoById(Long userId, Month month) {

        LocalDateTime[] dates = operationsUtilComponent.getFirstAndLastDateOfTheMonth(month);
        LocalDateTime localDateTime = dates[0];
        LocalDateTime end = dates[1];
        Long awardPoints = this.customerRepository.sumAwaredPointInOrderByUserIdAndBetweenCreateAtDate(userId, localDateTime, end);
        return this.customerRepository.findById(userId)
                .map(user -> new CustomerSchema.CustomerInfo(user.getName(), user.getLastname(), user.getEmail(), user.getBirthday(), awardPoints))
                .orElseThrow(() -> new ResourceNotFoundException(Customer.class.getTypeName(), userId.toString(), USER_404));
    }

    @Override
    public List<CustomerSchema.CustomerDto> fetchAll() {
        return customerRepository.findAll()
                .stream()
                .map(this.customerMapper::userToUserDto).toList();
    }

    @Override
    public CustomerSchema.CustomerDto insert(CustomerSchema.CustomerCreateDto model) {
        try {
            Customer customer = this.customerMapper.userCreateDtoToUser(model);
            customer = customerRepository.save(customer);
            return this.customerMapper.userToUserDto(customer);

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            String message = dataIntegrityViolationException.getMessage();
            Map<String, Object> error = new HashMap<>();
            error.put("dup_un_key", message);
            throw new ResourceBadCreationException(Customer.class.getTypeName(), USER_400, error);
        }
    }

    @Override
    public CustomerSchema.CustomerDto update(Long id, CustomerSchema.CustomerUpdateDto model) {
        return customerRepository.findById(id)
                .map(user -> {
                    this.customerMapper.updateUser(model, user);
                    user = customerRepository.save(user);
                    return this.customerMapper.userToUserDto(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException(Customer.class.getTypeName(), id.toString(), USER_404));
    }

    @Override
    public void delete(Long key) {
        this.customerRepository.deleteById(key);
    }


}
