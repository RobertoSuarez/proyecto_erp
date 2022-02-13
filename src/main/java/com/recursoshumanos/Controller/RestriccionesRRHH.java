package com.recursoshumanos.Controller;

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
 * @author ninat
 *
 * Las clases CONTROLLER son los que responden a la interacción (eventos del
 * mismo) que hace el usuario en la interfaz y realiza las peticiones al
 * modelDAO
 */
@Named(value = "rolMB")
@ViewScoped
public class RestriccionesRRHH implements Serializable {

    /**
     * Declaración de variables
     */
    private UsuarioDAO usuarioDAO;
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = FacesContext.getCurrentInstance()
            .getExternalContext();
    List<Roles> listaRoles = (List<Roles>) context.getExternalContext()
            .getSessionMap().get("usuario_rol");

    public RestriccionesRRHH() {
        usuarioDAO = new UsuarioDAO();
    }

    /**
     * Metodo que ayuda a deshabilitar botones Recursos Humanos
     * Dependiendo del rol de usuario
     * retorna un String para determinar si disabled es true o false
     */
    public String rol() {
        if (listaRoles.get(0).getNombre()
                .equals("Asistente de recursos humanos")) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * Metodo que ayuda a aplicar rendered en Recursos Humanos
     * Dependiendo del rol de usuario
     * retorna un String para determinar si rendered es true o false
     */
    public String rendered() {
        if (listaRoles.get(0).getNombre().equals("Asistente de recursos humanos")) {
            return "false";
        } else {
            return "true";
        }
    }

    /**
     * Metodo que ayuda a ocultar una pagina de Recursos Humanos
     * Dependiendo del rol de usuario
     * retorna un String con la Url de la pagina que debe mostrar 
     */
    public String vista() {
        if (listaRoles.get(0).getNombre().equals("Asistente de recursos humanos")) {
            return "../recursoshumanos/ciudad.xhtml";
        } else {
            return "../recursoshumanos/empresa.xhtml";
        }
    }

    /**
     * Metodo que ayuda a aplicar rendered en el menu principal
     * Dependiendo del rol de usuario
     * retorna un String para determinar si rendered es true o false
     */
    public String menu() {
        System.out.println("Holaaaaa" + listaRoles.get(0).getNombre());
        if ("Gerente".equals(listaRoles.get(0).getNombre())
                || "Administrador de la empresa".equals(listaRoles.get(0).getNombre())
                || "Jefe de recursos humanos".equals(listaRoles.get(0).getNombre())
                || "Asistente de recursos humanos".equals(listaRoles.get(0).getNombre())) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * Metodo que redirige a el menu principal
     * Dependiendo del rol de usuario
     * si no tiene acceso lo envia al menu principal
     */
    public void red() {
        if ("Gerente".equals(listaRoles.get(0).getNombre())
                || "Administrador de la empresa".equals(listaRoles.get(0).getNombre())
                || "Jefe de recursos humanos".equals(listaRoles.get(0).getNombre())
                || "Asistente de recursos humanos".equals(listaRoles.get(0).getNombre())) {
            System.out.println("Ingreso exitoso");
        } else {
            try {
                externalContext.redirect("/proyecto_erp/View/Global/Main.xhtml");
            } catch (IOException ex) {

            }
        }
    }
}
