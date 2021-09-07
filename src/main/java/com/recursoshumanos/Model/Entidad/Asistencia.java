/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.Entidad;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author kestradalp
 */
public class Asistencia implements Serializable {
    private EmpleadoPuesto empleadoPuesto;
    private Date fecha, ingreso, salida;
    private DetalleHorario detalleHorario;

    public Asistencia() {
        Calendar tmp = Calendar.getInstance();
        tmp.set(Calendar.HOUR_OF_DAY, 0);
        tmp.set(Calendar.MINUTE, 0);
        tmp.set(Calendar.SECOND, 0);
        tmp.set(Calendar.MILLISECOND, 0);
        Date minT = tmp.getTime();
        
        this.empleadoPuesto = new EmpleadoPuesto();
        this.ingreso = minT;
        this.salida = minT;
        this.fecha = new Date();
        this.detalleHorario = new DetalleHorario();
    }

    public Asistencia(EmpleadoPuesto empleadoPuesto, Date ingreso, 
        Date salida, Date fecha, DetalleHorario detalleHorario) {
        this.empleadoPuesto = empleadoPuesto;
        this.ingreso = ingreso;
        this.salida = salida;
        this.fecha = fecha;
        this.detalleHorario = detalleHorario;
    }

    public EmpleadoPuesto getEmpleadoPuesto() {
        return empleadoPuesto;
    }

    public void setEmpleadoPuesto(EmpleadoPuesto empleadoPuesto) {
        this.empleadoPuesto = empleadoPuesto;
    }

    public Date getIngreso() {
        return ingreso;
    }

    public void setIngreso(Date ingreso) {
        this.ingreso = ingreso;
    }

    public Date getSalida() {
        return salida;
    }

    public void setSalida(Date salida) {
        this.salida = salida;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public DetalleHorario getDetalleHorario() {
        return detalleHorario;
    }

    public void setDetalleHorario(DetalleHorario detalleHorario) {
        this.detalleHorario = detalleHorario;
    }
}