/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.models;

//Posible refactorizacion con una futura clase bancos.

import java.io.Serializable;
import java.time.LocalDate;


public class Cheque implements Serializable {
    //Declaracion de las variables que necesita un Cheque.
    private int idCheque;
    private int idVenta;
    private String nombreCliente;
    private String codBanco;
    private String nombreBanco;
    private String numeroDeCuenta;
    private String estadoCheque;
    private double valor_Cheque;
    private LocalDate fecha=LocalDate.now();
    
    //Constructor vacio

    public Cheque() {
    }
    
     //Constructor con todas las variables del modelo.
    
     //Constructor para llenar una lista de cheques de un cliente
    //, solo con datos informativos.
    public Cheque(int idCheque, int idVenta, String nombreCliente, 
            String codBanco, String nombreBanco, String numeroDeCuenta, 
            String estadoCheque, LocalDate fecha) {
        this.idCheque = idCheque;
        this.idVenta = idVenta;
        this.nombreCliente = nombreCliente;
        this.codBanco = codBanco;
        this.nombreBanco = nombreBanco;
        this.numeroDeCuenta = numeroDeCuenta;
        this.estadoCheque = estadoCheque;
        this.fecha= fecha;
    }

    //Declaracion de  Getters y Setters

    public int getIdCheque() {
        return idCheque;
    }

    public void setIdCheque(int idCheque) {
        this.idCheque = idCheque;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getCodBanco() {
        return codBanco;
    }

    public void setCodBanco(String codBanco) {
        this.codBanco = codBanco;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    public void setNumeroDeCuenta(String numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    public String getEstadoCheque() {
        return estadoCheque;
    }

    public void setEstadoCheque(String estadoCheque) {
        this.estadoCheque = estadoCheque;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    } 

    public double getValor_Cheque() {
        return valor_Cheque;
    }

    public void setValor_Cheque(double valor_Cheque) {
        this.valor_Cheque = valor_Cheque;
    }
    
    
}

