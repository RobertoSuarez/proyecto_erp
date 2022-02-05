/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.models;

import java.sql.Time;

/**
 *
 * @author Alex
 */
public class SubProceso {
    private int codigo_subproceso;
    private int id_codigo_proceso;
    private String nombre;
    private String descripcion;
    private String hora;
    private float rendimiento;

    public SubProceso() {
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public SubProceso(int id_codigo_proceso, String nombre, String descripcion, String hora) {
        this.id_codigo_proceso = id_codigo_proceso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hora = hora;
    }

    public SubProceso(int codigo_subproceso, String nombre) {
        this.codigo_subproceso = codigo_subproceso;
        this.nombre = nombre;
    }
    
    

    public SubProceso(int codigo_subproceso, int id_codigo_proceso, String nombre, String descripcion, String hora, float rendimiento) {
        this.codigo_subproceso = codigo_subproceso;
        this.id_codigo_proceso = id_codigo_proceso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hora = hora;
        this.rendimiento = rendimiento;
    }
    

    public SubProceso(int codigo_subproceso, int id_codigo_proceso, String nombre, String descripcion, String hora) {
        this.codigo_subproceso = codigo_subproceso;
        this.id_codigo_proceso = id_codigo_proceso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hora = hora;
    }

    public float getRendimiento() {
        return rendimiento;
    }

    public void setRendimiento(float rendimiento) {
        this.rendimiento = rendimiento;
    }
    
    

    public SubProceso(int codigo_subproceso) {
        this.codigo_subproceso = codigo_subproceso;
    }


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
