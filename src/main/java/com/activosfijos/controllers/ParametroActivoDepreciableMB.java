/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.activosfijos.controllers;

import com.activosfijos.dao.ParametroActivoDepreciableDAO;
import com.activosfijos.model.ParametroActivoDepreciable;
import com.contabilidad.dao.SubCuentaDAO;
import com.contabilidad.models.SubCuenta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author cturriagos
 */
@Named(value = "parametroView")
@ViewScoped
public class ParametroActivoDepreciableMB implements Serializable {
    private final ParametroActivoDepreciableDAO parametroActivoDepreciableDAO; 
    private final SubCuentaDAO subCuentaDAO;
    private ParametroActivoDepreciable parametroActivoDepreciable;
    private List<ParametroActivoDepreciable> Lista;
    private List<SubCuenta> subCuentaList;

    public ParametroActivoDepreciableMB() {
        this.parametroActivoDepreciableDAO = new ParametroActivoDepreciableDAO();
        this.subCuentaDAO = new SubCuentaDAO();
        this.parametroActivoDepreciable = new ParametroActivoDepreciable();
        this.Lista = new ArrayList<>();
        this.subCuentaList = new ArrayList<>();
    }
    
    @PostConstruct
    public void ConstructorParametroActivoDepreciable() {
        this.subCuentaList = subCuentaDAO.getSubCuentas("Activos fijos tangibles depreciables");
        this.Lista = parametroActivoDepreciableDAO.Listar();
    }

    public ParametroActivoDepreciable getParametroActivoDepreciable() {
        return parametroActivoDepreciable;
    }

    public void setParametroActivoDepreciable(ParametroActivoDepreciable parametroActivoDepreciable) {
        this.parametroActivoDepreciable = parametroActivoDepreciable;
    }

    public List<ParametroActivoDepreciable> getLista() {
        return Lista;
    }

    public void setLista(List<ParametroActivoDepreciable> Lista) {
        this.Lista = Lista;
    }

    public List<SubCuenta> getSubCuentaList() {
        return subCuentaList;
    }

    public void setSubCuentaList(List<SubCuenta> subCuentaList) {
        this.subCuentaList = subCuentaList;
    }

    public void abrirNuevo() {
        this.parametroActivoDepreciable = new ParametroActivoDepreciable();
    }
    
    public void enviar() {
        parametroActivoDepreciableDAO.setParametroActivoDepreciable(parametroActivoDepreciable);
        if (parametroActivoDepreciable.getIdParametro()== 0) {
            if (parametroActivoDepreciableDAO.insertar() > 0) {
                mostrarMensajeInformacion("El parámetro se ha guardado con éxito");
                Lista.add(parametroActivoDepreciable);
            } else {
                mostrarMensajeError("El parámetro no se pudo guardar");
            }
        } else {
            if (parametroActivoDepreciableDAO.actualizar() > 0) {
                mostrarMensajeInformacion("El parámetro se ha editado con éxito");
            } else {
                mostrarMensajeError("El parámetro no se pudo editar");
            }
        }
        this.Lista = this.parametroActivoDepreciableDAO.Listar();
        PrimeFaces.current().executeScript("PF('manageParametroDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-paramentros");
    }

    /**
     * Evento que muestra el mensaje de informaciòn en la interfaz
     * de que ha sido Éxitoso el mensaje 
     * @param mensaje Objeto que almacena la información
     * ha ser mostrada en la interfaz.
     */
    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Evento que muestra el mensaje de informaciòn en la interfaz
     * de que ha sido con Error el mensaje 
     * @param mensaje Objeto que almacena la información
     * ha ser mostrada en la interfaz.
     */
    public void mostrarMensajeError(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
