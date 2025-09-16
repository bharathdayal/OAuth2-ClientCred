package com.example.crud.controller;

import com.example.crud.dto.EmployeeDTO;
import com.example.crud.entity.EmployeeTest;
import com.example.crud.service.DepartmentService;
import com.example.crud.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/v1/employee")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service){
        this.service=service;
    }

    @PostMapping()
    public ResponseEntity<EmployeeTest> createEmp(
            @RequestBody EmployeeDTO employeeDTO,
            @RequestParam Long deptId
            ) {
        EmployeeTest employeeTest = service.createEmp(employeeDTO,deptId);
        return new ResponseEntity<>(employeeTest, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmpById(@PathVariable Long id) {
        EmployeeDTO employeeTest = service.getEmpById(id);
        return new ResponseEntity<>(employeeTest,HttpStatus.OK);

    }

    @GetMapping("/emp/{id}")
    public ResponseEntity<Optional<String>> getEmpById2(@PathVariable Long id) {
        Optional<String> employeeTest = service.getEmpById2(id);
        return new ResponseEntity<>(employeeTest,HttpStatus.OK);

    }
}
