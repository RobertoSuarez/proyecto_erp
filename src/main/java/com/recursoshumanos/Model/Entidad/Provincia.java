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
 * Clase de tipo ENTIDAD, representa la tabla amonestación de la base de datos
 */
public class Provincia implements Serializable {

    /**
     * Lista de variables que representas los atributos de la tabla
     */
    private int id;

    private String Nombre;

    /**
     * Constructores para la amonestación
     */
    public Provincia() {
        this.id = 0;
        this.Nombre = "";
    }

    public Provincia(int id, String Nombre) {
        this.id = id;
        this.Nombre = Nombre;
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
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Provincia) {
            return this.equals((Provincia) obj);
        } else {
            return false;
        }
    }

    public boolean equals(Provincia obj) {
        return this.id == obj.id && this.Nombre.equals(obj.Nombre);
    }
}
