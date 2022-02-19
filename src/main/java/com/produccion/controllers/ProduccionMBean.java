/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.OrdenProduccionDAO;
import com.produccion.models.ArticuloFormula;
import com.produccion.models.CentroCosto;
import com.produccion.models.FormulaProduccion;
import com.produccion.models.OrdenTrabajo;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author HP
 */
@Named(value = "produccionMBean")
@ViewScoped
public class ProduccionMBean implements Serializable {

    /**
     * Creates a new instance of ProduccionMBean
     */
    private OrdenTrabajo ordenTrabajo;
    OrdenProduccionDAO ordenDao;
    private List<OrdenTrabajo> listaProducto;
    private List<OrdenTrabajo> listaFormula;
    private List<CentroCosto> listaCentro;
    private List<ArticuloFormula> listaMateriaPrima;
    private List<ArticuloFormula> detalleListaMateriaPrima;
    private List<FormulaProduccion> listaCostos;

    public ProduccionMBean() {
        ordenTrabajo = new OrdenTrabajo();
        ordenDao = new OrdenProduccionDAO();

        listaCostos = new ArrayList<>();
        listaProducto = new ArrayList<>();
        listaFormula = new ArrayList<>();
        listaCentro = new ArrayList<>();
        listaMateriaPrima = new ArrayList<>();
        detalleListaMateriaPrima = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        ordenTrabajo.setCodigo_orden(idOrden());
        listaProducto = ordenDao.getListaProducto(ordenTrabajo.getCodigo_orden());
        listaCentro = ordenDao.getListaCentro();

    }

    public int idOrden() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        Integer categorySelected = new Integer((String) params.get("orden"));
        return categorySelected;
    }

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public List<OrdenTrabajo> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<OrdenTrabajo> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public List<OrdenTrabajo> getListaFormula() {
        return listaFormula;
    }

    public void setListaFormula(List<OrdenTrabajo> listaFormula) {
        this.listaFormula = listaFormula;
    }

    public List<CentroCosto> getListaCentro() {
        return listaCentro;
    }

    public void setListaCentro(List<CentroCosto> listaCentro) {
        this.listaCentro = listaCentro;
    }

    public List<ArticuloFormula> getListaMateriaPrima() {
        return listaMateriaPrima;
    }

    public void setListaMateriaPrima(List<ArticuloFormula> listaMateriaPrima) {
        this.listaMateriaPrima = listaMateriaPrima;
    }

    public List<ArticuloFormula> getDetalleListaMateriaPrima() {
        return detalleListaMateriaPrima;
    }

    public void setDetalleListaMateriaPrima(List<ArticuloFormula> detalleListaMateriaPrima) {
        this.detalleListaMateriaPrima = detalleListaMateriaPrima;
    }

    public List<FormulaProduccion> getListaCostos() {
        return listaCostos;
    }

    public void setListaCostos(List<FormulaProduccion> listaCostos) {
        this.listaCostos = listaCostos;
    }

    public void llenarFormula() {
        listaFormula = ordenDao.getListaFormula(ordenTrabajo.getCodigo_producto());
        llenarCantidad();
    }

    public void llenarCantidad() {
        float cantidad = 0;
        for (OrdenTrabajo producto : listaProducto) {
            if (producto.getCodigo_producto() == ordenTrabajo.getCodigo_producto()) {
                cantidad = producto.getCantidad();
            }
        }
        ordenTrabajo.setCantidad(cantidad);

    }

    public void llenarProceso() {
        ordenTrabajo.setState(ordenDao.renderProduccionDetallada(ordenTrabajo.getCodigo_formula()));
        ordenTrabajo.setNombre_proceso(ordenDao.getProceso(ordenTrabajo.getCodigo_formula()));
        listaMateriaPrima = ordenDao.getListaConsumoMateriales(ordenTrabajo.getCodigo_formula(), ordenTrabajo.getCantidad());
        costoMateriaPrima();
    }

    public void costoMateriaPrima() {
        float materiaPrima=0;
        float costosD=0;
        float costosI=0;
        detalleListaMateriaPrima = new ArrayList<>();
        for (ArticuloFormula materiales : listaMateriaPrima) {
            materiales.setTotal(materiales.getCantidad() * materiales.getCosto());
            detalleListaMateriaPrima.add(materiales);
            materiaPrima+=materiales.getTotal();
        }
        ordenTrabajo.setTotalMateria(materiaPrima);
        listaCostos = ordenDao.getListaCosto(ordenTrabajo.getCodigo_formula(), ordenTrabajo.getCantidad());
        
        for(FormulaProduccion costoP:listaCostos){
            costosD+=costoP.getMODUnidad();
            costosI+=costoP.getCIFUnidad();
        }
        ordenTrabajo.setTotalMOD(costosD);     
        ordenTrabajo.setTotalCIF(costosI);
        ordenTrabajo.setCostoTotal(ordenTrabajo.getTotalMateria()+ordenTrabajo.getTotalMOD()+ordenTrabajo.getTotalCIF());
        ordenTrabajo.setCostoUnitario(ordenTrabajo.getCostoTotal()/ordenTrabajo.getCantidad());
    }

}
