package com.example.demo.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.Model.Ciudad;
import com.example.demo.Model.Departamento;
import com.example.demo.services.CiudadService;
import com.example.demo.services.DepartamentoService;

@RestController
@RequestMapping("/api/ubicacion")
public class CiudadesController {

    private final DepartamentoService departamentoService;
    private final CiudadService ciudadService;

   
    public CiudadesController(DepartamentoService departamentoService, CiudadService ciudadService) {
        this.departamentoService = departamentoService;
        this.ciudadService = ciudadService;
    }

    @GetMapping("/departamentos")
    public ResponseEntity<List<Departamento>> listarDepartamentos() {
        try {
            List<Departamento> departamentos = departamentoService.listarDepartamentos();
            System.out.println("Departamentos encontrados: " + departamentos.size());
            return ResponseEntity.ok(departamentos);
        } catch (Exception e) {
            System.err.println("Error al listar departamentos: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/ciudades/{idDepartamento}")
    public List<Ciudad> listarCiudades(@PathVariable int idDepartamento) {
        List<Ciudad> ciudades = ciudadService.listarPorDepartamento(idDepartamento);
        System.out.println("Ciudades encontradas para departamento " + idDepartamento + ": " + ciudades.size());
        return ciudades;
    }    


}
