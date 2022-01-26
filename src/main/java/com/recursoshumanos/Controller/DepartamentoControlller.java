/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.DepartamentoDAO;
import com.recursoshumanos.Model.Entidad.Departamento;
import com.seguridad.models.Roles;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
@Named(value = "departamentoView")
@ViewScoped
public class DepartamentoControlller implements Serializable {

    /**
     * Se declaran las variables del modelo Controlador de
     * la parte de Ciudad.
     */
    private Departamento departamento;
    private final DepartamentoDAO departamentoDAO;
    private List<Departamento> lista;
    
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    List<Roles> listaRoles = (List<Roles>) context.getExternalContext().getSessionMap().get("usuario_rol");

    /**
     * Se crea las nuevas variables para asignarlos
     */
    public DepartamentoControlller() {
        departamento = new Departamento();
        departamentoDAO = new DepartamentoDAO(new Departamento());
        lista = new ArrayList<>();
        
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
    public void constructorDepartamento() {
        lista = departamentoDAO.Listar();
    }

    /**
     * MÉTODO ENVIAR Hace un llamado al DAO para ejecutar el método
     * insertarDepartamento
     *
     */
    public void enviar() {
        departamentoDAO.setDepartamento(departamento);
        if (departamento.getId() == 0) {
            if (departamentoDAO.insertar() > 0) {
                departamento.setId(departamentoDAO.getDepartamento().getId());
                mostrarMensajeInformacion("El departamento se ha guardado con éxito");
                lista.add(departamento);
            } else {
                mostrarMensajeError("El departamento no se pudo guardar");
            }
        } else {
            if (departamentoDAO.actualizar() > 0) {
                mostrarMensajeInformacion("El departamento se ha editado con éxito");
            } else {
                mostrarMensajeError("El departamento no se pudo editar");
            }
        }
        PrimeFaces.current().executeScript("PF('manageDepartamentoDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-departamentos");
    }
    
    /**
     * Permite habilitar o deshabilitar el estado del departamento
     * al momento de que el usuario presione el botón del mismo.
     * @param departamento Objeto que lleva el evento de activado
     * y desactivado
     */
    public void cambiarEstado(Departamento departamento){
        departamentoDAO.setDepartamento(departamento);
        departamentoDAO.cambiarEstado();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-puestoLaborals");
    }
    
    /**
     * Da formato a las fechas para una clara visualización en 
     * la interfaz del usuario y asu vez permite registrar
     * de forma correcta la fecha en la Base de Datos
     * @param fecha Objeto que carga con el formato de la fecha
     * @return fecha retorna la fecha con el nuevo parametro
     */
    public String darFormato(Date fecha){
        return fecha != null? new SimpleDateFormat("dd/MM/yyyy").format(fecha):"";
    }

    /**
     * Evento que permite abrir un nuevo Departamento
     * a lo que el usuario presiona el botón de NUEVO en la
     * interfaz gràfica
     */
    public void abrirNuevo() {
        this.departamento = new Departamento();
    }

    
    /**
     * A continuación continuan los métodos de GET y SET
     * de cada una de las variables declaradas al inicio de
     * la clase.
     * @return lista Los GET tienen un return que nos retornan
     * los datos y los SET una variable que recibe el dato.
     */
    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Departamento> getLista() {
        return lista;
    }

    public void setLista(List<Departamento> departamentos) {
        this.lista = departamentos;
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
