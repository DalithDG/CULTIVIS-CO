package com.example.demo.services;

import com.example.demo.Model.PerfilVendedor;
import com.example.demo.Model.Usuario;
import com.example.demo.repository.PerfilVendedorRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VendedorService {

    @Autowired
    private PerfilVendedorRepository perfilVendedorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Crea un perfil de vendedor para un usuario existente
     */
    @Transactional
    public PerfilVendedor crearPerfilVendedor(int usuarioId, String razonSocial, 
                                              String telefonoContacto, String direccionNegocio,
                                              String tipoProductos, String descripcionNegocio,
                                              String cuentaBancaria, String banco) {
        
        // Validar que el usuario existe
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Validar que el usuario no tiene ya un perfil de vendedor
        if (perfilVendedorRepository.existsByUsuarioId(usuarioId)) {
            throw new IllegalArgumentException("Este usuario ya tiene un perfil de vendedor");
        }

        // Validaciones de campos obligatorios
        if (telefonoContacto == null || telefonoContacto.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono de contacto es obligatorio");
        }

        // Crear el perfil
        PerfilVendedor perfil = new PerfilVendedor();
        perfil.setUsuario(usuario);
        perfil.setRazonSocial(razonSocial);
        perfil.setTelefonoContacto(telefonoContacto);
        perfil.setDireccionNegocio(direccionNegocio);
        perfil.setTipoProductos(tipoProductos);
        perfil.setDescripcionNegocio(descripcionNegocio);
        perfil.setCuentaBancaria(cuentaBancaria);
        perfil.setBanco(banco);
        perfil.setVerificado(false); // Por defecto no verificado

        return perfilVendedorRepository.save(perfil);
    }

    /**
     * Obtiene el perfil de vendedor de un usuario
     */
    public Optional<PerfilVendedor> obtenerPerfilPorUsuarioId(int usuarioId) {
        return perfilVendedorRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Verifica si un usuario es vendedor
     */
    public boolean esVendedor(int usuarioId) {
        return perfilVendedorRepository.existsByUsuarioId(usuarioId);
    }

    /**
     * Actualiza el perfil de vendedor
     */
    @Transactional
    public PerfilVendedor actualizarPerfil(int perfilId, String razonSocial,
                                          String telefonoContacto, String direccionNegocio,
                                          String tipoProductos, String descripcionNegocio,
                                          String cuentaBancaria, String banco) {
        
        PerfilVendedor perfil = perfilVendedorRepository.findById(perfilId)
            .orElseThrow(() -> new IllegalArgumentException("Perfil no encontrado"));

        if (razonSocial != null) perfil.setRazonSocial(razonSocial);
        if (telefonoContacto != null) perfil.setTelefonoContacto(telefonoContacto);
        if (direccionNegocio != null) perfil.setDireccionNegocio(direccionNegocio);
        if (tipoProductos != null) perfil.setTipoProductos(tipoProductos);
        if (descripcionNegocio != null) perfil.setDescripcionNegocio(descripcionNegocio);
        if (cuentaBancaria != null) perfil.setCuentaBancaria(cuentaBancaria);
        if (banco != null) perfil.setBanco(banco);

        return perfilVendedorRepository.save(perfil);
    }

    /**
     * Elimina el perfil de vendedor (el usuario deja de ser vendedor)
     */
    @Transactional
    public void eliminarPerfilVendedor(int usuarioId) {
        Optional<PerfilVendedor> perfil = perfilVendedorRepository.findByUsuarioId(usuarioId);
        perfil.ifPresent(perfilVendedorRepository::delete);
    }
}