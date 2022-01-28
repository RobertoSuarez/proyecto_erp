/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.FormulaProduccionDAO;
import com.produccion.models.FormulaProduccion;
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
 * @author Jimmy
 */
/** Solo a los controladores se les pone implements serializable*/

@ManagedBean (name = "formulaMB")

/** ViewScoped consuntar*/
@ViewScoped

public class FormulaProduccionManagedBean implements Serializable{
    
    private FormulaProduccion formulaProduccion=new FormulaProduccion();
    private FormulaProduccionDAO formulaProduccionDAO = new FormulaProduccionDAO();
    private List<FormulaProduccion> listaFormula=new ArrayList<>();
    
    @PostConstruct
    public void init() {
        System.out.println("PostConstruct");
        listaFormula=formulaProduccionDAO.getFormula();
    }
    
    public void closeDialogModalf(){
        PrimeFaces.current().executeScript("PF('crearFormulaDialog').hide()");
    }
    
    public void insertarFormula(){
    
        try {
            if("".equals(formulaProduccion.getNombre_formula())){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Ingresar Nombre")); 
            }
            else if("".equals(formulaProduccion.getDescripcion())){
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Ingresar descripcion")); 
            }else if("".equals(formulaProduccion.getRendimiento())){
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Ingresar rendimiento")); 
            }else {
            this.formulaProduccionDAO.insertarF(formulaProduccion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Formula Agregada"));
            }
            
                    
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Error no se guardo"));
        }
        closeDialogModalf();
        PrimeFaces.current().ajax().update("form-prinFormula:dtFormulaPrin");

    }
    
    public void editarFormula() {
        try {
            if ("".equals(formulaProduccion.getNombre_formula())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(formulaProduccion.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Descripción"));
            } else if ("".equals(formulaProduccion.getRendimiento())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Rendimiento"));
            } else {
                this.formulaProduccionDAO.actualizar(formulaProduccion, formulaProduccion.getCodigo_formula());
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Guardado"));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
        PrimeFaces.current().executeScript("PF('crearFormulaDialog').hide()");
        PrimeFaces.current().ajax().update("form:dtFormulaPrin", "form:growl");
    }
    
    
    public FormulaProduccion getFormulaProduccion() {
        return formulaProduccion;
    }

    public void setFormulaProduccion(FormulaProduccion formulaProduccion) {
        this.formulaProduccion = formulaProduccion;
    }

    public FormulaProduccionDAO getFormulaProduccionDAO() {
        return formulaProduccionDAO;
    }

    public void setFormulaProduccionDAO(FormulaProduccionDAO formulaProduccionDAO) {
        this.formulaProduccionDAO = formulaProduccionDAO;
    }

    public List<FormulaProduccion> getListaFormula() {
        return listaFormula;
    }

    public void setListaFormula(List<FormulaProduccion> listaFormula) {
        this.listaFormula = listaFormula;
    }
    
     /**
      * Cargar metodo que permite cargar la infomracion de la formula  
      */
    
    
    
    
    
}
