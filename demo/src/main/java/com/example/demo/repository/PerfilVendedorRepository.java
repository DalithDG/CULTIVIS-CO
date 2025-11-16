package com.example.demo.repository;

import com.example.demo.Model.PerfilVendedor;
import com.example.demo.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PerfilVendedorRepository extends JpaRepository<PerfilVendedor, Integer> {
    
    Optional<PerfilVendedor> findByUsuario(Usuario usuario);
    
    Optional<PerfilVendedor> findByUsuarioId(int usuarioId);
    
    boolean existsByUsuarioId(int usuarioId);
}