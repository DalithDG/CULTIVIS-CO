package com.example.demo.services;

import java.util.List;

import com.example.demo.Model.Usuario;
import com.example.demo.repository.UsuarioRepositoryImpl;

public class UsuarioService implements IUsuarioService {

    private UsuarioRepositoryImpl repositorio = new UsuarioRepositoryImpl();

    @Override
    public void guardarUsuario(Usuario usuario) {
        repositorio.guardarUsuario(usuario);
   }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        repositorio.actualizarUsuario(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorId(int id) {
        return repositorio.encontrarPorId(id);
    }

    @Override
    public Usuario iniciarSesion(String correo, String contraseña) {
        Usuario usuario = repositorio.encontrarPorEmail(correo);
        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            return usuario;
        }
        return null;
    }

    @Override
    public List<Usuario> obtenerTodos() {
         return repositorio.encontrarTodosUsuarios();
    }

    @Override
    public boolean eliminarUsuario(int id) {
        return repositorio.eliminarUsuario(id);
    }
    
}
