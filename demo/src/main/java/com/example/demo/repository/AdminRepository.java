package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Admin;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    
    // Buscar admin por nombre de usuario
    Optional<Admin> findByUserAdm(String userAdm);
    
    // Verificar si existe un nombre de usuario
    boolean existsByUserAdm(String userAdm);
}