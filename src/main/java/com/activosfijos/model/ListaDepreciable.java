/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.model;

import com.contabilidad.models.SubCuenta;
import java.time.LocalDate;

/**
 *
 * @author desta
 */
public class ListaDepreciable {

    private int id_activo_fijo;
    private String detalle_de_activo;
    private int valor_adquisicion;
    private LocalDate fecha_adquisicion = LocalDate.now();
    private int id_empresa;
    private int depreciacion_meses;
    private double cuota_depresiacion;
    private double porcentaje_depreciacion;
    private double valor_neto_libros;
    private int idproveedor;
    private String proveedor;
    private String numero_factura ;
    private double saldo_depresiacion;
    private SubCuenta subCuenta;
    
    public ListaDepreciable(int id_activo_fijo, String detalle_de_activo, int valor_adquisicion, int id_empresa,
                            int depreciacion_meses, double cuota_depresiacion, double porcentaje_depreciacion,
                            double valor_neto_libros, int idproveedor, String proveedor, SubCuenta subCuenta) {
        this.id_activo_fijo = id_activo_fijo;
        this.detalle_de_activo = detalle_de_activo;
        this.valor_adquisicion = valor_adquisicion;
        this.id_empresa = id_empresa;
        this.depreciacion_meses = depreciacion_meses;
        this.cuota_depresiacion = cuota_depresiacion;
        this.porcentaje_depreciacion = porcentaje_depreciacion;
        this.valor_neto_libros = valor_neto_libros;
        this.idproveedor = idproveedor;
        this.proveedor = proveedor;
        this.subCuenta = subCuenta;
    }

    public ListaDepreciable(int depreciacion_meses, double porcentaje_depreciacion, double saldo_depresiacion) {
        this.depreciacion_meses = depreciacion_meses;
        this.porcentaje_depreciacion = porcentaje_depreciacion;
        this.saldo_depresiacion = saldo_depresiacion;
        this.subCuenta = new SubCuenta();
    }

    public double getSaldo_depresiacion() {
        return saldo_depresiacion;
    }

    public void setSaldo_depresiacion(double saldo_depresiacion) {
        this.saldo_depresiacion = saldo_depresiacion;
    }

    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public ListaDepreciable() {
        subCuenta = new SubCuenta();
    }

    public int getId_activo_fijo() {
        return id_activo_fijo;
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

    public int getDepreciacion_meses() {
        return depreciacion_meses;
    }

    public void setDepreciacion_meses(int depreciacion_meses) {
        this.depreciacion_meses = depreciacion_meses;
    }

    public double getCuota_depresiacion() {
        return cuota_depresiacion;
    }

    public void setCuota_depresiacion(double cuota_depresiacion) {
        this.cuota_depresiacion = cuota_depresiacion;
    }

    public double getPorcentaje_depreciacion() {
        return porcentaje_depreciacion;
    }

    public void setPorcentaje_depreciacion(double porcentaje_depreciacion) {
        this.porcentaje_depreciacion = porcentaje_depreciacion;
    }

    public double getValor_neto_libros() {
        return valor_neto_libros;
    }

    public void setValor_neto_libros(double valor_neto_libros) {
        this.valor_neto_libros = valor_neto_libros;
    }

    public SubCuenta getSubCuenta() {
        return subCuenta;
    }

    public void setSubCuenta(SubCuenta subCuenta) {
        this.subCuenta = subCuenta;
    }
}
