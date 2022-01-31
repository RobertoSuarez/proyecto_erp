/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.controllers;

import com.inventario.DAO.BodegaDAO;
import com.inventario.models.Bodega;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;


@ManagedBean(name ="bodegaMB")

@ViewScoped

public class BodegaManagedBean  implements Serializable{
    
    private Bodega bodega= new Bodega();
    private BodegaDAO bodegaDAO= new BodegaDAO();
    private List<Bodega> listaBodega = new ArrayList<>();
    
    
    
    
    @PostConstruct
    public void init(){
    System.out.println("PostConstruct");
    listaBodega = bodegaDAO.getBodega();
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public BodegaDAO getBodegaDAO() {
        return bodegaDAO;
    }

    public void setBodegaDAO(BodegaDAO bodegaDAO) {
        this.bodegaDAO = bodegaDAO;
    }

    public List<Bodega> getListaBodega() {
        return listaBodega;
    }

    public void setListaBodega(List<Bodega> listaBodegas) {
        this.listaBodega = listaBodegas;
    }
 
    
}
