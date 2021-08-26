/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Abono implements Serializable{
    //Declaracion de las variables que necesita un abono.
    private int idAbono;
    private int idVenta;
    private double valorAbonado;
    private int idPlanDePago;
    private LocalDate fechaAbono=LocalDate.now();
    private int idFormaDePago;
    private String descrFormaPago;
    private String numFactura;
   
    
    //Constructor vacio

    public Abono() {
    }
    
    //Constructor con todas las variables del modelo.

    public Abono(int idAbono, int idVenta, double valorAbonado, int idPlanDePago, LocalDate fechaAbono, int idFormaDePago, String descrFormaPago) {
        this.idAbono = idAbono;
        this.idVenta = idVenta;
        this.valorAbonado = valorAbonado;
        this.idPlanDePago = idPlanDePago;
        this.fechaAbono = fechaAbono;
        this.idFormaDePago = idFormaDePago;
        this.descrFormaPago = descrFormaPago;
    }
    
    

    //Constructor para llenar una lista de abonos, solo con datos informativos.

    public Abono(int idAbono, int idVenta, double valorAbonado, LocalDate fechaAbono, String descrFormaPago, String numFactura) {
        this.idAbono = idAbono;
        this.idVenta = idVenta;
        this.valorAbonado = valorAbonado;
        this.fechaAbono = fechaAbono;
        this.descrFormaPago = descrFormaPago;
        this.numFactura=numFactura;
    }
   
    

    //Declaracion de  Getters y Setters
    public int getIdAbono() {
        return idAbono;
    }

    public void setIdAbono(int idAbono) {
        this.idAbono = idAbono;
    }

    public int getIdPlanDePago() {
        return idPlanDePago;
    }

    public void setIdPlanDePago(int idPlanDePago) {
        this.idPlanDePago = idPlanDePago;
    }

    public int getIdFormaDePago() {
        return idFormaDePago;
    }

    public void setIdFormaDePago(int idFormaDePago) {
        this.idFormaDePago = idFormaDePago;
    }

    public double getValorAbonado() {
        return valorAbonado;
    }

    public void setValorAbonado(double valorAbonado) {
        this.valorAbonado = valorAbonado;
    }

    public LocalDate getFechaAbono() {
        return fechaAbono;
    }

    public void setFechaAbono(LocalDate fechaAbono) {
        this.fechaAbono = fechaAbono;
    }

    public String getDescrFormaPago() {
        return descrFormaPago;
    }

    public void setDescrFormaPago(String descrFormaPago) {
        this.descrFormaPago = descrFormaPago;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }
    
    
}

