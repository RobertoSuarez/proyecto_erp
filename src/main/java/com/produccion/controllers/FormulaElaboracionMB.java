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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.math3.util.Precision;
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
    SubProceso centro;

    private List<FormulaProduccion> listaFormula;
    private List<ProcesoProduccion> listProceso;
    private List<SubProceso> listSubProceso;
    private List<SubProceso> listSubProcesoAdicional;
    private List<FormulaMateriales> listaMateriales;
    private List<FormulaMateriales> listaMaterialesConfirmados;
    private List<FormulaProduccion> productoTerminado;
    private List<FormulaProduccion> productoSM;
    private List<FormulaProduccion> listaEditar;

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
        productoSM = new ArrayList<>();
        listaEditar = new ArrayList<>();
        listSubProcesoAdicional = new ArrayList<>();
        formulaMaterialesDAO = new FormulaMaterialesDAO();
        centro = new SubProceso();
    }

    @PostConstruct
    public void init() {
        listaFormula = formulaProduccionDAO.getFormula();
//        listProceso = procesoProduccionDAO.getProcesosProduccion();
        productoTerminado = formulaProduccionDAO.getArticulosT("Materia Prima");
        productoSM = formulaProduccionDAO.getArticulosT("Producto Terminado");
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

    public List<FormulaProduccion> getProductoSM() {
        return productoSM;
    }

    public void setProductoSM(List<FormulaProduccion> productoSM) {
        this.productoSM = productoSM;
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

    public List<FormulaProduccion> getListaEditar() {
        return listaEditar;
    }

    public void setListaEditar(List<FormulaProduccion> listaEditar) {
        this.listaEditar = listaEditar;
    }

    public List<SubProceso> getListSubProcesoAdicional() {
        return listSubProcesoAdicional;
    }

    public void setListSubProcesoAdicional(List<SubProceso> listSubProcesoAdicional) {
        this.listSubProcesoAdicional = listSubProcesoAdicional;
    }

    //metodos
    public void llenarListaSubproceso() {
        listSubProceso = formulaProduccionDAO.getListaSubProceso(formulaProduccion.getCodigo_proceso());
        listSubProcesoAdicional = formulaProduccionDAO.getListaSubProcesoAdicional();
        //formulaProduccion.setRendimiento(formulaProduccionDAO.rendimiento(formulaProduccion.getCodigo_proceso()));
        //costosProduccion();
    }

    public void insertarDatos() {
        try {
            if ("".equals(formulaProduccion.getCodigo_proceso())) {
                showWarn("Ingrese un Identificador");
            } else if ("".equals(formulaProduccion.getNombre_formula())) {
                showWarn("Ingrese un Nombre en la formula");
            } else if ("".equals(formulaProduccion.getDescripcion())) {
                showWarn("Ingrese una Descripción");
            } else if ("".equals(formulaProduccion.getRendimiento()) && formulaProduccion.getRendimiento() > 0) {
                showWarn("Ingrese el rendimiento");
            } else if ("".equals(formulaProduccion.getCodigo_producto())) {
                showWarn("Escoja un producto");
            } else if (listaMaterialesConfirmados.size() < 1) {
                showWarn("Escoja los materiales para la formulación");
            } else if (listSubProceso.size() < 1) {
                showWarn("Escoja una ruta de producción");
            } else if (!verificaCantidad()) {
                showWarn("Ingrese Cantidades de los productos que requiere para la formulación.");
            } else if (!verificaHora()) {
                showWarn("Ingrese Las horas en los centros de trabajo.");
            } else {

                if (formulaProduccionDAO.insertarFormula(formulaProduccion) > 0) {
                    formulaProduccion.setCodigo_formula(formulaProduccionDAO.idFormula());
                    for (FormulaMateriales lista : listaMaterialesConfirmados) {
                        lista.setCantidadUnidad(lista.getCantidad() / formulaProduccion.getRendimiento());
                        formulaProduccionDAO.InsertarMateriales(lista, formulaProduccion.getCodigo_formula());
                    }
                    for (SubProceso lista : listSubProceso) {
                        formulaProduccionDAO.InsertarDetalleFormula(lista, formulaProduccion.getCodigo_formula());
                    }
                    showInfo("Formula Registrada");
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

    public boolean verificaCantidad() {
        boolean verifica = true;
        for (FormulaMateriales lista : listaMaterialesConfirmados) {
            if (lista.getCantidad() < 1 || lista.getCodigoSuproceso() < 1) {
                verifica = false;
                break;
            }
        }
        return verifica;
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

    public void recalculaRendimiento() {
        if (formulaProduccion.getCodigo_proceso() != 0) {
            for (SubProceso lista : listSubProceso) {
                lista.setPieza_minuto(convertMinutos(lista.getHora()) / formulaProduccion.getRendimiento());
            }
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
            materialesFormula.setUnidadMedida(materiales.getUnidadMedida());
            return materialesFormula;
        } else {
            FormulaMateriales buscar = new FormulaMateriales(materiales.getNombre(),
                    materiales.getDescripcionProducto(), formulaProduccion.getCodigo_formula(),
                    materiales.getCodigo_producto(), materiales.getCosto());
            buscar.setUnidadMedida(materiales.getUnidadMedida());
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
                materialesFormula.setUnidadMedida(productoE.getUnidadMedida());
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

        formulaProduccion.setTiempoUnidad(formulaProduccion.getTiempoFormula() / formulaProduccion.getRendimiento());
        formulaProduccion.setMODUnidad(formulaProduccion.getMOD() / formulaProduccion.getTiempoFormula());
        formulaProduccion.setCIFUnidad(formulaProduccion.getCIF() / formulaProduccion.getTiempoFormula());
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
        productoSM = new ArrayList<>();
        listaMaterialesConfirmados = new ArrayList<>();
        formulaProduccion.setCodigo_formula(formulaProduccionDAO.IdFormula());
        formulaMaterialesDAO = new FormulaMaterialesDAO();
        listaFormula = formulaProduccionDAO.getFormula();
        listProceso = procesoProduccionDAO.getProcesosProduccion();
        productoTerminado = formulaProduccionDAO.getArticulosT("Materia Prima");
        productoSM = formulaProduccionDAO.getArticulosT("Producto Terminado");
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

    public SubProceso getCentro() {
        return centro;
    }

    public void setCentro(SubProceso centro) {
        this.centro = centro;
    }

    //Todo lo nuevo de editar
    public void editaFormula(FormulaProduccion formula) {
        listaEditar = formulaProduccionDAO.traemeFormula(formula.getCodigo_formula());
        for (FormulaProduccion lista : listaEditar) {
            formulaProduccion.setCodigo_formula(lista.getCodigo_formula());
            formulaProduccion.setNombre_formula(lista.getNombre_formula());
            formulaProduccion.setNombre_producto(lista.getNombre_producto());
            formulaProduccion.setDescripcion(lista.getDescripcion());
            formulaProduccion.setRendimiento(lista.getRendimiento());
            formulaProduccion.setNombre(lista.getNombre());
            formulaProduccion.setCategoria(lista.getCategoria());
            formulaProduccion.setTipo(lista.getTipo());
        }
        listaEditar = formulaProduccionDAO.listaMateriales(formula.getCodigo_formula());
    }

    public void editarFormula() {
        try {
            if ("".equals(formulaProduccion.getNombre_formula())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Nombre"));
            } else if ("".equals(formulaProduccion.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else if ("".equals(formulaProduccion.getRendimiento())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese una Descripción"));
            } else {
                formulaProduccionDAO.update(formulaProduccion);
                for (FormulaProduccion listEdit : listaEditar) {
                    if (listEdit.getCantidad() > 0) {
                        formulaProduccionDAO.actualizarMateriales(formulaProduccion.getCodigo_formula(), listEdit.getCodigo_articulo(), listEdit.getCantidad());
                    } else {
                        showWarn("Ingese una cantidad mayor a cero.");
                    }
                }
                showInfo("Formula Modificada");
                listaFormula = formulaProduccionDAO.getFormula();
                listaEditar = new ArrayList<>();
            }
        } catch (SQLException e) {
            showWarn("Error al guardar");
        }
    }

    public void deleteEdit(FormulaProduccion producto) {
        listaEditar.remove(producto);
    }

    public void calculoValorHora(SubProceso subproceso) {
        if (formulaProduccion.getRendimiento() == 0) {
            showWarn("Ingrese un rendimiento");
        } else {
            float costoMinutoDirecto = subproceso.getMinuto_directo() / 60;
            float costoMinutoIndirecto = subproceso.getMinuto_intirecto() / 60;
            float tiempoMinuto = convertMinutos(subproceso.getHora());
            subproceso.setImporte_directo(Precision.round(costoMinutoDirecto * tiempoMinuto, 2));
            subproceso.setImporte_indirecto(Precision.round(costoMinutoIndirecto * tiempoMinuto, 2));
            subproceso.setPieza_minuto(convertMinutos(subproceso.getHora()) / formulaProduccion.getRendimiento());
            subproceso.setCosto_minuto_directo(subproceso.getImporte_directo() / convertMinutos(subproceso.getHora()));
            subproceso.setCosto_minuto_indirecto(subproceso.getImporte_indirecto() / convertMinutos(subproceso.getHora()));
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

    public void deleteFila(SubProceso centro) {
        listSubProceso.remove(centro);
    }

    public void costoCalculo() {
        if (formulaProduccion.getRendimiento() == 0) {
            showWarn("Ingrese un rendimiento");
        } else {
            if (verificaHora()) {
                float directo = 0, indirecto = 0;
                for (SubProceso lista : listSubProceso) {
                    directo += lista.getImporte_directo();
                    indirecto += lista.getImporte_indirecto();
                }
                formulaProduccion.setMOD(directo);
                formulaProduccion.setCIF(indirecto);
            } else {
                showWarn("Ingrese los tiempos de trabajo por cada centro.");
            }
        }

    }

    public boolean verificaHora() {
        boolean verifica = true;
        for (SubProceso lista : listSubProceso) {
            if (lista.getHora() == null) {
                verifica = false;
            }
        }
        return verifica;
    }

    public void addCentro() {

        SubProceso centroTrabajo = centroT();
        if (!verificaCentro(centroTrabajo)) {
            listSubProceso.add(centroTrabajo);
            centro = new SubProceso();
        } else {
            showWarn("Ya ha agregado este Costo Indirecto");
        }

    }

    public SubProceso centroT() {
        for (SubProceso subproceso : listSubProcesoAdicional) {
            if (subproceso.getCodigo_subproceso() == centro.getCodigo_subproceso()) {
                centro.setNombre(subproceso.getNombre());
                centro.setMinuto_directo(subproceso.getMinuto_directo());
                centro.setMinuto_intirecto(subproceso.getMinuto_intirecto());
            }
        }
        return centro;
    }

    public boolean verificaCentro(SubProceso costoIndirecto) {
        boolean verifica = false;
        for (SubProceso centroT : listSubProceso) {
            if (costoIndirecto.getCodigo_subproceso() == centroT.getCodigo_subproceso()) {
                verifica = true;
                break;
            }
        }
        return verifica;
    }

}
