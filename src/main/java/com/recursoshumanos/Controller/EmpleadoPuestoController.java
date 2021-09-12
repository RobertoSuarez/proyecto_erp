/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.EmpleadoDAO;
import com.recursoshumanos.Model.DAO.EmpleadoPuestoDAO;
import com.recursoshumanos.Model.DAO.EmpleadoSucursalDAO;
import com.recursoshumanos.Model.DAO.HorarioLaboralDAO;
import com.recursoshumanos.Model.DAO.PuestoLaboralDAO;
import com.recursoshumanos.Model.DAO.SucursalDAO;
import com.recursoshumanos.Model.Entidad.EmpleadoPuesto;
import com.recursoshumanos.Model.Entidad.EmpleadoSucursal;
import com.recursoshumanos.Model.Entidad.HorarioLaboral;
import com.recursoshumanos.Model.Entidad.PuestoLaboral;
import com.recursoshumanos.Model.Entidad.Sucursal;
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
@Named(value = "empleadoPuestoView")
@ViewScoped
public class EmpleadoPuestoController implements Serializable {
    
    /**
     * Se declaran las variables del modelo Controlador de
     * la parte de Empleado Puesto Parte1.
     */
    private final EmpleadoSucursalDAO empleadoSucursalDAO;
    private final EmpleadoPuestoDAO empleadoPuestoDAO;
    
    /**
     * Se declaran las variables del modelo Controlador de
     * la parte de Empleado Puesto Parte2.
     */
    private EmpleadoPuesto empleadoPuesto;
    private EmpleadoSucursal empleadoSucursal;
    
    /**
     * Se declaran las variables del modelo Controlador de
     * la parte de Empleado Puesto Parte3.
     */
    private List<PuestoLaboral> puestos;
    private List<HorarioLaboral> horarios;
    private List<Sucursal> sucursales;
    
    /**
     * Se declaran las variables del modelo Controlador de
     * la parte de Empleado Puesto Parte4.
     */
    private int idPuesto, idHorario, idSucursal;

    /**
     * Se crea las nuevas variables para asignarlos
     */
    public EmpleadoPuestoController() {
        empleadoSucursalDAO = new EmpleadoSucursalDAO();
        empleadoPuestoDAO = new EmpleadoPuestoDAO();
        empleadoPuesto = new EmpleadoPuesto();
        empleadoSucursal = new EmpleadoSucursal();
        puestos = new ArrayList<>();
        horarios = new ArrayList<>();
        sucursales = new ArrayList<>();
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
    public void constructorEmpleadoPuestoEditar() {
        PuestoLaboralDAO puestoLaboralDAO = new PuestoLaboralDAO();
        puestos = puestoLaboralDAO.Activos();
        HorarioLaboralDAO horarioLaboralDAO = new HorarioLaboralDAO();
        horarios = horarioLaboralDAO.Activos();
        SucursalDAO sucursalDAO = new SucursalDAO();
        sucursales = sucursalDAO.Listar();
        
    }

    /**
     * A continuación continuan los métodos de GET y SET
     * de cada una de las variables declaradas al inicio de
     * la clase.
     * @return lista Los GET tienen un return que nos retornan
     * los datos y los SET una variable que recibe el dato.
     */
    public EmpleadoPuesto getEmpleadoPuesto() {
        return empleadoPuesto;
    }

    public void setEmpleadoPuesto(EmpleadoPuesto empleadoPuesto) {
        this.empleadoPuesto = empleadoPuesto;
    }

    public EmpleadoSucursal getEmpleadoSucursal() {
        return empleadoSucursal;
    }

    public void setEmpleadoSucursal(EmpleadoSucursal empleadoSucursal) {
        this.empleadoSucursal = empleadoSucursal;
    }

    public List<PuestoLaboral> getPuestos() {
        return puestos;
    }

    public void setPuestos(List<PuestoLaboral> puestos) {
        this.puestos = puestos;
    }

    public List<HorarioLaboral> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioLaboral> horarios) {
        this.horarios = horarios;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }
    
    /**
     * Evento que carga al empleado sucursal
     * @param idEmpleado Objeto que carga el ID del empleado
     */
    public void postLoad(int idEmpleado) {
        if (idEmpleado > 0 ) {
            EmpleadoDAO empleadoDAO = new EmpleadoDAO();
            empleadoDAO.setEmpleado(empleadoDAO.buscarPorId(idEmpleado));
            empleadoSucursal = empleadoSucursalDAO.buscar(empleadoDAO.getEmpleado());
            empleadoPuesto = empleadoPuestoDAO.buscar(empleadoDAO.getEmpleado());
            idPuesto = empleadoPuesto.getPuestoLaboral().getId();
            idHorario = empleadoPuesto.getHorarioLaboral().getId();
            idSucursal = empleadoSucursal.getSucursal().getId();
            PrimeFaces.current().ajax().update(null, "form:dt-puesto");
        }
    }

    /**
     * Eventio que regustra al nuevo empleado al presioanr el
     * botón de guardar en la interfaz del usuario
     *
     */
    public void guardar() {
        if (empleadoPuesto.getPuestoLaboral().getId() != idPuesto || empleadoPuesto.getHorarioLaboral().getId() != idHorario) {
            empleadoPuestoDAO.desactivar();
            empleadoPuesto.getPuestoLaboral().setId(idPuesto);
            empleadoPuesto.getHorarioLaboral().setId(idHorario);
            empleadoPuesto.setId(0);
            if (empleadoPuestoDAO.insertar(empleadoPuesto) > 0) {
                empleadoPuesto.setId(empleadoPuestoDAO.getEmpleadoPuesto().getId());
                mostrarMensajeInformacion("Los datos del puesto laboral se ha guardado con éxito");
            } else {
                mostrarMensajeError("Los datos del puesto laboral no se pudieron guardar");
            }
        } else {
            if (empleadoPuestoDAO.actualizar(empleadoPuesto) > 0) {
                mostrarMensajeInformacion("Los datos del puesto laboral se ha actualizado con éxito");
            } else {
                mostrarMensajeError("Los datos del puesto laboral no se pudieron actualizar");
            }
        }
        if (empleadoSucursal.getSucursal().getId() != idSucursal) {
            empleadoSucursalDAO.desactivar();
            empleadoSucursal.getSucursal().setId(idSucursal);
            empleadoSucursal.setId(0);
            if (empleadoSucursalDAO.insertar(empleadoSucursal) > 0) {
                empleadoSucursal.setId(empleadoSucursalDAO.getEmpleadoSucursal().getId());
                mostrarMensajeInformacion("Los datos de la sucursal se ha guardado con éxito");
            } else {
                mostrarMensajeError("Los datos de la sucursal no se pudieron guardar");
            }
        } else {
            if (empleadoPuestoDAO.actualizar(empleadoPuesto) > 0) {
                mostrarMensajeInformacion("Los datos de la sucursal se ha actualizado con éxito");
            } else {
                mostrarMensajeError("Los datos de la sucursal no se pudieron actualizar");
            }
        }
        PrimeFaces.current().ajax().update("form:messages", "form:dt-puesto");
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
}
