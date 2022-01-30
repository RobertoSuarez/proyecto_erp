/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.controllers;

import com.inventario.DAO.ArticulosInventarioDAO;
import com.inventario.models.ArticulosInventario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Jimmy
 */

@ManagedBean(name ="articulosMB")

@ViewScoped

public class ArticulosInventarioManagedBean  implements Serializable{
    
    private ArticulosInventario articulosInventario = new ArticulosInventario();
    private ArticulosInventarioDAO articulosInventarioDAO = new ArticulosInventarioDAO();
    private List<ArticulosInventario> listaArticulos = new ArrayList<>();
    
    @PostConstruct
    public void init(){
    System.out.println("PostConstruct");
    listaArticulos = articulosInventarioDAO.getArticulos();
    }

    public ArticulosInventario getArticulosInventario() {
        return articulosInventario;
    }

    public void setArticulosInventario(ArticulosInventario articulosInventario) {
        this.articulosInventario = articulosInventario;
    }

    public ArticulosInventarioDAO getArticulosInventarioDAO() {
        return articulosInventarioDAO;
    }

    public void setArticulosInventarioDAO(ArticulosInventarioDAO articulosInventarioDAO) {
        this.articulosInventarioDAO = articulosInventarioDAO;
    }

    public List<ArticulosInventario> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<ArticulosInventario> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }
 
    
}
