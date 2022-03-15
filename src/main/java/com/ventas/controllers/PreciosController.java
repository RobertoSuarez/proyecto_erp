/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ventas.controllers;

import com.ventas.dao.PreciosDAO;
import com.ventas.models.Precios;
import com.ventas.models.ProductoVenta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author ninat
 */
@Named(value = "PreciosMB")
@ViewScoped
public class PreciosController implements Serializable {

    private Precios precios;
    private ProductoVenta producto;
    private List<Precios> listaPrecios;
    private List<SelectItem> tiposClientes;
    private List<ProductoVenta> listaProduc = new ArrayList<>();
    private List<ProductoVenta> aux;
    private PreciosDAO preciosDAO;
    private String tipos;
    private String aplicaTodos;
    private int codigoProducto;
    private String nombreProducto;

    public PreciosController() {
        precios = new Precios();
        preciosDAO = new PreciosDAO();
        producto = new ProductoVenta();
        listaPrecios = new ArrayList<>();
        tiposClientes = new ArrayList<>();
        listaPrecios = preciosDAO.mostrarPrecios();
        aplicaTodos = "true";
    }

    public void abrir() {
        this.precios = new Precios();
        tiposClientes.clear();
        tiposClientes = preciosDAO.llenarTipos();
        precios.setIdprecio(0);
    }

    public void productos() {
        boolean f = true;
        for (int i = 0; i < this.listaProduc.size(); i++) {
            if (this.listaProduc.get(i).getCodigo() == getCodigoProducto()) {
                f = false;
            }
        }
        if (f) {
            ProductoVenta p = new ProductoVenta();
            p.setCodigo(getCodigoProducto());
            p.setDescripcion(getNombreProducto());
            this.listaProduc.add(p);
            PrimeFaces.current().executeScript("PF('moviePicker').hide()");
            PrimeFaces.current().ajax().update(":form:dt-productos");
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El producyo ya ha sido ingresado"));
            PrimeFaces.current().ajax().update(":form:messages");
        }
    }

    public void cargardatos(Precios p) {
        this.precios = new Precios();
        tiposClientes.clear();
        tiposClientes = preciosDAO.llenarTipos();
        precios.setIdtipocliente(p.getIdtipocliente());
        precios.setDescuento(p.getDescuento());
        precios.setIdprecio(1);
        if (preciosDAO.opciones(p.getIdtipocliente())) {
            aplicaTodos = "true";
        } else {
            aplicaTodos = "false";
            listaProduc.clear();
            listaProduc = preciosDAO.llenarProducto(p.getIdtipocliente());
        }
    }

    public String visible() {
        if ("false".equals(aplicaTodos)) {
            return "visible";
        }
        return "hidden";
    }

    public String render() {
        System.out.println("RERNDER");
        return aplicaTodos;
    }

    public Precios getPrecios() {
        return precios;
    }

    public void setPrecios(Precios precios) {
        this.precios = precios;
    }

    public List<Precios> getListaPrecios() {
        return listaPrecios;
    }

    public void setListaPrecios(List<Precios> listaPrecios) {
        this.listaPrecios = listaPrecios;
    }

    public List<SelectItem> getTiposClientes() {
        return tiposClientes;
    }

    public void setTiposClientes(List<SelectItem> tiposClientes) {
        this.tiposClientes = tiposClientes;
    }

    public List<ProductoVenta> getListaProductos() {
        return listaProduc;
    }

    public void setListaProductos(List<ProductoVenta> listaProductos) {
        this.listaProduc = listaProductos;
    }

    public ProductoVenta getProducto() {
        return producto;
    }

    public void setProducto(ProductoVenta producto) {
        this.producto = producto;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getTipos() {
        return tipos;
    }

    public void setTipos(String tipos) {
        this.tipos = tipos;
    }

    public String getAplicaTodos() {
        return aplicaTodos;
    }

    public void setAplicaTodos(String aplicaTodos) {
        this.aplicaTodos = aplicaTodos;
    }

    public PreciosDAO getPreciosDAO() {
        return preciosDAO;
    }

    public void setPreciosDAO(PreciosDAO preciosDAO) {
        this.preciosDAO = preciosDAO;
    }

    public void guardar() {
        if (precios.getIdprecio() == 0) {
            insertar();
        } else {
            editar();
        }
    }

    public void insertar() {
        if (validarSelect(precios)) {
            if (aplicaTodos.equals("true")) {
                if (preciosDAO.insertarGeneral(precios) == 0) {
                    Exito();
                }
            } else if (listaProduc.isEmpty()) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertenica", "Por favor ingrese productos a la tabla"));
            } else {
                if (preciosDAO.insertProduct(precios, this.listaProduc) == 0) {
                    Exito();
                } else {
                    Error();
                }
            }
        }
    }

    public void editar() {
        if (validarSelect(precios)) {
            if (aplicaTodos.equals("true")) {
                if (preciosDAO.deleteProduct(precios.getIdtipocliente()) > 0) {
                    if (preciosDAO.update(precios, aplicaTodos.equals("true")) == 0) {
                        Exito();
                    }
                }
            } else if (listaProduc.isEmpty()) {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertenica", "Por favor ingrese productos a la tabla"));
            } else {
                if (preciosDAO.deleteProduct(precios.getIdtipocliente()) > 0) {
                    if (preciosDAO.update(precios, aplicaTodos.equals("true")) == 0) {
                        if (preciosDAO.insertProduct(precios, this.listaProduc) == 0) {
                            Exito();
                        } else {
                            Error();
                        }
                    } else {
                        Error();
                    }
                }
            }
        }
    }

    public boolean validarSelect(Precios precios) {
        if (precios.getIdtipocliente() >= 1) {
            return true;
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertenica", "Por favor seleccione un tipo de cliente"));
            return false;
        }
    }

    public boolean validarRadio() {
        if (true) {
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Advertenica", "Por favor seleccione la opción a aplicar"));
            return false;
        }
        return true;
    }

    public void reset() {
        PrimeFaces.current().resetInputs("form:outputnuevo");
        removeSessionScopedBean("preciosMB");
    }

    public void Exito() {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Se ha ingresado correctamente"));
        listaPrecios.clear();
        listaPrecios = preciosDAO.mostrarPrecios();
        PrimeFaces.current().executeScript("PF('newLista').hide()");
        PrimeFaces.current().ajax().update(":form:dt-precio");
    }

    public void Error() {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertenica", "Ha ocurrido un error intente nuevamente"));
        PrimeFaces.current().executeScript("PF('newLista').hide()");
        reset();
    }

    public static void removeSessionScopedBean(String beanName) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(beanName);
    }

    public void onRowSelect(SelectEvent<ProductoVenta> event) {
        setCodigoProducto(event.getObject().getCodigo());
        setNombreProducto(event.getObject().getDescripcion());
        productos();
    }
}
