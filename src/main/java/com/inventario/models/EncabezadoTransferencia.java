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
    private boolean verifica;
    private String bodDestino;
    
    private int cod_detalle;
    private int cod_articulo;
    private String nombre_articulo;
    private int cantidadTrasnferida;
    private int existencia;
    private double costo;

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

    public boolean isVerifica() {
        return verifica;
    }

    public void setVerifica(boolean verifica) {
        this.verifica = verifica;
    }

    public int getCod_detalle() {
        return cod_detalle;
    }

    public void setCod_detalle(int cod_detalle) {
        this.cod_detalle = cod_detalle;
    }

    public int getCod_articulo() {
        return cod_articulo;
    }

    public void setCod_articulo(int cod_articulo) {
        this.cod_articulo = cod_articulo;
    }

    public String getNombre_articulo() {
        return nombre_articulo;
    }

    public void setNombre_articulo(String nombre_articulo) {
        this.nombre_articulo = nombre_articulo;
    }

    public int getCantidadTrasnferida() {
        return cantidadTrasnferida;
    }

    public void setCantidadTrasnferida(int cantidadTrasnferida) {
        this.cantidadTrasnferida = cantidadTrasnferida;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    
    

}
