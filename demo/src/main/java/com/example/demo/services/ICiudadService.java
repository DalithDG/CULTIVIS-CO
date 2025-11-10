package com.example.demo.services;

import java.util.List;

import com.example.demo.Model.Ciudad;

public interface ICiudadService {

    List<Ciudad> listarPorDepartamento(int idDepartamento);

}
