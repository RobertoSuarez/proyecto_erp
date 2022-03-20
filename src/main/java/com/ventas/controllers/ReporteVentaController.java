/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.controllers;

import com.ventas.dao.ReporteVentaDAO;
import com.ventas.models.ReporteSimple;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author jaime
 */
@Named (value = "ReporteVentaMB")
@ViewScoped
public class ReporteVentaController implements Serializable {
    Date fechaInicio1;
    Date fechaFin1;
    Date fechaInicio2;
    Date fechaFin2;
    Date fechaInicio3;
    Date fechaFin3;
    
    List<ReporteSimple> reporteClientes;
    List<ReporteSimple> reporteProductos;
    List<ReporteSimple> reporteCategorias;
    
    ReporteVentaDAO reporteDao;
    
    SimpleDateFormat formater;
    
    @PostConstruct
    public void initReporte(){
        this.reporteDao = new ReporteVentaDAO();
        
        this.reporteProductos = new ArrayList<>();
        this.reporteCategorias = new ArrayList<>();
        this.reporteClientes = new ArrayList<>();
        
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2021);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 1);
        
        this.fechaInicio1 = this.fechaInicio2 = this.fechaInicio3 = c.getTime();
        this.fechaFin1 = this.fechaFin2 = this.fechaFin3 = new Date(System.currentTimeMillis());
        
        formater = new SimpleDateFormat("yyyy/MM/dd");
        
        updateProductos();
        updateClientes();
        updateCategorias();
    }
    
    
    public void updateProductos(){
        this.reporteProductos.clear();
        this.reporteProductos = new ArrayList<>();
        this.reporteProductos = reporteDao.getProductosMasVendidos(formater.format(fechaInicio1),formater.format(fechaFin1));
    }
    
    public void updateClientes(){
        this.reporteClientes.clear();
        this.reporteClientes = new ArrayList<>();
        this.reporteClientes = reporteDao.getClientesTop(formater.format(fechaInicio2),formater.format(fechaFin2));
    }
    
    public void updateCategorias(){
        this.reporteCategorias.clear();
        this.reporteCategorias = new ArrayList<>();
        this.reporteCategorias = reporteDao.getCategoriasMasVendidos(formater.format(fechaInicio3),formater.format(fechaFin3));
    }
    

    public Date getFechaInicio1() {
        return fechaInicio1;
    }

    public void setFechaInicio1(Date fechaInicio1) {
        this.fechaInicio1 = fechaInicio1;
    }

    public Date getFechaFin1() {
        return fechaFin1;
    }

    public void setFechaFin1(Date fechaFin1) {
        this.fechaFin1 = fechaFin1;
    }

    public Date getFechaInicio2() {
        return fechaInicio2;
    }

    public void setFechaInicio2(Date fechaInicio2) {
        this.fechaInicio2 = fechaInicio2;
    }

    public Date getFechaFin2() {
        return fechaFin2;
    }

    public void setFechaFin2(Date fechaFin2) {
        this.fechaFin2 = fechaFin2;
    }

    public Date getFechaInicio3() {
        return fechaInicio3;
    }

    public void setFechaInicio3(Date fechaInicio3) {
        this.fechaInicio3 = fechaInicio3;
    }

    public Date getFechaFin3() {
        return fechaFin3;
    }

    public void setFechaFin3(Date fechaFin3) {
        this.fechaFin3 = fechaFin3;
    }

    public List<ReporteSimple> getReporteClientes() {
        return reporteClientes;
    }

    public void setReporteClientes(List<ReporteSimple> reporteClientes) {
        this.reporteClientes = reporteClientes;
    }

    public List<ReporteSimple> getReporteProductos() {
        return reporteProductos;
    }

    public void setReporteProductos(List<ReporteSimple> reporteProductos) {
        this.reporteProductos = reporteProductos;
    }

    public List<ReporteSimple> getReporteCategorias() {
        return reporteCategorias;
    }

    public void setReporteCategorias(List<ReporteSimple> reporteCategorias) {
        this.reporteCategorias = reporteCategorias;
    }
    
    
}
