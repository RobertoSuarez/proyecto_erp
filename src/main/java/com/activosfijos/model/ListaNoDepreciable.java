/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.model;

import java.time.LocalDate;

/**
 *
 * @author desta
 */
public class ListaNoDepreciable {

    private int id_activo_fijo;
    private String detalle_de_activo;
    private int valor_adquisicion;
    private LocalDate fecha_adquisicion = LocalDate.now();
    private int id_empresa;
    private int tiempo_amortizacion;
    private double porcentaje_amortizacion;
    private int capitalizacion_meses;
    private double revalorizar;
    private double plusvalia;
    private int idproveedor;
    private String proveedor;
    private String numero_factura;

    public ListaNoDepreciable(int id_activo_fijo, String detalle_de_activo, int valor_adquisicion, int id_empresa, int tiempo_amortizacion, double porcentaje_amortizacion, int capitalizacion_meses, double revalorizar, double plusvalia, int idproveedor, String proveedor, String numero_factura) {
        this.id_activo_fijo = id_activo_fijo;
        this.detalle_de_activo = detalle_de_activo;
        this.valor_adquisicion = valor_adquisicion;
        this.id_empresa = id_empresa;
        this.tiempo_amortizacion = tiempo_amortizacion;
        this.porcentaje_amortizacion = porcentaje_amortizacion;
        this.capitalizacion_meses = capitalizacion_meses;
        this.revalorizar = revalorizar;
        this.plusvalia = plusvalia;
        this.idproveedor = idproveedor;
        this.proveedor = proveedor;
        this.numero_factura = numero_factura;
    }

    public ListaNoDepreciable(
            String detalle_de_activo,
            int valor_adquisicion,
            double plusvalia,
            String proveedor,
            String numero_factura) {
        this.detalle_de_activo = detalle_de_activo;
        this.valor_adquisicion = valor_adquisicion;
        this.plusvalia = plusvalia;
        this.proveedor = proveedor;
        this.numero_factura = numero_factura;
    }

    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public ListaNoDepreciable() {
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(String numero_factura) {
        this.numero_factura = numero_factura;
    }

    public int getId_activo_fijo() {
        return id_activo_fijo;
    }

    public void setId_activo_fijo(int id_activo_fijo) {
        this.id_activo_fijo = id_activo_fijo;
    }

    public String getDetalle_de_activo() {
        return detalle_de_activo;
    }

    public void setDetalle_de_activo(String detalle_de_activo) {
        this.detalle_de_activo = detalle_de_activo;
    }

    public int getValor_adquisicion() {
        return valor_adquisicion;
    }

    public void setValor_adquisicion(int valor_adquisicion) {
        this.valor_adquisicion = valor_adquisicion;
    }

    public LocalDate getFecha_adquisicion() {
        return fecha_adquisicion;
    }

    public void setFecha_adquisicion(LocalDate fecha_adquisicion) {
        this.fecha_adquisicion = fecha_adquisicion;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getTiempo_amortizacion() {
        return tiempo_amortizacion;
    }

    public void setTiempo_amortizacion(int tiempo_amortizacion) {
        this.tiempo_amortizacion = tiempo_amortizacion;
    }

    public double getPorcentaje_amortizacion() {
        return porcentaje_amortizacion;
    }

    public void setPorcentaje_amortizacion(double porcentaje_amortizacion) {
        this.porcentaje_amortizacion = porcentaje_amortizacion;
    }

    public int getCapitalizacion_meses() {
        return capitalizacion_meses;
    }

    public void setCapitalizacion_meses(int capitalizacion_meses) {
        this.capitalizacion_meses = capitalizacion_meses;
    }

    public double getRevalorizar() {
        return revalorizar;
    }

    public void setRevalorizar(double revalorizar) {
        this.revalorizar = revalorizar;
    }

    public double getPlusvalia() {
        return plusvalia;
    }

    public void setPlusvalia(double plusvalia) {
        this.plusvalia = plusvalia;
    }

}
