package com.example.demo.Model;

public class CarritoCompras {
    private int idCarrito;
    private int idUser;

    public CarritoCompras() {
    }
    public CarritoCompras(int idCarrito, int idUser) {
        this.idCarrito = idCarrito;
        this.idUser = idUser;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    
    
}