package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private DetalleCarritoRepository detalleCarritoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Muestra el formulario de pago
     */
    @GetMapping
    public String mostrarPago(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión para realizar el pago");
            return "redirect:/usuario/login";
        }

        // Obtener carrito del usuario
        Optional<Carrito> carritoOpt = carritoRepository.findByClienteId(usuario.getId());
        if (carritoOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Su carrito está vacío");
            return "redirect:/carrito";
        }
        Carrito carrito = carritoOpt.orElseThrow();

        List<DetalleCarrito> detalles = detalleCarritoRepository.findByCarritoId(carrito.getIdCarrito());

        if (detalles.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Su carrito está vacío");
            return "redirect:/carrito";
        }

        // Calcular total
        double total = detalles.stream()
                .mapToDouble(d -> d.getPrecioTotal())
                .sum();

        model.addAttribute("usuario", usuario);
        model.addAttribute("carrito", carrito);
        model.addAttribute("detalles", detalles);
        model.addAttribute("total", total);

        return "pago";
    }

    /**
     * Procesa el pago y crea el pedido
     */
    @PostMapping("/procesar")
    public String procesarPago(@RequestParam("metodoPago") String metodoPago,
            @RequestParam(value = "direccionEnvio", required = false) String direccionEnvio,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

            if (usuario == null) {
                redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión para realizar el pago");
                return "redirect:/usuario/login";
            }

            // Obtener carrito
            Carrito carrito = carritoRepository.findByClienteId(usuario.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

            List<DetalleCarrito> detallesCarrito = detalleCarritoRepository.findByCarrito(carrito);

            if (detallesCarrito.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Su carrito está vacío");
                return "redirect:/carrito";
            }

            // Verificar stock antes de crear el pedido
            for (DetalleCarrito detalle : detallesCarrito) {
                Producto producto = detalle.getProducto();
                if (producto.getStock() < detalle.getCantidad()) {
                    redirectAttributes.addFlashAttribute("error",
                            "El producto " + producto.getNombre() + " no tiene suficiente stock");
                    return "redirect:/carrito";
                }
            }

            // Calcular total
            double total = detallesCarrito.stream()
                    .mapToDouble(d -> d.getPrecioTotal())
                    .sum();

            // Crear pedido
            Pedido pedido = new Pedido();
            pedido.setCliente(usuario);
            pedido.setFechaPedido(LocalDateTime.now());
            pedido.setEstado("PENDIENTE");
            pedido.setTotal((float) total);
            pedido = pedidoRepository.save(pedido);

            // Crear detalles del pedido y actualizar stock
            for (DetalleCarrito detalleCarrito : detallesCarrito) {
                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setPedido(pedido);
                detallePedido.setProducto(detalleCarrito.getProducto());
                detallePedido.setCantidad(detalleCarrito.getCantidad());
                detallePedido.setPrecioUnitario(detalleCarrito.getPrecioUnitario());
                detallePedidoRepository.save(detallePedido);

                // Actualizar stock
                Producto producto = detalleCarrito.getProducto();
                producto.setStock(producto.getStock() - detalleCarrito.getCantidad());
                productoRepository.save(producto);
            }

            // Crear pago
            Pago pago = new Pago();
            pago.setPedido(pedido);
            pago.setMonto((float) total);
            pago.setFechaPago(LocalDateTime.now());
            pago.setMetodoPago(metodoPago);
            pagoRepository.save(pago);

            // Limpiar carrito
            Objects.requireNonNull(carrito, "El carrito no puede ser nulo al limpiar datos");
            detalleCarritoRepository.deleteByCarrito(carrito);
            carritoRepository.delete(carrito);

            redirectAttributes.addFlashAttribute("mensaje",
                    "¡Pago procesado exitosamente! Pedido #" + pedido.getIdPedido());
            return "redirect:/pago/confirmacion/" + pedido.getIdPedido();

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar el pago: " + e.getMessage());
            return "redirect:/pago";
        }
    }

    /**
     * Muestra la confirmación del pago
     */
    @GetMapping("/confirmacion/{pedidoId}")
    public String mostrarConfirmacion(@PathVariable int pedidoId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/usuario/login";
        }

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

        // Verificar que el pedido pertenece al usuario
        if (pedido.getCliente().getId() != usuario.getId()) {
            redirectAttributes.addFlashAttribute("error", "No tiene permiso para ver este pedido");
            return "redirect:/";
        }

        List<DetallePedido> detalles = detallePedidoRepository.findByPedidoIdPedido(pedidoId);
        Optional<Pago> pagoOpt = pagoRepository.findByPedido(pedido);

        model.addAttribute("usuario", usuario);
        model.addAttribute("pedido", pedido);
        model.addAttribute("detalles", detalles);
        model.addAttribute("pago", pagoOpt.orElse(null));

        return "confirmacion-pago";
    }
}
