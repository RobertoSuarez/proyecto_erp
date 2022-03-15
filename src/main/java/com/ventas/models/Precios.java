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
    private String tipoCliente;
    private String descripcionTipoCliente;
    private int idproducto = -1;
    private ProductoVenta producto;

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

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getDescripcionTipoCliente() {
        return descripcionTipoCliente;
    }

    public void setDescripcionTipoCliente(String descripcionTipoCliente) {
        this.descripcionTipoCliente = descripcionTipoCliente;
    }
    
    public Precios(int idtipocliente, String tipo) {
        this.idtipocliente = idtipocliente;
        this.tipoCliente = tipo;
    }
    

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public ProductoVenta getProducto() {
        return producto;
    }

    public void setProducto(ProductoVenta producto) {
        this.producto = producto;
    }

    public Precios(int idprecio, int idtipocliente, String tipo, String descripcion, double descuento, ProductoVenta prod) {
        this.idprecio = idprecio;
        this.idtipocliente = idtipocliente;
        this.tipoCliente = tipo;
        this.descripcionTipoCliente = descripcion;
        this.descuento = descuento;
        this.producto = prod;
    }
    
    public Precios(int idprecio, int idtipocliente, double descuento) {
        this.idprecio = idprecio;
        this.idtipocliente = idtipocliente;
        this.descuento = descuento;
    }
    
}
