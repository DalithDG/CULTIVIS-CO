package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Model.Departamento;
import com.example.demo.repository.DepartamentoRepository;
import java.util.List;
import java.util.ArrayList;

@Service
public class DepartamentoService implements IDepartamentoService {

    private final DepartamentoRepository repo;

    @Autowired
    public DepartamentoService(DepartamentoRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Departamento> listarDepartamentos() {
        return repo.findAllByOrderByNombreAsc();
    }

}
