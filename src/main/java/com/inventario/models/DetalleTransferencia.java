/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.models;

import java.io.Serializable;

public class DetalleTransferencia implements Serializable {

   private boolean verifica;
   private int cod_transferencia;
   private int cod_articulo;
   private int cant;
   private double costo;
   private String nombreArticulo;
   private char estado;
    public DetalleTransferencia() {
    }

    public DetalleTransferencia(int cod_transferencia, int cod_articulo, int cant, double costo) {
        this.cod_transferencia = cod_transferencia;
        this.cod_articulo = cod_articulo;
        this.cant = cant;
        this.costo = costo;
    }

    public DetalleTransferencia(int cod_articulo, int cant, double costo, String nombreArticulo) {
        this.cod_articulo = cod_articulo;
        this.cant = cant;
        this.costo = costo;
        this.nombreArticulo = nombreArticulo;
    }
    
    

    

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

   
   

    public int getCod_transferencia() {
        return cod_transferencia;
    }

    public void setCod_transferencia(int cod_transferencia) {
        this.cod_transferencia = cod_transferencia;
    }

    public int getCod_articulo() {
        return cod_articulo;
    }

    public void setCod_articulo(int cod_articulo) {
        this.cod_articulo = cod_articulo;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }


    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public boolean isVerifica() {
        return verifica;
    }

    public void setVerifica(boolean verifica) {
        this.verifica = verifica;
    }
    
   
   

}
