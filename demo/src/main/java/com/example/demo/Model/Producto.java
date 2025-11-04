package com.example.demo.Model;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private String descripcion;
    private String imagenUrl;
    private int categoria;
    private UnidadMedida unidadMedida;
    private Usuario usuario;
    

    public Producto() {
    }

    public Producto(int id, String nombre, double precio, int stock, String descripcion, String imagenUrl, Categoria categoria, UnidadMedida unidadMedida, Usuario usuario) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.categoria = categoria.getIdCategoria();
        this.unidadMedida = unidadMedida;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = (int) id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
    

    
}