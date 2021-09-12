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
 * Clase de tipo ENTIDAD, representa la tabla Dedicaciòn de la base de datos
 */
public class Dedicacion implements Serializable {

    /**
     * Lista de variables que representas los atributos y tablas con las que se
     * relaciona la tabla
     */
    private int id;
    private String nombre, detalle;
    private float porcentajeUtilidad;

    /**
     * Constructores para dedicación
     */
    public Dedicacion() {
        id = 1;
        nombre = "";
        detalle = "";
        porcentajeUtilidad = 0;
    }

    public Dedicacion(int id, String nombre, float porcentajeUtilidad,
            String detalle) {
        this.id = id;
        this.nombre = nombre;
        this.detalle = detalle;
        this.porcentajeUtilidad = porcentajeUtilidad;
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public float getPorcentajeUtilidad() {
        return porcentajeUtilidad;
    }

    public void setPorcentajeUtilidad(float porcentajeUtilidad) {
        this.porcentajeUtilidad = porcentajeUtilidad;
    }
}
