/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.models;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author angul
 */
public class KardexEntradasSalidas implements Serializable{
    
    private int cod;
    
    private Date fecha;
    
    private String observacion;
    
    private double entradas;
    
    private double salidas;
    
    private double saldoUnidades;
    
    private double saldoIngresos;
    
    private double saldoSalidas;
    
    private double costo;
    
    

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public double getEntradas() {
        return entradas;
    }

    public void setEntradas(double entradas) {
        this.entradas = entradas;
    }

    public double getSalidas() {
        return salidas;
    }

    public void setSalidas(double salidas) {
        this.salidas = salidas;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    
    
    
}
