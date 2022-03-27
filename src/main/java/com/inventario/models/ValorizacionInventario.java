/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.models;

/**
 *
 * @author angul
 */
public class ValorizacionInventario {
    int codigoTipo;
    
    String nombreTipo;
    
    int codigoProducto;
    
    String nombreProducto;
    
    String nombreUM;
    
    double cantidadProducto;
    
    double costoProducto;
    
    double valor;

    public ValorizacionInventario() {
    }

    public ValorizacionInventario(int codigoTipo, String nombreTipo, int codigoProducto, String nombreProducto, String nombreUM, double cantidadProducto, double costoProducto, double valor) {
        this.codigoTipo = codigoTipo;
        this.nombreTipo = nombreTipo;
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.nombreUM = nombreUM;
        this.cantidadProducto = cantidadProducto;
        this.costoProducto = costoProducto;
        this.valor = valor;
    }


    

    public int getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipo(int codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNombreUM() {
        return nombreUM;
    }

    public void setNombreUM(String nombreUM) {
        this.nombreUM = nombreUM;
    }

    public double getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(double cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public double getCostoProducto() {
        return costoProducto;
    }

    public void setCostoProducto(double costoProducto) {
        this.costoProducto = costoProducto;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    
    
    
    
}
