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
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

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
