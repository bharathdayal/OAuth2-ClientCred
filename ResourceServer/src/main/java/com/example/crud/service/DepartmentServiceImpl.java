package com.example.crud.service;

import com.example.crud.dto.DepartmentDTO;
import com.example.crud.entity.DepartmentTest;
import com.example.crud.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentRepository repository;

    public DepartmentServiceImpl(DepartmentRepository repository) {
        this.repository=repository;
    }

    @Override
    public DepartmentTest createDept(DepartmentDTO departmentDTO) {
        DepartmentTest dept = new DepartmentTest();
        dept.setDeptName(departmentDTO.getDeptName());
        dept.setEmployeeTestList(departmentDTO.getEmployeeTestList());
        return repository.save(dept);
    }

    @Override
    public List<DepartmentTest> findAllDept(){
        return repository.findAllByEmp();
    }
}
