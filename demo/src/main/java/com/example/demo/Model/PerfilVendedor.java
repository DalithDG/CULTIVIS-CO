package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "perfil_vendedor")
public class PerfilVendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil_vendedor")
    private int id;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "razon_social", length = 100)
    private String razonSocial;

    @Column(name = "telefono_contacto", length = 20, nullable = false)
    private String telefonoContacto;

    @Column(name = "direccion_negocio", length = 200)
    private String direccionNegocio;

    @Column(name = "tipo_productos", length = 100)
    private String tipoProductos;

    @Column(name = "descripcion_negocio", length = 500)
    private String descripcionNegocio;

    @Column(name = "cuenta_bancaria", length = 50)
    private String cuentaBancaria;

    @Column(name = "banco", length = 50)
    private String banco;

    @Column(name = "verificado", nullable = false)
    private boolean verificado = false;

    // Constructores
    public PerfilVendedor() {
    }

    public PerfilVendedor(Usuario usuario, String razonSocial, String telefonoContacto, 
                         String direccionNegocio, String tipoProductos) {
        this.usuario = usuario;
        this.razonSocial = razonSocial;
        this.telefonoContacto = telefonoContacto;
        this.direccionNegocio = direccionNegocio;
        this.tipoProductos = tipoProductos;
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

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getDireccionNegocio() {
        return direccionNegocio;
    }

    public void setDireccionNegocio(String direccionNegocio) {
        this.direccionNegocio = direccionNegocio;
    }

    public String getTipoProductos() {
        return tipoProductos;
    }

    public void setTipoProductos(String tipoProductos) {
        this.tipoProductos = tipoProductos;
    }

    public String getDescripcionNegocio() {
        return descripcionNegocio;
    }

    public void setDescripcionNegocio(String descripcionNegocio) {
        this.descripcionNegocio = descripcionNegocio;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }
}