/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.SubProcesoDAO;
import com.produccion.models.SubProceso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Alex
 */
@ManagedBean(name = "subprocesoMB")
@ViewScoped
public class SubProcesoManageBean implements Serializable{
    private List<SubProceso> listaSubProceso = new ArrayList<>();
    private SubProcesoDAO subProcesoDAO;
    private SubProceso subProceso;
    private SubProceso selectSubProceso;

    public SubProcesoManageBean() {
        subProceso = new SubProceso();
        subProcesoDAO = new SubProcesoDAO();
    }
    
    @PostConstruct
    public void init() {
        System.out.println("PostConstruct");
        listaSubProceso = subProcesoDAO.getSubProceso();
    }
}
