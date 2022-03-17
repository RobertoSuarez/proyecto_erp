/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.OrdenProduccionDAO;
import com.produccion.models.ArticuloFormula;
import com.produccion.models.CentroCosto;
import com.produccion.models.CentroTrabajo;
import com.produccion.models.FormulaMateriales;
import com.produccion.models.FormulaProduccion;
import com.produccion.models.OrdenTrabajo;
import com.produccion.models.SolicitudOrden;
import java.io.IOException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.commons.math3.util.Precision;

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
    private List<CentroTrabajo> listaCentroTiempo;
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
        listaCentroTiempo = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        int id = idOrden();
        if (id > 0) {
            ordenTrabajo.setCodigo_orden(id);
            ordenTrabajo.setCodigo_bodega(ordenDao.bodega(ordenTrabajo.getCodigo_orden()));
            listaProducto = ordenDao.getListaProducto(ordenTrabajo.getCodigo_orden(), "P");
            listaCentro = ordenDao.getListaCentro();
        } else {
            try {
                externalContext.redirect("../produccion/listaOrdenProduccion.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ProduccionMBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public int idOrden() {
        try {
            Map params = externalContext.getRequestParameterMap();
            params = externalContext.getRequestParameterMap();
            Integer categorySelected = new Integer((String) params.get("orden"));
            return categorySelected;
        } catch (NumberFormatException e) {
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

    public List<FormulaMateriales> getListaMateriaPrimaAdicional() {
        return listaMateriaPrimaAdicional;
    }

    public void setListaMateriaPrimaAdicional(List<FormulaMateriales> listaMateriaPrimaAdicional) {
        this.listaMateriaPrimaAdicional = listaMateriaPrimaAdicional;
    }

    public void setListaCostos(List<FormulaProduccion> listaCostos) {
        this.listaCostos = listaCostos;
    }

    public List<CentroTrabajo> getListaCentroTiempo() {
        return listaCentroTiempo;
    }

    public void setListaCentroTiempo(List<CentroTrabajo> listaCentroTiempo) {
        this.listaCentroTiempo = listaCentroTiempo;
    }

    public void llenarFormula() {
        if (ordenTrabajo.getCodigo_producto() == 0) {
            vaciar();
        } else {
            listaFormula = ordenDao.getListaFormula(ordenTrabajo.getCodigo_producto());
            setRegistro();
            llenarCantidad();
            if (ordenDao.registroExistencia(ordenTrabajo.getCodigo_registro()) > 0) {
                ordenDao.cancelarOrden(ordenTrabajo.getCodigo_registro());
            }
        }
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
        materiaPrimaCosto = 0;
        costosD = 0;
        costosI = 0;
        ordenTrabajo.setState(ordenDao.renderProduccionDetallada(ordenTrabajo.getCodigo_formula()));
        ordenTrabajo.setNombre_proceso(ordenDao.getProceso(ordenTrabajo.getCodigo_formula()));
        listaMateriaPrima = ordenDao.getListaConsumoMateriales(ordenTrabajo.getCodigo_formula(), ordenTrabajo.getCantidad());
        materiaPrima = ordenDao.getArticulos();
        costoMateriaPrima();
        listaCentroTiempo = ordenDao.getListaCentro(ordenTrabajo.getCodigo_formula());
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
        ordenTrabajo.setTotalMOD(Precision.round(costosD, 2));
        ordenTrabajo.setTotalCIF(Precision.round(costosI, 2));
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
        } else if (!verificarMateriales()) {
            showWarn("No se puede inivciar la producción por falta de materiales en el inventario.");
        } else {
            try {
                if (ordenDao.registrarProduccion(ordenTrabajo) > 0) {
                    if (ordenDao.actualizarOrden(ordenTrabajo.getCodigo_registro()) > 0) {
                        if (ordenDao.verificaOrden(ordenTrabajo.getCodigo_orden())) {
                            if (ordenDao.actualizaEstado(ordenTrabajo.getCodigo_orden()) > 0) {
                                Double materiales = 0.0, indirectos = 0.0, directos = 0.0;
                                listaAdicionales = ordenDao.getArticuloAdicionales(ordenTrabajo.getCodigo_orden());
                                ordenAsiento = ordenDao.calculaProduccion(ordenTrabajo.getCodigo_orden());
//                                ordenAsiento = ordenDao.movimientosProduccion(ordenTrabajo.getCodigo_orden());
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
                                        ordenDao.extraccionMateriales(articulo.getId(), articulo.getCantidad());
                                    }
                                    listaMateriaPrima = new ArrayList<>();
                                }
                                for (ArticuloFormula adicionales : listaAdicionales) {
                                    listaMovimientos.add(new FormulaProduccion(adicionales.getDescripcion(), adicionales.getCantidad() * adicionales.getCosto(), adicionales.getIdSubcuenta()));
                                    materiales += adicionales.getCantidad() * adicionales.getCosto();
                                    ordenDao.extraccionMateriales(adicionales.getId(), adicionales.getCantidad());
                                }
                                ordenTrabajo.setCodigo_bodega(ordenDao.bodega(ordenTrabajo.getCodigo_orden()));
//                                ordenDao.ingresoMateriales(ordenTrabajo.getCodigo_producto(), ordenTrabajo.getCantidad());
                                ordenDao.entradaInventario("PR-" + ordenTrabajo.getCodigo_orden(), ordenTrabajo.getFecha_fin(), ordenTrabajo.getCodigo_bodega());
                                //productos ingreso
                                listaProducto = new ArrayList<>();
                                int identrada;
                                identrada = ordenDao.idEntrada("PR-" + ordenTrabajo.getCodigo_orden());
                                listaProducto = ordenDao.getInventario(ordenTrabajo.getCodigo_orden());
                                for (OrdenTrabajo entrada : listaProducto) {
                                    ordenDao.entradaInventarioMateriales(entrada.getCodigo_producto(), identrada, entrada.getCantidad(), entrada.getCostoUnitario());
                                }
                                if (ordenDao.insertAsiento(ordenAsiento, listaMovimientos, directos, indirectos, materiales) < 1) {
                                    ordenDao.cancelarOrdenProduccion(ordenTrabajo.getCodigo_orden());
                                    ordenDao.updateRegistro(ordenTrabajo.getCodigo_orden());
                                    ordenDao.updateOrden(ordenTrabajo.getCodigo_orden());
                                    showWarn("No se pudo generar la Orden de producción");
                                }

                            }
                        }
                    }
                    showInfo("Orden de producción registrada");
                    vaciar();
                } else {
                    showWarn("No se pudo generar la Orden de producción");
                }
            } catch (Exception e) {
                showWarn("No se pudo generar la Orden de producción" + e);
            }

        }
    }

    public void vaciar() {
        int orden = ordenTrabajo.getCodigo_orden();
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
        materiaPrimaCosto = 0;
        costosD = 0;
        costosI = 0;
        ordenTrabajo.setCodigo_orden(orden);
        if (ordenDao.verificaOrden(ordenTrabajo.getCodigo_orden())) {
            showInfo("Orden de producción Finaliza, regrese a las listas de ordenes de trabajo.");
            ordenDao = new OrdenProduccionDAO();
        } else {
            listaProducto = ordenDao.getListaProducto(ordenTrabajo.getCodigo_orden(), "P");
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
            materialesFormula.setUnidadMedida(materiales.getUnidadMedida());
            return materialesFormula;
        } else {
            FormulaMateriales buscar = new FormulaMateriales(materiales.getNombre(),
                    materiales.getDescripcionProducto(), ordenTrabajo.getCodigo_registro(),
                    materiales.getCodigo_producto(), materiales.getCosto());
            buscar.setUnidadMedida(materiales.getUnidadMedida());
            for (FormulaMateriales lista : listaMateriaPrimaAdicional) {
                if (lista.getCodigoProducto() == buscar.getCodigoProducto()) {
                    listaMateriaPrimaAdicional.remove(lista);
                }
            }
            return null;
        }

    }

    public void llenaMaterialiesConfirmados() {
        materiaPrima = ordenDao.getArticulos();
        for (FormulaMateriales materiales : listaMateriaPrimaAdicional) {
            if (materiales != null) {
                if (duplicidadDatos(materiales)) {
                    showWarn("El producto ya se encuentra agregado");
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

    public void llenaAdicionales() {
        float valor;
        if (!verificaCampos()) {
            for (int j = 0; j < detalleListaMateriaPrima.size(); j++) {
                for (int i = 0; i < listaMaterialesConfirmados.size(); i++) {

                    if (listaMaterialesConfirmados.get(i).getCodigoProducto() == detalleListaMateriaPrima.get(j).getId()) {
                        detalleListaMateriaPrima.get(j).setCantidad(listaMaterialesConfirmados.get(i).getCantidad() + detalleListaMateriaPrima.get(j).getCantidad());
                        detalleListaMateriaPrima.get(j).setTotal(detalleListaMateriaPrima.get(j).getCantidad() * detalleListaMateriaPrima.get(j).getCosto());
                        materiaPrimaCosto += listaMaterialesConfirmados.get(i).getCantidad() * detalleListaMateriaPrima.get(j).getCosto();
                        valor = ordenDao.verificaProducto(ordenTrabajo.getCodigo_registro(), listaMaterialesConfirmados.get(i).getCodigoProducto());
                        if (valor > 0) {
                            valor += listaMaterialesConfirmados.get(i).getCantidad();
                            ordenDao.actualizaAdicionales(valor, ordenTrabajo.getCodigo_registro(), listaMaterialesConfirmados.get(i).getCodigoProducto());
                        } else {
                            ordenDao.registrarDetalleProduccion(listaMaterialesConfirmados.get(i), ordenTrabajo.getCodigo_registro());
                        }
                        listaMaterialesConfirmados.remove(listaMaterialesConfirmados.get(i));
                    }
                }
            }
            ordenTrabajo.setTotalMateria(materiaPrimaCosto);
            ordenTrabajo.setCostoTotal(ordenTrabajo.getTotalMateria() + ordenTrabajo.getTotalMOD() + ordenTrabajo.getTotalCIF());
            ordenTrabajo.setCostoUnitario(ordenTrabajo.getCostoTotal() / ordenTrabajo.getCantidad());
            valor = 0;
            for (FormulaMateriales materiaAdicional : listaMaterialesConfirmados) {
                detalleListaMateriaPrima.add(new ArticuloFormula(materiaAdicional.getCodigoProducto(), materiaAdicional.getNombre(), materiaAdicional.getDescripcion(),
                        materiaAdicional.getPrecio(), materiaAdicional.getCantidad(), materiaAdicional.getCantidad() * materiaAdicional.getPrecio()));
                materiaPrimaCosto += materiaAdicional.getCantidad() * materiaAdicional.getPrecio();
                for (FormulaMateriales materiaA : listaMaterialesConfirmados) {
                    valor = ordenDao.verificaProducto(ordenTrabajo.getCodigo_registro(), materiaA.getCodigoProducto());
                    if (valor > 0) {
                        valor += materiaA.getCantidad();
                        ordenDao.actualizaAdicionales(valor, ordenTrabajo.getCodigo_registro(), materiaA.getCodigoProducto());
                    } else {
                        ordenDao.registrarDetalleProduccion(materiaA, ordenTrabajo.getCodigo_registro());
                    }
                }
            }
            ordenTrabajo.setTotalMateria(materiaPrimaCosto);
            ordenTrabajo.setCostoTotal(ordenTrabajo.getTotalMateria() + ordenTrabajo.getTotalMOD() + ordenTrabajo.getTotalCIF());
            ordenTrabajo.setCostoUnitario(ordenTrabajo.getCostoTotal() / ordenTrabajo.getCantidad());
            if (listaMaterialesConfirmados.size() > 0) {
                showInfo("Productos agregados con exito.");

            }
            listaMaterialesConfirmados = new ArrayList<>();
        } else {
            showWarn("Debe de ingresar valores en la materia prima que agrego.");
        }

    }

    public void cancelarOrden() throws IOException {
        ordenDao.cancelarOrden(ordenTrabajo.getCodigo_registro());
        externalContext.redirect("../produccion/listaOrdenProduccion.xhtml");
    }

    public boolean verificarMateriales() {
        boolean verifica = true;
        for (FormulaProduccion material : materiaPrima) {
            for (ArticuloFormula articulos : detalleListaMateriaPrima) {
                if (material.getCantidad() < articulos.getCantidad()) {
                    verifica = false;
                }
            }
        }
        return verifica;
    }

    public void asignaHoraInicio(CentroTrabajo centro) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        centro.setHoraInicio(dateFormat.format(date));
    }

    public void asignaHoraFin(CentroTrabajo centro) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        centro.setHoraFin(dateFormat.format(date));
        float costoMinutoDirecto = centro.getCostoDirecto() / 60;
        float costoMinutoIndirecto = centro.getCostoIndirecto() / 60;
        float tiempoMinuto = convertMinutos(centro.getTiempoMinutos());
        try {
            Date dataInicio = dateFormat.parse(centro.getHoraInicio());
            Date dataFin = dateFormat.parse(centro.getHoraFin());
            int diferencia = (int) ((dataFin.getTime() - dataInicio.getTime()) / 1000);
            int horas = 0;
            int minutos = 0;
            if (diferencia > 3600) {
                horas = (int) Math.floor(diferencia / 3600);
                diferencia = diferencia - (horas * 3600);
            }
            if (diferencia > 60) {
                minutos = (int) Math.floor(diferencia / 60);
                diferencia = diferencia - (minutos * 60);
            }
            centro.setTiempoMinutos(horas + ":" + minutos + ":" + diferencia);
            centro.setTotalDirecto(Precision.round(costoMinutoDirecto * tiempoMinuto, 2));
            centro.setTotalIndirecto(Precision.round(costoMinutoIndirecto * tiempoMinuto, 2));
        } catch (ParseException ex) {
            Logger.getLogger(ProduccionMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public float convertMinutos(String hora) {
        float minutos = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(hora);
        } catch (ParseException e) {
            System.out.println("" + e);
        }
        minutos += date.getSeconds() / 60;
        minutos += date.getHours() * 60;
        minutos += date.getMinutes();
        return minutos;
    }

}
