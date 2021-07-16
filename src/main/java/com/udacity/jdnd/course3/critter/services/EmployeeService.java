package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeeService {
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO findEmployeeById(Long employeeId);
    void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId);
    List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO);
    List<EmployeeDTO> getAllEmployees();

}
