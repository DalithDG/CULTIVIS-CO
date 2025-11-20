package com.example.demo.services;

import com.example.demo.Model.Producto;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusquedaService {

    @Autowired
    private ProductoRepository productoRepository;

    // Buscar productos por query (nombre o descripción)
    public List<Producto> buscarProductos(String query) {
        if (query == null || query.trim().isEmpty()) {
            return productoRepository.findAll();
        }

        String queryLimpio = query.trim();
        return productoRepository.findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(
                queryLimpio, queryLimpio);
    }

    // Buscar productos por categoría
    public List<Producto> buscarPorCategoria(int categoriaId) {
        return productoRepository.findByCategoriaIdCategoria(categoriaId);
    }

    // Buscar productos por rango de precio
    public List<Producto> buscarPorRangoPrecio(Float precioMin, Float precioMax) {
        return productoRepository.findByPrecioBetween(precioMin, precioMax);
    }

    // Buscar productos con stock disponible
    public List<Producto> buscarConStock() {
        return productoRepository.findByStockGreaterThan(0);
    }

    // Ordenar productos por precio ascendente
    public List<Producto> ordenarPorPrecioAsc() {
        return productoRepository.findAllByOrderByPrecioAsc();
    }

    // Ordenar productos por precio descendente
    public List<Producto> ordenarPorPrecioDesc() {
        return productoRepository.findAllByOrderByPrecioDesc();
    }

    // Obtener productos más recientes
    public List<Producto> obtenerMasRecientes() {
        return productoRepository.findAllByOrderByIdDesc();
    }

    // Búsqueda avanzada con filtros
    public List<Producto> busquedaAvanzada(String query, Integer categoriaId, Float precioMin, Float precioMax) {
        List<Producto> resultados = buscarProductos(query);

        // Filtrar por categoría si se especifica
        if (categoriaId != null && categoriaId > 0) {
            resultados = resultados.stream()
                    .filter(p -> p.getCategoria() != null && p.getCategoria().getIdCategoria() == categoriaId)
                    .collect(Collectors.toList());
        }

        // Filtrar por rango de precio si se especifica
        if (precioMin != null && precioMax != null) {
            resultados = resultados.stream()
                    .filter(p -> p.getPrecio() >= precioMin && p.getPrecio() <= precioMax)
                    .collect(Collectors.toList());
        } else if (precioMin != null) {
            resultados = resultados.stream()
                    .filter(p -> p.getPrecio() >= precioMin)
                    .collect(Collectors.toList());
        } else if (precioMax != null) {
            resultados = resultados.stream()
                    .filter(p -> p.getPrecio() <= precioMax)
                    .collect(Collectors.toList());
        }

        // Filtrar solo productos con stock
        resultados = resultados.stream()
                .filter(p -> p.getStock() > 0)
                .collect(Collectors.toList());

        return resultados;
    }
}
