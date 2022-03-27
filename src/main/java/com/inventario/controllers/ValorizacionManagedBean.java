/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.controllers;

import com.cuentasporpagar.models.Proveedor;
import com.inventario.DAO.ArticulosInventarioDAO;
import com.inventario.DAO.BodegaDAO;
import com.inventario.DAO.ValorizacionInventarioDAO;
import com.inventario.models.ArticulosInventario;
import com.inventario.models.Bodega;
import com.inventario.models.ValorizacionInventario;
import com.inventario.report.ValorizacionReport;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author angul
 */

@Named(value = "ValorizacionMB")
@ViewScoped
public class ValorizacionManagedBean implements Serializable {
    


    private Bodega bodega;
    private BodegaDAO bodegaDAO;
    private int codBodega;
    private String nombreBodega;
    private String direccionBodega;
    private String ciudadBodega;
    
    
    private Date fechaImpresion;


    
    private ValorizacionInventario valorizacionInventario;
    private ValorizacionInventarioDAO valorizacionInventarioDAO;


    private Bodega bodegaSeleccionada;

    private ArticulosInventarioDAO productoDao;
    private ArticulosInventario producto;
    private int codigoProducto;
    private String nombreProducto;
    private float precioProducto;
    private double subTotalEntrada;

    public double getPrecioPromedio() {
        return precioPromedio;
    }

    public void setPrecioPromedio(double precioPromedio) {
        this.precioPromedio = precioPromedio;
    }
    private double precioPromedio;

    


    private List<Proveedor> listaProveedores;
    private List<ArticulosInventario> listaProductos;
    private List<Bodega> listaBodegas;
    
    private List<ValorizacionInventario> listaValorizacion;

 
    //Constructor
    @PostConstruct
    public void ValorizacionManagedBean() {

 //       this.productosReport = daoReport.getArticulosReport();
        this.precioPromedio = 0;
        this.nombreCategoria = "XXXX";



        this.bodega = new Bodega();
        this.bodegaDAO = new BodegaDAO();


        this.listaBodegas = bodegaDAO.getBodega();

        this.bodegaSeleccionada = null;

        
        this.valorizacionInventario = new ValorizacionInventario();
        this.valorizacionInventarioDAO = new ValorizacionInventarioDAO();
        

    }

    //Mostrar algun mensaje
    @Asynchronous
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }




    public void SeleccionarBodega(Bodega bod) {
        this.codBodega = bod.getCod();
        this.nombreBodega = bod.getNomBodega();
        this.ciudadBodega = bod.getNomCiudad();
        this.direccionBodega = bod.getDireccion();
        this.bodega = bod;
        
        this.listaValorizacion = valorizacionInventarioDAO.getValorizacionI(codBodega);
    
        
    }
    
        public JasperPrint  verValorizacion() throws JRException, ParseException {

       int estado = 1;

        JasperPrint jaspert = null;
        if (estado == 1) {



            List<ValorizacionReport> dataset = new ArrayList<>();
            double subtotal = 0.0f;
            double ice = 0.0f;
            double iva = 0.0f;
            double descuento = 0.0f;
            if (this.listaValorizacion.size() > 0) {
                for (ValorizacionInventario inv : this.listaValorizacion) {
                    dataset.add(new ValorizacionReport(String.valueOf(inv.getCodigoTipo()), inv.getNombreTipo(), String.valueOf(inv.getCodigoProducto()),
                            String.valueOf( inv.getNombreProducto()), inv.getNombreUM(),  (int) inv.getCantidadProducto(), String.format("%.2f", inv.getCostoProducto()),
                             inv.getValor() ));


                }
            }

            
            this.fechaImpresion = new Date();
              DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
              String currentDate = df.format(this.fechaImpresion);
              Date currentDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(currentDate);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy",
                    new Locale("es_ES"));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("FECHA", dateFormat.format(currentDate2));

            parametros.put("NOMBREBODEGA", this.bodega.getNomBodega());


            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("PlantillasReportes/valorizacionInventario.jasper"));

            // llenamos la plantilla con los datos.

            jaspert = JasperFillManager.fillReport(
                    filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(dataset));


        }
        return jaspert;

    }

    public void imprimirPDF() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition",
                    "attachment; filename=" + "valorizacion" + ".pdf");
            try ( ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(verValorizacion(), servletOutputStream);
                servletOutputStream.flush();
                FacesContext.getCurrentInstance().responseComplete();
            }
        } catch (Exception ex) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "hola", ex.getMessage()));
        }
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
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

    public int getCodBodega() {
        return codBodega;
    }

    public void setCodBodega(int codBodega) {
        this.codBodega = codBodega;
    }

    public String getDireccionBodega() {
        return direccionBodega;
    }

    public void setDireccionBodega(String direccionBodega) {
        this.direccionBodega = direccionBodega;
    }

    public String getCiudadBodega() {
        return ciudadBodega;
    }

    public void setCiudadBodega(String ciudadBodega) {
        this.ciudadBodega = ciudadBodega;
    }

    public Bodega getBodegaSeleccionada() {
        return bodegaSeleccionada;
    }

    public void setBodegaSeleccionada(Bodega bodegaSeleccionada) {
        this.bodegaSeleccionada = bodegaSeleccionada;
    }

    public List<Bodega> getListaBodegas() {
        return listaBodegas;
    }

    public void setListaBodegas(List<Bodega> listaBodegas) {
        this.listaBodegas = listaBodegas;
    }


    public ArticulosInventarioDAO getProductoDao() {
        return productoDao;
    }

    public void setProductoDao(ArticulosInventarioDAO productoDao) {
        this.productoDao = productoDao;
    }

    public ArticulosInventario getProducto() {
        return producto;
    }

    public void setProducto(ArticulosInventario producto) {
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

    public float getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(float precioProducto) {
        this.precioProducto = precioProducto;
    }

    public double getSubTotalEntrada() {
        return subTotalEntrada;
    }

    public void setSubTotalEntrada(int subTotalEntrada) {
        this.subTotalEntrada = subTotalEntrada;
    }


    public List<Proveedor> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public List<ArticulosInventario> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ArticulosInventario> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    private String nombreCategoria;











}
