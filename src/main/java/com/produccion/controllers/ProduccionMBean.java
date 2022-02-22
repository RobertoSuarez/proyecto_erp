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
import com.produccion.models.SolicitudOrden;
import java.io.IOException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
    private SolicitudOrden ordenAsiento;
    OrdenProduccionDAO ordenDao;
    private List<OrdenTrabajo> listaProducto;
    private List<OrdenTrabajo> listaFormula;
    private List<CentroCosto> listaCentro;
    private List<ArticuloFormula> listaMateriaPrima;
    private List<ArticuloFormula> detalleListaMateriaPrima;
    private List<FormulaProduccion> listaCostos;
    private List<FormulaProduccion> listaCostosDirectos;
    private List<FormulaProduccion> listaCostosIndirectos;
    private List<FormulaProduccion> listaMovimientos;
    private List<OrdenTrabajo> listaCostoProduccion;
    private List<ArticuloFormula> listaCformula;
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

    public ProduccionMBean() {
        ordenTrabajo = new OrdenTrabajo();
        ordenDao = new OrdenProduccionDAO();
        ordenAsiento = new SolicitudOrden();

        listaCostos = new ArrayList<>();
        listaProducto = new ArrayList<>();
        listaFormula = new ArrayList<>();
        listaCentro = new ArrayList<>();
        listaMateriaPrima = new ArrayList<>();
        detalleListaMateriaPrima = new ArrayList<>();
        listaCostosDirectos = new ArrayList<>();
        listaCostosIndirectos = new ArrayList<>();
        listaMovimientos = new ArrayList<>();
        listaCostoProduccion = new ArrayList<>();
        listaCformula=new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        int id = idOrden();
        if (id > 0) {
            ordenTrabajo.setCodigo_orden(id);
            listaProducto = ordenDao.getListaProducto(ordenTrabajo.getCodigo_orden());
            listaCentro = ordenDao.getListaCentro();
        } else {
            try {
                externalContext.redirect("/proyecto_erp/View/produccion/listaOrdenProduccion.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ProduccionMBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public int idOrden() {
        Map params = externalContext.getRequestParameterMap();
        if (params.get("orden") != null) {
            Integer categorySelected = new Integer((String) params.get("orden"));
            return categorySelected;
        } else {
            return 0;
        }

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
        setRegistro();
        llenarCantidad();
    }

    public List<FormulaProduccion> getListaCostosDirectos() {
        return listaCostosDirectos;
    }

    public void setListaCostosDirectos(List<FormulaProduccion> listaCostosDirectos) {
        this.listaCostosDirectos = listaCostosDirectos;
    }

    public List<FormulaProduccion> getListaCostosIndirectos() {
        return listaCostosIndirectos;
    }

    public void setListaCostosIndirectos(List<FormulaProduccion> listaCostosIndirectos) {
        this.listaCostosIndirectos = listaCostosIndirectos;
    }

    public SolicitudOrden getOrdenAsiento() {
        return ordenAsiento;
    }

    public void setOrdenAsiento(SolicitudOrden ordenAsiento) {
        this.ordenAsiento = ordenAsiento;
    }

    public List<FormulaProduccion> getListaMovimientos() {
        return listaMovimientos;
    }

    public void setListaMovimientos(List<FormulaProduccion> listaMovimientos) {
        this.listaMovimientos = listaMovimientos;
    }

    public List<OrdenTrabajo> getListaCostoProduccion() {
        return listaCostoProduccion;
    }

    public void setListaCostoProduccion(List<OrdenTrabajo> listaCostoProduccion) {
        this.listaCostoProduccion = listaCostoProduccion;
    }

    public List<ArticuloFormula> getListaCformula() {
        return listaCformula;
    }

    public void setListaCformula(List<ArticuloFormula> listaCformula) {
        this.listaCformula = listaCformula;
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
        float materiaPrima = 0;
        float costosD = 0;
        float costosI = 0;
        detalleListaMateriaPrima = new ArrayList<>();
        for (ArticuloFormula materiales : listaMateriaPrima) {
            materiales.setTotal(materiales.getCantidad() * materiales.getCosto());
            detalleListaMateriaPrima.add(materiales);
            materiaPrima += materiales.getTotal();
        }
        ordenTrabajo.setTotalMateria(materiaPrima);
        listaCostosDirectos = ordenDao.getListaCostos(ordenTrabajo.getCodigo_formula(), ordenTrabajo.getCodigo_registro(), ordenTrabajo.getCantidad(), "cmdunitario");
        listaCostosIndirectos = ordenDao.getListaCostos(ordenTrabajo.getCodigo_formula(), ordenTrabajo.getCodigo_registro(), ordenTrabajo.getCantidad(), "cifunitario");
        listaCostos = ordenDao.getListaCosto(ordenTrabajo.getCodigo_formula(), ordenTrabajo.getCantidad());

        for (FormulaProduccion costoP : listaCostos) {
            costosD += costoP.getMODUnidad();
            costosI += costoP.getCIFUnidad();
            ordenTrabajo.setTiempo(costoP.getTiempoUnidad());
        }
        ordenTrabajo.setTotalMOD(costosD);
        ordenTrabajo.setTotalCIF(costosI);
        ordenTrabajo.setCostoTotal(ordenTrabajo.getTotalMateria() + ordenTrabajo.getTotalMOD() + ordenTrabajo.getTotalCIF());
        ordenTrabajo.setCostoUnitario(ordenTrabajo.getCostoTotal() / ordenTrabajo.getCantidad());
    }

    public void setRegistro() {
        for (OrdenTrabajo orden : listaProducto) {
            if (orden.getCodigo_producto() == ordenTrabajo.getCodigo_producto()) {
                ordenTrabajo.setCodigo_registro(orden.getCodigo_registro());
                break;
            }
        }
    }

    public void registrarOrden() {
        if (ordenTrabajo.getFecha_inicio() == null) {
            showWarn("Ingrese una fecha de inicio");
        } else if (ordenTrabajo.getFecha_fin() == null) {
            showWarn("Ingrese una de fin de producción");
        } else if (ordenTrabajo.getCodigo_producto() < 1) {
            showWarn("Seleccione un producto");
        } else if (ordenTrabajo.getCodigo_formula() < 1) {
            showWarn("Seleccione una formula");
        } else if (ordenTrabajo.getCodigo_centro_trabajo() < 1) {
            showWarn("Seleccione un centro de costo");
        } else if ("".equals(ordenTrabajo.getDescripcion())) {
            showWarn("Ingrese una descripción");
        } else {
            if (ordenDao.registrarProduccion(ordenTrabajo) > 0) {
                ordenDao.actualizarOrden(ordenTrabajo.getCodigo_registro());
                if (ordenDao.verificaOrden(ordenTrabajo.getCodigo_orden())) {
                    ordenDao.actualizaEstado(ordenTrabajo.getCodigo_orden());
                    ordenAsiento = ordenDao.calculaProduccion(ordenTrabajo.getCodigo_orden());
                    ordenAsiento = ordenDao.movimientosProduccion(ordenTrabajo.getCodigo_orden());
                    listaCostoProduccion = ordenDao.listaCostoProduccion(ordenTrabajo.getCodigo_orden());
                    listaCformula=ordenDao.listaMaterialesFormula(ordenTrabajo.getCodigo_orden());
                    listaCostosDirectos = new ArrayList<>();
                    listaCostosIndirectos = new ArrayList<>();
                    listaMateriaPrima = new ArrayList<>();
                    float materiales=0,indirectos=0,directos=0;
                    for (OrdenTrabajo orden : listaCostoProduccion) {
                        listaCostosDirectos = ordenDao.getListaCostos(orden.getCodigo_formula(), orden.getCodigo_registro(), orden.getCantidad(), "cmdunitario");
                        listaCostosIndirectos = ordenDao.getListaCostos(orden.getCodigo_formula(), orden.getCodigo_registro(), orden.getCantidad(), "cifunitario");
                        for (FormulaProduccion directo : listaCostosDirectos) {
                            listaMovimientos.add(directo);
                            directos+=directo.getCosto();
                        }
                        for (FormulaProduccion indirecto : listaCostosIndirectos) {
                            listaMovimientos.add(indirecto);
                            indirectos+=indirecto.getCosto();
                        }
                        listaCostosDirectos = new ArrayList<>();
                        listaCostosIndirectos = new ArrayList<>();

                    }
                    for(ArticuloFormula formula:listaCformula){
                        listaMateriaPrima=ordenDao.getListaConsumoMateriales(formula.getId(), formula.getCantidad());
                        for(ArticuloFormula articulo:listaMateriaPrima){
                            listaMovimientos.add(new FormulaProduccion(articulo.getDescripcion(),articulo.getCantidad() * articulo.getCosto(),articulo.getIdSubcuenta()) );
                            materiales+=articulo.getCantidad() * articulo.getCosto();
                        }
                        listaMateriaPrima = new ArrayList<>();
                    }
                    ordenDao.insertAsiento(ordenAsiento,listaMovimientos,directos,indirectos,materiales);

                }
                showInfo("Orden de producción registrada");
                vaciar();
            } else {
                showWarn("No se pudo generar la Orden de producción");
            }

        }

    }

    public void vaciar() {
        ordenTrabajo = new OrdenTrabajo();
        ordenDao = new OrdenProduccionDAO();

        listaCostos = new ArrayList<>();
        listaProducto = new ArrayList<>();
        listaFormula = new ArrayList<>();
        listaCentro = new ArrayList<>();
        listaMateriaPrima = new ArrayList<>();
        detalleListaMateriaPrima = new ArrayList<>();
        listaCostosDirectos = new ArrayList<>();
        listaCostosIndirectos = new ArrayList<>();
//        ordenTrabajo.setCodigo_orden(idOrden());
//        try {
//            externalContext.redirect("/proyecto_erp/View/produccion/listaOrdenProduccion.xhtml");
////        ordenTrabajo.setCodigo_orden(idOrden());
////        listaProducto = ordenDao.getListaProducto(ordenTrabajo.getCodigo_orden());
////        listaCentro = ordenDao.getListaCentro();
//        } catch (IOException ex) {
//            Logger.getLogger(ProduccionMBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void showInfo(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, "Exito", message);
    }

    public void showWarn(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, "Advertencia", message);
    }

    public void showError(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, "Error", message);
    }

}
