/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.models;

import java.io.Serializable;

public class Proforma implements Serializable {
    public int id_proforma;
    public int id_cliente;
    public int id_empleado;
    public String fecha_creacion;
    public String fecha_actualizacion;
    public String fecha_expiracion;
    public boolean proforma_terminada;
    public boolean aceptacion_cliente;
    public String estado;
    public String fecha_autorizacion;
    public float base12;
    public float base0;
    public float base_excento_iva;
    public float iva12;
    public float ice;
    public float totalproforma;
    public int detalleproformacodigo;

    public Proforma() {
    }

    public int getId_proforma() {
        return id_proforma;
    }

    public void setId_proforma(int id_proforma) {
        this.id_proforma = id_proforma;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(String fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public String getFecha_expiracion() {
        return fecha_expiracion;
    }

    public void setFecha_expiracion(String fecha_expiracion) {
        this.fecha_expiracion = fecha_expiracion;
    }

    public boolean isProforma_terminada() {
        return proforma_terminada;
    }

    public void setProforma_terminada(boolean proforma_terminada) {
        this.proforma_terminada = proforma_terminada;
    }

    public boolean isAceptacion_cliente() {
        return aceptacion_cliente;
    }

    public void setAceptacion_cliente(boolean aceptacion_cliente) {
        this.aceptacion_cliente = aceptacion_cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_autorizacion() {
        return fecha_autorizacion;
    }

    public void setFecha_autorizacion(String fecha_autorizacion) {
        this.fecha_autorizacion = fecha_autorizacion;
    }

    public float getBase12() {
        return base12;
    }

    public void setBase12(float base12) {
        this.base12 = base12;
    }

    public float getBase0() {
        return base0;
    }

    public void setBase0(float base0) {
        this.base0 = base0;
    }

    public float getBase_excento_iva() {
        return base_excento_iva;
    }

    public void setBase_excento_iva(float base_excento_iva) {
        this.base_excento_iva = base_excento_iva;
    }

    public float getIva12() {
        return iva12;
    }

    public void setIva12(float iva12) {
        this.iva12 = iva12;
    }

    public float getIce() {
        return ice;
    }

    public void setIce(float ice) {
        this.ice = ice;
    }
    
    public float getTotalproforma() {
        return totalproforma;
    }

    public void setTotalproforma(float totalproforma) {
        this.totalproforma = totalproforma;
    }

    public int getDetalleproformacodigo() {
        return detalleproformacodigo;
    }

    public void setDetalleproformacodigo(int detalleproformacodigo) {
        this.detalleproformacodigo = detalleproformacodigo;
    }
    
    
}
