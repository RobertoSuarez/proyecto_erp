/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.report;

/**
 *
 * @author angul
 */
public class KardexReport {
    private String codigo;
    
    private String fecha;
    
    private String item;
    
    private String entradas;
    
    private String salidas;
    
    private String saldoUnidades;
    
    private String saldoIngresos;
    
    private String saldoSalidas;
    
    private String saldo;
    
    private String costo;

    public KardexReport() {
    }

    public KardexReport(String codigo, String fecha, String item, String entradas, String salidas, String saldoUnidades, String saldoIngresos, String saldoSalidas, String saldo, String costo) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.item = item;
        this.entradas = entradas;
        this.salidas = salidas;
        this.saldoUnidades = saldoUnidades;
        this.saldoIngresos = saldoIngresos;
        this.saldoSalidas = saldoSalidas;
        this.saldo = saldo;
        this.costo = costo;
    }

    
    
    
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getEntradas() {
        return entradas;
    }

    public void setEntradas(String entradas) {
        this.entradas = entradas;
    }

    public String getSalidas() {
        return salidas;
    }

    public void setSalidas(String salidas) {
        this.salidas = salidas;
    }

    public String getSaldoUnidades() {
        return saldoUnidades;
    }

    public void setSaldoUnidades(String saldoUnidades) {
        this.saldoUnidades = saldoUnidades;
    }

    public String getSaldoIngresos() {
        return saldoIngresos;
    }

    public void setSaldoIngresos(String saldoIngresos) {
        this.saldoIngresos = saldoIngresos;
    }

    public String getSaldoSalidas() {
        return saldoSalidas;
    }

    public void setSaldoSalidas(String saldoSalidas) {
        this.saldoSalidas = saldoSalidas;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }
    
    
}
