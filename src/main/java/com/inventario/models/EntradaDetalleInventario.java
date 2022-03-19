/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.models;

import com.inventario.DAO.ArticulosInventarioDAO;
import java.io.Serializable;

/**
 *
 * @author angul
 */
public class EntradaDetalleInventario implements Serializable {

    private int idEntradaDetalle;

    private int idEntrada;

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

    public EntradaDetalleInventario() {

    }

    //Reporte data
    public EntradaDetalleInventario(int idEntradaDetalle, int idEntrada, int idArticulo, double cant, double costo, double iva, double ice, double subtotal) {
        this.idEntradaDetalle = idEntradaDetalle;
        this.idEntrada = idEntrada;
        this.idArticulo = idArticulo;
        this.cant = cant;
        this.costo = costo;
        this.iva = iva;
        this.ice = ice;
        this.subtotal = subtotal;
        this.articuloInventario = null;
    }

    public EntradaDetalleInventario(int idEntradaDetalle, int idEntrada, int idArticulo, int cant, int costo, int iva, int ice, int subtotal) {
        this.idEntradaDetalle = idEntradaDetalle;
        this.idEntrada = idEntrada;
        this.idArticulo = idArticulo;
        this.cant = cant;
        this.costo = costo;
        this.iva = iva;
        this.ice = ice;
        this.subtotal = subtotal;
        this.articuloInventario = null;
    }

    public EntradaDetalleInventario(int idEntradaDetalle, int idEntrada, int idArticulo, int cant, int costo, int iva, int ice, int subtotal, ArticulosInventario articulosInventario) {
        this.idEntradaDetalle = idEntradaDetalle;
        this.idEntrada = idEntrada;
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
        return idEntrada;
    }

    public void setIdEntrada(int idEntrada) {
        this.idEntrada = idEntrada;
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

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public ArticulosInventario getArticuloInventario() {

        return this.articuloInventario;
    }

    public void setArticuloInventario(ArticulosInventario articuloInventario) {
        this.articuloInventario = articuloInventario;
    }

}
