/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.models;

import java.io.Serializable;

public class ClienteVenta implements Serializable {
    
    private int idCliente;
    private String nombre;
    private int idTipoCliente;
    private String tipoCliente;
    private String identificacion;
    private String contacto;
    private String direccion;
    
    public ClienteVenta(){
        
    }

    public int getIdCliente() {
        return idCliente;
    }
    

    public String getNombre() {
        return nombre;
    }

    public int getIdTipoCliente() {
        return idTipoCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getContacto() {
        return contacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdTipoCliente(int idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
    
}
