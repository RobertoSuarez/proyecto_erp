/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.OrdenProduccionDAO;
import com.produccion.models.CentroCosto;
import com.produccion.models.OrdenTrabajo;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author HP
 */
@Named(value = "produccionMBean")
@ViewScoped
public class ProduccionMBean implements Serializable {

    /**
     * Creates a new instance of ProduccionMBean
     */
    private OrdenTrabajo ordenTrabajo;
    OrdenProduccionDAO ordenDao;

    private List<OrdenTrabajo> listaProducto;
    private List<OrdenTrabajo> listaFormula;
    private List<CentroCosto> listaCentro;

    public ProduccionMBean() {
        ordenTrabajo = new OrdenTrabajo();
        ordenDao = new OrdenProduccionDAO();

        listaProducto = new ArrayList<>();
        listaFormula = new ArrayList<>();
        listaCentro = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        ordenTrabajo.setCodigo_orden(idOrden());
        listaProducto = ordenDao.getListaProducto(ordenTrabajo.getCodigo_orden());
        listaCentro=ordenDao.getListaCentro();

    }

    public int idOrden() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        Integer categorySelected = new Integer((String) params.get("orden"));
        return categorySelected;
    }

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public List<OrdenTrabajo> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<OrdenTrabajo> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public List<OrdenTrabajo> getListaFormula() {
        return listaFormula;
    }

    public void setListaFormula(List<OrdenTrabajo> listaFormula) {
        this.listaFormula = listaFormula;
    }

    public List<CentroCosto> getListaCentro() {
        return listaCentro;
    }

    public void setListaCentro(List<CentroCosto> listaCentro) {
        this.listaCentro = listaCentro;
    }

    public void llenarFormula() {
        listaFormula = ordenDao.getListaFormula(ordenTrabajo.getCodigo_producto());
    }

    public void llenarProceso() {
        ordenTrabajo.setNombre_proceso(ordenDao.getProceso(ordenTrabajo.getCodigo_formula()));
    }

}
