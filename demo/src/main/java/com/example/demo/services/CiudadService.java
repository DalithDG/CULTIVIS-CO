package com.example.demo.services;

import java.text.Normalizer;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Ciudad;
import com.example.demo.repository.CiudadRepository;

@Service
public class CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;
    
    public Ciudad findById(int id) {
        return ciudadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada con ID: " + id));
    }
    
    public List<Ciudad> findAll() {
        return ciudadRepository.findAllWithDepartamento();
    }
    
    public Ciudad findByNombre(String nombre) {
        return ciudadRepository.findByNombre(nombre)
                .stream()
                .findFirst()
                .orElse(null);
    }
    
    public Ciudad findByNombreFlexible(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }
        String buscado = normalizar(nombre);
        List<Ciudad> todas = findAll();
        
        // Primero intentar búsqueda exacta normalizada
        Ciudad encontrada = todas.stream()
                .filter(ciudad -> normalizar(ciudad.getNombre()).equals(buscado))
                .findFirst()
                .orElse(null);
        
        // Si no se encuentra, intentar búsqueda parcial (contiene)
        if (encontrada == null) {
            encontrada = todas.stream()
                    .filter(ciudad -> normalizar(ciudad.getNombre()).contains(buscado) || 
                                     buscado.contains(normalizar(ciudad.getNombre())))
                    .findFirst()
                    .orElse(null);
        }
        
        return encontrada;
    }
    
    @SuppressWarnings("null")
    public Ciudad save(Ciudad ciudad) {
        return ciudadRepository.save(ciudad);
    }
    
    public void deleteById(int id) {
        ciudadRepository.deleteById(id);
    }

    public List<Ciudad> findByDepartamentoId(int departamentoId) {
        return ciudadRepository.findByDepartamento_IdDepartamento(departamentoId);
    }

    private String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String sinTildes = Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return sinTildes.toLowerCase();
    }

}
