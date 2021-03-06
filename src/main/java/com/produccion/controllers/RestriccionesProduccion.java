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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author HP
 */
@Named(value = "restriccionesProduccion")
@ViewScoped
public class RestriccionesProduccion implements Serializable {

    UsuarioDAO usuarioDAO;
    FacesContext context = FacesContext.getCurrentInstance();
    /** Obteniendo la lista de Roles*/
    List<Roles> listaRoles = (List<Roles>) context.getExternalContext().getSessionMap().get("usuario_rol");
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

    public RestriccionesProduccion() {
        usuarioDAO = new UsuarioDAO();
    }
    //
    public String renderedMproduccion() {
        if (listaRoles.get(0).getNombre().equals("Operario")) {
            return "false";
        } else {
            return "true";
        }
    }

    public String restriccionUser() {
        if (listaRoles.get(0).getNombre().equals("Jefe de Producción")) {
            return "../produccion/listaOrdenProduccion.xhtml";
        } else {
            return "../produccion/procesoProduccion.xhtml";
        }
    }
    public String renderizarMenuInicio() {
        if ("Jefe de Producción".equals(listaRoles.get(0).getNombre())
                    || "Operario".equals(listaRoles.get(0).getNombre())
                    || "Gerente".equals(listaRoles.get(0).getNombre())) {
            return "true";
        } else {
            return "false";
        }
    }

    public void redireccionExternas() throws IOException {

        if (!"Jefe de Producción".equals(listaRoles.get(0).getNombre())&&
                !"Operario".equals(listaRoles.get(0).getNombre())
                && !"Gerente".equals(listaRoles.get(0).getNombre())) {
            externalContext.redirect("../Global/Main.xhtml");
        }
    }
    public void redireccionInternas() throws IOException {

        if ("Operario".equals(listaRoles.get(0).getNombre())) {
            externalContext.redirect("../produccion/procesoProduccion.xhtml");
        }
    }
}
