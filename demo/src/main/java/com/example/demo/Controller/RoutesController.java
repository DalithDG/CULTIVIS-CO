package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Model.Usuario;

@Controller
public class RoutesController {



    @GetMapping("/")
    public String Index() {
        return "index";
    }

    @GetMapping("/category")
    public String paginaCategoria() {
        return "category";
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
        return "frutas";
    }
    @GetMapping("/verduras")
    public String verVerduras(Model model) {
        return "verduras";
    }
    @GetMapping("/lacteos")
    public String verLacteos(Model model) {
        return "verduras";
    }
    @GetMapping("/cafe")
    public String verCafe(Model model) {
        return "cafeYcacao";
    }


    


    @GetMapping("/carrito")
    public String aliasCarrito() {
        return "carrito";
    }

}
