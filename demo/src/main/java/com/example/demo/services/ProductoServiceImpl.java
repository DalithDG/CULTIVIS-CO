package com.example.demo.services;

import java.util.List;

import com.example.demo.Model.Producto;
import com.example.demo.repository.ProductoRepository;

public class ProductoServiceImpl implements ProductoService {

    private ProductoRepository productoRepository = new ProductoRepository();


    @Override
    public Producto crearProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }

        if (producto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser negativo");
        }

        productoRepository.saveProducto(producto);
        return producto; 
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.EncontrarAllProductos();
    }

    @Override
    public Producto buscarPorId(int id) {
        return productoRepository.EncontrarId(id);
    }

    @Override
    public void eliminarProducto(int id) {
        productoRepository.deleteProducto(id);
    }

}
