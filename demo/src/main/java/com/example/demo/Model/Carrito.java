package com.example.demo.Model;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "carrito_compras")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private int idCarrito;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCarrito> detalles;

    public Carrito() {
    }

    public Carrito(int idCarrito, Usuario cliente, List<DetalleCarrito> detalles) {
        this.idCarrito = idCarrito;
        this.cliente = cliente;
        this.detalles = detalles;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public List<DetalleCarrito> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCarrito> detalles) {
        this.detalles = detalles;
    }
}