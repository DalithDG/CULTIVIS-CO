package com.example.demo.Model;

public class Ciudad {
    private Integer idCiudad;
    private String nombre;
    private String direccion;
    private Integer idDepartamento;

    public Ciudad() {
    }

    public Ciudad(Integer idCiudad, String nombre, String direccion, Integer idDepartamento) {
        this.idCiudad = idCiudad;
        this.nombre = nombre;
        this.direccion = direccion;
        this.idDepartamento = idDepartamento;
    }

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
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

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }
}