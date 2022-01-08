package com.registroUsuario.models;

import java.io.Serializable;
import java.util.Date;

public class Usuario implements Serializable{

    int idUsuario;
    String msj;
    String nombre;// limte 50
    String apellido;// limte 50
    String username;// limte 15
    String password;// limte 15
    Date fehcaCreacion;
    boolean habilitado;
    String email;
    int code;
// RETURNS TABLE(code integer, 
//reslt character varying, 
//iduser integer, name character varying, 
//surname character varying, 
//usrnm character varying, pssword character varying, 
//mail character varying) 

    public Usuario() {

    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Usuario(int code, String reslt, int iduser, String name, String surname, String usrnm, String pssword, String mail) {
        this.code = code;
        this.msj = reslt;
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

}
