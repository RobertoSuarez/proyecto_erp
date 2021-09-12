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
 * @author ClasK7
 * @author rturr
 *
 * Clase de tipo ENTIDAD, representa la tabla asistencia de la base de datos
 */
public class Asistencia implements Serializable {

    /**
     * Lista de variables que representas los atributos y tablas con las que se
     * relaciona la tabla
     */
    private EmpleadoPuesto empleadoPuesto;
    private Date fecha, ingreso, salida;
    private DetalleHorario detalleHorario;

    /**
     * Constructores para la Asistencia
     */
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

     /**
     * A continuación continuan los métodos de GET y SET de cada una de las
     * variables declaradas al inicio de la clase.
     *
     * @return lista Los GET tienen un return que nos retornan los datos y los
     * SET una variable que recibe el dato.
     */
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
