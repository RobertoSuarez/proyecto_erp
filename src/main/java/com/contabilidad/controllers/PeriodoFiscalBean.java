/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contabilidad.controllers;

import com.contabilidad.dao.PeriodoFiscalDAO;
import com.contabilidad.models.PeriodoFiscal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author cturriagos
 */
@Named(value = "periodoFiscalView")
@ViewScoped
public class PeriodoFiscalBean implements Serializable {

    private final PeriodoFiscalDAO periodoFiscalDAO;
    private PeriodoFiscal periodoFiscal;
    private List<PeriodoFiscal> lista;

    public PeriodoFiscalBean() {
        periodoFiscalDAO = new PeriodoFiscalDAO(new PeriodoFiscal());
        //periodoFiscal = new PeriodoFiscal();
        lista = new ArrayList<>();
    }

    @PostConstruct
    public void constructorPeriodoFiscal() {
        lista = periodoFiscalDAO.Listar();
    }

    public PeriodoFiscal getPeriodoFiscal() {
        return periodoFiscal;
    }

    public void setPeriodoFiscal(PeriodoFiscal periodoFiscal) {
        this.periodoFiscal = periodoFiscal;
    }

    public List<PeriodoFiscal> getLista() {
        return lista;
    }

    public void setLista(List<PeriodoFiscal> periodosFiscales) {
        this.lista = periodosFiscales;
    }

    public void abrirNuevo() {
        this.periodoFiscal = new PeriodoFiscal();
    }

    public void enviar() {
        this.periodoFiscal.setFechaInicio(new Date(this.periodoFiscal.getPreriodo(), 1, 1, 0, 0, 0));
        this.periodoFiscal.setFechaFin(new Date(this.periodoFiscal.getPreriodo(), 12, 31, 23, 59, 59));
        this.periodoFiscal.setActivo(true);
        this.periodoFiscalDAO.setPeriodoFiscal(periodoFiscal);
        this.periodoFiscal.setIdPeriodoFiscal(this.periodoFiscalDAO.insertar());
        if (this.periodoFiscal.getIdPeriodoFiscal() > 0 ){
            mostrarMensajeInformacion("El periodo fiscal se ha guardado con éxito");
        }else{
            mostrarMensajeError("El periodo fiscal no fue guardado");
        }
    }

    public void desactivar(PeriodoFiscal periodoFiscal) {
        this.periodoFiscalDAO.setPeriodoFiscal(periodoFiscal);
        this.periodoFiscalDAO.descativar();
        mostrarMensajeInformacion("Se desactivo el perioco " + this.periodoFiscalDAO.getPeriodoFiscal().getPreriodo());
        PrimeFaces.current().ajax().update("form:messages", "form:dt-periodos-fiscales");
    }

    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    //  MENSAJE DE ERROR
    public void mostrarMensajeError(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void mostrarMensajePrecaucion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención!", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
