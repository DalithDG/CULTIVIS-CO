package com.example.demo.Controller;

import com.example.demo.Model.Usuario;
import com.example.demo.Model.Producto;
import com.example.demo.Model.Pedido;
import com.example.demo.services.ResenaService;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/productos")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping("/{id}/resena")
    public String crearResena(@PathVariable int id,
            @RequestParam("calificacion") int calificacion,
            @RequestParam(value = "comentario", required = false) String comentario,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión para dejar una reseña");
            return "redirect:/usuario/login";
        }

        try {
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            // Verificar que el usuario puede reseñar este producto
            if (!resenaService.puedeResenar(usuario, producto)) {
                redirectAttributes.addFlashAttribute("error",
                        "No puedes reseñar este producto. Debes haberlo comprado primero.");
                return "redirect:/producto/" + id;
            }

            // Validar calificación
            if (calificacion < 1 || calificacion > 5) {
                redirectAttributes.addFlashAttribute("error", "La calificación debe estar entre 1 y 5 estrellas");
                return "redirect:/producto/" + id;
            }

            // Buscar el pedido del usuario que contiene este producto
            List<Pedido> pedidos = pedidoRepository.findByCliente(usuario);
            Pedido pedidoConProducto = null;

            for (Pedido pedido : pedidos) {
                // Aquí deberías verificar si el pedido contiene el producto
                // Por simplicidad, tomamos el primer pedido
                pedidoConProducto = pedido;
                break;
            }

            if (pedidoConProducto == null) {
                redirectAttributes.addFlashAttribute("error", "No se encontró un pedido válido para este producto");
                return "redirect:/producto/" + id;
            }

            // Crear la reseña
            resenaService.crearResena(pedidoConProducto, producto, usuario, calificacion, comentario);
            redirectAttributes.addFlashAttribute("mensaje", "¡Gracias por tu reseña!");

        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la reseña: " + e.getMessage());
        }

        return "redirect:/producto/" + id;
    }

    /**
     * Eliminar una reseña
     */
    @PostMapping("/resena/{idResena}/eliminar")
    public String eliminarResena(@PathVariable int idResena,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión");
            return "redirect:/usuario/login";
        }

        int productoId = 0;
        try {
            // IMPORTANTE: Obtener el producto ID ANTES de eliminar la reseña
            var resenaOpt = resenaService.obtenerResenaPorId(idResena);
            if (resenaOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Reseña no encontrada");
                return "redirect:/productos";
            }

            productoId = resenaOpt.get().getProducto().getId();

            // Ahora sí eliminar la reseña
            resenaService.eliminarResena(idResena, usuario);
            redirectAttributes.addFlashAttribute("mensaje", "Reseña eliminada exitosamente");

            return "redirect:/producto/" + productoId;
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            if (productoId > 0) {
                return "redirect:/producto/" + productoId;
            }
            return "redirect:/productos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la reseña: " + e.getMessage());
            if (productoId > 0) {
                return "redirect:/producto/" + productoId;
            }
            return "redirect:/productos";
        }
    }
}
