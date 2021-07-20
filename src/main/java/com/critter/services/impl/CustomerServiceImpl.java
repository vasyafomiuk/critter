package com.critter.services.impl;

import com.critter.consts.Messages;
import com.critter.domain.entities.Customer;
import com.critter.exceptions.customer.CustomerNotFoundException;
import com.critter.dto.CustomerDTO;
import com.critter.repository.CustomerRepository;
import com.critter.services.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO findCustomer(Long customerId) {
        return customerRepository.
                findById(customerId).
                orElseThrow(() -> new CustomerNotFoundException(String.format(Messages.CUSTOMER_NOT_FOUND, customerId))).
                toDto();
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customer) {
        return customerRepository.save(prepareCustomer(customer)).toDto();
    }

    @Override
    public Customer prepareCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().
                stream().
                map(Customer::toDto).
                collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long customerId) {
        return customerRepository.
                findById(customerId).
                orElseThrow(() -> new CustomerNotFoundException(String.format(Messages.CUSTOMER_NOT_FOUND, customerId))).
                toDto();
    }

    @Override
    @Transactional
    public CustomerDTO getOwnerByPet(Long petId) {
        return customerRepository.findAll().
                stream().
                filter(p -> p.getPetList().
                        stream().
                        anyMatch(o -> o.getId().equals(petId))).
                findFirst().
                orElseThrow(() -> new CustomerNotFoundException(String.format(Messages.CUSTOMER_FOR_PET_NOT_FOUND, petId))).
                toDto();
    }
}
