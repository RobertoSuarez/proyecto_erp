/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.AmonestacionDAO;
import com.recursoshumanos.Model.DAO.EmpleadoSucursalDAO;
import com.recursoshumanos.Model.DAO.EmpleadoReservaDAO;
import com.recursoshumanos.Model.DAO.HorarioLaboralDAO;
import com.recursoshumanos.Model.DAO.EmpleadoPuestoDAO;
import com.recursoshumanos.Model.DAO.PuestoLaboralDAO;
import com.recursoshumanos.Model.DAO.CargaFamiliarDAO;
import com.recursoshumanos.Model.DAO.EmpleadoDAO;
import com.recursoshumanos.Model.DAO.MultaDAO;
import com.recursoshumanos.Model.DAO.SucursalDAO;
import com.recursoshumanos.Model.DAO.PersonaDAO;
import com.recursoshumanos.Model.DAO.SueldoDAO;
import com.recursoshumanos.Model.DAO.SuspencionDAO;
import com.recursoshumanos.Model.Entidad.Amonestacion;
import com.recursoshumanos.Model.Entidad.EmpleadoSucursal;
import com.recursoshumanos.Model.Entidad.EmpleadoReserva;
import com.recursoshumanos.Model.Entidad.EmpleadoPuesto;
import com.recursoshumanos.Model.Entidad.HorarioLaboral;
import com.recursoshumanos.Model.Entidad.CargaFamiliar;
import com.recursoshumanos.Model.Entidad.PuestoLaboral;
import com.recursoshumanos.Model.Entidad.Sucursal;
import com.recursoshumanos.Model.Entidad.Empleado;
import com.recursoshumanos.Model.Entidad.Multa;
import com.recursoshumanos.Model.Entidad.Persona;
import com.recursoshumanos.Model.Entidad.Sueldo;
import com.recursoshumanos.Model.Entidad.Suspencion;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.commons.math3.util.Precision;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author cturriagos
 */
@Named(value = "empleadoView")
@ViewScoped
public class EmpleadoController implements Serializable {

    private final EmpleadoSucursalDAO empleadoSucursalDAO;
    private final EmpleadoReservaDAO empleadoReservaDAO;
    private final HorarioLaboralDAO horarioLaboralDAO;
    private final EmpleadoPuestoDAO empleadoPuestoDAO;
    private final PuestoLaboralDAO puestoLaboralDAO;
    private final CargaFamiliarDAO cargaFamiliarDAO;
    private final AmonestacionDAO amonestacionDAO;
    private final SuspencionDAO suspencionDAO;
    private final SucursalDAO sucursalDAO;
    private final EmpleadoDAO empleadoDAO;
    private final PersonaDAO personaDAO;
    private final SueldoDAO sueldoDAO;
    private final MultaDAO multaDAO;
    
    private EmpleadoSucursal empleadoSucursal;
    private EmpleadoReserva empleadoReserva;
    private EmpleadoPuesto empleadoPuesto;
    private CargaFamiliar cargaFamiliar;
    private Amonestacion amonestacion;
    private Suspencion suspencion;
    private Empleado empleado;
    private Persona persona;
    private Sueldo sueldo;
    private Multa multa;
    
    private List<HorarioLaboral> horarios;
    private List<PuestoLaboral> puestos;
    private List<Amonestacion> amonestaciones;
    private List<Suspencion> suspenciones;
    private List<Sucursal> sucursales;
    private List<Empleado> lista;
    private List<Sueldo> sueldos;
    private List<Multa> multas;
    
    private String resumeReserva;
    private Date fechaMax;

    public EmpleadoController() {
        empleadoSucursalDAO = new EmpleadoSucursalDAO();
        empleadoReservaDAO = new EmpleadoReservaDAO();
        empleadoPuestoDAO = new EmpleadoPuestoDAO();
        horarioLaboralDAO = new HorarioLaboralDAO();
        puestoLaboralDAO = new PuestoLaboralDAO();
        cargaFamiliarDAO = new CargaFamiliarDAO();
        amonestacionDAO = new AmonestacionDAO();
        suspencionDAO = new SuspencionDAO();
        sucursalDAO = new SucursalDAO();
        empleadoDAO = new EmpleadoDAO();
        personaDAO = new PersonaDAO();
        sueldoDAO = new SueldoDAO();
        multaDAO = new MultaDAO();
        
        empleadoSucursal = new EmpleadoSucursal();
        empleadoReserva = new EmpleadoReserva();
        empleadoPuesto = new EmpleadoPuesto();
        cargaFamiliar = new CargaFamiliar();
        amonestacion = new Amonestacion();
        suspencion = new Suspencion();
        empleado = new Empleado();
        persona = new Persona();
        sueldo = new Sueldo();
        multa = new Multa();
        
        lista = new ArrayList<>();
        multas = new ArrayList<>();
        puestos = new ArrayList<>();
        sueldos = new ArrayList<>();
        horarios = new ArrayList<>();
        sucursales = new ArrayList<>();
        fechaMax = new Date();
    }
    
    public void postLista(){
        lista = empleadoDAO.Listar();
    }

    public void postEmpleado(int idEmpleado) {
        if (idEmpleado > 0) {
            empleado = empleadoDAO.buscarPorId(idEmpleado);
            empleadoSucursal = empleadoSucursalDAO.buscar(empleado);
            empleadoReserva = empleadoReservaDAO.buscar(empleado);
            empleadoPuesto = empleadoPuestoDAO.buscar(empleado);
            cargaFamiliar = cargaFamiliarDAO.buscar(empleado);
            amonestacion = amonestacionDAO.buscar(empleado);
            suspencion = suspencionDAO.buscar(empleado);
            sueldo = sueldoDAO.Actual(empleado);
            multa = multaDAO.buacar(empleado);
            resumenReserva();
            PrimeFaces.current().ajax().update(null, "form:dt-empleado");
        }
    }
    
    public void postNuevoEmpleado(){
        empleadoDAO.setConexion(personaDAO.obtenerConexion());
        empleadoPuestoDAO.setConexion(personaDAO.obtenerConexion());
        empleadoPuestoDAO.setConexion(personaDAO.obtenerConexion());
        empleadoSucursalDAO.setConexion(personaDAO.obtenerConexion());
        sueldoDAO.setConexion(personaDAO.obtenerConexion());
        puestos = puestoLaboralDAO.Activos();
        horarios = horarioLaboralDAO.Activos();
        sucursales = sucursalDAO.Listar();
        fechaMax.setYear(103);
        empleado.getFechaNacimiento().setYear(103);
        PrimeFaces.current().ajax().update(null, "form:dt-empleado");
    }
    
    public void postEditarEmpleado(int idEmpleado){
        empleadoDAO.setConexion(personaDAO.obtenerConexion());
        if(idEmpleado > 0) {
            empleado = empleadoDAO.buscarPorId(idEmpleado);
            PrimeFaces.current().ajax().update("form:messages", "form:dt-empleado");
        }
    }

    public EmpleadoSucursal getEmpleadoSucursal() {
        return empleadoSucursal;
    }

    public void setEmpleadoSucursal(EmpleadoSucursal empleadoSucursal) {
        this.empleadoSucursal = empleadoSucursal;
    }

    public List<Amonestacion> getAmonestaciones() {
        return amonestaciones;
    }

    public void setAmonestaciones(List<Amonestacion> amonestaciones) {
        this.amonestaciones = amonestaciones;
    }

    public EmpleadoReserva getEmpleadoReserva() {
        return empleadoReserva;
    }

    public void setEmpleadoReserva(EmpleadoReserva empleadoReserva) {
        this.empleadoReserva = empleadoReserva;
    }

    public EmpleadoPuesto getEmpleadoPuesto() {
        return empleadoPuesto;
    }

    public void setEmpleadoPuesto(EmpleadoPuesto empleadoPuesto) {
        this.empleadoPuesto = empleadoPuesto;
    }

    public List<Suspencion> getSuspenciones() {
        return suspenciones;
    }

    public void setSuspenciones(List<Suspencion> suspenciones) {
        this.suspenciones = suspenciones;
    }

    public CargaFamiliar getCargaFamiliar() {
        return cargaFamiliar;
    }

    public void setCargaFamiliar(CargaFamiliar cargaFamiliar) {
        this.cargaFamiliar = cargaFamiliar;
    }

    public List<HorarioLaboral> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioLaboral> horarios) {
        this.horarios = horarios;
    }

    public Amonestacion getAmonestacion() {
        return amonestacion;
    }

    public void setAmonestacion(Amonestacion amonestacion) {
        this.amonestacion = amonestacion;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public List<PuestoLaboral> getPuestos() {
        return puestos;
    }

    public void setPuestos(List<PuestoLaboral> puestos) {
        this.puestos = puestos;
    }

    public String getResumeReserva() {
        return resumeReserva;
    }

    public void setResumeReserva(String resumeReserva) {
        this.resumeReserva = resumeReserva;
    }

    public Suspencion getSuspencion() {
        return suspencion;
    }

    public void setSuspencion(Suspencion suspencion) {
        this.suspencion = suspencion;
    }

    public List<Empleado> getLista() {
        return lista;
    }

    public void setLista(List<Empleado> empleados) {
        lista = empleados;
    }

    public List<Sueldo> getSueldos() {
        return sueldos;
    }

    public void setSueldos(List<Sueldo> sueldos) {
        this.sueldos = sueldos;
    }

    public String getResumenReserva() {
        return resumeReserva;
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Multa> getMultas() {
        return multas;
    }

    public void setMultas(List<Multa> multas) {
        this.multas = multas;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Date getFechaMax() {
        return fechaMax;
    }

    public void setFechaMax(Date fechaMax) {
        this.fechaMax = fechaMax;
    }

    public Sueldo getSueldo() {
        return sueldo;
    }

    public void setSueldo(Sueldo sueldo) {
        this.sueldo = sueldo;
    }

    public Multa getMulta() {
        return multa;
    }

    public void setMulta(Multa multa) {
        this.multa = multa;
    }
    
    private void resumenReserva(){
        if (empleadoReserva.getFormaPago() != 0) {
            resumeReserva = "$ " + (empleadoReserva.getFormaPago() * sueldo.getValor()) + " (" + (empleadoReserva.getFormaPago() == 1 ? "ANUAL" : "MENSUAL") + ")";
        } else {
            resumeReserva = "S/D";
        }
    }
    
    public void verAmonestaciones(){
        amonestaciones = amonestacionDAO.historial(empleado);
        PrimeFaces.current().ajax().update("form:messages", "form:dt-amonestaciones");
    }
    
    public void verSuspenciones(){
        suspenciones = suspencionDAO.Listar(empleado);
        PrimeFaces.current().ajax().update("form:messages", "form:dt-suspenciones");
    }
    
    public void verSueldos(){
        sueldos = sueldoDAO.historial(empleado);
        PrimeFaces.current().ajax().update("form:messages", "form:dt-sueldos");
    }
    
    public void verMultas(){
        multas = multaDAO.historial(empleado);
        PrimeFaces.current().ajax().update("form:messages", "form:dt-multas");
    }

    public void cambiarSueldo() {
        sueldoDAO.setSueldo(sueldo);
        sueldoDAO.desactivar();
        if (sueldoDAO.insertar() > 0) {
            sueldo.setId(sueldoDAO.getSueldo().getId());
            empleadoPuestoDAO.setEmpleadoPuesto(empleadoPuesto);
            empleadoPuestoDAO.actualizar();
            mostrarMensajeInformacion("Se ha cambiado el sueldo con éxito");
        }
        PrimeFaces.current().executeScript("PF('manageSueldoDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-empleado");
    }
    
    public void guardarMulta(){
        multa.setValor(Precision.round(sueldo.getValor() * (multa.getPorcentaje() /100), 2));
        multa.setId(multaDAO.insertar(multa));
        if(multa.getId() > 0){
            mostrarMensajeInformacion("Se ha guardado la multa con éxito");
        }else{
            mostrarMensajeError("La multa no se pudo guardar");
        }
    }
    
    public void guardarAmonestacion(){
        amonestacion.setId(amonestacionDAO.insertar(amonestacion));
        if(amonestacion.getId() > 0){
            mostrarMensajeInformacion("Se ha guardado la amonestación con éxito");
        }else{
            mostrarMensajeError("La amonestación no se pudo guardar");
        }
    }
    
    public void guardarSuspencion(){
        suspencion.setValor(Precision.round((suspencion.getValor()/30/8)* suspencion.getCantidadDias(), 2));
        suspencion.setId(suspencionDAO.insertar(suspencion));
        if(suspencion.getId() > 0){
            mostrarMensajeInformacion("Se ha guardado la suspención con éxito");
        }else{
            mostrarMensajeError("La suspención no se pudo guardar");
        }
    }

    public String guardar() {
        String mensaje = "";
        boolean result = false;
        personaDAO.setPersona(persona);
        if (personaDAO.insertar() > 0) {
            persona.setId(personaDAO.getPersona().getId());
            empleado.setPersona(persona);
            empleadoDAO.setEmpleado(empleado);
            if (empleadoDAO.insertar() > 0) {
                empleado.setId(empleadoDAO.getEmpleado().getId());
                empleadoPuesto.setEmpleado(empleado);
                empleadoPuestoDAO.setEmpleadoPuesto(empleadoPuesto);
                if (empleadoPuestoDAO.insertar() > 0) {
                    empleadoPuesto.setId(empleadoPuestoDAO.getEmpleadoPuesto().getId());
                    empleadoSucursal.setEmpleado(empleado);
                    empleadoSucursalDAO.setEmpleadoSucursal(empleadoSucursal);
                    if (empleadoSucursalDAO.insertar() > 0) {
                        empleadoSucursal.setId(empleadoSucursalDAO.getEmpleadoSucursal().getId());
                        sueldo.setEmpleado(empleado);
                        sueldoDAO.setSueldo(sueldo);
                        if (sueldoDAO.insertar() > 0) {
                            sueldo.setId(sueldoDAO.getSueldo().getId());
                            result = true;
                        } else {
                            mensaje = "El sueldo no se pudo asignar";
                        }
                    } else {
                        mensaje = "La sucursal no se pudo asignar";
                    }
                } else {
                    mensaje = "El puesto no se pudo asignar";
                }
            } else {
                mensaje = "El empleado no se pudo guardar";
            }
        } else {
            mensaje = "La persona no se pudo guardar";
        }
        if (result) {
            mostrarMensajeInformacion("Datos guardados correctamente");
            return "Empleado";
        } else {
            mostrarMensajeError(mensaje);
            return "";
        }
    }

    public String actualizar() {
        String mensaje = "";
        boolean result = false;
            empleadoDAO.setEmpleado(empleado);
            personaDAO.setPersona(empleado.getPersona());
            if (personaDAO.actualizar() > 0) {
                empleadoDAO.setEmpleado(empleado);
                if (empleadoDAO.actualizar() > 0) {
                    result = true;
                } else {
                    mensaje = "El empleado no se pudo actualizar";
                }
            } else {
                mensaje = "La persona no se pudo actualizar";
            }
        if (result) {
            mostrarMensajeInformacion("Datos actualizados correctamente");
            return "Empleado";
        } else {
            mostrarMensajeError(mensaje);
            return "";
        }
    }

    public void guardarCargaFamiliar() {
        cargaFamiliarDAO.setCargaFamiliar(cargaFamiliar);
        if (cargaFamiliar.getId() < 1) {
            if (cargaFamiliarDAO.insertar() > 0) {
                cargaFamiliar.setId(cargaFamiliarDAO.getCargaFamiliar().getId());
                mostrarMensajeInformacion("Los datos de la carga familiar se ha guardado con éxito");
            } else {
                mostrarMensajeError("Los datos de la carga familiar se pudieron guardar");
            }
        } else {
            if (cargaFamiliarDAO.actualizar() > 0) {
                mostrarMensajeInformacion("Los datos de la carga familiar se ha actualizado con éxito");
            } else {
                mostrarMensajeError("Los datos de la carga familiar se pudieron actualizar");
            }
        }
        PrimeFaces.current().executeScript("PF('manageCargaFamiliarDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-empleado");
    }
    
    public void cambiarEstado(Empleado empleado){
        empleadoDAO.setEmpleado(empleado);
        empleadoDAO.cambiarEstado();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-empleados");
    }

    public void guardarReserva() {
        empleadoReservaDAO.setEmpleadoReserva(empleadoReserva);
        if (resumeReserva.equalsIgnoreCase("S/D")) {
            if (empleadoReservaDAO.insertar() > 0) {
                mostrarMensajeInformacion("Los datos se guardarón con éxito");
            }else{
                mostrarMensajeError("No se pudierón guardar los datos");
            }
        }else{
            if (empleadoReservaDAO.actualizar() > 0) {
                mostrarMensajeInformacion("Los datos se actualizarón con éxito");
            }else{
                mostrarMensajeError("No se pudierón actualizar los datos");
            }
        }
        resumenReserva();
        PrimeFaces.current().executeScript("PF('manageReservaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-empleado");
    }
    
    public String darFormato(Date fecha){
        return fecha != null? new SimpleDateFormat("dd/MM/yyyy").format(fecha):"";
    }

    //  MENSAJE DE ERROR
    public void mostrarMensajeError(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}