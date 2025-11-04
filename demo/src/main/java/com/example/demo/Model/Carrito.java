package com.example.demo.Model;

import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private int idCarrito;
    private Usuario Cliente;
    private List<DetalleCarrito> detalles = new ArrayList<>();

    public Carrito() {
    }

    public Carrito(int idCarrito, Usuario cliente, List<DetalleCarrito> detalles) {
        this.idCarrito = idCarrito;
        Cliente = cliente;
        this.detalles = detalles;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public Usuario getCliente() {
        return Cliente;
    }

    public void setCliente(Usuario cliente) {
        Cliente = cliente;
    }

    public List<DetalleCarrito> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCarrito> detalles) {
        this.detalles = detalles;
    }

    

    


}
