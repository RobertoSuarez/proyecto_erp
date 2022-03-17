/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.controllers;

import com.ventas.dao.ClienteVentaDao;
import com.ventas.dao.DetalleVentaDAO;
import com.ventas.dao.ProductoVentaDAO;
import com.ventas.dao.ProformaDAO;
import com.ventas.dao.VentaDAO;
import com.ventas.models.ClienteVenta;
import com.ventas.models.DetalleProforma;
import com.ventas.models.DetalleVenta;
import com.ventas.models.ProductoVenta;
import com.ventas.models.Proforma;
import com.ventas.models.Venta;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author jaime
 */
@Named(value = "ListaProformaMB")
@ViewScoped
public class ListaProformaManagedBean implements Serializable {
    private ProformaDAO proformaDao;
    private Proforma proformaActual;
    private List<Proforma> listaProformas;
    private List<DetalleProforma> listaDetalle;
    private ClienteVenta cliente;
    private ClienteVentaDao clienteDao;
    private ProductoVentaDAO productoDao;
    private boolean productosCompletos;
    private List<DetalleProforma> listaProductosFaltantes;

    public ListaProformaManagedBean() {
        this.proformaDao = new ProformaDAO();
        this.proformaDao = new ProformaDAO();
        
        this.listaDetalle = new ArrayList<>();
        this.listaProformas = new ArrayList<>();
        
        this.productoDao = new ProductoVentaDAO();
        this.listaProductosFaltantes = new ArrayList<>();
        productosCompletos = true;
        
        cargarProformas();
    }
    
    private void cargarProformas(){
        try{
            this.listaProformas = proformaDao.retornarProformas();
        }catch(Exception e){
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
        }
    }
    
    public void cargarDatosProforma(Proforma prf){
        this.proformaActual = proformaDao.getProformaById(prf.getId_proforma());
        System.out.println(prf.getId_proforma());
        this.listaDetalle = proformaDao.getDetalleProforma(proformaActual.getId_proforma());
    }
    
    public void revisarProforma(Proforma prf){
        for(DetalleProforma det: listaDetalle){
            ProductoVenta aux = productoDao.ObtenerProducto(det.getCodigoProducto());
            if(det.getCantidad() < aux.getStock()){
                this.listaProductosFaltantes.add(det);
                this.productosCompletos = false;
            }
        }
        
        if(this.productosCompletos){
            PrimeFaces.current().executeScript("PF('').show();");
        }else{
            PrimeFaces.current().executeScript("PF('').show();");
        }
    }
    
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    public double convertTwoDecimal(double doubleNumero) {
        double temp = new BigDecimal(doubleNumero).setScale(3, RoundingMode.HALF_UP).doubleValue();
        return temp < 0 ? 0 : temp;
    }

    public Proforma getProformaActual() {
        return proformaActual;
    }

    public void setProformaActual(Proforma proformaActual) {
        this.proformaActual = proformaActual;
    }

    public List<Proforma> getListaProformas() {
        return listaProformas;
    }

    public void setListaProformas(List<Proforma> listaProformas) {
        this.listaProformas = listaProformas;
    }

    public List<DetalleProforma> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<DetalleProforma> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public ClienteVenta getCliente() {
        return cliente;
    }

    public void setCliente(ClienteVenta cliente) {
        this.cliente = cliente;
    }

    public List<DetalleProforma> getListaProductosFaltantes() {
        return listaProductosFaltantes;
    }

    public void setListaProductosFaltantes(List<DetalleProforma> listaProductosFaltantes) {
        this.listaProductosFaltantes = listaProductosFaltantes;
    }
    
    
}
