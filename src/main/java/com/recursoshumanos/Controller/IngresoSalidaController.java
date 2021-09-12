/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.IngresosSalidasDAO;
import com.recursoshumanos.Model.Entidad.IngresosSalidas;
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
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 *
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades para
 * todo lo que tenga que ver con un horario laboral. Y se encarga de administrar
 * las sentencias de la BD, utilizando las clases de los modelos
 */
@Named(value = "ingresosSalidasView")
@ViewScoped
public class IngresoSalidaController implements Serializable {

    /**
     * Se declaran las variables que usarán el Controlador de ingresos y
     * salidas.
     */
    private IngresosSalidas ingresosSalidas;
    private final IngresosSalidasDAO ingresosSalidasDAO;
    private List<IngresosSalidas> lista;

    /**
     * Constructore del controlador
     */
    public IngresoSalidaController() {
        ingresosSalidasDAO = new IngresosSalidasDAO(new IngresosSalidas());
        lista = new ArrayList<>();
    }

    /**
     * La notación POSTCONSTRUCT define un método como un método de
     * inicialización de un bean que se ejecuta después de que se complete el
     * ingreso de la dependencia.
     */
    @PostConstruct
    /**
     * Se crea el constructor
     */
    public void constructorIngresosSalidas() {
        lista = ingresosSalidasDAO.Listar();
    }

    /**
     * A continuación continuan los métodos de GET y SET de cada una de las
     * variables declaradas al inicio de la clase.
     *
     * @return lista Los GET tienen un return que nos retornan los datos y los
     * SET una variable que recibe el dato.
     */
    public IngresosSalidas getIngresosSalidas() {
        return ingresosSalidas;
    }

    public void setIngresosSalidas(IngresosSalidas ingresosSalidas) {
        this.ingresosSalidas = ingresosSalidas;
    }

    public List<IngresosSalidas> getLista() {
        return lista;
    }

    public void setLista(List<IngresosSalidas> ingresosSalidas) {
        this.lista = ingresosSalidas;
    }

    /**
     * abrirNuevo() Instancia un nuevo ingreso y salida para ser llenado
     */
    public void abrirNuevo() {
        this.ingresosSalidas = new IngresosSalidas();
        PrimeFaces.current().ajax().update("form:messages", "form:manage-ingresosSalidas-content");
    }

    /**
     * enviar() Guarda los datos de ingreso y de salida Verifica si se traa de
     * una inserción o una actualización y actúan en consecuencia
     */
    public void enviar() {
        ingresosSalidasDAO.setIngresosSalidas(ingresosSalidas);
        if (ingresosSalidas.getId() == 0) {
            if (ingresosSalidasDAO.insertar() > 0) {
                mostrarMensajeInformacion("El registro de horas de salida e ingreso se ha guardado con éxito");
                lista.add(ingresosSalidas);
            } else {
                mostrarMensajeError("El registro de horas de salida e ingreso no se pudo guardar");
            }
        } else {
            if (ingresosSalidasDAO.actualizar() > 0) {
                mostrarMensajeInformacion("El registro de horas de salida e ingreso  se ha editado con éxito");
            } else {
                mostrarMensajeError("El registro de horas de salida e ingreso  no se pudo editar");
            }
        }
        PrimeFaces.current().executeScript("PF('manageIngresosSalidasDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-ingresosSalidas");
    }

    /**
     * Evento que muestra el mensaje de informaciòn en la interfaz de que ha
     * sido con Error el mensaje
     *
     * @param mensaje Objeto que almacena la información ha ser mostrada en la
     * interfaz.
     */
    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    //  MENSAJE DE ERROR
    public void mostrarMensajeError(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
