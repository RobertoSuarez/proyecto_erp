/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.controllers;

/**
 *
 * @author ClasK
 */

import com.seguridad.controllers.UsuarioController;
import com.seguridad.models.Rol;
import com.seguridad.models.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

//seguridadCtsXCbr: Nombre "Seguridad de Cuentas por Cobrar
@Named(value="seguridadCtsXCbr")
@SessionScoped
public class SeguridadCuentasXCobrar implements Serializable{
    private UsuarioController usuarioController;
    
    public SeguridadCuentasXCobrar() {
        this.usuarioController = new UsuarioController();
    }    
    
    public String renderForGerente(){
        List<String> rolesAdmitidos = new ArrayList<>();
        rolesAdmitidos.add("Gerente");
        if(usuarioController.verificarPermisoNombre(rolesAdmitidos, 2)){
            return "true";
        }
        return "false";
    }
    
    public String renderForEveryone(){
        List<String> rolesAdmitidos = new ArrayList<>();
        rolesAdmitidos.add("Gerente");
        rolesAdmitidos.add("Contador");
        if(usuarioController.verificarPermisoNombre(rolesAdmitidos, 2)){
            return "true";
        }
        return "false";
    }
    
    public void verificarPermiso() {
        FacesContext context = FacesContext.getCurrentInstance();
        List<Rol> roles = (List<Rol>) context.getExternalContext().getSessionMap().get("roles");
        try {
            if (renderForEveryone().equals("false")) {
                context.getExternalContext().redirect("/proyecto_erp/View/Global/Main.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
