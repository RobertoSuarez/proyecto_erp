/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registroUsuario.controllers;

import com.registroUsuario.dao.UsuarioDAO;
import com.registroUsuario.models.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javassist.bytecode.stackmap.BasicBlock;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.core.Context;
import org.primefaces.PrimeFaces;

@Named(value = "usuarioMB")
@ViewScoped
public class UsuarioController implements Serializable {

    Usuario usuario;
    UsuarioDAO usuarioDAO;
    String warnMsj = "Advertencia";
    String infMsj = "Exito";

    public UsuarioController() {
        usuario = new Usuario();
        usuarioDAO = new UsuarioDAO();

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

    public void registrarUsuario() {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        //  Matcher matcher = pattern.matcher(usuario.getEmail())  ///Ingresar dato Email a la BD
        //  this.usuarioDAO.registrarUsuario(usuario);
        try {

            if ("".equals(usuario.getNombre())) {
                PFW("Ingrese un Nombre");
                Date fecha = new Date();
                System.err.println(fecha);

                //  PrimeFaces.current().ajax().update("form:messages");
            } else if ("".equals(usuario.getApellido())) {
                PFW("Ingrese un Apellido");

            } else if ("".equals(usuario.getUsername())) {
                PFW("Ingrese un Usuario");

            } else if ("".equals(usuario.getPassword())) {
                PFW("Ingrese una Contraseña");

            }else {
                PFE("Usuario registrado");

            }
        } catch (Exception e) {

        }

    }

    public void PFW(String msj) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, warnMsj, msj));

    }

    public void PFE(String msj) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, infMsj, msj));

    }

    public void validateSamePassword(FacesContext context, UIComponent uIComponent, Object value) {
        UIInput passwordField = (UIInput) context.getViewRoot().findComponent("registerForm:password");
    if (passwordField == null)
        throw new IllegalArgumentException(String.format("Unable to find component."));
    String password = (String) passwordField.getValue();
    String confirmPassword = (String) value;
    if (!confirmPassword.equals(password)) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Passwords do not match!", "Passwords do not match!");
        throw new ValidatorException(message);
    }
    }
}
