/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.controllers;

import java.time.LocalDate;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author elect
 */

@ManagedBean(name = "estadoDeCuentaMB")
@RequestScoped
public class EstadoDeCuentaMB {

    /**
     * Creates a new instance of EstadoDeCuentaMB
     */
    private String proveedor;
    private LocalDate desde;
    private LocalDate hasta;
    private boolean sinfechas;
    
    public EstadoDeCuentaMB() {
        desde = LocalDate.now();
        hasta = LocalDate.now().plusDays(5);
        sinfechas = true;
    }
    
    public LocalDate getDesde() {
        return desde;
    }

    public void setDesde(LocalDate desde) {
        this.desde = desde;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public LocalDate getHasta() {
        return hasta;
    }

    public void setHasta(LocalDate hasta) {
        this.hasta = hasta;
    }

    public boolean isSinfechas() {
        return sinfechas;
    }

    public void setSinfechas(boolean sinfechas) {
        this.sinfechas = sinfechas;
    }

    
}
