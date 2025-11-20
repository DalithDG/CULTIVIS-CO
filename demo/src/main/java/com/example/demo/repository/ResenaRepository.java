package com.example.demo.repository;

import com.example.demo.Model.Resena;
import com.example.demo.Model.Producto;
import com.example.demo.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Integer> {

    // Buscar reseñas por producto
    List<Resena> findByProducto(Producto producto);

    // Buscar reseñas por producto ID
    List<Resena> findByProductoId(int productoId);

    // Buscar reseñas por pedido
    List<Resena> findByPedido(Pedido pedido);

    // Buscar reseña específica por pedido y producto
    Optional<Resena> findByPedidoAndProducto(Pedido pedido, Producto producto);

    // Verificar si existe reseña para un pedido y producto
    boolean existsByPedidoAndProducto(Pedido pedido, Producto producto);

    // Calcular promedio de calificaciones por producto
    @Query("SELECT AVG(CAST(r.calificacion AS double)) FROM Resena r WHERE r.producto.id = :productoId")
    Double calcularPromedioCalificacion(int productoId);

    // Contar reseñas por producto
    long countByProductoId(int productoId);

    // Obtener últimas reseñas (ordenadas por fecha)
    List<Resena> findTop10ByOrderByFechaDesc();
}
