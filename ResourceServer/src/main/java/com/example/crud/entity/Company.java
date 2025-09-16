package com.example.crud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyid;

    private String companyName;

    @OneToMany(mappedBy = "company",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Product> product;

}
