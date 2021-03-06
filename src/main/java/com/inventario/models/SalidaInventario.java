/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.models;

import com.cuentasporpagar.models.Proveedor;
import java.util.Date;

/**
 *
 * @author angul
 */
public class SalidaInventario {

    private static final long serialVersionUID = 1L;

    private int cod;

    private String numComprobante;

    private Date fecha;

    private int idBodega;

    private int idProveedor;

    private Proveedor proveedor;

    private String nombreProveedor;

    private String nombreBodega;

    private String observacion;

    public SalidaInventario() {
    }

    public SalidaInventario(Integer cod) {
        this.cod = cod;
    }

    public SalidaInventario(int cod, String numComprobante, Date fecha, int idBodega, int idProveedor) {
        this.cod = cod;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.idBodega = idBodega;
        this.idProveedor = idProveedor;
    }

    public SalidaInventario(int cod, String numComprobante, Date fecha, int idBodega, int idProveedor, String nombreProveedor, String nombreBodega) {
        this.cod = cod;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.idBodega = idBodega;
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.nombreBodega = nombreBodega;
    }

    public SalidaInventario(int cod, String numComprobante, Date fecha, int idBodega, int idProveedor, String nombreProveedor, String nombreBodega, String observacion) {
        this.cod = cod;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.idBodega = idBodega;
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.nombreBodega = nombreBodega;
        this.observacion = observacion;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNumComprobante() {
        return numComprobante;
    }

    public void setNumComprobante(String numComprobante) {
        this.numComprobante = numComprobante;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(int idBodega) {
        this.idBodega = idBodega;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

}
