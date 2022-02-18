/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ventas.controllers;

import com.seguridad.dao.UsuarioDAO;
import com.seguridad.models.Roles;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author cturriagos
 */
@Named(value = "rolventMB")
@ViewScoped
public class RestriccionVenta implements Serializable {

    private UsuarioDAO usuarioDAO;
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    List<Roles> listaRoles = (List<Roles>) context.getExternalContext().getSessionMap().get("usuario_rol");

    public RestriccionVenta() {
        usuarioDAO = new UsuarioDAO();
    }

    /**
     * Creación del método rol, para comprobar si un rol es detectado
     * @return String falso o verdadero.
     **/
    public String rol() {
        if (listaRoles.get(0).getNombre().equals("Vendedor")) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * Creación del método rendered, para comprobar si un rol es detectado
     * y controlar aspectos visuales
     * @return String falso o verdadero.
     **/
    public String rendered() {
        if (listaRoles.get(0).getNombre().equals("Vendedor")) {
            return "false";
        } else {
            return "true";
        }
    }

    /**
     * Creación del método vista, para comprobar si un rol es detectado
     * y redireccionar páginas (NO UTILIZADO)
     * @return String falso o verdadero.
     **/
    public String vista() {
        if (listaRoles.get(0).getNombre().equals("Vendedor")) {
            return "../recursoshumanos/ciudad.xhtml";
        } else {
            return "../recursoshumanos/empresa.xhtml";
        }
    }

    /**
     * Creación del método menu, para comprobar si un rol es detectado
     * y acceder al menú principal
     * @return String falso o verdadero.
     **/
    public String menu() {
        if ("Vendedor".equals(listaRoles.get(0).getNombre())
                || "Vendedor".equals(listaRoles.get(0).getNombre())) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * Creación del método red, para comprobar si un rol es detectado
     * e ingresar al módulo
     * @return void
     **/
    public void red() {
        if ("Gerente".equals(listaRoles.get(0).getNombre())
                || "Administrador de la empresa".equals(listaRoles.get(0).getNombre())
                || "Contador".equals(listaRoles.get(0).getNombre())
                || "Asistente del contador".equals(listaRoles.get(0).getNombre())) {
            System.out.println("Ingreso exitoso");
        } else {
            try {
                externalContext.redirect("/proyecto_erp/View/Global/Main.xhtml");
            } catch (IOException ex) {

            }
        }
    }
}
