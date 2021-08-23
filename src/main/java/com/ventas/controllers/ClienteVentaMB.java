/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.controllers;

import com.ventas.models.ClienteVenta;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


@Named(value = "ClienteMB")
@SessionScoped
public class ClienteVentaMB implements Serializable {
    private ClienteVenta cliente;
    
    public ClienteVentaMB(){
        this.cliente = new ClienteVenta();
        System.out.print("Persona MB");
    }

    public ClienteVenta getCliente() {
        return cliente;
    }

    public void setCliente(ClienteVenta cliente) {
        this.cliente = cliente;
    }
    
    
    
}
