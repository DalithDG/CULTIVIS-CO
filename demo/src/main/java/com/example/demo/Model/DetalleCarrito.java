package com.example.demo.Model;

public class DetalleCarrito {

    private int idCarrito;
    private Producto producto;
    private int cantidad;

    public DetalleCarrito() {}

    public DetalleCarrito(int idCarrito, Producto producto, int cantidad) {
        this.idCarrito = idCarrito;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
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

    
    public double getPrecioTotal() {
        if (producto == null) {
            return 0.0;
        }
        return producto.getPrecio() * cantidad;
    }
}
