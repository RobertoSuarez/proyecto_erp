/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.OrdenProduccionDAO;
import com.produccion.models.FormulaProduccion;
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
    private OrdenTrabajo ordenTerminada;
    OrdenProduccionDAO ordenDAO;
    private OrdenProduccion ordenTrabajo;
    private List<OrdenProduccion> listaOrden;
    List<OrdenTrabajo> listaState;
    private List<OrdenTrabajo> listaProducto;
    private List<FormulaProduccion> listaCostosDirectos;
    private List<FormulaProduccion> listaCostosIndirectos;

    public OrdenProduccionMB() {
        ordenTerminada = new OrdenTrabajo();
        ordenDAO = new OrdenProduccionDAO();
        ordenTrabajo = new OrdenProduccion();
        listaOrden = new ArrayList<>();
        listaState = new ArrayList<>();
        listaProducto = new ArrayList<>();
        listaCostosDirectos = new ArrayList<>();
        listaCostosIndirectos = new ArrayList<>();
    }

    public OrdenProduccion getOrdenTrabajo() {
        return ordenTrabajo;
    }

    @PostConstruct
    public void init() {
        listaOrden = state();

    }

    public List<OrdenTrabajo> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<OrdenTrabajo> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public OrdenTrabajo getOrdenTerminada() {
        return ordenTerminada;
    }

    public void setOrdenTerminada(OrdenTrabajo ordenTerminada) {
        this.ordenTerminada = ordenTerminada;
    }

    public List<FormulaProduccion> getListaCostosDirectos() {
        return listaCostosDirectos;
    }

    public void setListaCostosDirectos(List<FormulaProduccion> listaCostosDirectos) {
        this.listaCostosDirectos = listaCostosDirectos;
    }

    public List<FormulaProduccion> getListaCostosIndirectos() {
        return listaCostosIndirectos;
    }

    public void setListaCostosIndirectos(List<FormulaProduccion> listaCostosIndirectos) {
        this.listaCostosIndirectos = listaCostosIndirectos;
    }

    public List<OrdenProduccion> state() {
        int contador = 0;
        int size = 0;
        float procentaje;
        listaOrden = ordenDAO.getListaOrden();
        List<OrdenProduccion> lista = new ArrayList<>();
        for (OrdenProduccion orden : listaOrden) {
            listaState = ordenDAO.progresoProduccion(orden.getCodigo_orden());
            for (OrdenTrabajo state : listaState) {
                if ("T".equals(state.getEstado().trim())) {
                    contador++;
                }
                size++;
            }
            procentaje = (100 / size);
            lista.add(new OrdenProduccion(orden.getCodigo_orden(), orden.getFecha_emision(), orden.getFecha_fin(),
                    orden.getDescripcion(), orden.getEstado(), procentaje * contador));
            size = 0;
            contador = 0;
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

    public void llenarCombox(int idOrden) {
        listaProducto = ordenDAO.getListaProductoElaborado(idOrden);
    }

    public void llenarCostos() {
        for (OrdenTrabajo orden : listaProducto) {
            if (orden.getCodigo_producto() == ordenTerminada.getCodigo_producto()) {
                System.out.println("hola");
                listaCostosDirectos = ordenDAO.getListaCostos(orden.getCodigo_formula(), orden.getCodigo_registro(), orden.getCantidad(), "cmdunitario");
                listaCostosIndirectos = ordenDAO.getListaCostos(orden.getCodigo_formula(), orden.getCodigo_registro(), orden.getCantidad(), "cifunitario");
                ordenTerminada.setCantidad(orden.getCantidad());
                ordenTerminada.setCostoTotal(orden.getCostoTotal());
                ordenTerminada.setTotalMateria(orden.getTotalMateria());
                ordenTerminada.setTotalMOD(orden.getTotalMOD());
                ordenTerminada.setTotalCIF(orden.getTotalCIF());
                ordenTerminada.setCostoUnitario(orden.getCostoUnitario());
                break;
            }
        }
    }

}
