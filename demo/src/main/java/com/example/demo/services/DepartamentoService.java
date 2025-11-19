package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Departamento;
import com.example.demo.repository.DepartamentoRepository;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;
    
    public List<Departamento> findAll() {
        return departamentoRepository.findAllByOrderByNombreAsc();
    }
    
    public Departamento findById(int id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado con ID: " + id));
    }
    
    public Departamento findByNombre(String nombre) {
        return departamentoRepository.findByNombreIgnoreCase(nombre);
    }
    
    public Departamento save(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }
}

