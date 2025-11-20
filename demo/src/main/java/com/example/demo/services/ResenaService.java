package com.example.demo.services;

import com.example.demo.Model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    // Crear una nueva reseña
    public Resena crearResena(Pedido pedido, Producto producto, Usuario usuario, int calificacion, String comentario) {
        // Verificar que no exista ya una reseña para este pedido y producto
        if (resenaRepository.existsByPedidoAndProducto(pedido, producto)) {
            throw new IllegalStateException("Ya existe una reseña para este producto en este pedido");
        }

        Resena resena = new Resena();
        resena.setPedido(pedido);
        resena.setProducto(producto);
        resena.setUsuario(usuario);
        resena.setCalificacion(calificacion);
        resena.setComentario(comentario);
        resena.setFecha(LocalDate.now());

        return resenaRepository.save(resena);
    }

    // Obtener reseñas por producto
    public List<Resena> obtenerResenasPorProducto(int productoId) {
        return resenaRepository.findByProductoId(productoId);
    }

    // Obtener reseñas por producto (objeto)
    public List<Resena> obtenerResenasPorProducto(Producto producto) {
        return resenaRepository.findByProducto(producto);
    }

    // Calcular promedio de calificaciones
    public double calcularPromedioCalificacion(int productoId) {
        Double promedio = resenaRepository.calcularPromedioCalificacion(productoId);
        return promedio != null ? promedio : 0.0;
    }

    // Contar reseñas de un producto
    public long contarResenas(int productoId) {
        return resenaRepository.countByProductoId(productoId);
    }

    // Verificar si un usuario puede reseñar un producto
    public boolean puedeResenar(Usuario usuario, Producto producto) {
        // Buscar pedidos del usuario que contengan el producto
        List<Pedido> pedidos = pedidoRepository.findByCliente(usuario);

        for (Pedido pedido : pedidos) {
            // Verificar si el pedido contiene el producto
            boolean contieneProducto = detallePedidoRepository.existsByPedidoAndProducto(pedido, producto);

            if (contieneProducto) {
                // Verificar si ya reseñó este producto en este pedido
                boolean yaReseno = resenaRepository.existsByPedidoAndProducto(pedido, producto);
                if (!yaReseno) {
                    return true;
                }
            }
        }

        return false;
    }

    // Obtener últimas reseñas
    public List<Resena> obtenerUltimasResenas() {
        return resenaRepository.findTop10ByOrderByFechaDesc();
    }

    // Eliminar reseña (solo el autor puede eliminarla)
    public void eliminarResena(int idResena, Usuario usuario) {
        Resena resena = resenaRepository.findById(idResena)
                .orElseThrow(() -> new IllegalStateException("Reseña no encontrada"));

        // Verificar que el usuario sea el autor de la reseña
        if (resena.getUsuario().getId() != usuario.getId()) {
            throw new IllegalStateException("No tienes permiso para eliminar esta reseña");
        }

        resenaRepository.delete(resena);
    }

    // Obtener reseña por ID
    public Optional<Resena> obtenerResenaPorId(int resenaId) {
        return resenaRepository.findById(resenaId);
    }
}
