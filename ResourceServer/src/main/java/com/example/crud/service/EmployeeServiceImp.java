package com.example.crud.service;

import com.example.crud.dto.EmployeeDTO;
import com.example.crud.entity.DepartmentTest;
import com.example.crud.entity.EmployeeTest;
import com.example.crud.repository.EmployeeRepository;
import com.example.crud.repository.EmployeeRepositoryJDBC;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@EnableTransactionManagement
public class EmployeeServiceImp implements EmployeeService{


    private final EmployeeRepository repository;

    private final EmployeeRepositoryJDBC repositoryJDBC;

    private EntityManager entityManager;



    public EmployeeServiceImp(EmployeeRepository repository,EntityManager entityManager,EmployeeRepositoryJDBC repositoryJDBC) throws SQLException {
        this.repository=repository;
        this.repositoryJDBC=repositoryJDBC;
        this.entityManager=entityManager;
    }


    @Override
    @Transactional()
    public EmployeeTest createEmp(EmployeeDTO employeeDTO,Long deptId) {
        DepartmentTest department =entityManager.find(DepartmentTest.class,deptId);
        EmployeeTest emp= new EmployeeTest();
        emp.setEmpName(employeeDTO.getEmpName());
        emp.setEmpSalary(employeeDTO.getEmpSalary());
        emp.setDepartmentTest(department);
        return repository.save(emp);
    }

    @Transactional
    public void addEmpDept(EmployeeDTO employeeDTO,Long deptId) {
        DepartmentTest department =entityManager.find(DepartmentTest.class,deptId);
        System.out.println("DEPARTMENT===> "+department.getDeptName());
        EmployeeTest emp= new EmployeeTest();
        emp.setEmpName(employeeDTO.getEmpName());

        emp.setDepartmentTest(department);
        entityManager.persist(emp);
    }

    public EmployeeDTO getEmpById(Long id) {
        return repositoryJDBC.findByID(id).orElseThrow(()->new NoSuchElementException("Not found"));
    }

    public Optional<String> getEmpById2(Long id) {
        return repository.findEmpById(id);

    }


    public void transact() {
        System.out.println("Transaction");
    }
}
