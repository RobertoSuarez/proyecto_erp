/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.Entidad;

import java.io.Serializable;

public class Ciudad implements Serializable {

    /**
     * Lista de variables que representas los atributos y tablas con las que se
     * relaciona la tabla
     */
    private int id;
    private Provincia provincia;
    private String nombre, detalle;

    /**
     * Constructores para la Ciudad
     */
    public Ciudad() {
        this.id = 0;
        this.provincia = new Provincia();
        this.nombre = "";
        this.detalle = "";
    }

    public Ciudad(int id, Provincia provincia, String nombre, String detalle) {
        this.id = id;
        this.provincia = provincia;
        this.nombre = nombre;
        this.detalle = detalle;
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

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
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

    /**
     * resumen() Concatena el nombre de la ciudad con la provincia
     * @return nombre de la ciudad con la provincia
     */
    public String resumen() {
        return nombre + " (" + provincia.getNombre() + ")";
    }
}
