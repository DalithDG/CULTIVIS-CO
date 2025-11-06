package com.example.demo.Model;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "unidad_medida")
public class UnidadMedida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidad")
    private int idUnidad;

    @Column(name = "nombre", length = 20, nullable = false)
    private String nombre;

    @Column(name = "abreviatura", length = 3, nullable = false)
    private String abreviatura;

    @OneToMany(mappedBy = "unidadMedida", cascade = CascadeType.ALL)
    private List<Producto> productos;

    public UnidadMedida() {
    }

    public UnidadMedida(int idUnidad, String nombre, String abreviatura) {
        this.idUnidad = idUnidad;
        this.nombre = nombre;
        this.abreviatura = abreviatura;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}