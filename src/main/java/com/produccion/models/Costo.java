/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.models;

/**
 *
 * @author Alex
 */
public class Costo {
    private int codigo_costos;
    private String nombre;
    private String descripcion;
    private String tipo;

    public Costo() {
    }

    public Costo(int codigo_costos, String nombre, String descripcion, String tipo) {
        this.codigo_costos = codigo_costos;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public int getCodigo_costos() {
        return codigo_costos;
    }

    public void setCodigo_costos(int codigo_costos) {
        this.codigo_costos = codigo_costos;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
