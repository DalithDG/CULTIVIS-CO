package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.UnidadMedida;

import java.util.Optional;

@Repository
public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Integer> {
    
    Optional<UnidadMedida> findByNombre(String nombre);
    
    Optional<UnidadMedida> findByAbreviatura(String abreviatura);
    
    boolean existsByNombre(String nombre);
}

