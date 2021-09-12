/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.Entidad;

import com.recursoshumanos.Model.DAO.TipoRubroDAO;
import java.io.Serializable;

/**
 *
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 *
 * Clase de tipo ENTIDAD, representa la tabla amonestación de la base de datos
 */
public class Suspencion implements Serializable {

    /**
     * Lista de variables que representas los atributos y tablas con las que se
     * relaciona la tabla
     */
    private int id, cantidadDias;
    private Empleado empleado;
    private float valor;
    private String detalle;
    private boolean estado;
    private TipoRubro tipoRubro;

    /**
     * Constructores para la clase
     */
    public Suspencion() {
        this.id = 0;
        this.cantidadDias = 0;
        this.empleado = new Empleado();
        this.valor = 0;
        this.detalle = "";
        this.estado = true;
        inicializarTipo();
    }

    public Suspencion(Empleado empleado) {
        this.id = 0;
        this.cantidadDias = 0;
        this.empleado = empleado;
        this.valor = 0;
        this.detalle = "";
        this.estado = true;
        inicializarTipo();
    }

    public Suspencion(int id, int cantidadDias, Empleado empleado, float valor, String detalle, boolean estado) {
        this.id = id;
        this.cantidadDias = cantidadDias;
        this.empleado = empleado;
        this.valor = valor;
        this.detalle = detalle;
        this.estado = estado;
        inicializarTipo();
    }

    /**
     * inicializarTipo() selecciona le tipo de rubro 2, el cual hace referencia
     * a suspeción
     */
    private void inicializarTipo() {
        TipoRubroDAO tipoRubroDAO = new TipoRubroDAO();
        tipoRubro = tipoRubroDAO.buscarPorId(12);
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

    public int getCantidadDias() {
        return cantidadDias;
    }

    public void setCantidadDias(int cantidadDias) {
        this.cantidadDias = cantidadDias;
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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
