package com.example.demo.Controller;

import com.example.demo.Model.Producto;
import com.example.demo.Model.Categoria;
import com.example.demo.services.BusquedaService;
import com.example.demo.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BusquedaController {

    @Autowired
    private BusquedaService busquedaService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/buscar")
    public String buscarProductos(
            @RequestParam(value = "q", required = false, defaultValue = "") String query,
            @RequestParam(value = "categoria", required = false) Integer categoriaId,
            @RequestParam(value = "precioMin", required = false) Float precioMin,
            @RequestParam(value = "precioMax", required = false) Float precioMax,
            @RequestParam(value = "orden", required = false, defaultValue = "recientes") String orden,
            Model model) {

        List<Producto> productos;

        // Búsqueda avanzada con filtros
        if (categoriaId != null || precioMin != null || precioMax != null) {
            productos = busquedaService.busquedaAvanzada(query, categoriaId, precioMin, precioMax);
        } else if (query != null && !query.trim().isEmpty()) {
            productos = busquedaService.buscarProductos(query);
        } else {
            productos = busquedaService.buscarConStock();
        }

        // Aplicar ordenamiento sobre los resultados existentes
        if (productos != null && !productos.isEmpty()) {
            switch (orden) {
                case "precio-asc":
                    productos = productos.stream()
                            .sorted((p1, p2) -> Float.compare(p1.getPrecio(), p2.getPrecio()))
                            .collect(Collectors.toList());
                    break;
                case "precio-desc":
                    productos = productos.stream()
                            .sorted((p1, p2) -> Float.compare(p2.getPrecio(), p1.getPrecio()))
                            .collect(Collectors.toList());
                    break;
                case "recientes":
                    productos = productos.stream()
                            .sorted((p1, p2) -> Integer.compare(p2.getId(), p1.getId()))
                            .collect(Collectors.toList());
                    break;
            }
        }

        // Obtener categorías para filtros
        List<Categoria> categorias = categoriaRepository.findAll();

        model.addAttribute("productos", productos != null ? productos : new ArrayList<>());
        model.addAttribute("categorias", categorias);
        model.addAttribute("query", query);
        model.addAttribute("categoriaSeleccionada", categoriaId);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("orden", orden);
        model.addAttribute("totalResultados", productos != null ? productos.size() : 0);

        return "busqueda-resultados";
    }
}
