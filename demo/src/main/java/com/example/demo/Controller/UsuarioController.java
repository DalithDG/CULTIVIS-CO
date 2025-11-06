package com.example.demo.Controller;

import com.example.demo.Model.Ciudad;
import com.example.demo.Model.Usuario;
import com.example.demo.services.UsuarioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService servicio;

    // Página principal: lista de usuarios
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = servicio.obtenerTodos();
        model.addAttribute("usuarios", usuarios);
        return "usuarios"; // -> usuarios.html
    }

    // Formulario para registrar un nuevo usuario
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("ciudad", new Ciudad(1, "Medellín", null, null));
        return "nuevoUsuario"; // -> nuevoUsuario.html
    }

    // Guardar usuario (desde el formulario)
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        servicio.guardarUsuario(usuario);
        return "redirect:/usuarios";
    }

    // Editar usuario
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable int id, Model model) {
        Usuario usuario = servicio.obtenerUsuarioPorId(id);
        model.addAttribute("usuario", usuario);
        return "editarUsuario"; // -> editarUsuario.html
    }

    // Actualizar usuario
    @PostMapping("/actualizar")
    public String actualizarUsuario(@ModelAttribute Usuario usuario) {
        servicio.actualizarUsuario(usuario);
        return "redirect:/usuarios";
    }

    // Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable int id) {
        servicio.eliminarUsuario(id);
        return "redirect:/usuarios";
    }
}
