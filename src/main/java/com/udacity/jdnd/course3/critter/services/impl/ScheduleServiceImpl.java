package com.udacity.jdnd.course3.critter.services.impl;

import com.udacity.jdnd.course3.critter.domain.entities.Customer;
import com.udacity.jdnd.course3.critter.domain.entities.Employee;
import com.udacity.jdnd.course3.critter.domain.entities.Pet;
import com.udacity.jdnd.course3.critter.domain.entities.Schedule;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository, PetRepository petRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
    }

    @Override
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        if (Objects.nonNull(scheduleDTO.getEmployeeIds()) && !scheduleDTO.getEmployeeIds().isEmpty()) {
            schedule.setEmployees(employeeRepository.findAllById(scheduleDTO.getEmployeeIds()));
        }
        if (Objects.nonNull(scheduleDTO.getPetIds()) && !scheduleDTO.getPetIds().isEmpty()) {
            schedule.setPets(petRepository.findAllById(scheduleDTO.getPetIds()));
        }
        return scheduleRepository.save(schedule).toDto();
    }

    @Override
    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) {
        return scheduleRepository.findAll().stream().
                filter(p -> p.getEmployees().
                        stream().
                        map(Employee::getId).
                        collect(Collectors.toList()).contains(employeeId)).
                map(Schedule::toDto).
                collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> getScheduleForCustomer(Long customerId) {
        return scheduleRepository.findAll().stream().
                filter(p -> p.getCustomers().
                        stream().
                        map(Customer::getId).
                        collect(Collectors.toList()).contains(customerId)).
                map(Schedule::toDto).
                collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository.
                findAll().
                stream().
                map(Schedule::toDto).
                collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> getScheduleForPet(Long petId) {
        return scheduleRepository.findAll().
                stream().
                filter(p -> p.getPets().
                        stream().
                        map(Pet::getId).
                        collect(Collectors.toList()).contains(petId)).
                map(Schedule::toDto).
                collect(Collectors.toList());
    }

}