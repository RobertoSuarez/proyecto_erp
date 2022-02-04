/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.SubProcesoDAO;
import com.produccion.dao.dSubprocesoDAO;
import com.produccion.models.Costo;
import com.produccion.models.ProcesoProduccion;
import com.produccion.models.SubProceso;
import com.produccion.models.dSubproceso;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author HP
 */
@Named(value = "SubProcesoMB")
@ViewScoped
public class SubProcesoMB implements Serializable {

    SubProceso sproceso;
    SubProcesoDAO subProcesoDAO;
    dSubprocesoDAO detalleSuprocesoDAO;
    Costo costo;
    private dSubproceso subDproceso;
    private List<ProcesoProduccion> listaProceso;
    private List<Costo> listaCostoDirecto;
    private List<Costo> listaCostoIndirecto;
    private List<Costo> NuevolistaCostoDirecto;
    private List<Costo> NuevolistaCostoIndirecto;
    List<dSubproceso> listaDsubprocesoDirecta;
    List<dSubproceso> listaDsubprocesoInirecta;
    float totalDirecto;
    float totalIndirecto;

    public SubProcesoMB() {
        sproceso = new SubProceso();
        subProcesoDAO = new SubProcesoDAO();
        detalleSuprocesoDAO = new dSubprocesoDAO();
        costo = new Costo();
        subDproceso = new dSubproceso();
        listaProceso = new ArrayList<>();
        listaCostoDirecto = new ArrayList<>();
        listaCostoIndirecto = new ArrayList<>();
        NuevolistaCostoDirecto = new ArrayList<>();
        NuevolistaCostoIndirecto = new ArrayList<>();
        listaDsubprocesoDirecta = new ArrayList<>();
        listaDsubprocesoInirecta = new ArrayList<>();
        sproceso.setCodigo_subproceso(subProcesoDAO.idSubproceso());
    }

    @PostConstruct
    public void init() {
        listaProceso = subProcesoDAO.getProcesosProduccion();
        listaCostoDirecto = detalleSuprocesoDAO.getCosto("Directo");
        listaCostoIndirecto = detalleSuprocesoDAO.getCosto("Indirecto");

    }

    public float getTotalDirecto() {
        return totalDirecto;
    }

    public void setTotalDirecto(float totalDirecto) {
        this.totalDirecto = totalDirecto;
    }

    public float getTotalIndirecto() {
        return totalIndirecto;
    }

    public void setTotalIndirecto(float totalIndirecto) {
        this.totalIndirecto = totalIndirecto;
    }

    public dSubproceso getSubDproceso() {
        return subDproceso;
    }

    public void setSubDproceso(dSubproceso subDproceso) {
        this.subDproceso = subDproceso;
    }

    public Costo getCosto() {
        return costo;
    }

    public void setCosto(Costo costo) {
        this.costo = costo;
    }

    public List<Costo> getNuevolistaCostoDirecto() {
        return NuevolistaCostoDirecto;
    }

    public void setNuevolistaCostoDirecto(List<Costo> NuevolistaCostoDirecto) {
        this.NuevolistaCostoDirecto = NuevolistaCostoDirecto;
    }

    public List<Costo> getNuevolistaCostoIndirecto() {
        return NuevolistaCostoIndirecto;
    }

    public void setNuevolistaCostoIndirecto(List<Costo> NuevolistaCostoIndirecto) {
        this.NuevolistaCostoIndirecto = NuevolistaCostoIndirecto;
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

    public List<ProcesoProduccion> getListaProceso() {
        return listaProceso;
    }

    public void setListaProceso(List<ProcesoProduccion> listaProceso) {
        this.listaProceso = listaProceso;
    }

    public SubProceso getSproceso() {
        return sproceso;
    }

    public void setSproceso(SubProceso sproceso) {
        this.sproceso = sproceso;
    }

    public void insertarSubProceso() {
        if (subProcesoDAO.insertarSubproceso(sproceso) > 0) {
            subProcesoDAO.insertardSubproceso(sproceso);
            llenarDetalleDirecto();
            for (dSubproceso subproceso : listaDsubprocesoDirecta) {
                subProcesoDAO.insertarDetalleSubproceso(subproceso);
            }
            llenarDetalleInirecto();
            for (dSubproceso subproceso : listaDsubprocesoInirecta) {
                subProcesoDAO.insertarDetalleSubproceso(subproceso);
            }
            FacesMessage msg = new FacesMessage("Se inserto con existo",null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void addDirecto() {

        NuevolistaCostoDirecto.add(costoD());
        costo = new Costo();

    }

    public Costo costoD() {
        for (Costo costoDirecto : listaCostoDirecto) {
            if (costo.getCodigo_costos() == costoDirecto.getCodigo_costos()) {
                costo.setNombre(costoDirecto.getNombre());
                costo.setDescripcion(costoDirecto.getDescripcion());
                costo.setCosto(costoDirecto.getCosto());
            }
        }
        return costo;
    }

    public Costo costoI() {
        for (Costo costoIndirecto : listaCostoIndirecto) {
            if (costo.getCodigo_costos() == costoIndirecto.getCodigo_costos()) {
                costo.setNombre(costoIndirecto.getNombre());
                costo.setDescripcion(costoIndirecto.getDescripcion());
                costo.setCosto(costoIndirecto.getCosto());
            }
        }
        return costo;
    }

    public void addIndirecto() {
        NuevolistaCostoIndirecto.add(costoI());
        costo = new Costo();
    }

    public void llenarDetalleDirecto() {
        for (Costo directo : NuevolistaCostoDirecto) {
            subDproceso.setCodigo_subproceso(sproceso.getCodigo_subproceso());
            subDproceso.setCodigo_costos(directo.getCodigo_costos());
            subDproceso.setCosto_mano_obra(directo.getCosto());
            listaDsubprocesoDirecta.add(subDproceso);
            subDproceso = new dSubproceso();
        }
    }

    public void llenarDetalleInirecto() {
        for (Costo indirecto : NuevolistaCostoIndirecto) {
            subDproceso.setCodigo_subproceso(sproceso.getCodigo_subproceso());
            subDproceso.setCodigo_costos(indirecto.getCodigo_costos());
            subDproceso.setCosto_indirecto(indirecto.getCosto());
            listaDsubprocesoInirecta.add(subDproceso);
            subDproceso = new dSubproceso();
        }
    }

    public void sumarIndirectos() {
        float totalI = 0;
        for (Costo indirecto : NuevolistaCostoIndirecto) {
            totalI += indirecto.getCosto();
        }
        totalIndirecto = totalI;
    }

    public void sumarDirectos() {
        float tD = 0;
        for (Costo directo : NuevolistaCostoDirecto) {
            tD += directo.getCosto();
        }
        totalDirecto = tD;
    }

    public void deleteFila(Costo directo) {
        NuevolistaCostoDirecto.remove(directo);
        sumarDirectos();
    }

    public void deleteFilaIndirecto(Costo directo) {
        NuevolistaCostoIndirecto.remove(directo);
        sumarIndirectos();
    }

}
