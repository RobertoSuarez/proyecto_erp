/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controllers;

import com.seguridad.dao.UsuarioDAO;
import com.seguridad.models.Roles;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Andres Mora
 */
@Named
@ViewScoped
public class RolController implements Serializable {

    private UsuarioDAO usuarioDAO;
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    List<Roles> listaRoles = (List<Roles>) context.getExternalContext().getSessionMap().get("usuario_rol");

    public RolController() {
        usuarioDAO = new UsuarioDAO();
    }

    public String renderPrincipalView() {
        if (listaRoles.get(0).getNombre().equals("Gerente")) {
            return "true";
        } else {
            return "false";
        }
    }

}
