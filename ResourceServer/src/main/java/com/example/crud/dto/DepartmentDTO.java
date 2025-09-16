package com.example.crud.dto;

import com.example.crud.entity.EmployeeTest;
import lombok.Data;

import java.util.List;

@Data
public class DepartmentDTO {

    public String deptName;
    public List<EmployeeTest> employeeTestList;
}
