/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controllers;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import com.seguridad.models.Modulo;
import com.seguridad.dao.ModuleDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Crisito
 */
@Named(value = "ModuleMB") 
@ViewScoped
public class ModuleController implements Serializable {
    private Modulo moduleOject;
    private List<Modulo> lstModuleObject;
    private ModuleDAO moduleDao;
    private Modulo moduleNew;
    private Modulo moduleEditable;
    String nameModule="";
    String descriptionModule="";

    public ModuleController() {
        this.moduleNew = new Modulo();
        this.moduleEditable= new Modulo();
        this.moduleOject = new Modulo();
        this.lstModuleObject= new ArrayList<>();
        this.moduleDao= new ModuleDAO();
        this.lstModuleObject = this.moduleDao.invokeAllModules();
    }

    public ModuleController(Modulo moduleOject, List<Modulo> lstModuleObject, ModuleDAO moduleDao) {
        this.moduleOject = moduleOject;
        this.lstModuleObject = lstModuleObject;
        this.moduleDao = moduleDao;
        this.lstModuleObject = this.moduleDao.invokeAllModules();
    }
    
    public void insertModuleData(){
        System.out.println(this.moduleNew.getNameModule()+this.moduleNew.getDescriptionModule());
        this.moduleDao.insertModule(this.moduleNew);
    }
    
    public void prepareEditModuleData(Modulo mod){
        this.moduleEditable= mod;
    }
    
    public void editModuleData(){
        this.moduleDao.editModule(this.moduleEditable);
    }
    
    public void deleteModuleData(Modulo mod){
        this.moduleDao.deleteModule(mod);
    }
    
    public Modulo getModuleOject() {
        return moduleOject;
    }

    public void setModuleOject(Modulo moduleOject) {
        this.moduleOject = moduleOject;
    }

    public List<Modulo> getLstModuleObject() {
        return lstModuleObject;
    }

    public void setLstModuleObject(List<Modulo> lstModuleObject) {
        this.lstModuleObject = lstModuleObject;
    }

    public ModuleDAO getModuleDao() {
        return moduleDao;
    }

    public void setModuleDao(ModuleDAO moduleDao) {
        this.moduleDao = moduleDao;
    }

    public Modulo getModuleNew() {
        return moduleNew;
    }

    public void setModuleNew(Modulo moduleNew) {
        this.moduleNew = moduleNew;
    }

    public Modulo getModuleEditable() {
        return moduleEditable;
    }

    public void setModuleEditable(Modulo moduleEditable) {
        this.moduleEditable = moduleEditable;
    }

    public String getNameModule() {
        return nameModule;
    }

    public void setNameModule(String nameModule) {
        this.nameModule = nameModule;
    }

    public String getDescriptionModule() {
        return descriptionModule;
    }

    public void setDescriptionModule(String descriptionModule) {
        this.descriptionModule = descriptionModule;
    }
    
}
