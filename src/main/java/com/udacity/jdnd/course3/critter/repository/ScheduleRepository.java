package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.domain.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
//    List<Schedule> findAllByEmployeeId(Long employeeId);
//
//    List<Schedule> findAllByCustomerId(Long customerId);

//    List<Schedule> findAllByPetId(Long petId);


}
