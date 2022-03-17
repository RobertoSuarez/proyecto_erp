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

    private boolean verifica;
    private int codigo_subproceso;
    private int id_codigo_proceso;
    private String nombre;
    private String descripcion;
    private String hora;
    private float rendimiento;

    private float pieza;
    private float minuto_directo;
    private float minuto_intirecto;
    private float minuto_pieza;
    private float importe_directo;
    private float importe_indirecto;
    private float pieza_minuto;
    private float costo_minuto_directo;
    private float costo_minuto_indirecto;
    public SubProceso() {
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

    public SubProceso(int codigo_subproceso, String nombre, float minuto_directo, float minuto_intirecto) {
        this.codigo_subproceso = codigo_subproceso;
        this.nombre = nombre;
        this.minuto_directo = minuto_directo;
        this.minuto_intirecto = minuto_intirecto;
    }

    public float getMinuto_pieza() {
        return minuto_pieza;
    }

    public void setMinuto_pieza(float minuto_pieza) {
        this.minuto_pieza = minuto_pieza;
    }

    public float getPieza() {
        return pieza;
    }

    public void setPieza(float pieza) {
        this.pieza = pieza;
    }

    public float getMinuto_directo() {
        return minuto_directo;
    }

    public void setMinuto_directo(float minuto_directo) {
        this.minuto_directo = minuto_directo;
    }

    public float getMinuto_intirecto() {
        return minuto_intirecto;
    }

    public void setMinuto_intirecto(float minuto_intirecto) {
        this.minuto_intirecto = minuto_intirecto;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
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

    public boolean isVerifica() {
        return verifica;
    }

    public void setVerifica(boolean verifica) {
        this.verifica = verifica;
    }

    public float getImporte_directo() {
        return importe_directo;
    }

    public void setImporte_directo(float importe_directo) {
        this.importe_directo = importe_directo;
    }

    public float getImporte_indirecto() {
        return importe_indirecto;
    }

    public void setImporte_indirecto(float importe_indirecto) {
        this.importe_indirecto = importe_indirecto;
    }

    public float getPieza_minuto() {
        return pieza_minuto;
    }

    public void setPieza_minuto(float pieza_minuto) {
        this.pieza_minuto = pieza_minuto;
    }

    public float getCosto_minuto_directo() {
        return costo_minuto_directo;
    }

    public void setCosto_minuto_directo(float costo_minuto_directo) {
        this.costo_minuto_directo = costo_minuto_directo;
    }

    public float getCosto_minuto_indirecto() {
        return costo_minuto_indirecto;
    }

    public void setCosto_minuto_indirecto(float costo_minuto_indirecto) {
        this.costo_minuto_indirecto = costo_minuto_indirecto;
    }

}
