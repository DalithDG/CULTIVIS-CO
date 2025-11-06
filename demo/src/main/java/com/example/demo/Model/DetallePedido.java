package com.example.demo.Model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "Detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalles")
    private int idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_pedidos", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_productos", nullable = false)
    private Producto producto;

    @Column(name = "Cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private Float precioUnitario;

    @Column(name = "Fecha_entrega")
    private LocalDate fechaEntrega;

    public DetallePedido() {
    }

    public DetallePedido(int idDetalle, Pedido pedido, Producto producto, int cantidad, Float precioUnitario, LocalDate fechaEntrega) {
        this.idDetalle = idDetalle;
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.fechaEntrega = fechaEntrega;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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

    public double getSubtotal() {
        if (precioUnitario == null) {
            return 0.0;
        }
        return precioUnitario * cantidad;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}