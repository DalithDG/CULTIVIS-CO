package com.example.demo.Controller;

import com.example.demo.Model.Usuario;
import com.example.demo.Model.Ciudad;
import com.example.demo.Model.Departamento;
import com.example.demo.services.UsuarioService;
import com.example.demo.services.CiudadService;
import com.example.demo.services.DepartamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final CiudadService ciudadService;
    private final DepartamentoService departamentoService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService,
                             CiudadService ciudadService,
                             DepartamentoService departamentoService) {
        this.usuarioService = usuarioService;
        this.ciudadService = ciudadService;
        this.departamentoService = departamentoService;
    }

    // ===========================
    // MOSTRAR FORMULARIO DE REGISTRO
    // ===========================
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("departamentos", departamentoService.findAll());
        return "registro";
    }

    // ===========================
    // GUARDAR NUEVO USUARIO
    // ===========================
    @PostMapping("/guardar")
    public String guardarUsuario(@RequestParam("nombre") String nombre,
                                 @RequestParam("email") String email,
                                 @RequestParam("contrasena") String contrasena,
                                 @RequestParam("departamento") String nombreDepartamento,
                                 @RequestParam("ciudad") String nombreCiudad,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        try {
            // Validaciones
            if (nombre == null || nombre.trim().isEmpty()) {
                model.addAttribute("error", "El nombre es requerido");
                return "registro";
            }
            if (email == null || email.trim().isEmpty()) {
                model.addAttribute("error", "El correo electrónico es requerido");
                return "registro";
            }
            if (contrasena == null || contrasena.trim().isEmpty()) {
                model.addAttribute("error", "La contraseña es requerida");
                return "registro";
            }

            // Validar email duplicado
            String emailLimpio = email.trim().toLowerCase();
            if (usuarioService.existeEmail(emailLimpio)) {
                model.addAttribute("error", "Este correo electrónico ya está registrado");
                return "registro";
            }

            // Buscar o crear departamento
            Departamento departamento = departamentoService.findByNombre(nombreDepartamento.trim());
            if (departamento == null) {
                departamento = new Departamento();
                departamento.setNombre(nombreDepartamento.trim());
                departamento = departamentoService.save(departamento);
            }

            // Buscar o crear ciudad
            Ciudad ciudad = ciudadService.findByNombre(nombreCiudad.trim());
            if (ciudad == null) {
                ciudad = new Ciudad();
                ciudad.setNombre(nombreCiudad.trim());
                ciudad.setDepartamento(departamento);
                ciudad = ciudadService.save(ciudad);
            }

            // Crear usuario
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre.trim());
            usuario.setEmail(emailLimpio);
            usuario.setContrasena(contrasena);
            usuario.setDepartamento(departamento);
            usuario.setCiudad(ciudad);

            // Guardar usuario
            usuarioService.save(usuario);
            System.out.println("✅ Usuario guardado: " + usuario.getEmail());

            // Redirigir al login con mensaje
            redirectAttributes.addFlashAttribute("mensaje", "¡Registro exitoso! Por favor inicia sesión.");
            return "redirect:/usuario/login";

        } catch (Exception e) {
            System.err.println("❌ Error al registrar: " + e.getMessage());
            model.addAttribute("error", "Error al registrar el usuario: " + e.getMessage());
            return "registro";
        }
    }

    // ===========================
    // MOSTRAR LOGIN
    // ===========================
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    // ===========================
    // VALIDAR LOGIN
    // ===========================
    @PostMapping("/login")
    public String validarLogin(@RequestParam("email") String email,
                               @RequestParam("contrasena") String contrasena,
                               HttpSession session,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            System.out.println("🔍 Intentando login con email: " + email);
            
            if (email == null || email.trim().isEmpty() ||
                contrasena == null || contrasena.trim().isEmpty()) {
                System.out.println("❌ Campos vacíos");
                model.addAttribute("error", "Por favor ingrese su correo y contraseña");
                return "login";
            }

            String emailLimpio = email.trim().toLowerCase();
            System.out.println("🔍 Buscando usuario: " + emailLimpio);
            
            Usuario usuario = usuarioService.findByEmail(emailLimpio);
            
            if (usuario == null) {
                System.out.println("❌ Usuario no encontrado: " + emailLimpio);
                model.addAttribute("error", "No existe una cuenta con ese correo electrónico");
                return "login";
            }

            System.out.println("✓ Usuario encontrado: " + usuario.getNombre());
            System.out.println("🔍 Contraseña ingresada: [" + contrasena + "]");
            System.out.println("🔍 Contraseña guardada: [" + usuario.getContrasena() + "]");

            if (!usuario.getContrasena().equals(contrasena)) {
                System.out.println("❌ Contraseña incorrecta");
                model.addAttribute("error", "Contraseña incorrecta");
                return "login";
            }

            // Guardar usuario en sesión
            session.setAttribute("usuarioLogueado", usuario);
            System.out.println("✅ Login exitoso: " + usuario.getEmail());

            redirectAttributes.addFlashAttribute("mensaje", "Bienvenido, " + usuario.getNombre() + "!");
            return "redirect:/usuario/inicio";

        } catch (Exception e) {
            System.err.println("❌ Error en login: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error al iniciar sesión: " + e.getMessage());
            return "login";
        }
    }

    // ===========================
    // PÁGINA DE INICIO (después del login)
    // ===========================
    @GetMapping("/inicio")
    public String mostrarInicio(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
            return "redirect:/usuario/login";
        }
        
        model.addAttribute("usuario", usuario);
        return "inicio";
    }

    // ===========================
    // CERRAR SESIÓN
    // ===========================
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        return "redirect:/";
    }

    // ===========================
    // PERFIL DEL USUARIO
    // ===========================
    @GetMapping("/perfil")
    public String mostrarPerfil(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
            return "redirect:/usuario/login";
        }
        
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    // ===========================
    // PÁGINA PRINCIPAL (raíz)
    // ===========================
    @GetMapping("/")
    public String paginaPrincipal() {
        return "redirect:/";
    }
}