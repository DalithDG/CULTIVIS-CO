package com.example.demo.model;

import java.time.LocalDate;

public class Reseña {
    private Integer idResena;
    private Integer calificacion;
    private LocalDate fecha;
    private Integer idPedidos;

    public Reseña() {
    }

    public Reseña(Integer idResena, Integer calificacion, LocalDate fecha, Integer idPedidos) {
        this.idResena = idResena;
        this.calificacion = calificacion;
        this.fecha = fecha;
        this.idPedidos = idPedidos;
    }

    public Integer getIdResena() {
        return idResena;
    }

    public void setIdResena(Integer idResena) {
        this.idResena = idResena;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getIdPedidos() {
        return idPedidos;
    }

    public void setIdPedidos(Integer idPedidos) {
        this.idPedidos = idPedidos;
    }
}