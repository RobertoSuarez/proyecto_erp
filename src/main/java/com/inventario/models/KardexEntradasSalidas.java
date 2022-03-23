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

    private String codigo;
            
    private int cod;
    
    private Date fecha;
    
    private String observacion;
    
    private double entradas;
    
    private double salidas;
    
    private double saldoUnidades;
    
    private double saldoIngresos;
    
    private double saldoSalidas;
    
    private double saldo;
    
    private double costo;

    public KardexEntradasSalidas() {
    }

  
    
    
    public KardexEntradasSalidas(int cod, Date fecha, String observacion, double entradas, double salidas, double saldoUnidades, double saldoIngresos, double saldoSalidas, double saldo, double costo) {
        this.cod = cod;
        this.fecha = fecha;
        this.observacion = observacion;
        this.entradas = entradas;
        this.salidas = salidas;
        this.saldoUnidades = saldoUnidades;
        this.saldoIngresos = saldoIngresos;
        this.saldoSalidas = saldoSalidas;
        this.saldo = saldo;
        this.costo = costo;
    }
    
    
        public KardexEntradasSalidas(int cod, Date fecha, String observacion, double entradas, double salidas, double costo) {
        this.cod = cod;
        this.fecha = fecha;
        this.observacion = observacion;
        this.entradas = entradas;
        this.salidas = salidas;
        this.costo = costo;
    }
    
    
    

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

    public double getSaldoUnidades() {
        return saldoUnidades;
    }

    public void setSaldoUnidades(double saldoUnidades) {
        this.saldoUnidades = saldoUnidades;
    }

    public double getSaldoIngresos() {
        return saldoIngresos;
    }

    public void setSaldoIngresos(double saldoIngresos) {
        this.saldoIngresos = saldoIngresos;
    }

    public double getSaldoSalidas() {
        return saldoSalidas;
    }

    public void setSaldoSalidas(double saldoSalidas) {
        this.saldoSalidas = saldoSalidas;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    
}
