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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    }

    @PostConstruct
    public void init() {
        listaProceso = subProcesoDAO.getProcesosProduccion();
        listaCostoDirecto = detalleSuprocesoDAO.getCostoDirecto();
        listaCostoIndirecto = detalleSuprocesoDAO.getCostoIndirecto();

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

        if ("".equals(sproceso.getNombre())) {
            showWarn("Ingrese un Nombre al Subproceso.");
        } else if ("".equals(sproceso.getDescripcion())) {
            showWarn("La descripción no puede estar vacia.");
        } else if (sproceso.getRendimiento() <= 0) {
            showWarn("El rendimiento no pude ser cero.");
        } else if ("".equals(sproceso.getHora())) {
            showWarn("Debe ingresar las horas de trabajo.");
        } else if (sproceso.getId_codigo_proceso() == 0) {
            showWarn("Debe escojer un proceso.");
        } else if (!verificaListas(NuevolistaCostoDirecto)) {
            showWarn("Ingrese valor en los Costos Directos");
        } else if (!verificaListas(NuevolistaCostoIndirecto)) {
            showWarn("Ingrese valor en los Costos Indirectos");
        } else {
            if (subProcesoDAO.insertarSubproceso(sproceso) > 0) {
                sproceso.setCodigo_subproceso(subProcesoDAO.idSubproceso());
                subProcesoDAO.insertardSubproceso(sproceso);
                llenarDetalleDirecto();
                float minutos = 0;
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(sproceso.getHora());
                } catch (ParseException e) {
                }
                minutos += date.getSeconds() / 60;
                minutos += date.getHours() * 60;
                minutos += date.getMinutes();
                for (dSubproceso subproceso : listaDsubprocesoDirecta) {
                    //insertamos costo directo
                    subproceso.setHora_costo(minutos/sproceso.getRendimiento());
                    subproceso.setModunitario(subproceso.getCosto_mano_obra() / sproceso.getRendimiento());
                    subProcesoDAO.insertarDetalleSubproceso(subproceso);
                }
                llenarDetalleInirecto();
                for (dSubproceso subproceso : listaDsubprocesoInirecta) {
                    //insertamos costo indirecto
                    subproceso.setHora_costo(minutos/sproceso.getRendimiento());
                    subproceso.setCifunitario(subproceso.getCosto_indirecto() / sproceso.getRendimiento());
                    subProcesoDAO.insertarDetalleSubproceso(subproceso);
                }
                subProcesoDAO.actualizaProceso(sproceso);
                showInfo("Subproceso registrado con exito");
                limpiarCampos();
            }
        }
    }

    public boolean verificaListas(List<Costo> listaDsubproceso) {
        boolean verifica = false;
        for (Costo lista : listaDsubproceso) {
            if (lista.getCosto() > 0) {
                verifica = true;
            } else {
                verifica = false;
                break;
            }
        }
        return verifica;
    }

    public void limpiarCampos() {
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

        listaProceso = subProcesoDAO.getProcesosProduccion();
        listaCostoDirecto = detalleSuprocesoDAO.getCostoDirecto();
        listaCostoIndirecto = detalleSuprocesoDAO.getCostoIndirecto();

        totalDirecto = 0;
        totalIndirecto = 0;
    }

    public void llenarDetalleDirecto() {
        for (Costo directo : NuevolistaCostoDirecto) {
            subDproceso.setCodigo_subproceso(sproceso.getCodigo_subproceso());
            subDproceso.setCodigo_costos(directo.getCodigo_subcuenta());
            subDproceso.setCosto_mano_obra(directo.getCosto());
            listaDsubprocesoDirecta.add(subDproceso);
            subDproceso = new dSubproceso();
        }
    }

    public void llenarDetalleInirecto() {
        for (Costo indirecto : NuevolistaCostoIndirecto) {
            subDproceso.setCodigo_subproceso(sproceso.getCodigo_subproceso());
            subDproceso.setCodigo_costos(indirecto.getCodigo_subcuenta());
            subDproceso.setCosto_indirecto(indirecto.getCosto());
            listaDsubprocesoInirecta.add(subDproceso);
            subDproceso = new dSubproceso();
        }
    }

    public void addDirecto() {
        Costo costoDirecto = costoD();
        if (!verificaCostoD(costoDirecto)) {
            NuevolistaCostoDirecto.add(costoDirecto);
            costo = new Costo();
        } else {
            showWarn("Ya ha agregado este Costo Directo");
        }
    }

    public void addIndirecto() {

        Costo costoIndirecto = costoI();
        if (!verificaCostoI(costoIndirecto)) {
            NuevolistaCostoIndirecto.add(costoIndirecto);
            costo = new Costo();
        } else {
            showWarn("Ya ha agregado este Costo Indirecto");
        }

    }

    public Costo costoD() {
        for (Costo costoDirecto : listaCostoDirecto) {
            if (costo.getCodigo_subcuenta() == costoDirecto.getCodigo_subcuenta()) {

                costo.setNombre_subcuenta(costoDirecto.getNombre_subcuenta());
                costo.setDescripcion_subgrupo(costoDirecto.getDescripcion_subgrupo());
                costo.setCosto(costoDirecto.getCosto());

            }
        }
        return costo;
    }

    public boolean verificaCostoD(Costo costoDirecto) {
        boolean verifica = false;
        for (Costo directo : NuevolistaCostoDirecto) {
            if (costoDirecto.getCodigo_subcuenta() == directo.getCodigo_subcuenta()) {
                verifica = true;
                break;
            }
        }
        return verifica;
    }

    public boolean verificaCostoI(Costo costoIndirecto) {
        boolean verifica = false;
        for (Costo indirecto : NuevolistaCostoIndirecto) {
            if (costoIndirecto.getCodigo_subcuenta() == indirecto.getCodigo_subcuenta()) {
                verifica = true;
                break;
            }
        }
        return verifica;
    }

    public Costo costoI() {
        for (Costo costoIndirecto : listaCostoIndirecto) {
            if (costo.getCodigo_subcuenta() == costoIndirecto.getCodigo_subcuenta()) {
                costo.setNombre_subcuenta(costoIndirecto.getNombre_subcuenta());
                costo.setDescripcion_subgrupo(costoIndirecto.getDescripcion_subgrupo());
                costo.setCosto(costoIndirecto.getCosto());
            }
        }
        return costo;
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
