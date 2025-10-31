package com.example.demo.model;

public class CarritoCompras {
    private Integer idCarrito;
    private Integer idUser;

    public CarritoCompras() {
    }

    public CarritoCompras(Integer idCarrito, Integer idUser) {
        this.idCarrito = idCarrito;
        this.idUser = idUser;
    }

    public Integer getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(Integer idCarrito) {
        this.idCarrito = idCarrito;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}