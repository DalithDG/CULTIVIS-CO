package com.example.demo.Model;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String tipo;
    private String contraseña;
    private Ciudad ciudad;

    public Usuario() {
    }


    public Usuario(int id, String nombre, String email, String tipo, String contraseña, Ciudad ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.tipo = tipo;
        this.contraseña = contraseña;
        this.ciudad = ciudad;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getContraseña() {
        return contraseña;
    }


    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }


    public Ciudad getCiudad() {
        return ciudad;
    }


    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }


    

    

    
    
}

