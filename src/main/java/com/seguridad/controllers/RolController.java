/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controllers;

import com.seguridad.dao.UsuarioDAO;
import com.seguridad.dao.RolDAO;
import com.seguridad.models.Roles;
import com.seguridad.models.Rol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Andres Mora
 */
@Named(value = "RolMB") 
@ViewScoped
public class RolController implements Serializable {

    private RolDAO rolDao; 
    private UsuarioDAO usuarioDAO;
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    List<Roles> listaRoles = (List<Roles>) context.getExternalContext().getSessionMap().get("usuario_rol");
    private List<Rol> lstOfRoles;
    private Rol rolSeleccionado;
    
    public RolController() {
        usuarioDAO = new UsuarioDAO();
        rolSeleccionado = new Rol();
        rolDao = new RolDAO();
        this.lstOfRoles = new ArrayList<>();
        this.lstOfRoles = rolDao.GetRols();
    }

    public String renderPrincipalView() {
        if (listaRoles.get(0).getNombre().equals("Gerente")) {
            return "true";
        } else {
            return "false";
        }
    }

    public List<Rol> getListOfRoles() {
        return lstOfRoles;
    }

    public void setListOfRoles(List<Rol> listOfRoles) {
        this.lstOfRoles = listOfRoles;
    }

    public Rol getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(Rol rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }
    
    

}
