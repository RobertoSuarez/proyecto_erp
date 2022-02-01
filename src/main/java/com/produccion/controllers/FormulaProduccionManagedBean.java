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
/**
 * Solo a los controladores se les pone implements serializable
 */
@ManagedBean(name = "formulaMB")

@ViewScoped

public class FormulaProduccionManagedBean implements Serializable {

    private FormulaProduccion formulaProduccion = new FormulaProduccion();
    private FormulaProduccionDAO formulaProduccionDAO = new FormulaProduccionDAO();
    private List<FormulaProduccion> listaFormula = new ArrayList<>();

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct");
        listaFormula = formulaProduccionDAO.getFormula();
    }
    
      public void closeDialogModal() {
        PrimeFaces.current().executeScript("PF('crearFormulaDialog').hide()");
    }
      
       public void insertar() {
           System.err.println("Entro ......");
        try {
            if ("".equals(formulaProduccion.getCodigo_proceso())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else if ("".equals(formulaProduccion.getNombre_formula())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(formulaProduccion.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else if ("".equals(formulaProduccion.getRendimiento())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else if ("".equals(formulaProduccion.getEstado())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else if ("".equals(formulaProduccion.getCodigo_producto())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                this.formulaProduccionDAO.insertarFormula(formulaProduccion);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Agregado"));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
        closeDialogModal();
        PrimeFaces.current().ajax().update("form-princFormula:dtformulaPrin");
    }
     
         public void editar() {
             System.err.println("Entro en editar......");
        try {
            if ("".equals(formulaProduccion.getCodigo_proceso())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else if ("".equals(formulaProduccion.getNombre_formula())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(formulaProduccion.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else if ("".equals(formulaProduccion.getRendimiento())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else if ("".equals(formulaProduccion.getEstado())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else if ("".equals(formulaProduccion.getCodigo_producto())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                this.formulaProduccionDAO.update(formulaProduccion);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Agregado"));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
        PrimeFaces.current().executeScript("PF('crearFormulaDialg').hide()");
        PrimeFaces.current().ajax().update("form:dtFormulaPrin", "form:growl");
    } 
         
    public void eliminarFormula(){
        try {
            this.formulaProduccionDAO.eliminarF(formulaProduccion, formulaProduccion.getNombre_formula());
            listaFormula = formulaProduccionDAO.getFormula();
            PrimeFaces.current().executeScript("PF('eliminarFormulaDialog').hide()");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito", "Formula Eliminada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Error al Eliminar la Formula"));
            
        }
        
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

  
    
    
    
    

}
