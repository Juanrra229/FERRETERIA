package com.example.ferreteriabd.entities;

public class Cliente {

    private int cedula;
    private String nombre;
    private String direccion;
    private int telefono;

    public Cliente(int cedula, String nombre, String direccion, int telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getTelefono() {
        return telefono;
    }
}
