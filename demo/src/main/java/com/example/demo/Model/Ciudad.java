package com.example.demo.Model;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "ciudad")
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ciudad")
    private int idCiudad;

    @Column(name = "nombre", length = 20, nullable = false)
    private String nombre;

    @Column(name = "direccion", length = 50)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "id_departamento", nullable = false)
    private Departamento departamento;

    @OneToMany(mappedBy = "ciudad", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

    public Ciudad() {
    }

    public Ciudad(int idCiudad, String nombre, String direccion, Departamento departamento) {
        this.idCiudad = idCiudad;
        this.nombre = nombre;
        this.direccion = direccion;
        this.departamento = departamento;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}