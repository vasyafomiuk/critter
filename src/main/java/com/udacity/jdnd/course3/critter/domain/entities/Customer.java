package com.udacity.jdnd.course3.critter.domain.entities;


import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DiscriminatorValue("customer")
public class Customer extends User {
    private String phoneNumber;

    private String notes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private List<Pet> pets = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "customers_schedule",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id")
    )
    private List<Schedule> scheduleList = new ArrayList<>();

    public CustomerDTO toDto() {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(this, customerDTO);
        customerDTO.setPetIds(this.pets.stream().map(Pet::getId).collect(Collectors.toList()));
        return customerDTO;
    }

}
