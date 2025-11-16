package com.example.demo.Controller;

import com.example.demo.Model.PerfilVendedor;
import com.example.demo.Model.Usuario;
import com.example.demo.services.VendedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    /**
     * Muestra el formulario para convertirse en vendedor
     */
    @GetMapping("/registro")
    public String mostrarFormularioVendedor(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
            return "redirect:/usuario/login";
        }

        // Verificar si ya es vendedor
        if (vendedorService.esVendedor(usuario.getId())) {
            redirectAttributes.addFlashAttribute("info", "Ya tienes un perfil de vendedor");
            return "redirect:/vendedor/perfil";
        }

        model.addAttribute("usuario", usuario);
        return "registro-vendedor"; // Vista con el formulario
    }

    /**
     * Procesa el formulario de registro de vendedor
     */
    @PostMapping("/guardar")
    public String guardarPerfilVendedor(@RequestParam("razonSocial") String razonSocial,
                                       @RequestParam("telefonoContacto") String telefonoContacto,
                                       @RequestParam("direccionNegocio") String direccionNegocio,
                                       @RequestParam("tipoProductos") String tipoProductos,
                                       @RequestParam(value = "descripcionNegocio", required = false) String descripcionNegocio,
                                       @RequestParam(value = "cuentaBancaria", required = false) String cuentaBancaria,
                                       @RequestParam(value = "banco", required = false) String banco,
                                       HttpSession session,
                                       RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            
            if (usuario == null) {
                redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
                return "redirect:/usuario/login";
            }

            // Crear perfil de vendedor
            PerfilVendedor perfil = vendedorService.crearPerfilVendedor(
                usuario.getId(),
                razonSocial,
                telefonoContacto,
                direccionNegocio,
                tipoProductos,
                descripcionNegocio,
                cuentaBancaria,
                banco
            );

            // Actualizar el usuario en sesión con el perfil cargado
            usuario.setPerfilVendedor(perfil);
            session.setAttribute("usuarioLogueado", usuario);

            System.out.println("Perfil de vendedor creado para: " + usuario.getEmail());
            
            redirectAttributes.addFlashAttribute("mensaje", "¡Felicidades! Ahora eres vendedor en Cultivus");
            return "redirect:/vendedor/inicio";

        } catch (IllegalArgumentException e) {
            System.err.println("Error al crear perfil: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/vendedor/registro";
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al crear el perfil de vendedor");
            return "redirect:/vendedor/registro";
        }
    }

    /**
     * Página de inicio para vendedores (usa la misma vista que usuario/inicio pero
     * con header de vendedor)
     */
    @GetMapping("/inicio")
    public String mostrarInicioVendedor(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
            return "redirect:/usuario/login";
        }

        if (!vendedorService.esVendedor(usuario.getId())) {
            redirectAttributes.addFlashAttribute("error", "Debe registrarse como vendedor primero");
            return "redirect:/vendedor/registro";
        }

        model.addAttribute("usuario", usuario);
        return "inicio-vendedor"; // Vista que usa header-vendedor
    }

    /**
     * Muestra el dashboard del vendedor
     */
    @GetMapping("/dashboard")
    public String mostrarDashboard(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
            return "redirect:/usuario/login";
        }

        if (!vendedorService.esVendedor(usuario.getId())) {
            redirectAttributes.addFlashAttribute("error", "Debe registrarse como vendedor primero");
            return "redirect:/vendedor/registro";
        }

        // Obtener perfil de vendedor actualizado
        Optional<PerfilVendedor> perfilOpt = vendedorService.obtenerPerfilPorUsuarioId(usuario.getId());
        perfilOpt.ifPresent(perfil -> model.addAttribute("perfilVendedor", perfil));

        model.addAttribute("usuario", usuario);
        return "Miproductos"; // Vista del dashboard
    }

    /**
     * Muestra el perfil completo del vendedor
     */
    @GetMapping("/perfil")
    public String mostrarPerfil(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
            return "redirect:/usuario/login";
        }

        Optional<PerfilVendedor> perfilOpt = vendedorService.obtenerPerfilPorUsuarioId(usuario.getId());
        
        if (perfilOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No tienes un perfil de vendedor");
            return "redirect:/vendedor/registro";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("perfilVendedor", perfilOpt.get());
        return "perfil-vendedor"; // Vista del perfil
    }

    /**
     * Muestra formulario para editar el perfil de vendedor
     */
    @GetMapping("/editar")
    public String mostrarFormularioEditar(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
            return "redirect:/usuario/login";
        }

        Optional<PerfilVendedor> perfilOpt = vendedorService.obtenerPerfilPorUsuarioId(usuario.getId());
        
        if (perfilOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No tienes un perfil de vendedor");
            return "redirect:/vendedor/registro";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("perfilVendedor", perfilOpt.get());
        return "editar-perfil-vendedor"; // Vista para editar
    }

    /**
     * Actualiza el perfil de vendedor
     */
    @PostMapping("/actualizar")
    public String actualizarPerfil(@RequestParam("perfilId") int perfilId,
                                   @RequestParam("razonSocial") String razonSocial,
                                   @RequestParam("telefonoContacto") String telefonoContacto,
                                   @RequestParam("direccionNegocio") String direccionNegocio,
                                   @RequestParam("tipoProductos") String tipoProductos,
                                   @RequestParam(value = "descripcionNegocio", required = false) String descripcionNegocio,
                                   @RequestParam(value = "cuentaBancaria", required = false) String cuentaBancaria,
                                   @RequestParam(value = "banco", required = false) String banco,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            
            if (usuario == null) {
                redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
                return "redirect:/usuario/login";
            }

            vendedorService.actualizarPerfil(
                perfilId,
                razonSocial,
                telefonoContacto,
                direccionNegocio,
                tipoProductos,
                descripcionNegocio,
                cuentaBancaria,
                banco
            );

            redirectAttributes.addFlashAttribute("mensaje", "Perfil actualizado exitosamente");
            return "redirect:/vendedor/perfil";

        } catch (Exception e) {
            System.err.println("Error al actualizar perfil: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el perfil");
            return "redirect:/vendedor/editar";
        }
    }
}