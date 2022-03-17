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
import com.seguridad.models.Permisos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
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
    private ModuleDAO moduleDAO;
    private Modulo module;
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    List<Roles> listaRoles = (List<Roles>) context.getExternalContext().getSessionMap().get("usuario_rol");
    private List<Rol> lstOfRoles;
    private Rol rolSeleccionado;
    private List<Modulo> modulesActives;
    private List<Modulo> lstModulesItem;
    private List<SelectItem> lstModulesSelected;
    private List<Permisos> lstPermisos;
    private int CodigoModule;
    String nameRol;
    String descriptionRol;
    String idModulo;

    public RolController() {
        rolSeleccionado = new Rol();
        rolDao = new RolDAO();
        moduleDAO = new ModuleDAO();
        this.lstOfRoles = new ArrayList<>();
        lstModulesItem = new ArrayList<>();
        lstModulesSelected = new ArrayList<>();
        lstPermisos = new ArrayList<>();
        modulesActives = new ArrayList<>();
        module = new Modulo();
        CodigoModule = 0;
        nameRol = "";
        descriptionRol = "";
        idModulo="";
        this.lstOfRoles = rolDao.GetRols();
        this.lstModulesItem = moduleDAO.invokeAllModulesForRol();
    }

    public String renderPrincipalView() {
        if (listaRoles.get(0).getNombre().equals("Gerente")) {
            return "true";
        } else {
            return "false";
        }
    }

    public void chargeDataModulesAndRol() {
        System.out.println(this.module.getIdModule());
        System.out.println(this.lstModulesItem.size());
    }

    public void chargeRolesSelected(Modulo mod) {
        this.lstModulesSelected.add(rolDao.GetRolsSelected(mod));
        lstModulesItem.remove(mod);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modulo añadido", "El modulo se cargo correctamente");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void chargePermisosSelected() {
        System.out.println(module.getIdModule());
        if (module.getIdModule()<=0) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccion invalida", "El modulo seleccionado no es valido");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            System.out.println(module.getIdModule());
//            System.out.println(this.rolSeleccionado.getId());
//            this.lstPermisos = rolDao.GetPermissionsRoles(this.rolSeleccionado.getId(), CodigoModule);
        }
    }

    public void selection() {

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

    public List<SelectItem> getLstModulesSelected() {
        return lstModulesSelected;
    }

    public void setLstModulesSelected(List<SelectItem> lstModulesSelected) {
        this.lstModulesSelected = lstModulesSelected;
    }

    public int getCodigoModule() {
        return CodigoModule;
    }

    public void setCodigoModule(int CodigoModule) {
        this.CodigoModule = CodigoModule;
    }

    public List<Permisos> getLstPermisos() {
        return lstPermisos;
    }

    public void setLstPermisos(List<Permisos> lstPermisos) {
        this.lstPermisos = lstPermisos;
    }

    public List<Modulo> getModulesActives() {
        return modulesActives;
    }

    public void setModulesActives(List<Modulo> modulesActives) {
        this.modulesActives = modulesActives;
    }

    public Modulo getModule() {
        return module;
    }

    public void setModule(Modulo module) {
        this.module = module;
    }

    public List<Modulo> getLstModulesItem() {
        return lstModulesItem;
    }

    public void setLstModulesItem(List<Modulo> lstModulesItem) {
        this.lstModulesItem = lstModulesItem;
    }

    public String getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
    }

}
