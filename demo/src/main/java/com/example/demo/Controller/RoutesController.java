package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Model.Usuario;
import com.example.demo.Model.Producto;
import com.example.demo.repository.ProductoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RoutesController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/")
    public String Index(Model model) {
        // Obtener productos recientes (últimos 8 productos)
        List<Producto> productos = productoRepository.findAll();
        // Ordenar por ID descendente para obtener los más recientes
        productos = productos.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getId(), p1.getId()))
                .limit(8)
                .collect(Collectors.toList());

        model.addAttribute("productos", productos);
        return "inicio-publico";
    }

    @GetMapping("/product-detall")
    public String ProductoDetall() {
        return "product-detall";
    }

    @GetMapping("/login")
    public String Login(Model model) {
        model.addAttribute("usuario", new Usuario()); // tu clase de usuario
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @GetMapping("/register")
    public String register() {
        return "redirect:/registro";
    }

    @GetMapping("/frutas")
    public String verFrutas(Model model) {
        List<Producto> productos = productoRepository.findAll().stream()
                .filter(p -> p.getCategoria() != null &&
                        "Frutas".equalsIgnoreCase(p.getCategoria().getNombre()))
                .collect(Collectors.toList());
        model.addAttribute("productos", productos);
        return "frutas";
    }

    @GetMapping("/verduras")
    public String verVerduras(Model model) {
        List<Producto> productos = productoRepository.findAll().stream()
                .filter(p -> p.getCategoria() != null &&
                        "Verduras".equalsIgnoreCase(p.getCategoria().getNombre()))
                .collect(Collectors.toList());
        model.addAttribute("productos", productos);
        return "verduras";
    }

    @GetMapping("/lacteos")
    public String verLacteos(Model model) {
        List<Producto> productos = productoRepository.findAll().stream()
                .filter(p -> p.getCategoria() != null &&
                        ("Lácteos".equalsIgnoreCase(p.getCategoria().getNombre()) ||
                                "Lacteos".equalsIgnoreCase(p.getCategoria().getNombre())))
                .collect(Collectors.toList());
        model.addAttribute("productos", productos);
        return "lacteos";
    }

    @GetMapping("/cafe")
    public String verCafe(Model model) {
        List<Producto> productos = productoRepository.findAll().stream()
                .filter(p -> p.getCategoria() != null &&
                        ("Café y Cacao".equalsIgnoreCase(p.getCategoria().getNombre()) ||
                                "Cafe".equalsIgnoreCase(p.getCategoria().getNombre()) ||
                                "Cacao".equalsIgnoreCase(p.getCategoria().getNombre())))
                .collect(Collectors.toList());
        model.addAttribute("productos", productos);
        return "cafeYcacao";
    }

}
