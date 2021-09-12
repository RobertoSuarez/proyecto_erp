/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.Entidad;

import java.io.Serializable;

/**
 *
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 *
 * Clase de tipo ENTIDAD, representa la tabla IngresosSalidas de la base de
 * datos
 */
public class IngresosSalidas implements Serializable {

    /**
     * Lista de variables que representas los atributos y tablas con las que se
     * relaciona la tabla
     */
    private int id;
    private String horaIngreso, horaSalida;
    private String observaciones;

    /**
     * Constructores para la clase
     */
    public IngresosSalidas() {
    }

    public IngresosSalidas(int id, String horaIngreso, String horaSalida, String observaciones) {
        this.id = id;
        this.horaIngreso = horaIngreso;
        this.horaSalida = horaSalida;
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

    public String getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(String horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String horario() {
        return horaIngreso + "-" + horaSalida;
    }

    public String resumen() {
        return observaciones + " (" + horario() + ")";
    }
}
