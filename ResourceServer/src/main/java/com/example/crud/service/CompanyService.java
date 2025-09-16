package com.example.crud.service;

import com.example.crud.entity.Company;
import com.example.crud.repository.CompanyRepository;

import java.util.List;

public interface CompanyService {

    List<Company> findAll();
}
