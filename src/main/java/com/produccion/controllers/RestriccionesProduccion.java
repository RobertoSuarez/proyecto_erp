/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controllers;

import com.seguridad.dao.UsuarioDAO;
import com.seguridad.models.Roles;
import java.io.IOException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author HP
 */
@Named(value = "restriccionesProduccion")
@ViewScoped
public class RestriccionesProduccion implements Serializable {

    private UsuarioDAO usuarioDAO;
    FacesContext context = FacesContext.getCurrentInstance();
    List<Roles> listaRoles = (List<Roles>) context.getExternalContext().getSessionMap().get("usuario_rol");
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

    public RestriccionesProduccion() {
        usuarioDAO = new UsuarioDAO();
    }

    public String rendered() {
        if (listaRoles.get(0).getNombre().equals("Asistente de recursos humanos")) {
            return "false";
        } else {
            return "true";
        }
    }

    public void restriccionUser() {
        try {

            if ("Jefe de Producción".equals(listaRoles.get(0).getNombre())
                    || "Operario".equals(listaRoles.get(0).getNombre())
                    || "Gerente".equals(listaRoles.get(0).getNombre())) {
                externalContext.redirect("/proyecto_erp/View/produccion/listaOrdenProduccion.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Solo los Administradores y personal "
                                + "encargado de producción pueden ingresar al módulo."));
            }
        } catch (IOException e) {

        }

        System.out.println("Holaaaaa" + listaRoles.get(0).getNombre());
    }

    public void redireccion() throws IOException {

        if (!"Gerente".equals(listaRoles.get(0).getNombre())
                || !"Jefe de Producción".equals(listaRoles.get(0).getNombre())
                || !"Operario".equals(listaRoles.get(0).getNombre())) {
            //externalContext.redirect("/proyecto_erp/View/Global/Main.xhtml");
        }
    }

}
