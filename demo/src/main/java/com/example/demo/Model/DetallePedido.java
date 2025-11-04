package com.example.demo.Model;

import java.time.LocalDate;

public class DetallePedido {
    private int idDetalle;
    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private LocalDate fechaEntrega;

    public DetallePedido() {
    }

    public DetallePedido(int idDetalle, Producto producto, int cantidad, double precioUnitario, double subtotal, LocalDate fechaEntrega) {
        this.idDetalle = idDetalle;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.fechaEntrega = fechaEntrega;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return precioUnitario * cantidad;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal; // Se mantiene el campo para compatibilidad, pero el valor real se calcula en getSubtotal()
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}