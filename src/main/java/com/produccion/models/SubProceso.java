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
public class SubProceso {
    private int codigo_subproceso;
    private int id_codigo_proceso;
    private String nombre;
    private String descripcion;

    public SubProceso() {
    }
    //Detalle proceso produccion
    public SubProceso(int codigo_subproceso, int id_codigo_proceso) {
        this.codigo_subproceso = codigo_subproceso;
        this.id_codigo_proceso = id_codigo_proceso;
    }
    //Subproceso
    public SubProceso(int codigo_subproceso, String nombre, String descripcion) {
        this.codigo_subproceso = codigo_subproceso;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getCodigo_subproceso() {
        return codigo_subproceso;
    }

    public void setCodigo_subproceso(int codigo_subproceso) {
        this.codigo_subproceso = codigo_subproceso;
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

    public int getId_codigo_proceso() {
        return id_codigo_proceso;
    }

    public void setId_codigo_proceso(int id_codigo_proceso) {
        this.id_codigo_proceso = id_codigo_proceso;
    }
    
    
}
