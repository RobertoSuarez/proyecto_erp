/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.DedicacionDAO;
import com.recursoshumanos.Model.Entidad.Dedicacion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
@Named(value = "dedicacionView")
@ViewScoped
public class DedicacionController implements Serializable {

    /**
     * Se declaran las variables del modelo Controlador de
     * la parte de Dedicación.
     */
    private Dedicacion dedicacion;
    private final DedicacionDAO dedicacionDAO;
    private List<Dedicacion> lista;

    /**
     * Se crea las nuevas variables para asignarlos
     */
    public DedicacionController() {
        dedicacionDAO = new DedicacionDAO(new Dedicacion());
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
    public void constructorDedicacion() {
        lista = dedicacionDAO.Listar();
    }

    /**
     * A continuación continuan los métodos de GET y SET
     * de cada una de las variables declaradas al inicio de
     * la clase.
     * @return lista Los GET tienen un return que nos retornan
     * los datos y los SET una variable que recibe el dato.
     */
    public Dedicacion getDedicacion() {
        return dedicacion;
    }

    public void setDedicacion(Dedicacion dedicacion) {
        this.dedicacion = dedicacion;
    }

    public List<Dedicacion> getLista() {
        return lista;
    }

    public void setLista(List<Dedicacion> dedicaciones) {
        this.lista = dedicaciones;
    }

    /**
     * evento que permite el abrir o generar una nueva dedicación
     */
    public void abrirNuevo() {
        this.dedicacion = new Dedicacion();
        PrimeFaces.current().ajax().update("form:messages", "form:manage-dedicacion-content");
    }

    /**
     * Evento que permite enviar los datos, para ejecutar 
     * los insert o updates en la BD.
     */
    public void enviar() {
        dedicacionDAO.setDedicacion(dedicacion);
        if (dedicacion.getId() == 0) {
            if (dedicacionDAO.insertar() > 0) {
                mostrarMensajeInformacion("La dedicacion se ha guardado con éxito");
                lista.add(dedicacion);
            } else {
                mostrarMensajeError("La dedicacion no se pudo guardar");
            }
        } else {
            if (dedicacionDAO.actualizar() > 0) {
                mostrarMensajeInformacion("La dedicacion se ha editado con éxito");
            } else {
                mostrarMensajeError("La dedicacion no se pudo editar");
            }
        }
        PrimeFaces.current().executeScript("PF('manageDedicacionDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-dedicaciones");
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