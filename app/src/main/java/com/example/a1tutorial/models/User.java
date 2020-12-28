package com.example.a1tutorial.models;

import java.io.Serializable;

public class User implements Serializable {
    String id;
    String name;
    String email;
    String oficio;
    String pass;
    Long fechaCreacion;

    public User() {

    }

    public User(String email, String pass){
        this.email = email;
        this.pass = pass;
    }

    public User( String name, String email, String oficio, String pass) {
        this.name = name;
        this.email = email;
        this.oficio = oficio;
        this.pass = pass;
        //fechaCreacion =
    }

    public User(String id, String name, String email, String oficio, String pass) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.oficio = oficio;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
