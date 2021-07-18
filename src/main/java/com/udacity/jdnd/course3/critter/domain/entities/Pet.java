package com.udacity.jdnd.course3.critter.domain.entities;

import com.udacity.jdnd.course3.critter.domain.enums.PetType;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "pets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "owner")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customer owner;
//    @Column(name = "owner_id")
//    private Long ownerId;

    private LocalDate birthDate;

    private String notes;

    @Enumerated(EnumType.STRING)
    private PetType type;

    @ManyToMany(mappedBy = "pets")
    private List<Schedule> scheduleList = new ArrayList<>();

    public PetDTO toDto() {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(this, petDTO);
        petDTO.setOwnerId(this.owner.getId());
        return petDTO;
    }
}
