/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registroUsuario.controllers;

import com.registroUsuario.dao.UsuarioDAO;
import com.registroUsuario.models.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "usuarioMB")
@ViewScoped
public class UsuarioController implements Serializable {
    
    Usuario usuario;
    private  List<Usuario> listaUsuario;
    UsuarioDAO usuarioDAO;
    String warnMsj = "Advertencia";
    String infMsj = "Exito";
    
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
    
    public void registrarUsuario() throws Exception {
        
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
                
            } else if (matcher.find() == false) {
                PFW("Ingrese un email válido");
            } else {
                this.usuarioDAO.registrarUsuario(usuario);
                PFE("Usuario registrado");
                
            }
        } catch (Exception e) {
            
        }
        
    }
    
    public void iniciarSesion() {
        listaUsuario = new ArrayList<>();
        listaUsuario = usuarioDAO.iniciarSesion(usuario);
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
