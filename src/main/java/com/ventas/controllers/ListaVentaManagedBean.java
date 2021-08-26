/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.controllers;

import com.ventas.dao.VentaDAO;
import com.ventas.models.Venta;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Andres
 */
@Named(value = "ListaVentaMB")
@ViewScoped
public class ListaVentaManagedBean implements Serializable {

    private VentaDAO ventaDao;
    private List<Venta> listaVentas;

    public ListaVentaManagedBean() throws SQLException {
        this.listaVentas = new ArrayList<>();
        ObtenerTodasVentas();
    }

    public void ObtenerTodasVentas() throws SQLException {
        try {
            VentaDAO vd = new VentaDAO();
            this.listaVentas = vd.TodasVentas();
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }

    public List<Venta> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

}
