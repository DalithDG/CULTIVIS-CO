package com.example.demo.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Model.Roles;
import com.example.demo.Model.Usuario;
import com.example.demo.Model.PerfilAdmin;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.PerfilAdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolesRepository rolRepository;

    @Autowired
    private PerfilAdminRepository perfilAdminRepository;

    @Override
    @Transactional
    public void run(String... args) {
        try {
            logger.info("🚀 Iniciando DataLoader...");

            // Crear rol ADMIN si no existe
            Optional<Roles> rolAdminOpt = rolRepository.findByNombre("ADMIN");
            Roles rolAdmin;

            if (rolAdminOpt.isEmpty()) {
                rolAdmin = new Roles();
                rolAdmin.setNombre("ADMIN");
                rolAdmin = rolRepository.save(rolAdmin);
                logger.info("✅ Rol ADMIN creado correctamente");
                System.out.println("✅ Rol ADMIN creado correctamente");
            } else {
                rolAdmin = rolAdminOpt.get();
                logger.info("ℹ️ Rol ADMIN ya existe");
                System.out.println("ℹ️ Rol ADMIN ya existe");
            }

            // Crear usuario ADMIN si no existe
            Usuario usuarioExistente = usuarioRepository.findByEmail("admin1@demo.com");

            if (usuarioExistente == null) {
                Usuario admin = new Usuario();
                admin.setNombre("Administrador");
                admin.setEmail("admin1@demo.com");
                admin.setContrasena("admin1234"); // Contraseña en texto plano
                admin.setRol(rolAdmin);
                admin = usuarioRepository.save(admin);

                // Crear perfil de admin
                PerfilAdmin perfilAdmin = new PerfilAdmin();
                perfilAdmin.setUsuario(admin);
                perfilAdmin.setNivelPermisos("ADMIN");
                perfilAdmin.setDepartamentoResponsable("Sistemas");
                perfilAdmin.setActivo(true);
                perfilAdminRepository.save(perfilAdmin);

                logger.info("✅ Usuario ADMIN creado: admin1@demo.com / admin1234");
                System.out.println("✅ Usuario ADMIN creado:");
                System.out.println("   📧 Email: admin1@demo.com");
                System.out.println("   🔑 Contraseña: admin1234");
                System.out.println("   ✅ Perfil de Admin creado");
            } else {
                // Verificar si tiene perfil de admin
                Optional<PerfilAdmin> perfilOpt = perfilAdminRepository.findByUsuario(usuarioExistente);
                if (perfilOpt.isEmpty()) {
                    // Crear perfil de admin si no existe
                    PerfilAdmin perfilAdmin = new PerfilAdmin();
                    perfilAdmin.setUsuario(usuarioExistente);
                    perfilAdmin.setNivelPermisos("ADMIN");
                    perfilAdmin.setDepartamentoResponsable("Sistemas");
                    perfilAdmin.setActivo(true);
                    perfilAdminRepository.save(perfilAdmin);
                    logger.info("✅ Perfil de Admin creado para usuario existente");
                    System.out.println("✅ Perfil de Admin creado para usuario existente");
                } else {
                    logger.info("ℹ️ Usuario ADMIN ya existe con perfil");
                    System.out.println("ℹ️ Usuario ADMIN ya existe con perfil");
                }
            }

            logger.info("🚀 DataLoader finalizado correctamente");

        } catch (Exception e) {
            logger.error("❌ Error en DataLoader: ", e);
            System.err.println("❌ Error en DataLoader: " + e.getMessage());
            e.printStackTrace();
            // No relanzamos la excepción para que la aplicación pueda iniciar
        }
    }
}