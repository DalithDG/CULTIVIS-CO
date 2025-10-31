package com.example.demo.model;

public class DetalleCarrito {
    private Integer idDetalle;
    private Integer idCarrito;
    private Integer idProducto;
    private Integer cantidad;
    private Float precioUnitario;

    public DetalleCarrito() {
    }

    public DetalleCarrito(Integer idDetalle, Integer idCarrito, Integer idProducto, 
                          Integer cantidad, Float precioUnitario) {
        this.idDetalle = idDetalle;
        this.idCarrito = idCarrito;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(Integer idCarrito) {
        this.idCarrito = idCarrito;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}