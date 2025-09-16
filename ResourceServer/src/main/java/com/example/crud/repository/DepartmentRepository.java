package com.example.crud.repository;

import com.example.crud.entity.DepartmentTest;
import lombok.Lombok;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<DepartmentTest, Long> {

    //@Query("SELECT d FROM DepartmentTest d JOIN FETCH d.employeeTestList")
    @EntityGraph(attributePaths = {"employeeTestList"})
    @Query("SELECT d FROM DepartmentTest d")
    List<DepartmentTest> findAllByEmp();
}
