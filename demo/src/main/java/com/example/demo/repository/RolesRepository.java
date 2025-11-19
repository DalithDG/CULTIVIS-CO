package com.example.demo.repository;

import com.example.demo.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
