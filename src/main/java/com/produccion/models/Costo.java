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
    private String identificador;
    private float costo;

    public Costo() {
    }

    public Costo(int codigo_costos, String nombre, String descripcion, String tipo, String identificador, float costo) {
        this.codigo_costos = codigo_costos;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.identificador = identificador;
        this.costo = costo;
    }
    

    public Costo(int codigo_costos, String nombre, String descripcion, String tipo, String identificador) {
        this.codigo_costos = codigo_costos;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.identificador = identificador;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
    
}
