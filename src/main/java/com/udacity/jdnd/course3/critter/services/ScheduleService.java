package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;

import java.util.List;

public interface ScheduleService {
    ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);
    List<ScheduleDTO> getScheduleForEmployee(Long employeeId);
    List<ScheduleDTO> getScheduleForCustomer(Long customerId);
    List<ScheduleDTO> getAllSchedules();
    List<ScheduleDTO> getScheduleForPet(Long petId);

}
