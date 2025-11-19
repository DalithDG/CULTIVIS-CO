package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Pedido;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    
    // Buscar pedidos por cliente
    List<Pedido> findByClienteId(int clienteId);
    
    // Buscar pedidos por estado
    List<Pedido> findByEstado(String estado);
    
    // Buscar pedidos de un cliente por estado
    List<Pedido> findByClienteIdAndEstado(int clienteId, String estado);
    
    // Buscar pedidos que contienen productos de un vendedor específico
    @Query("SELECT DISTINCT p FROM Pedido p " +
           "JOIN p.detalles d " +
           "WHERE d.producto.usuario.id = :vendedorId")
    List<Pedido> findPedidosByVendedorId(@Param("vendedorId") int vendedorId);
    
    // Buscar pedidos de un vendedor por estado
    @Query("SELECT DISTINCT p FROM Pedido p " +
           "JOIN p.detalles d " +
           "WHERE d.producto.usuario.id = :vendedorId AND p.estado = :estado")
    List<Pedido> findPedidosByVendedorIdAndEstado(@Param("vendedorId") int vendedorId, 
                                                   @Param("estado") String estado);
}

