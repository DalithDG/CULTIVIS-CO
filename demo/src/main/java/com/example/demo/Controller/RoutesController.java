package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/category")
    public String paginaCategoria(@RequestParam(value = "categoria", required = false) List<String> categoriasFiltro,
                                 Model model) {
        List<Producto> productos = productoRepository.findAll();
        
        if (categoriasFiltro != null && !categoriasFiltro.isEmpty()) {
            // Mapeo de filtros a nombres de categorías
            java.util.Map<String, String> mapeoCategorias = new java.util.HashMap<>();
            mapeoCategorias.put("frutas", "Frutas");
            mapeoCategorias.put("verduras", "Verduras");
            mapeoCategorias.put("lacteos", "Lácteos");
            mapeoCategorias.put("cafe", "Café y Cacao");
            mapeoCategorias.put("cacao", "Café y Cacao");
            
            // Filtrar por categorías seleccionadas
            List<String> nombresCategorias = categoriasFiltro.stream()
                    .map(c -> mapeoCategorias.getOrDefault(c.toLowerCase(), 
                            c.substring(0, 1).toUpperCase() + c.substring(1)))
                    .distinct()
                    .collect(Collectors.toList());
            
            productos = productos.stream()
                    .filter(p -> {
                        if (p.getCategoria() == null) return false;
                        String nombreCategoria = p.getCategoria().getNombre();
                        return nombresCategorias.stream()
                                .anyMatch(nc -> nombreCategoria.equalsIgnoreCase(nc) ||
                                               nombreCategoria.toLowerCase().contains(nc.toLowerCase()) ||
                                               nc.toLowerCase().contains(nombreCategoria.toLowerCase()));
                    })
                    .collect(Collectors.toList());
        }
        
        model.addAttribute("productos", productos);
        return "category";
    }

    @GetMapping("/product-detall")
    public String ProductoDetall() {
        return "product-detall";
    }

    @GetMapping("/registro-vendedor")
    public String RegistroVendedor() {
        return "registro-vendedor";
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
