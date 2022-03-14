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
    private int codigo;
    private double cantidad;
    private double descuento;
    private double precio;
    private ProductoVenta producto;
    private double subTotal;
    private String nombreProducto;

    public DetalleVenta() {
        
    }

    public DetalleVenta(int iddetalleventa, int idventa, int codprincipal, double cantidad, double descuento, double precio, ProductoVenta prod) {
        this.iddetalleventa = iddetalleventa;
        this.idventa = idventa;
        this.codigo = codprincipal;
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

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public ProductoVenta getProducto() {
        return producto;
    }

    public void setProducto(ProductoVenta producto) {
        this.producto = producto;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
       
}
