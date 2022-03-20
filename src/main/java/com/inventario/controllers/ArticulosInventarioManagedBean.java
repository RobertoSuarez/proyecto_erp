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
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

//(name = "articulosMB")
@Named(value = "articulosMB")
@ViewScoped

public class ArticulosInventarioManagedBean implements Serializable {

    private int preparando;
    private ArticulosInventario articulosInventario;
    private ArticulosInventarioDAO articulosInventarioDAO = new ArticulosInventarioDAO();
    private List<ArticulosInventario> listaArticulos = new ArrayList<>();
    private List<ArticulosInventario> listaServicios = new ArrayList<>();
    private List<ArticulosInventario> listaDatosArticulos;
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
    
    private boolean visible;
    private boolean isIva;
    private boolean iStockeable;

    private List<ArticulosInventario> listaIva = new ArrayList<>();
    private List<ArticulosInventario> listaUnidades_medidas = new ArrayList<>();
    private List<ArticulosInventario> listaIce = new ArrayList<>();

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

    public List<ArticulosInventario> getListaIce() {
        return listaIce;
    }

    public void setListaIce(List<ArticulosInventario> listaIce) {
        this.listaIce = listaIce;
    }

    public List<ArticulosInventario> getListaUnidades_medidas() {
        return listaUnidades_medidas;
    }

    public void setListaUnidades_medidas(List<ArticulosInventario> listaUnidades_medidas) {
        this.listaUnidades_medidas = listaUnidades_medidas;
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

    public List<ArticulosInventario> getListaIva() {
        return listaIva;
    }

    public void setListaIva(List<ArticulosInventario> listaIva) {
        this.listaIva = listaIva;
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
        articulosInventario = new ArticulosInventario();
        this.preparando = 0;
        System.out.println("PostConstruct");
        listaArticulos = articulosInventarioDAO.getArticulos();
        listaServicios = articulosInventarioDAO.getServices();
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
        visible = false;
        iStockeable = true;
        listaDatosArticulos = new ArrayList<>();
        listaIva = articulosInventarioDAO.getPorcentajesIVA();
        listaUnidades_medidas = articulosInventarioDAO.getUnidades_medida();
        listaIce = articulosInventarioDAO.getimpuestos_ice();

    }

    public void seleccionarCategoria(Categoria c) {
        this.categoria = c;

        this.articulosInventario.setId_categoria(c.getIdCat());
        int preparando = 5;

    }

    public List<ArticulosInventario> getListaDatosArticulos() {
        return listaDatosArticulos;
    }

    public void setListaDatosArticulos(List<ArticulosInventario> listaDatosArticulos) {
        this.listaDatosArticulos = listaDatosArticulos;
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

    public void limpiarProductos() {
        articulosInventario = new ArticulosInventario();
        articulosInventarioDAO = new ArticulosInventarioDAO();
        listaArticulos = articulosInventarioDAO.getArticulos();

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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isiStockeable() {
        return iStockeable;
    }

    public void setiStockeable(boolean iStockeable) {
        this.iStockeable = iStockeable;
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

    public List<ArticulosInventario> getListaServicios() {
        return listaServicios;
    }

    public void setListaServicios(List<ArticulosInventario> listaServicios) {
        this.listaServicios = listaServicios;
    }

    public void insertarArticulos() {

        try {
            if ("".equals(articulosInventario.getNombre())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese el nombre del producto"));
            } else if (articulosInventario.getCat_cod() < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese todos los campos"));
            } else if (articulosInventario.getId_tipo() < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese todos los campos"));
            } else if ("".equals(articulosInventario.getDescripcion())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese todos los campos"));
            } else if (articulosInventario.getId_bodega() < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese todos los campos"));;
            } else if (articulosInventario.getIdunidad()< 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese todos los campos"));
            } else if (articulosInventario.getIdIva()< 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese todos los campos"));
            } else if (articulosInventario.getIdice() < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese todos los campos"));
            } else if (articulosInventario.getCantidad() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese todos los campos"));
            } else if (articulosInventario.getCoast() == 0.00) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese todos los campos"));
            } else {
                if(articulosInventario.getId_tipo()==3){
                    this.articulosInventarioDAO.insertMateriaPrima(articulosInventario);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Producto Agregado"));
                    PrimeFaces.current().executeScript("PF('newProduct').hide()");
                    limpiarProductos();
                }
                else if(articulosInventario.getId_tipo()==1){
                    this.articulosInventarioDAO.insertProductoTerminado(articulosInventario);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Producto Agregado"));
                    PrimeFaces.current().executeScript("PF('newProduct').hide()");
                    limpiarProductos();
                }
                else if(articulosInventario.getId_tipo()==2){
                    this.articulosInventarioDAO.insertProductoenProceso(articulosInventario);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Producto Agregado"));
                    PrimeFaces.current().executeScript("PF('newProduct').hide()");
                    limpiarProductos();
                }
                else if(articulosInventario.getId_tipo()==5){
                    this.articulosInventarioDAO.insertSuministros(articulosInventario);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Producto Agregado"));
                    PrimeFaces.current().executeScript("PF('newProduct').hide()");
                    limpiarProductos();
                }
                else if(articulosInventario.getId_tipo()==6){
                    this.articulosInventarioDAO.insertInsumos(articulosInventario);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Producto Agregado"));
                    PrimeFaces.current().executeScript("PF('newProduct').hide()");
                    limpiarProductos();
                }

            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                            "Error al guardar"));
        }

    }

    public void insertarServicioStockeable() {

        if (isIva) {
            try {
                if (articulosInventario.getIdSubCuenta() < 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Seleccione una subcuenta"));
                } else if ("".equals(articulosInventario.getNombre())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese el nombre del servicio"));
                } else if ("".equals(articulosInventario.getDescripcion())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese una descripción del servicio"));
                } else if (articulosInventario.getCantidad() == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese la cantidad limitada del servicio"));
                } else if (articulosInventario.getCoast() == 0.00) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese el costo del servicio"));
                } else {
                    this.articulosInventarioDAO.insertServicioStockeableIva(articulosInventario, true);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Servicio Agregado"));
                    PrimeFaces.current().executeScript("PF('newServiceStockeable').hide()");
                    limpiarProductos();
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                "Error al guardar"));
            }
        } else {

            this.articulosInventarioDAO.insertServicioStockeablesinIva(articulosInventario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Producto Agregado"));
            PrimeFaces.current().executeScript("PF('newServiceStockeable').hide()");
            limpiarProductos();
            limpiarProductos();
        }
    }

    public void insertarServicionoStockeable() {

        if (isIva) {
            try {
                if (articulosInventario.getIdSubCuenta() < 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Seleccione una subcuenta"));
                } else if ("".equals(articulosInventario.getNombre())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ingrese el nombre del servicio"));
                } else if ("".equals(articulosInventario.getDescripcion())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese una descripción del servicio"));
                } else if (articulosInventario.getCoast() == 0.00) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Por favor ingrese el costo del servicio"));
                } else {
                    this.articulosInventarioDAO.insertServicioNoStockeablesIva(articulosInventario, true);

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Servicio Agregado"));
                    PrimeFaces.current().executeScript("PF('newServiceNoStockeable').hide()");
                    limpiarProductos();
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                "Error al guardar"));
            }
        } else {

            this.articulosInventarioDAO.insertServicioNoStockeablesinIva(articulosInventario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Producto Agregado"));
            PrimeFaces.current().executeScript("PF('newServiceNoStockeable').hide()");
            limpiarProductos();
            limpiarProductos();
        }
    }

    public void CargarInfoProducto(ArticulosInventario articulosInventario) {
        int cod = articulosInventario.getId();
        this.articulosInventario.setId(articulosInventario.getId());
        this.articulosInventario.setNombre(articulosInventario.getNombre());
        this.articulosInventario.setNom_categoria(articulosInventario.getNom_categoria());
        this.articulosInventario.setTipoP(articulosInventario.getTipoP());
        this.articulosInventario.setDescripcion(articulosInventario.getDescripcion());
        this.articulosInventario.setNomBodega(articulosInventario.getNomBodega());
        this.articulosInventario.setUnidad_medida(articulosInventario.getUnidad_medida());
        this.articulosInventario.setValor(articulosInventario.getValor());
        this.articulosInventario.setPorcentaje_ice(articulosInventario.getPorcentaje_ice());
        this.articulosInventario.setCantidad(articulosInventario.getCantidad());
        this.articulosInventario.setCoast(articulosInventario.getCoast());
        this.articulosInventario.setNom_subcuenta(articulosInventario.getNom_subcuenta());
        listaDatosArticulos = articulosInventarioDAO.obtenerDatos(cod);
    }

    public void reset() {
        PrimeFaces.current().resetInputs("articulos:productosnew");
        listaArticulos.clear();
    }

    public void isStockeable() {
        if (iStockeable) {
            visible = true;
        } else {
            visible = false;
        }
    }

}
