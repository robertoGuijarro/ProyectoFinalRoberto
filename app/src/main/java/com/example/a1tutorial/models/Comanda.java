package com.example.a1tutorial.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Comanda implements Serializable {
    String idCamarero;
    String nameCamarero;
    int mesa;
    List<Carta> platos;
    double precioTotal;
    boolean servido;

    //Escribir comanda
    public Comanda(String idCamarero, String nameCamarero, int mesa, List<Carta> platos, double precioTotal, boolean servido) {
        this.idCamarero = idCamarero;
        this.nameCamarero = nameCamarero;
        this.mesa = mesa;
        this.platos = platos;
        this.precioTotal = precioTotal;
        this.servido = servido;
    }

    public Comanda() {
    }

    public String getNameCamarero() {
        return nameCamarero;
    }

    public void setNameCamarero(String nameCamarero) {
        this.nameCamarero = nameCamarero;
    }

    public String getIdCamarero() {
        return idCamarero;
    }

    public void setIdCamarero(String idCamarero) {
        this.idCamarero = idCamarero;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public List<Carta> getPlatos() {
        return platos;
    }

    public void setPlatos(List<Carta> platos) {
        this.platos = platos;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public boolean isServido() {
        return servido;
    }

    public void setServido(boolean servido) {
        this.servido = servido;
    }
}
