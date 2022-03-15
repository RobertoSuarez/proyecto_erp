/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Facturas_Pendientes implements Serializable {

    //Declaración de las Variables para las facturas pendientes
    private LocalDate fechaFacturacion = LocalDate.now();
    private int diasCredito;
    private LocalDate fechaVencimiento = LocalDate.now();
    private String nombreDelCliente;
    private int idFactura;
    private double valorTotalFactura;
    private double valorPendiente;
    private LocalDate fechaUltimoPago = LocalDate.now();
    private String estadoFactura;
    private int diasMora;
    private String numFactura;
    private double valor_Abono;

    //Constructor Vacío
    public Facturas_Pendientes() {
    }

    //Constructor para almacenar las facturas pendientes
    public Facturas_Pendientes(LocalDate fechaFacturacion, int diasCredito,
            LocalDate fechaVencimiento, String nombreDelCliente, int idFactura, 
            double valorTotalFactura, double valorPendiente, LocalDate fechaUltimoPago,
            String estadoFactura, int diasMora, String numFactura) {
        this.fechaFacturacion = fechaFacturacion;
        this.diasCredito = diasCredito;
        this.fechaVencimiento = fechaVencimiento;
        this.nombreDelCliente = nombreDelCliente;
        this.idFactura = idFactura;
        this.valorTotalFactura = valorTotalFactura;
        this.valorPendiente = valorPendiente;
        this.fechaUltimoPago = fechaUltimoPago;
        this.estadoFactura = estadoFactura;
        this.diasMora = diasMora;
        this.numFactura=numFactura;
    }

    public String identificaEstado() {

        switch (estadoFactura) {

            case "PENDIENTE":
                return "pendiente";

            case "PAGADO":
                return "pagado";

            case "VENCIDO":
                return "vencido";

            default:
                return "DEFAULT";
        }
    }

    //Declaracion de  Getters y Setters
    public LocalDate getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(LocalDate fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public int getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(int diasCredito) {
        this.diasCredito = diasCredito;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNombreDelCliente() {
        return nombreDelCliente;
    }

    public void setNombreDelCliente(String nombreDelCliente) {
        this.nombreDelCliente = nombreDelCliente;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public double getValorTotalFactura() {
        return valorTotalFactura;
    }

    public void setValorTotalFactura(double valorTotalFactura) {
        this.valorTotalFactura = valorTotalFactura;
    }

    public double getValorPendiente() {
        return valorPendiente;
    }

    public void setValorPendiente(double valorPendiente) {
        this.valorPendiente = valorPendiente;
    }

    public LocalDate getFechaUltimoPago() {
        return fechaUltimoPago;
    }

    public void setFechaUltimoPago(LocalDate fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
    }

    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public int getDiasMora() {
        return diasMora;
    }

    public void setDiasMora(int diasMora) {
        this.diasMora = diasMora;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public double getValor_Abono() {
        return valor_Abono;
    }

    public void setValor_Abono(double valor_Abono) {
        this.valor_Abono = valor_Abono;
    }
    
    
}

