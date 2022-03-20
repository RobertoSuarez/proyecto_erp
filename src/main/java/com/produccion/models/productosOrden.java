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
public class productosOrden {

    private boolean verifica;
    private int codigoProducto;
    private int codigoFormula;
    private int codigoOrden;
    private String nombreProducto;
    private String descripcion;
    private float cantidad;
    private float precio;
    private String unidadMedida;
    private String tipoProducto;
    private char estado;

    public productosOrden() {
    }

    public productosOrden(int codigoProducto, String nombreProducto, String tipoProducto) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.tipoProducto = tipoProducto;
    }

    public productosOrden(int codigoProducto, String nombreProducto, String descripcion, float cantidad, float precio, String tipoProducto, String unidadMedida) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.tipoProducto = tipoProducto;
        this.unidadMedida = unidadMedida;
    }

    public productosOrden(int codigoProducto, int codigoOrden, String nombreProducto, float cantidad, String unidadMedida, String tipoProducto, char estado) {
        this.codigoProducto = codigoProducto;
        this.codigoOrden = codigoOrden;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.unidadMedida = unidadMedida;
        this.tipoProducto = tipoProducto;
        this.estado = estado;
    }

    public productosOrden(int codigoProducto, int codigoOrden, float cantidad, float precio, String unidadMedida, char estado) {
        this.codigoProducto = codigoProducto;
        this.codigoOrden = codigoOrden;
        this.cantidad = cantidad;
        this.precio = precio;
        this.unidadMedida = unidadMedida;
        this.estado = estado;
    }

    public productosOrden(int codigoProducto, String nombreProducto, String descripcion, String tipoProducto, String unidadMedida) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.tipoProducto = tipoProducto;
        this.unidadMedida = unidadMedida;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getCodigoOrden() {
        return codigoOrden;
    }

    public void setCodigoOrden(int codigoOrden) {
        this.codigoOrden = codigoOrden;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
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

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean isVerifica() {
        return verifica;
    }

    public void setVerifica(boolean verifica) {
        this.verifica = verifica;
    }

    public int getCodigoFormula() {
        return codigoFormula;
    }

    public void setCodigoFormula(int codigoFormula) {
        this.codigoFormula = codigoFormula;
    }

}
