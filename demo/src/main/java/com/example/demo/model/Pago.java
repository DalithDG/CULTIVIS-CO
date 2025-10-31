package com.example.demo.model;

import java.time.LocalDateTime;

public class Pago {
    private Integer idPago;
    private String metodo;
    private Float monto;
    private LocalDateTime fechaPago;
    private Integer idPedido;

    public Pago() {
    }

    public Pago(Integer idPago, String metodo, Float monto, LocalDateTime fechaPago, Integer idPedido) {
        this.idPago = idPago;
        this.metodo = metodo;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.idPedido = idPedido;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
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

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }
}