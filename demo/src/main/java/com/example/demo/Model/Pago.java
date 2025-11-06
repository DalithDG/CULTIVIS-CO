package com.example.demo.Model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "Pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPago")
    private int idPago;

    @OneToOne
    @JoinColumn(name = "id_pedido", nullable = false, unique = true)
    private Pedido pedido;

    @Column(name = "Monto", nullable = false)
    private Float monto;

    @Column(name = "Fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "Metodo", length = 15)
    private String metodoPago;

    public Pago() {
    }

    public Pago(int idPago, Pedido pedido, Float monto, LocalDateTime fechaPago, String metodoPago) {
        this.idPago = idPago;
        this.pedido = pedido;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
