/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.models;

/**
 *
 * @author angul
 */
public class EntradaDetalleInventario {



    private int idEntradaDetalle;

    private int idEntrada;

    private int idArticulo;

    private int cant;

    private int costo;

    private int iva;

    private int ice;

    private int subtotal;

    private ArticulosInventario articulosInventario;



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
        this.articulosInventario = null;
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
        this.articulosInventario = articulosInventario;
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

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
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

    public ArticulosInventario getArticulosInventario() {
        return articulosInventario;
    }

    public void setArticulosInventario(ArticulosInventario articulosInventario) {
        this.articulosInventario = articulosInventario;
    }



  

    

}
