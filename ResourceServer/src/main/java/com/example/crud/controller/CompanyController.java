package com.example.crud.controller;

import com.example.crud.entity.Company;
import com.example.crud.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {


    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Company>> findAll() {
        List<Company> company = service.findAll();
        return new ResponseEntity<>(company, HttpStatus.OK);
    }
}
