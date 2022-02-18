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
public class OrdenProduccion {

    private int codigo_orden;
    private Date fecha_emision;
    private Date fecha_fin;
    private String descripcion;
    private String estado;

    public OrdenProduccion() {
    }

    public OrdenProduccion(int codigo_orden, Date fecha_emision, Date fecha_fin, String descripcion, String estado) {
        this.codigo_orden = codigo_orden;
        this.fecha_emision = fecha_emision;
        this.fecha_fin = fecha_fin;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getCodigo_orden() {
        return codigo_orden;
    }

    public void setCodigo_orden(int codigo_orden) {
        this.codigo_orden = codigo_orden;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
