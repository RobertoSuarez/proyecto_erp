/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.ProcesoProduccionDAO;
import com.produccion.models.ProcesoProduccion;
import com.produccion.models.SubProceso;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author HP
 */
@Named(value = "rutaProduccion")
@ViewScoped
public class RutaProduccion implements Serializable {

    private List<SubProceso> listaSubProcesos;
    private List<SubProceso> listaSubConfirmadaProcesos;
    private List<SubProceso> listaConfirmadaProcesos;
    private ProcesoProduccion procesoProduccion;
    ProcesoProduccionDAO procesoProduccionDAO;
    SubProceso sProceso;

    public RutaProduccion() {
        procesoProduccionDAO = new ProcesoProduccionDAO();
        listaSubProcesos = new ArrayList<>();
        listaSubConfirmadaProcesos = new ArrayList<>();
        sProceso = new SubProceso();
        listaConfirmadaProcesos = new ArrayList<>();
        procesoProduccion = new ProcesoProduccion();
    }

    @PostConstruct
    public void init() {
        listaSubProcesos = procesoProduccionDAO.getListaSubProcesos();
        aleatorioIdenti();
    }

    public List<SubProceso> getListaSubProcesos() {
        return listaSubProcesos;
    }

    public void setListaSubProcesos(List<SubProceso> listaSubProcesos) {
        this.listaSubProcesos = listaSubProcesos;
    }

    public List<SubProceso> getListaSubConfirmadaProcesos() {
        return listaSubConfirmadaProcesos;
    }

    public void setListaSubConfirmadaProcesos(List<SubProceso> listaSubConfirmadaProcesos) {
        this.listaSubConfirmadaProcesos = listaSubConfirmadaProcesos;
    }

    public List<SubProceso> getListaConfirmadaProcesos() {
        return listaConfirmadaProcesos;
    }

    public void setListaConfirmadaProcesos(List<SubProceso> listaConfirmadaProcesos) {
        this.listaConfirmadaProcesos = listaConfirmadaProcesos;
    }

    public SubProceso getsProceso() {
        return sProceso;
    }

    public void setsProceso(SubProceso sProceso) {
        this.sProceso = sProceso;
    }

    public ProcesoProduccion getProcesoProduccion() {
        return procesoProduccion;
    }

    public void setProcesoProduccion(ProcesoProduccion procesoProduccion) {
        this.procesoProduccion = procesoProduccion;
    }

    public void addMateriales(SubProceso producto) {
        if (producto.isVerifica() == true) {
            listaSubConfirmadaProcesos.add(new SubProceso(producto.getCodigo_subproceso(),
                    producto.getNombre(),
                    producto.getDescripcion()));

        } else {
            for (SubProceso lista : listaSubConfirmadaProcesos) {
                if (lista.getCodigo_subproceso() == producto.getCodigo_subproceso()) {
                    listaSubConfirmadaProcesos.remove(lista);
                }
            }
        }
    }

    public void llenaProductoConfirmado() {
        for (SubProceso lista : listaSubConfirmadaProcesos) {
            if (duplicidadDatos(lista)) {
                showWarn("El Centro de trabajo ya se encuentra agregado");
            } else {
                listaConfirmadaProcesos.add(lista);
            }

        }
    }

    public boolean duplicidadDatos(SubProceso producto) {
        boolean confirmacion = false;
        for (SubProceso lista : listaConfirmadaProcesos) {
            if (lista.getCodigo_subproceso() == producto.getCodigo_subproceso()) {
                confirmacion = true;
            }
        }
        return confirmacion;
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void showInfo(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, "Exito", message);
    }

    public void showWarn(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, "Advertencia", message);
    }

    public void showError(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, "Error", message);
    }

    public void insertarProceso() {
        if ("".equals(procesoProduccion.getIdentificador())) {
            showWarn("Ingrese un Identificador");
        } else if ("".equals(procesoProduccion.getNombre())) {
            showWarn("Ingrese un Nombre");
        } else if ("".equals(procesoProduccion.getDescripcion())) {
            showWarn("Ingrese una Descripción");
        } else if (listaConfirmadaProcesos.size()<1) {
            showWarn("Seleccione un centro de trabajo");
        } else {
            procesoProduccionDAO.insertarp(procesoProduccion);
            procesoProduccion.setCodigo_proceso(procesoProduccionDAO.idProceso());
            for (SubProceso lista : listaConfirmadaProcesos) {
                procesoProduccionDAO.insertarDetallleSub(procesoProduccion.getCodigo_proceso(), lista.getCodigo_subproceso());
            }
            procesoProduccionDAO.updateCosto(procesoProduccionDAO.idProceso());
            procesoProduccionDAO.updateTiempo(procesoProduccionDAO.idProceso());
            showInfo("Ruta registrada con exito");
            vaciarRegistro();
        }
    }

    public void aleatorioIdenti() {
        this.procesoProduccion.setIdentificador("PR-" + "000" + (procesoProduccionDAO.idProceso() + 1));
    }

    public void deleteFila(SubProceso subproceso) {
        listaConfirmadaProcesos.remove(subproceso);
    }
    public void vaciarRegistro(){
        procesoProduccionDAO = new ProcesoProduccionDAO();
        listaSubProcesos = new ArrayList<>();
        listaSubConfirmadaProcesos = new ArrayList<>();
        sProceso = new SubProceso();
        listaConfirmadaProcesos = new ArrayList<>();
        procesoProduccion = new ProcesoProduccion();
        listaSubProcesos = procesoProduccionDAO.getListaSubProcesos();
        aleatorioIdenti();
    }

}
