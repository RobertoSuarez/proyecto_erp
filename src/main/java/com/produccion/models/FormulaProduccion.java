/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.models;

import com.global.config.Conexion;

public class FormulaProduccion {

    private boolean verifica;
    private int codigo_formula;
    private int codigo_proceso;
    private int codigo_subproceso;
    private int codigo_articulo;
    private String nombre_formula;
    private String nombre_producto;
    private String descripcion;
    private int rendimiento;
    private String estado;
    private int codigo_producto;

    private String nombre;
    private String categoria;
    private String descripcionProducto;
    private String tipo;
    private float costo;
    private float cantidad;
    private float cantidadMaxima;
    private float MOD;
    private float CIF;
    private float tiempoFormula;
    private String unidadMedida;
    private float tiempoUnidad;
    private float MODUnidad;
    private float CIFUnidad;
    private int subcuenta;
    private String codigoSubcuenta;
    private String nombreCuenta;

    public FormulaProduccion(String nombre, String descripcionProducto, float costo) {
        this.nombre = nombre;
        this.descripcionProducto = descripcionProducto;
        this.costo = costo;
    }

    public FormulaProduccion(String nombre, float costo, int subcuenta, String codigoSubcuenta, String nombreCuenta) {
        this.nombre = nombre;
        this.costo = costo;
        this.subcuenta = subcuenta;
        this.codigoSubcuenta = codigoSubcuenta;
        this.nombreCuenta = nombreCuenta;
    }

    public FormulaProduccion(String nombre_formula, float tiempoUnidad, float MODUnidad, float CIFUnidad) {
        this.nombre_formula = nombre_formula;
        this.tiempoUnidad = tiempoUnidad;
        this.MODUnidad = MODUnidad;
        this.CIFUnidad = CIFUnidad;
    }

    public FormulaProduccion(int codigo_formula, String nombre_formula, String nombre_producto,
            String descripcion, int rendimiento, String nombre_proceso, String categoria, String tipo) {
        this.codigo_formula = codigo_formula;
        this.nombre_formula = nombre_formula;
        this.nombre_producto = nombre_producto;
        this.descripcion = descripcion;
        this.rendimiento = rendimiento;
        this.nombre = nombre_proceso;
        this.categoria = categoria;
        this.tipo = tipo;
    }

    public FormulaProduccion(int codigo_formula, int codigo_subproceso, int codigo_articulo,
            String nombre_producto, String descripcion, String nombre, float costo,
            float cantidad, String unidadMedida) {
        this.codigo_formula = codigo_formula;
        this.codigo_subproceso = codigo_subproceso;
        this.codigo_articulo = codigo_articulo;
        this.nombre_producto = nombre_producto;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.costo = costo;
        this.cantidad = cantidad;
        this.unidadMedida = unidadMedida;
    }

    public FormulaProduccion(int codigo_producto, String nombre,
            String categoria, String descripcionProducto,
            String tipo, float costo, float cantidad, float cantidadMaxima, String unidadMedida) {
        this.codigo_producto = codigo_producto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcionProducto = descripcionProducto;
        this.tipo = tipo;
        this.costo = costo;
        this.cantidad = cantidad;
        this.cantidadMaxima = cantidadMaxima;
        this.unidadMedida = unidadMedida;
    }

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

    public FormulaProduccion(int codigo_formula, float cantidad) {
        this.codigo_formula = codigo_formula;
        this.cantidad = cantidad;
    }

    public FormulaProduccion(String nombre, float costo, int subcuenta) {
        this.nombre = nombre;
        this.costo = costo;
        this.subcuenta = subcuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getMOD() {
        return MOD;
    }

    public void setMOD(float MOD) {
        this.MOD = MOD;
    }

    public float getCIF() {
        return CIF;
    }

    public void setCIF(float CIF) {
        this.CIF = CIF;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getCantidadMaxima() {
        return cantidadMaxima;
    }

    public void setCantidadMaxima(float cantidadMaxima) {
        this.cantidadMaxima = cantidadMaxima;
    }

    public int getCodigo_articulo() {
        return codigo_articulo;
    }

    public void setCodigo_articulo(int codigo_articulo) {
        this.codigo_articulo = codigo_articulo;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getCodigo_subproceso() {
        return codigo_subproceso;
    }

    public void setCodigo_subproceso(int codigo_subproceso) {
        this.codigo_subproceso = codigo_subproceso;
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

    public boolean isVerifica() {
        return verifica;
    }

    public void setVerifica(boolean verifica) {
        this.verifica = verifica;
    }

    public float getTiempoFormula() {
        return tiempoFormula;
    }

    public void setTiempoFormula(float tiempoFormula) {
        this.tiempoFormula = tiempoFormula;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public float getTiempoUnidad() {
        return tiempoUnidad;
    }

    public void setTiempoUnidad(float tiempoUnidad) {
        this.tiempoUnidad = tiempoUnidad;
    }

    public float getMODUnidad() {
        return MODUnidad;
    }

    public void setMODUnidad(float MODUnidad) {
        this.MODUnidad = MODUnidad;
    }

    public float getCIFUnidad() {
        return CIFUnidad;
    }

    public void setCIFUnidad(float CIFUnidad) {
        this.CIFUnidad = CIFUnidad;
    }

    public int getSubcuenta() {
        return subcuenta;
    }

    public void setSubcuenta(int subcuenta) {
        this.subcuenta = subcuenta;
    }

    public String getCodigoSubcuenta() {
        return codigoSubcuenta;
    }

    public void setCodigoSubcuenta(String codigoSubcuenta) {
        this.codigoSubcuenta = codigoSubcuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

}
