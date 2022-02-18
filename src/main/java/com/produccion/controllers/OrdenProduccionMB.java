/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.OrdenProduccionDAO;
import com.produccion.models.OrdenProduccion;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author HP
 */
@Named(value = "ordenProduccionMB")
@ViewScoped
public class OrdenProduccionMB implements Serializable {

    /**
     * Creates a new instance of OrdenProduccionMB
     */
    OrdenProduccionDAO ordenDAO;
    private OrdenProduccion ordenTrabajo;
    private List<OrdenProduccion> listaOrden;

    public OrdenProduccionMB() {
        ordenDAO = new OrdenProduccionDAO();
        ordenTrabajo = new OrdenProduccion();
        listaOrden = new ArrayList<>();
    }

    public OrdenProduccion getOrdenTrabajo() {
        return ordenTrabajo;
    }

    @PostConstruct
    public void init() {
        listaOrden = ordenDAO.getListaOrden();
    }

    public void setOrdenTrabajo(OrdenProduccion ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public List<OrdenProduccion> getListaOrden() {
        return listaOrden;
    }

    public void setListaOrden(List<OrdenProduccion> listaOrden) {
        this.listaOrden = listaOrden;
    }

}
