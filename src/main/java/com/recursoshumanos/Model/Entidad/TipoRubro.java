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
 */
public class TipoRubro implements Serializable {
    private int id, coeficiente;
    private String nombre;

    public TipoRubro() {
        this.id = 0;
        this.coeficiente = 0;
        this.nombre = "";
    }

    public TipoRubro(int id, int coeficiente, String nombre) {
        this.id = id;
        this.coeficiente = coeficiente;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(int coeficiente) {
        this.coeficiente = coeficiente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
