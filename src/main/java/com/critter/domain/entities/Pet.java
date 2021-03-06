package com.critter.domain.entities;

import com.critter.domain.enums.PetType;
import com.critter.dto.PetDTO;
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
@ToString(exclude = {"owner", "scheduleList"})
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Customer.class)
    private Customer owner;

    private LocalDate birthDate;

    private String notes;

    @Enumerated(EnumType.STRING)
    private PetType type;

    @ManyToMany(mappedBy = "pets")
    private List<Schedule> scheduleList = new ArrayList<>();

    public List<Schedule> addSchedule(Schedule schedule) {
        scheduleList.add(schedule);
        return this.scheduleList;
    }

    public PetDTO toDto() {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(this, petDTO);
        petDTO.setOwnerId(this.owner.getId());
        return petDTO;
    }
}
