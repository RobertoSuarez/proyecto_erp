/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.AmonestacionDAO;
import com.recursoshumanos.Model.DAO.DetalleRolPagoDAO;
import com.recursoshumanos.Model.DAO.EmpleadoDAO;
import com.recursoshumanos.Model.DAO.EmpleadoReservaDAO;
import com.recursoshumanos.Model.DAO.MultaDAO;
import com.recursoshumanos.Model.DAO.RolPagosDAO;
import com.recursoshumanos.Model.DAO.SueldoDAO;
import com.recursoshumanos.Model.DAO.SuspencionDAO;
import com.recursoshumanos.Model.Entidad.Amonestacion;
import com.recursoshumanos.Model.Entidad.DetalleRolPago;
import com.recursoshumanos.Model.Entidad.Empleado;
import com.recursoshumanos.Model.Entidad.EmpleadoReserva;
import com.recursoshumanos.Model.Entidad.Multa;
import com.recursoshumanos.Model.Entidad.RolPagos;
import com.recursoshumanos.Model.Entidad.Sueldo;
import com.recursoshumanos.Model.Entidad.Suspencion;
import com.recursoshumanos.Model.Entidad.TipoRubro;
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
import org.apache.commons.math3.util.Precision;
import org.primefaces.PrimeFaces;

/**
 *
 * @author kestradalp
 */
@Named(value = "rolDePagoView")
@ViewScoped
public class RolDePagoController implements Serializable{
    
    private final EmpleadoReservaDAO empleadoReservaDAO;
    private final DetalleRolPagoDAO detalleRolPagoDAO;
    private final AmonestacionDAO amonestacionDAO;
    private final SuspencionDAO suspencionDAO;
    private final RolPagosDAO rolPagosDAO;
    private final EmpleadoDAO empleadoDAO;
    private final SueldoDAO sueldoDAO;
    private final MultaDAO multaDAO;
    
    private EmpleadoReserva empleadoReserva;
    private Amonestacion amonestacion;
    private Suspencion suspencion;
    private Empleado empleado;
    private RolPagos rolPagos;
    private Sueldo sueldo;
    private Multa multa;
    
    private List<DetalleRolPago> detalles;
    private List<Empleado> empleados;
    private List<RolPagos> pagos;
    
    private float aportesIESS, decimoTercero, decimoCuarto, fondosReserva, montoHLabboradas, montoHSuplem, subTotal, total;
    private int horasLaboradas, horasSuplementarias, idEmpleado;
    private boolean checkdDecimoTercero, checkdDecimoCuarto, checkedMulta, checkedSuspencion, checkedAmonestacion;
    private Date fechaPago;
    

    public RolDePagoController() {
        empleadoReservaDAO = new EmpleadoReservaDAO();
        detalleRolPagoDAO = new DetalleRolPagoDAO();
        amonestacionDAO = new AmonestacionDAO();
        suspencionDAO = new SuspencionDAO();
        rolPagosDAO = new RolPagosDAO();
        empleadoDAO = new EmpleadoDAO();
        sueldoDAO = new SueldoDAO();
        multaDAO = new MultaDAO();
        
        empleadoReserva = new EmpleadoReserva();
        amonestacion = new Amonestacion();
        suspencion = new Suspencion();
        empleado = new Empleado();
        rolPagos = new RolPagos();
        sueldo = new Sueldo();
        multa = new Multa();
        
        empleados = new ArrayList<>();
        detalles = new ArrayList<>();
        pagos = new ArrayList<>();
    }
    
    @PostConstruct
    public void constructorRolDePago() {
        fondosReserva = 0;
        decimoTercero = 0;
        decimoCuarto = 0;
        aportesIESS = 0;
        subTotal = 0;
        total = 0;
        
        checkdDecimoTercero = false;
        checkedAmonestacion = false;
        checkdDecimoCuarto = false;
        checkedSuspencion = false;
        checkedMulta = false;
    }
    
    public void postRolDePagoLista(){
        empleados = empleadoDAO.Listar();
    }

    public void postRolDePagoCrear(Empleado empleado) {
        this.empleado = empleado;
        rolPagos.setEmpleado(this.empleado);
        sueldo = sueldoDAO.Actual(empleado);
        empleadoReserva = empleadoReservaDAO.buscar(empleado);
        fondosReserva = Precision.round(empleadoReserva.getFormaPago() * sueldo.getValor(), 2);
        suspencion = suspencionDAO.buscar(empleado);
        amonestacion = amonestacionDAO.buscar(empleado);
        multa = multaDAO.buacar(empleado);
    }
    
    public void postRolDePago(int idRolDePago, boolean nuevo) {
        this.rolPagos = rolPagosDAO.buscarPorId(idRolDePago);
        detalles = detalleRolPagoDAO.buscar(rolPagos);
        for (DetalleRolPago detalle : detalles) {
            if (detalle.getTipoRubro().getId() == 11) {
                sueldo.setEmpleado(rolPagos.getEmpleado());
                sueldo = sueldoDAO.buscarPorId(detalle.getRubro());
                break;
            }
        }
        horasLaboradas = (int) (rolPagos.getHorasLaboradas()/((sueldo.getValor()/30)/8));
        horasSuplementarias = (int) (rolPagos.getHorasSuplemetarias()/(((sueldo.getValor()/30)/8)*1.5));
        float porcentajeIESS = 0;
        for (DetalleRolPago detalle : detalles) {
            switch (detalle.getTipoRubro().getId()) {
                case 2:
                    amonestacion.setEmpleado(rolPagos.getEmpleado());
                    amonestacion = amonestacionDAO.buscarPorId(detalle.getRubro());
                    break;
                case 3:
                    porcentajeIESS = (float) 0.0945;
                    break;
                case 6:
                    decimoCuarto = Precision.round(rolPagosDAO.obtenerDecicmoCuarto(),2);
                    break;
                case 7:
                    decimoTercero = Precision.round(rolPagosDAO.obtenerDecicmoTercero(), 2);
                    break;
                case 8:
                    empleadoReserva = empleadoReservaDAO.buscar(rolPagos.getEmpleado());
                    fondosReserva = Precision.round(empleadoReserva.getFormaPago() * sueldo.getValor(), 2);
                    break;
                case 9:
                    multa.setEmpleado(rolPagos.getEmpleado());
                    multa = multaDAO.buscarPorId(detalle.getRubro());
                    break;
                case 12:
                    suspencion.setEmpleado(rolPagos.getEmpleado());
                    suspencion = suspencionDAO.buscarPorId(detalle.getRubro());
                    break;
            }
        }
        subTotal = Precision.round((fondosReserva * empleadoReserva.getTipoRubro().getCoeficiente() + rolPagos.getHorasLaboradas()
                + rolPagos.getHorasSuplemetarias() + decimoTercero + decimoCuarto), 2);
        aportesIESS = (float) Precision.round(subTotal * porcentajeIESS, 2);
        total = Precision.round((subTotal - aportesIESS + (amonestacion.getValor() * amonestacion.getTipoRubro().getCoeficiente())
                + (suspencion.getValor() * suspencion.getTipoRubro().getCoeficiente())
                + (multa.getValor() * multa.getTipoRubro().getCoeficiente())), 2);
        if (nuevo){
            mostrarMensajeInformacion("Pago Generado");
        }
        PrimeFaces.current().ajax().update("form:messages", "form:rolPago");
    }

    public boolean isCheckdDecimoTercero() {
        return checkdDecimoTercero;
    }

    public void setCheckdDecimoTercero(boolean checkdDecimoTercero) {
        this.checkdDecimoTercero = checkdDecimoTercero;
        calcularTotal();
    }

    public EmpleadoReserva getEmpleadoReserva() {
        return empleadoReserva;
    }

    public void setEmpleadoReserva(EmpleadoReserva empleadoReserva) {
        this.empleadoReserva = empleadoReserva;
    }

    public boolean isCheckedAmonestacion() {
        return checkedAmonestacion;
    }

    public void setCheckedAmonestacion(boolean checkedAmonestacion) {
        this.checkedAmonestacion = checkedAmonestacion;
        calcularTotal();
    }

    public boolean isCheckdDecimoCuarto() {
        return checkdDecimoCuarto;
    }

    public void setCheckdDecimoCuarto(boolean checkdDecimoCuarto) {
        this.checkdDecimoCuarto = checkdDecimoCuarto;
        calcularTotal();
    }

    public int getHorasSuplementarias() {
        return horasSuplementarias;
    }

    public void setHorasSuplementarias(int horasSuplementarias) {
        this.horasSuplementarias = horasSuplementarias;
    }

    public boolean isCheckedSuspencion() {
        return checkedSuspencion;
    }

    public void setCheckedSuspencion(boolean checkedSuspencion) {
        this.checkedSuspencion = checkedSuspencion;
        calcularTotal();
    }

    public float getMontoHLabboradas() {
        return montoHLabboradas;
    }

    public void setMontoHLabboradas(float montoHLabboradas) {
        this.montoHLabboradas = montoHLabboradas;
    }

    public Amonestacion getAmonestacion() {
        return amonestacion;
    }

    public void setAmonestacion(Amonestacion amonestacion) {
        this.amonestacion = amonestacion;
    }

    public List<DetalleRolPago> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleRolPago> detalles) {
        this.detalles = detalles;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public float getFondosReserva() {
        return fondosReserva;
    }

    public void setFondosReserva(float fondosReserva) {
        this.fondosReserva = fondosReserva;
    }

    public int getHorasLaboradas() {
        return horasLaboradas;
    }

    public void setHorasLaboradas(int horasLaboradas) {
        this.horasLaboradas = horasLaboradas;
    }

    public float getDecimoTercero() {
        return decimoTercero;
    }

    public void setDecimoTercero(float decimoTercero) {
        this.decimoTercero = decimoTercero;
    }

    public boolean isCheckedMulta() {
        return checkedMulta;
    }

    public void setCheckedMulta(boolean checkedMulta) {
        this.checkedMulta = checkedMulta;
        calcularTotal();
    }

    public Suspencion getSuspencion() {
        return suspencion;
    }

    public void setSuspencion(Suspencion suspencion) {
        this.suspencion = suspencion;
    }

    public float getDecimoCuarto() {
        return decimoCuarto;
    }

    public void setDecimoCuarto(float decimoCuarto) {
        this.decimoCuarto = decimoCuarto;
    }

    public float getMontoHSuplem() {
        return montoHSuplem;
    }

    public void setMontoHSuplem(float montoHSuplem) {
        this.montoHSuplem = montoHSuplem;
    }

    public float getAportesIESS() {
        return aportesIESS;
    }

    public void setAportesIESS(float aportesIESS) {
        this.aportesIESS = aportesIESS;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<RolPagos> getPagos() {
        return pagos;
    }

    public void setPagos(List<RolPagos> pagos) {
        this.pagos = pagos;
    }

    public RolPagos getRolPagos() {
        return rolPagos;
    }

    public void setRolPagos(RolPagos rolPagos) {
        this.rolPagos = rolPagos;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
        if(idEmpleado != 0){
            empleado = empleadoDAO.buscarPorId(idEmpleado);
            pagos = rolPagosDAO.buscar(empleado);
            PrimeFaces.current().ajax().update("form:messages", "form:dt-roles");
        }
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public Sueldo getSueldo() {
        return sueldo;
    }

    public void setSueldo(Sueldo sueldo) {
        this.sueldo = sueldo;
    }
    
    public String darFormato(Date fecha){
        return fecha != null? new SimpleDateFormat("dd/MM/yyyy").format(fecha) : "";
    }

    public Multa getMulta() {
        return multa;
    }

    public void setMulta(Multa multa) {
        this.multa = multa;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void obtenerDatos() {
        rolPagos.setFechaGenerado(fechaPago);
        rolPagosDAO.setRolPagos(rolPagos);
        horasLaboradas = rolPagosDAO.obtenerHorasLaboradas();
        horasSuplementarias = rolPagosDAO.obtenerHorasSuplementarias();
        montoHLabboradas = Precision.round(horasLaboradas * ((sueldo.getValor() / 30) / 8), 2);
        montoHSuplem = (float) Precision.round((horasSuplementarias * ((sueldo.getValor() / 30) / 8) * 1.5), 2);
        decimoTercero = Precision.round(rolPagosDAO.obtenerDecicmoTercero(), 2);
        decimoCuarto = Precision.round(rolPagosDAO.obtenerDecicmoCuarto(), 2);
        calcularTotal();
    }

    public void calcularTotal() {
        subTotal = Precision.round((fondosReserva * empleadoReserva.getTipoRubro().getCoeficiente() + montoHLabboradas + montoHSuplem + (checkdDecimoTercero ? decimoTercero : 0)
                + (checkdDecimoCuarto ? decimoCuarto : 0)), 2);
        aportesIESS = (float) Precision.round(subTotal * 0.0945, 2);
        total = Precision.round((subTotal - aportesIESS + (checkedAmonestacion ? (amonestacion.getValor() * amonestacion.getTipoRubro().getCoeficiente()) : 0)
                + (checkedSuspencion ? (suspencion.getValor() * suspencion.getTipoRubro().getCoeficiente()) : 0)
                + (checkedMulta ? (multa.getValor() * multa.getTipoRubro().getCoeficiente()) : 0)), 2);
        PrimeFaces.current().ajax().update("form:messages", "form:DATOS");
    }

    public String guardar() {
        String mensaje = "";
        boolean result = false;
        rolPagos.setEstado("GENERADO");
        rolPagos.setCodigo(java.util.UUID.randomUUID().toString());
        rolPagos.setValor(total);
        rolPagos.setHorasLaboradas(montoHLabboradas);
        rolPagos.setHorasSuplemetarias(montoHSuplem);
        rolPagosDAO.setRolPagos(rolPagos);
        if (rolPagosDAO.insertar() > 0) {
            rolPagos.setId(rolPagosDAO.getRolPagos().getId());
            detalleRolPagoDAO.setDetalleRolPago(new DetalleRolPago(rolPagos, sueldo.getTipoRubro(), sueldo.getId()));
            if (detalleRolPagoDAO.insertar() > 0) {
                detalleRolPagoDAO.setDetalleRolPago(new DetalleRolPago(rolPagos, empleadoReserva.getTipoRubro(), empleadoReserva.getEmpleado().getId()));
                if (detalleRolPagoDAO.insertar() > 0) {
                    detalleRolPagoDAO.setDetalleRolPago(new DetalleRolPago(rolPagos, new TipoRubro(3, 1, ""), 1));
                    if (detalleRolPagoDAO.insertar() > 0) {
                        if (checkdDecimoTercero) {
                            detalleRolPagoDAO.setDetalleRolPago(new DetalleRolPago(rolPagos, new TipoRubro(7, 1, ""), 1));
                            result = true;
                            if (detalleRolPagoDAO.insertar() < 1) {
                                mensaje = "El detalle no se pudo asignar";
                                result = false;
                            }
                        }
                        if (checkdDecimoCuarto && result) {
                            detalleRolPagoDAO.setDetalleRolPago(new DetalleRolPago(rolPagos, new TipoRubro(6, 1, ""), 1));
                            result = true;
                            if (detalleRolPagoDAO.insertar() < 1) {
                                mensaje = "El detalle no se pudo asignar";
                                result = false;
                            }
                        }
                        if (checkedAmonestacion && result) {
                            detalleRolPagoDAO.setDetalleRolPago(new DetalleRolPago(rolPagos, amonestacion.getTipoRubro(), amonestacion.getId()));
                            result = true;
                            if (detalleRolPagoDAO.insertar() < 1) {
                                mensaje = "El detalle no se pudo asignar";
                                result = false;
                            }
                        }
                        if (checkedMulta && result) {
                            detalleRolPagoDAO.setDetalleRolPago(new DetalleRolPago(rolPagos, multa.getTipoRubro(), multa.getId()));
                            result = true;
                            if (detalleRolPagoDAO.insertar() < 1) {
                                mensaje = "El detalle no se pudo asignar";
                                result = false;
                            }
                        }
                        if (checkedSuspencion && result) {
                            detalleRolPagoDAO.setDetalleRolPago(new DetalleRolPago(rolPagos, suspencion.getTipoRubro(), suspencion.getId()));
                            result = true;
                            if (detalleRolPagoDAO.insertar() < 1) {
                                mensaje = "El detalle no se pudo asignar";
                                result = false;
                            }
                        }
                    } else {
                        mensaje = "La aportación al IESS no se pudo asignar al detalle";
                    }
                } else {
                    mensaje = "El fondo de reserva no se pudo asignar al detalle";
                }
            } else {
                mensaje = "El sueldo no se pudo guardar al detalle";
            }
        } else {
            mensaje = "El rol de pagos no se pudo guardar";
        }
        if (result) {
            mostrarMensajeInformacion("Datos guardados correctamente");
        } else {
            mostrarMensajeError(mensaje);
        }
        PrimeFaces.current().ajax().update("form:messages", "form:DATOS");
        return result ? "RolDePago" : "";
    }

    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void mostrarMensajeError(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
