package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Pago;
import com.example.demo.Model.Pedido;

import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    
    Optional<Pago> findByPedido(Pedido pedido);
    
    Optional<Pago> findByPedidoIdPedido(int pedidoId);
}

