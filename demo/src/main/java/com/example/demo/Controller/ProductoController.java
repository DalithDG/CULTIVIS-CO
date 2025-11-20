package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.repository.*;
import com.example.demo.services.ProductoService;
import com.example.demo.services.VendedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/vendedor")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VendedorService vendedorService;

    /**
     * Muestra el formulario para agregar un nuevo producto
     */
    @GetMapping("/productos/nuevo")
    public String mostrarFormularioProducto(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
            return "redirect:/usuario/login";
        }

        if (!vendedorService.esVendedor(usuario.getId())) {
            redirectAttributes.addFlashAttribute("error", "Debe registrarse como vendedor primero");
            return "redirect:/vendedor/registro";
        }

        // Obtener todas las categorías y unidades de medida
        List<Categoria> categorias = categoriaRepository.findAll();
        List<UnidadMedida> unidadesMedida = unidadMedidaRepository.findAll();

        // Si no hay categorías, crear algunas por defecto
        if (categorias.isEmpty()) {
            crearCategoriasPorDefecto();
            categorias = categoriaRepository.findAll();
        }

        // Si no hay unidades de medida, crear algunas por defecto
        if (unidadesMedida.isEmpty()) {
            crearUnidadesMedidaPorDefecto();
            unidadesMedida = unidadMedidaRepository.findAll();
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("categorias", categorias);
        model.addAttribute("unidadesMedida", unidadesMedida);
        model.addAttribute("producto", new Producto());

        return "agregar-producto";
    }

    /**
     * Procesa el formulario de creación de producto
     */
    @PostMapping("/productos/guardar")
    public String guardarProducto(@RequestParam("nombre") String nombre,
            @RequestParam("precio") Float precio,
            @RequestParam("stock") int stock,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("imagenUrl") String imagenUrl,
            @RequestParam("peso") Float peso,
            @RequestParam("categoriaId") int categoriaId,
            @RequestParam("unidadMedidaId") int unidadMedidaId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

            if (usuario == null) {
                redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
                return "redirect:/usuario/login";
            }

            if (!vendedorService.esVendedor(usuario.getId())) {
                redirectAttributes.addFlashAttribute("error", "Debe registrarse como vendedor primero");
                return "redirect:/vendedor/registro";
            }

            // Obtener categoría y unidad de medida
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

            UnidadMedida unidadMedida = unidadMedidaRepository.findById(unidadMedidaId)
                    .orElseThrow(() -> new IllegalArgumentException("Unidad de medida no encontrada"));

            // Crear el producto
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setStock(stock);
            producto.setDescripcion(descripcion);
            producto.setImagenUrl(imagenUrl);
            producto.setPeso(peso);
            producto.setCategoria(categoria);
            producto.setUnidadMedida(unidadMedida);
            producto.setUsuario(usuario); // Asociar al vendedor

            productoService.crearProducto(producto);

            redirectAttributes.addFlashAttribute("mensaje", "Producto creado exitosamente");
            return "redirect:/vendedor/Miproductos";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/vendedor/productos/nuevo";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear el producto: " + e.getMessage());
            return "redirect:/vendedor/productos/nuevo";
        }
    }

    /**
     * Muestra todos los productos del vendedor
     */
    @GetMapping("/Miproductos")
    public String mostrarMisProductos(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
            return "redirect:/usuario/login";
        }

        if (!vendedorService.esVendedor(usuario.getId())) {
            redirectAttributes.addFlashAttribute("error", "Debe registrarse como vendedor primero");
            return "redirect:/vendedor/registro";
        }

        // Obtener productos del vendedor
        List<Producto> productos = productoRepository.findByUsuarioId(usuario.getId());

        model.addAttribute("usuario", usuario);
        model.addAttribute("productos", productos);

        return "mis-productos";
    }

    /**
     * Muestra el formulario para editar un producto
     */
    @GetMapping("/productos/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable int id,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
            return "redirect:/usuario/login";
        }

        if (!vendedorService.esVendedor(usuario.getId())) {
            redirectAttributes.addFlashAttribute("error", "Debe ser vendedor para editar productos");
            return "redirect:/vendedor/registro";
        }

        Producto producto = productoRepository.findById(id).orElse(null);

        if (producto == null) {
            redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
            return "redirect:/vendedor/Miproductos";
        }

        if (producto.getUsuario().getId() != usuario.getId()) {
            redirectAttributes.addFlashAttribute("error", "No tienes permiso para editar este producto");
            return "redirect:/vendedor/Miproductos";
        }

        List<Categoria> categorias = categoriaRepository.findAll();
        List<UnidadMedida> unidadesMedida = unidadMedidaRepository.findAll();

        if (categorias.isEmpty()) {
            crearCategoriasPorDefecto();
            categorias = categoriaRepository.findAll();
        }

        if (unidadesMedida.isEmpty()) {
            crearUnidadesMedidaPorDefecto();
            unidadesMedida = unidadMedidaRepository.findAll();
        }

        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categorias);
        model.addAttribute("unidadesMedida", unidadesMedida);
        model.addAttribute("usuario", usuario);

        return "editar-producto";
    }

    /**
     * Procesa la actualización de un producto
     */
    @PostMapping("/productos/actualizar/{id}")
    public String actualizarProducto(@PathVariable int id,
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") Float precio,
            @RequestParam("stock") int stock,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("peso") Float peso,
            @RequestParam("imagenUrl") String imagenUrl,
            @RequestParam("categoriaId") int categoriaId,
            @RequestParam("unidadMedidaId") int unidadMedidaId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
            return "redirect:/usuario/login";
        }

        Producto producto = productoRepository.findById(id).orElse(null);

        if (producto == null) {
            redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
            return "redirect:/vendedor/Miproductos";
        }

        if (producto.getUsuario().getId() != usuario.getId()) {
            redirectAttributes.addFlashAttribute("error", "No tienes permiso para editar este producto");
            return "redirect:/vendedor/Miproductos";
        }

        try {
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setStock(stock);
            producto.setDescripcion(descripcion);
            producto.setPeso(peso);
            producto.setImagenUrl(imagenUrl);

            Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
            if (categoria != null) {
                producto.setCategoria(categoria);
            }

            UnidadMedida unidadMedida = unidadMedidaRepository.findById(unidadMedidaId).orElse(null);
            if (unidadMedida != null) {
                producto.setUnidadMedida(unidadMedida);
            }

            productoRepository.save(producto);

            redirectAttributes.addFlashAttribute("mensaje", "Producto actualizado exitosamente");
            return "redirect:/vendedor/Miproductos";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el producto: " + e.getMessage());
            return "redirect:/vendedor/productos/editar/" + id;
        }
    }

    /**
     * Elimina un producto
     */
    @PostMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable int id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

            if (usuario == null) {
                redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión primero");
                return "redirect:/usuario/login";
            }

            Producto producto = productoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            // Verificar que el producto pertenece al vendedor
            if (producto.getUsuario().getId() != usuario.getId()) {
                redirectAttributes.addFlashAttribute("error", "No tiene permiso para eliminar este producto");
                return "redirect:/vendedor/Miproductos";
            }

            productoRepository.delete(producto);
            redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado exitosamente");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el producto");
        }

        return "redirect:/vendedor/Miproductos";
    }

    // Métodos auxiliares para crear datos por defecto
    private void crearCategoriasPorDefecto() {
        String[] categorias = { "Frutas", "Verduras", "Lácteos", "Carnes y Aves", "Granos y Cereales",
                "Bebidas", "Panadería", "Condimentos", "Otros" };
        for (String nombre : categorias) {
            if (!categoriaRepository.existsByNombre(nombre)) {
                Categoria categoria = new Categoria();
                categoria.setNombre(nombre);
                categoria.setDescripcion("Categoría de " + nombre);
                categoriaRepository.save(categoria);
            }
        }
    }

    private void crearUnidadesMedidaPorDefecto() {
        String[][] unidades = {
                { "Kilogramos", "kg" },
                { "Gramos", "g" },
                { "Libras", "lb" },
                { "Litros", "L" },
                { "Mililitros", "ml" },
                { "Unidades", "un" },
                { "Docena", "doc" }
        };

        for (String[] unidad : unidades) {
            if (!unidadMedidaRepository.existsByNombre(unidad[0])) {
                UnidadMedida um = new UnidadMedida();
                um.setNombre(unidad[0]);
                um.setAbreviatura(unidad[1]);
                unidadMedidaRepository.save(um);
            }
        }
    }
}
