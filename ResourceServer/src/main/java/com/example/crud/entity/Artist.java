package com.example.crud.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstName",nullable = false)
    @NotEmpty(message = "No empty fields") @NotNull(message = "No empty fields")
    private String artistName;
    @Column(name = "bandName",nullable = false)
    @NotEmpty(message = "No empty fields") @NotNull(message = "No empty fields")
    private String artistBand;
    @JsonProperty()
    private String artistCountry;
}
