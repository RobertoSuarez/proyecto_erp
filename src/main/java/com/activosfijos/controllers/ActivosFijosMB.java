/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.controllers;

import com.activosfijos.dao.NoDepreciableDAO;
import com.activosfijos.dao.TangibleDAO;
import java.io.Serializable;
import com.activosfijos.model.ActivosFijos;
import com.activosfijos.model.ActivoDepreciable;
import com.activosfijos.model.ActivoNoDepreciable;
import com.activosfijos.model.ListaDepreciable;
import com.activosfijos.model.ListaNoDepreciable;
import com.cuentasporpagar.models.Proveedor;
import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author desta
 */
@Named(value = "activosFijosMB")
@ViewScoped
public class ActivosFijosMB implements Serializable {

    ActivosFijos activosFijos = new ActivosFijos();
    ActivoDepreciable activodepreciable = new ActivoDepreciable();
    ActivoNoDepreciable activoNoDepreciable = new ActivoNoDepreciable();
    TangibleDAO tangibleDAO = new TangibleDAO();
    NoDepreciableDAO nodepreciabledao = new NoDepreciableDAO();
    ListaDepreciable listadepreciable = new ListaDepreciable();
    ListaNoDepreciable listanodepreciable = new ListaNoDepreciable();
    private List<ListaDepreciable> listamesesD;
    int idactivofijo;
    int id_proveedor = 0;
    String nombre = "";
    String warnMsj = "Advertencia";
    String infMsj = "Exito";

    public ActivosFijosMB() {
        listamesesD = new ArrayList<>();
    }

    public List<ListaDepreciable> getListamesesD() {
        return listamesesD;
    }

    public void setListamesesD(List<ListaDepreciable> listamesesD) {
        this.listamesesD = listamesesD;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public ActivosFijos getActivosFijos() {
        return activosFijos;
    }

    public void setActivosFijos(ActivosFijos activosFijos) {
        this.activosFijos = activosFijos;
    }

    public NoDepreciableDAO getNodepreciabledao() {
        return nodepreciabledao;
    }

    public ListaNoDepreciable getListanodepreciable() {
        return listanodepreciable;
    }

    public void setListanodepreciable(ListaNoDepreciable listanodepreciable) {
        this.listanodepreciable = listanodepreciable;
    }

    public void setNodepreciabledao(NoDepreciableDAO nodepreciabledao) {
        this.nodepreciabledao = nodepreciabledao;
    }

    public ListaDepreciable getListadepreciable() {
        return listadepreciable;
    }

    public void setListadepreciable(ListaDepreciable listadepreciable) {
        this.listadepreciable = listadepreciable;
    }

    public TangibleDAO getTangibleDAO() {
        return tangibleDAO;
    }

    public void setTangibleDAO(TangibleDAO tangibleDAO) {
        this.tangibleDAO = tangibleDAO;
    }

    public ActivoNoDepreciable getActivoNoDepreciable() {
        return activoNoDepreciable;
    }

    public ActivoDepreciable getActivodepreciable() {
        return activodepreciable;
    }

    public void setActivoNoDepreciable(ActivoNoDepreciable activoNoDepreciable) {
        this.activoNoDepreciable = activoNoDepreciable;
    }

    public void setActivodepreciable(ActivoDepreciable activodepreciable) {
        this.activodepreciable = activodepreciable;
    }

//----------------------------Activos fijos tangibles-------------------------\\
//---------- DEPRECIABLES
//Registrar un tangible depreciable
    public void setRegistrarTangible() {
        String data = "";
        TangibleDAO tangibledao = new TangibleDAO();
        try {
            if ("".equals(activosFijos.getIdproveedor())) {
                mensajeDeAdvertencia("Agrege un proveedor");
            } else if ("".equals(activosFijos.getValor_adquisicion()) || activosFijos.getValor_adquisicion() <= 0) {
                mensajeDeAdvertencia("Agrege un costo de adquisición");
            } else if ("".equals(activosFijos.getFecha_adquisicion())) {
                mensajeDeAdvertencia("Agrege una fecha de adquisición");
            } else if ("".equals(activosFijos.getDetalle_de_activo())) {
                mensajeDeAdvertencia("Agrege un detalle para el activo tangible depreciable");
            } else if ("".equals(activosFijos.getNumero_factura())) {
                mensajeDeAdvertencia("Agrege un número de factura");
            } else if ("".equals(activodepreciable.getDepreciacion_meses()) || activodepreciable.getDepreciacion_meses() <= 0) {
                mensajeDeAdvertencia("Agrege un tiempo de depreciacion mensual");
            } else if ("".equals(activodepreciable.getPorcentaje_depreciacion()) || activodepreciable.getPorcentaje_depreciacion() <= 0.0) {
                mensajeDeAdvertencia("Agrege un porcentaje de depreciación");
            } else if ("".equals(getNombre())) {
                mensajeDeAdvertencia("Agrege un proveedor");
            } else {

                tangibledao.registrarTangibleDepreciable(activosFijos, activodepreciable);
                System.out.println("Registrado correctamente");
                //Aqui se llama a la funcion de depreciable
                tangibledao.actualizarcuotadepresiacion();
                mensajeDeExito("Activo tangible depreciable agregado");
                PrimeFaces.current().executeScript("PF('NuevoDepreciable').hide()");
                activosFijos.setDetalle_de_activo("");
                activosFijos.setValor_adquisicion(0);
                activosFijos.setNumero_factura("001-001-000000000");
                activosFijos.setFecha_adquisicion(LocalDate.now());
                activodepreciable.setDepreciacion_meses(0);
                activodepreciable.setPorcentaje_depreciacion(0.0);
                activodepreciable.setCuota_depresiacion(0);
                setNombre("");
                PrimeFaces.current().ajax().update(":formnuevoDepreciable:panelnuevodepreciable");

            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }
//Editar un tangible depreciable

    public void setEditarTangibles() {
        String data = "";
        TangibleDAO tangibledao = new TangibleDAO();

        try {
            listadepreciable.setId_activo_fijo(idactivofijo);

            if ("".equals(listadepreciable.getProveedor())) {
                mensajeDeAdvertencia("Agrege un proveedor");
            } else if ("".equals(listadepreciable.getValor_adquisicion()) || listadepreciable.getValor_adquisicion() <= 0) {
                mensajeDeAdvertencia("Agrege un costo de adquisición");
            } else if ("".equals(listadepreciable.getFecha_adquisicion())) {
                mensajeDeAdvertencia("Agrege una fecha de adquisición");
            } else if ("".equals(listadepreciable.getDetalle_de_activo())) {
                mensajeDeAdvertencia("Agrege un detalle para el activo tangible depreciable");
            } else if ("".equals(listadepreciable.getNumero_factura())) {
                mensajeDeAdvertencia("Agrege un número de factura");
            } else if ("".equals(listadepreciable.getDepreciacion_meses()) || listadepreciable.getDepreciacion_meses() <= 0) {
                mensajeDeAdvertencia("Agrege un tiempo de depreciacion mensual");
            } else if ("".equals(listadepreciable.getPorcentaje_depreciacion()) || listadepreciable.getPorcentaje_depreciacion() <= 0.0) {
                mensajeDeAdvertencia("Agrege un porcentaje de depreciación");
            } else {

                tangibledao.editarTangibleDepreciable(listadepreciable);
                System.out.println("Actualizado correctamente");
                mensajeDeExito("Activo tangible depreciable actualizado");
                PrimeFaces.current().executeScript("PF('EditarDepreciable').hide()");
                activosFijos.setDetalle_de_activo("");
                activosFijos.setValor_adquisicion(0);
                activosFijos.setNumero_factura("001-001-000000000");
                activosFijos.setFecha_adquisicion(LocalDate.now());
                activodepreciable.setDepreciacion_meses(0);
                activodepreciable.setPorcentaje_depreciacion(0.0);
                listadepreciable.setProveedor("");
                setNombre("");
                PrimeFaces.current().ajax().update(":editarDepreciable:paneleditarpreciableeditar");

            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }
//Habilitar tangible depreciable

    public void setHabilitarTangibleDepreciable(int id) {
        String data = "";

        try {
            activodepreciable.setId_activo_fijo(id);
            tangibleDAO.habilitardepreciable(activodepreciable);
            System.out.println("Actualizado correctamente");
            mensajeDeExito("Activo tangible habilitado");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }
//Deshabilitar tangible depreciable

    public void setDeshabilitarTangibleDepreciable() {
        String data = "";
        try {
            activodepreciable.setId_activo_fijo(idactivofijo);
            tangibleDAO.deshabilitartangible(listadepreciable);
            System.out.println("Actualizado correctamente");
            mensajeDeExito("Activo tangible deshabilitado");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }
//--------- NO DEPRECIABLES
//Registrar un tangible no depreciable

    public void setRegistrarNoDepreciable() {
        String data = "";
        NoDepreciableDAO nodepreciable = new NoDepreciableDAO();
        try {
            if ("".equals(activosFijos.getDetalle_de_activo())) {
                mensajeDeAdvertencia("Agregue una descripcion al activo no depreciable");
            } else if ("".equals(activosFijos.getValor_adquisicion()) || activosFijos.getValor_adquisicion() <= 0) {
                mensajeDeAdvertencia("Agregue un costo de adquisición ");
            } else if ("".equals(activosFijos.getFecha_adquisicion())) {
                mensajeDeAdvertencia("Agregue una fecha de adquisición");
            } else if ("".equals(activoNoDepreciable.getPlusvalia()) || activoNoDepreciable.getPlusvalia() <= 0) {
                mensajeDeAdvertencia("Agregue un valor de plusvalia");
            } else if ("".equals(activosFijos.getNumero_factura())) {
                mensajeDeAdvertencia("Agregue un numero de factura");
            } else if ("".equals(getNombre())) {
                mensajeDeAdvertencia("Agregue un proveedor");
            } else {
                nodepreciable.registrarTangibleNoDepreciable(activosFijos, activoNoDepreciable);
                System.out.println("Registrado correctamente");
                mensajeDeExito("Activo no depreciable registrado");
                activosFijos.setDetalle_de_activo("");
                activosFijos.setValor_adquisicion(0);
                activosFijos.setNumero_factura("001-001-000000000");
                activosFijos.setFecha_adquisicion(LocalDate.now());
                activoNoDepreciable.setPlusvalia(0.0);
                setNombre("");
                PrimeFaces.current().executeScript("PF('NuevoNoDepreciable').hide()");

            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data + "se ingreso no depreciable");

    }
    //Editar un tangible no depreciable

    public void setEditarNoDepreciable() {

        nodepreciabledao = new NoDepreciableDAO();
        try {

            listanodepreciable.setId_activo_fijo(idactivofijo);

            if ("".equals(listanodepreciable.getDetalle_de_activo())) {
                mensajeDeAdvertencia("Agregue una descripcion al activo no depreciable");
            } else if ("".equals(listanodepreciable.getValor_adquisicion()) || listanodepreciable.getValor_adquisicion() <= 0) {
                mensajeDeAdvertencia("Agregue un costo de adquisición ");
            } else if ("".equals(listanodepreciable.getFecha_adquisicion())) {
                mensajeDeAdvertencia("Agregue una fecha de adquisición");
            } else if ("".equals(listanodepreciable.getPlusvalia()) || listanodepreciable.getPlusvalia() <= 0) {
                mensajeDeAdvertencia("Agregue un valor de plusvalia");
            } else if ("".equals(listanodepreciable.getNumero_factura())) {
                mensajeDeAdvertencia("Agregue un numero de factura");
            } else if ("".equals(listanodepreciable.getProveedor())) {
                mensajeDeAdvertencia("Agregue un proveedor");
            } else {
                nodepreciabledao.editarNoDepreciable(listanodepreciable);
                System.out.println("Actualizado correctamente");
                mensajeDeExito("Activo tangible no depreciable actualizado");
                activosFijos.setDetalle_de_activo("");
                activosFijos.setValor_adquisicion(0);
                activosFijos.setNumero_factura("001-001-000000000");
                activosFijos.setFecha_adquisicion(LocalDate.now());
                activoNoDepreciable.setPlusvalia(0.0);
                setNombre("");
                PrimeFaces.current().executeScript("PF('EditarNoDepreciable').hide()");

            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

//Habilitar tangible no depreciable
    public void setHabilitarintangibleNoDepreciable(int id) {
        String data = "";

        try {
            activoNoDepreciable.setId_activo_fijo(id);
            nodepreciabledao.habilitarnoDepreciable(activoNoDepreciable);
            System.out.println("Actualizado correctamente");
            mensajeDeExito("Activo tangible no depreciable habilitado");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

//Deshabilitar tangible no depreciable
    public void setDeshabilitarnodepreciable() {
        String data = "";
        nodepreciabledao = new NoDepreciableDAO();
        try {
            activoNoDepreciable.setId_activo_fijo(idactivofijo);
            nodepreciabledao.deshabilitarnoDepreciable(listanodepreciable);
            System.out.println("Actualizado correctamente");
            mensajeDeExito("Activo no depreciable deshabilitado");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }
//--------- INTANGIBLES

    public void setEliminar() {
        String data = "";
        TangibleDAO tangibledao = new TangibleDAO();
        try {
            listadepreciable.setId_activo_fijo(idactivofijo);
            tangibledao.eliminar(listadepreciable);
            System.out.println("Actualizado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

    public void obtenerDatosDepreciables(ListaDepreciable lista) {
        this.listadepreciable = lista;
        idactivofijo = lista.getId_activo_fijo();
        System.out.println("Id obtenido de activo: " + lista.getId_activo_fijo());
        System.out.println("Id obtenido de empresa: " + lista.getProveedor());

    }

    public void obtenerdatosNodepreciables(ListaNoDepreciable listanp) {
        this.listanodepreciable = listanp;
        idactivofijo = listanp.getId_activo_fijo();
        System.out.println("Id obtenido de activo: " + listanp.getId_activo_fijo());
        System.out.println("Id obtenido de empresa: " + listanp.getProveedor());

    }
//Seleccion de proveedor en depreciables

    public void onRowSelectDepreciable(SelectEvent<Proveedor> event) {
        try {
            int idProveedor = event.getObject().getIdProveedor();
            String nombreProveedor = event.getObject().getNombre();
            //activos fijos 
            this.activosFijos.setProveedor(nombreProveedor);
            this.activosFijos.setIdproveedor(idProveedor);
            //LISTA DEPRECIABLE
            this.listadepreciable.setIdproveedor(idProveedor);
            this.listadepreciable.setProveedor(nombreProveedor);
            setNombre(nombreProveedor);
            PrimeFaces.current().ajax().update(":formnuevoDepreciable:panelnuevodepreciable");
            //  PrimeFaces.current().ajax().update(":formnuevoNodepreciable:panelNuevoNoDepreciable");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
//Seleccion de proveedor en no depreciables

    public void onRowSelectNoDepreciable(SelectEvent<Proveedor> event) {
        try {

            int idProveedor = event.getObject().getIdProveedor();
            String nombreProveedor = event.getObject().getNombre();
            //activos fijos 
            this.activosFijos.setProveedor(nombreProveedor);
            this.activosFijos.setIdproveedor(idProveedor);
            PrimeFaces.current().ajax().update(":formnuevoNodepreciable:panelNuevoNoDepreciable");

            //LISTA NO DEPRECIABLE
            this.listanodepreciable.setIdproveedor(idProveedor);
            this.listanodepreciable.setProveedor(nombreProveedor);
            setNombre(nombreProveedor);
            PrimeFaces.current().ajax().update(":formnuevoNodepreciable:panelNuevoNoDepreciable");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
// mensajes de advertencia

    public void mensajeDeAdvertencia(String msj) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, warnMsj, msj));

    }
//mensajes de exito

    public void mensajeDeExito(String msj) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, infMsj, msj));

    }

    public void ListarMesesDepreciables(ListaDepreciable MesesDepre) {
        listamesesD = new ArrayList<>();
        double cuota = MesesDepre.getCuota_depresiacion();
        double valorDepresiacion = MesesDepre.getValor_adquisicion();
        
        int meses_depre = MesesDepre.getDepreciacion_meses();
        for (int x = 0; x <= meses_depre; x++) {
            if (x == 0) {
                listamesesD.add(new ListaDepreciable(x, cuota, valorDepresiacion));
            } else {
                listamesesD.add(new ListaDepreciable(x, cuota, valorDepresiacion -= cuota));
            }

        }
     
    }

}
