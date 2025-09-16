package com.example.crud.controller;

import com.example.crud.dto.DepartmentDTO;
import com.example.crud.entity.DepartmentTest;
import com.example.crud.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/depart")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service=service;
    }

    @PostMapping
    public ResponseEntity<DepartmentTest> createDept(
            @RequestBody DepartmentDTO departmentDTO
    ) {
        DepartmentTest dept = service.createDept(departmentDTO);
        return new ResponseEntity<>(dept, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentTest>> findAllDept() {
        List<DepartmentTest> departmentTests = service.findAllDept();
        return new ResponseEntity<>(departmentTests,HttpStatus.OK);
    }
}
