package com.critter.domain.entities;

import com.critter.domain.enums.EmployeeSkill;
import com.critter.dto.ScheduleDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @ManyToMany
    @JoinTable(
            name = "employees_schedule",
            joinColumns = @JoinColumn(name = "scheduleId"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "pets_schedule",
            joinColumns = @JoinColumn(name = "scheduleId"),
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private List<Pet> pets = new ArrayList<>();

    private LocalDate date;

    @ElementCollection(targetClass = EmployeeSkill.class, fetch = FetchType.LAZY)
    private Set<EmployeeSkill> activities = new HashSet<>();

    public ScheduleDTO toDto() {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(this, scheduleDTO);
        scheduleDTO.setId(this.scheduleId);
        scheduleDTO.setEmployeeIds(employees.stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(pets.stream().map(Pet::getId).collect(Collectors.toList()));
        return scheduleDTO;
    }
}
