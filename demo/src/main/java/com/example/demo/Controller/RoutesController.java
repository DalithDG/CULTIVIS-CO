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

    @GetMapping("/product")
    public String Producto() {
        return "product";
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

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private CiudadService ciudadService;

    @GetMapping("/registro")
    public String Registro(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("departamentos", departamentoService.listarDepartamentos());
        model.addAttribute("ciudades", ciudadService.listarTodasLasCiudades());
        return "register";
    }

    // Rutas compatibles con el header y alias
    @GetMapping("/newregister")
    public String aliasNewRegister() {
        return "redirect:/registro";
    }

    @GetMapping("/loginnew")
    public String aliasLoginNew() {
        return "redirect:/login";
    }

    @GetMapping("/categorias")
    public String aliasCategorias() {
        return "redirect:/product";
    }

    @GetMapping("/carrito")
    public String aliasCarrito() {
        // A falta de una vista de carrito, redirigimos a la lista de productos por ahora
        return "redirect:/product";
    }

}
