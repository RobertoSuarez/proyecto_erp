/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.CostoDAO;
import com.produccion.models.Costo;
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
@ManagedBean(name = "costoMB")
@ViewScoped
public class CostoManageBean implements Serializable{
    private List<Costo> listaCosto = new ArrayList<>();
    private CostoDAO costoDAO;
    private Costo costo;
    private Costo selectCosto;

    public CostoManageBean(CostoDAO costoDAO, Costo costo) {
        this.costoDAO = costoDAO;
        this.costo = costo;
    }
    
    @PostConstruct
    public void init(){
        System.out.println("PostConstruct");
        listaCosto = costoDAO.getCosto();
    }

    public CostoDAO getCostoDAO() {
        return costoDAO;
    }

    public void setCostoDAO(CostoDAO costoDAO) {
        this.costoDAO = costoDAO;
    }

    public Costo getCosto() {
        return costo;
    }

    public void setCosto(Costo costo) {
        this.costo = costo;
    }

    public Costo getSelectCosto() {
        return selectCosto;
    }

    public void setSelectCosto(Costo selectCosto) {
        this.selectCosto = selectCosto;
    }

    public List<Costo> getListaCosto() {
        return listaCosto;
    }

    public void setListaCosto(List<Costo> listaCosto) {
        this.listaCosto = listaCosto;
    }
    
}
