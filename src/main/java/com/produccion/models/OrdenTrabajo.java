package com.produccion.models;

import java.util.Date;

/**
 *
 * @author HP
 */
public class OrdenTrabajo {

    private int codigo_producto;
    private int codigo_orden;
    private int codigo_registro;
    private int codigo_formula;
    private int codigo_centro_trabajo;
    private int codigo_proceso;
    private String Descripcion;
    private float cantidad;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String nombre_formula;
    private String nombre_centro;
    private String nombre_proceso;
    private String nombre_producto;

    public OrdenTrabajo() {
    }

    public OrdenTrabajo(String nombre_proceso, int codigo_proceso) {
        this.nombre_proceso = nombre_proceso;
        this.codigo_proceso = codigo_proceso;
    }

    public OrdenTrabajo(int codigo_formula, String nombre_formula) {
        this.codigo_formula = codigo_formula;
        this.nombre_formula = nombre_formula;
    }

    public OrdenTrabajo(int codigo_producto, int codigo_registro, String nombre_producto) {
        this.codigo_producto = codigo_producto;
        this.codigo_registro = codigo_registro;
        this.nombre_producto = nombre_producto;
    }

    public int getCodigo_orden() {
        return codigo_orden;
    }

    public void setCodigo_orden(int codigo_orden) {
        this.codigo_orden = codigo_orden;
    }

    public int getCodigo_registro() {
        return codigo_registro;
    }

    public void setCodigo_registro(int codigo_registro) {
        this.codigo_registro = codigo_registro;
    }

    public int getCodigo_formula() {
        return codigo_formula;
    }

    public void setCodigo_formula(int codigo_formula) {
        this.codigo_formula = codigo_formula;
    }

    public int getCodigo_centro_trabajo() {
        return codigo_centro_trabajo;
    }

    public void setCodigo_centro_trabajo(int codigo_centro_trabajo) {
        this.codigo_centro_trabajo = codigo_centro_trabajo;
    }

    public int getCodigo_proceso() {
        return codigo_proceso;
    }

    public void setCodigo_proceso(int codigo_proceso) {
        this.codigo_proceso = codigo_proceso;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getNombre_formula() {
        return nombre_formula;
    }

    public void setNombre_formula(String nombre_formula) {
        this.nombre_formula = nombre_formula;
    }

    public String getNombre_centro() {
        return nombre_centro;
    }

    public void setNombre_centro(String nombre_centro) {
        this.nombre_centro = nombre_centro;
    }

    public String getNombre_proceso() {
        return nombre_proceso;
    }

    public void setNombre_proceso(String nombre_proceso) {
        this.nombre_proceso = nombre_proceso;
    }

    public int getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(int codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

}
