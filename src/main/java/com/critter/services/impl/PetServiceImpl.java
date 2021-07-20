package com.critter.services.impl;

import com.critter.consts.Messages;
import com.critter.domain.entities.Customer;
import com.critter.domain.entities.Pet;
import com.critter.dto.CustomerDTO;
import com.critter.dto.PetDTO;
import com.critter.exceptions.customer.CustomerNotFoundException;
import com.critter.exceptions.pet.PetNotFoundException;
import com.critter.repository.CustomerRepository;
import com.critter.repository.PetRepository;
import com.critter.services.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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
                orElseThrow(() -> new CustomerNotFoundException(String.format(Messages.CUSTOMER_NOT_FOUND, petDTO.getOwnerId())));
        pet.setOwner(customer);
        pet = petRepository.save(pet);

        Customer petOwner = pet.getOwner();

        if (petOwner != null) {
            petOwner.getPetList().add(pet);
            customerRepository.save(petOwner);
        }

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
                orElseThrow(() -> new PetNotFoundException(String.format(Messages.PET_NOT_FOUND, petId))).
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
                orElseThrow(() -> new PetNotFoundException(String.format(Messages.PET_NOT_FOUND, petId)));
        return pet.getOwner().toDto();
    }
}
