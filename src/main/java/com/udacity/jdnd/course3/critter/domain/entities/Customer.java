package com.udacity.jdnd.course3.critter.domain.entities;


import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@DiscriminatorValue("customer")
public class Customer extends User {
    private String phoneNumber;
    private String notes;

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            targetEntity = Pet.class)
    private List<Pet> petList = new ArrayList<>();


    public CustomerDTO toDto() {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(this, customerDTO);
        customerDTO.setPetIds(this.petList.stream().map(Pet::getId).collect(Collectors.toList()));
        return customerDTO;
    }

}
