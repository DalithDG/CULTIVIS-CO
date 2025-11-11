package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Model.Usuario;
import com.example.demo.services.DepartamentoService;
import com.example.demo.services.CiudadService;

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


    

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private CiudadService ciudadService;

    @GetMapping("/register")
    public String Registro(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("departamentos", departamentoService.listarDepartamentos());
        model.addAttribute("ciudades", ciudadService.listarTodasLasCiudades());
        return "register";
    }


    @GetMapping("/carrito")
    public String aliasCarrito() {
        return "carrito";
    }

}
