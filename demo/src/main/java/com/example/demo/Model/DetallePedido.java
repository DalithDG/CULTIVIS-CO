package com.example.demo.Model;

import java.time.LocalDate;

public class DetallePedido {
    private Integer idDetalles;
    private Integer idPedidos;
    private Integer idProductos;
    private LocalDate fechaEntrega;
    private Float precioUnitario;
    private Integer cantidad;
    private String estado;

    public DetallePedido() {
    }

    public DetallePedido(Integer idDetalles, Integer idPedidos, Integer idProductos, 
                         LocalDate fechaEntrega, Float precioUnitario, Integer cantidad, String estado) {
        this.idDetalles = idDetalles;
        this.idPedidos = idPedidos;
        this.idProductos = idProductos;
        this.fechaEntrega = fechaEntrega;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    public Integer getIdDetalles() {
        return idDetalles;
    }

    public void setIdDetalles(Integer idDetalles) {
        this.idDetalles = idDetalles;
    }

    public Integer getIdPedidos() {
        return idPedidos;
    }

    public void setIdPedidos(Integer idPedidos) {
        this.idPedidos = idPedidos;
    }

    public Integer getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(Integer idProductos) {
        this.idProductos = idProductos;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}