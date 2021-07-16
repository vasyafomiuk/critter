package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.domain.enums.EmployeeSkill;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a request to find available employees by skills. Does not map
 * to the database directly.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeRequestDTO {
    private Set<EmployeeSkill> skills;
    private LocalDate date;

}
