package com.example.demo.services;

import com.example.demo.Model.PerfilAdmin;
import com.example.demo.Model.Usuario;
import com.example.demo.repository.PerfilAdminRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private PerfilAdminRepository perfilAdminRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

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

        // Crear el usuario base (sin ciudad/departamento si no son necesarios para admin)
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

        if (nivelPermisos != null) perfil.setNivelPermisos(nivelPermisos);
        if (departamentoResponsable != null) perfil.setDepartamentoResponsable(departamentoResponsable);
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
}