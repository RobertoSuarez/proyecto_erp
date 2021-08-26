/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.models;

import java.io.Serializable;

public class Persona implements Serializable {

    //Declaración de las Variables
    private int idTipoIdenficacion;
    private String direccion;
    private String identificacion;
    private boolean estado;
    private String tlf1;
    private String tlf2;
    private String correo;
    //Declaracion de las variables para mostrar clientes
    private int idCliente;
    private String descrIdentificacion;
    private String razonNombre;
    private int idTipoCliente;
    private String descrTipoCliente;
    private String descrEstado;

    public Persona() {
    }

    //Constructor con los datos importantes
    public Persona(int idTipoIdenficacion, String direccion,
            String identificacion, boolean estado, String tlf1,
            String tlf2, String correo, int idTipoCliente) {
        this.idTipoIdenficacion = idTipoIdenficacion;
        this.direccion = direccion;
        this.identificacion = identificacion;
        this.estado = estado;
        this.tlf1 = tlf1;
        this.tlf2 = tlf2;
        this.correo = correo;
        this.idTipoCliente = idTipoCliente;
    }

    //Constructor con los datos que se mostraran en la tabla
    public Persona(int idCliente, String identificacion,
            String descrIdentificacion, String razonNombre,
            String direccion, String tlf1, String tlf2, String correo,
            String descrTipoCliente, String descr_Estado) {
        this.direccion = direccion;
        this.identificacion = identificacion;
        this.tlf1 = tlf1;
        this.tlf2 = tlf2;
        this.correo = correo;
        this.idCliente = idCliente;
        this.descrIdentificacion = descrIdentificacion;
        this.razonNombre = razonNombre;
        this.descrTipoCliente = descrTipoCliente;
        this.descrEstado = descr_Estado;
    }

    //Constructor para la lista de clientes con y sin adeudo
    public Persona(int idCliente, String descrIdentificacion, 
            String razonNombre,String direccion, String tlf1, String tlf2, 
            String correo) {
        this.idCliente = idCliente;
        this.descrIdentificacion = descrIdentificacion;
        this.razonNombre = razonNombre;
        this.direccion = direccion;
        this.tlf1 = tlf1;
        this.tlf2 = tlf2;
        this.correo = correo;
    }
    
    //Constructor con el id_cliente y el nombre de la persona
    public Persona(int id_Cliente, String razon_nombre) {
        this.idCliente = id_Cliente;
        this.razonNombre = razon_nombre;
    }
    
    //Constructor con el id_Cliente, la identificacion y el nombre

    public Persona(String identificacion, int idCliente, String razonNombre) {
        this.identificacion = identificacion;
        this.idCliente = idCliente;
        this.razonNombre = razonNombre;
    }
    
    

    public String createLabel() {

        switch (descrEstado) {

            case "Activo":
                return "SUCCESS";

            case "Inactivo":
                return "FAILURE";

            default:
                return "DEFAULT";
        }
    }

    public int getIdTipoIdenficacion() {
        return idTipoIdenficacion;
    }

    public void setIdTipoIdenficacion(int idTipoIdenficacion) {
        this.idTipoIdenficacion = idTipoIdenficacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getTlf1() {
        return tlf1;
    }

    public void setTlf1(String tlf1) {
        this.tlf1 = tlf1;
    }

    public String getTlf2() {
        return tlf2;
    }

    public void setTlf2(String tlf2) {
        this.tlf2 = tlf2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdTipoCliente() {
        return idTipoCliente;
    }

    public void setIdTipoCliente(int idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }

    public String getDescrIdentificacion() {
        return descrIdentificacion;
    }

    public void setDescrIdentificacion(String descrIdentificacion) {
        this.descrIdentificacion = descrIdentificacion;
    }

    public String getRazonNombre() {
        return razonNombre;
    }

    public void setRazonNombre(String razonNombre) {
        this.razonNombre = razonNombre;
    }

    public String getDescrTipoCliente() {
        return descrTipoCliente;
    }

    public void setDescrTipoCliente(String descrTipoCliente) {
        this.descrTipoCliente = descrTipoCliente;
    }

    public String getDescrEstado() {
        return descrEstado;
    }

    public void setDescrEstado(String descrEstado) {
        this.descrEstado = descrEstado;
    }
}
