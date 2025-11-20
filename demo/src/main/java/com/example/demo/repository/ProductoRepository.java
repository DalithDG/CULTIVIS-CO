package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Producto;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Buscar productos por categoría
    List<Producto> findByCategoriaIdCategoria(int idCategoria);

    // Buscar productos por usuario
    List<Producto> findByUsuarioId(int userId);

    // Buscar productos por nombre (búsqueda parcial)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Buscar productos con stock disponible
    List<Producto> findByStockGreaterThan(int stock);

    // Buscar productos por rango de precio
    List<Producto> findByPrecioBetween(Float precioMin, Float precioMax);

    // Búsqueda avanzada por nombre o descripción
    List<Producto> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String nombre, String descripcion);

    // Buscar productos ordenados por precio
    List<Producto> findAllByOrderByPrecioAsc();

    List<Producto> findAllByOrderByPrecioDesc();

    // Buscar productos ordenados por fecha (más recientes primero)
    List<Producto> findAllByOrderByIdDesc();
}