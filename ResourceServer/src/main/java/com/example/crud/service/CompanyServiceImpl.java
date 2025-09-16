package com.example.crud.service;

import com.example.crud.entity.Company;
import com.example.crud.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {


    private final CompanyRepository repository;

    public CompanyServiceImpl(CompanyRepository repository) {
        this.repository = repository;
    }

    public List<Company> findAll() {
        return repository.findAll();
    }
}
