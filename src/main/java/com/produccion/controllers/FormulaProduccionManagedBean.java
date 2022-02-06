/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.inventario.models.ArticulosInventario;
import com.produccion.dao.FormulaProduccionDAO;
import com.produccion.dao.ProcesoProduccionDAO;
import com.produccion.dao.SubProcesoDAO;
import com.produccion.models.ArticuloFormula;
import com.produccion.models.FormulaMateriales;
import com.produccion.models.FormulaProduccion;
import com.produccion.models.ProcesoProduccion;
import com.produccion.models.SubProceso;
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
 * @author Jimmy
 */
/**
 * Solo a los controladores se les pone implements serializable
 */
@ManagedBean(name = "formulaMB")

@ViewScoped

public class FormulaProduccionManagedBean implements Serializable {

    private FormulaProduccion formulaProduccion= new FormulaProduccion();
    private FormulaProduccionDAO formulaProduccionDAO;
    private List<FormulaProduccion> listaFormula;
    private List<FormulaProduccion>articuloMateriaP;
    ArticuloFormula articuloMateriaPrima;
    ArticulosInventario articulo;
    private ProcesoProduccion procesoProduccion;
    ProcesoProduccionDAO procesoProduccionDao;
    List<ProcesoProduccion> listProceso = new ArrayList<>();
    private SubProceso subProceso;
    SubProcesoDAO subProcesoDAO;
    List<SubProceso> listSubProceso = new ArrayList<>();
    List<SubProceso> listTempSubPro = new ArrayList<>();
    List<FormulaMateriales> listaMateriales;
    FormulaMateriales materialesProduccion;
    private boolean verificar;

    public FormulaProduccionManagedBean() {
       
        formulaProduccionDAO = new FormulaProduccionDAO();
        procesoProduccionDao = new ProcesoProduccionDAO();
        procesoProduccion = new ProcesoProduccion();
        subProceso = new SubProceso();
        articulo= new ArticulosInventario();
        subProcesoDAO = new SubProcesoDAO();
        listaFormula = new ArrayList<>();
        articuloMateriaP= new ArrayList<>();
        listaMateriales= new ArrayList<>();
        formulaProduccion.setCodigo_formula(formulaProduccionDAO.IdFormula());
        materialesProduccion=new FormulaMateriales();
    }

    @PostConstruct
    public void init() {
        listaFormula = formulaProduccionDAO.getFormula();
        listProceso = procesoProduccionDao.getProcesosProduccion();
        articuloMateriaP=formulaProduccionDAO.getArticulos();
    }

    public List<FormulaMateriales> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<FormulaMateriales> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }

    public boolean isVerificar() {
        return verificar;
    }

    public void setVerificar(boolean verificar) {
        this.verificar = verificar;
    }
    
    

    
    public void closeDialogModal() {
        PrimeFaces.current().executeScript("PF('crearFormulaDialog').hide()");
    }

    public void insertar() {
        System.err.println("Entro ......");
        try {
            
                formulaProduccionDAO.insertarFormula(formulaProduccion);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Agregado"));
            

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
        closeDialogModal();
        PrimeFaces.current().ajax().update("form-princFormula:dtformulaPrin");
    }

    public void editar() {
        System.err.println("Entro en editar......");
        try {
            if ("".equals(formulaProduccion.getCodigo_proceso())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else if ("".equals(formulaProduccion.getNombre_formula())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(formulaProduccion.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else if ("".equals(formulaProduccion.getRendimiento())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else if ("".equals(formulaProduccion.getEstado())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else if ("".equals(formulaProduccion.getCodigo_producto())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                this.formulaProduccionDAO.update(formulaProduccion);
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Proceso Agregado"));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
        PrimeFaces.current().executeScript("PF('crearFormulaDialg').hide()");
        PrimeFaces.current().ajax().update("form:dtFormulaPrin", "form:growl");
    }

    public void eliminarFormula() {
        try {
            this.formulaProduccionDAO.eliminarF(formulaProduccion, formulaProduccion.getNombre_formula());
            listaFormula = formulaProduccionDAO.getFormula();
            PrimeFaces.current().executeScript("PF('eliminarFormulaDialog').hide()");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Formula Eliminada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error al Eliminar la Formula"));

        }

    }
    public void subProcesoLista(){
        listSubProceso=formulaProduccionDAO.getSubProceso(formulaProduccion.getCodigo_proceso());
    }

    public SubProceso getSubProceso() {
        return subProceso;
    }

    public List<FormulaProduccion> getArticuloMateriaP() {
        return articuloMateriaP;
    }

    public void setArticuloMateriaP(List<FormulaProduccion> articuloMateriaP) {
        this.articuloMateriaP = articuloMateriaP;
    }

    public ArticuloFormula getArticuloMateriaPrima() {
        return articuloMateriaPrima;
    }

    public void setArticuloMateriaPrima(ArticuloFormula articuloMateriaPrima) {
        this.articuloMateriaPrima = articuloMateriaPrima;
    }
    
    

    public void setSubProceso(SubProceso subProceso) {
        this.subProceso = subProceso;
    }

    public FormulaProduccion getFormulaProduccion() {
        return formulaProduccion;
    }

    public void setFormulaProduccion(FormulaProduccion formulaProduccion) {
        this.formulaProduccion = formulaProduccion;
    }

    public FormulaProduccionDAO getFormulaProduccionDAO() {
        return formulaProduccionDAO;
    }

    public void setFormulaProduccionDAO(FormulaProduccionDAO formulaProduccionDAO) {
        this.formulaProduccionDAO = formulaProduccionDAO;
    }

    public List<FormulaProduccion> getListaFormula() {
        return listaFormula;
    }

    public void setListaFormula(List<FormulaProduccion> listaFormula) {
        this.listaFormula = listaFormula;
    }

    public List<ProcesoProduccion> getListProceso() {
        return listProceso;
    }

    public void setListProceso(List<ProcesoProduccion> listProceso) {
        this.listProceso = listProceso;
    }

    public ProcesoProduccion getProcesoProduccion() {
        return procesoProduccion;
    }

    public void setProcesoProduccion(ProcesoProduccion procesoProduccion) {
        this.procesoProduccion = procesoProduccion;
    }

    public List<SubProceso> getListSubProceso() {
        return listSubProceso;
    }

    public void setListSubProceso(List<SubProceso> listSubProceso) {
        this.listSubProceso = listSubProceso;
    }

    public List<SubProceso> getListTempSubPro() {
        return listTempSubPro;
    }

    public void setListTempSubPro(List<SubProceso> listTempSubPro) {
        this.listTempSubPro = listTempSubPro;
    }

    public ArticulosInventario getArticulo() {
        return articulo;
    }
    
    public void aggMateriales(ArticulosInventario materiales){
        if(verificar){
            listaMateriales.add(llenarMateriales(materiales));
            System.out.println("Hola");
            
        }
        
//        NuevolistaCostoIndirecto.add(costoI());
//        costo = new Costo();
    }
    public FormulaMateriales llenarMateriales(ArticulosInventario materiales){
        materialesProduccion= new FormulaMateriales();
        materialesProduccion=new FormulaMateriales(formulaProduccion.getCodigo_formula(),materiales.getCod());
        return materialesProduccion;
    }

    public void setArticulo(ArticulosInventario articulo) {
        this.articulo = articulo;
    }
    public void llenaArticulo(FormulaProduccion inventario){
        formulaProduccion.setCodigo_producto(inventario.getCodigo_producto());
        formulaProduccion.setNombre_producto(inventario.getNombre());
        formulaProduccion.setTipo(inventario.getTipo());
        formulaProduccion.setCategoria(inventario.getCategoria());
    }

}
