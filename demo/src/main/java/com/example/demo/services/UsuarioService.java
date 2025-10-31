package com.example.demo.services;

import com.example.demo.model.Usuario;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private List<Usuario> usuarios = new ArrayList<>();

    public String registrarUsuario(Usuario nuevo) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(nuevo.getEmail())) {
                return "El usuario ya está registrado";
            }
        }
        usuarios.add(nuevo);
        return "Registro exitoso";
    }

    public String iniciarSesion(String email, String contraseña) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getContraseña().equals(contraseña)) {
                return "Inicio de sesión exitoso, bienvenido " + u.getNombre();
            }
        }
        return "Credenciales incorrectas";
    }

    public List<Usuario> listarUsuarios() {
        return usuarios;
    }
}
