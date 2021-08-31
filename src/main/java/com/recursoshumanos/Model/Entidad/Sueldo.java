/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.Entidad;

import Model.DAO.TipoRubroDAO;
import java.util.Date;

/**
 *
 * @author ClasK7
 */
public class Sueldo {
    private int id;
    private Empleado empleado;
    private float valor;
    private Date fechaActualizacion;
    private boolean estado;
    private TipoRubro tipoRubro;

    public Sueldo() {
        this.id = 0;
        this.empleado = new Empleado();
        this.valor = 0;
        this.fechaActualizacion = new Date();
        this.estado = true;
        inicializarTipo();
    }

    public Sueldo(int id, Empleado empleado, float valor, Date fechaActualizacion, boolean estado) {
        this.id = id;
        this.empleado = empleado;
        this.valor = valor;
        this.fechaActualizacion = fechaActualizacion;
        this.estado = estado;
        inicializarTipo();
    }

    private void inicializarTipo(){
        TipoRubroDAO tipoRubroDAO = new TipoRubroDAO();
        tipoRubro = tipoRubroDAO.buscarPorId(11);
    } 

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

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public TipoRubro getTipoRubro() {
        return tipoRubro;
    }
}
