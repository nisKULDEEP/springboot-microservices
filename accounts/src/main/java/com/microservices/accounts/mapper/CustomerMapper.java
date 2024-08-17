package com.microservices.accounts.mapper;

import com.microservices.accounts.dtos.CustomerRequestDTO;
import com.microservices.accounts.dtos.CustomerResponseDTO;
import com.microservices.accounts.models.Customer;

public class CustomerMapper {
    public static CustomerResponseDTO mapToCustomerDto(Customer customer, CustomerResponseDTO customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerRequestDTO customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
