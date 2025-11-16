package com.example.demo.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "perfil_admin")
public class PerfilAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil_admin")
    private int id;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "nivel_permisos", nullable = false)
    private String nivelPermisos = "ADMIN_BASICO"; // ADMIN_BASICO, ADMIN_MODERADOR, SUPER_ADMIN

    @Column(name = "departamento_responsable", length = 100)
    private String departamentoResponsable;

    @Column(name = "fecha_nombramiento", nullable = false)
    private LocalDateTime fechaNombramiento;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    // Constructores
    public PerfilAdmin() {
        this.fechaNombramiento = LocalDateTime.now();
    }

    public PerfilAdmin(Usuario usuario, String nivelPermisos, String departamentoResponsable) {
        this.usuario = usuario;
        this.nivelPermisos = nivelPermisos;
        this.departamentoResponsable = departamentoResponsable;
        this.fechaNombramiento = LocalDateTime.now();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNivelPermisos() {
        return nivelPermisos;
    }

    public void setNivelPermisos(String nivelPermisos) {
        this.nivelPermisos = nivelPermisos;
    }

    public String getDepartamentoResponsable() {
        return departamentoResponsable;
    }

    public void setDepartamentoResponsable(String departamentoResponsable) {
        this.departamentoResponsable = departamentoResponsable;
    }

    public LocalDateTime getFechaNombramiento() {
        return fechaNombramiento;
    }

    public void setFechaNombramiento(LocalDateTime fechaNombramiento) {
        this.fechaNombramiento = fechaNombramiento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}