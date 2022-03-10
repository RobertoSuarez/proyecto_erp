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

    public void ObtenerTodasVentas() throws SQLException {
        try {
            VentaDAO vd = new VentaDAO();
            this.listaVentas = vd.TodasVentas();
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }
    }

    public void CargarVenta(Venta ventaSeleccionada) throws SQLException {
        this.listaDetalle = new ArrayList<>();
        this.ventaActual = ventaSeleccionada;
        this.listaDetalle = detalleDao.ObtenerDetalleVentas(ventaSeleccionada.getIdVenta());
        this.ventaActual.setTotalFactura(new BigDecimal(ventaSeleccionada.getTotalFactura()).setScale(2, RoundingMode.UP).doubleValue());

        this.cliente = clienteDao.BuscarClientePorId(ventaSeleccionada.getIdCliente());

        System.out.println();

        if (this.cliente != null) {
            this.identCliente = this.cliente.getIdentificacion();
            this.nombreCliente = this.cliente.getNombre();
        }

    }
    
    public void exportpdf() throws IOException, JRException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        // Cabecera de la respuesta.
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseHeader("Content-disposition",String.format("attachment; filename=Factura.pdf"));

        // tomamos el stream para llenarlo con el pdf.
        try (OutputStream stream = ec.getResponseOutputStream()) {

            // Parametros para el reporte.
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("nombreempresa", EmpresaMatrizDAO.getEmpresa().getNombre());
            parametros.put("razonsocial", EmpresaMatrizDAO.getEmpresa().getRazonsocial());
            parametros.put("detalle", EmpresaMatrizDAO.getEmpresa().getDetalle());
            parametros.put("numFactura", ventaActual.getFactura());

            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("/PlantillasReportes/FacturaVenta.jasper"));

            // llenamos la plantilla con los datos.
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(this.listaDetalle)
            );

            // exportamos a pdf.
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            //JasperExportManager.exportReportToXmlStream(jasperPrint, outputStream);

            stream.flush();
            stream.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            // enviamos la respuesta.
            fc.responseComplete();
        }
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
