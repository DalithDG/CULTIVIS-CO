package com.example.demo.model;

public class UnidadMedida {
    private Integer idUnidad;
    private String nombre;
    private String abreviatura;

    public UnidadMedida() {
    }

    public UnidadMedida(Integer idUnidad, String nombre, String abreviatura) {
        this.idUnidad = idUnidad;
        this.nombre = nombre;
        this.abreviatura = abreviatura;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
}