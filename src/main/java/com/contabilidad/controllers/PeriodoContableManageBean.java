/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contabilidad.controllers;

import com.contabilidad.dao.PeriodoContableDAO;
import com.contabilidad.dao.ejercicioContableDAO;
import com.contabilidad.models.Periodo;
import com.contabilidad.models.ejercicioContable;
import java.io.Serializable;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author User
 */
@ManagedBean(name = "periodoMB")
@SessionScoped
public class PeriodoContableManageBean implements Serializable {

    private SimpleDateFormat dateFormat;
    private Date fechaInicio;
    int idForant;
    private Date fechaFin;
    private String nombrePeriodo;
    private boolean estado;
    private List<ejercicioContable> ejercicioModel;
    private Periodo periodo;
    private String nombreEjecicio;
    List<SelectItem> EjerItem;
    private ejercicioContable selectEjercicioContableModel;
    private ejercicioContableDAO ejercicioDAO;
    private List<Periodo> listaPeriodo;
    private PeriodoContableDAO pediodoDAO;

    public String getNombreEjecicio() {
        return nombreEjecicio;
    }

    public void setNombreEjecicio(String nombreEjecicio) {
        this.nombreEjecicio = nombreEjecicio;
    }

    @PostConstruct
    public void init() {
        ejercicioContableDAO ejer = new ejercicioContableDAO();

        this.ejercicioModel = ejer.listarContable();
    }

    public PeriodoContableManageBean() {

        listaPeriodo = new ArrayList<>();
        pediodoDAO = new PeriodoContableDAO();
        ejercicioDAO = new ejercicioContableDAO();
        EjerItem = new ArrayList<>();

    }

    public void llenar() {
        List<ejercicioContable> auxi = ejercicioDAO.listarContable();
        for (int x = 0; x < auxi.size(); x++) {
            SelectItem ejercicio = new SelectItem(auxi.get(x).getNombrePeriodo());
            EjerItem.add(ejercicio);

        }

    }

    public int getIdForant() {
        return idForant;
    }

    public void setIdForant(int idForant) {
        this.idForant = idForant;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public List<Periodo> getListaPeriodo() {
        return listaPeriodo;
    }

    public void setListaPeriodo(List<Periodo> listaPeriodo) {
        this.listaPeriodo = listaPeriodo;
    }

    public void actualizaEjercicioContable() {
        ejercicioContableDAO ejercont = new ejercicioContableDAO();
        this.ejercicioModel = ejercont.listarContable();
        PrimeFaces.current().ajax().update(":form:tb_ejercicioContable");

    }

    public List<ejercicioContable> getEjercicioModel() {
        return ejercicioModel;
    }

    public void setEjercicioModel(List<ejercicioContable> ejercicioModel) {
        this.ejercicioModel = ejercicioModel;
    }

    public ejercicioContable getSelectEjercicioContableModel() {
        return selectEjercicioContableModel;
    }

    public void setSelectEjercicioContableModel(ejercicioContable selectEjercicioContableModel) {
        this.selectEjercicioContableModel = selectEjercicioContableModel;
    }

    public PeriodoContableDAO getPediodoDAO() {
        return pediodoDAO;
    }

    public void setPediodoDAO(PeriodoContableDAO pediodoDAO) {
        this.pediodoDAO = pediodoDAO;
    }

    public void onRowSelect(SelectEvent<ejercicioContable> event) {
        try {
            int idProveedor = event.getObject().getIdPeriodo();
            String nombreProveedor = event.getObject().getNombrePeriodo();
            periodo.setIdPeriodo(idProveedor);

            //  PrimeFaces.current().ajax().update(":formnuevoNodepreciable:panelNuevoNoDepreciable");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private List<SelectItem> selectedPeriodo;

    public List<SelectItem> getSelectedPeriodo() {
        return selectedPeriodo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setSelectedPeriodo(List<SelectItem> selectedPeriodo) {
        this.selectedPeriodo = selectedPeriodo;
    }

    public void addNewFila() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        List<Periodo> auxi = new ArrayList<>();
        for (int x = 0; x < listaPeriodo.size(); x++) {
            System.out.println(listaPeriodo.get(x).getNombrePeriodo());
        }
        Periodo per = new Periodo("Nombre", this.fechaFin, this.fechaFin, estado);
        llenar();
        listaPeriodo.add(per);
        nombrePeriodo = "";
        estado = false;
        for (int x = 0; x < listaPeriodo.size(); x++) {
            System.out.println(listaPeriodo.get(x).getNombrePeriodo() + listaPeriodo.get(x).getIdPeriodo());
        }

    }

    public void insertPeriodo() {
        pediodoDAO.insertarPeriodo(listaPeriodo);
    }

    public void deleteRow(Periodo p) {
        this.listaPeriodo.remove(p);
        PrimeFaces.current().ajax().update(":form2:dt-periodo");
    }

    public void reset() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cancelado"));
        PrimeFaces.current().resetInputs(":form2", ":form2:dt-periodo");
        removeScopedBean("periodoMB");
        listaPeriodo.clear();
    }

    public List<SelectItem> getEjerItem() {
        return EjerItem;
    }

    public void setEjerItem(List<SelectItem> EjerItem) {
        this.EjerItem = EjerItem;
    }

    public ejercicioContableDAO getEjercicioDAO() {
        return ejercicioDAO;
    }

    public void setEjercicioDAO(ejercicioContableDAO ejercicioDAO) {
        this.ejercicioDAO = ejercicioDAO;
    }

    public void onRowEdit(RowEditEvent<Periodo> event) {
        Periodo p = event.getObject();
        p.setNombrePeriodo(nombrePeriodo);
        p.setFechaInicio(fechaInicio);
        p.setFechaFin(fechaFin);
        p.setEstado(estado);
        FacesMessage msg = new FacesMessage(
                "Periodo Editado",
                String.valueOf(event.getObject().getNombrePeriodo()));
        FacesContext.getCurrentInstance().
                addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<Periodo> event) {
        FacesMessage msg = new FacesMessage(
                "Editar Cancelado", String.valueOf(event.getObject().getNombrePeriodo()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private void removeScopedBean(String periodoMB) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(periodoMB);

    }

}
