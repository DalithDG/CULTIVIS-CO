package com.example.demo.Controller;

import com.example.demo.Model.Usuario;
import com.example.demo.Model.Ciudad;
import com.example.demo.Model.Departamento;
import com.example.demo.Model.Roles;
import com.example.demo.Model.Producto;
import com.example.demo.services.UsuarioService;
import com.example.demo.services.CiudadService;
import com.example.demo.services.DepartamentoService;
import com.example.demo.services.RolesService;
import com.example.demo.services.VendedorService;
import com.example.demo.services.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final CiudadService ciudadService;
    private final DepartamentoService departamentoService;
    private final VendedorService vendedorService;
    private final RolesService rolesService;
    private final ProductoService productoService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService,
            CiudadService ciudadService,
            DepartamentoService departamentoService,
            VendedorService vendedorService,
            RolesService rolesService,
            ProductoService productoService) {
        this.usuarioService = usuarioService;
        this.ciudadService = ciudadService;
        this.departamentoService = departamentoService;
        this.vendedorService = vendedorService;
        this.rolesService = rolesService;
        this.productoService = productoService;
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
    // GUARDAR NUEVO USUARIO (FORMULARIO)
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
            // VALIDACIONES
            if (nombre == null || nombre.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    contrasena == null || contrasena.trim().isEmpty()) {
                model.addAttribute("error", "Todos los campos son requeridos");
                return "registro";
            }

            String emailLimpio = email.trim().toLowerCase();
            if (usuarioService.existeEmail(emailLimpio)) {
                model.addAttribute("error", "Este correo ya está registrado");
                return "registro";
            }

            // BUSCAR O CREAR DEPARTAMENTO
            Departamento departamento = departamentoService.findByNombre(nombreDepartamento.trim());
            if (departamento == null) {
                departamento = new Departamento();
                departamento.setNombre(nombreDepartamento.trim());
                departamento = departamentoService.save(departamento);
            }

            // BUSCAR O CREAR CIUDAD
            Ciudad ciudad = ciudadService.findByNombre(nombreCiudad.trim());
            if (ciudad == null) {
                ciudad = new Ciudad();
                ciudad.setNombre(nombreCiudad.trim());
                ciudad.setDepartamento(departamento);
                ciudad = ciudadService.save(ciudad);
            }

            // CREAR USUARIO
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre.trim());
            usuario.setEmail(emailLimpio);
            usuario.setContrasena(contrasena);
            usuario.setDepartamento(departamento);
            usuario.setCiudad(ciudad);

            // ASIGNAR ROL POR DEFECTO
            Roles rolComprador = rolesService.crearRolSiNoExiste("COMPRADOR");
            usuario.setRol(rolComprador);

            usuarioService.save(usuario);

            redirectAttributes.addFlashAttribute("mensaje", "¡Registro exitoso! Inicie sesión.");
            return "redirect:/usuario/login";

        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            return "registro";
        }
    }

    // ===========================
    // LOGIN
    // ===========================
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String validarLogin(@RequestParam("email") String email,
            @RequestParam("contrasena") String contrasena,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (email == null || email.trim().isEmpty() ||
                contrasena == null || contrasena.trim().isEmpty()) {
            model.addAttribute("error", "Correo y contraseña requeridos");
            return "login";
        }

        String emailLimpio = email.trim().toLowerCase();
        Usuario usuario = usuarioService.findByEmail(emailLimpio);

        if (usuario == null || !usuario.getContrasena().equals(contrasena)) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login";
        }

        // Recargar usuario desde la base de datos para obtener el rol actualizado
        Usuario usuarioActualizado = usuarioService.obtenerUsuarioPorId(usuario.getId());
        if (usuarioActualizado != null) {
            usuario = usuarioActualizado;
        }

        // CARGAR PERFIL DE VENDEDOR SI EXISTE
        vendedorService.obtenerPerfilPorUsuarioId(usuario.getId())
                .ifPresent(usuario::setPerfilVendedor);

        session.setAttribute("usuarioLogueado", usuario);
        redirectAttributes.addFlashAttribute("mensaje", "Bienvenido, " + usuario.getNombre());

        // VERIFICAR SI ES ADMINISTRADOR Y REDIRIGIR AL DASHBOARD
        try {
            // Intentar obtener AdminService si existe
            org.springframework.context.ApplicationContext context = org.springframework.web.context.support.WebApplicationContextUtils
                    .getWebApplicationContext(session.getServletContext());

            if (context != null && context.containsBean("adminService")) {
                com.example.demo.services.AdminService adminService = context
                        .getBean(com.example.demo.services.AdminService.class);

                if (adminService.esAdmin(usuario.getId())) {
                    return "redirect:/admin/dashboard";
                }
            }
        } catch (Exception e) {
            // Si hay error, continuar con flujo normal
        }

        return "redirect:/usuario/inicio";
    }

    // ===========================
    // INICIO DESPUÉS DEL LOGIN
    // ===========================
    @GetMapping("/inicio")
    public String mostrarInicio(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
            return "redirect:/usuario/login";
        }

        // Recargar usuario para obtener el rol actualizado
        Usuario usuarioActualizado = usuarioService.obtenerUsuarioPorId(usuario.getId());
        if (usuarioActualizado != null) {
            usuario = usuarioActualizado;
            vendedorService.obtenerPerfilPorUsuarioId(usuario.getId())
                    .ifPresent(usuario::setPerfilVendedor);
            session.setAttribute("usuarioLogueado", usuario);
        }

        // Cargar productos recientes
        List<Producto> productos = productoService.listarProductos();
        productos = productos.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getId(), p1.getId()))
                .limit(8)
                .collect(java.util.stream.Collectors.toList());

        model.addAttribute("usuario", usuario);
        model.addAttribute("productos", productos);

        // REDIRIGIR SEGÚN ROL
        if (usuario.getRol() != null && usuario.getRol().getNombre().equalsIgnoreCase("VENDEDOR")) {
            return "inicio-vendedor";
        } else {
            return "inicio-comprador";
        }
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

        // Recargar usuario para obtener datos actualizados
        Usuario usuarioActualizado = usuarioService.obtenerUsuarioPorId(usuario.getId());
        if (usuarioActualizado != null) {
            usuario = usuarioActualizado;
            session.setAttribute("usuarioLogueado", usuario);
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("departamentos", departamentoService.findAll());
        return "perfil";
    }

    // ===========================
    // ACTUALIZAR PERFIL DEL USUARIO
    // ===========================
    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(@RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("departamento") String nombreDepartamento,
            @RequestParam("ciudad") String nombreCiudad,
            @RequestParam(value = "contrasena", required = false) String contrasena,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            if (usuario == null) {
                redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
                return "redirect:/usuario/login";
            }

            // Validaciones
            if (nombre == null || nombre.trim().isEmpty() ||
                    email == null || email.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Nombre y email son requeridos");
                return "redirect:/usuario/perfil";
            }

            String emailLimpio = email.trim().toLowerCase();

            // Verificar si el email ya existe (excepto el del usuario actual)
            Usuario usuarioConEmail = usuarioService.findByEmail(emailLimpio);
            if (usuarioConEmail != null && usuarioConEmail.getId() != usuario.getId()) {
                redirectAttributes.addFlashAttribute("error", "Este correo ya está registrado por otro usuario");
                return "redirect:/usuario/perfil";
            }

            // Obtener usuario actualizado de la base de datos
            Usuario usuarioActualizado = usuarioService.obtenerUsuarioPorId(usuario.getId());
            if (usuarioActualizado == null) {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
                return "redirect:/usuario/login";
            }

            // Actualizar datos básicos
            usuarioActualizado.setNombre(nombre.trim());
            usuarioActualizado.setEmail(emailLimpio);

            // Actualizar contraseña solo si se proporcionó una nueva
            if (contrasena != null && !contrasena.trim().isEmpty()) {
                usuarioActualizado.setContrasena(contrasena);
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

            usuarioActualizado.setDepartamento(departamento);
            usuarioActualizado.setCiudad(ciudad);

            // Guardar cambios
            usuarioService.actualizarUsuario(usuarioActualizado);

            // Actualizar sesión
            session.setAttribute("usuarioLogueado", usuarioActualizado);

            redirectAttributes.addFlashAttribute("mensaje", "Perfil actualizado exitosamente");
            return "redirect:/usuario/perfil";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el perfil: " + e.getMessage());
            return "redirect:/usuario/perfil";
        }
    }

    // ===========================
    // CERRAR SESIÓN
    // ===========================
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        return "redirect:/";
    }
}
