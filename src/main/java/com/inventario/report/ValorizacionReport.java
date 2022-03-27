/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.report;

/**
 *
 * @author angul
 */
public class ValorizacionReport {
    String codigoTipo;
    
    String nombreTipo;
    
    String codigoProducto;
    
    String nombreProducto;
    
    String nombreUM;
    
    int cantidadProducto;
    
    String costoProducto;
    
    double valor;

    public ValorizacionReport() {
    }

    public ValorizacionReport(String codigoTipo, String nombreTipo, String codigoProducto, String nombreProducto, String nombreUM, int cantidadProducto, String costoProducto, double valor) {
        this.codigoTipo = codigoTipo;
        this.nombreTipo = nombreTipo;
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.nombreUM = nombreUM;
        this.cantidadProducto = cantidadProducto;
        this.costoProducto = costoProducto;
        this.valor = valor;
    }

    public String getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipo(String codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
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

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public String getCostoProducto() {
        return costoProducto;
    }

    public void setCostoProducto(String costoProducto) {
        this.costoProducto = costoProducto;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    
    
}
