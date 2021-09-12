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
 * Clase de tipo ENTIDAD, representa la tabla DetalleHorario de la base de datos
 */
public class DetalleHorario implements Serializable {

    /**
     * Lista de variables que representan los atributos y tablas con las que se
     * relaciona la tabla
     */
    private int id;
    private IngresosSalidas ingresoSalida;
    private HorarioLaboral horarioLaboral;
    private DiaSemana diaSemana;
    private boolean estado;

    /**
     * Constructores para la detalleHorario
     */
    public DetalleHorario() {
        id = 0;
        ingresoSalida = new IngresosSalidas();
        horarioLaboral = new HorarioLaboral();
        diaSemana = new DiaSemana();
        estado = true;
    }

    public DetalleHorario(int id, IngresosSalidas ingresoSalida,
            HorarioLaboral horarioLaboral, DiaSemana diaSemana, boolean estado) {
        this.id = id;
        this.ingresoSalida = ingresoSalida;
        this.horarioLaboral = horarioLaboral;
        this.diaSemana = diaSemana;
        this.estado = estado;
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

    public IngresosSalidas getIngresoSalida() {
        return ingresoSalida;
    }

    public void setIngresoSalida(IngresosSalidas ingresoSalida) {
        this.ingresoSalida = ingresoSalida;
    }

    public HorarioLaboral getHorarioLaboral() {
        return horarioLaboral;
    }

    public void setHorarioLaboral(HorarioLaboral horarioLaboral) {
        this.horarioLaboral = horarioLaboral;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
