/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.models;

public class EncabezadoTransferencia {

    private int codemcabezado;
    private String fecha;
    private String motivo;
    private int codBodOrigen;
    private int codBodDestino;
    private String bodOrigen;
    private String bodDestino;

    public EncabezadoTransferencia() {
    }

    public EncabezadoTransferencia(int codemcabezado, String fecha, String motivo, int codBodOrigen, int codBodDestino, String bodOrigen, String bodDestino) {
        this.codemcabezado = codemcabezado;
        this.fecha = fecha;
        this.motivo = motivo;
        this.codBodOrigen = codBodOrigen;
        this.codBodDestino = codBodDestino;
        this.bodOrigen = bodOrigen;
        this.bodDestino = bodDestino;
    }

    public int getCodemcabezado() {
        return codemcabezado;
    }

    public void setCodemcabezado(int codemcabezado) {
        this.codemcabezado = codemcabezado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getCodBodOrigen() {
        return codBodOrigen;
    }

    public void setCodBodOrigen(int codBodOrigen) {
        this.codBodOrigen = codBodOrigen;
    }

    public int getCodBodDestino() {
        return codBodDestino;
    }

    public void setCodBodDestino(int codBodDestino) {
        this.codBodDestino = codBodDestino;
    }

    public String getBodOrigen() {
        return bodOrigen;
    }

    public void setBodOrigen(String bodOrigen) {
        this.bodOrigen = bodOrigen;
    }

    public String getBodDestino() {
        return bodDestino;
    }

    public void setBodDestino(String bodDestino) {
        this.bodDestino = bodDestino;
    }
    
    

}
