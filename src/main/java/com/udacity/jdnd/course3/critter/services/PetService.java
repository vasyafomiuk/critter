package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.PetDTO;

import java.util.List;

public interface PetService {
    PetDTO savePet(PetDTO petDTO);
    List<PetDTO> findAllPets();
    PetDTO findPetById(Long petId);
    List<PetDTO> getPetsByOwner(Long ownerId);
    CustomerDTO getOwnerByPet(Long petId);
}
