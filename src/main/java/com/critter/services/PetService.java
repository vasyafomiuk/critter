package com.critter.services;

import com.critter.dto.CustomerDTO;
import com.critter.dto.PetDTO;

import java.util.List;

public interface PetService {
    PetDTO savePet(PetDTO petDTO);
    List<PetDTO> findAllPets();
    PetDTO findPetById(Long petId);
    List<PetDTO> getPetsByOwner(Long ownerId);
    CustomerDTO getOwnerByPet(Long petId);
}
