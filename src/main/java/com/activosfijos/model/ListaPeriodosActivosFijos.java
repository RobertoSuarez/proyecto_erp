/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.model;

/**
 *
 * @author desta
 */
public class ListaPeriodosActivosFijos {
    private int anio;
    private int monto_total;
    private String inicio;
    private String fin;
    private double total_depreciables;
    private double total_no_depreciables;
    private double total_agotables;
    private double total_intangibles;

    public ListaPeriodosActivosFijos() {
    }

    public ListaPeriodosActivosFijos(int anio, int monto_total, String inicio, String fin, double total_depreciables, double total_no_depreciables, double total_agotables, double total_intangibles) {
        this.anio = anio;
        this.monto_total = monto_total;
        this.inicio = inicio;
        this.fin = fin;
        this.total_depreciables = total_depreciables;
        this.total_no_depreciables = total_no_depreciables;
        this.total_agotables = total_agotables;
        this.total_intangibles = total_intangibles;
    }

    public double getTotal_depreciables() {
        return total_depreciables;
    }

    public void setTotal_depreciables(double total_depreciables) {
        this.total_depreciables = total_depreciables;
    }

    public double getTotal_no_depreciables() {
        return total_no_depreciables;
    }

    public void setTotal_no_depreciables(double total_no_depreciables) {
        this.total_no_depreciables = total_no_depreciables;
    }

    public double getTotal_agotables() {
        return total_agotables;
    }

    public void setTotal_agotables(double total_agotables) {
        this.total_agotables = total_agotables;
    }

    public double getTotal_intangibles() {
        return total_intangibles;
    }

    public void setTotal_intangibles(double total_intangibles) {
        this.total_intangibles = total_intangibles;
    }


    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(int monto_total) {
        this.monto_total = monto_total;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    
    
}
