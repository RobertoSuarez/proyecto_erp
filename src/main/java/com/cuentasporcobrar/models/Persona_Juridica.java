/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.models;

import java.time.LocalDate;

public class Persona_Juridica extends Persona{
    
    //Declaración de las Variables
    private String razonSocial;
    private String nomContacto;
    private String cargoContacto;
    private String paginaWeb;
    private LocalDate fechaCreacion;

    public Persona_Juridica() {
    }
    
    public Persona_Juridica(String razonSocial, int idTipoIdenficacion,
                            String direccion, String identificacion,
                            boolean estado, String tlf1, String tlf2, 
                            String correo, int idTipoCliente,String nomContacto,
                            String cargoContacto,String paginaWeb, LocalDate fechaCreacion) {
        super(idTipoIdenficacion, direccion, identificacion, estado, tlf1, tlf2, 
                correo, idTipoCliente);
        
        this.razonSocial = razonSocial;
        this.nomContacto=nomContacto;
        this.cargoContacto=cargoContacto;
        this.cargoContacto=cargoContacto;
        this.fechaCreacion=fechaCreacion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNomContacto() {
        return nomContacto;
    }

    public void setNomContacto(String nomContacto) {
        this.nomContacto = nomContacto;
    }

    public String getCargoContacto() {
        return cargoContacto;
    }

    public void setCargoContacto(String cargoContacto) {
        this.cargoContacto = cargoContacto;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}

