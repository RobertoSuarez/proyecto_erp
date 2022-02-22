/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.models;

public class DetalleAjuste {
    
  private int codaDetalle;
  private int codAjuste;
  private int codArticulo;
  private String nomArticulo;
  private int CantActual;
  private int idTipoAjuste;
  private String tipo;
  private int cantAjustada;
  private double costo;
  private int cantidadFinal;

    public DetalleAjuste() {
    }

    public DetalleAjuste(int codaDetalle, int codAjuste, int codArticulo, String nomArticulo, int CantActual, int idTipoAjuste, String tipo, int cantAjustada, double costo, int cantidadFinal) {
        this.codaDetalle = codaDetalle;
        this.codAjuste = codAjuste;
        this.codArticulo = codArticulo;
        this.nomArticulo = nomArticulo;
        this.CantActual = CantActual;
        this.idTipoAjuste = idTipoAjuste;
        this.tipo = tipo;
        this.cantAjustada = cantAjustada;
        this.costo = costo;
        this.cantidadFinal = cantidadFinal;
    }

    public int getCodaDetalle() {
        return codaDetalle;
    }

    public void setCodaDetalle(int codaDetalle) {
        this.codaDetalle = codaDetalle;
    }

    public int getCodAjuste() {
        return codAjuste;
    }

    public void setCodAjuste(int codAjuste) {
        this.codAjuste = codAjuste;
    }

    public int getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(int codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getNomArticulo() {
        return nomArticulo;
    }

    public void setNomArticulo(String nomArticulo) {
        this.nomArticulo = nomArticulo;
    }

    public int getCantActual() {
        return CantActual;
    }

    public void setCantActual(int CantActual) {
        this.CantActual = CantActual;
    }

    public int getIdTipoAjuste() {
        return idTipoAjuste;
    }

    public void setIdTipoAjuste(int idTipoAjuste) {
        this.idTipoAjuste = idTipoAjuste;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantAjustada() {
        return cantAjustada;
    }

    public void setCantAjustada(int cantAjustada) {
        this.cantAjustada = cantAjustada;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

   

    public int getCantidadFinal() {
        return cantidadFinal;
    }

    public void setCantidadFinal(int cantidadFinal) {
        this.cantidadFinal = cantidadFinal;
    }
  
 
  
}
