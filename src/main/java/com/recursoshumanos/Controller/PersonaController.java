/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.PersonaDAO;
import com.recursoshumanos.Model.Entidad.Persona;
import com.seguridad.models.Roles;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
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
 * Las clases CONTROLLER son los que responden a la interacción (eventos del
 * mismo) que hace el usuario en la interfaz y realiza las peticiones al
 * modelDAO
 */
@Named(value = "personaView")
@ViewScoped
public class PersonaController implements Serializable {
    

    /**
     * Se declaran las variables del modelo Controlador de la parte de Persona
     */
    private Persona persona;

    private final PersonaDAO personaDAO;

    /**
     * Constructor de la clase
     */
    public PersonaController() {
        personaDAO = new PersonaDAO();
    }

    public Persona getPersona() {
        return persona;
    }

    /**
     *  cargar() Carga un persona
     * @param id id de la persona que se desea cargar
     */
    public void cargar(int id) {
        if (id > 0) {
            persona = personaDAO.buscarPorId(id);
        } else {
            persona = new Persona();
        }
        PrimeFaces.current().ajax().update("form:messages", "form:dt-personas");
    }

    /**
     * enviar() Guarda los datos de la persona Verifica si se trata de una
     * inserción o una actualización y actúa en cosecuencia
     */
    public void enviar() {
        personaDAO.setPersona(persona);
        if (persona.getId() == 0) {
            if (personaDAO.insertar() > 0) {
                mostrarMensajeInformacion("La persona se ha guardado con éxito");
            } else {
                mostrarMensajeError("La persona no se pudo guardar");
            }
        } else {
            if (personaDAO.actualizar() > 0) {
                mostrarMensajeInformacion("La persona se ha editado con éxito");
            } else {
                mostrarMensajeError("La persona no se pudo editar");
            }
        }
        PrimeFaces.current().ajax().update("form:messages", "form:dt-personas");
    }

    /**
     * Evento que muestra el mensaje de informaciòn en la interfaz de que ha
     * sido con Error el mensaje
     *
     * @param mensaje Objeto que almacena la información ha ser mostrada en la
     * interfaz.
     */
    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Éxito", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    //  MENSAJE DE ERROR
    public void mostrarMensajeError(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
