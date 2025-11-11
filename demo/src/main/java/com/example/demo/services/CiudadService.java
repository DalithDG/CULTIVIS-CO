package com.example.demo.services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import com.example.demo.Model.Ciudad;
import com.example.demo.repository.CiudadRepository;

@Service
public class CiudadService implements ICiudadService {

    private final CiudadRepository repo;


    public CiudadService(CiudadRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Ciudad> listarPorDepartamento(int idDepartamento) {
        return repo.findByDepartamentoIdDepartamento(idDepartamento);
    }

    public List<Ciudad> listarTodasLasCiudades() {
        List<Ciudad> ciudades = new ArrayList<>();
        repo.findAll().forEach(ciudades::add);
        return ciudades;
    }

}
