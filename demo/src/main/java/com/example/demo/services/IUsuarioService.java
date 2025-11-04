package com.example.demo.services;

import java.util.List;

import com.example.demo.Model.Usuario;

public interface IUsuarioService {

    void guardarUsuario(Usuario usuario);
    void actualizarUsuario(Usuario usuario);
    Usuario obtenerUsuarioPorId(int id);
    Usuario iniciarSesion(String correo, String contraseña);
    List<Usuario> obtenerTodos();
    boolean eliminarUsuario(int id);
    
} 
