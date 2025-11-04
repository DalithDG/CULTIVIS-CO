package com.example.demo.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    
    private int idCarrito;
    private Usuario cliente;
    private LocalDateTime fechaPedido;
    private List<DetallePedido> detalles = new ArrayList<>();
    private Pago pago;
    private String estado;

    public Pedido() {
    }

    public Pedido(int idCarrito, Usuario cliente, LocalDateTime fechaPedido, List<DetallePedido> detalles, Pago pago, String estado) {
        this.idCarrito = idCarrito;
        this.cliente = cliente;
        this.fechaPedido = fechaPedido;
        this.detalles = detalles;
        this.pago = pago;
        this.estado = estado;
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

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
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

    
}