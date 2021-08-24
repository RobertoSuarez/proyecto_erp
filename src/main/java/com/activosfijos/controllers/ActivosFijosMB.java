/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.controllers;

import com.activosfijos.dao.AgotableDAO;
import com.activosfijos.dao.NoDepreciableDAO;
import com.activosfijos.dao.TangibleDAO;
import com.activosfijos.model.ActivoAgotable;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import com.activosfijos.model.ActivosFijos;
import com.activosfijos.model.ActivoDepreciable;
import com.activosfijos.model.ActivoNoDepreciable;
import com.activosfijos.model.ListaAgotable;
import com.activosfijos.model.ListaDepreciable;
import com.activosfijos.model.ListaNoDepreciable;
import com.cuentasporpagar.models.Proveedor;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
//import javax.faces.bean.ViewScoped;
//import javax.faces.bean.SessionScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author desta
 */
@Named ( value = "activosFijosMB")
//@ManagedBean(name = "activosFijosMB")
@ViewScoped
//@SessionScoped
public class ActivosFijosMB implements Serializable {

    ActivosFijos activosFijos = new ActivosFijos();
    ActivoDepreciable activodepreciable = new ActivoDepreciable();
    ActivoNoDepreciable activoNoDepreciable = new ActivoNoDepreciable();
    ActivoAgotable activosagotables = new ActivoAgotable();
    TangibleDAO tangibleDAO = new TangibleDAO();
    NoDepreciableDAO nodepreciabledao = new NoDepreciableDAO();
    AgotableDAO agotablesdao = new AgotableDAO();
    ListaDepreciable listadepreciable = new ListaDepreciable();
    ListaNoDepreciable listanodepreciable = new ListaNoDepreciable();
    ListaAgotable listaragotables = new ListaAgotable();
    int idactivofijo;
    int id_proveedor=0;





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
    
    public ListaAgotable getListaragotables() {
        return listaragotables;
    }

    public void setListaragotables(ListaAgotable listaragotables) {
        this.listaragotables = listaragotables;
    }

    public AgotableDAO getAgotablesdao() {
        return agotablesdao;
    }

    public void setAgotablesdao(AgotableDAO agotablesdao) {
        this.agotablesdao = agotablesdao;
    }

    public ActivoAgotable getActivosagotables() {
        return activosagotables;
    }

    public void setActivosagotables(ActivoAgotable activosagotables) {
        this.activosagotables = activosagotables;
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



    public void setRegistrar() {
        String data = "";
        TangibleDAO tangibledao = new TangibleDAO();
        try {
            tangibledao.guardar(activosFijos, activodepreciable);
            System.out.println("Registrado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

    public void setEditar() {
        String data = "";
        TangibleDAO tangibledao = new TangibleDAO();
        try {
            listadepreciable.setId_activo_fijo(idactivofijo);
            tangibledao.editar(listadepreciable);
            System.out.println("Actualizado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

    public void setEditar1() {
        String data = "";
        NoDepreciableDAO nodepreciabledao = new NoDepreciableDAO();
        try {
            activoNoDepreciable.setId_activo_fijo(idactivofijo);
            nodepreciabledao.editar1(listanodepreciable);
            System.out.println("Actualizado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

    public void setEditar2() {
        String data = "";
        AgotableDAO agotablesdao = new AgotableDAO();
        try {
            activosagotables.setId_activo_fijo(idactivofijo);
            agotablesdao.editar2(listaragotables);
            System.out.println("Actualizado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

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

    public void regresar() {

    }

    public void obtenerdatos(ListaDepreciable lista) {
        this.listadepreciable = lista;
        idactivofijo = lista.getId_activo_fijo();
        System.out.println("Id obtenido de activo: " + lista.getId_activo_fijo());
        System.out.println("Id obtenido de empresa: " + lista.getId_empresa());

    }

    public void obtenerdatosNodepreciables(ListaNoDepreciable listanp) {
        this.listanodepreciable = listanp;
        idactivofijo = listanp.getId_activo_fijo();
        System.out.println("Id obtenido de activo: " + listanp.getId_activo_fijo());
        System.out.println("Id obtenido de empresa: " + listanp.getId_empresa());

    }

    public void obtenerdatosAgotables(ListaAgotable listaago) {
        this.listaragotables = listaago;
        idactivofijo = listaago.getId_activo_fijo();
        System.out.println("Id obtenido de activo: " + listaago.getId_activo_fijo());
        System.out.println("Id obtenido de empresa: " + listaago.getId_empresa());

    }

    public void setRegistrarNoDepreciable() {
        String data = "";
        NoDepreciableDAO nodepreciable = new NoDepreciableDAO();
        try {
            nodepreciable.guardar1(activosFijos, activoNoDepreciable);
            System.out.println("Registrado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data + "se ingreso no depreciable");

    }

    public void setRegistrarAgotables() {
        String data = "";
        AgotableDAO tangibleDAO = new AgotableDAO();
        try {
            agotablesdao.guardar2(activosFijos, activosagotables);
            System.out.println("Registrado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data + "se ingreso no depreciable");

    }

    public void setDeshabilitartangible() {
        String data = "";
        try {
            activodepreciable.setId_activo_fijo(idactivofijo);
            tangibleDAO.deshabilitartangible(listadepreciable);
            System.out.println("Actualizado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

    public void setDeshabilitarnodepreciable() {
        String data = "";
        NoDepreciableDAO nodepreciabledao = new NoDepreciableDAO();
        try {
            activoNoDepreciable.setId_activo_fijo(idactivofijo);
            nodepreciabledao.deshabilitarnoDepreciable(listanodepreciable);
            System.out.println("Actualizado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

    public void setDeshabilitaragotable() {
        String data = "";
        try {
            activosagotables.setId_activo_fijo(idactivofijo);
            agotablesdao.deshabilitarnoagotable(listaragotables);
            System.out.println("Actualizado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

    public void setHabilitarintangible(int id) {
        String data = "";

        try {
            activodepreciable.setId_activo_fijo(id);
            tangibleDAO.habilitardepreciable(activodepreciable);
            System.out.println("Actualizado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

    public void setHabilitarintangibleNoDepreciable(int id) {
        String data = "";

        try {
            activoNoDepreciable.setId_activo_fijo(id);
            nodepreciabledao.habilitarnoDepreciable(activoNoDepreciable);
            System.out.println("Actualizado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

    public void setHabilitarintangibleAgotables(int id) {
        String data = "";

        try {
            activosagotables.setId_activo_fijo(id);
            agotablesdao.habilitarnoAgotable(activosagotables);
            System.out.println("Actualizado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(data);

    }

    public void onRowSelect(SelectEvent<Proveedor> event) {
        try {
            
            this.activosFijos.setProveedor(event.getObject().getNombre());
            this.activosFijos.setIdproveedor(event.getObject().getIdProveedor());
            
                        System.out.println("Nombre del proveedor seleccionado:  "+activosFijos.getProveedor());
                        mostrarMensajeInformacion(activosFijos.getProveedor());
            this.listadepreciable.setIdproveedor(event.getObject().getIdProveedor());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void mostrarMensajeInformacion(String mensaje) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Exito", mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void getDataApi() {
        String data = "";
        data = "{\"detalle_de_activo\":\"" + activosFijos.getDetalle_de_activo()
                + "\",\"valor_adquisicion\":\"" + activosFijos.getValor_adquisicion() 
                + "\",\"fecha_adquisicion\":\"" + activosFijos.getFecha_adquisicion()
                + "\",\"idproveedor\":\"" + activosFijos.getIdproveedor()
                + "\",\"numero_factura\":\"" + activosFijos.getNumero_factura() 
                + "\",\"depreciacion_meses\":\"" + activodepreciable.getDepreciacion_meses()
                + "\",\"porcentaje_depreciacion\":\"" + activodepreciable.getPorcentaje_depreciacion()+ "\"}";
        System.out.println(data);
        consumirapi(data);
    }

    public static void consumirapi(String data){
        
        WebTarget webTarget;
        Client client;
       //url para dirigir el proyecto llegando al administrador de web services
        String BASE_URI = "http://localhost:8080/proyecto_erp/webresources";
        //String BASE_URI = "https://restapipurchase.azurewebsites.net/webresources";
        
        // dirige a la clase de web service
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("depreciable");
        // dirige a la ruta del web service al metodo api post
        Response r = webTarget.path("insertdepreciable").request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(javax.ws.rs.client.Entity.entity(data, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        String result = r.readEntity(String.class);
        System.out.println(r.getStatus());
        System.out.println(result);
    }
}
