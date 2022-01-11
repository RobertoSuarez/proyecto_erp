/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seguridad.controllers;

import com.seguridad.dao.UsuarioDAO;
import com.seguridad.models.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named(value = "usuarioMB")
@SessionScoped
public class UsuarioController implements Serializable {

    private Usuario usuario;
    private List<Usuario> listaUsuario;
    private UsuarioDAO usuarioDAO;
    String warnMsj = "Advertencia";
    String infMsj = "Exito";
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);

    public UsuarioController() {
        usuario = new Usuario();
        usuarioDAO = new UsuarioDAO();
        listaUsuario = new ArrayList<>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public String registrarUsuario() throws Exception {

        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(usuario.getEmail());  ///Ingresar dato Email a la BD
        //  
        try {

            if ("".equals(usuario.getNombre())) {
                PFW("Ingrese un Nombre");

                //  PrimeFaces.current().ajax().update("form:messages");
            } else if ("".equals(usuario.getApellido())) {
                PFW("Ingrese un Apellido");

            } else if ("".equals(usuario.getUsername())) {
                PFW("Ingrese un Usuario");

            } else if ("".equals(usuario.getPassword())) {
                PFW("Ingrese una Contraseña");
            }/* else if (usuario.isHabilitado() == false) {
                PFW("Aceptar los terminos y condiciones");

            } */else if (matcher.find() == false) {
                PFW("Ingrese un email válido");
            } else {
                this.usuarioDAO.registrarUsuario(usuario);
                PFE("Usuario registrado");
                return "/View/login_and_registro/login.xhtml";
            }
        } catch (Exception e) {

        }
        return "";
    }

    public String iniciarSesion() {
        listaUsuario = new ArrayList<>();
        if ("".equals(usuario.getUsername())) {
            PFW("Ingrese un usuario");
        }
        if ("".equals(usuario.getPassword())) {
            PFW("Ingrese una contraseña");
        }
        if (!usuario.getUsername().isEmpty() && !usuario.getPassword().isEmpty()) {
            listaUsuario = usuarioDAO.iniciarSesion(usuario);
            if (listaUsuario.get(0).getCode() == -1) {
                PFW(listaUsuario.get(0).getMsj());
                return "";
            } else if (listaUsuario.get(0).getCode() == -2) {
                PFE(listaUsuario.get(0).getMsj());
                return "";

            } else {
                PFE(listaUsuario.get(0).getMsj());
                httpSession.setAttribute("username", listaUsuario);
                return "/View/Global/Main?faces-redirect=true";
            }

        } else {
            return "";
        }
    }

    public void PFW(String msj) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, warnMsj, msj));

    }

    public void PFE(String msj) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, infMsj, msj));

    }


}
