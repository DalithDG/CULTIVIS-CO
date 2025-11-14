package com.example.demo.repository;

import com.example.demo.Model.Ciudad;
import com.example.demo.Model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {
    
    @Query("SELECT c FROM Ciudad c JOIN FETCH c.departamento ORDER BY c.departamento.nombre, c.nombre")
    List<Ciudad> findAllWithDepartamento();
    
    // Buscar ciudades por nombre
    List<Ciudad> findByNombre(String nombre);
    
    // Buscar ciudades por nombre que contenga (búsqueda parcial)
    List<Ciudad> findByNombreContaining(String nombre);
    
    // Buscar ciudades por departamento
    List<Ciudad> findByDepartamento(Departamento departamento);
    
    // Buscar ciudades por ID de departamento
    List<Ciudad> findByDepartamento_IdDepartamento(int idDepartamento);
    
    // Buscar ciudad por nombre y departamento
    Optional<Ciudad> findByNombreAndDepartamento(String nombre, Departamento departamento);
    
    // Verificar si existe una ciudad con ese nombre
    boolean existsByNombre(String nombre);
    
    // Contar ciudades por departamento
    long countByDepartamento_IdDepartamento(int idDepartamento);
}