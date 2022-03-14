/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.controllers;

import com.inventario.DAO.EntradaProductoDao;
import com.inventario.models.EntradaProducto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Lister Leonardo
 */
@ManagedBean(name = "entradaProductosMB")
@ViewScoped
public class EntradaProductoManagedBean implements Serializable {

    private EntradaProductoDao entradaProductoDao;
    private EntradaProducto entradaProducto;
    private float datoCostoVenta;
    private List<EntradaProducto> ListaEntrada;
    private List<EntradaProducto> ListaDetalle;
    private boolean check;

    public EntradaProductoManagedBean() {
        entradaProductoDao = new EntradaProductoDao();
        entradaProducto = new EntradaProducto();
        ListaEntrada = new ArrayList<>();
        ListaDetalle = new ArrayList<>();
        this.ListaEntrada.clear();
        this.ListaEntrada = this.entradaProductoDao.ListarEntradas();
    }

    public EntradaProductoDao getEntradaProductoDao() {
        return entradaProductoDao;
    }

    public void setEntradaProductoDao(EntradaProductoDao entradaProductoDao) {
        this.entradaProductoDao = entradaProductoDao;
    }

    public EntradaProducto getEntradaProducto() {
        return entradaProducto;
    }

    public float getDatoCostoVenta() {
        return datoCostoVenta;
    }

    public void setDatoCostoVenta(float datoCostoVenta) {
        this.datoCostoVenta = datoCostoVenta;
    }
    
    public void setEntradaProducto(EntradaProducto entradaProducto) {
        this.entradaProducto = entradaProducto;
    }

    public List<EntradaProducto> getListaDetalle() {
        return ListaDetalle;
    }

    public void setListaDetalle(List<EntradaProducto> ListaDetalle) {
        this.ListaDetalle = ListaDetalle;
    }

    public List<EntradaProducto> getListaEntrada() {
        return ListaEntrada;
    }

    public void setListaEntrada(List<EntradaProducto> ListaEntrada) {
        this.ListaEntrada = ListaEntrada;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    //cargar los datos
    public void cargarDatos(EntradaProducto entradaProducto) {
        String dato = entradaProducto.getNum_comprobante();
        this.entradaProducto.setNum_comprobante(entradaProducto.getNum_comprobante());
        this.entradaProducto.setFecha(entradaProducto.getFecha());
        this.entradaProducto.setNombre_proveedor(entradaProducto.getNombre_proveedor());
        this.entradaProducto.setNombreBodega(entradaProducto.getNombreBodega());
        this.entradaProducto.setNombre_producto(entradaProducto.getNombre_producto());
        this.entradaProducto.setDetalleProduto(entradaProducto.getDetalleProduto());
        this.entradaProducto.setCantidad(entradaProducto.getCantidad());
        this.entradaProducto.setCosto(entradaProducto.getCosto());
        datoCostoVenta=0;
        ListaDetalle.clear();
        ListaDetalle = entradaProductoDao.llenarDetalle(dato);

    }
     public void reset() {
        PrimeFaces.current().resetInputs("form:outputvisu, form:dt-detalle");
        ListaDetalle.clear();
    }
     
    
}
