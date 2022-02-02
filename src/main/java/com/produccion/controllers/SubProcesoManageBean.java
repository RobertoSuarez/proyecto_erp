package com.produccion.controllers;

import com.produccion.dao.SubProcesoDAO;
import com.produccion.models.Costo;
import com.produccion.models.ProcesoProduccion;
import com.produccion.models.SubProceso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;

@ManagedBean(name = "subprocesoMB")
@ViewScoped
public class SubProcesoManageBean implements Serializable {

    private List<SubProceso> listaSubProceso;
    private List<ProcesoProduccion> listaProceso;
    private SubProcesoDAO subProcesoDAO;
    private SubProceso subProceso;
    private ProcesoProduccion procesoProduccion;
    private Costo costoProduccion;
    private List<Costo> listaCostoDirecto;
    private List<Costo> listaCostoIndirecto;
    private int id_subproceso;

    public int getId_subproceso() {
        return id_subproceso;
    }

    public void setId_subproceso(int id_subproceso) {
        this.id_subproceso = id_subproceso;
    }

    public List<Costo> getListaCostoDirecto() {
        return listaCostoDirecto;
    }

    public void setListaCostoDirecto(List<Costo> listaCostoDirecto) {
        this.listaCostoDirecto = listaCostoDirecto;
    }

    public List<Costo> getListaCostoIndirecto() {
        return listaCostoIndirecto;
    }

    public void setListaCostoIndirecto(List<Costo> listaCostoIndirecto) {
        this.listaCostoIndirecto = listaCostoIndirecto;
    }

    public Costo getCostoProduccion() {
        return costoProduccion;
    }

    public void setCostoProduccion(Costo costoProduccion) {
        this.costoProduccion = costoProduccion;
    }
    int idSubproceso;

    public int getIdSubproceso() {
        return idSubproceso;
    }

    public void setIdSubproceso(int idSubproceso) {
        this.idSubproceso = idSubproceso;
    }

    public ProcesoProduccion getProcesoProduccion() {
        return procesoProduccion;
    }

    public void setProcesoProduccion(ProcesoProduccion procesoProduccion) {
        this.procesoProduccion = procesoProduccion;
    }

    public List<ProcesoProduccion> getListaProceso() {
        return listaProceso;
    }

    public void setListaProceso(List<ProcesoProduccion> listaProceso) {
        this.listaProceso = listaProceso;
    }

    public SubProcesoManageBean() {
        subProceso = new SubProceso();
        subProcesoDAO = new SubProcesoDAO();
        listaSubProceso = new ArrayList<>();
        listaProceso = new ArrayList<>();
        procesoProduccion = new ProcesoProduccion();
        idSubproceso = (subProcesoDAO.idSubproceso());
        costoProduccion = new Costo();
        listaCostoDirecto = new ArrayList<>();
        listaCostoIndirecto = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listaProceso = subProcesoDAO.getProcesosProduccion();
        listaCostoDirecto = subProcesoDAO.getCosto("Directo");
        listaCostoIndirecto = subProcesoDAO.getCosto("Indirecto");

    }

    public void listarDirectos() {
        System.out.println(this.idSubproceso);

    }

    public List<SubProceso> getListaSubProceso() {
        return listaSubProceso;
    }

    public void setListaSubProceso(List<SubProceso> listaSubProceso) {
        this.listaSubProceso = listaSubProceso;
    }

    public SubProcesoDAO getSubProcesoDAO() {
        return subProcesoDAO;
    }

    public void setSubProcesoDAO(SubProcesoDAO subProcesoDAO) {
        this.subProcesoDAO = subProcesoDAO;
    }

    public SubProceso getSubProceso() {
        return subProceso;
    }

    public void setSubProceso(SubProceso subProceso) {
        this.subProceso = subProceso;
    }

}
