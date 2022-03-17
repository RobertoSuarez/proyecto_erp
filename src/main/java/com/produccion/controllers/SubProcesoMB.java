/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.produccion.dao.PersonalSubproceso;
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
    private List<PersonalSubproceso> listaPersonal;
    List<PersonalSubproceso> listaPersonalImplicado;

    List<dSubproceso> listaDsubprocesoDirecta;
    List<dSubproceso> listaDsubprocesoInirecta;

    float totalDirecto;
    float totalIndirecto;
    float totalxHoraIndirecto;
    float totalxHoraDirecto;

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
        listaPersonal = new ArrayList<>();
        listaPersonalImplicado = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listaProceso = subProcesoDAO.getProcesosProduccion();
        listaCostoDirecto = detalleSuprocesoDAO.getCostoDirecto();
        listaCostoIndirecto = detalleSuprocesoDAO.getCostoIndirecto();
        listaPersonal = detalleSuprocesoDAO.getListaEmpleado();
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

    public List<PersonalSubproceso> getListaPersonal() {
        return listaPersonal;
    }

    public void setListaPersonal(List<PersonalSubproceso> listaPersonal) {
        this.listaPersonal = listaPersonal;
    }

    public float getTotalxHoraIndirecto() {
        return totalxHoraIndirecto;
    }

    public void setTotalxHoraIndirecto(float totalxHoraIndirecto) {
        this.totalxHoraIndirecto = totalxHoraIndirecto;
    }

    public float getTotalxHoraDirecto() {
        return totalxHoraDirecto;
    }

    public void setTotalxHoraDirecto(float totalxHoraDirecto) {
        this.totalxHoraDirecto = totalxHoraDirecto;
    }

    public void insertarSubProceso() {

        if ("".equals(sproceso.getNombre())) {
            showWarn("Ingrese un Nombre al Subproceso.");
        } else if ("".equals(sproceso.getDescripcion())) {
            showWarn("La descripción no puede estar vacia.");
        } else if ("".equals(sproceso.getHora())) {
            showWarn("Debe ingresar las horas de trabajo.");
        } else if (!verificaListas(NuevolistaCostoDirecto)) {
            showWarn("Ingrese valor en los Costos Directos");
        } else if (!verificaListas(NuevolistaCostoIndirecto)) {
            showWarn("Ingrese valor en los Costos Indirectos");
        } else {
            if (subProcesoDAO.insertarSubproceso(sproceso,totalxHoraDirecto,totalxHoraIndirecto) > 0) {
                sproceso.setCodigo_subproceso(subProcesoDAO.idSubproceso());
//                subProcesoDAO.insertardSubproceso(sproceso);
                llenarDetalleDirecto();
                float minutos = convertMinutos(sproceso.getHora());
                for (dSubproceso subproceso : listaDsubprocesoDirecta) {
                    //insertamos costo directo
                    subproceso.setHora_costo(minutos /1);
                    subproceso.setModunitario(subproceso.getCosto_mano_obra() /1);
                    subProcesoDAO.insertarDetalleSubproceso(subproceso);
                }
                llenarDetalleInirecto();
                for (dSubproceso subproceso : listaDsubprocesoInirecta) {
                    //insertamos costo indirecto
                    subproceso.setHora_costo(minutos /1);
                    subproceso.setCifunitario(subproceso.getCosto_indirecto() /1);
                    subProcesoDAO.insertarDetalleSubproceso(subproceso);
                }
                subProcesoDAO.updateSubProceso(sproceso.getCodigo_subproceso(),minutos,60);
//                subProcesoDAO.actualizaProceso(sproceso);
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
        listaPersonal = new ArrayList<>();
        listaPersonalImplicado = new ArrayList<>();

        listaProceso = subProcesoDAO.getProcesosProduccion();
        listaCostoDirecto = detalleSuprocesoDAO.getCostoDirecto();
        listaCostoIndirecto = detalleSuprocesoDAO.getCostoIndirecto();
        listaPersonal = detalleSuprocesoDAO.getListaEmpleado();

        totalDirecto = 0;
        totalIndirecto = 0;
        totalxHoraIndirecto = 0;
        totalxHoraDirecto = 0;
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
        float minutos;
        if (!"".equals(sproceso.getHora())) {
            minutos = convertMinutos(sproceso.getHora());
        } else {
            minutos = 0;
            showWarn("Ingrese las Horas de trabajo.");
        }
        float horasTrabajo = 8;
        float diasTrabajo = 25;
        float totalMinutosTrabajo = 12000;
        float cotoMiniuto;
        Costo costoDirecto = costoD();
        if (!verificaCostoD(costoDirecto)) {
            if ("Sueldos y Salarios directos".equals(costoDirecto.getNombre_subcuenta())) {
                cotoMiniuto = valorManoObra() / totalMinutosTrabajo;
                costoDirecto.setCosto(minutos * cotoMiniuto);
                NuevolistaCostoDirecto.add(costoDirecto);
                sumarDirectos();
                costo = new Costo();
            } else {
                NuevolistaCostoDirecto.add(costoDirecto);
                sumarDirectos();
                costo = new Costo();
            }

        } else {
            showWarn("Ya ha agregado este Costo Directo");
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

    public float valorManoObra() {
        float valor = 0;
        for (PersonalSubproceso personaActivo : listaPersonalImplicado) {
            valor += personaActivo.getSueldo();
        }
        return valor;
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
        float totalI = 0, totalHora = convertMinutos(sproceso.getHora()), costoxminuto;
        for (Costo indirecto : NuevolistaCostoIndirecto) {
            totalI += indirecto.getCosto();
        }
        costoxminuto = totalI / 60;
        totalxHoraIndirecto = costoxminuto * 60;
        totalIndirecto = costoxminuto * totalHora;
    }

    public void sumarDirectos() {
        float tD = 0, totalHora = convertMinutos(sproceso.getHora()), costoxminuto;
        for (Costo directo : NuevolistaCostoDirecto) {
            tD += directo.getCosto();
        }
        costoxminuto = tD / 60;
        totalxHoraDirecto = costoxminuto * 60;
        totalDirecto = costoxminuto * totalHora;
    }

    public void deleteFila(Costo directo) {
        NuevolistaCostoDirecto.remove(directo);
        sumarDirectos();
    }

    public void deleteFilaIndirecto(Costo directo) {
        NuevolistaCostoIndirecto.remove(directo);
        sumarIndirectos();
    }

    public void llenaPersonal(PersonalSubproceso personal) {
        if (!llenarTrabajador(personal)) {
            if (!"".equals(sproceso.getHora())) {
                listaPersonalImplicado.add(personal);
                listaPersonal.remove(personal);
                showInfo("Se realizo la asignación");
            } else {
                showWarn("Ingrese las horas de trabajo");
            }
        } else {
            showWarn("Ya se encuentra asignado al subproceso");
        }
    }

    public boolean llenarTrabajador(PersonalSubproceso personal) {
        boolean verifica = false;

        for (PersonalSubproceso personaActivo : listaPersonalImplicado) {
            if (personaActivo.getIdEmpleado() == personal.getIdEmpleado()) {
                verifica = true;
                break;
            }
        }
        return verifica;
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
