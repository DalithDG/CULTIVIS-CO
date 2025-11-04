package com.example.demo.services;

import java.util.List;

import com.example.demo.Model.Producto;

public interface ProductoService {
    
    Producto crearProducto(Producto producto);

    List<Producto> listarProductos();

    Producto buscarPorId(int id);

    void eliminarProducto(int id);
}
