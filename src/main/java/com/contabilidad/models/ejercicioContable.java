/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contabilidad.models;

import java.util.Date;

/**
 *
 * @author User
 */
public class ejercicioContable {

  
    int idPeriodo;
    Date fechaInicio;
    Date fechaFin;
    boolean estado;
    String nombrePeriodo;
    String estadoString;

    public ejercicioContable(int idPeriodo, Date fechaInicio, Date fechaFin, boolean estado, String nombrePeriodo) {
        this.idPeriodo = idPeriodo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.nombrePeriodo = nombrePeriodo;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getEstadoString() {
        if (estado == false) {
            estadoString = "Cerrado";
        } else {
            estadoString = "En curso";
        }
        return estadoString;
    }

    public void setEstadoString(String estadoString) {
        if (estado == false) {
            estadoString = "En curso";
            this.estadoString = estadoString;
        } else {
            estadoString = "Cerrado";
            this.estadoString = estadoString;
        }

    }

    public ejercicioContable(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public ejercicioContable(Date fechaInicio, Date fechaFin, boolean estado, String nombrePeriodo) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.nombrePeriodo = nombrePeriodo;
    }

    public ejercicioContable() {
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

}
