package com.example.demo.Model;

import java.time.LocalDateTime;

public class Pedido {
    private Integer idPedidos;
    private LocalDateTime fechaPedido;
    private Integer idUser;

    public Pedido() {
    }

    public Pedido(Integer idPedidos, LocalDateTime fechaPedido, Integer idUser) {
        this.idPedidos = idPedidos;
        this.fechaPedido = fechaPedido;
        this.idUser = idUser;
    }

    public Integer getIdPedidos() {
        return idPedidos;
    }

    public void setIdPedidos(Integer idPedidos) {
        this.idPedidos = idPedidos;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}