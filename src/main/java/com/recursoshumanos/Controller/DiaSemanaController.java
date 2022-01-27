/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.DiaSemanaDAO;
import com.recursoshumanos.Model.Entidad.DiaSemana;
import com.seguridad.models.Roles;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
  * 
  * @author kestradalp
  * @author ClasK7
  * @author rturr
  * 
  * Las clases CONTROLLER son los que responden a la interacción
  * (eventos del mismo) que hace el usuario en la interfaz
  * y realiza las peticiones al modelDAO
  */
@Named(value = "diaSemanaView")
@ViewScoped
public class DiaSemanaController implements Serializable {
    
    /**
     * Se declaran las variables del modelo Controlador de
     * la parte de Día de la Semana.
     */
    private DiaSemana diaSemana;
    private List<DiaSemana> lista;
    private final DiaSemanaDAO diaSemanaDAO;

    /**
     * Se crea las nuevas variables para asignarlos
     */
    public DiaSemanaController() {
        diaSemanaDAO = new DiaSemanaDAO();
        diaSemana = new DiaSemana();
        lista = new ArrayList<>();
    }

    /**
     * La notación POSTCONSTRUCT define un método como un método
     * de inicialización de un bean que se ejecuta después de que
     * se complete el ingreso de la dependencia.
     */
    @PostConstruct
    /**
     * Se crea el constructor
     */
    public void constructorDiaSemana() {
        lista = diaSemanaDAO.Listar();
    }

    /**
     * A continuación continuan los métodos de GET y SET
     * de cada una de las variables declaradas al inicio de
     * la clase.
     * @return lista Los GET tienen un return que nos retornan
     * los datos y los SET una variable que recibe el dato.
     */
    public List<DiaSemana> getLista() {
        return lista;
    }

    public void setLista(List<DiaSemana> lista) {
        this.lista = lista;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setCargo(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void abrirNuevo() {
        diaSemana = new DiaSemana();
    }

    /**
     * MÉTODO ENVIAR Hace un llamado al DAO para ejecutar el método
     * insertar o actualizar
     *
     */
    public void enviar() {
        diaSemanaDAO.setDiaSemana(diaSemana);
        if (diaSemana.getId() == 0) {
            if (diaSemanaDAO.insertar() > 0) {
                mostrarMensajeInformacion("El dia/semana se ha guardado con éxito");
                lista.add(diaSemana);
            } else {
                mostrarMensajeError("El dia/semana no se pudo guardar");
            }
        } else {
            if (diaSemanaDAO.actualizar() > 0) {
                mostrarMensajeInformacion("El dia/semana se ha editado con éxito");
            } else {
                mostrarMensajeError("El dia/semana no se pudo editar");
            }
        }
        PrimeFaces.current().executeScript("PF('manageDiaSemanaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-diaSemanas");
    }

    /**
     * Evento que muestra el mensaje de informaciòn en la interfaz
     * de que ha sido Éxitoso el mensaje 
     * @param mensaje Objeto que almacena la información
     * ha ser mostrada en la interfaz.
     */
    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Evento que muestra el mensaje de informaciòn en la interfaz
     * de que ha sido con Error el mensaje 
     * @param mensaje Objeto que almacena la información
     * ha ser mostrada en la interfaz.
     */
    public void mostrarMensajeError(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}