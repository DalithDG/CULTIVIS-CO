package com.example.demo.services;

import java.util.List;
import com.example.demo.Model.Usuario;

public interface IUsuarioService {
    
    // Métodos de autenticación
    Usuario iniciarSesion(String correo, String contrasena);
    
    // Métodos de búsqueda
    Usuario obtenerUsuarioPorId(int id);
    Usuario findByEmail(String email);  // ✅ Agregado
    Usuario findUsuario(String email);
    List<Usuario> obtenerTodos();
    
    // Métodos de modificación
    void actualizarUsuario(Usuario usuario);
    Usuario save(Usuario usuario);
    boolean eliminarUsuario(int id);
    
    // Validaciones
    boolean existeEmail(String email);
}