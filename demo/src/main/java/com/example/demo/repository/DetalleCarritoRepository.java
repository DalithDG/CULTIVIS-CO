package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.DetalleCarrito;
import com.example.demo.Model.Carrito;
import com.example.demo.Model.Producto;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, DetalleCarrito.DetalleCarritoId> {

    List<DetalleCarrito> findByCarrito(Carrito carrito);

    Optional<DetalleCarrito> findByCarritoAndProducto(Carrito carrito, Producto producto);

    @Modifying
    @Transactional
    void deleteByCarrito(Carrito carrito);

    @Query("SELECT d FROM DetalleCarrito d WHERE d.carrito.idCarrito = :carritoId")
    List<DetalleCarrito> findByCarritoId(@Param("carritoId") int carritoId);

}
