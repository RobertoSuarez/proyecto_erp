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
    private Modulo mod;
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    List<Roles> listaRoles = (List<Roles>) context.getExternalContext().getSessionMap().get("usuario_rol");
    private List<Rol> lstOfRoles;
    private Rol rolSeleccionado;
    private List<Modulo> modulesActives;
    private List<SelectItem> lstModulesItem;
    private List<SelectItem> lstModulesSelected;
    private List<Permisos> lstPermisos;
    private int CodigoModule;
    String nameRol;
    String descriptionRol;
    
    public RolController() {
        rolSeleccionado = new Rol();
        rolDao = new RolDAO();
        moduleDAO = new ModuleDAO();
        this.lstOfRoles = new ArrayList<>();
        lstModulesItem = new ArrayList<>();
        lstModulesSelected = new ArrayList<>();
        lstPermisos= new ArrayList<>();
        modulesActives = new ArrayList<>();
        CodigoModule =0;
        nameRol="";
        descriptionRol="";
        SelectItem ItemDefault=new SelectItem();
        ItemDefault.setLabel("Seleccione..");
        ItemDefault.setValue(-1);
        lstModulesSelected.add(ItemDefault);
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
    
    public void chargeDataModulesAndRol(){
        System.out.println(this.modulesActives.size());
        System.out.println(this.lstModulesItem.size());
    }
    
    public void chargeRolesSelected(){
        System.out.println(this.modulesActives.size());
        System.out.println(this.lstModulesSelected.size());
        this.lstModulesSelected = new ArrayList<>();
//        this.lstModulesSelected= rolDao.GetRolsSelected(this.modulesActives);
    }
    
    public void chargePermisosSelected(){
        System.out.println(CodigoModule);
        System.out.println(this.rolSeleccionado.getId());
        this.lstPermisos = rolDao.GetPermissionsRoles(this.rolSeleccionado.getId(), CodigoModule);
    }
    
    public void selection(){
        
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

    public List<SelectItem> getLstModulesItem() {
        return lstModulesItem;
    }

    public void setLstModulesItem(List<SelectItem> lstModulesItem) {
        this.lstModulesItem = lstModulesItem;
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

    public Modulo getMod() {
        return mod;
    }

    public void setMod(Modulo mod) {
        this.mod = mod;
    }
    
}
