/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.controllers;

import com.cuentasporcobrar.daos.PersonaDAO;
import com.cuentasporcobrar.daos.Persona_JuridicaDAO;
import com.cuentasporcobrar.daos.Persona_NaturalDAO;
import com.cuentasporcobrar.models.Persona;
import com.cuentasporcobrar.models.Persona_Juridica;
import com.cuentasporcobrar.models.Persona_Natural;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

@Named(value = "personaController")
@ViewScoped
public class PersonaController implements Serializable {

    //Objeto para traer funciones de primefaces
    PrimeFaces current = PrimeFaces.current();

    //Declaro mis clases Persona y PersonaDAO
    Persona persona;
    PersonaDAO personaDAO;

    //Declaro mis clases Persona_Natural y Persona_NaturalDAO
    Persona_Natural persona_Natural;
    Persona_NaturalDAO persona_NaturalDAO;

    //Declaro mis clases Persona_Juridica y Persona_JuridicaDAO
    Persona_Juridica persona_Juridica;
    Persona_JuridicaDAO persona_JuridicaDAO;

    //Declaro mi listaCliente que van hacer cargadas en el datatable
    List<Persona> listaCliente;

    int idCliente = 0;

    //Constructor que instancia mis clases declaradas
    public PersonaController() {
        persona = new Persona();
        personaDAO = new PersonaDAO();

        persona_Juridica = new Persona_Juridica();
        persona_Natural = new Persona_Natural();

        try {

            listaCliente = new ArrayList<>();
            //Esta linea de código nos obtiene todos los clientes.
            //@return Retorna una lista, la cual será cargada en la tabla clientes.
            listaCliente = personaDAO.obtenerTodosLosClientes();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

    public void mostrar() {
        try {
            listaCliente = personaDAO.obtenerTodosLosClientes();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    //Getters y Setters 
    //Inicio
    public Persona getPersona() {
        return persona;
    }

    public void setPersonaDAO(PersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }

    public List<Persona> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Persona> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public Persona_Natural getPersona_Natural() {
        return persona_Natural;
    }

    public void setPersona_Natural(Persona_Natural persona_Natural) {
        this.persona_Natural = persona_Natural;
    }

    public Persona_Juridica getPersona_Juridica() {
        return persona_Juridica;
    }

    public void setPersona_Juridica(Persona_Juridica persona_Juridica) {
        this.persona_Juridica = persona_Juridica;
    }
    //Fin

    public void cargarClientes(Persona per) {
        try {
            this.persona = per;
            idCliente = per.getIdCliente();
            if (personaDAO.identificar_cliente(idCliente).equals("N")) {

                System.out.println("Entra al if Natural");
                //CARGAR EN EL OBJETO PERSONA NATURAL LOS DATOS.
                obtenerUnClienteNatural(idCliente);
                current.ajax().update(":dialogEditarClienteN");
                current.executeScript("PF('ClienteNaturalEdit').show();");

            } else {

                System.out.println("Entra al if Juridico");
                //CARGAR EN EL OBJETO PERSONA JURIDICA LOS DATOS.
                obtenerUnClienteJuridico(idCliente);
                current.ajax().update(":dialogEditarClienteJ");
                current.executeScript("PF('ClienteJuridicoEdit').show();");

            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    //Método que retorna los clientes Juridicos
    public void inactivarCliente(int id) {
        try {
            System.out.println(id);
            if (personaDAO.deshabilitarCliente(id) > 0) {
                System.out.print("Cliente inactivo");
                listaCliente = personaDAO.obtenerTodosLosClientes();
                mostrarMensajeInformacion("Cliente Inactivado Correctamente");
            } else {
                System.out.print("Error al inactivar cliente");
                mostrarMensajeError("No se pudo Inactivar al Cliente");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void activarCliente(int id) {
        try {
            if (personaDAO.habilitarCliente(id) > 0) {
                System.out.print("Cliente Activado");
                mostrarMensajeInformacion("Cliente Activado Correctamente");
                this.listaCliente = personaDAO.obtenerTodosLosClientes();
            } else {
                System.out.print("Error al activar cliente");
                mostrarMensajeInformacion("No se pudo Activar al Cliente");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void registrarClienteJuridico() {
        try {
            persona_JuridicaDAO = new Persona_JuridicaDAO(persona_Juridica);

            if (validarIdentificacion(persona_Juridica.getIdentificacion(),
                    personaDAO.obtenerTodosLosClientes())) {

                if (persona_JuridicaDAO.insertarClienteJuridico() > 0) {

                    mostrarMensajeInformacion("Se Registró Correctamente");
                    this.listaCliente = personaDAO.obtenerTodosLosClientes();

                } else {

                    System.out.println("No se Ingresó el Cliente Juridico.");
                    mostrarMensajeError("No se Registró Correctamente");

                }

            } else {
                System.out.println("La identificación del cliente ya Existe.!");
                mostrarMensajeError("La identificación del cliente ya Existe.!");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        PrimeFaces.current().executeScript("PF('clienteJuridicoNew').hide()");
        PrimeFaces.current().executeScript("location.reload()");
    }

    /*
    *@param identificacion : identificación del nuevo cliente
    *@param listIdentificaciones : lista de todas las identificaciones de la BD.
     */
    public boolean validarIdentificacion(String identificacion, List<Persona> listIdentificaciones) {

        for (Persona lst : listIdentificaciones) {
            if (lst.getIdentificacion().equals(identificacion)) {
                return false;
            }
        }
        /*
        *@return true Si la identificación no se repite, se retornará True.
        *@return false Si la identificación ya existe en la BD, se retorna False.
         */
        return true;
    }

    //Actualizar Objeto de Nuevo Cliente Juridico
    public void nuevoClienteJ() {
        this.persona_Juridica = new Persona_Juridica();
    }

    public void registrarClienteNatural() {
        try {
            persona_NaturalDAO = new Persona_NaturalDAO(persona_Natural);

            if (validarIdentificacion(persona_Natural.getIdentificacion(),
                    personaDAO.obtenerTodosLosClientes())) {

                if (persona_NaturalDAO.insertarClienteNatural() > 0) {
                    
                    mostrarMensajeInformacion("Se Registró Correctamente");
                    this.listaCliente = personaDAO.obtenerTodosLosClientes();
                    
                } else {
                    
                    System.out.println("No se Ingresó el Cliente Natural.");
                    mostrarMensajeError("No se Registró Correctamente");
                    
                }
            }else {
                System.out.println("La identificación del cliente ya Existe.!");
                mostrarMensajeError("La identificación del cliente ya Existe.!");
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        PrimeFaces.current().executeScript("PF('clienteNaturalNew').hide()");
        PrimeFaces.current().executeScript("location.reload()");
    }

    //Actualizar Objeto de Nuevo Cliente Natural
    public void nuevoClienteN() {
        this.persona_Natural = new Persona_Natural();
    }

    //Al momento de darle click al icono de editar, se ejecuta este procedi.
    public void obtenerUnClienteJuridico(int idClienteJ) {
        try {

            //Se almacena el id cliente en una variable auxiliar
            int aux = idClienteJ;
            persona_JuridicaDAO = new Persona_JuridicaDAO(persona_Juridica);
            //Se obtiene ese cliente por el id
            Persona_Juridica per_juridica = persona_JuridicaDAO.obtenerClienteJuridico(idClienteJ);

            //Se remplazan los objetos
            persona_Juridica = per_juridica;
            //Ubicamos nuevamente el id de la variable auxiliar
            persona_Juridica.setIdCliente(aux);
            //Se instancia nuevamente la personaJuridicaDAO pero con todos los 
            //datos recopilados
            persona_JuridicaDAO = new Persona_JuridicaDAO(persona_Juridica);

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void obtenerUnClienteNatural(int idClienteN) {
        try {
            //Se almacena el id cliente en una variable auxiliar
            int aux = idClienteN;
            persona_NaturalDAO = new Persona_NaturalDAO(persona_Natural);

            //Se obtiene ese cliente por el id
            Persona_Natural per_Natural = persona_NaturalDAO.obtenerClienteNatural(idClienteN);

            //Se remplazan los objetos
            persona_Natural = per_Natural;

            //Ubicamos nuevamente el id de la variable auxiliar
            persona_Natural.setIdCliente(aux);

            //Se instancia nuevamente la personaJuridicaDAO pero con todos los 
            //datos recopilados
            persona_NaturalDAO = new Persona_NaturalDAO(persona_Natural);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void actualizarClienteJuridico() {
        try {
            if (persona_JuridicaDAO.actualizarClienteJuridico() > 0) {
                System.out.println("Se Editó Correctamente");
                mostrarMensajeInformacion("Se Editó Correctamente");
                listaCliente = personaDAO.obtenerTodosLosClientes();
            } else {
                System.out.println("No se Editó");
                mostrarMensajeError("No se Editó Correctamente");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void actualizarClienteNatural() {
        try {
            if (persona_NaturalDAO.actualizarClienteNatural() > 0) {
                System.out.println("Se Editó Correctamente");
                mostrarMensajeInformacion("Se Editó Correctamente");
                listaCliente = personaDAO.obtenerTodosLosClientes();
            } else {
                System.out.println("No se Editó");
                mostrarMensajeError("No se Editó Correctamente");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    //Metodos para mostrar mensajes de Información y Error
    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Exito", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void mostrarMensajeError(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
