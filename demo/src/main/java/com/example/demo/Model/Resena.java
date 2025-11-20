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

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "Calificacion", nullable = false)
    private int calificacion;

    @Column(name = "Comentario", length = 500)
    private String comentario;

    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    public Resena() {
    }

    public Resena(Pedido pedido, Producto producto, Usuario usuario, int calificacion, String comentario,
            LocalDate fecha) {
        this.pedido = pedido;
        this.producto = producto;
        this.usuario = usuario;
        this.calificacion = calificacion;
        this.comentario = comentario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}