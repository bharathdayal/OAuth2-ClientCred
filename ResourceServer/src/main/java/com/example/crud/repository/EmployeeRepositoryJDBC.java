package com.example.crud.repository;

import com.example.crud.dto.EmployeeDTO;
import com.example.crud.entity.EmployeeTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepositoryJDBC {

    private final JdbcTemplate jdbc;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EmployeeRepositoryJDBC(JdbcTemplate jdbc,NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbc=jdbc;
        this.namedParameterJdbcTemplate=namedParameterJdbcTemplate;
    }

    public Optional<EmployeeDTO> findByID(Long id) {


        String sql = "SELECT emp_name FROM demo_spring.employee_test WHERE id=?";
      //  List<EmployeeDTO> emp =jdbc.query(sql,
               // new Object[]{id},
              //  (rs, rowNum) ->
             //   new EmployeeDTO(rs.getString("emp_name"),id));
       // return emp.stream().findFirst();
        return null;
    }
}
