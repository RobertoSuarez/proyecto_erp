/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.models;

import java.util.Date;

/**
 *
 * @author HP
 */
public class SolicitudOrden {

    private int codigo_orden;
    private String codigoSecundario;
    private Date fecha_orden;
    private Date fecha_fin;
    private String descripcion;
    private char estado;

    public SolicitudOrden() {
    }

    public SolicitudOrden(int codigo_orden, Date fecha_orden, Date fecha_fin, String escripcion, char estado) {
        this.codigo_orden = codigo_orden;
        this.fecha_orden = fecha_orden;
        this.fecha_fin = fecha_fin;
        this.descripcion = escripcion;
        this.estado = estado;
    }

    public int getCodigo_orden() {
        return codigo_orden;
    }

    public void setCodigo_orden(int codigo_orden) {
        this.codigo_orden = codigo_orden;
    }

    public Date getFecha_orden() {
        return fecha_orden;
    }

    public void setFecha_orden(Date fecha_orden) {
        this.fecha_orden = fecha_orden;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public String getCodigoSecundario() {
        return codigoSecundario;
    }

    public void setCodigoSecundario(String codigoSecundario) {
        this.codigoSecundario = codigoSecundario;
    }
    

}
