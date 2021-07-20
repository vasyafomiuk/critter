package com.udacity.jdnd.course3.critter.services.impl;

import com.udacity.jdnd.course3.critter.domain.entities.Customer;
import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.exceptions.customer.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.consts.Messages.CUSTOMER_FOR_PET_NOT_FOUND;
import static com.udacity.jdnd.course3.critter.consts.Messages.CUSTOMER_NOT_FOUND;

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
                orElseThrow(() -> new CustomerNotFoundException(String.format(CUSTOMER_NOT_FOUND, customerId))).
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
                orElseThrow(() -> new CustomerNotFoundException(String.format(CUSTOMER_NOT_FOUND, customerId))).
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
                orElseThrow(() -> new CustomerNotFoundException(String.format(CUSTOMER_FOR_PET_NOT_FOUND, petId))).
                toDto();
    }
}
