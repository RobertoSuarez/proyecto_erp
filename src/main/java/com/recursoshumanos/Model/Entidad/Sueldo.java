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
 * Clase de tipo ENTIDAD, representa la tabla Sueldo de la base de datos
 */
public class Sueldo implements Serializable {

    /**
     * Lista de variables que representas los atributos y tablas con las que se
     * relaciona la tabla
     */
    private int id;
    private Empleado empleado;
    private float valor;
    private Date fechaActualizacion;
    private boolean estado;
    private TipoRubro tipoRubro;

    /**
     * Constructores para la clase
     */
    public Sueldo() {
        this.id = 0;
        this.empleado = new Empleado();
        this.valor = 0;
        this.fechaActualizacion = new Date();
        this.estado = true;
        inicializarTipo();
    }

    public Sueldo(int id, Empleado empleado, float valor,
            Date fechaActualizacion, boolean estado) {
        this.id = id;
        this.empleado = empleado;
        this.valor = valor;
        this.fechaActualizacion = fechaActualizacion;
        this.estado = estado;
        inicializarTipo();
    }

    /**
     * inicializarTipo() selecciona le tipo de rubro 11, el cual hace referencia
     * a sueldo
     */
    private void inicializarTipo() {
        TipoRubroDAO tipoRubroDAO = new TipoRubroDAO();
        tipoRubro = tipoRubroDAO.buscarPorId(11);
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
