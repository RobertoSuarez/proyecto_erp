package com.seguridad.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario implements Serializable{

    int idUsuario;
    String nombre;// limte 50
    String apellido;// limte 50
    String username;// limte 15
    String password;// limte 15
    Date fehcaCreacion;
    boolean habilitado = true;
    String email;
    List<Rol> roles;
    
    String mensajeAux;
    int codigoAux;
    
    public Usuario() {
        this.roles = new ArrayList<>();
    }

    public String getMensajeAux() {
        return mensajeAux;
    }

    public void setMensajeAux(String mensajeAux) {
        this.mensajeAux = mensajeAux;
    }

    public int getCodigoAux() {
        return codigoAux;
    }

    public void setCodigoAux(int codigoAux) {
        this.codigoAux = codigoAux;
    }

    public Usuario(int code, String reslt, int iduser, String name, String surname, String usrnm, String pssword, String mail) {
        this.codigoAux = code;
        this.mensajeAux = reslt;
        this.idUsuario = iduser;
        this.nombre = name;
        this.apellido = surname;
        this.username = usrnm;
        this.password = pssword;
        this.email = mail;

    }

    public Usuario(String usuario, String password) {
        this.password = password;
        this.username = usuario;
    }

    public Usuario(int idUsuario, String nombre, String apellido, String username, String password, Date fehcaCreacion, boolean habilitado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = password;
        this.fehcaCreacion = fehcaCreacion;
        this.habilitado = habilitado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFehcaCreacion() {
        return fehcaCreacion;
    }

    public void setFehcaCreacion(Date fehcaCreacion) {
        this.fehcaCreacion = fehcaCreacion;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
    
}
