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
 * Clase de tipo ENTIDAD, representa la tabla Empresa de la base de datos
 */
public class Empresa implements Serializable {

    /**
     * Lista de variables que representas los atributos y tablas con las que se
     * relaciona la tabla
     */
    private int id;
    private Dedicacion dedicacion;
    private String ruc, tipo, nombre, razonSocial, detalle;

    /**
     * Constructores para la clase
     */
    public Empresa() {
        this.id = 0;
        this.dedicacion = new Dedicacion(1, "", 0, "");
        this.ruc = "";
        this.tipo = "";
        this.nombre = "";
        this.razonSocial = "";
        this.detalle = "";
    }

    public Empresa(int id, Dedicacion dedicacion, String ruc, String tipo,
            String nombre, String razonSocial, String detalle) {
        this.id = id;
        this.dedicacion = dedicacion;
        this.ruc = ruc;
        this.tipo = tipo;
        this.nombre = nombre;
        this.razonSocial = razonSocial;
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

    public Dedicacion getDedicacion() {
        return dedicacion;
    }

    public void setDedicacion(Dedicacion dedicacion) {
        this.dedicacion = dedicacion;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
