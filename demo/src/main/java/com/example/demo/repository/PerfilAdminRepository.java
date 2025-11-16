package com.example.demo.repository;

import com.example.demo.Model.PerfilAdmin;
import com.example.demo.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PerfilAdminRepository extends JpaRepository<PerfilAdmin, Integer> {
    
    Optional<PerfilAdmin> findByUsuario(Usuario usuario);
    
    Optional<PerfilAdmin> findByUsuarioId(int usuarioId);
    
    boolean existsByUsuarioId(int usuarioId);
}