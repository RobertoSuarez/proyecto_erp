/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.CiudadDAO;
import com.recursoshumanos.Model.DAO.ProvinciaDAO;
import com.recursoshumanos.Model.Entidad.Ciudad;
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
@Named(value = "ciudadView")
@ViewScoped
public class CiudadController implements Serializable {

    /**
     * Se declaran las variables del modelo Controlador de
     * la parte de Ciudad.
     */
    private final CiudadDAO ciudadDAO;
    private List<Ciudad> ciudades;
    private Ciudad ciudad;
    private final ProvinciaDAO provinciaDAO;
    private List<Provincia> provincias;
    private List<Provincia> filtroProvincias;
    
    private int idProvincia;
    
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    List<Roles> listaRoles = (List<Roles>) context.getExternalContext().getSessionMap().get("usuario_rol");

    /**
     * Se crea las nuevas variables para asignarlos
     */
    public CiudadController() {
        ciudad = new Ciudad();
        ciudades = new ArrayList<>();
        ciudadDAO = new CiudadDAO();
        provincias = new ArrayList<>();
        provinciaDAO = new ProvinciaDAO();
        filtroProvincias = new ArrayList<>();
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
    public void constructorCiudad() {
        this.idProvincia = 0;
        this.ciudades = ciudadDAO.Listar();
        for(Ciudad ciudad : ciudades){
            if(filtroProvincias.isEmpty()){
                filtroProvincias.add(ciudad.getProvincia());
            }else{
                if(!filtroProvincias.contains(ciudad.getProvincia())){
                    filtroProvincias.add(ciudad.getProvincia());
                }
            }
        }
        this.provincias = provinciaDAO.Listar();
    }

    /**
     * A continuación continuan los métodos de GET y SET
     * de cada una de las variables declaradas al inicio de
     * la clase.
     * @return lista Los GET tienen un return que nos retornan
     * los datos y los SET una variable que recibe el dato.
     */
    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    public List<Provincia> getFiltroProvincias() {
        return filtroProvincias;
    }

    public void setFiltroProvincias(List<Provincia> filtroProvincias) {
        this.filtroProvincias = filtroProvincias;
    }
    
    /**
     * Evento para editar
     * @param id Objeto que ayuda para identificar el ID
     */
    public void editar(int id){
        idProvincia = id;
    }
    
    /**
     * Evento para cancelar (Botón cancelar al momento
     * que la interfaz se encuentra en la ventana
     * de registro).
     */
    public void cancelar(){
        if (idProvincia != 0 ){
            ciudad.getProvincia().setId(idProvincia);
            idProvincia = 0;
        }
    }
    
    /**
     * Evento que carga la Provincia que se haya
     * registrado con aterioridad.
     */
    public void cargarProvincia(){
        for(Provincia provincia : provincias){
            if (provincia.getId() == ciudad.getProvincia().getId()){
                this.ciudad.getProvincia().setNombre(provincia.getNombre());
                break;
            }
        }
    }

    /**
     * evento que permite el abrir o generar una nuev
     */
    public void abrirNuevo() {
        this.ciudad = new Ciudad();
        idProvincia = ciudad.getProvincia().getId();
        PrimeFaces.current().ajax().update("form:messages", "form:manage-ciudad-content");
    }

    /**
     * Evento que permite enviar los datos, para ejecutar 
     * los insert o updates en la BD.
     */
    public void enviar() {
        cargarProvincia();
        ciudadDAO.setCiudad(ciudad);
        if (ciudad.getId() == 0) {
            if (ciudadDAO.insertar() > 0) {
                mostrarMensajeInformacion("La ciudad se ha guardado con éxito");
                ciudades.add(ciudad);
            } else {
                mostrarMensajeError("La ciudad no se pudo guardar");
            }
        } else {
            if (ciudadDAO.actualizar() > 0) {
                mostrarMensajeInformacion("La ciudad se ha editado con éxito");
            } else {
                ciudad.getProvincia().setId(idProvincia);
                mostrarMensajeError("La ciudad no se pudo editar");
            }
        }
        PrimeFaces.current().executeScript("PF('manageCiudadDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-ciudades");
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