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

    // Metodo que se usa para el registro de un ACTIVO INTANGIBLE     
    public void setRegistrarIntangible() {
        String data = "";
        intangibledao = new IntangibleDAO();
        try {
            intangibledao.guardarActivoIntangible(activosFijos, activoingantible);
            System.out.println("Registrado correctamente");
            PrimeFaces.current().executeScript("PF('NuevoIntangible').hide()");
            mensajeDeExito("Activo intangible agregado correctamente");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }
// Lista los ACTIVOS INTANGIBLES registrados en la base de datos

    public void obtenerdatosIntangibles(ListarIntangible listaIntan) {
        this.listaintangible = listaIntan;
        idactivofijo = listaIntan.getId_activo_fijo();
        System.out.println("Id obtenido de activo: " + listaIntan.getId_activo_fijo());
        System.out.println("Id obtenido de empresa: " + listaIntan.getId_empresa());
        System.out.println("Id obtenido de idproveedor: " + listaIntan.getIdproveedor());

    }

//Método que sirve para editar un activo Intangible
    public void setEditarIntangible() {
        String data = "";
        try {
            listaintangible.setId_activo_fijo(idactivofijo);
            intangibledao.editarActivosIntangibles(listaintangible);
            System.out.println("activos_fijos/Actualizado correctamente");
            PrimeFaces.current().executeScript("PF('EditarIntangible').hide()");
            PrimeFaces.current().ajax().update("formintangible:verListaIntangibles");
            mensajeDeExito("Activo intangible actualizado");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }
//Método que sirve para deshablitar un ACTIVO INTANGIBLE

    public void setDeshabilitarIntangible() {
        String data = "";
        intangibledao = new IntangibleDAO();
        try {
            activoingantible.setId_activo_fijo(idactivofijo);
            intangibledao.deshabilitarIntangible(listaintangible);
            System.out.println("activos_fijos/Actualizado correctamente");
            PrimeFaces.current().executeScript("PF('EditarIntangible').hide()");
            PrimeFaces.current().ajax().update(":formintangible");
            mensajeDeExito("Activo intangible deshabilitado");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);
    }
//Metodo que sirve para habilitar un ACTIVO INTANGIBLE

    public void setHabilitarIntangible(int id) {
        String data = "";
        try {
            activoingantible.setId_activo_fijo(id);
            intangibledao.habilitarIntangible(activoingantible);
            System.out.println("activos_fijos/Actualizado correctamente");
            PrimeFaces.current().executeScript("PF('deshabilitadosintangible').hide()");
            PrimeFaces.current().ajax().update(":formintangible");
            mensajeDeExito("Activo intangible habilitado");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

    /*Metodo el cual se utiliza para la seleccion de un proveedor en espesifico 
      dicho metodo se lo utilizara en el componente OverlayPanel en donde 
      recibe como parametros una lista de proveedores */
    public void onRowSelect(SelectEvent<Proveedor> event) {
        try {

            this.activosFijos.setProveedor(event.getObject().getNombre());
            this.activosFijos.setIdproveedor(event.getObject().getIdProveedor());
            System.out.println("Nombre del proveedor seleccionado:  " + activosFijos.getProveedor());
            System.out.println("Nombre del proveedor seleccionado variable :  " + getNombre());
            this.listaintangible.setIdproveedor(event.getObject().getIdProveedor());
            this.listaintangible.setProveedor(event.getObject().getNombre());
            setNombre(event.getObject().getNombre());
            setId_proveedor(event.getObject().getIdProveedor());
            PrimeFaces.current().ajax().update(":formintangible:panelnuevodepreciableintangible");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*Metodo para mostrar un mensaje de advertencia en caso de que el usuario
      haya cometido algun error al ejecutar una accion o al no ingresar los
      datos necesarios a algun componente*/
    public void mensajeDeAdvertencia(String msj) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, warnMsj, msj));

    }


    /*Metodo para mostrar un mensaje de éxito en caso de que el usuario
      haya realizado alguna accion ya sea el registrar editar o deshabilitar 
      algun dato*/
    public void mensajeDeExito(String msj) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, infMsj, msj));

    }
}
