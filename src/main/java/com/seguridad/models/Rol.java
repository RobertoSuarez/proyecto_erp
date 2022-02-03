
package com.seguridad.models;

import java.io.Serializable;

public class Rol implements Serializable {
    private int id;
    private String nombre;
    private String detalle;
    private Modulo modulo;
    private boolean habilitado;

    public Rol() {
    }

    public Rol(int id, String nombre, String detalle, Modulo modulo, boolean habilitado) {
        this.id = id;
        this.nombre = nombre;
        this.detalle = detalle;
        this.modulo = modulo;
        this.habilitado = habilitado;
    }

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

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
    
    
}
