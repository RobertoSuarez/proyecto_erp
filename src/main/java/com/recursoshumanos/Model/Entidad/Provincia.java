/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.Entidad;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author kestradalp
 */
public class Provincia implements Serializable {
    private int id;;
    private String Nombre;

    public Provincia() {
        this.id = 0;
        this.Nombre = "";
    }

    public Provincia(int id, String Nombre) {
        this.id = id;
        this.Nombre = Nombre;
    }

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
    public boolean equals (Object obj) {
        if (obj instanceof Provincia) {
            return this.equals((Provincia) obj);
        }  else { return false; }
    }
    
    public boolean equals (Provincia obj) {
        return this.id == obj.id && this.Nombre.equals(obj.Nombre);
    }
}
