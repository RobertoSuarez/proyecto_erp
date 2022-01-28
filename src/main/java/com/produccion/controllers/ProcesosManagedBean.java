/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.ProcesoProduccionDAO;
import com.produccion.models.ProcesoProduccion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Alex
 */
@ManagedBean(name = "procesoMB")
@ViewScoped
public class ProcesosManagedBean implements Serializable {

    private List<ProcesoProduccion> listaProcesos = new ArrayList<>();
    private ProcesoProduccionDAO procesoProduccionDAO;
    private ProcesoProduccion procesoProduccion;
    private ProcesoProduccion selectProceso;

    public ProcesosManagedBean() {
        procesoProduccionDAO = new ProcesoProduccionDAO();
        procesoProduccion = new ProcesoProduccion();
    }

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct");
        listaProcesos = procesoProduccionDAO.getProcesosProduccion();
    }

    public void closeDialogModal() {
        PrimeFaces.current().executeScript("PF('procesoPrincDialog').hide()");
    }

    public void aleatorioIdenti() {
        String uuid = java.util.UUID.randomUUID().toString().substring(4, 7).toUpperCase();
        String uuid2 = java.util.UUID.randomUUID().toString().substring(4, 7);
        this.procesoProduccion.setIdentificador("PR-" + uuid + uuid2);
    }

    public void openNew() {
        this.selectProceso = new ProcesoProduccion();
        aleatorioIdenti();
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void showInfo(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, "Exito", message);
    }

    public void showWarn(String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", message);
    }

    public void insertar() {
        try {
            if ("".equals(procesoProduccion.getIdentificador())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else if ("".equals(procesoProduccion.getNombre())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(procesoProduccion.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                this.procesoProduccionDAO.insertarp(procesoProduccion);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Agregado"));
                PrimeFaces.current().executeScript("PF('nuevoProcesoPrincDialog').hide()");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
        
        PrimeFaces.current().ajax().update("form-princ:dtProcesoPrin", "form-princ:growl");
    }

    public void editar() {
        try {
            if ("".equals(procesoProduccion.getIdentificador())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Codigo"));
            } else if ("".equals(procesoProduccion.getNombre())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(procesoProduccion.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                this.procesoProduccionDAO.update(procesoProduccion);
                System.out.println(procesoProduccion.getCodigo_proceso());
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Guardado"));
                PrimeFaces.current().executeScript("PF('procesoEditDialog').hide()");
                PrimeFaces.current().ajax().update("form-princ:dtProcesoPrin");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
        
        
    }

    //En proceso, no utilizar
    public void eliminar() {
        try {
            this.procesoProduccionDAO.delete(procesoProduccion, procesoProduccion.getIdentificador());
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Eliminado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al eliminar"));
        }
    }

    public ProcesoProduccion getProcesoProduccion() {
        return procesoProduccion;
    }

    public void setProcesoProduccion(ProcesoProduccion procesoProduccion) {
        this.procesoProduccion = procesoProduccion;
    }

    public List<ProcesoProduccion> getListaProcesos() {
        return listaProcesos;
    }

    public void setListaProcesos(List<ProcesoProduccion> listaProcesos) {
        this.listaProcesos = listaProcesos;
    }

    public ProcesoProduccionDAO getProcesoProduccionDAO() {
        return procesoProduccionDAO;
    }

    public void setProcesoProduccionDAO(ProcesoProduccionDAO procesoProduccionDAO) {
        this.procesoProduccionDAO = procesoProduccionDAO;
    }

    public ProcesoProduccion getSelectProceso() {
        return selectProceso;
    }

    public void setSelectProceso(ProcesoProduccion selectProceso) {
        this.selectProceso = selectProceso;
    }

}
