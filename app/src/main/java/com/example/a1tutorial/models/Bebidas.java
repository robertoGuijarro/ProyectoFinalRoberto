package com.example.a1tutorial.models;

import java.io.Serializable;

public class Bebidas implements Serializable {

    String idBebidas;
    String nombre;
    double precio;
    String url;
    int stock;
    int unidades;
    String estado;
    boolean alcoholicas;

    public Bebidas (){

    }

    public Bebidas(String nombre, double precio, String url, int stock, boolean alcoholicas) {
        this.nombre = nombre;
        this.precio = precio;
        this.url = url;
        this.stock = stock;
        this.alcoholicas = alcoholicas;
    }

    //comanda
    public Bebidas(String idBebidas, String nombre, double precio, String url, int stock, int unidades, String estado, boolean alcoholicas) {
        this.idBebidas = idBebidas;
        this.nombre = nombre;
        this.precio = precio;
        this.url = url;
        this.stock = stock;
        this.unidades = unidades;
        this.estado = estado;
        this.alcoholicas = alcoholicas;
    }

    public String getIdBebidas() {
        return idBebidas;
    }

    public void setIdBebidas(String idBebidas) {
        this.idBebidas = idBebidas;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isAlcoholicas() {
        return alcoholicas;
    }

    public void setAlcoholicas(boolean alcoholicas) {
        this.alcoholicas = alcoholicas;
    }
}
