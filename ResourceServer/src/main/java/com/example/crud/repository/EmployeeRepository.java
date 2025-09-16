package com.example.crud.repository;

import com.example.crud.dto.EmployeeDTO;
import com.example.crud.entity.EmployeeTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeTest,Long> {

    @Query("SELECT e.empName FROM EmployeeTest e WHERE e.id=:id")
    Optional<String> findEmpById(@Param("id") Long id);

}
