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
 * Clase de tipo ENTIDAD, representa la tabla HorarioLaboral de la base de datos
 */
public class HorarioLaboral implements Serializable {

    /**
     * Lista de variables que representas los atributos y tablas con las que se
     * relaciona la tabla
     */
    private int id;
    private String nombre;
    private boolean estado;
    private String observaciones;
    private Date fechaVigencia;

    /**
     * Constructores para la clase
     */
    public HorarioLaboral() {
        this.id = 0;
        this.nombre = "";
        this.estado = true;
        this.observaciones = "";
        this.fechaVigencia = new Date();
    }

    public HorarioLaboral(int id, String nombre, boolean estado, String observaciones, Date fechaVigencia) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.observaciones = observaciones;
        this.fechaVigencia = fechaVigencia;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }
}
