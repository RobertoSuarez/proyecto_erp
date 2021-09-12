/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.CargoDAO;
import com.recursoshumanos.Model.DAO.DepartamentoDAO;
import com.recursoshumanos.Model.DAO.PuestoLaboralDAO;
import com.recursoshumanos.Model.Entidad.Cargo;
import com.recursoshumanos.Model.Entidad.Departamento;
import com.recursoshumanos.Model.Entidad.PuestoLaboral;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@Named(value = "puestoLaboralView")
@ViewScoped
public class PuestoLaboralController implements Serializable {

    /**
     * Se declaran las variables del modelo Controlador de
     * la parte de Puesto Laboral.
     */
    private final PuestoLaboralDAO puestoLaboralDAO;
    private final DepartamentoDAO departamentoDAO;
    private final CargoDAO cargoDAO;

    private PuestoLaboral puestoLaboral;
    private List<PuestoLaboral> lista;
    private List<Departamento> departamentos;
    private List<Cargo> cargos;
    private int idDepartamento, idCargo;
        
    /**
     * Se crea las nuevas variables para asignarlos
     */
    public PuestoLaboralController() {
        puestoLaboralDAO = new PuestoLaboralDAO();
        departamentoDAO = new DepartamentoDAO();
        cargoDAO = new CargoDAO();
        lista = new ArrayList<>();
        departamentos = new ArrayList<>();
        cargos = new ArrayList<>();
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
        lista = puestoLaboralDAO.Listar();
        departamentos = departamentoDAO.Listar();
        cargos = cargoDAO.Listar();
    }

    /**
     * A continuación continuan los métodos de GET y SET
     * de cada una de las variables declaradas al inicio de
     * la clase.
     * @return lista Los GET tienen un return que nos retornan
     * los datos y los SET una variable que recibe el dato.
     */
    public PuestoLaboral getPuestoLaboral() {
        return puestoLaboral;
    }

    public void setPuestoLaboral(PuestoLaboral puestoLaboral) {
        this.puestoLaboral = puestoLaboral;
    }

    public List<PuestoLaboral> getLista() {
        return lista;
    }

    public void setLista(List<PuestoLaboral> puestos) {
        this.lista = puestos;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }
    
    /**
     * Evento que asigna formato a la fecha
     * @param fecha Contiene el formato que sera asignado
     * @return Retorna con el formato ya asignado
     */
    public String darFormato(Date fecha){
        return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
    }

    /**
     * Permite crear un Puesto Laboral a lo que el usuario
     * persiona el botòn de nuevo en la interfaz del usuario
     */
    public void abrirNuevo() {
        idCargo = 0;
        idDepartamento = 0;
        puestoLaboral = new PuestoLaboral(0, new Cargo(), new Departamento(), new Date(), true, "");
    }

    /**
     * Evento que permite editar el puesto laboral
     * @param idCargo Objeto que utiliza el ID del cargo para 
     * poder ser editado.
     * @param idDepartamento Objeto que usa el ID del departamento
     * para poder ser editado.
     */
    public void abrirEditar(int idCargo, int idDepartamento) {
        this.idCargo = idCargo;
        this.idDepartamento = idDepartamento;
    }
    
    /**
     * Evento que permite seleccionar el cargo y departamento
     * para el puesto laboral
     */
    public void asignarCargoDepartamento(){
        for(Cargo cargo : cargos){
            if(cargo.getId() == idCargo){
                puestoLaboral.setCargo(cargo);
                break;
            }
        }
        for(Departamento departamento : departamentos){
            if(departamento.getId() == idDepartamento){
                puestoLaboral.setDepartamento(departamento);
                break;
            }
        }
    }

    /**
     * Evento que permite enviar
     */
    public void enviar() {
        if (idCargo != 0 && idDepartamento != 0) {
            asignarCargoDepartamento();
            puestoLaboralDAO.setPuestoLaboral(puestoLaboral);
            if (puestoLaboral.getId() == 0) {
                if (puestoLaboralDAO.insertar() > 0) {
                    mostrarMensajeInformacion("El Puesto Laboral se ha guardado con éxito");
                    lista.add(puestoLaboral);
                } else {
                    mostrarMensajeError("El Puesto Laboral no se pudo guardar");
                }
            } else {
                if (puestoLaboralDAO.actualizar() > 0) {
                    mostrarMensajeInformacion("El Puesto Laboral se ha editado con éxito");
                } else {
                    mostrarMensajeError("El Puesto Laboral no se pudo editar");
                }
            }
        }else{
            mostrarMensajeError("Debe de seleccionar un cargo y un departamento");
        }
        PrimeFaces.current().executeScript("PF('managePuestoLaboralDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-puestoLaborals");
    }
    
    /**
     * Permite cambiar el estado de activado a desactivado del
     * puesto laboral
     * @param puestoLaboral Objeto que lleva el estado
     */
    public void cambiarEstado(PuestoLaboral puestoLaboral){
        puestoLaboralDAO.setPuestoLaboral(puestoLaboral);
        puestoLaboralDAO.cambiarEstado();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-puestoLaborals");
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
