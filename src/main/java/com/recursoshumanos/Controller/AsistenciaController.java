/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.AsistenciaDAO;
import com.recursoshumanos.Model.DAO.DetalleHorarioDAO;
import com.recursoshumanos.Model.DAO.EmpleadoDAO;
import com.recursoshumanos.Model.DAO.EmpleadoPuestoDAO;
import com.recursoshumanos.Model.Entidad.Asistencia;
import com.recursoshumanos.Model.Entidad.DetalleHorario;
import com.recursoshumanos.Model.Entidad.Empleado;
import com.seguridad.models.Roles;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
@Named(value = "asistenciaView")
@ViewScoped
public class AsistenciaController implements Serializable {

    /**
     * Se declaran las variables del modelo Controlador de
     * la parte de Asistencia.
     */
    private final EmpleadoPuestoDAO empleadoPuestoDAO;
    private final DetalleHorarioDAO detalleHorarioDAO;
    private final AsistenciaDAO asistenciaDAO;
    private final EmpleadoDAO empleadoDAO;
    private Asistencia asistencia;
    private List<DetalleHorario> horarios;
    private List<Asistencia> asistencias;
    private List<Empleado> empleados;
    private Date minTimeI, maxTimeI, minTimeS, maxTimeS, minT;
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    List<Roles> listaRoles = (List<Roles>) context.getExternalContext().getSessionMap().get("usuario_rol");

    /**
     * Se crea las nuevas variables para asignarlos
     */
    public AsistenciaController() {
        empleadoPuestoDAO = new EmpleadoPuestoDAO();
        detalleHorarioDAO = new DetalleHorarioDAO();
        asistenciaDAO = new AsistenciaDAO();
        empleadoDAO = new EmpleadoDAO();
        asistencias = new ArrayList<>();
        asistencia = new Asistencia();
        empleados = new ArrayList<>();
        horarios = new ArrayList<>();
        
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
    public void constructorAsistencia() {
        /**
         * Aqui se construye el constructor de la Asistencia,
         * donde se obtienen los datos para el calendario.
         * permitiendo registrar "Horas, Minutos, Segundos
         * y Milesegundos, todo esto siempre y cuando el empleado
         * de la clase DAO se encuentre validado
         * desde el método ACTIVOS.
         */
        Calendar tmp = Calendar.getInstance();
        tmp.set(Calendar.HOUR_OF_DAY, 0);
        tmp.set(Calendar.MINUTE, 0);
        tmp.set(Calendar.SECOND, 0);
        tmp.set(Calendar.MILLISECOND, 0);
        minT = tmp.getTime();
        empleados = empleadoDAO.activos();
    }

    /**
     * Método GET de Asistencias
     * @return asistencias Objeto que retorna las asistencias
     */
    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    /**
     * Método SET de las Asistencias
     * @param asistencias Objeto que recibe el dato de la
     * variable asistencia.
     */
    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    /**
     * Método GET del Detalle de Horarios
     * @return horarios Objeto que retorna los datos.
     */
    public List<DetalleHorario> getHorarios() {
        return horarios;
    }

    /**
     * Método SET del Detalle de Horarios
     * @param horarios Objeto que recibe el dato del 
     * detalle del horario
     */
    public void setHorarios(List<DetalleHorario> horarios) {
        this.horarios = horarios;
    }

    /**
     * A continuación continuan los métodos de GET y SET
     * de cada una de las variables declaradas al inicio de
     * la clase.
     * @return empleados Los GET tienen un return que nos retornan
     * los datos y los SET una variable que recibe el dato.
     */
    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public Asistencia getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
    }

    public Date getMinTimeI() {
        return minTimeI;
    }

    public void setMinTimeI(Date minTimeI) {
        this.minTimeI = minTimeI;
    }

    public Date getMaxTimeI() {
        return maxTimeI;
    }

    public void setMaxTimeI(Date maxTimeI) {
        this.maxTimeI = maxTimeI;
    }

    public Date getMinTimeS() {
        return minTimeS;
    }

    public void setMinTimeS(Date minTimeS) {
        this.minTimeS = minTimeS;
    }

    public Date getMaxTimeS() {
        return maxTimeS;
    }

    public void setMaxTimeS(Date maxTimeS) {
        this.maxTimeS = maxTimeS;
    }

    public Date getMinT() {
        return minT;
    }

    public void setMinT(Date minT) {
        this.minT = minT;
    }

    /**
     * Evento al seleccionar el empleado en la interfaz
     */
    public void empleadoSeleccionado() {
        asistencia.setEmpleadoPuesto(empleadoPuestoDAO.buscar(asistencia.getEmpleadoPuesto().getEmpleado()));
        horarios = detalleHorarioDAO.buscar(asistencia.getEmpleadoPuesto());
        cargarDatos();
    }
    
    /**
     * Evento de actualizar asistencias
     */
    private void actualizarAsistencias(){
        asistencias = asistenciaDAO.buscar(asistencia.getEmpleadoPuesto(), asistencia.getFecha());
        PrimeFaces.current().ajax().update("form:messages", "form:dt-asistencia form:dt-asistencias");
    }
    
    /**
     * Evento que maneja el minimo y máximo del Calendario
     * haciendo uso de un Switch
     */
    public void minmax(){
        Calendar tmp = Calendar.getInstance();
        String[] hoarioStr;
        switch(horarios.size()){
            case 1:
                tmp.set(Calendar.HOUR_OF_DAY, 0);
                tmp.set(Calendar.MINUTE, 0);
                tmp.set(Calendar.SECOND, 1);
                tmp.set(Calendar.MILLISECOND, 0);
                minTimeI = tmp.getTime();
                tmp = Calendar.getInstance();
                hoarioStr = horarios.get(0).getIngresoSalida().getHoraSalida().split(":");
                tmp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hoarioStr[0]));
                tmp.set(Calendar.MINUTE, Integer.parseInt(hoarioStr[1]));
                tmp.set(Calendar.SECOND, Integer.parseInt(hoarioStr[2]));
                tmp.set(Calendar.MILLISECOND, 0);
                maxTimeI = tmp.getTime();
                tmp = Calendar.getInstance();
                hoarioStr = horarios.get(0).getIngresoSalida().getHoraIngreso().split(":");
                tmp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hoarioStr[0]));
                tmp.set(Calendar.MINUTE, Integer.parseInt(hoarioStr[1]));
                tmp.set(Calendar.SECOND, Integer.parseInt(hoarioStr[2]));
                tmp.set(Calendar.MILLISECOND, 0);
                minTimeS = tmp.getTime();
                tmp = Calendar.getInstance();
                tmp.set(Calendar.HOUR_OF_DAY, 23);
                tmp.set(Calendar.MINUTE, 59);
                tmp.set(Calendar.SECOND, 59);
                tmp.set(Calendar.MILLISECOND, 999);
                maxTimeS =  tmp.getTime();
                break;
            case 2:
                int hora1 = Integer.parseInt(horarios.get(0).getIngresoSalida().getHoraIngreso().split(":")[0]);
                int hora2 = Integer.parseInt(horarios.get(1).getIngresoSalida().getHoraIngreso().split(":")[0]);
                if(hora1<hora2){
                    hora1 = 0;
                    hora2 = 1;
                }else{
                    hora1 = 1;
                    hora2 = 0;
                }
                if(asistencia.getDetalleHorario().getId() == horarios.get(hora1).getId()){
                    tmp.set(Calendar.HOUR_OF_DAY, 0);
                    tmp.set(Calendar.MINUTE, 0);
                    tmp.set(Calendar.SECOND, 1);
                    tmp.set(Calendar.MILLISECOND, 0);
                    minTimeI = tmp.getTime();
                    tmp = Calendar.getInstance();
                    hoarioStr = horarios.get(hora1).getIngresoSalida().getHoraSalida().split(":");
                    tmp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hoarioStr[0]));
                    tmp.set(Calendar.MINUTE, Integer.parseInt(hoarioStr[1]));
                    tmp.set(Calendar.SECOND, Integer.parseInt(hoarioStr[2]));
                    tmp.set(Calendar.MILLISECOND, 0);
                    maxTimeI = tmp.getTime();
                    tmp = Calendar.getInstance();
                    hoarioStr = horarios.get(hora1).getIngresoSalida().getHoraIngreso().split(":");
                    tmp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hoarioStr[0]));
                    tmp.set(Calendar.MINUTE, Integer.parseInt(hoarioStr[1]));
                    tmp.set(Calendar.SECOND, Integer.parseInt(hoarioStr[2]));
                    tmp.set(Calendar.MILLISECOND, 0);
                    minTimeS = tmp.getTime();
                    tmp = Calendar.getInstance();
                    hoarioStr = horarios.get(hora2).getIngresoSalida().getHoraIngreso().split(":");
                    tmp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hoarioStr[0]));
                    tmp.set(Calendar.MINUTE, Integer.parseInt(hoarioStr[1]));
                    tmp.set(Calendar.SECOND, Integer.parseInt(hoarioStr[2]));
                    tmp.set(Calendar.MILLISECOND, 0);
                    maxTimeS = tmp.getTime();
                }else{
                    if(asistencia.getDetalleHorario().getId() == horarios.get(hora2).getId()){
                        hoarioStr = horarios.get(hora1).getIngresoSalida().getHoraSalida().split(":");
                        tmp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hoarioStr[0]));
                        tmp.set(Calendar.MINUTE, Integer.parseInt(hoarioStr[1]));
                        tmp.set(Calendar.SECOND, Integer.parseInt(hoarioStr[2]));
                        tmp.set(Calendar.MILLISECOND, 0);
                        minTimeI = tmp.getTime();
                        tmp = Calendar.getInstance();
                        hoarioStr = horarios.get(hora2).getIngresoSalida().getHoraSalida().split(":");
                        tmp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hoarioStr[0]));
                        tmp.set(Calendar.MINUTE, Integer.parseInt(hoarioStr[1]));
                        tmp.set(Calendar.SECOND, Integer.parseInt(hoarioStr[2]));
                        tmp.set(Calendar.MILLISECOND, 0);
                        maxTimeI = tmp.getTime();
                        tmp = Calendar.getInstance();
                        hoarioStr = horarios.get(hora2).getIngresoSalida().getHoraIngreso().split(":");
                        tmp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hoarioStr[0]));
                        tmp.set(Calendar.MINUTE, Integer.parseInt(hoarioStr[1]));
                        tmp.set(Calendar.SECOND, Integer.parseInt(hoarioStr[2]));
                        tmp.set(Calendar.MILLISECOND, 0);
                        minTimeS = tmp.getTime();
                        tmp = Calendar.getInstance();
                        tmp.set(Calendar.HOUR_OF_DAY, 23);
                        tmp.set(Calendar.MINUTE, 59);
                        tmp.set(Calendar.SECOND, 59);
                        tmp.set(Calendar.MILLISECOND, 999);
                        maxTimeS =  tmp.getTime();
                    }
                }
                break;
        }
    }
    
    /**
     * Evento de cargar datos de la asistencia o en si actualizarlos
     */
    public void cargarDatos(){
        asistencia = asistenciaDAO.buscar(asistencia.getEmpleadoPuesto(), asistencia.getFecha(), asistencia.getDetalleHorario());
        try{
            minmax();
        }catch(Exception ex){
            mostrarMensajeError(ex.getMessage());
        }
        actualizarAsistencias();
    }

    /**
     * Evento que se ejecuta al presioanr el botón de Guardar en la
     * interfaz ejecutando la siguiente sentencia y actualizando
     * la asistencia al final.
     */
    public void guardar(){
        
        asistenciaDAO.setAsistencia(asistencia);
        if (asistencia.getIngreso() != null && asistencia.getSalida() == null) {
            if (asistenciaDAO.insertar() > 0) {
                mostrarMensajeInformacion("Se marco la hora de ingreso");
            } else {
                mostrarMensajeError("No se pudo marcar la hora de ingreso");
            }
        } else {
            if (asistencia.getIngreso() != null && asistencia.getSalida() != null) {
                if (asistenciaDAO.actualizar()> 0) {
                    mostrarMensajeInformacion("Se marco la hora de salida");
                } else {
                    mostrarMensajeError("No se pudo marcar la hora de salida");
                }
            }else{
                mostrarMensajeError("Debe de ingresar un valor en los campos");
            }
        }
        actualizarAsistencias();
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
