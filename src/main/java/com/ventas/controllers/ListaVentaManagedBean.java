/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.controllers;

import com.empresa.global.EmpresaMatrizDAO;
import com.ventas.dao.ClienteVentaDao;
import com.ventas.dao.DetalleVentaDAO;
import com.ventas.dao.VentaDAO;
import com.ventas.models.ClienteVenta;
import com.ventas.models.DetalleVenta;
import com.ventas.models.Venta;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Andres
 */
@Named(value = "ListaVentaMB")
@ViewScoped
public class ListaVentaManagedBean implements Serializable {

    private VentaDAO ventaDao;
    private DetalleVentaDAO detalleDao;
    private Venta ventaActual;
    private List<Venta> listaVentas;
    private List<DetalleVenta> listaDetalle;
    private ClienteVenta cliente;
    private ClienteVentaDao clienteDao;

    private String nombreCliente;
    private String identCliente;
    private String contactoCliente;

    public ListaVentaManagedBean() throws SQLException {
        this.listaVentas = new ArrayList<>();
        this.ventaActual = new Venta();
        this.cliente = new ClienteVenta();
        this.listaDetalle = new ArrayList<>();
        this.detalleDao = new DetalleVentaDAO();
        this.identCliente = "######";
        this.nombreCliente = "XXXXXX";
        this.contactoCliente = "0000000000";
        clienteDao = new ClienteVentaDao();
        ObtenerTodasVentas();
    }

    /**
     * Obtiene la lista de las ventas realizadas.
     * @throws SQLException 
     */
    public void ObtenerTodasVentas() throws SQLException {
        try {
            VentaDAO vd = new VentaDAO();
            this.listaVentas = vd.TodasVentas();
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }

    /**
     * Carga una venta, recibida como par??metro, es cargada en pantalla con todos su items y detalles.
     * @param ventaSeleccionada
     * @throws SQLException 
     */
    public void CargarVenta(Venta ventaSeleccionada) throws SQLException {
        this.listaDetalle = new ArrayList<>();
        this.ventaActual = ventaSeleccionada;
        this.listaDetalle = detalleDao.ObtenerDetalleVentas(ventaSeleccionada.getIdVenta());
        this.ventaActual.setTotalFactura(convertTwoDecimal(ventaSeleccionada.getTotalFactura()));

        this.cliente = clienteDao.BuscarClientePorId(ventaSeleccionada.getIdCliente());

        System.out.println();

        if (this.cliente != null) {
            this.identCliente = this.cliente.getIdentificacion();
            this.nombreCliente = this.cliente.getNombre();
        }
    }
    
    public double convertTwoDecimal(double doubleNumero) {
        double temp = new BigDecimal(doubleNumero).setScale(3, RoundingMode.HALF_UP).doubleValue();
        return temp < 0 ? 0 : temp;
    }
    
    public List<Venta> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public List<DetalleVenta> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<DetalleVenta> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public Venta getVentaActual() {
        return ventaActual;
    }

    public void setVentaActual(Venta ventaActual) {
        this.ventaActual = ventaActual;
    }

    public ClienteVenta getCliente() {
        return cliente;
    }

    public void setCliente(ClienteVenta cliente) {
        this.cliente = cliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getIdentCliente() {
        return identCliente;
    }

    public void setIdentCliente(String identCliente) {
        this.identCliente = identCliente;
    }

    public String getContactoCliente() {
        return contactoCliente;
    }

    public void setContactoCliente(String contactoCliente) {
        this.contactoCliente = contactoCliente;
    }

}
