/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contabilidad.controllers;

import com.contabilidad.dao.ejercicioContableDAO;
import com.contabilidad.models.ejercicioContable;
import static com.primefaces.Messages.addMessage;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author User
 */
@Named
@ViewScoped
public class ejercicioContableMB implements Serializable {

    private ejercicioContableDAO ejercicioContableDAO;
    private ejercicioContable ejercicioContableModel;
    private List<ejercicioContable> ejercicioContable;
    private List<ejercicioContable> ejercicioBusqueda;
    private SimpleDateFormat dateFormat;
    private Date fechaInicio;
    private Date fechaFin;

    public ejercicioContableMB() {
        ejercicioContable = new ArrayList<>();
        ejercicioBusqueda = new ArrayList<>();
        ejercicioContableDAO = new ejercicioContableDAO();
        ejercicioContableModel = new ejercicioContable();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        ejercicioContable = ejercicioContableDAO.listarEjercicioContable();
    }

    public void abrirNuevo() {
        ejercicioContableModel = new ejercicioContable();
        ejercicioContableDAO = new ejercicioContableDAO();
        ejercicioContableModel = new ejercicioContable();
    }

    public void insertarEjercicioContable() {

        ejercicioContableDAO.insertarEjercicioContable(ejercicioContableModel);
        showInfo("Registro Exitoso");
        PrimeFaces.current().ajax().update(":form:dt-periodos-fiscales");

    }

    public ejercicioContableDAO getEjercicioContableDAO() {
        return ejercicioContableDAO;
    }

    public void setEjercicioContableDAO(ejercicioContableDAO ejercicioContableDAO) {
        this.ejercicioContableDAO = ejercicioContableDAO;
    }

    public void cargarEditar(ejercicioContable ejerContable) {
        this.ejercicioContableModel.setNombrePeriodo(ejerContable.getNombrePeriodo());
    }

    public ejercicioContable getEjercicioContableModel() {
        return ejercicioContableModel;
    }

    public String FormatoFecha(Date fecha) {
        return new SimpleDateFormat("dd-MM-yyyy").format(fecha);
    }

    public void setEjercicioContableModel(ejercicioContable ejercicioContableModel) {
        this.ejercicioContableModel = ejercicioContableModel;
    }

    public List<ejercicioContable> getEjercicioContable() {
        ejercicioContable = ejercicioContableDAO.listarEjercicioContable();
        return ejercicioContable;
    }

    public void setEjercicioContable(List<ejercicioContable> ejercicioContable) {
        this.ejercicioContable = ejercicioContable;
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

    public void showInfo(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, "Exito", message);
    }

    public void showWarn(String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", message);
    }

    public List<ejercicioContable> getEjercicioBusqueda() {
        ejercicioBusqueda = ejercicioContableDAO.listarContable();
        return ejercicioBusqueda;
    }

    public void setEjercicioBusqueda(List<ejercicioContable> ejercicioBusqueda) {
        this.ejercicioBusqueda = ejercicioBusqueda;
    }

}
