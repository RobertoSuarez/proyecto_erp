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
public class ArticuloFormula {

    private int id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String tipo;
    private float costo;
    private float cantidad;
    private float cantidadMaxima;
    private String UnidadMedida;
    private float total;
    private int idSubcuenta;
    private String subCuenta;

    public ArticuloFormula(int id, String nombre, String categoria, String descripcion, String tipo, float costo, float cantidad, float cantidadMaxima) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.costo = costo;
        this.cantidad = cantidad;
        this.cantidadMaxima = cantidadMaxima;
    }

    public ArticuloFormula(int id, String nombre, String descripcion, float costo, float cantidad, float total) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.cantidad = cantidad;
        this.total = total;
    }

    public ArticuloFormula(int id, String nombre, String descripcion, String tipo, float cantidad, 
            String UnidadMedida,float costo,
            int idSubcuenta,String subCuenta) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.UnidadMedida = UnidadMedida;
        this.costo=costo;
        this.idSubcuenta=idSubcuenta;
        this.subCuenta=subCuenta;
    }

    public ArticuloFormula(int id, float cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public ArticuloFormula() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getCantidadMaxima() {
        return cantidadMaxima;
    }

    public void setCantidadMaxima(float cantidadMaxima) {
        this.cantidadMaxima = cantidadMaxima;
    }

    public String getUnidadMedida() {
        return UnidadMedida;
    }

    public void setUnidadMedida(String UnidadMedida) {
        this.UnidadMedida = UnidadMedida;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getIdSubcuenta() {
        return idSubcuenta;
    }

    public void setIdSubcuenta(int idSubcuenta) {
        this.idSubcuenta = idSubcuenta;
    }

    public String getSubCuenta() {
        return subCuenta;
    }

    public void setSubCuenta(String subCuenta) {
        this.subCuenta = subCuenta;
    }

}
