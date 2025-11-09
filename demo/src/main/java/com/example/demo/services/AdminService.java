package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public void guardarAdmin(Admin admin) {
        if (adminRepository.existsByUserAdm(admin.getUserAdm())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        adminRepository.save(admin);
    }

    public void actualizarAdmin(Admin admin) {
        if (admin.getIdAdmin() <= 0) {
            throw new IllegalArgumentException("ID de admin inválido");
        }
        if (!adminRepository.existsById(admin.getIdAdmin())) {
            throw new IllegalArgumentException("Admin no encontrado con ID: " + admin.getIdAdmin());
        }
        adminRepository.save(admin);
    }

    public Admin obtenerAdminPorId(int id) {
        Optional<Admin> admin = adminRepository.findById(id);
        return admin.orElse(null);
    }

    public Admin iniciarSesionAdmin(String userAdm, String password) {
        Optional<Admin> adminOpt = adminRepository.findByUserAdm(userAdm);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null;
    }

    public List<Admin> obtenerTodosAdmins() {
        return adminRepository.findAll();
    }

    public boolean eliminarAdmin(int id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existeUserAdm(String userAdm) {
        return adminRepository.existsByUserAdm(userAdm);
    }
}