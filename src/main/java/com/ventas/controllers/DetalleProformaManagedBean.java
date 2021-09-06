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
    Proforma proformaSeleccionada;
    ClienteVenta cliente;
    ClienteVentaDao clientedao;
    String Identificacion;
    String nombrecliente;
    
    public DetalleProformaManagedBean() {
        this.nombrecliente ="";
        this.Identificacion="";
        listarProformas();
    }

    
public void detalleProforma(Proforma ProformaSeleccionada) throws SQLException{
    this.listaDetalles = new ArrayList<>();
    this.proformaSeleccionada= ProformaSeleccionada;
    this.listaDetalles = this.profDao.listaDetalleProforma(this.proformaSeleccionada);
    this.cliente = this.clientedao.BuscarClientePorId(this.proformaSeleccionada.getId_cliente());
    this.Identificacion= this.cliente.getIdentificacion();
    this.nombrecliente=this.cliente.getNombre();
}
    
    @Asynchronous
    public void listarProformas() {
        this.profDao = new ProformaDAO();
        this.listaProformas = new ArrayList<>();
        try {
            System.out.println("Llenando lista de proforma");
            this.listaProformas = this.profDao.retornarProformas();
            System.out.println("Lista llenada");
            if (listaProformas.isEmpty()) {
                addMessage(FacesMessage.SEVERITY_ERROR, "No existe proformas en la Base de Datos", "Message Content");
            }
        } catch (SQLException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().toString(), "Message Content");
        }
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

    public Proforma getProformaSeleccionada() {
        return proformaSeleccionada;
    }

    public void setProformaSeleccionada(Proforma proformaSeleccionada) {
        this.proformaSeleccionada = proformaSeleccionada;
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
