/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seguridad.models;

/**
 *
 * @author ninat
 */
public class Roles {
    
    int idRol;
    String nombre;
    String detalle;
    String URL;

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
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

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
    
    public Roles(){
        
    }

    public Roles(int idRol, String nombre, String detalle) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.detalle = detalle;
    }
    
    
    
}
