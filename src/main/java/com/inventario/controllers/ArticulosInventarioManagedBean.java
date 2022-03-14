/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.controllers;

import com.contabilidad.models.SubCuenta;
import com.inventario.DAO.ArticulosInventarioDAO;
import com.inventario.DAO.BodegaDAO;
import com.inventario.DAO.CategoriaDAO;
import com.inventario.DAO.TipoDAO;
import com.inventario.models.ArticulosInventario;
import com.inventario.models.Bodega;
import com.inventario.models.Categoria;
import com.inventario.models.Tipo;
import static com.primefaces.Messages.showWarn;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "articulosMB")

@ViewScoped

public class ArticulosInventarioManagedBean implements Serializable {

    private int preparando;
    private ArticulosInventario articulosInventario = new ArticulosInventario();
    private ArticulosInventarioDAO articulosInventarioDAO = new ArticulosInventarioDAO();
    private List<ArticulosInventario> listaArticulos = new ArrayList<>();
    private Categoria categoria = new Categoria();

    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    private List<Categoria> listaCategoria = new ArrayList<>();
    private int codCategoria;
    private String nomCategoria;
    private Categoria categoriaSeleccionada;

    private TipoDAO tipoDAO = new TipoDAO();
    private Tipo tipo = new Tipo();
    private List<Tipo> listaTipos = new ArrayList<>();

    private SubCuenta subCuenta;
    private List<SubCuenta> listaSubcuenta;
    private int codsubcuenta;
    private String nomSubCuenta;

    private Bodega bodega = new Bodega();
    private List<Bodega> listaBodega = new ArrayList<>();
    private BodegaDAO bodegaDAO = new BodegaDAO();

    private boolean isIva;

    public SubCuenta getSubCuenta() {
        return subCuenta;
    }

    public void setSubCuenta(SubCuenta subCuenta) {
        this.subCuenta = subCuenta;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public BodegaDAO getBodegaDAO() {
        return bodegaDAO;
    }

    public void setBodegaDAO(BodegaDAO bodegaDAO) {
        this.bodegaDAO = bodegaDAO;
    }

    public List<Tipo> getListaTipos() {
        return listaTipos;
    }

    public void setListaTipos(List<Tipo> listaTipos) {
        this.listaTipos = listaTipos;
    }

    public List<SubCuenta> getListaSubcuenta() {
        return listaSubcuenta;
    }

    public void setListaSubcuenta(List<SubCuenta> listaSubcuenta) {
        this.listaSubcuenta = listaSubcuenta;
    }

    public int getCodsubcuenta() {
        return codsubcuenta;
    }

    public void setCodsubcuenta(int codsubcuenta) {
        this.codsubcuenta = codsubcuenta;
    }

    public String getNomSubCuenta() {
        return nomSubCuenta;
    }

    public void setNomSubCuenta(String nomSubCuenta) {
        this.nomSubCuenta = nomSubCuenta;
    }

    public Categoria getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(Categoria categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    public TipoDAO getTipoDAO() {
        return tipoDAO;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public List<Bodega> getListaBodega() {
        return listaBodega;
    }

    public void setListaBodega(List<Bodega> listaBodega) {
        this.listaBodega = listaBodega;
    }

    public void setTipoDAO(TipoDAO tipoDAO) {
        this.tipoDAO = tipoDAO;
    }

    public ArticulosInventarioManagedBean() {
        this.preparando = 0;
        System.out.println("PostConstruct");
        listaArticulos = articulosInventarioDAO.getArticulos();
        this.listaCategoria = categoriaDAO.getCategoria();
        this.categoria = new Categoria();
        this.codCategoria = 0;
        this.nomCategoria = "XXXXXX";
        this.categoriaSeleccionada = null;
        subCuenta = new SubCuenta();
        listaSubcuenta = articulosInventarioDAO.LlenarSubcuentas();
        listaTipos = tipoDAO.getTipoArticulo();
        listaBodega = bodegaDAO.getBodega();
        isIva = true;

    }

    public void seleccionarCategoria(Categoria c) {
        this.categoria = c;

        this.articulosInventario.setId_categoria(c.getIdCat());
        int preparando = 5;

    }

    public void insertararticulo() {
        int valor = preparando;
        try {
            if ("".equals(articulosInventario.getId_bodega())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else if ("".equals(articulosInventario.getId_tipo())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else if ("".equals(articulosInventario.getId_categoria())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else if ("".equals(articulosInventario.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else if ("".equals(articulosInventario.getCantidad())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else if ("".equals(articulosInventario.getCosto())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese un Identificador"));
            } else {
                this.articulosInventarioDAO.insertarAriculo(articulosInventario);
            }

            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Datos Agregados"));

            PrimeFaces.current().executeScript("PF('productosNew').hide()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }
    }

    public ArticulosInventario getArticulosInventario() {
        return articulosInventario;
    }

    public void setArticulosInventario(ArticulosInventario articulosInventario) {
        this.articulosInventario = articulosInventario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public CategoriaDAO getCategoriaDAO() {
        return categoriaDAO;
    }

    public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public List<Categoria> getListaCategoria() {
        return listaCategoria;
    }

    public void setListaCategoria(List<Categoria> listaCategoria) {
        this.listaCategoria = listaCategoria;
    }

    public int getCodCategoria() {
        return codCategoria;
    }

    public boolean isIsIva() {
        return isIva;
    }

    public void setIsIva(boolean isIva) {
        this.isIva = isIva;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getNomCategoria() {
        return nomCategoria;
    }

    public void setNomCategoria(String nomCategoria) {
        this.nomCategoria = nomCategoria;
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

    public void insertarArticulos() {
                articulosInventarioDAO.insertArticulos(articulosInventario);
                FacesContext.getCurrentInstance().
                 addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Producto Agregado"));
    }

}
