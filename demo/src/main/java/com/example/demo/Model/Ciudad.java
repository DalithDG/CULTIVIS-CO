package com.example.demo.Model;

public class Ciudad {
    private int idCiudad;
    private String nombre;
    private String direccion;
    private Departamento departamento;

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

}