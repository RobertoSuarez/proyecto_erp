/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.models;

import java.util.Date;

/**
 *
 * @author jaime
 */
public class ReporteSimple {
   private String dato1;
   private String dato2;
   private String dato3;
   private int id;
   private double valor1;
   private double valor2;
   private double valor3;
   private Date fecha1;
   private Date fecha2;

    public ReporteSimple() {
    }

    public ReporteSimple(String dato1, int id, double valor, Date fecha1, Date fecha2) {
        this.dato1 = dato1;
        this.id = id;
        this.valor1 = valor;
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
    }

    public String getDato1() {
        return dato1;
    }

    public void setDato1(String dato1) {
        this.dato1 = dato1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor1() {
        return valor1;
    }

    public void setValor1(double valor1) {
        this.valor1 = valor1;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }
   
   
}
