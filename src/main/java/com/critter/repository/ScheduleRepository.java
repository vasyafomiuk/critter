package com.critter.repository;

import com.critter.domain.entities.Pet;
import com.critter.domain.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByPetsContains(Pet pet);
}
