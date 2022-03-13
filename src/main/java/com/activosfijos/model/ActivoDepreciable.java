/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.model;

import com.contabilidad.models.SubCuenta;

/**
 *
 * @author desta
 */
public class ActivoDepreciable {
    private int id_activo_fijo=0;
    private int depreciacion_meses=0;
    private double porcentaje_depreciacion=0;
    private double cuota_depresiacion;
    private SubCuenta subCuenta;

    
    public ActivoDepreciable() {
        subCuenta = new SubCuenta();
    }

    public ActivoDepreciable(int id_activo_fijo, int depreciacion_meses, double porcentaje_depreciacion,
                             double valor_neto_libros, SubCuenta subCuenta) {
        this.id_activo_fijo = id_activo_fijo;
        this.depreciacion_meses = depreciacion_meses;
        this.porcentaje_depreciacion = porcentaje_depreciacion;
        this.subCuenta = subCuenta;
    }

    public int getId_activo_fijo() {
        return id_activo_fijo;
    }

    public void setId_activo_fijo(int id_activo_fijo) {
        this.id_activo_fijo = id_activo_fijo;
    }

    public int getDepreciacion_meses() {
        return depreciacion_meses;
    }

    public void setDepreciacion_meses(int depreciacion_meses) {
        this.depreciacion_meses = depreciacion_meses;
    }

    public double getCuota_depresiacion() {
        return cuota_depresiacion;
    }

    public void setCuota_depresiacion(double cuota_depresiacion) {
        this.cuota_depresiacion = cuota_depresiacion;
    }

    public double getPorcentaje_depreciacion() {
        return porcentaje_depreciacion;
    }

    public void setPorcentaje_depreciacion(double porcentaje_depreciacion) {
        this.porcentaje_depreciacion = porcentaje_depreciacion;
    }

    public SubCuenta getSubCuenta() {
        return subCuenta;
    }

    public void setSubCuenta(SubCuenta subCuenta) {
        this.subCuenta = subCuenta;
    }

}
