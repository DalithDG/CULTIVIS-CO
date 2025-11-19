package com.example.demo.services;

import com.example.demo.Model.Roles;

public interface IRolesService {

    Roles obtenerRolPorNombre(String nombre);

    Roles crearRolSiNoExiste(String nombre);
}
