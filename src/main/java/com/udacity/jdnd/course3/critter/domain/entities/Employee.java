package com.udacity.jdnd.course3.critter.domain.entities;

import com.udacity.jdnd.course3.critter.domain.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Entity
@DiscriminatorValue("employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee extends User {
    @ElementCollection(targetClass = EmployeeSkill.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "employee_skills", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "skill")
    private Set<EmployeeSkill> skills;

    @ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "availability_days", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "day")
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(mappedBy = "employees")
    private List<Schedule> scheduleList = new ArrayList<>();

    public EmployeeDTO toDto() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(this, employeeDTO);
        return employeeDTO;
    }

    public boolean matchesSkills(Set<EmployeeSkill> employeeSkills) {
        if (employeeSkills.size() == this.getSkills().size()) {
            return new TreeSet<>(employeeSkills).equals(new TreeSet<>(this.getSkills()));
        }
        return employeeSkills.stream().anyMatch(employeeSkills::contains);
    }
}
