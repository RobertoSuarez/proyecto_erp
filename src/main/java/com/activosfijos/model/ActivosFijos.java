/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.model;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author desta
 */
public class ActivosFijos {

    private int id_activo_fijo = 0;
    private String detalle_de_activo = "";
    private int valor_adquisicion = 0;
    private LocalDate fecha_adquisicion = LocalDate.now();
    private int id_empresa = 0;
    private int idproveedor = 0;
    private String proveedor="" ;
    private String numero_factura = "001-001-000000000";

    public ActivosFijos() {
    }

    public ActivosFijos(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public ActivosFijos(int id_activo_fijo, String detalle_de_activo, int valor_adquisicion, LocalDate fecha_adquisicion, int id_empresa, int idproveedor, String numero_factura) {
        this.detalle_de_activo = detalle_de_activo;
        this.valor_adquisicion = valor_adquisicion;
        this.fecha_adquisicion = fecha_adquisicion;
        this.id_empresa = id_empresa;
        this.idproveedor = idproveedor;
        this.numero_factura = numero_factura;
    }

    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public String getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(String numero_factura) {
        this.numero_factura = numero_factura;
    }

    public String getDetalle_de_activo() {
        return detalle_de_activo;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getId_activo_fijo() {
        return id_activo_fijo;
    }

    public void setId_activo_fijo(int id_activo_fijo) {
        this.id_activo_fijo = id_activo_fijo;
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


}
