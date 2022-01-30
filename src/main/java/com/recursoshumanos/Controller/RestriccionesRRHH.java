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
 */
@Named(value = "rolMB")
@ViewScoped
public class RestriccionesRRHH implements Serializable {

    private UsuarioDAO usuarioDAO;
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    List<Roles> listaRoles = (List<Roles>) context.getExternalContext().getSessionMap().get("usuario_rol");

    public RestriccionesRRHH() {
        usuarioDAO = new UsuarioDAO();
    }

    public String rol() {
        if (listaRoles.get(0).getNombre().equals("Asistente de recursos humanos")) {
            return "true";
        } else {
            return "false";
        }
    }

    public String rendered() {
        if (listaRoles.get(0).getNombre().equals("Asistente de recursos humanos")) {
            return "false";
        } else {
            return "true";
        }
    }

    public String vista() {
        if (listaRoles.get(0).getNombre().equals("Asistente de recursos humanos")) {
            return "../recursoshumanos/ciudad.xhtml";
        } else {
            return "../recursoshumanos/empresa.xhtml";
        }
    }

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
