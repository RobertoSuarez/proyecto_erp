/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.models;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author angul
 */
public class AsientoInventario implements Serializable {
    
    private int idDiario;
    
    private double total;
    
    private String documento;
    
    private String detalle;
    
    private String fechaCreacion;
    
    private String fechaCierre;

    public AsientoInventario() {
    }

    public AsientoInventario(int idDiario, double total, String documento, String detalle, String fechaCreacion, String fechaCierre) {
        this.idDiario = idDiario;
        this.total = total;
        this.documento = documento;
        this.detalle = detalle;
        this.fechaCreacion = fechaCreacion;
        this.fechaCierre = fechaCierre;
    }

    
    
    
    public int getIdDiario() {
        return idDiario;
    }

    public void setIdDiario(int idDiario) {
        this.idDiario = idDiario;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
    
    
    
}
