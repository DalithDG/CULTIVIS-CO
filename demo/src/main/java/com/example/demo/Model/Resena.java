package com.example.demo.Model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "Reseña")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reseña")
    private int idResena;

    @ManyToOne
    @JoinColumn(name = "id_pedidos", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_productos", nullable = false)
    private Producto producto;

    @Column(name = "Calificacion", length = 1, nullable = false)
    private String calificacion;

    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    public Resena() {
    }

    public Resena(int idResena, Pedido pedido, Producto producto, String calificacion, LocalDate fecha) {
        this.idResena = idResena;
        this.pedido = pedido;
        this.producto = producto;
        this.calificacion = calificacion;
        this.fecha = fecha;
    }

    public int getIdResena() {
        return idResena;
    }

    public void setIdResena(int idResena) {
        this.idResena = idResena;
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

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}