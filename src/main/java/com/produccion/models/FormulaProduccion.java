/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.models;

import com.global.config.Conexion;

/**
 *
 * @author Jimmy
 */
public class FormulaProduccion {
    
    private int codigo_formula;
    private int codigo_proceso;
    private String nombre_formula;
    private String descripcion;
    private int rendimiento;
    private String estado;
    private int codigo_producto;

    public FormulaProduccion() {
    }

    public FormulaProduccion(int codigo_formula, 
            int codigo_proceso, 
            String nombre_formula, 
            String descripcion, 
            int rendimiento, 
            String estado, 
            int codigo_producto) {
        this.codigo_formula = codigo_formula;
        this.codigo_proceso = codigo_proceso;
        this.nombre_formula = nombre_formula;
        this.descripcion = descripcion;
        this.rendimiento = rendimiento;
        this.estado = estado;
        this.codigo_producto = codigo_producto;
    }

    public int getCodigo_formula() {
        return codigo_formula;
    }

    public void setCodigo_formula(int codigo_formula) {
        this.codigo_formula = codigo_formula;
    }

    public int getCodigo_proceso() {
        return codigo_proceso;
    }

    public void setCodigo_proceso(int codigo_proceso) {
        this.codigo_proceso = codigo_proceso;
    }

    public String getNombre_formula() {
        return nombre_formula;
    }

    public void setNombre_formula(String nombre_formula) {
        this.nombre_formula = nombre_formula;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getRendimiento() {
        return rendimiento;
    }

    public void setRendimiento(int rendimiento) {
        this.rendimiento = rendimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(int codigo_producto) {
        this.codigo_producto = codigo_producto;
    }
    
    
 
    
  
    
}
