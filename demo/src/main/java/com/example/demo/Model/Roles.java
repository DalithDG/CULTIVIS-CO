package com.example.demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

@Entity
@Table(name = "Roles")

public class Roles {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Long id;

     @Column(nullable = false, unique = true)
    private String nombre;  // "COMPRADOR", "VENDEDOR", "ADMIN"

    public Roles() {}

    public Roles(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   
}
