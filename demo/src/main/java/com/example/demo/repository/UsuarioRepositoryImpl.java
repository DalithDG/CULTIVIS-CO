package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Model.Usuario;

public class UsuarioRepositoryImpl implements IUsuarioRepository {

    public List<Usuario> ListadoUsuarios = new ArrayList<>();

    @Override
    public void guardarUsuario(Usuario usuario) {
        ListadoUsuarios.add(usuario);
        
    }

    @Override
    public Usuario encontrarPorId(int id) {
        for (Usuario usuario : ListadoUsuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public Usuario encontrarPorEmail(String email) {
        for( Usuario usuario : ListadoUsuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> encontrarTodosUsuarios() {
        return ListadoUsuarios;
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() <= 0) {
            throw new IllegalArgumentException("Usuario o ID inválido");
        }

        Usuario existente = encontrarPorId(usuario.getId());
        if (existente != null) {
            ListadoUsuarios.removeIf(u -> u.getId() == usuario.getId());
            ListadoUsuarios.add(usuario);
        } else {
            throw new IllegalArgumentException("No se encontró el usuario a actualizar");
        }
    }

    @Override
    public boolean eliminarUsuario(int id) {
        return ListadoUsuarios.removeIf(u -> u.getId() == id);
    }
    
}
