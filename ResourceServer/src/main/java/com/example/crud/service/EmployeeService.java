package com.example.crud.service;

import com.example.crud.dto.EmployeeDTO;
import com.example.crud.entity.EmployeeTest;

import java.util.Optional;

public interface EmployeeService {

    EmployeeTest createEmp(EmployeeDTO employeeDTO,Long deptId);

    EmployeeDTO getEmpById(Long id);

    Optional<String> getEmpById2(Long id);
}
