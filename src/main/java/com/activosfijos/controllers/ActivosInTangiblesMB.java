/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.controllers;

import com.activosfijos.dao.IntangibleDAO;
import com.activosfijos.model.ActivoIntangible;
import com.activosfijos.model.ActivosFijos;
import com.activosfijos.model.ListarIntangible;
import com.cuentasporpagar.models.Proveedor;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author desta
 */
@ManagedBean(name = "activosintangilesMB")
@ViewScoped
public class ActivosInTangiblesMB implements Serializable {

    IntangibleDAO intangibledao = new IntangibleDAO();
    ListarIntangible listaintangible = new ListarIntangible();
    ActivoIntangible activoingantible = new ActivoIntangible();
    ActivosFijos activosFijos = new ActivosFijos();
    int idactivofijo;
    int id_proveedor;
    String nombre = "";
    String warnMsj = "Advertencia";
    String infMsj = "Exito";

    public String getNombre() {
        return nombre;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ActivosInTangiblesMB() {
    }

    public ActivosFijos getActivosFijos() {
        return activosFijos;
    }

    public void setActivosFijos(ActivosFijos activosFijos) {
        this.activosFijos = activosFijos;
    }

    public ActivoIntangible getActivoingantible() {
        return activoingantible;
    }

    public void setActivoingantible(ActivoIntangible activoingantible) {
        this.activoingantible = activoingantible;
    }

    public IntangibleDAO getIntangibledao() {
        return intangibledao;
    }

    public void setIntangibledao(IntangibleDAO intangibledao) {
        this.intangibledao = intangibledao;
    }

    public ListarIntangible getListaintangible() {
        return listaintangible;
    }

    public void setListaintangible(ListarIntangible listaintangible) {
        this.listaintangible = listaintangible;
    }

    public void setRegistrar3() {
        String data = "";
        IntangibleDAO intangibledao = new IntangibleDAO();
        try {
            intangibledao.guardar3(activosFijos, activoingantible);
            System.out.println("Registrado correctamente");
            PrimeFaces.current().executeScript("PF('NuevoIntangible').hide()");
            PrimeFaces.current().ajax().update("formintangible:verListaIntangibles");
            PFE("Activo intangible agregado correctamente");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

    public void obtenerdatosIntangibles(ListarIntangible listaIntan) {
        this.listaintangible = listaIntan;
        idactivofijo = listaIntan.getId_activo_fijo();
        System.out.println("Id obtenido de activo: " + listaIntan.getId_activo_fijo());
        System.out.println("Id obtenido de empresa: " + listaIntan.getId_empresa());
        System.out.println("Id obtenido de idproveedor: " + listaIntan.getIdproveedor());

    }

    public void setEditar3() {
        String data = "";
        try {
            activoingantible.setId_activo_fijo(idactivofijo);
            intangibledao.editar2(listaintangible);
            System.out.println("activos_fijos/Actualizado correctamente");
            PrimeFaces.current().executeScript("PF('EditarIntangible').hide()");
            PrimeFaces.current().ajax().update("formintangible:verListaIntangibles");
            PFE("Activo intangible actualizado");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

    public void setDeshabilitarintangible() {
        String data = "";
        IntangibleDAO intangibledao = new IntangibleDAO();
        try {
            activoingantible.setId_activo_fijo(idactivofijo);
            intangibledao.deshabilitarintangible(listaintangible);
            System.out.println("activos_fijos/Actualizado correctamente");
            PrimeFaces.current().executeScript("PF('EditarIntangible').hide()");
            PrimeFaces.current().ajax().update(":formintangible");
            PFE("Activo intangible deshabilitado");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);
    }

    public void setHabilitarintangible(int id) {
        String data = "";
        try {
            activoingantible.setId_activo_fijo(id);
            intangibledao.habilitarintangible(activoingantible);
            System.out.println("activos_fijos/Actualizado correctamente");
            PrimeFaces.current().executeScript("PF('deshabilitadosintangible').hide()");
            PrimeFaces.current().ajax().update(":formintangible");
            PFE("Activo intangible habilitado");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

    public void onRowSelect(SelectEvent<Proveedor> event) {
        try {

            this.activosFijos.setProveedor(event.getObject().getNombre());
            this.activosFijos.setIdproveedor(event.getObject().getIdProveedor());
            setNombre(event.getObject().getNombre());
            setId_proveedor(event.getObject().getIdProveedor());
            System.out.println("Nombre del proveedor seleccionado:  " + activosFijos.getProveedor());
            System.out.println("Nombre del proveedor seleccionado variable :  " + getNombre());

            this.listaintangible.setIdproveedor(event.getObject().getIdProveedor());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void PFW(String msj) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, warnMsj, msj));

    }

    public void PFE(String msj) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, infMsj, msj));

    }
}
