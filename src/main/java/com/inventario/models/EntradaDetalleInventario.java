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

    private int cant;

    private int costo;

    private double iva;

    private int ice;

    private int subtotal;

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

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public int getIce() {
        return ice;
    }

    public void setIce(int ice) {
        this.ice = ice;
    }

    public int getSubtotal() {
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
