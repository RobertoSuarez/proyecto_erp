/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.DetalleHorarioDAO;
import com.recursoshumanos.Model.DAO.DiaSemanaDAO;
import com.recursoshumanos.Model.DAO.HorarioLaboralDAO;
import com.recursoshumanos.Model.DAO.IngresosSalidasDAO;
import com.recursoshumanos.Model.Entidad.DetalleHorario;
import com.recursoshumanos.Model.Entidad.DiaSemana;
import com.recursoshumanos.Model.Entidad.HorarioLaboral;
import com.recursoshumanos.Model.Entidad.IngresosSalidas;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 *
 * Clase tipo CONTROLER que se encargará de proporcionar ciertas funcionalidades para
 * todo lo que tenga que ver con un horario laboral. Y se encarga de administrar
 * las sentencias de la BD, utilizando las clases de los modelos
 */
@Named(value = "horarioLaboralView")
@ViewScoped
public class HorarioLaboralController implements Serializable {

    /**
     * Se declaran las variables que usará el Controlador de horario laboral.
     */
    private final IngresosSalidasDAO ingresosSalidasDAO;
    private final HorarioLaboralDAO horarioLaboralDAO;
    private final DetalleHorarioDAO detalleHorarioDAO;
    private final DiaSemanaDAO diaSemanaDAO;
    private final String INICIO = "HorarioLaboral";

    private static HttpSession httpSession;
    
    private HorarioLaboral horarioLaboral;
    private DetalleHorario detalleHorario;
    private List<HorarioLaboral> lista;
    private List<DetalleHorario> horarios;
    private List<DiaSemana> dias;
    private List<IngresosSalidas> horas;
    private int idDia, idIngresoSalida;

    /**
     * Se crea las nuevas variables para asignarlos
     */
    public HorarioLaboralController() {
        ingresosSalidasDAO = new IngresosSalidasDAO();
        horarioLaboralDAO = new HorarioLaboralDAO();
        detalleHorarioDAO = new DetalleHorarioDAO();
        diaSemanaDAO = new DiaSemanaDAO();
        horarioLaboral = new HorarioLaboral();
        httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        dias = new ArrayList<>();
        horas = new ArrayList<>();
        lista = new ArrayList<>();
        horarios = new ArrayList<>();
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
    public void constructorHorarioLaboral() {
        lista = horarioLaboralDAO.Listar();
    }

    /**
     * A continuación continuan los métodos de GET y SET de cada una de las
     * variables declaradas al inicio de la clase.
     *
     * @return lista Los GET tienen un return que nos retornan los datos y los
     * SET una variable que recibe el dato.
     */
    public HorarioLaboral getHorarioLaboral() {
        return horarioLaboral;
    }

    public void setHorarioLaboral(HorarioLaboral horarioLaboral) {
        this.horarioLaboral = horarioLaboral;
    }

    public List<HorarioLaboral> getLista() {
        return lista;
    }

    public void setLista(List<HorarioLaboral> lista) {
        this.lista = lista;
    }

    public DetalleHorario getDetalleHorario() {
        return detalleHorario;
    }

    public void setDetalleHorario(DetalleHorario detalleHorario) {
        this.detalleHorario = detalleHorario;
    }

    public List<DetalleHorario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<DetalleHorario> horarios) {
        this.horarios = horarios;
    }

    public int getIdDia() {
        return idDia;
    }

    public void setIdDia(int idDia) {
        this.idDia = idDia;
    }

    public int getIdIngresoSalida() {
        return idIngresoSalida;
    }

    public void setIdIngresoSalida(int idIngresoSalida) {
        this.idIngresoSalida = idIngresoSalida;
    }

    public List<DiaSemana> getDias() {
        return dias;
    }

    public void setDias(List<DiaSemana> dias) {
        this.dias = dias;
    }

    public List<IngresosSalidas> getHoras() {
        return horas;
    }

    public void setHoras(List<IngresosSalidas> horas) {
        this.horas = horas;
    }

    /**
     * postLoadDetalle() Carga el detalle del horario antes de que se renderice
     * la interfaz del horario laboral
     */
    public void postLoadDetalle() {
        horarioLaboral = (HorarioLaboral) httpSession.getAttribute("horarioLaboral");
        horarios = detalleHorarioDAO.Listar(horarioLaboral.getId());
        dias = diaSemanaDAO.Listar();
        horas = ingresosSalidasDAO.Listar();
        if (horarios.isEmpty()) {
            mostrarMensajePrecaucion("Debe definir los dias y horas de este horario");
        }
    }

    /**
     * darFormato() Coloca un formato a un fecha
     *
     * @param fecha Objeto tipo fecha
     */
    public String darFormato(Date fecha) {
        return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
    }

    /**
     * nuevoHorario() Instancia un nuevo horario
     */
    public void nuevoHorario() {
        horarioLaboral = new HorarioLaboral();
    }

    /**
     * nuevoDetalle() le prepata todo para el nuevo detalle de un horario
     * laboral
     */
    public void nuevoDetalle() {
        idDia = 0;
        idIngresoSalida = 0;
        detalleHorario = new DetalleHorario();
        detalleHorario.setHorarioLaboral(horarioLaboral);
    }

    /**
     * nuevoDetalle() limpia los datos y regresa a inicio
     */
    public String volver() {
        horarios.clear();
        dias.clear();
        horas.clear();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-puestoLaborals");
        return INICIO;
    }

    /**
     * editarDetalle() edita el detalle de un horario laboral
     */
    public void editarDetalle(int idDia, int idIngresoSalida) {
        this.idDia = idDia;
        this.idIngresoSalida = idIngresoSalida;
    }
    
    public void verDetalle(){
        httpSession.setAttribute("horarioLaboral", horarioLaboral);
    }

    /**
     * guardarHorario() Guarda los datos del horario laboral Define si se trata
     * de una edición o de una inserción y actúan en consecuncia
     */
    public void guardarHorario() {
        horarioLaboralDAO.setHorarioLaboral(horarioLaboral);
        if (horarioLaboral.getId() == 0) {
            if (horarioLaboralDAO.insertar() > 0) {
                horarioLaboral.setId(horarioLaboralDAO.getHorarioLaboral().getId());
                lista.add(horarioLaboral);
            } else {
                mostrarMensajeError("El horario laboral no se pudo guardar");
            }
        } else {
            if (horarioLaboralDAO.actualizar() > 0) {
                mostrarMensajeInformacion("El horario laboral se ha editado con éxito");
            } else {
                mostrarMensajeError("El horario laboral no se pudo editar");
            }
        }
        PrimeFaces.current().executeScript("PF('managePuestoLaboralDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-puestoLaborals");
    }

    /**
     * enviar() Guarda el detalle de una horario laboral de una edición o de una
     * inserción y actúan en consecuncia
     */
    public void enviar() {
        if (idDia != 0 && idIngresoSalida != 0) {
            detalleHorario.getDiaSemana().setId(idDia);
            detalleHorario.getIngresoSalida().setId(idIngresoSalida);
            detalleHorario.setHorarioLaboral(horarioLaboral);
            detalleHorarioDAO.setDetalleHorario(detalleHorario);
            if (detalleHorario.getId() == 0) {
                if (detalleHorarioDAO.insertar() > 0) {
                    mostrarMensajeInformacion("El horario se ha guardado con éxito");
                    horarios.add(detalleHorario);
                } else {
                    mostrarMensajeError("El horario no se pudo guardar");
                }
            } else {
                if (detalleHorarioDAO.actualizar() > 0) {
                    mostrarMensajeInformacion("El horario se ha editado con éxito");
                } else {
                    mostrarMensajeError("El horario no se pudo editar");
                }
            }
        } else {
            mostrarMensajeError("Debe de seleccionar un dia y un horaria");
        }
        PrimeFaces.current().executeScript("PF('manageDetalleHorarioDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-detalleHorarios");
    }

    /**
     * cambiarEstado () cambia el estado de un horario laboral
     *
     * @param horarioLaboral objeto de tipo HorarioLaboral al que se le desea
     * hacer el cambio de estado
     */
    public void cambiarEstado(HorarioLaboral horarioLaboral) {
        horarioLaboralDAO.setHorarioLaboral(horarioLaboral);
        horarioLaboralDAO.cambiarEstado();
        if (detalleHorarioDAO.Listar(horarioLaboral.getId()).isEmpty() && !horarioLaboral.isEstado()) {
            mostrarMensajePrecaucion("Debe definir los dias y horas del horario " + horarioLaboral.getNombre());
        }
        PrimeFaces.current().ajax().update("form:messages", "form:dt-puestoLaborals");
    }

    /**
     * cambiarEstadoDetalle () cambia el estado del detalle de un horaio laboral
     *
     * @param detalleHorario objeto de tipo DetalleHorario al que se le desea
     * hacer el cambio de estado
     */
    public void cambiarEstadoDetalle(DetalleHorario detalleHorario) {
        detalleHorarioDAO.setDetalleHorario(detalleHorario);
        detalleHorarioDAO.cambiarEstado();
        PrimeFaces.current().ajax().update("form:messages", "form:dt-detalleHorarios");
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

    public void mostrarMensajePrecaucion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención!", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
