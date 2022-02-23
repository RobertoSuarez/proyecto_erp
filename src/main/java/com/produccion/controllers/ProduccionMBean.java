/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.OrdenProduccionDAO;
import com.produccion.models.ArticuloFormula;
import com.produccion.models.CentroCosto;
import com.produccion.models.FormulaMateriales;
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
import java.util.Timer;
import java.util.TimerTask;
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
    float materiaPrimaCosto = 0;
    float costosD = 0;
    float costosI = 0;
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
    private List<FormulaProduccion> materiaPrima;
    private List<FormulaMateriales> listaMateriaPrimaAdicional;
    private List<FormulaMateriales> listaMaterialesConfirmados;
    List<ArticuloFormula> listaAdicionales;
    private FormulaMateriales materialesFormula;
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

    public ProduccionMBean() {
        ordenTrabajo = new OrdenTrabajo();
        ordenDao = new OrdenProduccionDAO();
        ordenAsiento = new SolicitudOrden();
        materialesFormula = new FormulaMateriales();

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
        listaCformula = new ArrayList<>();
        materiaPrima = new ArrayList<>();
        listaMateriaPrimaAdicional = new ArrayList<>();
        listaMaterialesConfirmados = new ArrayList<>();
        listaAdicionales = new ArrayList<>();
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

    public List<FormulaMateriales> getListaMateriaPrimaAdicional() {
        return listaMateriaPrimaAdicional;
    }

    public void setListaMateriaPrimaAdicional(List<FormulaMateriales> listaMateriaPrimaAdicional) {
        this.listaMateriaPrimaAdicional = listaMateriaPrimaAdicional;
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

    public List<FormulaProduccion> getMateriaPrima() {
        return materiaPrima;
    }

    public void setMateriaPrima(List<FormulaProduccion> materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    public List<FormulaMateriales> getListaMaterialesConfirmados() {
        return listaMaterialesConfirmados;
    }

    public void setListaMaterialesConfirmados(List<FormulaMateriales> listaMaterialesConfirmados) {
        this.listaMaterialesConfirmados = listaMaterialesConfirmados;
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
        listaMaterialesConfirmados = new ArrayList<>();
        ordenTrabajo.setState(ordenDao.renderProduccionDetallada(ordenTrabajo.getCodigo_formula()));
        ordenTrabajo.setNombre_proceso(ordenDao.getProceso(ordenTrabajo.getCodigo_formula()));
        listaMateriaPrima = ordenDao.getListaConsumoMateriales(ordenTrabajo.getCodigo_formula(), ordenTrabajo.getCantidad());
        materiaPrima = ordenDao.getArticulos();
        costoMateriaPrima();
    }

    public void costoMateriaPrima() {

        detalleListaMateriaPrima = new ArrayList<>();
        for (ArticuloFormula materiales : listaMateriaPrima) {
            materiales.setTotal(materiales.getCantidad() * materiales.getCosto());
            detalleListaMateriaPrima.add(materiales);
            materiaPrimaCosto += materiales.getTotal();
        }

        ordenTrabajo.setTotalMateria(materiaPrimaCosto);
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
        } else if (verificaCampos()) {
            showWarn("Debe de ingresar valores en la materia prima que agrego.");
        } else {
            if (ordenDao.registrarProduccion(ordenTrabajo) > 0) {
                if (ordenDao.actualizarOrden(ordenTrabajo.getCodigo_registro()) > 0) {

                    if (ordenDao.verificaOrden(ordenTrabajo.getCodigo_orden())) {
                        if (ordenDao.actualizaEstado(ordenTrabajo.getCodigo_orden()) > 0) {
                            float materiales = 0, indirectos = 0, directos = 0;
                            listaAdicionales = ordenDao.getArticuloAdicionales(ordenTrabajo.getCodigo_orden());

                            ordenAsiento = ordenDao.calculaProduccion(ordenTrabajo.getCodigo_orden());
                            ordenAsiento = ordenDao.movimientosProduccion(ordenTrabajo.getCodigo_orden());
                            listaCostoProduccion = ordenDao.listaCostoProduccion(ordenTrabajo.getCodigo_orden());
                            listaCformula = ordenDao.listaMaterialesFormula(ordenTrabajo.getCodigo_orden());
                            listaCostosDirectos = new ArrayList<>();
                            listaCostosIndirectos = new ArrayList<>();
                            listaMateriaPrima = new ArrayList<>();

                            for (OrdenTrabajo orden : listaCostoProduccion) {
                                listaCostosDirectos = ordenDao.getListaCostos(orden.getCodigo_formula(), orden.getCodigo_registro(), orden.getCantidad(), "cmdunitario");
                                listaCostosIndirectos = ordenDao.getListaCostos(orden.getCodigo_formula(), orden.getCodigo_registro(), orden.getCantidad(), "cifunitario");
                                for (FormulaProduccion directo : listaCostosDirectos) {
                                    listaMovimientos.add(directo);
                                    directos += directo.getCosto();
                                }
                                for (FormulaProduccion indirecto : listaCostosIndirectos) {
                                    listaMovimientos.add(indirecto);
                                    indirectos += indirecto.getCosto();
                                }
                                listaCostosDirectos = new ArrayList<>();
                                listaCostosIndirectos = new ArrayList<>();

                            }
                            for (ArticuloFormula formula : listaCformula) {
                                listaMateriaPrima = ordenDao.getListaConsumoMateriales(formula.getId(), formula.getCantidad());
                                for (ArticuloFormula articulo : listaMateriaPrima) {
                                    listaMovimientos.add(new FormulaProduccion(articulo.getDescripcion(), articulo.getCantidad() * articulo.getCosto(), articulo.getIdSubcuenta()));
                                    materiales += articulo.getCantidad() * articulo.getCosto();
                                }
                                listaMateriaPrima = new ArrayList<>();
                            }
                            for (ArticuloFormula adicionales : listaAdicionales) {
                                listaMovimientos.add(new FormulaProduccion(adicionales.getDescripcion(), adicionales.getCantidad() * adicionales.getCosto(), adicionales.getIdSubcuenta()));
                                materiales += adicionales.getCantidad() * adicionales.getCosto();
                            }

                            ordenDao.insertAsiento(ordenAsiento, listaMovimientos, directos, indirectos, materiales);
                        }
                    } else {
                        showWarn("No se pudo generar la Orden de producción");
                    }
                    showInfo("Orden de producción registrada");
                    vaciar();
                } else {
                    showWarn("No se pudo generar la Orden de producción");
                }

            } else {
                showWarn("No se pudo generar la Orden de producción");
            }

        }

    }

    public void vaciar() {
        int orden = ordenTrabajo.getCodigo_orden();
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
        listaMaterialesConfirmados = new ArrayList<>();
        materiaPrima = new ArrayList<>();
        ordenTrabajo.setCodigo_orden(orden);
        if (ordenDao.verificaOrden(ordenTrabajo.getCodigo_orden())) {
            showInfo("Orden de producción Finaliza, regrese a las listas de ordenes de trabajo.");
            Timer timer = new Timer();
            TimerTask tarea = new TimerTask() {
                @Override
                public void run() {
                    redireccion();
                }
            };
            timer.schedule(tarea, 5000);
            ordenDao = new OrdenProduccionDAO();
        } else {
            listaProducto = ordenDao.getListaProducto(ordenTrabajo.getCodigo_orden());
            listaCentro = ordenDao.getListaCentro();
        }
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

    private void redireccion() {
        try {
            externalContext.redirect("/proyecto_erp/View/produccion/listaOrdenProduccion.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ProduccionMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addMateriales2(FormulaProduccion producto) {
        FormulaMateriales verifica = llenarMateriales(producto);
        if (verifica != null) {
            if (producto.isVerifica()) {
                listaMateriaPrimaAdicional.add(verifica);
            }
        }
    }

    public FormulaMateriales llenarMateriales(FormulaProduccion materiales) {
        if (materiales.isVerifica()) {
            materialesFormula = new FormulaMateriales(materiales.getNombre(),
                    materiales.getDescripcionProducto(), ordenTrabajo.getCodigo_registro(), materiales.getCodigo_producto(), materiales.getCosto());
            return materialesFormula;
        } else {
            FormulaMateriales buscar = new FormulaMateriales(materiales.getNombre(),
                    materiales.getDescripcionProducto(), ordenTrabajo.getCodigo_registro(),
                    materiales.getCodigo_producto(), materiales.getCosto());
            for (FormulaMateriales lista : listaMateriaPrimaAdicional) {
                if (lista.getCodigoProducto() == buscar.getCodigoProducto()) {
                    listaMateriaPrimaAdicional.remove(lista);
                }

            }
            return null;
        }

    }

    public void llenaMaterialiesConfirmados() {
        for (FormulaMateriales materiales : listaMateriaPrimaAdicional) {
            if (materiales != null) {
                if (duplicidadDatos(materiales)) {
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "",
                                    "El producto ya se encuentra agregado"));
                } else {
                    listaMaterialesConfirmados.add(materiales);
                }
            }
        }
    }

    public boolean duplicidadDatos(FormulaMateriales datos) {
        boolean confirmacion = false;
        for (FormulaMateriales materiales : listaMaterialesConfirmados) {
            if (materiales.getCodigoProducto() == datos.getCodigoProducto()) {
                confirmacion = true;
            }
        }
        return confirmacion;
    }

    public void deleteFila(FormulaMateriales producto) {
//        for (ArticuloFormula eliminarArticulo : detalleListaMateriaPrima) {
//            if (producto.getCodigoProducto() == eliminarArticulo.getId()) {
//                detalleListaMateriaPrima.remove(eliminarArticulo);
//                materiaPrimaCosto -= eliminarArticulo.getTotal();
//            }
//        }
//        ordenTrabajo.setTotalMateria(materiaPrimaCosto);
//        ordenTrabajo.setCostoTotal(ordenTrabajo.getTotalMateria() + ordenTrabajo.getTotalMOD() + ordenTrabajo.getTotalCIF());
//        ordenTrabajo.setCostoUnitario(ordenTrabajo.getCostoTotal() / ordenTrabajo.getCantidad());
        listaMaterialesConfirmados.remove(producto);

    }

    public boolean verificaCampos() {
        boolean verifica = false;
        for (FormulaMateriales materiaAdicional : listaMaterialesConfirmados) {
            if (materiaAdicional.getCantidad() <= 0 || "".equals(materiaAdicional.getUnidadMedida())) {
                verifica = true;
                break;
            }
        }
        return verifica;
    }

    public void llenarMaterialesAdicionales() {
        boolean verifica = false;
        for (FormulaMateriales materiaAdicional : listaMaterialesConfirmados) {
            for (ArticuloFormula listaexistencia : detalleListaMateriaPrima) {
                if (materiaAdicional.getCodigoProducto() == listaexistencia.getId()) {
                    verifica = true;
                    break;
                }
            }
        }
        if (!verificaCampos()) {
            if (!verifica) {
                for (FormulaMateriales materiaAdicional : listaMaterialesConfirmados) {
                    detalleListaMateriaPrima.add(new ArticuloFormula(materiaAdicional.getCodigoProducto(), materiaAdicional.getNombre(), materiaAdicional.getDescripcion(),
                            materiaAdicional.getPrecio(), materiaAdicional.getCantidad(), materiaAdicional.getCantidad() * materiaAdicional.getPrecio()));
                    materiaPrimaCosto += materiaAdicional.getCantidad() * materiaAdicional.getPrecio();
                }
                ordenTrabajo.setTotalMateria(materiaPrimaCosto);
                ordenTrabajo.setCostoTotal(ordenTrabajo.getTotalMateria() + ordenTrabajo.getTotalMOD() + ordenTrabajo.getTotalCIF());
                ordenTrabajo.setCostoUnitario(ordenTrabajo.getCostoTotal() / ordenTrabajo.getCantidad());
                float valor;
                for (FormulaMateriales materiaAdicional : listaMaterialesConfirmados) {
                    valor = ordenDao.verificaProducto(ordenTrabajo.getCodigo_registro(), materiaAdicional.getCodigoProducto());
                    if (valor > 0) {
                        valor += materiaAdicional.getCantidad();
                        ordenDao.actualizaAdicionales(valor, ordenTrabajo.getCodigo_registro(), materiaAdicional.getCodigoProducto());
                        showInfo("Productos agregados con exito.");
                    } else {
                        ordenDao.registrarDetalleProduccion(materiaAdicional, ordenTrabajo.getCodigo_registro());
                        showInfo("Productos agregados con exito.");
                    }
                }
                listaMaterialesConfirmados = new ArrayList<>();
            } else {
                for (FormulaMateriales materiaAdicional : listaMaterialesConfirmados) {
                    for (ArticuloFormula listaexistencia : detalleListaMateriaPrima) {
                        if (materiaAdicional.getCodigoProducto() == listaexistencia.getId()) {
                            listaexistencia.setCantidad(materiaAdicional.getCantidad() + listaexistencia.getCantidad());
                            listaexistencia.setTotal(listaexistencia.getCantidad() * listaexistencia.getCosto());
                            materiaPrimaCosto += materiaAdicional.getCantidad() * listaexistencia.getCosto();
                        }
                    }
                }
                ordenTrabajo.setTotalMateria(materiaPrimaCosto);
                ordenTrabajo.setCostoTotal(ordenTrabajo.getTotalMateria() + ordenTrabajo.getTotalMOD() + ordenTrabajo.getTotalCIF());
                ordenTrabajo.setCostoUnitario(ordenTrabajo.getCostoTotal() / ordenTrabajo.getCantidad());
                float valor;
                for (FormulaMateriales materiaAdicional : listaMaterialesConfirmados) {
                    valor = ordenDao.verificaProducto(ordenTrabajo.getCodigo_registro(), materiaAdicional.getCodigoProducto());
                    if (valor > 0) {
                        valor += materiaAdicional.getCantidad();
                        ordenDao.actualizaAdicionales(valor, ordenTrabajo.getCodigo_registro(), materiaAdicional.getCodigoProducto());
                        showInfo("Productos agregados con exito.");
                    } else {
                        ordenDao.registrarDetalleProduccion(materiaAdicional, ordenTrabajo.getCodigo_registro());
                        showInfo("Productos agregados con exito.");

                    }
                }
                listaMaterialesConfirmados = new ArrayList<>();
            }

        } else {
            showWarn("Debe de ingresar valores en la materia prima que agrego.");
        }

    }

    public void cancelarOrden() throws IOException {
        ordenDao.cancelarOrden(ordenTrabajo.getCodigo_registro());
        externalContext.redirect("/proyecto_erp/View/produccion/listaOrdenProduccion.xhtml");
    }

}
