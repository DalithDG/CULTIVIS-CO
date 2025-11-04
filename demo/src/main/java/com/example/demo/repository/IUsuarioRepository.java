package com.example.demo.repository;

import java.util.List;

import com.example.demo.Model.Usuario;

public interface IUsuarioRepository {

    void guardarUsuario(Usuario usuario);
    Usuario encontrarPorId(int id);
    Usuario encontrarPorEmail(String email);
    List<Usuario> encontrarTodosUsuarios();
    void actualizarUsuario(Usuario usuario);
    boolean eliminarUsuario(int id);   
    
} 