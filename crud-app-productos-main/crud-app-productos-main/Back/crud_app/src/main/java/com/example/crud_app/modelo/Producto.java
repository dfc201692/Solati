package com.example.crud_app.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table; // Agrega esta important


@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer cantidad_en_stock;

    public Producto(Integer id, String nombre, String descripcion, Double precio, Integer cantidad_en_stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad_en_stock = cantidad_en_stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad_en_stock() {
        return cantidad_en_stock;
    }

    public void setCantidad_en_stock(Integer cantidad_en_stock) {
        this.cantidad_en_stock = cantidad_en_stock;
    }

    public Producto() {
        // Constructor vacío necesario para JPA
    }

        /*public Producto(String nombre, String descripcion, Double precio, Integer cantidad_en_stock) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.precio = precio;
            this.cantidad_en_stock = cantidad_en_stock;
        }*/

}

