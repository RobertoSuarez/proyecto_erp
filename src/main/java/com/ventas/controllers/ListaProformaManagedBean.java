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
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
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
    private List<Proforma> listaProformasPendientes;
    private List<Proforma> listaProformasAprobadas;
    private List<DetalleProforma> listaDetalle;
    private ClienteVenta cliente;
    private ClienteVentaDao clienteDao;
    private ProductoVentaDAO productoDao;
    private boolean productosCompletos;
    private List<DetalleProforma> listaProductosFaltantes;
    private int diasPago;

    public ListaProformaManagedBean() {
        this.proformaDao = new ProformaDAO();
        this.proformaDao = new ProformaDAO();

        this.listaDetalle = new ArrayList<>();
        this.listaProformasPendientes = new ArrayList<>();
        this.listaProformasAprobadas = new ArrayList<>();

        this.productoDao = new ProductoVentaDAO();
        this.listaProductosFaltantes = new ArrayList<>();
        productosCompletos = true;

        this.diasPago = 15;

        cargarProformas();
    }

    private void cargarProformas() {
        try {
            this.listaProformasPendientes = proformaDao.retornarProformasPendientes();
            this.listaProformasAprobadas = proformaDao.retornarProformasAprobadas();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
        }
    }

    public void cargarDatosProforma(Proforma prf) throws SQLException {
        this.proformaActual = proformaDao.getProformaById(prf.getId_proforma());
        this.cliente = new ClienteVentaDao().BuscarClientePorId(proformaActual.getId_cliente());
        System.out.println(prf.getId_proforma());
        this.listaDetalle = proformaDao.getDetalleProforma(proformaActual.getId_proforma());
    }

    public void facturarProforma(int formaPago) throws IOException {
        int idVenta;
        VentaDAO ventaDao = new VentaDAO();
        idVenta = ventaDao.facturarProforma(proformaActual, formaPago, formaPago == 1 ? 0 : diasPago);

        if (idVenta != 0) {
            for (DetalleProforma det : listaDetalle) {
                proformaDao.setProformaFacturada(proformaActual.getId_proforma());
                int codProd = det.getProducto().getCodigo();
                double qty = convertTwoDecimal(det.getCantidad());
                double dsc = convertTwoDecimal(det.getDescuento());
                double price = convertTwoDecimal(det.getPrice());
                double sbttl = convertTwoDecimal(det.getSubtotal());
                DetalleVentaDAO daoDetail = new DetalleVentaDAO();
                if (det.getProducto().isStockeable()) {
                    daoDetail.RegistrarProductos(idVenta, codProd, qty, dsc, price, sbttl);
                } else {
                    daoDetail.RegistrarProductosNoStockeable(idVenta, codProd, qty, dsc, price, sbttl);
                }
            }
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado", "Compra Guardada satisfactoriamente"));
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.getExternalContext().redirect("/proyecto_erp/View/ventas/listaVentas.xhtml?faces-redirect=true");
        }
    }

    public void aceptarProforma(Proforma prf) {
        this.listaProformasAprobadas.clear();
        this.listaProformasPendientes.clear();
        this.listaProformasPendientes = new ArrayList<>();
        this.listaProformasAprobadas = new ArrayList<>();
        proformaDao.aceptarProforma(prf.getId_proforma());
        this.listaProformasAprobadas = proformaDao.retornarProformasAprobadas();
        this.listaProformasPendientes = proformaDao.retornarProformasPendientes();
    }

    public void rechazarProforma(Proforma prf) {
        this.listaProformasAprobadas.clear();
        this.listaProformasPendientes.clear();
        this.listaProformasPendientes = new ArrayList<>();
        this.listaProformasAprobadas = new ArrayList<>();
        proformaDao.rechazarProforma(prf.getId_proforma());
        this.listaProformasAprobadas = proformaDao.retornarProformasAprobadas();
        this.listaProformasPendientes = proformaDao.retornarProformasPendientes();
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

    public List<Proforma> getListaProformasPendientes() {
        return listaProformasPendientes;
    }

    public void setListaProformasPendientes(List<Proforma> listaProformasPendientes) {
        this.listaProformasPendientes = listaProformasPendientes;
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

    public List<Proforma> getListaProformasAprobadas() {
        return listaProformasAprobadas;
    }

    public void setListaProformasAprobadas(List<Proforma> listaProformasAprobadas) {
        this.listaProformasAprobadas = listaProformasAprobadas;
    }

    public int getDiasPago() {
        return diasPago;
    }

    public void setDiasPago(int diasPago) {
        this.diasPago = diasPago;
    }

}
