/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.model;

import java.text.DecimalFormat; 

/**
 *
 * @author desta
 */
public class ListaPeriodosActivosFijos {

    DecimalFormat formato1 = new DecimalFormat("#.00");
    private String detalle_de_activo = "";
    private int valor_adquisicion = 0;
    private double cuota_depresiacion = 0.0;
    private double valor_neto_libros = 0.0;
    private int depreciacion_meses = 0;
    private double porcentaje_depreciacion = 0.0;
    private String numero_factura = "";

    public ListaPeriodosActivosFijos() {
    }

    public ListaPeriodosActivosFijos(String detalle_de_activo, int valor_adquisicion, double cuota_depresiacion, double valor_neto_libros, int depreciacion_meses, double porcentaje_depreciacion, String numero_factura) {
        this.detalle_de_activo = detalle_de_activo;
        this.valor_adquisicion = valor_adquisicion;
        this.cuota_depresiacion =  Math.round(cuota_depresiacion * Math.pow(10, 2))/ Math.pow(10, 2);
        this.valor_neto_libros =  Math.round(valor_neto_libros * Math.pow(10, 2))/ Math.pow(10, 2);
        this.depreciacion_meses = depreciacion_meses;
        this.porcentaje_depreciacion = porcentaje_depreciacion;
        this.numero_factura = numero_factura;
    }

    public String getDetalle_de_activo() {
        return detalle_de_activo;
    }

    public void setDetalle_de_activo(String detalle_de_activo) {
        this.detalle_de_activo = detalle_de_activo;
    }

    public int getValor_adquisicion() {
        return valor_adquisicion;
    }

    public void setValor_adquisicion(int valor_adquisicion) {
        this.valor_adquisicion = valor_adquisicion;
    }

    public double getCuota_depresiacion() {
        return   Math.round(cuota_depresiacion * Math.pow(10, 2))/ Math.pow(10, 2);
    }

    public void setCuota_depresiacion(double cuota_depresiacion) {
        this.cuota_depresiacion = Math.round(cuota_depresiacion * Math.pow(10, 2))/ Math.pow(10, 2);
    }

    public double getValor_neto_libros() {
        return  Math.round(valor_neto_libros * Math.pow(10, 2))/ Math.pow(10, 2);
    }

    public void setValor_neto_libros(double valor_neto_libros) {
        this.valor_neto_libros =  Math.round(valor_neto_libros * Math.pow(10, 2))/ Math.pow(10, 2);
    }

    public int getDepreciacion_meses() {
        return depreciacion_meses;
    }

    public void setDepreciacion_meses(int depreciacion_meses) {
        this.depreciacion_meses = depreciacion_meses;
    }

    public double getPorcentaje_depreciacion() {
        return porcentaje_depreciacion;
    }

    public void setPorcentaje_depreciacion(double porcentaje_depreciacion) {
        this.porcentaje_depreciacion = porcentaje_depreciacion;
    }

    public String getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(String numero_factura) {
        this.numero_factura = numero_factura;
    }

}
