package com.example.demo.Controller;

import com.example.demo.Model.Producto;
import com.example.demo.Model.Categoria;
import com.example.demo.services.BusquedaService;
import com.example.demo.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BusquedaController {

    @Autowired
    private BusquedaService busquedaService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/buscar")
    public String buscarProductos(@RequestParam(value = "q", required = false) String query,
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

        // Aplicar ordenamiento
        if ("precio-asc".equals(orden)) {
            productos = busquedaService.ordenarPorPrecioAsc();
        } else if ("precio-desc".equals(orden)) {
            productos = busquedaService.ordenarPorPrecioDesc();
        } else if ("recientes".equals(orden)) {
            productos = busquedaService.obtenerMasRecientes();
        }

        // Obtener categorías para filtros
        List<Categoria> categorias = categoriaRepository.findAll();

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("query", query);
        model.addAttribute("categoriaSeleccionada", categoriaId);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("orden", orden);
        model.addAttribute("totalResultados", productos.size());

        return "busqueda-resultados";
    }
}
