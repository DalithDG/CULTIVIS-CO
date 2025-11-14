package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

            
    // Buscar usuario por email
    Usuario findByEmail(String email);
    
    // Verificar si existe un email
    boolean existsByEmail(String email);
    
    // Buscar usuarios por ciudad
    List<Usuario> findByCiudadIdCiudad(int idCiudad);
    
    // Buscar usuarios por nombre (búsqueda parcial)
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
}