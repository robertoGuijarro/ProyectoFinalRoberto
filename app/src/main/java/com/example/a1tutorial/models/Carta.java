package com.example.a1tutorial.models;

import java.io.Serializable;

public class Carta implements Serializable {
    String idComida;
    String nombre;
    double precio;
    String tipo;
    String url;
    int stock;
    int unidades;

    public Carta() {
    }

    public Carta(String nombre, double precio, String tipo, String url, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.url = url;
        this.stock = stock;
    }

    public Carta(String nombre, double precio, int unidades) {
        this.nombre = nombre;
        this.precio = precio;
        this.unidades = unidades;
    }
//Comanda
    public Carta(String idComida, String nombre, double precio, int unidades) {
        this.idComida = idComida;
        this.nombre = nombre;
        this.precio = precio;
        this.unidades = unidades;
    }

    public Carta(String idComida, int unidades) {
        this.idComida = idComida;
        this.unidades = unidades;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public void setIdComida(String idComida) {
        this.idComida = idComida;
    }
    public String getIdComida() {
        return idComida;
    }
    @Override
    public String toString() {
        return "Carta{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", tipo='" + tipo + '\'' +
                ", url='" + url + '\'' +
                ", stock=" + stock +
                '}';
    }
}

