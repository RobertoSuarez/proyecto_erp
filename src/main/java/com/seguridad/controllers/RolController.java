/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controllers;

import com.seguridad.dao.UsuarioDAO;
import com.seguridad.dao.RolDAO;
import com.seguridad.models.Roles;
import com.seguridad.models.Rol;
import com.seguridad.models.Modulo;
import com.seguridad.dao.ModuleDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Andres Mora
 */
@Named(value = "RolMB") 
@ViewScoped
public class RolController implements Serializable {

    private RolDAO rolDao; 
    private UsuarioDAO usuarioDAO;
    private ModuleDAO moduleDAO;
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    List<Roles> listaRoles = (List<Roles>) context.getExternalContext().getSessionMap().get("usuario_rol");
    private List<Rol> lstOfRoles;
    private Rol rolSeleccionado;
    private List<Modulo> lstOfModules;
    private int[] modulesActives;
    private List<SelectItem> lstModulesItem;
    String nameRol="";
    String descriptionRol="";
    
    public RolController() {
        usuarioDAO = new UsuarioDAO();
        rolSeleccionado = new Rol();
        rolDao = new RolDAO();
        moduleDAO = new ModuleDAO();
        this.lstOfRoles = new ArrayList<>();
        lstModulesItem = new ArrayList<>();
        this.lstOfModules = new ArrayList<>();
        this.lstOfRoles = rolDao.GetRols();
        this.lstOfModules = this.moduleDAO.invokeAllModules();
    }

    public String renderPrincipalView() {
        if (listaRoles.get(0).getNombre().equals("Gerente")) {
            return "true";
        } else {
            return "false";
        }
    }
    
    public void chargeDataModulesAndRol(Rol rolSeleccionado){
        nameRol=rolSeleccionado.getNombre();
        descriptionRol=rolSeleccionado.getDetalle();
        lstModulesItem = moduleDAO.invokeAllModulesForViews();
    }

    public List<Rol> getListOfRoles() {
        return lstOfRoles;
    }

    public void setListOfRoles(List<Rol> listOfRoles) {
        this.lstOfRoles = listOfRoles;
    }

    public Rol getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(Rol rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public String getNameRol() {
        return nameRol;
    }

    public void setNameRol(String nameRol) {
        this.nameRol = nameRol;
    }

    public String getDescriptionRol() {
        return descriptionRol;
    }

    public void setDescriptionRol(String descriptionRol) {
        this.descriptionRol = descriptionRol;
    }

    public List<Modulo> getLstOfModules() {
        return lstOfModules;
    }

    public void setLstOfModules(List<Modulo> lstOfModules) {
        this.lstOfModules = lstOfModules;
    }

    public int[] getModules() {
        return modulesActives;
    }

    public void setModules(int[] modules) {
        this.modulesActives = modules;
    }

    public int[] getModulesActives() {
        return modulesActives;
    }

    public void setModulesActives(int[] modulesActives) {
        this.modulesActives = modulesActives;
    }

    public List<SelectItem> getLstModulesItem() {
        return lstModulesItem;
    }

    public void setLstModulesItem(List<SelectItem> lstModulesItem) {
        this.lstModulesItem = lstModulesItem;
    }

    
}
