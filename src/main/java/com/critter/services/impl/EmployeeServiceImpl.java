package com.critter.services.impl;

import com.critter.consts.Messages;
import com.critter.domain.entities.Employee;
import com.critter.dto.EmployeeDTO;
import com.critter.exceptions.employee.EmployeeNotFoundException;
import com.critter.dto.EmployeeRequestDTO;
import com.critter.repository.EmployeeRepository;
import com.critter.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employeeRepository.save(employee).toDto();
    }

    @Override
    public EmployeeDTO findEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).
                orElseThrow(() -> new EmployeeNotFoundException(String.format(Messages.EMPLOYEE_NOT_FOUND, employeeId)));
        return employee.toDto();
    }

    @Override
    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Employee employee = employeeRepository.
                findById(employeeId).
                orElseThrow(() -> new EmployeeNotFoundException(String.format(Messages.EMPLOYEE_NOT_FOUND, employeeId)));
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        return employeeRepository.
                findAll().
                stream().
                filter(p ->
                        p.matchesSkills(employeeRequestDTO.getSkills()) &&
                                p.getDaysAvailable().contains(employeeRequestDTO.getDate().getDayOfWeek())).
                map(Employee::toDto).
                collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(Employee::toDto).collect(Collectors.toList());
    }
}
