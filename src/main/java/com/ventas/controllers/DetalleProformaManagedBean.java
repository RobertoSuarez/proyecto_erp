/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.controllers;

import com.ventas.dao.ClienteVentaDao;
import com.ventas.dao.ProformaDAO;
import com.ventas.models.ClienteVenta;
import com.ventas.models.Proforma;
import com.ventas.models.DetalleProforma;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Asynchronous;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


@Named(value = "detalleProformaManagedBean")
@Dependent
public class DetalleProformaManagedBean implements Serializable {
    
    ProformaDAO profDao;
    List<Proforma> listaProformas;
    DetalleProforma details;
    List<DetalleProforma> listaDetalles;
    Proforma proformaActual;
    ClienteVenta cliente;
    ClienteVentaDao clientedao;
    String Identificacion;
    String nombrecliente;
    
    public DetalleProformaManagedBean() throws SQLException {
        this.nombrecliente ="";
        this.Identificacion="";
        this.profDao = new ProformaDAO();
        this.listaProformas = new ArrayList<>();
        this.details = new DetalleProforma();
        this.listaDetalles = new ArrayList<>();
        this.cliente = new ClienteVenta();
    }

    
public void detalleProforma() throws SQLException{
    System.out.println("Ingresa al metodo");
    this.listaDetalles = new ArrayList<>();
    this.listaDetalles = this.profDao.listaDetalleProforma(this.proformaActual);
    this.cliente = this.clientedao.BuscarClientePorId(this.proformaActual.getId_cliente());
    this.Identificacion= this.cliente.getIdentificacion();
    this.nombrecliente=this.cliente.getNombre();
}
    
    
    //Mostrar algun mensaje
    @Asynchronous
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public ProformaDAO getProfDao() {
        return profDao;
    }

    public void setProfDao(ProformaDAO profDao) {
        this.profDao = profDao;
    }

    public List<Proforma> getListaProformas() {
        return listaProformas;
    }

    public void setListaProformas(List<Proforma> listaProformas) {
        this.listaProformas = listaProformas;
    }

    public DetalleProforma getDetails() {
        return details;
    }

    public void setDetails(DetalleProforma details) {
        this.details = details;
    }

    public List<DetalleProforma> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<DetalleProforma> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public Proforma getProformaActual() {
        return proformaActual;
    }

    public void setProformaActual(Proforma proformaActual) {
        this.proformaActual = proformaActual;
    }

    public ClienteVenta getCliente() {
        return cliente;
    }

    public void setCliente(ClienteVenta cliente) {
        this.cliente = cliente;
    }

    public ClienteVentaDao getClientedao() {
        return clientedao;
    }

    public void setClientedao(ClienteVentaDao clientedao) {
        this.clientedao = clientedao;
    }

    public String getIdentificacion() {
        return Identificacion;
    }

    public void setIdentificacion(String Identificacion) {
        this.Identificacion = Identificacion;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }
    
    
    
}
