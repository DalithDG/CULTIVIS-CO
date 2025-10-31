package com.example.demo.Controller;

import com.example.demo.Model.Usuario;
import com.example.demo.services.UsuarioService; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Inyección del servicio (corregido el nombre del atributo)
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Mostrar formulario de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro"; // vista registro.html
    }

    // Procesar registro
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        String mensaje = usuarioService.registrarUsuario(usuario);
        model.addAttribute("mensaje", mensaje);
        return "login";
    }

    // Mostrar formulario de login
    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login"; // vista login.html
    }

    // Procesar login
    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute Usuario usuario, Model model) {
        String mensaje = usuarioService.iniciarSesion(usuario.getEmail(), usuario.getContraseña());
        model.addAttribute("mensaje", mensaje);

        if (mensaje.startsWith("Inicio de sesión exitoso")) {
            return "index"; // vista principal después de iniciar sesión
        } else {
            return "login"; // vuelve al login si falla
        }
    }
}
