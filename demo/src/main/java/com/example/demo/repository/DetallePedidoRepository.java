package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.DetallePedido;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

       // Buscar detalles por pedido
       List<DetallePedido> findByPedidoIdPedido(int pedidoId);

       // Buscar detalles de productos de un vendedor específico
       @Query("SELECT d FROM DetallePedido d " +
                     "WHERE d.producto.usuario.id = :vendedorId")
       List<DetallePedido> findDetallesByVendedorId(@Param("vendedorId") int vendedorId);

       // Buscar detalles de un pedido específico que pertenecen a un vendedor
       @Query("SELECT d FROM DetallePedido d " +
                     "WHERE d.pedido.idPedido = :pedidoId AND d.producto.usuario.id = :vendedorId")
       List<DetallePedido> findDetallesByPedidoIdAndVendedorId(@Param("pedidoId") int pedidoId,
                     @Param("vendedorId") int vendedorId);

       // Verificar si existe un detalle con un pedido y producto específico
       boolean existsByPedidoAndProducto(com.example.demo.Model.Pedido pedido,
                     com.example.demo.Model.Producto producto);
}
