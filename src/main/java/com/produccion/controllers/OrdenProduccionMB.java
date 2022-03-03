/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.OrdenProduccionDAO;
import com.produccion.models.OrdenProduccion;
import com.produccion.models.OrdenTrabajo;
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
    List<OrdenTrabajo> listaState;

    public OrdenProduccionMB() {
        ordenDAO = new OrdenProduccionDAO();
        ordenTrabajo = new OrdenProduccion();
        listaOrden = new ArrayList<>();
        listaState = new ArrayList<>();
    }

    public OrdenProduccion getOrdenTrabajo() {
        return ordenTrabajo;
    }

    @PostConstruct
    public void init() {
        listaOrden = state();

    }

    public List<OrdenProduccion> state() {
        int contador=0;
        int size=0;
        float procentaje;
        listaOrden = ordenDAO.getListaOrden();
        List<OrdenProduccion> lista = new ArrayList<>();
        for (OrdenProduccion orden : listaOrden) {
            listaState = ordenDAO.progresoProduccion(orden.getCodigo_orden());
            for (OrdenTrabajo state : listaState) {
                if("T".equals(state.getEstado().trim())){
                    contador++;
                }
                size++;
            }
            procentaje=(100/size);
            lista.add(new OrdenProduccion(orden.getCodigo_orden(),orden.getFecha_emision(),orden.getFecha_fin(),
            orden.getDescripcion(),orden.getEstado(),procentaje*contador));
            size=0;
            contador=0;
        }
        return lista;
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
