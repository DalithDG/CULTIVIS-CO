package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.Producto;
import com.example.demo.services.ProductoServiceImpl;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoServiceImpl productoService;

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodosProductos() {
        try {
            List<Producto> productos = productoService.listarProductos();
            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id) {
        try {
            Producto producto = productoService.buscarPorId(id);
            if (producto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear nuevo producto
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.crearProducto(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el producto: " + e.getMessage());
        }
    }

    // Actualizar producto existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable int id, @RequestBody Producto producto) {
        try {
            producto.setId(id);
            Producto productoActualizado = productoService.actualizarProducto(producto);
            return ResponseEntity.ok(productoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el producto: " + e.getMessage());
        }
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable int id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.ok().body("Producto eliminado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el producto: " + e.getMessage());
        }
    }

    // Buscar productos por categoría
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Producto>> buscarPorCategoria(@PathVariable int categoriaId) {
        try {
            List<Producto> productos = productoService.buscarPorCategoria(categoriaId);
            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar productos por usuario/vendedor
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Producto>> buscarPorUsuario(@PathVariable int userId) {
        try {
            List<Producto> productos = productoService.buscarPorUsuario(userId);
            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar productos por nombre (búsqueda parcial)
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        try {
            List<Producto> productos = productoService.buscarPorNombre(nombre);
            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar productos con stock disponible
    @GetMapping("/disponibles")
    public ResponseEntity<List<Producto>> buscarProductosDisponibles() {
        try {
            List<Producto> productos = productoService.buscarConStock();
            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar productos por rango de precio
    @GetMapping("/rango-precio")
    public ResponseEntity<List<Producto>> buscarPorRangoPrecio(
            @RequestParam Float precioMin, 
            @RequestParam Float precioMax) {
        try {
            List<Producto> productos = productoService.buscarPorRangoPrecio(precioMin, precioMax);
            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Actualizar stock de un producto
    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> actualizarStock(@PathVariable int id, @RequestParam int nuevoStock) {
        try {
            Producto producto = productoService.buscarPorId(id);
            if (producto == null) {
                return ResponseEntity.notFound().build();
            }
            producto.setStock(nuevoStock);
            Producto productoActualizado = productoService.actualizarProducto(producto);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el stock: " + e.getMessage());
        }
    }
}