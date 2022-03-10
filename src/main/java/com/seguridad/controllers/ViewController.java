/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controllers;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import com.seguridad.models.Vista;
import com.seguridad.dao.VistaDAO;
import com.seguridad.dao.ModuleDAO;
import com.seguridad.models.Modulo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author Crisito
 */
@Named(value = "ViewsMB")
@ViewScoped
public class ViewController implements Serializable {

    List<Vista> lstViews;
    Vista views;
    VistaDAO viewsDAO;
    List<SelectItem> lstModulesItems;
    List<Modulo> lstModules;
    ModuleDAO moduleDAO;

    public ViewController() {
        lstViews = new ArrayList<>();
        lstModules = new ArrayList<>();
        lstModulesItems = new ArrayList<>();
        views = new Vista();
        viewsDAO = new VistaDAO();
        lstViews = viewsDAO.GetAllViews();
        moduleDAO = new ModuleDAO();
        lstModules = moduleDAO.invokeAllModules();
        lstModulesItems = moduleDAO.invokeAllModulesForViews();
    }

    public void insertViews() {
        if (views.getId_Modulo() == 0 || views.getId_Modulo() < 0) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ingreso Fallido", "Error no se puede cargar la vista");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            viewsDAO.insertViews(views);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingreso Exitoso", "La vista se cargo correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
            lstViews = new ArrayList<>();
            lstViews = viewsDAO.GetAllViews();
        }
    }

    public void prepareEditViews(Vista viewsAux) {
        views = viewsAux;
    }

    public void editViews() {
        viewsDAO.updateViews(views);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Modificado", "La vista se edito correctamente");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void erasedViews(Vista viewsAux) {
        viewsDAO.deleteViews(viewsAux);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Registro Eliminado", "La vista ha sido eliminada");
        FacesContext.getCurrentInstance().addMessage(null, message);
        lstViews = new ArrayList<>();
        lstViews = viewsDAO.GetAllViews();
    }

    public List<Vista> getLstViews() {
        return lstViews;
    }

    public void setLstViews(List<Vista> lstViews) {
        this.lstViews = lstViews;
    }

    public Vista getViews() {
        return views;
    }

    public void setViews(Vista views) {
        this.views = views;
    }

    public VistaDAO getViewsDAO() {
        return viewsDAO;
    }

    public void setViewsDAO(VistaDAO viewsDAO) {
        this.viewsDAO = viewsDAO;
    }

    public List<SelectItem> getLstModulesItems() {
        return lstModulesItems;
    }

    public void setLstModulesItems(List<SelectItem> lstModulesItems) {
        this.lstModulesItems = lstModulesItems;
    }

    public List<Modulo> getLstModules() {
        return lstModules;
    }

    public void setLstModules(List<Modulo> lstModules) {
        this.lstModules = lstModules;
    }

}
