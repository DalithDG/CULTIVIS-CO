package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ProductoController {

    @GetMapping("/producto")
    public String verProducto(Model model) {
        // Crear producto principal
        Producto producto = new Producto();
        producto.setNombre("Tomate");
        producto.setDescripcion("Tomates frescos del campo.");
        producto.setPrecio(5000);
        producto.setStock(20);
        producto.setUbicacion("Cundinamarca");
        producto.setActiva(true);
        producto.setImagenPrincipal("/image/tomate.jpg");
        producto.setImagenes(List.of("/image/tomate1.jpg", "/image/tomate2.jpg"));

        // Crear productos relacionados
        List<Producto> relacionados = List.of(
            new Producto("Lechuga", "/image/lechuga.jpg"),
            new Producto("Zanahoria", "/image/zanahoria.jpg")
        );

        // Pasar datos a la vista
        model.addAttribute("producto", producto);
        model.addAttribute("relacionados", relacionados);

        return "producto"; // coincide con producto.html en src/main/resources/templates/
    }
}
