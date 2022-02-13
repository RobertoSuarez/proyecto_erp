/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.controllers;

import com.seguridad.controllers.UsuarioController;
import com.seguridad.models.Rol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value="seguridadActvFijo")
@SessionScoped
public class SeguridadActivosFijos implements Serializable{

    private UsuarioController usuarioSesion;
    
    public SeguridadActivosFijos() {
        this.usuarioSesion = new UsuarioController();
    }
    
    public String renderSoloGerente(){
        List<String> rolesAdmitidos = new ArrayList<>();
        rolesAdmitidos.add("Gerente");
        if(usuarioSesion.verificarPermisoNombre(rolesAdmitidos, 2)){
            return "true";
        }
        return "false";
    }
    
    public String renderTodosRolesAdmitidos(){
        List<String> rolesAdmitidos = new ArrayList<>();
        rolesAdmitidos.add("Gerente");
        rolesAdmitidos.add("Contador");
        if(usuarioSesion.verificarPermisoNombre(rolesAdmitidos, 2)){
            return "true";
        }
        return "false";
    }
    
    public void verificarPermisoVista() {
        FacesContext context = FacesContext.getCurrentInstance();
        List<Rol> roles = (List<Rol>) context.getExternalContext().getSessionMap().get("roles");
        try {
            if (renderTodosRolesAdmitidos().equals("false")) {
                context.getExternalContext().redirect("/proyecto_erp/View/Global/Main.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
