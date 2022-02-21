/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.controllers;

import com.inventario.DAO.ArticulosInventarioDAO;
import com.inventario.DAO.BodegaDAO;
import com.inventario.DAO.TipoDAO;
import com.inventario.DAO.TransferenciasDAO;
import com.inventario.models.ArticulosInventario;
import com.inventario.models.Bodega;
import com.inventario.models.DetalleTransferencia;
import com.inventario.models.EncabezadoTransferencia;
import com.inventario.models.Tipo;
import static com.primefaces.Messages.addMessage;
import static com.primefaces.Messages.showWarn;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "tipoMB")

@ViewScoped

public class TipoManagedBean implements Serializable {

    private Tipo tipo;
    private TipoDAO tipoDAO = new TipoDAO();
    private List<Tipo> listTipo = new ArrayList<>();
    private int codTipo;
    private String nomTipo;
    

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct");
        
        this.tipo=new Tipo();
        this.listTipo=tipoDAO.getTipoArticulo();
        this.codTipo=0;
        this.nomTipo="XXXXXX";
        

    }

    public void SeleccionarTipo(Tipo t) {
        this.codTipo = t.getId();
        this.nomTipo = t.getTipo();
        this.tipo = t;

    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public TipoDAO getTipoDAO() {
        return tipoDAO;
    }

    public void setTipoDAO(TipoDAO tipoDAO) {
        this.tipoDAO = tipoDAO;
    }

    public List<Tipo> getListTipo() {
        return listTipo;
    }

    public void setListTipo(List<Tipo> listTipo) {
        this.listTipo = listTipo;
    }

    public int getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(int codTipo) {
        this.codTipo = codTipo;
    }

    public String getNomTipo() {
        return nomTipo;
    }

    public void setNomTipo(String nomTipo) {
        this.nomTipo = nomTipo;
    }

   

}
