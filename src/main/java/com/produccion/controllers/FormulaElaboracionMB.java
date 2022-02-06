/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

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

    private List<FormulaProduccion> listaFormula;
    private List<ProcesoProduccion> listProceso;
    private List<SubProceso> listSubProceso;
    private List<FormulaMateriales> listaMateriales;
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
        formulaProduccion.setCodigo_formula(formulaProduccionDAO.IdFormula());
    }

    @PostConstruct
    public void init() {
        listaFormula = formulaProduccionDAO.getFormula();
        listProceso = procesoProduccionDAO.getProcesosProduccion();
        productoTerminado = formulaProduccionDAO.getArticulos();
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

    //metodos
    public void llenarListaSubproceso() {
        listSubProceso = formulaProduccionDAO.getSubProceso(formulaProduccion.getCodigo_proceso());
    }

    public void insertarDatos() {
        try {
            formulaProduccionDAO.insertarFormula(formulaProduccion);
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Agregado"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
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
        listaMateriales.add(llenarMateriales(producto));
    }

    public FormulaMateriales llenarMateriales(FormulaProduccion materiales) {
        materialesFormula = new FormulaMateriales(materiales.getNombre(),
                materiales.getDescripcionProducto(),formulaProduccion.getCodigo_formula(), materiales.getCodigo_producto(), materiales.getCosto());
        return materialesFormula;
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

    public void deleteFila(FormulaMateriales producto) {
        listaMateriales.remove(producto);
    }

}
