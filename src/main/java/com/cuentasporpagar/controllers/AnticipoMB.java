package com.cuentasporpagar.controllers;

import com.cuentasporpagar.daos.AnticipoDAO;
import com.cuentasporpagar.daos.BuscarProvDAO;
import com.cuentasporpagar.models.Anticipo;
import com.cuentasporpagar.models.Proveedor;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author elect
 * pagina que va a interacturar con ajax
 */

@ManagedBean(name = "anticipoMB")
@SessionScoped
public class AnticipoMB  {

    private List<Anticipo> anticipos;
    private Anticipo selected_anticipo;
    private List<Proveedor> list_proveedor; // se mostrar en el dialogo para selecionar el proveedor
    private Proveedor selected_Proveedor;
    
    public AnticipoMB() {
        //this.anticipos = Anticipo.getAll(); // trae solo los datos de los anticipos
        //Anticipo.GetAllDBProveedor(this.anticipos); // trae los proveedores de cada anticipo.
        
        //this.selected_anticipo = new Anticipo();
        //this.selected_anticipo.setFecha(new Date());
        //this.selected_anticipo.setDescripcion("");
        //this.selected_anticipo.setImporte(0.0);
        
        
    }
    
    @PostConstruct
    public void init() {
        try {
            this.anticipos = AnticipoDAO.getAllJson();
        } catch (SQLException ex) {
            Logger.getLogger(AnticipoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        BuscarProvDAO BP = new BuscarProvDAO();
        this.list_proveedor = BP.llenar();
    }

    public List<Anticipo> getAnticipos() {
        return anticipos;
    }

    public void setAnticipos(List<Anticipo> anticipos) {
        this.anticipos = anticipos;
    }

    public Anticipo getSelected_anticipo() {
        return selected_anticipo;
    }

    public void setSelected_anticipo(Anticipo selected_anticipo) {
        this.selected_anticipo = selected_anticipo;
    }

    public List<Proveedor> getList_proveedor() {
        return list_proveedor;
    }

    public void setList_proveedor(List<Proveedor> list_proveedor) {
        this.list_proveedor = list_proveedor;
    }

    public Proveedor getSelected_Proveedor() {
        return selected_Proveedor;
    }

    public void setSelected_Proveedor(Proveedor selected_Proveedor) {
        this.selected_Proveedor = selected_Proveedor;
    }

    
    
    // metodos aux
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    public void open_new() {
        this.selected_anticipo = new Anticipo();
    }
    
    public void guardar_anticipo() {
        System.out.println("guardar");
        try {
            System.out.println(this.selected_anticipo.getId_anticipo());
            System.out.println(this.selected_anticipo.getDescripcion());
            
            if (this.selected_anticipo.getId_anticipo().isEmpty()) {
                this.selected_anticipo.InsertDB();
                addMessage(FacesMessage.SEVERITY_INFO, "Anticipo creado", "El anticipo fue creado correctamente");
            } else {
                this.selected_anticipo.UpdateDB();
                System.out.println("update registro: " + this.selected_anticipo.getId_anticipo());
                addMessage(FacesMessage.SEVERITY_INFO, "Anticipo actualizado", "El anticipo fue actualizado correctamente");
            }
            
            
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error al guardar el anticipo");
        }
        
        
        try {
            this.anticipos = AnticipoDAO.getAllJson();  // Actualiza los datos de la tabla
        } catch (SQLException ex) {
            Logger.getLogger(AnticipoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PrimeFaces.current().executeScript("PF('manageAnticipoDialog').hide()");
        PrimeFaces.current().ajax().update(":form:dt_anticipos");
        PrimeFaces.current().ajax().update(":form:growl");
        //PrimeFaces.current().executeScript("location.reload()");
    }
    
    public void delete() {
        System.out.println("Anticipo delete");
        System.out.println(this.selected_anticipo.getId_anticipo());
        System.out.println(this.selected_anticipo.getDescripcion());
        System.out.println("Anticipo fin");
        
        
        try {
            this.selected_anticipo.deleteDB();
        } 
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        try {
            this.anticipos = AnticipoDAO.getAllJson();  // Actualiza los datos de la tabla
        } catch (SQLException ex) {
            Logger.getLogger(AnticipoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrimeFaces.current().ajax().update(":form:dt_anticipos");
        
        PrimeFaces.current().executeScript("PF('delete_anticipo_dialog').hide()");
        addMessage(FacesMessage.SEVERITY_WARN, "Anticipo eliminado", "El anticipo se elimino");
        
        PrimeFaces.current().ajax().update(":form:growl");
    }
    
    // FormatoFecha da el formato a la fecha que se presentara en la tabla.
    public static String FormatoFecha(Date fecha) {
        return new SimpleDateFormat("dd-MM-yyyy").format(fecha);
    }
    
    public void onRowSelect(SelectEvent<Proveedor> event) {
        try {
            
            this.selected_anticipo.setProveedor(event.getObject());
            this.selected_anticipo.setId_proveedor(event.getObject().getIdProveedor());
            
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        

        //setVence(msg4);
    }
    
}
