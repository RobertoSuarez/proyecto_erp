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
 * Clase de tipo ENTIDAD, representa la tabla Sucursal de la base de datos
 */
public class Sucursal implements Serializable {

    /**
     * Lista de variables que representas los atributos y tablas con las que se
     * relaciona la tabla
     */
    private int id;
    private Empresa empresa;
    private Ciudad ciudad;
    private String direccion, detalle;

    /**
     * Constructores para la clase
     */
    public Sucursal() {
        this.id = 0;
        this.empresa = new Empresa();
        this.ciudad = new Ciudad();
        this.direccion = "";
        this.detalle = "";
    }

    public Sucursal(int id, Empresa empresa, Ciudad ciudad, String direccion, String detalle) {
        this.id = id;
        this.empresa = empresa;
        this.ciudad = ciudad;
        this.direccion = direccion;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String resumen() {
        return this.direccion + " - " + this.ciudad.resumen();
    }
}
