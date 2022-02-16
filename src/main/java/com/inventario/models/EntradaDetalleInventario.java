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



    private Integer idEntradaDetalle;

    private Integer idEntrada;

    private Integer idArticulo;

    private Integer cant;

    private Short costo;

    private Integer iva;

    private Integer ice;

    private Integer subtotal;

    private ArticulosInventario articulosInventario;



    public EntradaDetalleInventario() {
    }

    public EntradaDetalleInventario(Integer idEntradaDetalle, Integer idEntrada, Integer idArticulo, Integer cant, Short costo, Integer iva, Integer ice) {
        this.idEntradaDetalle = idEntradaDetalle;
        this.idEntrada = idEntrada;
        this.idArticulo = idArticulo;
        this.cant = cant;
        this.costo = costo;
        this.iva = iva;
        this.ice = ice;
    }

    public Integer getIdEntradaDetalle() {
        return idEntradaDetalle;
    }

    public void setIdEntradaDetalle(Integer idEntradaDetalle) {
        this.idEntradaDetalle = idEntradaDetalle;
    }

    public Integer getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Integer idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Integer getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Integer getCant() {
        return cant;
    }

    public void setCant(Integer cant) {
        this.cant = cant;
    }

    public Short getCosto() {
        return costo;
    }

    public void setCosto(Short costo) {
        this.costo = costo;
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public Integer getIce() {
        return ice;
    }

    public void setIce(Integer ice) {
        this.ice = ice;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public ArticulosInventario getArticulosInventario() {
        return articulosInventario;
    }

    public void setArticulosInventario(ArticulosInventario articulosInventario) {
        this.articulosInventario = articulosInventario;
    }


    

}
