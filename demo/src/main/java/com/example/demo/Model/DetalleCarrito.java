package com.example.demo.Model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "Detalle_carrito")
@IdClass(DetalleCarrito.DetalleCarritoId.class)
public class DetalleCarrito {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_carrito", nullable = false)
    private Carrito carrito;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private Float precioUnitario;

    public DetalleCarrito() {}

    public DetalleCarrito(Carrito carrito, Producto producto, int cantidad, Float precioUnitario) {
        this.carrito = carrito;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
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

    public Float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getPrecioTotal() {
        if (producto == null || precioUnitario == null) {
            return 0.0;
        }
        return precioUnitario * cantidad;
    }

    // Clase interna para clave compuesta
    public static class DetalleCarritoId implements Serializable {
        private int carrito;
        private int producto;

        public DetalleCarritoId() {}

        public DetalleCarritoId(int carrito, int producto) {
            this.carrito = carrito;
            this.producto = producto;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DetalleCarritoId that = (DetalleCarritoId) o;
            return carrito == that.carrito && producto == that.producto;
        }

        @Override
        public int hashCode() {
            return Objects.hash(carrito, producto);
        }
    }
}