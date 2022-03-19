/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.controllers;

import com.cuentasporcobrar.daos.AbonoDAO;
import com.cuentasporcobrar.daos.ChequeDAO;
import com.cuentasporcobrar.models.Abono;
import com.cuentasporcobrar.models.Cheque;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Crisito
 */
@Named(value = "chequeMB")
@ViewScoped
public class chequesController implements Serializable {

    private List<Cheque> lstCheques;
    ChequeDAO chequeDAO = new ChequeDAO();
    Abono abon = new Abono();
    AbonoDAO abonDao = new AbonoDAO();

    public chequesController() {
        lstCheques = new ArrayList<>();
        lstCheques = chequeDAO.ObtenerTodosLosCheques();

    }

    public void IngresarCheque(Cheque chequeAux) {
        if ("Devuelto".equals(chequeAux.getEstadoCheque())||"Cobrado".equals(chequeAux.getEstadoCheque())) {
            mostrarMensajeError("El cheque ha sido devuelto o ya se realizo el cobro");
        } else {
            if (chequeAux.getFecha().isEqual(LocalDate.now()) || chequeAux.getFecha().isBefore(LocalDate.now())) {
                abon.setIdVenta(chequeAux.getIdVenta());
                abon.setIdPlanDePago(abonDao.obtenerIdPlanPago(chequeAux.getIdVenta()));
                abon.setIdFormaDePago(3);
                abon.setValorAbonado(chequeAux.getValor_Cheque());
                abon.setFechaAbono(LocalDate.now());
                int revision = abonDao.insertarNuevoCobro(abon, chequeDAO.ObtenerIdCliente(abon.getIdVenta()), abon.getIdPlanDePago());
                System.out.println("Se inserto un nuevo abono");
                if (revision < 0) {
                    mostrarMensajeInformacion("El pago completo se realizo satisfactoriamente");
                    abonDao.insertAccountingSeat(abon.getIdVenta(), abon);
                    System.out.println("Se inserto el asiento contable");
                    mostrarMensajeInformacion("Se ingreso el abono correspondiente");
                    chequeAux.setEstadoCheque("Cobrado");
                    chequeDAO.ModificarCheque(chequeAux);
                    lstCheques = new ArrayList<>();
                    lstCheques = chequeDAO.ObtenerTodosLosCheques();
                }
            } else {
                mostrarMensajeInformacion("No es la fecha de pago");
            }
        }
    }

    public void RetirarCheque(Cheque chequeAux) {
        if ("Cobrado".equals(chequeAux.getEstadoCheque())) {
            mostrarMensajeError("El cheque ya ha sido cobrado");
        } else {
            chequeAux.setEstadoCheque("Devuelto");
            chequeDAO.ModificarCheque(chequeAux);
            mostrarMensajeAdvertencia("Se ha retirado un cheque");
            lstCheques = new ArrayList<>();
            lstCheques = chequeDAO.ObtenerTodosLosCheques();
        }
    }

    //Metodos para mostrar mensajes de Información y Error
    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Exitó", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void mostrarMensajeAdvertencia(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Advertencia", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void mostrarMensajeError(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<Cheque> getLstCheques() {
        return lstCheques;
    }

    public void setLstCheques(List<Cheque> lstCheques) {
        this.lstCheques = lstCheques;
    }

    public Abono getAbon() {
        return abon;
    }

    public void setAbon(Abono abon) {
        this.abon = abon;
    }

}
