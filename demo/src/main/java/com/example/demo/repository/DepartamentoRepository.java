package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Model.Departamento;
import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
    List<Departamento> findAllByOrderByNombreAsc();
}
