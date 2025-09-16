package com.example.crud.dto;

import com.example.crud.entity.DepartmentTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    public String empName;
    public Double empSalary;
    public DepartmentTest departmentTest;


}
