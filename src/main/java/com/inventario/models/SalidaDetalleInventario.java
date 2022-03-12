/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.models;

/**
 *
 * @author angul
 */
public class SalidaDetalleInventario {
    private int idEntradaDetalle;

    private int idSalida;

    private int idArticulo;

    private double cant;

    private double costo;

    private double iva;

    private double ice;

    private double subtotal;

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    private String nombreProducto;
    private String nombreCategoria;

    private ArticulosInventario articuloInventario;

    public SalidaDetalleInventario() {

    }

    //Reporte data
    public SalidaDetalleInventario(int idEntradaDetalle, int idSalida, int idArticulo, double cant, double costo, double iva, double ice, double subtotal) {
        this.idEntradaDetalle = idEntradaDetalle;
        this.idSalida = idSalida;
        this.idArticulo = idArticulo;
        this.cant = cant;
        this.costo = costo;
        this.iva = iva;
        this.ice = ice;
        this.subtotal = subtotal;
        this.articuloInventario = null;
    }

    public SalidaDetalleInventario(int idEntradaDetalle, int idSalida, int idArticulo, int cant, int costo, int iva, int ice, int subtotal) {
        this.idEntradaDetalle = idEntradaDetalle;
        this.idSalida = idSalida;
        this.idArticulo = idArticulo;
        this.cant = cant;
        this.costo = costo;
        this.iva = iva;
        this.ice = ice;
        this.subtotal = subtotal;
        this.articuloInventario = null;
    }

    public SalidaDetalleInventario(int idEntradaDetalle, int idSalida, int idArticulo, int cant, int costo, int iva, int ice, int subtotal, ArticulosInventario articulosInventario) {
        this.idEntradaDetalle = idEntradaDetalle;
        this.idSalida = idSalida;
        this.idArticulo = idArticulo;
        this.cant = cant;
        this.costo = costo;
        this.iva = iva;
        this.ice = ice;
        this.subtotal = subtotal;
        this.articuloInventario = articulosInventario;
    }

    public int getIdEntradaDetalle() {
        return idEntradaDetalle;
    }

    public void setIdEntradaDetalle(int idEntradaDetalle) {
        this.idEntradaDetalle = idEntradaDetalle;
    }

    public int getIdEntrada() {
        return idSalida;
    }

    public void setIdEntrada(int idEntrada) {
        this.idSalida = idEntrada;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public double getCant() {
        return cant;
    }

    public void setCant(double cant) {
        this.cant = cant;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getIce() {
        return ice;
    }

    public void setIce(double ice) {
        this.ice = ice;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public ArticulosInventario getArticuloInventario() {

        return this.articuloInventario;
    }

    public void setArticuloInventario(ArticulosInventario articuloInventario) {
        this.articuloInventario = articuloInventario;
    }
  
}
