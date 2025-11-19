package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoRepository carritoRepository;
    
    @Autowired
    private DetalleCarritoRepository detalleCarritoRepository;
    
    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Muestra el carrito del usuario
     */
    @GetMapping
    public String mostrarCarrito(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión para ver su carrito");
            return "redirect:/usuario/login";
        }

        // Obtener o crear carrito del usuario
        Carrito carrito = carritoRepository.findByClienteId(usuario.getId())
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setCliente(usuario);
                    return carritoRepository.save(nuevoCarrito);
                });

        // Obtener detalles del carrito
        List<DetalleCarrito> detalles = detalleCarritoRepository.findByCarrito(carrito);
        
        // Calcular total
        double total = detalles.stream()
                .mapToDouble(d -> d.getPrecioTotal())
                .sum();

        model.addAttribute("usuario", usuario);
        model.addAttribute("carrito", carrito);
        model.addAttribute("detalles", detalles);
        model.addAttribute("total", total);
        
        return "carrito";
    }

    /**
     * Agrega un producto al carrito
     */
    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam("productoId") int productoId,
                                   @RequestParam(value = "cantidad", defaultValue = "1") int cantidad,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            
            if (usuario == null) {
                redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión para agregar productos al carrito");
                return "redirect:/usuario/login";
            }

            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            // Verificar stock
            if (producto.getStock() < cantidad) {
                redirectAttributes.addFlashAttribute("error", "No hay suficiente stock disponible");
                return "redirect:/";
            }

            // Obtener o crear carrito
            Carrito carrito = carritoRepository.findByClienteId(usuario.getId())
                    .orElseGet(() -> {
                        Carrito nuevoCarrito = new Carrito();
                        nuevoCarrito.setCliente(usuario);
                        return carritoRepository.save(nuevoCarrito);
                    });

            // Verificar si el producto ya está en el carrito
            Optional<DetalleCarrito> detalleExistente = detalleCarritoRepository.findByCarritoAndProducto(carrito, producto);
            
            if (detalleExistente.isPresent()) {
                DetalleCarrito detalle = detalleExistente.get();
                int nuevaCantidad = detalle.getCantidad() + cantidad;
                
                if (producto.getStock() < nuevaCantidad) {
                    redirectAttributes.addFlashAttribute("error", "No hay suficiente stock disponible");
                    return "redirect:/";
                }
                
                detalle.setCantidad(nuevaCantidad);
                detalleCarritoRepository.save(detalle);
            } else {
                DetalleCarrito nuevoDetalle = new DetalleCarrito();
                nuevoDetalle.setCarrito(carrito);
                nuevoDetalle.setProducto(producto);
                nuevoDetalle.setCantidad(cantidad);
                nuevoDetalle.setPrecioUnitario(producto.getPrecio());
                detalleCarritoRepository.save(nuevoDetalle);
            }

            redirectAttributes.addFlashAttribute("mensaje", "Producto agregado al carrito");
            
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al agregar producto al carrito");
        }
        
        return "redirect:/carrito";
    }

    /**
     * Actualiza la cantidad de un producto en el carrito
     */
    @PostMapping("/actualizar")
    public String actualizarCantidad(@RequestParam("productoId") int productoId,
                                     @RequestParam("cantidad") int cantidad,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            
            if (usuario == null) {
                return "redirect:/usuario/login";
            }

            Carrito carrito = carritoRepository.findByClienteId(usuario.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            if (cantidad <= 0) {
                // Eliminar del carrito si la cantidad es 0 o menor
                detalleCarritoRepository.findByCarritoAndProducto(carrito, producto)
                        .ifPresent(detalleCarritoRepository::delete);
                redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado del carrito");
            } else {
                if (producto.getStock() < cantidad) {
                    redirectAttributes.addFlashAttribute("error", "No hay suficiente stock disponible");
                    return "redirect:/carrito";
                }

                DetalleCarrito detalle = detalleCarritoRepository.findByCarritoAndProducto(carrito, producto)
                        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado en el carrito"));

                detalle.setCantidad(cantidad);
                detalleCarritoRepository.save(detalle);
                redirectAttributes.addFlashAttribute("mensaje", "Cantidad actualizada");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el carrito");
        }
        
        return "redirect:/carrito";
    }

    /**
     * Elimina un producto del carrito
     */
    @PostMapping("/eliminar")
    public String eliminarDelCarrito(@RequestParam("productoId") int productoId,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            
            if (usuario == null) {
                return "redirect:/usuario/login";
            }

            Carrito carrito = carritoRepository.findByClienteId(usuario.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            detalleCarritoRepository.findByCarritoAndProducto(carrito, producto)
                    .ifPresent(detalleCarritoRepository::delete);

            redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado del carrito");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el producto");
        }
        
        return "redirect:/carrito";
    }
}

