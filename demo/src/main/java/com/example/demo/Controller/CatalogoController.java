package com.example.demo.Controller;

import com.example.demo.Model.Producto;
import com.example.demo.Model.Usuario;
import com.example.demo.repository.ProductoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controlador para las rutas públicas de productos (catálogo)
 */
@Controller
public class CatalogoController {

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Muestra todos los productos disponibles (página pública)
     * Ruta: /productos
     */
    @GetMapping("/productos")
    public String listarProductos(Model model, HttpSession session) {
        // Obtener todos los productos
        List<Producto> productos = productoRepository.findAll();
        
        // Obtener usuario si está logueado (opcional)
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        model.addAttribute("productos", productos);
        model.addAttribute("usuario", usuario);
        
        return "productos-listado";
    }
    
    /**
     * Muestra el detalle de un producto específico (página pública)
     * Ruta: /producto/{id}
     */
    @GetMapping("/producto/{id}")
    public String verDetalleProducto(@PathVariable int id, 
                                     Model model, 
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        try {
            // Buscar el producto por ID
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
            
            // Obtener usuario si está logueado (opcional)
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            
            model.addAttribute("producto", producto);
            model.addAttribute("usuario", usuario);
            
            return "producto-detalle";
            
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/productos";
        }
    }
    
}