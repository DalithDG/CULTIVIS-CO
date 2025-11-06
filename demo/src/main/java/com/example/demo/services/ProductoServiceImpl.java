package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Producto;
import com.example.demo.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto crearProducto(Producto producto) {
        // Validaciones de negocio
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }

        if (producto.getPrecio() == null || producto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser negativo");
        }

        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto buscarPorId(int id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.orElse(null);
    }

    @Override
    public void eliminarProducto(int id) {
        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    @Override
    public Producto actualizarProducto(Producto producto) {
        if (producto.getId() <= 0) {
            throw new IllegalArgumentException("ID de producto inválido");
        }
        if (!productoRepository.existsById(producto.getId())) {
            throw new IllegalArgumentException("Producto no encontrado con ID: " + producto.getId());
        }
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> buscarPorCategoria(int categoriaId) {
        return productoRepository.findByCategoriaIdCategoria(categoriaId);
    }

    @Override
    public List<Producto> buscarPorUsuario(int userId) {
        return productoRepository.findByUsuarioId(userId);
    }
}