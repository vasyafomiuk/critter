package com.critter.services;

import com.critter.dto.EmployeeDTO;
import com.critter.dto.EmployeeRequestDTO;

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
