package com.example.demo.services;

import com.example.demo.Model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private PerfilAdminRepository perfilAdminRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private RolesRepository rolesRepository;

    /**
     * Registra un nuevo usuario como administrador
     * Este método se usa en el registro, creando automáticamente el perfil admin
     */
    @Transactional
    public Usuario registrarAdmin(String nombre, String email, String contrasena,
            String nivelPermisos, String departamentoResponsable) {

        // Validar email duplicado
        if (usuarioService.existeEmail(email)) {
            throw new IllegalArgumentException("Este correo electrónico ya está registrado");
        }

        // Crear el usuario base (sin ciudad/departamento si no son necesarios para
        // admin)
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email.toLowerCase().trim());
        usuario.setContrasena(contrasena);

        // Guardar usuario primero
        usuario = usuarioRepository.save(usuario);

        // Crear el perfil de admin automáticamente
        PerfilAdmin perfilAdmin = new PerfilAdmin(usuario, nivelPermisos, departamentoResponsable);
        perfilAdmin = perfilAdminRepository.save(perfilAdmin);

        System.out.println("✅ Admin registrado: " + email);
        return usuario;
    }

    /**
     * Verifica si un usuario es administrador
     */
    public boolean esAdmin(int usuarioId) {
        return perfilAdminRepository.existsByUsuarioId(usuarioId);
    }

    /**
     * Obtiene el perfil de admin de un usuario
     */
    public Optional<PerfilAdmin> obtenerPerfilPorUsuarioId(int usuarioId) {
        return perfilAdminRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Actualiza el perfil de administrador
     */
    @Transactional
    public PerfilAdmin actualizarPerfil(int perfilId, String nivelPermisos,
            String departamentoResponsable, boolean activo) {

        PerfilAdmin perfil = perfilAdminRepository.findById(perfilId)
                .orElseThrow(() -> new IllegalArgumentException("Perfil de admin no encontrado"));

        if (nivelPermisos != null)
            perfil.setNivelPermisos(nivelPermisos);
        if (departamentoResponsable != null)
            perfil.setDepartamentoResponsable(departamentoResponsable);
        perfil.setActivo(activo);

        return perfilAdminRepository.save(perfil);
    }

    /**
     * Desactiva un administrador (no se elimina, solo se desactiva)
     */
    @Transactional
    public void desactivarAdmin(int usuarioId) {
        Optional<PerfilAdmin> perfilOpt = perfilAdminRepository.findByUsuarioId(usuarioId);

        perfilOpt.ifPresent(perfil -> {
            perfil.setActivo(false);
            perfilAdminRepository.save(perfil);
        });
    }

    /**
     * Login específico para administradores
     */
    public Usuario loginAdmin(String email, String contrasena) {
        Usuario usuario = usuarioService.findByEmail(email.toLowerCase().trim());

        if (usuario == null) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        if (!usuario.getContrasena().equals(contrasena)) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        // Verificar que sea admin
        if (!esAdmin(usuario.getId())) {
            throw new IllegalArgumentException("No tienes permisos de administrador");
        }

        // Verificar que el admin esté activo
        Optional<PerfilAdmin> perfilOpt = obtenerPerfilPorUsuarioId(usuario.getId());
        if (perfilOpt.isPresent() && !perfilOpt.get().isActivo()) {
            throw new IllegalArgumentException("Tu cuenta de administrador está inactiva");
        }

        return usuario;
    }

    // ==================== GESTIÓN DE USUARIOS ====================

    /**
     * Obtener todos los usuarios
     */
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Obtener usuario por ID
     */
    public Optional<Usuario> obtenerUsuarioPorId(int id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Cambiar rol de usuario
     */
    @Transactional
    public void cambiarRolUsuario(int usuarioId, String nuevoRol) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Roles rol = rolesRepository.findByNombre(nuevoRol)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

        usuario.setRol(rol);
        usuarioRepository.save(usuario);
    }

    /**
     * Eliminar usuario
     */
    @Transactional
    public void eliminarUsuario(int usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }

    // ==================== GESTIÓN DE PRODUCTOS ====================

    /**
     * Obtener todos los productos
     */
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    /**
     * Eliminar producto
     */
    @Transactional
    public void eliminarProducto(int productoId) {
        productoRepository.deleteById(productoId);
    }

    /**
     * Obtener productos por vendedor
     */
    public List<Producto> obtenerProductosPorVendedor(int vendedorId) {
        return productoRepository.findByUsuarioId(vendedorId);
    }

    // ==================== GESTIÓN DE RESEÑAS ====================

    /**
     * Obtener todas las reseñas
     */
    public List<Resena> obtenerTodasLasResenas() {
        return resenaRepository.findAll();
    }

    /**
     * Eliminar reseña
     */
    @Transactional
    public void eliminarResena(int resenaId) {
        resenaRepository.deleteById(resenaId);
    }

    /**
     * Obtener últimas reseñas
     */
    public List<Resena> obtenerUltimasResenas() {
        return resenaRepository.findTop10ByOrderByFechaDesc();
    }

    // ==================== ESTADÍSTICAS ====================

    /**
     * Obtener estadísticas del sistema
     */
    public Map<String, Object> obtenerEstadisticas() {
        Map<String, Object> estadisticas = new HashMap<>();

        // Contar usuarios
        long totalUsuarios = usuarioRepository.count();
        estadisticas.put("totalUsuarios", totalUsuarios);

        // Contar productos
        long totalProductos = productoRepository.count();
        estadisticas.put("totalProductos", totalProductos);

        // Contar pedidos
        long totalPedidos = pedidoRepository.count();
        estadisticas.put("totalPedidos", totalPedidos);

        // Contar reseñas
        long totalResenas = resenaRepository.count();
        estadisticas.put("totalResenas", totalResenas);

        // Contar usuarios por rol
        List<Roles> roles = rolesRepository.findAll();
        Map<String, Long> usuariosPorRol = new HashMap<>();
        for (Roles rol : roles) {
            long count = usuarioRepository.findAll().stream()
                    .filter(u -> u.getRol() != null && u.getRol().getId() == rol.getId())
                    .count();
            usuariosPorRol.put(rol.getNombre(), count);
        }
        estadisticas.put("usuariosPorRol", usuariosPorRol);

        return estadisticas;
    }
}