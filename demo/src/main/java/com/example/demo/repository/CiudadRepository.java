package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Model.Ciudad;
import java.util.List;

@Repository
public interface CiudadRepository extends CrudRepository<Ciudad, Integer> {
    List<Ciudad> findByDepartamentoIdDepartamento(int idDepartamento);
}