/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.models;

public class Ajustes {
    
   private int cod;
   private String fecha;
   private int codBodega;
   private String Nom_bodega;
   private String Observacion;

    public Ajustes() {
    }

    public Ajustes(int cod, String fecha, int codBodega, String Observacion) {
        this.cod = cod;
        this.fecha = fecha;
        this.codBodega = codBodega;
        this.Observacion = Observacion;
    }

    public int getCod() {
        return cod;
    }

    public String getNom_bodega() {
        return Nom_bodega;
    }

    public void setNom_bodega(String Nom_bodega) {
        this.Nom_bodega = Nom_bodega;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCodBodega() {
        return codBodega;
    }

    public void setCodBodega(int codBodega) {
        this.codBodega = codBodega;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String Observacion) {
        this.Observacion = Observacion;
    }
   
   
    
}
