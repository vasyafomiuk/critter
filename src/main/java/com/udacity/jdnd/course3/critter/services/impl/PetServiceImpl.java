package com.udacity.jdnd.course3.critter.services.impl;

import com.udacity.jdnd.course3.critter.consts.Messages;
import com.udacity.jdnd.course3.critter.domain.entities.Customer;
import com.udacity.jdnd.course3.critter.domain.entities.Pet;
import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.exceptions.customer.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exceptions.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.consts.Messages.CUSTOMER_NOT_FOUND;
import static com.udacity.jdnd.course3.critter.consts.Messages.PET_NOT_FOUND;

@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetServiceImpl(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        Customer customer = customerRepository.
                findById(petDTO.getOwnerId()).
                orElseThrow(() -> new CustomerNotFoundException(String.format(CUSTOMER_NOT_FOUND, petDTO.getOwnerId())));
        pet.setOwner(customer);
        pet = petRepository.save(pet);
        return pet.toDto();
    }

    @Override
    public List<PetDTO> findAllPets() {
        return petRepository.
                findAll().
                stream().
                map(Pet::toDto).
                collect(Collectors.toList());
    }

    @Override
    public PetDTO findPetById(Long petId) {
        return petRepository.
                findById(petId).
                orElseThrow(() -> new PetNotFoundException(String.format(PET_NOT_FOUND, petId))).
                toDto();
    }

    @Override
    public List<PetDTO> getPetsByOwner(Long ownerId) {
        return petRepository.
                findAll().
                stream().
                filter(p -> p.getOwner().getId().equals(ownerId)).
                map(Pet::toDto).
                collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getOwnerByPet(Long petId) {
        Pet pet = petRepository.
                findById(petId).
                orElseThrow(() -> new PetNotFoundException(String.format(PET_NOT_FOUND, petId)));
        System.out.println("[pet service] PET :: " + pet);
        return pet.getOwner().toDto();
//        return petRepository.findById(petId).
//                orElseThrow(() -> new CustomerNotFoundException(String.format(PET_NOT_FOUND, petId))).
//                getOwner().
//                toDto();
    }
}
