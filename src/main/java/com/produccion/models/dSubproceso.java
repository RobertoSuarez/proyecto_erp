/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.models;

import java.sql.Time;
import java.util.List;

/**
 *
 * @author HP
 */
public class dSubproceso {

    private int codigo_subproceso;
    private int codigo_costos;
    private String descripcion;
    private float costo_mano_obra;
    private float costo_indirecto;
    private String hora_costo;
    private List<Costo> costoDirecto;
    private List<Costo> costoIndirecto;
    
  
    public dSubproceso(int codigo_subproceso, int codigo_costos, String descripcion, float costo_mano_obra, float costo_indirecto, String hora_costo) {
        this.codigo_subproceso = codigo_subproceso;
        this.codigo_costos = codigo_costos;
        this.descripcion = descripcion;
        this.costo_mano_obra = costo_mano_obra;
        this.costo_indirecto = costo_indirecto;
        this.hora_costo = hora_costo;
    }
    
    public dSubproceso(int codigo_subproceso, int codigo_costos, String descripcion, float costo_mano_obra, float costo_indirecto) {
        this.codigo_subproceso = codigo_subproceso;
        this.codigo_costos = codigo_costos;
        this.descripcion = descripcion;
        this.costo_mano_obra = costo_mano_obra;
        this.costo_indirecto = costo_indirecto;
    }
   
    public dSubproceso() {
    }

    public List<Costo> getCostoDirecto() {
        return costoDirecto;
    }

    public void setCostoDirecto(List<Costo> costoDirecto) {
        this.costoDirecto = costoDirecto;
    }

    public List<Costo> getCostoIndirecto() {
        return costoIndirecto;
    }

    public void setCostoIndirecto(List<Costo> costoIndirecto) {
        this.costoIndirecto = costoIndirecto;
    }

    public int getCodigo_subproceso() {
        return codigo_subproceso;
    }

    public void setCodigo_subproceso(int codigo_subproceso) {
        this.codigo_subproceso = codigo_subproceso;
    }

    public int getCodigo_costos() {
        return codigo_costos;
    }

    public void setCodigo_costos(int codigo_costos) {
        this.codigo_costos = codigo_costos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getCosto_mano_obra() {
        return costo_mano_obra;
    }

    public void setCosto_mano_obra(float costo_mano_obra) {
        this.costo_mano_obra = costo_mano_obra;
    }

    public float getCosto_indirecto() {
        return costo_indirecto;
    }

    public void setCosto_indirecto(float costo_indirecto) {
        this.costo_indirecto = costo_indirecto;
    }

    public String getHora_costo() {
        return hora_costo;
    }

    public void setHora_costo(String hora_costo) {
        this.hora_costo = hora_costo;
    }

}
