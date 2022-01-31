/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.models;

/**
 *
 * @author Jimmy
 */
public class Bodega {
    
    private int cod;
    private String nomBodega;
    private String direccion;
    private String telefono;
    private int codCiudad;
    private String nomCiudad;

    public Bodega() {
    }

    public Bodega(int cod, String nomBodega, String direccion, String telefono, int codCiudad, String nomCiudad) {
        this.cod = cod;
        this.nomBodega = nomBodega;
        this.direccion = direccion;
        this.telefono = telefono;
        this.codCiudad = codCiudad;
        this.nomCiudad = nomCiudad;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNomBodega() {
        return nomBodega;
    }

    public void setNomBodega(String nomBodega) {
        this.nomBodega = nomBodega;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getCodCiudad() {
        return codCiudad;
    }

    public void setCodCiudad(int codCiudad) {
        this.codCiudad = codCiudad;
    }

    public String getNomCiudad() {
        return nomCiudad;
    }

    public void setNomCiudad(String nomCiudad) {
        this.nomCiudad = nomCiudad;
    }
    
}
