/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.FormulaMaterialesDAO;
import com.produccion.dao.FormulaProduccionDAO;
import com.produccion.dao.ProcesoProduccionDAO;
import com.produccion.models.FormulaMateriales;
import com.produccion.models.FormulaProduccion;
import com.produccion.models.ProcesoProduccion;
import com.produccion.models.SubProceso;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author HP
 */
@Named(value = "formulaMBeans")
@ViewScoped
public class FormulaElaboracionMB implements Serializable {

    private FormulaProduccion formulaProduccion;
    FormulaProduccionDAO formulaProduccionDAO;
    ProcesoProduccionDAO procesoProduccionDAO;
    private FormulaMateriales materialesFormula;
    private boolean verificar;//eliminar si no sale
    FormulaMaterialesDAO formulaMaterialesDAO;
    private String render;

    private List<FormulaProduccion> listaFormula;
    private List<ProcesoProduccion> listProceso;
    private List<SubProceso> listSubProceso;
    private List<FormulaMateriales> listaMateriales;
    private List<FormulaMateriales> listaMaterialesConfirmados;
    private List<FormulaProduccion> productoTerminado;

    public FormulaElaboracionMB() {
        formulaProduccion = new FormulaProduccion();
        formulaProduccionDAO = new FormulaProduccionDAO();
        procesoProduccionDAO = new ProcesoProduccionDAO();
        materialesFormula = new FormulaMateriales();
        listaFormula = new ArrayList<>();
        listProceso = new ArrayList<>();
        listSubProceso = new ArrayList<>();
        listaMateriales = new ArrayList<>();
        productoTerminado = new ArrayList<>();
        listaMaterialesConfirmados = new ArrayList<>();
        
        formulaMaterialesDAO = new FormulaMaterialesDAO();
    }

    @PostConstruct
    public void init() {
        listaFormula = formulaProduccionDAO.getFormula();
        listProceso = procesoProduccionDAO.getProcesosProduccion();
        productoTerminado = formulaProduccionDAO.getArticulos();
    }

    public String getRender() {
        return render;
    }

    public void setRender(String render) {
        this.render = render;
    }

    public FormulaProduccion getFormulaProduccion() {
        return formulaProduccion;
    }

    public void setFormulaProduccion(FormulaProduccion formulaProduccion) {
        this.formulaProduccion = formulaProduccion;
    }

    public List<ProcesoProduccion> getListProceso() {
        return listProceso;
    }

    public void setListProceso(List<ProcesoProduccion> listProceso) {
        this.listProceso = listProceso;
    }

    public List<SubProceso> getListSubProceso() {
        return listSubProceso;
    }

    public void setListSubProceso(List<SubProceso> listSubProceso) {
        this.listSubProceso = listSubProceso;
    }

    public FormulaMateriales getMaterialesFormula() {
        return materialesFormula;
    }

    public void setMaterialesFormula(FormulaMateriales materialesFormula) {
        this.materialesFormula = materialesFormula;
    }

    public List<FormulaProduccion> getListaFormula() {
        return listaFormula;
    }

    public void setListaFormula(List<FormulaProduccion> listaFormula) {
        this.listaFormula = listaFormula;
    }

    public List<FormulaMateriales> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<FormulaMateriales> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }

    public List<FormulaProduccion> getProductoTerminado() {
        return productoTerminado;
    }

    public void setProductoTerminado(List<FormulaProduccion> productoTerminado) {
        this.productoTerminado = productoTerminado;
    }

    //nuevo
    public boolean isVerificar() {
        return verificar;
    }

    public void setVerificar(boolean verificar) {
        this.verificar = verificar;
    }

    public List<FormulaMateriales> getListaMaterialesConfirmados() {
        return listaMaterialesConfirmados;
    }

    public void setListaMaterialesConfirmados(List<FormulaMateriales> listaMaterialesConfirmados) {
        this.listaMaterialesConfirmados = listaMaterialesConfirmados;
    }

    //metodos
    public void llenarListaSubproceso() {
        listSubProceso = formulaProduccionDAO.getSubProceso(formulaProduccion.getCodigo_proceso());
        costosProduccion();
    }

    public void insertarDatos() {
        try {
            if ("".equals(formulaProduccion.getCodigo_proceso())) {
                showWarn("Ingrese un Identificador");
            } else if ("".equals(formulaProduccion.getNombre_formula())) {
                showWarn("Ingrese un Nombre en la formula");
            } else if ("".equals(formulaProduccion.getDescripcion())) {
                showWarn("Ingrese una Descripción");
            } else if ("".equals(formulaProduccion.getRendimiento())) {
                showWarn("Ingrese el rendimiento");
            } else if ("".equals(formulaProduccion.getCodigo_producto())) {
                showWarn("Escoja un producto");
            } else if (formulaProduccion.getCodigo_proceso() == 0) {
                showWarn("Seleccione un proceso");
            } else if (!validaMateriales()) {
                showWarn("Ingrese valores Unidad de Medida, Cantidades y seleccione un Subproceso");
            } else {
                
                if (formulaProduccionDAO.insertarFormula(formulaProduccion) > 0) {
                    formulaProduccion.setCodigo_formula(formulaProduccionDAO.idFormula());
                    for (FormulaMateriales lista : listaMaterialesConfirmados) {
                        if (lista.getCantidad() != 0 && !"".equals(lista.getUnidadMedida())) {
                            lista.setCantidadUnidad(lista.getCantidad() / formulaProduccion.getRendimiento());
                            formulaProduccionDAO.InsertarMateriales(lista,formulaProduccion.getCodigo_formula());
                        } else {
                            showWarn("Ingrese valores Unidad de Medida y Cantidades");
                        }
                    }
                    showInfo("Formula Agregada");
                    limpiarFormulario();
                } else {
                    showError("Error al guardar");
                }

            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
    }

    public boolean validaMateriales() {
        boolean verifica = false;
        for (FormulaMateriales lista : listaMaterialesConfirmados) {
            if (lista.getCantidad() >= 0 && lista.getUnidadMedida() != null
                    && lista.getCodigoSuproceso() > 0 && listaMaterialesConfirmados != null) {
                verifica = true;
            } else {
                verifica = false;
                break;
            }

        }
        return verifica;
    }

    public void llenaArticulo(FormulaProduccion inventario) {
        formulaProduccion.setCodigo_producto(inventario.getCodigo_producto());
        formulaProduccion.setNombre_producto(inventario.getNombre());
        formulaProduccion.setTipo(inventario.getTipo());
        formulaProduccion.setCategoria(inventario.getCategoria());
    }

    public void addMateriales() {
        listaMateriales.add(materiaPrima());
        materialesFormula = new FormulaMateriales();
    }

    //borrar si no sale
    public void addMateriales2(FormulaProduccion producto) {
        FormulaMateriales verifica = llenarMateriales(producto);
        if (verifica != null) {
            if (producto.isVerifica()) {
                listaMateriales.add(verifica);
            }
        }
    }

    public FormulaMateriales llenarMateriales(FormulaProduccion materiales) {
        if (materiales.isVerifica()) {
            materialesFormula = new FormulaMateriales(materiales.getNombre(),
                    materiales.getDescripcionProducto(), formulaProduccion.getCodigo_formula(), materiales.getCodigo_producto(), materiales.getCosto());
            return materialesFormula;
        } else {
            FormulaMateriales buscar = new FormulaMateriales(materiales.getNombre(),
                    materiales.getDescripcionProducto(), formulaProduccion.getCodigo_formula(),
                    materiales.getCodigo_producto(), materiales.getCosto());
            for (FormulaMateriales lista : listaMateriales) {
                if (lista.getCodigoProducto() == buscar.getCodigoProducto()) {
                    listaMateriales.remove(lista);
                }

            }
            return null;
        }

    }

    public void llenaMaterialiesConfirmados() {
        for (FormulaMateriales materiales : listaMateriales) {
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

    //hasta aqui eliminar si no sale
    public FormulaMateriales materiaPrima() {
        for (FormulaProduccion productoE : productoTerminado) {
            if (materialesFormula.getCodigoProducto() == productoE.getCodigo_producto()) {
                materialesFormula.setCodigFormula(formulaProduccion.getCodigo_formula());
                materialesFormula.setPrecio(productoE.getCosto());
                materialesFormula.setNombre(productoE.getNombre());
                materialesFormula.setDescripcion(productoE.getDescripcion());
                break;
            }
        }
        return materialesFormula;
    }

    public void costosProduccion() {
        formulaProduccion.setCIF(formulaProduccionDAO.CIF(formulaProduccion.getCodigo_proceso(),
                formulaProduccion.getRendimiento()));
        formulaProduccion.setMOD(formulaProduccionDAO.MOD(formulaProduccion.getCodigo_proceso(),
                formulaProduccion.getRendimiento()));
        formulaProduccion.setTiempoFormula(formulaProduccionDAO.tiempoFormula(formulaProduccion.getCodigo_proceso(),
                formulaProduccion.getRendimiento()));
        
        formulaProduccion.setTiempoUnidad(formulaProduccion.getTiempoFormula()/formulaProduccion.getRendimiento());
        formulaProduccion.setMODUnidad(formulaProduccion.getMOD()/formulaProduccion.getTiempoFormula());
        formulaProduccion.setCIFUnidad(formulaProduccion.getCIF()/formulaProduccion.getTiempoFormula());
    }

    public void limpiarFormulario() {
        formulaProduccion = new FormulaProduccion();
        formulaProduccionDAO = new FormulaProduccionDAO();
        procesoProduccionDAO = new ProcesoProduccionDAO();
        materialesFormula = new FormulaMateriales();
        listaFormula = new ArrayList<>();
        listProceso = new ArrayList<>();
        listSubProceso = new ArrayList<>();
        listaMateriales = new ArrayList<>();
        productoTerminado = new ArrayList<>();
        listaMaterialesConfirmados = new ArrayList<>();
        formulaProduccion.setCodigo_formula(formulaProduccionDAO.IdFormula());
        formulaMaterialesDAO = new FormulaMaterialesDAO();
        listaFormula = formulaProduccionDAO.getFormula();
        listProceso = procesoProduccionDAO.getProcesosProduccion();
        productoTerminado = formulaProduccionDAO.getArticulos();
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

    public List<FormulaMateriales> listarMateriales() {
        return listaMateriales;
    }

    public void deleteFila(FormulaMateriales producto) {
        listaMaterialesConfirmados.remove(producto);
    }

}
