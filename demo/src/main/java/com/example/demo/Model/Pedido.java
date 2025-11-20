package com.example.demo.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "Pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedidos")
    private int idPedido;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private Usuario cliente;

    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaPedido;

    @Column(name = "estado", length = 15)
    private String estado;

    @Column(name = "total")
    private float total;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pago pago;

    public Pedido() {
    }

    public Pedido(int idPedido, Usuario cliente, LocalDateTime fechaPedido, String estado) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    // Método auxiliar para compatibilidad con LocalDate (usado en algunos
    // controladores)
    public void setFecha(LocalDate fecha) {
        this.fechaPedido = fecha.atStartOfDay();
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}