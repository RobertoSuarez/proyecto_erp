/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.controllers;

import com.cuentasporcobrar.daos.PersonaDAO;
import com.cuentasporcobrar.daos.Plan_PagoDAO;
import com.cuentasporcobrar.models.Persona;
import com.cuentasporcobrar.models.Plan_Pago;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 * Una clase Plan_PagoController que se va a encargar de la lógica de negocio
 * que lleva consigo tener acceso a la BD y al modelo.
 *
 * @author Alexander Vega, Andy Ninasunta.
 */
@Named(value = "plan_PagoController")
@ViewScoped
public class Plan_PagoController implements Serializable {

    //Objeto para traer funciones de primefaces
    PrimeFaces current = PrimeFaces.current();

    //Se Declaran las clases Plan_Pago y Plan_PagoDAO
    Plan_Pago plan_Pago;
    Plan_PagoDAO plan_PagoDAO;

    //Objeto Persona y Persona DAO para buscar un cliente
    Persona persona;
    PersonaDAO personaDAO;

    //Lista donde se cargaran los cobros
    List<Plan_Pago> lista_Cobros;

    //Variable con la identificacion;
    String identificacion = "";

    /**
     * Constructor Plan_PagoController vacio.
     */
    public Plan_PagoController() {

    }

    /**
     * Se realiza la búsqueda de un cliente.
     */
    public void buscarCliente() {
        try {

            //Se inicializa la clase PersonaDAO.
            personaDAO = new PersonaDAO();

            //Se inicializa la clase Persona.
            persona = new Persona();

            //Cargamos el nombre del cliente en el input.
            persona = personaDAO.obtenerNombreClienteXIdentificacion(identificacion);

            //Este if nos permite verificar si existe o no un cliente.
            if (persona.getIdCliente() == 0) {

                /*Muestra un mensaje de advertencia en caso de ser 0 el 
                  idCliente. */
                System.out.println("El Cliente NO EXISTE O ESTA INACTIVO ");
                mostrarMensajeAdvertencia("El Cliente No Existe o esta Inactivo");
            } else {

                /*En caso de que exista cargamos sus cobros (Claro si 
                  tiene cobros). */
                lista_Cobros = new ArrayList<>();
                plan_PagoDAO = new Plan_PagoDAO();

                //Cargamos los cobros de un determinado Cliente.
                lista_Cobros = plan_PagoDAO.obtenerCobrosCliente(persona.getIdCliente());

                //Este if valida si el cliente tiene o no cobros.
                if (lista_Cobros.isEmpty()) {

                    /*Muestra un mensaje de advertencia en caso de no tener 
                      cobros el cliente. */
                    mostrarMensajeAdvertencia("Ese cliente no tiene cobros");
                } else {

                    /*Muestra un mensaje de información en caso de tener cobros 
                      el cliente. */
                    mostrarMensajeInformacion("Se Cargaron los Cobros de "
                            + persona.getRazonNombre());

                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Se selecciona la fila de un determinado cliente.
     *
     * @param event Un evento de selección de primefaces.
     */
    public void onRowSelect(SelectEvent<Persona> event) {
        try {
            this.identificacion = event.getObject().getIdentificacion();
            System.out.println(this.identificacion);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Getters y Setters de las Listas
    //Inicio
    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Plan_PagoController(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public List<Plan_Pago> getLista_Cobros() {
        return lista_Cobros;
    }
    //Fin

    /**
     * Se Indican los mensajes de información.
     *
     * @param mensaje Se guarda el mensaje que desee mostrar en la interfaz.
     */
    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Éxito: ", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Se Indican los mensajes de error.
     *
     * @param mensaje Se guarda el mensaje que desee mostrar en la interfaz.
     */
    public void mostrarMensajeAdvertencia(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Advertencia: ", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Se Indican los mensajes de advertencia.
     *
     * @param mensaje Se guarda el mensaje que desee mostrar en la interfaz.
     */
    public void mostrarMensajeError(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error: ", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
