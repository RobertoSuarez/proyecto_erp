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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

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

    public CostoManageBean() {
        costoDAO = new CostoDAO();
        costo = new Costo();
    }
    
    @PostConstruct
    public void init(){
        System.out.println("PostConstruct");
        listaCosto = costoDAO.getCosto();
    }
    
    public void insertar() {
        try {
            if ("".equals(costo.getIdentificador())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else if ("".equals(costo.getNombre())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(costo.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                this.costoDAO.insertarCosto(costo);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Datos Agregados"));
                PrimeFaces.current().executeScript("PF('agregarCostos').hide()");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
        //PrimeFaces.current().executeScript("PF('agregarCostos').hide()");
        //PrimeFaces.current().ajax().update("dtCosto");
    }
    
    public void editar() {
        try {
            if ("".equals(costo.getIdentificador())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Codigo"));
            } else if ("".equals(costo.getNombre())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(costo.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                this.costoDAO.updateCosto(costo);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Datos Guardados"));
                PrimeFaces.current().executeScript("PF('costoEditDialog').hide()");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
        
        PrimeFaces.current().ajax().update(":form-princ:dtCosto");
    }
    
    public void eliminar() {
        try {
            this.costoDAO.deletecosto(costo, costo.getIdentificador());
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Eliminado"));
            PrimeFaces.current().executeScript("PF('agregarCostos').hide()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al eliminar"));
        }
    }
    
    public void aleatorioIdentiCosto() {
        String uuid = java.util.UUID.randomUUID().toString().substring(4, 7).toUpperCase();
        String uuid2 = java.util.UUID.randomUUID().toString().substring(4, 7);
        this.costo.setIdentificador("CT-" + uuid + uuid2);
    }
    
    public void openNew() {
        this.selectCosto = new Costo();
        aleatorioIdentiCosto();
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
