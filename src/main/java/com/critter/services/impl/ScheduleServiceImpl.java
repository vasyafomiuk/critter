package com.critter.services.impl;

import com.critter.consts.Messages;
import com.critter.domain.entities.Employee;
import com.critter.domain.entities.Pet;
import com.critter.domain.entities.Schedule;
import com.critter.dto.ScheduleDTO;
import com.critter.exceptions.customer.CustomerNotFoundException;
import com.critter.repository.ScheduleRepository;
import com.critter.services.ScheduleService;
import com.critter.domain.entities.Customer;
import com.critter.repository.CustomerRepository;
import com.critter.repository.EmployeeRepository;
import com.critter.repository.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository,
                               EmployeeRepository employeeRepository,
                               PetRepository petRepository,
                               CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        if (Objects.nonNull(scheduleDTO.getEmployeeIds())) {
            List<Employee> employees = employeeRepository.findAllById(scheduleDTO.getEmployeeIds());
            employees.forEach(e -> e.addSchedule(schedule));
            schedule.setEmployees(employees);
            employeeRepository.saveAll(employees);
        }
        if (Objects.nonNull(scheduleDTO.getPetIds())) {
            List<Pet> pets = petRepository.findAllById(scheduleDTO.getPetIds());
            pets.forEach(p -> p.addSchedule(schedule));
            schedule.setPets(pets);
            petRepository.saveAll(pets);
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
        Customer customer = customerRepository.
                findById(customerId).
                orElseThrow(() -> new CustomerNotFoundException(String.format(Messages.CUSTOMER_NOT_FOUND, customerId)));
        return customer.
                getPetList().
                stream().
                map(scheduleRepository::findByPetsContains).
                flatMap(Collection::stream).
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
