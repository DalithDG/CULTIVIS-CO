package com.example.demo.Model;

import java.time.LocalDateTime;

public class Pago {
    private int idPago;
    private Pedido pedido;
    private Double monto;
    private LocalDateTime fechaPago;
    private String metodoPago;
    private String estado;

    public Pago() {
    }

    public Pago(int idPago, Pedido pedido, Double monto, LocalDateTime fechaPago, String metodoPago, String estado) {
        this.idPago = idPago;
        this.pedido = pedido;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.estado = estado;
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

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    

}