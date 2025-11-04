package com.example.demo.repository;

import java.util.List;

import com.example.demo.Model.Producto;

public interface IProductoRepository {

    List<Producto> EncontrarAllProductos();
    Producto EncontrarId(int id);
    void saveProducto(Producto producto);
    void updateProducto(Producto producto);
    void deleteProducto(int id);
    List<Producto> getProductosByCategoriaId(int categoriaId);
    List<Producto> getProductosByUserId(int userId);

    
}
