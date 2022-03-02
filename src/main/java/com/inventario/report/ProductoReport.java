/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.report;

import java.io.Serializable;

/**
 *
 * @author angul
 */
public class ProductoReport implements Serializable{
    
    String codigo;
    
    String producto;
    
    int cantidad;
    
    int costo;
    
    int total;

    public ProductoReport(String codigo, String producto, int cantidad, int costo, int total) {
        this.codigo = codigo;
        this.producto = producto;
        this.cantidad = cantidad;
        this.costo = costo;
        this.total = total;
    }

    public ProductoReport() {
    }
    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
    
    
}
