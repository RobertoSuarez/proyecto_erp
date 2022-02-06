/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.models;

/**
 *
 * @author HP
 */
public class FormulaMateriales {

    private String nombre;
    private String descripcion;
    private int codigFormula;
    private int codigoProducto;
    private float cantidad;
    private String unidadMedida;
    private float precio;

    public FormulaMateriales() {
    }

    public FormulaMateriales(int codigFormula, int codigoProducto, float precio) {
        this.codigFormula = codigFormula;
        this.codigoProducto = codigoProducto;
        this.precio = precio;
    }

    public FormulaMateriales(String nombre, String descripcion, int codigFormula, int codigoProducto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigFormula = codigFormula;
        this.codigoProducto = codigoProducto;
    }

    public FormulaMateriales(String nombre, String descripcion, int codigFormula, int codigoProducto, float precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigFormula = codigFormula;
        this.codigoProducto = codigoProducto;
        this.precio = precio;
    }

    public FormulaMateriales(int codigFormula, int codigoProducto, float cantidad, String unidadMedida, float precio) {
        this.codigFormula = codigFormula;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.unidadMedida = unidadMedida;
        this.precio = precio;
    }

    public FormulaMateriales(String nombre, String descripcion, int codigFormula, int codigoProducto, float cantidad, String unidadMedida, float precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigFormula = codigFormula;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.unidadMedida = unidadMedida;
        this.precio = precio;
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

    public int getCodigFormula() {
        return codigFormula;
    }

    public void setCodigFormula(int codigFormula) {
        this.codigFormula = codigFormula;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

}
