/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.CentroCostoDAO;
import com.produccion.models.CentroCosto;
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
@ManagedBean(name = "centroMB")
@ViewScoped
public class CentroCostoManageBean implements Serializable{
    
    private List<CentroCosto> listaCentro = new ArrayList<>();
    private CentroCostoDAO centroCostoDAO;
    private CentroCosto centroCosto;
    private CentroCosto selectCosto;

    public CentroCostoManageBean() {
        centroCosto = new CentroCosto();
        centroCostoDAO = new CentroCostoDAO();
    }
    
    @PostConstruct
    public void init(){
        System.out.println("PostConstruct");
        listaCentro = centroCostoDAO.getCentroCosots();
    }
    
    public void insertar() {
        try {
            if ("".equals(centroCosto.getIdentificador())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else if ("".equals(centroCosto.getNombre())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(centroCosto.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                this.centroCostoDAO.insertarc(centroCosto);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Dato Agregado"));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
        //PrimeFaces.current().executeScript("PF('nuevoProcesoPrincDialog').hide()");
        PrimeFaces.current().ajax().update("dtCentroCosto");
    }
    
    public void editar() {
        try {
            if ("".equals(centroCosto.getIdentificador())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Codigo"));
            } else if ("".equals(centroCosto.getNombre())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(centroCosto.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                this.centroCostoDAO.updatec(centroCosto);
                System.out.println(centroCosto.getCodigo_centroc());
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Guardado"));
                PrimeFaces.current().executeScript("PF('centroEditDialog').hide()");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
        
        PrimeFaces.current().ajax().update(":form-princ:dtProcesoPrin");
    }
    
    public void eliminar() {
        try {
            this.centroCostoDAO.deletec(centroCosto, centroCosto.getIdentificador());
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Eliminado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al eliminar"));
        }
    }

    public List<CentroCosto> getListaCentro() {
        return listaCentro;
    }

    public void setListaCentro(List<CentroCosto> listaCentro) {
        this.listaCentro = listaCentro;
    }
    
    public void aleatorioIdentiCosto() {
        String uuid = java.util.UUID.randomUUID().toString().substring(4, 7).toUpperCase();
        String uuid2 = java.util.UUID.randomUUID().toString().substring(4, 7);
        this.centroCosto.setIdentificador("CC-" + uuid + uuid2);
    }
    
    public void openNew() {
        this.selectCosto = new CentroCosto();
        aleatorioIdentiCosto();
    }

    public CentroCostoDAO getCentroCostoDAO() {
        return centroCostoDAO;
    }

    public void setCentroCostoDAO(CentroCostoDAO centroCostoDAO) {
        this.centroCostoDAO = centroCostoDAO;
    }

    public CentroCosto getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(CentroCosto centroCosto) {
        this.centroCosto = centroCosto;
    }

    public CentroCosto getSelectCosto() {
        return selectCosto;
    }

    public void setSelectCosto(CentroCosto selectCosto) {
        this.selectCosto = selectCosto;
    }
    
    
}
