package com.example.demo.repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import com.example.demo.Model.Producto;

public class ProductoRepository implements IProductoRepository {

    private List<Producto> productos = new ArrayList<>();
    private int NextId = 1;

    @Override
    public List<Producto> EncontrarAllProductos() {
        return productos;
    }

    @Override
    public Producto EncontrarId(int id) {
        return productos.stream()
                .filter(producto -> producto.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void saveProducto(Producto producto) {
        producto.setId(NextId++);
        productos.add(producto);
    }

    @Override
    public void updateProducto(Producto producto) {
        if (producto == null || producto.getId() <= 0) {
            throw new IllegalArgumentException("Producto o ID inválido");
        }

        Producto existente = EncontrarId(producto.getId());
        if (existente == null) {
            throw new IllegalArgumentException("No se encontró el producto con ID: " + producto.getId());
        }

        productos.removeIf(p -> p.getId() == producto.getId());
        productos.add(producto);
    }

    @Override
    public void deleteProducto(int id) {
        productos.removeIf(p -> p.getId() == id);
    }

    @Override
    public List<Producto> getProductosByCategoriaId(int categoriaId) {
        return productos.stream()
                .filter(p -> p.getCategoria() == categoriaId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> getProductosByUserId(int userId) {
        return productos.stream()
                .filter(p -> p.getId() == userId)
                .collect(Collectors.toList());
    }

}
