/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.Entidad;

import com.recursoshumanos.Model.DAO.TipoRubroDAO;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 *
 * Clase de tipo ENTIDAD, representa la tabla CargaFamiliar de la base de datos
 */
public class CargaFamiliar implements Serializable {

    /**
     * Lista de variables que representas los atributos y tablas con las que se
     * relaciona la tabla
     */
    private TipoRubro tipoRubro;
    private int id, faliares;
    private Empleado empleado;
    private String conyuge, detalle;
    private Date fechaCambio;

    /**
     * Constructores para cargaFamiliar
     */
    public CargaFamiliar() {
        this.id = 0;
        this.faliares = 0;
        this.empleado = new Empleado();
        this.conyuge = "";
        this.detalle = "";
        this.fechaCambio = new Date();
        inicializarTipo();
    }

    public CargaFamiliar(int id, int faliares, Empleado empleado, String conyuge,
             String detalle, Date fechaCambio) {
        this.id = id;
        this.faliares = faliares;
        this.empleado = empleado;
        this.conyuge = conyuge;
        this.detalle = detalle;
        this.fechaCambio = fechaCambio;
        inicializarTipo();
    }

    /**
     * inicializarTipo() selecciona le tipo de rubro 5, el cual hace referencia
     * a cargaFamiliar
     */
    private void inicializarTipo() {
        TipoRubroDAO tipoRubroDAO = new TipoRubroDAO();
        tipoRubro = tipoRubroDAO.buscarPorId(5);
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

    public int getFaliares() {
        return faliares;
    }

    public void setFaliares(int faliares) {
        this.faliares = faliares;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getConyuge() {
        return conyuge;
    }

    public void setConyuge(String conyuge) {
        this.conyuge = conyuge;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public TipoRubro getTipoRubro() {
        return tipoRubro;
    }
}
