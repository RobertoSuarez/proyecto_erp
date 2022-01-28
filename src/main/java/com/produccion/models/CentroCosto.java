/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.models;

/**
 *
 * @author Alex
 */
public class CentroCosto {
    private int codigo_centroc;
    private String nombre;
    private String descripcion;
    private String identificador;

    public CentroCosto(int codigo_centroc, String nombre, String descripcion, String identificador) {
        this.codigo_centroc = codigo_centroc;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.identificador = identificador;
    }

    public CentroCosto() {
    }

    public int getCodigo_centroc() {
        return codigo_centroc;
    }

    public void setCodigo_centroc(int codigo_centroc) {
        this.codigo_centroc = codigo_centroc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.codigo_centroc;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CentroCosto other = (CentroCosto) obj;
        if (this.codigo_centroc != other.codigo_centroc) {
            return false;
        }
        return true;
    }
    
    
}
