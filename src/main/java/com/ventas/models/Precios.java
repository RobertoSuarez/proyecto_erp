/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ventas.models;

/**
 *
 * @author ninat
 */
public class Precios {

    private int idprecio;
    private int idtipocliente;
    private double descuento;
    private String tipo;
    private String descripcion;

    public Precios() {
    }

    public int getIdprecio() {
        return idprecio;
    }

    public void setIdprecio(int idprecio) {
        this.idprecio = idprecio;
    }

    public int getIdtipocliente() {
        return idtipocliente;
    }

    public void setIdtipocliente(int idtipocliente) {
        this.idtipocliente = idtipocliente;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Precios(int idtipocliente, String tipo) {
        this.idtipocliente = idtipocliente;
        this.tipo = tipo;
    }

    public Precios(int idprecio, int idtipocliente, String tipo, String descripcion, double descuento) {
        this.idprecio = idprecio;
        this.idtipocliente = idtipocliente;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.descuento = descuento;
    }
    
    public Precios(int idprecio, int idtipocliente, double descuento) {
        this.idprecio = idprecio;
        this.idtipocliente = idtipocliente;
        this.descuento = descuento;
    }
    
}
