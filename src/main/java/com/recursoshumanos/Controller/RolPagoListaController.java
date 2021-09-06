/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Controller;

import com.recursoshumanos.Model.DAO.EmpleadoDAO;
import com.recursoshumanos.Model.DAO.RolPagosDAO;
import com.recursoshumanos.Model.Entidad.Empleado;
import com.recursoshumanos.Model.Entidad.RolPagos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author ClasK7
 */
@Named(value = "rolPagoListaView")
@ViewScoped
public class RolPagoListaController implements Serializable {
    
    private final RolPagosDAO rolPagosDAO;
    private final EmpleadoDAO empleadoDAO;
    private List<Empleado> empleados;
    private List<RolPagos> pagos;
    private Empleado empleado;
    private int idEmpleado;
        
    public RolPagoListaController() {
        rolPagosDAO = new RolPagosDAO();
        empleadoDAO = new EmpleadoDAO();
        empleado = new Empleado();
        empleados = new ArrayList<>();
        pagos = new ArrayList<>();
    }

    @PostConstruct
    public void constructorRolPagoLista() {
        empleados = empleadoDAO.Listar();
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public List<RolPagos> getPagos() {
        return pagos;
    }

    public void setPagos(List<RolPagos> pagos) {
        this.pagos = pagos;
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
    
    public String darFormato(Date fecha){
        return fecha != null? new SimpleDateFormat("dd/MM/yyyy").format(fecha) : "";
    }
}