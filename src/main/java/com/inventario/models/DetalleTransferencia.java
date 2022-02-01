/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.models;

public class DetalleTransferencia {

   private int idDetalle;
   private int cod_transferencia;
   private int cod_articulo;
   private int cant;

    public DetalleTransferencia() {
    }

    public DetalleTransferencia(int idDetalle, int cod_transferencia, int cod_articulo, int cant) {
        this.idDetalle = idDetalle;
        this.cod_transferencia = cod_transferencia;
        this.cod_articulo = cod_articulo;
        this.cant = cant;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
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
   
   

}
