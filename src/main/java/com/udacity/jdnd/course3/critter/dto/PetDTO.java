package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.domain.enums.PetType;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */
@Data
public class PetDTO {
    private Long id;
    private PetType type;
    private String name;
    private Long ownerId;
    private LocalDate birthDate;
    private String notes;
}
