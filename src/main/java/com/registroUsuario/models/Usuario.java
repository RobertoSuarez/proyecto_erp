package com.registroUsuario.models;

import java.util.Date;

public class Usuario{

    int idUsuario;
    String nombre;// limte 50
    String apellido;// limte 50
    String username;// limte 15
    String password;// limte 15
    Date fehcaCreacion;
    boolean habilitado;

    public Usuario() {
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

}
