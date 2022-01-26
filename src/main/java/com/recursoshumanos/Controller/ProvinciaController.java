/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.ProvinciaDAO;
import com.recursoshumanos.Model.Entidad.Provincia;
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
@Named(value = "provinciaView")
@ViewScoped
public class ProvinciaController implements Serializable {
    
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    List<Roles> listaRoles = (List<Roles>) context.getExternalContext().getSessionMap().get("usuario_rol");
    
    /**
     * Se declaran las variables del modelo Controlador de
     * la parte de Provincia.
     */
    private final ProvinciaDAO provinciaDAO;
    private Provincia provincia;
    private List<Provincia> provincias;
    private List<Provincia> filteredProvincia;

    /**
     * Se crea las nuevas variables para asignarlos
     */
    public ProvinciaController() {
        provinciaDAO = new ProvinciaDAO();
        provincia = new Provincia();
        provincias = new ArrayList<>();
        filteredProvincia = new ArrayList<>();
        
        if ("Gerente".equals(listaRoles.get(0).getNombre()) || 
                "Administrador de la empresa".equals(listaRoles.get(0).getNombre())|| 
                "Jefe de recursos humanos".equals(listaRoles.get(0).getNombre())||
                "Asistente de recursos humanos".equals(listaRoles.get(0).getNombre()))
            System.out.println("Ingreso exitoso");
        else{
            try {
                externalContext.redirect("/proyecto_erp/View/Global/Main.xhtml");
            } catch (IOException ex) {

            }
        }
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
    public void constructorRolPago() {
        provincias = provinciaDAO.Listar();
    }

    /**
     * A continuación continuan los métodos de GET y SET
     * de cada una de las variables declaradas al inicio de
     * la clase.
     * @return lista Los GET tienen un return que nos retornan
     * los datos y los SET una variable que recibe el dato.
     */
    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }

    public List<Provincia> getFilteredProvincia() {
        return filteredProvincia;
    }

    public void setFilteredProvincia(List<Provincia> filteredProvincia) {
        this.filteredProvincia = filteredProvincia;
    }

    /**
     * Permite crear una nueva provincia a lo que el usuario
     * persiona el botòn de nuevo en la interfaz del usuario
     */
    public void nuevo() {
        this.provincia = new Provincia();
        PrimeFaces.current().ajax().update("form:messages", "form:manage-provincia-content");
    }

    /**
     * Permite enviar una nueva provincia a lo que el usuario
     * persiona el botòn de enviar en la interfaz del usuario
     */
    public void enviar() {
        provinciaDAO.setProvincia(provincia);
        if (provincia.getId() == 0) {
            if (provinciaDAO.insertar() > 0) {
                mostrarMensajeInformacion("La provincia se ha guardado con éxito");
                provincias.add(provincia);
            } else {
                mostrarMensajeError("La provincia no se pudo guardar");
            }
        } else {
            if (provinciaDAO.actualizar() > 0) {
                mostrarMensajeInformacion("La provincia se ha editado con éxito");
            } else {
                mostrarMensajeError("La provincia no se pudo editar");
            }
        }
        PrimeFaces.current().executeScript("PF('manageProvinciaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-provincias");
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
