package com.critter.services;

import com.critter.domain.entities.Customer;
import com.critter.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO findCustomer(Long customerId);
    CustomerDTO createCustomer(CustomerDTO customer);
    Customer prepareCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long customerId);
    CustomerDTO getOwnerByPet(Long petId);
}
