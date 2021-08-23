/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.models;

import java.io.Serializable;

/**
 *
 * @author Andres
 */
public class DetalleVenta implements Serializable {
    private int iddetalleventa;
    private int idventa;
    private int codprincipal;
    private double cantidad;
    private double descuento;
    private double precio;
    private Producto producto;
    private double subTotal;

    public DetalleVenta() {
        
    }

    public DetalleVenta(int iddetalleventa, int idventa, int codprincipal, double cantidad, double descuento, double precio, Producto prod) {
        this.iddetalleventa = iddetalleventa;
        this.idventa = idventa;
        this.codprincipal = codprincipal;
        this.cantidad = cantidad;
        this.descuento = descuento;
        this.precio = precio;
        this.producto = prod;
    }

    public int getIddetalleventa() {
        return iddetalleventa;
    }

    public void setIddetalleventa(int iddetalleventa) {
        this.iddetalleventa = iddetalleventa;
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public int getCodprincipal() {
        return codprincipal;
    }

    public void setCodprincipal(int codprincipal) {
        this.codprincipal = codprincipal;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
    
}
