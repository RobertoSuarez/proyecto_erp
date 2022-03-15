/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.activosfijos.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cturriagos
 */
public class DepreciacionActivosFijos implements Serializable {
    private String codigo;
    private int mes;
    private double depreciacion, saldo;
    private Date fecha;
    private ListaDepreciable listaDepreciable;

    public DepreciacionActivosFijos() {
        this.codigo = "";
        this.mes = 0;
        this.depreciacion = 0;
        this.saldo = 0;
        this.fecha = new Date();
        this.listaDepreciable = new ListaDepreciable();
    }

    public DepreciacionActivosFijos(int mes, double depreciacion, double saldo, Date fecha, ListaDepreciable listaDepreciable) {
        this.codigo = "DEP-" + listaDepreciable.getIdDepreciable() + "-" + mes;
        this.mes = mes;
        this.depreciacion = depreciacion;
        this.saldo = saldo;
        this.fecha = fecha;
        this.listaDepreciable = listaDepreciable;
    }

    public DepreciacionActivosFijos(String codigo, int mes, double depreciacion, double saldo, Date fecha, ListaDepreciable listaDepreciable) {
        this.codigo = codigo;
        this.mes = mes;
        this.depreciacion = depreciacion;
        this.saldo = saldo;
        this.fecha = fecha;
        this.listaDepreciable = listaDepreciable;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public double getDepreciacion() {
        return depreciacion;
    }

    public void setDepreciacion(double depreciacion) {
        this.depreciacion = depreciacion;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ListaDepreciable getListaDepreciable() {
        return listaDepreciable;
    }

    public void setListaDepreciable(ListaDepreciable listaDepreciable) {
        this.listaDepreciable = listaDepreciable;
    }
}
