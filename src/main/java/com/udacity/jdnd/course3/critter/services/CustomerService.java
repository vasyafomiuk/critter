package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.domain.entities.Customer;
import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CustomerService {
    CustomerDTO findCustomer(Long customerId);
    CustomerDTO createCustomer(CustomerDTO customer);
    Customer prepareCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getOwnerByPet(Long petId);
}
