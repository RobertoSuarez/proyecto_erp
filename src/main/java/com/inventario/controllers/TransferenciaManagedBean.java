/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.controllers;

import com.inventario.DAO.ArticulosInventarioDAO;
import com.inventario.DAO.BodegaDAO;
import com.inventario.models.ArticulosInventario;
import com.inventario.models.Bodega;
import com.inventario.models.DetalleTransferencia;
import com.inventario.models.EncabezadoTransferencia;
import static com.primefaces.Messages.addMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "transferenciaMB")

@ViewScoped

public class TransferenciaManagedBean implements Serializable {

    private Bodega bodega = new Bodega();
    private EncabezadoTransferencia transferencia= new EncabezadoTransferencia();
    private DetalleTransferencia detalleTransferencia= new DetalleTransferencia(); 
    private BodegaDAO bodegaDAO = new BodegaDAO();
    private List<Bodega> listaBodega = new ArrayList<>();
    private ArticulosInventario articulosInventario = new ArticulosInventario();
    private ArticulosInventarioDAO articulosInventarioDAO = new ArticulosInventarioDAO();
    private List<ArticulosInventario> listaArticulos = new ArrayList<>();
    private int codBodegaOrigen;
    private int codBodegaDestino;
    private String NombreBodegaOrigen;
    private String NombreBodegaDestino;
    private int codArticulo;
    private String nombreArticulo;
    private EncabezadoTransferencia bodegaseleccionada;
    private DetalleTransferencia prodselecionado;
    private List<DetalleTransferencia> listDetalle;

    private int cantidad;

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct");
        this.listaBodega = bodegaDAO.getBodega();
        this.listaArticulos = articulosInventarioDAO.getArticulos();
        this.bodega = new Bodega();
        this.articulosInventario = new ArticulosInventario();
        this.codBodegaOrigen = 0;
        this.codBodegaDestino = 0;
        this.NombreBodegaOrigen = " ";
        this.NombreBodegaDestino = " ";
        this.bodegaseleccionada = null;

        this.codArticulo = 0;
        this.nombreArticulo = "";
        this.prodselecionado = null;

        this.cantidad = 1;
        this.listDetalle= new ArrayList<>();

    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public BodegaDAO getBodegaDAO() {
        return bodegaDAO;
    }

    public void setBodegaDAO(BodegaDAO bodegaDAO) {
        this.bodegaDAO = bodegaDAO;
    }

    public List<Bodega> getListaBodega() {
        return listaBodega;
    }

    public void setListaBodega(List<Bodega> listaBodegas) {
        this.listaBodega = listaBodegas;
    }

    @Asynchronous
    public void SearchBodega() {
        this.bodega = null;
        this.NombreBodegaOrigen = "mmm";

        this.bodega = this.bodegaDAO.obtenerBodega(this.codBodegaOrigen);
        if (this.bodega.getNomBodega() == null) {
            System.out.println("Bodega nula");
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No existe la bodega");

        } else {
            System.out.println("Existe la bodega" + this.NombreBodegaOrigen);
            this.NombreBodegaOrigen = this.bodega.getNomBodega();
        }
    }

    @Asynchronous
    public void AgregarArticulosLista() {
        try {
            if (this.articulosInventario.getCod() > 0) {
                if (this.articulosInventario.getCantidad() < this.cantidad) {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No hay Stock suficiente");
                } else {
                    if (this.cantidad <= 0) {
                        addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Valor invalido");
                    }else{
                        
                        //poner los articulos en el detalle
                        DetalleTransferencia dt=new DetalleTransferencia();
                        dt.setIdDetalle(this.detalleTransferencia.getIdDetalle());
                        dt.setCod_transferencia(this.transferencia.getCodemcabezado());
                        dt.setCod_articulo(this.articulosInventario.getCod());
                        dt.setIdDetalle(codArticulo);
                        dt.setCant(this.cantidad);
                        dt.setCosto(this.articulosInventario.getCosto());
                        
                        this.listDetalle.add(dt);
                       
                    }
                }
            }else{
                 System.out.println("Ningun articulo seleccionado");
            }

        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
        }
    }

    public void SeleccionarBodegaOrigen(Bodega b) {
        this.codBodegaOrigen = b.getCod();
        this.NombreBodegaOrigen = b.getNomBodega();
        this.bodega = b;

    }

    public void SeleccionarBodegaDestino(Bodega b) {
        this.codBodegaDestino = b.getCod();
        this.NombreBodegaDestino = b.getNomBodega();
        this.bodega = b;

    }

    public void SeleccionarArticulo(ArticulosInventario ai) {
        this.codArticulo = ai.getId();
        this.nombreArticulo = ai.getNombre();
        this.articulosInventario = ai;
    }

    public EncabezadoTransferencia getBodegaseleccionada() {
        return bodegaseleccionada;
    }

    public void setBodegaseleccionada(EncabezadoTransferencia bodegaseleccionada) {
        this.bodegaseleccionada = bodegaseleccionada;
    }

    public int getCodBodegaOrigen() {
        return codBodegaOrigen;
    }

    public void setCodBodegaOrigen(int codBodegaOrigen) {
        this.codBodegaOrigen = codBodegaOrigen;
    }

    public int getCodBodegaDestino() {
        return codBodegaDestino;
    }

    public void setCodBodegaDestino(int codBodegaDestino) {
        this.codBodegaDestino = codBodegaDestino;
    }

    public String getNombreBodegaDestino() {
        return NombreBodegaDestino;
    }

    public void setNombreBodegaDestino(String NombreBodegaDestino) {
        this.NombreBodegaDestino = NombreBodegaDestino;
    }

    public String getNombreBodegaOrigen() {
        return NombreBodegaOrigen;
    }

    public void setNombreBodegaOrigen(String NombreBodegaOrigen) {
        this.NombreBodegaOrigen = NombreBodegaOrigen;
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

    public int getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(int codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public DetalleTransferencia getProdselecionado() {
        return prodselecionado;
    }

    public void setProdselecionado(DetalleTransferencia prodselecionado) {
        this.prodselecionado = prodselecionado;
    }

    public EncabezadoTransferencia getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(EncabezadoTransferencia transferencia) {
        this.transferencia = transferencia;
    }

    public DetalleTransferencia getDetalleTransferencia() {
        return detalleTransferencia;
    }

    public void setDetalleTransferencia(DetalleTransferencia detalleTransferencia) {
        this.detalleTransferencia = detalleTransferencia;
    }

    public List<DetalleTransferencia> getListDetalle() {
        return listDetalle;
    }

    public void setListDetalle(List<DetalleTransferencia> listDetalle) {
        this.listDetalle = listDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    

}
