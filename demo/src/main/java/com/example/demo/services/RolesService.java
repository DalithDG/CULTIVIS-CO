package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Roles;
import com.example.demo.repository.RolesRepository;

@Service
public class RolesService implements IRolesService{

@Autowired
    private RolesRepository rolesRepository;

    @Override
    public Roles obtenerRolPorNombre(String nombre) {
        return rolesRepository.findByNombre(nombre).orElse(null);
    }

    @Override
   public Roles crearRolSiNoExiste(String nombre) {
    return rolesRepository.findByNombre(nombre)
        .orElseGet(() -> rolesRepository.save(new Roles(nombre)));
}
    
}
