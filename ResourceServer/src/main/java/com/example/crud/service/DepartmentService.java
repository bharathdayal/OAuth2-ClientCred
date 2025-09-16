package com.example.crud.service;

import com.example.crud.dto.DepartmentDTO;
import com.example.crud.entity.DepartmentTest;

import java.util.List;

public interface DepartmentService {

    DepartmentTest createDept(DepartmentDTO departmentDTO);

    List<DepartmentTest> findAllDept();
}
