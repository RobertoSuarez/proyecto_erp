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

/**
 * Una clase PersonaController que se va a encargar de la lógica de negocio que
 * lleva consigo tener acceso a la BD y al modelo.
 *
 * @author Alexander Vega, Andy Ninasunta.
 */

@Named(value = "personaController")
@ViewScoped

public class PersonaController implements Serializable {

    //Objeto para traer funciones de primefaces
    PrimeFaces current = PrimeFaces.current();

    //Declaro mis clases Persona y PersonaDAO
    Persona persona;
    PersonaDAO personaDAO;

    //Declaro mis clases Persona_Natural y Persona_NaturalDAO.
    Persona_Natural persona_Natural;
    Persona_NaturalDAO persona_NaturalDAO;

    //Declaro mis clases Persona_Juridica y Persona_JuridicaDAO.
    Persona_Juridica persona_Juridica;
    Persona_JuridicaDAO persona_JuridicaDAO;

    //Declaro mi listaCliente que van hacer cargadas en la tabla.
    List<Persona> listaCliente;
    List<Persona> listaClienteInactivos;

    //Declaro una variable idCliente para guardar el id de un cliente.
    int idCliente = 0;

    /**
     * Constructor que instancia mis clases declaradas.
     */
    public PersonaController() {

        //Inicializo mi clase Persona
        persona = new Persona();

        //Inicializo mi clase PersonaDAO
        personaDAO = new PersonaDAO();

        //Inicializo mi clase Persona_Juridica
        persona_Juridica = new Persona_Juridica();

        //Inicializo mi clase Persona_JuridicaDAO
        persona_Natural = new Persona_Natural();

        try {

            /*Inicializo mi listaCliente y guardo todos lo clientes
              que obtengo de un metodo en mi clase personaDAO.*/
            listaCliente = new ArrayList<>();
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

    public List<Persona> getListaClienteInactivos() {
        return this.listaClienteInactivos = personaDAO.obtenerClientesInactivos();
    }

    //Fin
    
    /**
     * Cargar la información de un cliente en un nuevo objeto Persona.
     *
     * @param per Crear un nuevo objeto persona como un auxiliar.
     */
    public void cargarClientes(Persona per) {
        try {
            /* Igualamos el Objeto per con el objeto persona ya instanciado
               en el constructor*/
            this.persona = per;
            idCliente = per.getIdCliente();

            /*Realizamos una condición para identificar al cliente que se
              le deben cargar los datos.*/
            if (personaDAO.identificar_cliente(idCliente).equals("N")) {

                System.out.println("Entra al if Natural");
                /**
                 * Cargar en el objeto persona natural los datos, y a su vez
                 * actualizar y cerrar el dialogo
                 */
                obtenerUnClienteNatural(idCliente);
                current.ajax().update(":dialogEditarClienteN");
                current.executeScript("PF('ClienteNaturalEdit').show();");

            } else {

                System.out.println("Entra al if Juridico");
                /**
                 * Cargar en el objeto persona juridica los datos, y a su vez
                 * actualizar y cerrar el dialogo
                 */
                obtenerUnClienteJuridico(idCliente);
                current.ajax().update(":dialogEditarClienteJ");
                current.executeScript("PF('ClienteJuridicoEdit').show();");

            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Realiza la inactivación de un cliente sea este natural o jurídico.
     *
     * @param id Es el id del cliente que se desea inactivar.
     */
    public void inactivarCliente(int id) {
        try {
            System.out.println(id);
            
            /*Realizamos una condición en la que se indica si el cliente
              a inactivar existe para poder realizar la inactivación.*/
            if (personaDAO.deshabilitarCliente(id) > 0) {
                System.out.print("Cliente inactivo");
                
                /*Una vez inactivado el cliente se actualiza la lista
                  cliente y arroja un mensaje de información.*/
                listaCliente = personaDAO.obtenerTodosLosClientes();
                mostrarMensajeInformacion("Cliente Inactivado Correctamente");
            } else {
                System.out.print("Error al inactivar cliente");
                
                //Caso contrario se arroja un mensaje de error.
                mostrarMensajeError("No se pudo Inactivar al Cliente");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Realiza la activación de un cliente sea este natural o jurídico.
     *
     * @param id Es el id del cliente que se desea activar.
     */
    public void activarCliente(int id) {
        try {
            if (personaDAO.habilitarCliente(id) > 0) {
                System.out.print("Cliente Activado");
                /*Una vez activado el cliente se actualiza la lista
                  cliente y arroja un mensaje de información.*/
                mostrarMensajeInformacion("Cliente Activado Correctamente");
                this.listaCliente = personaDAO.obtenerTodosLosClientes();
            } else {
                System.out.print("Error al activar cliente");
                //Caso contrario se arroja un mensaje de error.
                mostrarMensajeInformacion("No se pudo Activar al Cliente");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Realiza el registro de los clientes jurídicos.
     */
    public void registrarClienteJuridico() {
        try {
            /*Instancia el objeto persona_juridicaDAO y le paso el 
              objeto persona juridica*/
            persona_JuridicaDAO = new Persona_JuridicaDAO(persona_Juridica);

            /*Validando de que la identificacion ingresada corresponda al tipo
              de identificacion.*/
            if (persona_Juridica.getIdentificacion().length() == 13) {

                //Validando que no exista la identificación ingresada.
                if (validarIdentificacion(persona_Juridica.getIdentificacion(),
                        personaDAO.obtenerTodosLosClientes())) {

                    //Validando el registro existoso de un cliente juridico.
                    if (persona_JuridicaDAO.insertarClienteJuridico() > 0) {

                        /*Una vez se registra el cliente juridico se actualiza
                          el dialogo y muestra un mensaje de información.*/
                        mostrarMensajeInformacion("Se Registró Correctamente");
                        this.listaCliente = personaDAO.obtenerTodosLosClientes();
                        PrimeFaces.current().executeScript("PF('clienteJuridicoNew').hide()");
                        PrimeFaces.current().ajax().update(":frmtblClientes:tblClientes");
                    } else {

                        System.out.println("No se Ingresó el Cliente Juridico.");
                        //Caso contrario se arroja un mensaje de error.
                        mostrarMensajeError("No se Registró Correctamente");
                    }

                } else {

                    System.out.println("La identificación del cliente ya Existe.!");
                    //Caso contrario se arroja un mensaje de Advertencia.
                    mostrarMensajeAdvertencia("La identificación del cliente ya Existe.!");
                }
            } else {

                //Caso contrario se arroja un mensaje de Advertencia.
                mostrarMensajeAdvertencia("Por favor digite un número de"
                        + " Identificación correspondiente a un RUC.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * @param identificacion : identificación del nuevo cliente
     * @param listIdentificaciones : lista de todas las identificaciones de la
     * BD.
     * @return true Si la identificación no se repite, se retornará True, caso
     * contrario se retorna False.
     */
    public boolean validarIdentificacion(String identificacion,
            List<Persona> listIdentificaciones) {

        /*El for se realiza para ir identificando si se repite la 
          identificación de un cliente.*/
        for (Persona lst : listIdentificaciones) {
            if (lst.getIdentificacion().equals(identificacion)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Se realiza la instancia de un nuevo objeto en persona_Juridica.
     */
    public void nuevoClienteJ() {
        this.persona_Juridica = new Persona_Juridica();
    }

    /**
     * Se realiza el registro de un cliente natural.
     */
    public void registrarClienteNatural() {
        try {
            persona_NaturalDAO = new Persona_NaturalDAO(persona_Natural);

            /*Validando que la longitud de la identificacion ingresada 
              sea mayor a 7. */
            if (persona_Natural.getIdentificacion().length() > 7) {

                /*Validando de que la identificacion ingresada corresponda 
                  al tipo de identificacion. */
                if ((persona_Natural.getIdentificacion().length() == 10
                        && persona_Natural.getIdTipoIdenficacion() == 1)
                        || (persona_Natural.getIdentificacion().length() == 13
                        && persona_Natural.getIdTipoIdenficacion() == 2)
                        || (persona_Natural.getIdentificacion().length() == 13
                        && persona_Natural.getIdTipoIdenficacion() == 3)) {

                    //Validando que no exista la identificación ingresada.
                    if (validarIdentificacion(persona_Natural.getIdentificacion(),
                            personaDAO.obtenerTodosLosClientes())) {

                        //Validando el registro exitoso de un cliente natural.
                        if (persona_NaturalDAO.insertarClienteNatural() > 0) {

                            /*Una vez se registra el cliente natural se 
                              actualiza el dialogo y se muestra un mensaje 
                              de información.*/
                            mostrarMensajeInformacion("Se Registró Correctamente");
                            this.listaCliente = personaDAO.obtenerTodosLosClientes();
                            PrimeFaces.current().executeScript("PF('clienteNaturalNew').hide()");
                            PrimeFaces.current().ajax().update(":frmtblClientes:tblClientes");
                        } else {

                            //Caso contrario se muestra un mensaje de Error.
                            System.out.println("No se Ingresó el Cliente Natural.");
                            mostrarMensajeError("No se Registró Correctamente");

                        }
                    } else {

                        System.out.println("La identificación del cliente ya Existe.!");
                        //Caso contrario se muestra un mensaje de Advertencia.
                        mostrarMensajeAdvertencia("La identificación del cliente ya Existe.!");
                    }
                } else {

                    //Caso contrario se muestra un mensaje de Advertencia.
                    mostrarMensajeAdvertencia("Por favor digite un número de"
                            + " Identificación correspondiente al tipo de "
                            + "identificacion elegido.");
                    System.out.println("Por favor digite un número de"
                            + " Identificación correspondiente al tipo de "
                            + "identificacion elegido.");
                }

            } else {

                //Caso contrario se muestra un mensaje de Advertencia.
                mostrarMensajeAdvertencia("Por favor digite un número de"
                        + " Identificación Correcto.");
                System.out.println("Por favor digite un número de"
                        + " Identificación Correcto.");
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Se realiza la instancia de un nuevo objeto en persona_Natural.
     */
    public void nuevoClienteN() {
        this.persona_Natural = new Persona_Natural();
    }

    /**
     * Se obtiene un cliente jurídico por su id.
     *
     * @param idClienteJ Obtener el id de un cliente jurídico.
     */
    public void obtenerUnClienteJuridico(int idClienteJ) {
        try {

            /*Se almacena el id cliente en una variable auxiliar y realiza
              la instancia a un nuevo objeto persona_JuridicaDAO.*/
            int aux = idClienteJ;
            persona_JuridicaDAO = new Persona_JuridicaDAO(persona_Juridica);

            //Se obtiene ese cliente por el id.
            Persona_Juridica per_juridica = persona_JuridicaDAO.obtenerClienteJuridico(idClienteJ);

            //Se remplazan los objetos
            persona_Juridica = per_juridica;

            //Ubicamos nuevamente el id de la variable auxiliar.
            persona_Juridica.setIdCliente(aux);

            /*Se instancia nuevamente la personaJuridicaDAO pero con todos  
              los datos recopilados. */
            persona_JuridicaDAO = new Persona_JuridicaDAO(persona_Juridica);

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Se Obtiene un cliente natural por su id.
     *
     * @param idClienteN Obtener el id de un cliente natural.
     */
    public void obtenerUnClienteNatural(int idClienteN) {
        try {

            //Se almacena el id cliente en una variable auxiliar.
            int aux = idClienteN;
            persona_NaturalDAO = new Persona_NaturalDAO(persona_Natural);

            //Se obtiene ese cliente por el id.
            Persona_Natural per_Natural = persona_NaturalDAO.obtenerClienteNatural(idClienteN);

            //Se remplazan los objetos.
            persona_Natural = per_Natural;

            //Ubicamos nuevamente el id de la variable auxiliar.
            persona_Natural.setIdCliente(aux);

            /*Se instancia nuevamente la personaJuridicaDAO pero con todos 
              los datos recopilados. */
            persona_NaturalDAO = new Persona_NaturalDAO(persona_Natural);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Se realizar la modificación de un cliente juridico.
     */
    public void actualizarClienteJuridico() {
        try {

            /*Validando de que la identificacion ingresada corresponda al 
              tipo de identificacion. */
            if (persona_Juridica.getIdentificacion().length() == 13) {

                /*Una vez se modifica el cliente juridico se actualiza
                  el dialogo y muestra un mensaje de información.*/
                if (persona_JuridicaDAO.actualizarClienteJuridico() > 0) {
                    System.out.println("Se Editó Correctamente");
                    mostrarMensajeInformacion("Se Editó Correctamente");
                    listaCliente = personaDAO.obtenerTodosLosClientes();
                } else {

                    System.out.println("No se Editó");
                    //Caso contrario se muestra un mensaje de error.
                    mostrarMensajeError("No se Editó Correctamente");
                }
            } else {

                //Caso contrario se muestra un mensaje de advertencia.
                mostrarMensajeAdvertencia("Por favor digite un número de"
                        + " Identificación correspondiente a un RUC.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        //Y finalmente se cierra el dialogo y actualiza la tabla.
        PrimeFaces.current().executeScript("PF('ClienteJuridicoEdit').hide()");
        PrimeFaces.current().ajax().update(":frmtblClientes:tblClientes");
    }

    /**
     * Se realizar la modificación de un cliente natural.
     */
    public void actualizarClienteNatural() {
        try {

            /*Validando de que la identificacion ingresada corresponda al 
              tipo de identificacion. */
            if ((persona_Natural.getIdentificacion().length() == 10
                    && persona_Natural.getIdTipoIdenficacion() == 1)
                    || (persona_Natural.getIdentificacion().length() == 13
                    && persona_Natural.getIdTipoIdenficacion() == 2)
                    || (persona_Natural.getIdentificacion().length() == 13
                    && persona_Natural.getIdTipoIdenficacion() == 2)) {

                /*Una vez se modifica el cliente natural se actualiza
                  el dialogo y muestra un mensaje de información.*/
                if (persona_NaturalDAO.actualizarClienteNatural() > 0) {
                    System.out.println("Se Editó Correctamente");
                    mostrarMensajeInformacion("Se Editó Correctamente");
                    listaCliente = personaDAO.obtenerTodosLosClientes();
                } else {

                    //Caso contrario se muestra un mensaje de error.
                    System.out.println("No se Editó");
                    mostrarMensajeError("No se Editó Correctamente");
                }
            } else {

                //Caso contrario se muestra un mensaje de advertencia.
                mostrarMensajeAdvertencia("Por favor digite un número de"
                        + " Identificación correspondiente al tipo de "
                        + "identificacion elegido.");
                System.out.println("Por favor digite un número de"
                        + " Identificación correspondiente al tipo de "
                        + "identificacion elegido.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        //Y finalmente se cierra el dialogo y actualiza la tabla.
        PrimeFaces.current().executeScript("PF('ClienteNaturalEdit').hide()");
        PrimeFaces.current().ajax().update(":frmtblClientes:tblClientes");
    }

    /**
     * Se Indican los mensajes de información.
     *
     * @param mensaje Se guarda el mensaje que desee mostrar en la interfaz.
     */
    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Éxito", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Se Indican los mensajes de error.
     *
     * @param mensaje Se guarda el mensaje que desee mostrar en la interfaz.
     */
    public void mostrarMensajeAdvertencia(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Advertencia", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Se Indican los mensajes de advertencia.
     *
     * @param mensaje Se guarda el mensaje que desee mostrar en la interfaz.
     */
    public void mostrarMensajeError(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
