package com.example.demo.services;

import java.util.List;
import com.example.demo.Model.Producto;

public interface ProductoService {
    
    Producto crearProducto(Producto producto);
    List<Producto> listarProductos();
    Producto buscarPorId(int id);
    void eliminarProducto(int id);
    Producto actualizarProducto(Producto producto);
    List<Producto> buscarPorCategoria(int categoriaId);
    List<Producto> buscarPorUsuario(int userId);
    List<Producto> buscarPorNombre(String nombre);
    List<Producto> buscarConStock();
    List<Producto> buscarPorRangoPrecio(Float precioMin, Float precioMax);
}