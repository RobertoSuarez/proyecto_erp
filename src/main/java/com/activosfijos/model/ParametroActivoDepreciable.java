/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.activosfijos.model;

import com.contabilidad.models.SubCuenta;
import java.io.Serializable;

/**
 *
 * @author cturriagos
 */
public class ParametroActivoDepreciable implements Serializable {
    private int idParametro;
    private SubCuenta activoDepreciable;
    private int anios;
    private double porcentajeAnual;

    public ParametroActivoDepreciable() {
        this.idParametro = 0;
        this.activoDepreciable = new SubCuenta();
        this.anios = 0;
        this.porcentajeAnual = 0;
    }

    public ParametroActivoDepreciable(int idParametro, SubCuenta activoDepreciable, int anios, double porcentajeAnual) {
        this.idParametro = idParametro;
        this.activoDepreciable = activoDepreciable;
        this.anios = anios;
        this.porcentajeAnual = porcentajeAnual;
    }

    public int getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(int idParametro) {
        this.idParametro = idParametro;
    }

    public SubCuenta getActivoDepreciable() {
        return activoDepreciable;
    }

    public void setActivoDepreciable(SubCuenta activoDepreciable) {
        this.activoDepreciable = activoDepreciable;
    }

    public int getAnios() {
        return anios;
    }

    public void setAnios(int anios) {
        this.anios = anios;
    }

    public double getPorcentajeAnual() {
        return porcentajeAnual;
    }

    public void setPorcentajeAnual(double porcentajeAnual) {
        this.porcentajeAnual = porcentajeAnual;
    }
}
