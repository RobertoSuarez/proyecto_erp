/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.models;

import java.io.Serializable;

/**
 *
 * @author angul
 */
public class MovimientoInventario implements Serializable {
    
    int idSubcuenta;
    
    double debe;
    
    double haber;
    
    String tipoMovimiento;

    public MovimientoInventario() {
    }

    public MovimientoInventario(int idSubcuenta, double debe, double haber, String tipoMovimiento) {
        this.idSubcuenta = idSubcuenta;
        this.debe = debe;
        this.haber = haber;
        this.tipoMovimiento = tipoMovimiento;
    }

    
    
    public int getIdSubcuenta() {
        return idSubcuenta;
    }

    public void setIdSubcuenta(int idSubcuenta) {
        this.idSubcuenta = idSubcuenta;
    }

    public double getDebe() {
        return debe;
    }

    public void setDebe(double debe) {
        this.debe = debe;
    }

    public double getHaber() {
        return haber;
    }

    public void setHaber(double haber) {
        this.haber = haber;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }
    
    
}
