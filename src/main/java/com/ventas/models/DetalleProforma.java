/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.models;

import java.io.Serializable;

/**
 *
 * @author Personal
 */
public class DetalleProforma implements Serializable {
    private int idDetalle;
    private int idProforma;
    private int codigoProducto;
    private double cantidad;
    private double descuento;
    private double price;
    private ProductoVenta producto;
    private double subtotal;

    public DetalleProforma() {
    }

    public DetalleProforma(int idDetalle, int idProforma, int codigoProducto, double cantidad, double descuento, double price, ProductoVenta producto, double subtotal) {
        this.idDetalle = idDetalle;
        this.idProforma = idProforma;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.descuento = descuento;
        this.price = price;
        this.producto = producto;
        this.subtotal = subtotal;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdProforma() {
        return idProforma;
    }

    public void setIdProforma(int idProforma) {
        this.idProforma = idProforma;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductoVenta getProducto() {
        return producto;
    }

    public void setProducto(ProductoVenta producto) {
        this.producto = producto;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
}
