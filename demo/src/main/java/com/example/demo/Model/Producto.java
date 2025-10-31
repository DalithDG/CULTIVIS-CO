package com.example.demo.Model;

public class Producto {
    private Integer idProductos;
    private String nombre;
    private String imagenUrl;
    private Float precio;
    private Integer stock;
    private Float peso;
    private String descripcion;
    private Integer idCategoria;
    private Integer idUser;
    private Integer idUnidad;
    private Integer idCiudad;

    public Producto() {
    }

    public Producto(Integer idProductos, String nombre, String imagenUrl, Float precio, Integer stock, 
                    Float peso, String descripcion, Integer idCategoria, Integer idUser, 
                    Integer idUnidad, Integer idCiudad) {
        this.idProductos = idProductos;
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        this.precio = precio;
        this.stock = stock;
        this.peso = peso;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.idUser = idUser;
        this.idUnidad = idUnidad;
        this.idCiudad = idCiudad;
    }

    public Integer getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(Integer idProductos) {
        this.idProductos = idProductos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }
}