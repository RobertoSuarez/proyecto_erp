/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.Entidad;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 *
 * Clase de tipo ENTIDAD, representa la tabla EmpleadoPuesto de la base de datos
 */
public class EmpleadoPuesto implements Serializable {

    /**
     * Lista de variables que representas los atributos y tablas con las que se
     * relaciona la tabla
     */
    private int id;
    private Empleado empleado;
    private PuestoLaboral puestoLaboral;
    private HorarioLaboral horarioLaboral;
    private Date fechaCambio;
    private boolean estado;
    private String observaciones;

    /**
     * Constructores para la clase
     */
    public EmpleadoPuesto() {
        this.id = 0;
        this.empleado = new Empleado();
        this.puestoLaboral = new PuestoLaboral();
        this.horarioLaboral = new HorarioLaboral();
        this.fechaCambio = new Date();
        this.estado = true;
        this.observaciones = "";
    }

    public EmpleadoPuesto(int id, Empleado empleado,
            PuestoLaboral puestoLaboral, HorarioLaboral horarioLaboral,
            Date fechaCambio, boolean estado, String observaciones) {
        this.id = id;
        this.empleado = empleado;
        this.puestoLaboral = puestoLaboral;
        this.horarioLaboral = horarioLaboral;
        this.fechaCambio = fechaCambio;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    /**
     * A continuación continuan los métodos de GET y SET de cada una de las
     * variables declaradas al inicio de la clase.
     *
     * @return lista Los GET tienen un return que nos retornan los datos y los
     * SET una variable que recibe el dato.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public PuestoLaboral getPuestoLaboral() {
        return puestoLaboral;
    }

    public void setPuestoLaboral(PuestoLaboral puestoLaboral) {
        this.puestoLaboral = puestoLaboral;
    }

    public HorarioLaboral getHorarioLaboral() {
        return horarioLaboral;
    }

    public void setHorarioLaboral(HorarioLaboral horarioLaboral) {
        this.horarioLaboral = horarioLaboral;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
