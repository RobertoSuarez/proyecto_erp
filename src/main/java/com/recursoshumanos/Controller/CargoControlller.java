/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.CargoDAO;
import com.recursoshumanos.Model.Entidad.Cargo;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

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
@Named(value = "cargoView")
@ViewScoped
public class CargoControlller implements Serializable {

    /**
     * Se declaran las variables del modelo Controlador de
     * la parte de Cargo.
     */
    private Cargo cargo;
    private final CargoDAO cargoDAO;
    private List<Cargo> lista;

    /**
     * Se crean las nuevas variables para ser 
     * asignadas.
     */
    public CargoControlller() {
        cargoDAO = new CargoDAO(new Cargo());
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
    public void constructorCargo() {
        lista = cargoDAO.Listar();
    }
    
     /**
     * A continuación continuan los métodos de GET y SET
     * de cada una de las variables declaradas al inicio de
     * la clase.
     * @return lista Los GET tienen un return que nos retornan
     * los datos y los SET una variable que recibe el dato.
     */
    public List<Cargo> getLista() {
        return lista;
    }

    public void setLista(List<Cargo> lista) {
        this.lista = lista;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public void abrirNuevo() {
        this.cargo = new Cargo();
    }

    /**
     * MÉTODO ENVIAR Hace un llamado al DAO para ejecutar el método
     * insertar o actualizar
     *
     */
    public void enviar() {
        cargoDAO.setCargo(cargo);
        if (cargo.getId() == 0) {
            if (cargoDAO.insertar() > 0) {
                mostrarMensajeInformacion("El cargo se ha guardado con éxito");
                lista.add(cargo);
            } else {
                mostrarMensajeError("El cargo no se pudo guardar");
            }
        } else {
            if (cargoDAO.actualizar() > 0) {
                mostrarMensajeInformacion("El cargo se ha editado con éxito");
            } else {
                mostrarMensajeError("El cargo no se pudo editar");
            }
        }
        PrimeFaces.current().executeScript("PF('manageCargoDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cargos");
    }

    public void onCancel(RowEditEvent<Cargo> event) {
        mostrarMensajeInformacion("Se canceló");
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
