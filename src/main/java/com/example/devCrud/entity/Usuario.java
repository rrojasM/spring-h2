package com.example.devCrud.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Setter
@Getter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String nombre;

    private LocalDate fechaNacimiento;

    private String correo;
    private Character genero;
    private String hobbies;

}
