/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.controllers;

import com.contabilidad.models.BalanceGeneral;
import com.cuentasporpagar.daos.ProveedorDAO;
import com.cuentasporpagar.models.Proveedor;
import com.inventario.DAO.ArticulosInventarioDAO;
import com.inventario.DAO.BodegaDAO;
import com.inventario.DAO.EntradaDao;
import com.inventario.DAO.EntradaDetalleDAO;
import com.inventario.DAO.HistoricoPreciosDAO;
import com.inventario.DAO.KardexEntradaSalidaDAO;
import com.inventario.models.ArticulosInventario;
import com.inventario.models.Bodega;
import com.inventario.models.EntradaDetalleInventario;
import com.inventario.models.EntradaInventario;
import com.inventario.models.HistoricoPrecios;
import com.inventario.models.KardexEntradasSalidas;
import com.inventario.report.KardexReport;
import com.inventario.report.ProductoReport;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.math3.util.Precision;

/**
 *
 * @author angul
 */
@Named(value = "KardexMB")
@ViewScoped
public class KardexManagedBean implements Serializable {

    ArticulosInventarioDAO daoReport = new ArticulosInventarioDAO();

    List<ProductoReport> productosReport;

    private HistoricoPrecios historico;
    private HistoricoPreciosDAO historicoDAO;
    
    
    private int idProveedor;

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    private Proveedor proveedor;
    private ProveedorDAO proveedorDAO;
    private String proveedordINum;
    private String proveedorNombre;
    private Boolean SiICE;
    private Boolean SiIVA;
    private Bodega bodega;
    private BodegaDAO bodegaDAO;
    private int codBodega;
    private String nombreBodega;
    private String direccionBodega;
    private String ciudadBodega;
    private String numeroComprobante;
    
    
    private Date fechaImpresion;
    private Date fechaInicio;
    private Date fechaFin;
    private KardexEntradasSalidas kardexEntradasSalidas;
    private KardexEntradaSalidaDAO kardexEntradaSalidaDAO;
   

    private EntradaDetalleInventario productoSeleccionado;
    private Proveedor proveedorSeleccionado;
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
    private EntradaDetalleInventario detalleEntrada;
    private EntradaDetalleDAO detalleDAO;
    private List<EntradaDetalleInventario> listaDetalle;
    private int cantidad;

    private int subtotal12;
    private int subtotal0;
    private int descuento;
    private int ice;
    private int iva;
    private int total;

    private EntradaInventario entrada;
    private EntradaDao entradaDAO;

    private int efectivo;
    private int cambio;
    private int diasPago;

    private List<Proveedor> listaProveedores;
    private List<ArticulosInventario> listaProductos;
    private List<Bodega> listaBodegas;

    private List<EntradaInventario> listaEntradas;
    
    private List<KardexEntradasSalidas> listaKardex;

 
    //Constructor
    @PostConstruct
    public void KardexManagedBean() {

 //       this.productosReport = daoReport.getArticulosReport();
        this.precioPromedio = 0;
        this.historico = new HistoricoPrecios();
        this.historicoDAO = new HistoricoPreciosDAO();
        
        this.idProveedor = 0;
        this.proveedor = new Proveedor();
        this.proveedorDAO = new ProveedorDAO();
        this.nombreCategoria = "XXXX";
        this.numeroComprobante = "";
        aleatorioCod();
        
        

        this.fechaInicio =  new GregorianCalendar(LocalDateTime.now().getYear(), Calendar.JANUARY, 1).getTime();
        this.fechaFin = new GregorianCalendar(LocalDateTime.now().getYear(), Calendar.DECEMBER, 31).getTime();;
        this.bodega = new Bodega();
        this.bodegaDAO = new BodegaDAO();

        this.producto = new ArticulosInventario();
        this.productoDao = new ArticulosInventarioDAO();
        this.codigoProducto = 0;
        this.nombreProducto = "XXXXXX";
        this.subTotalEntrada = 0;

        this.subtotal12 = 0;
        this.subtotal0 = 0;
        this.descuento = 0;
        this.ice = 0;
        this.iva = 0;
        this.total = 0;

        this.listaDetalle = new ArrayList<>();
        this.cantidad = 1;

        this.entrada = new EntradaInventario();
        this.entradaDAO = new EntradaDao();

        this.listaEntradas = entradaDAO.getEntradas();
        this.listaBodegas = bodegaDAO.getBodega();
        this.productoSeleccionado = null;
        this.proveedorSeleccionado = null;
        this.bodegaSeleccionada = null;

        this.efectivo = 0;
        this.cambio = 0;
        this.diasPago = 0;

        this.proveedorDAO = new ProveedorDAO();
        this.detalleDAO = new EntradaDetalleDAO();
        this.listaProveedores = new ArrayList<>();
        this.listaProveedores = proveedorDAO.ListarProveedor();
        
        
        
        
        this.kardexEntradasSalidas = new KardexEntradasSalidas();
        this.kardexEntradaSalidaDAO = new KardexEntradaSalidaDAO();
        this.listaKardex = new ArrayList<>();
        
        this.listaProductos = productoDao.getArticulosEntradasSalidas();

        this.SiICE = false;
        this.SiIVA = false;
        
        

        System.out.print(listaProveedores.get(0).getNombre());
    }

    //Mostrar algun mensaje
    @Asynchronous
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

//    Buscar proveedor



    public JasperPrint  verKardexProducto() throws JRException, ParseException {

       int estado = 1;

        JasperPrint jaspert = null;
        if (estado == 1) {
            List<EntradaDetalleInventario> detalleEntradas = new ArrayList<>();
            EntradaDetalleDAO dao = new EntradaDetalleDAO();


            List<KardexReport> dataset = new ArrayList<>();
            double subtotal = 0.0f;
            double ice = 0.0f;
            double iva = 0.0f;
            double descuento = 0.0f;
            if (this.listaKardex.size() > 0) {
                for (KardexEntradasSalidas inv : this.listaKardex) {
                    dataset.add(new KardexReport(String.valueOf(inv.getCodigo()), String.valueOf(inv.getFecha()), inv.getObservacion(),
                            String.valueOf( (int) inv.getEntradas()), String.valueOf( (int) inv.getSalidas()), String.valueOf( (int) inv.getSaldoUnidades()), String.format("%.2f", inv.getSaldoIngresos()),
                            String.format("%.2f", inv.getSaldoSalidas()), String.format("%.2f", inv.getSaldo()), String.format("%.2f", inv.getCosto()) ));
                            
//                    subtotal += inv.getCosto() * (double) inv.getCant();
//                    ice += inv.getIce();
//                    iva += inv.getIva();

                }
            }


            // Cabecera de la respuesta.
            /*ec.responseReset();
            ec.setResponseContentType("application/pdf");
            ec.setResponseHeader("Content-disposition", "attachment; "
                    + "filename=factura.pdf");
             */
            // tomamos el stream para llenarlo con el pdf.
            //try ( OutputStream stream = ec.getResponseOutputStream()) {
            // Parametros para el reporte.
            
            this.fechaImpresion = new Date();
              DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
              String currentDate = df.format(this.fechaImpresion);
              Date currentDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(currentDate);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy",
                    new Locale("es_ES"));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("FECHA", dateFormat.format(currentDate2));
            parametros.put("IDARTICULO", String.valueOf( this.producto.getId()));
            parametros.put("NOMBREARTICULO", this.producto.getNombre());


            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("PlantillasReportes/kardexv1.jasper"));

            // llenamos la plantilla con los datos.
            /*JasperPrint jasperPrint = JasperFillManager.fillReport(
                        filetext.getPath(),
                        parametros,
                        new JRBeanCollectionDataSource(dataset)*/
            jaspert = JasperFillManager.fillReport(
                    filetext.getPath(),
                    parametros,
                    new JRBeanCollectionDataSource(dataset));

            // exportamos a pdf.
            /* JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
                //JasperExportManager.exportReportToXmlStream(jasperPrint, outputStream);

                stream.flush();
                stream.close();*/
 /* } catch (Exception ex) {
                System.out.println(ex.getMessage().toString());
            } finally {
                // enviamos la respuesta.
                fc.responseComplete();
            }*/
        }
        return jaspert;

    }

    public void imprimirPDF() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition",
                    "attachment; filename=" + "kardex" + ".pdf");
            try ( ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(verKardexProducto(), servletOutputStream);
                servletOutputStream.flush();
                FacesContext.getCurrentInstance().responseComplete();
            }
        } catch (Exception ex) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "hola", ex.getMessage()));
        }
    }

    //Agregar producto a la lista de detalle
 

        

        


    



    public void SeleccionarProducto(ArticulosInventario pr) {
        try {
            this.codigoProducto = pr.getId();
            this.nombreProducto = pr.getDescripcion();
            this.precioProducto = pr.getCoast();
            this.precioPromedio = productoDao.getPrecioPromedio(pr.getId());
            
            this.listaKardex = kardexEntradaSalidaDAO.getKardexES(this.codigoProducto, fechaInicio, fechaFin);
            
           this.producto = pr;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Asynchronous
    public void LimpiarProducto() {
        this.producto = null;
        this.nombreProducto = "";
        this.cantidad = 0;
        this.codigoProducto = 0;
        this.SiICE = false;
    }

    public void SeleccionarBodega(Bodega bod) {
        this.codBodega = bod.getCod();
        this.nombreBodega = bod.getNomBodega();
        this.ciudadBodega = bod.getNomCiudad();
        this.direccionBodega = bod.getDireccion();
        this.bodega = bod;
        this.listaProductos = productoDao.getArticulosEntradas(codBodega);
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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public ProveedorDAO getProveedorDAO() {
        return proveedorDAO;
    }

    public void setProveedorDAO(ProveedorDAO proveedorDAO) {
        this.proveedorDAO = proveedorDAO;
    }

    public String getProveedordINum() {
        return proveedordINum;
    }

    public void setProveedordINum(String proveedordINum) {
        this.proveedordINum = proveedordINum;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public EntradaDetalleInventario getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(EntradaDetalleInventario productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public Proveedor getProveedorSeleccionado() {
        return proveedorSeleccionado;
    }

    public void setProveedorSeleccionado(Proveedor proveedorSeleccionado) {
        this.proveedorSeleccionado = proveedorSeleccionado;
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

    public EntradaDetalleInventario getDetalleEntrada() {
        return detalleEntrada;
    }

    public void setDetalleEntrada(EntradaDetalleInventario detalleEntrada) {
        this.detalleEntrada = detalleEntrada;
    }

    public EntradaDetalleDAO getDetalleDAO() {
        return detalleDAO;
    }

    public void setDetalleDAO(EntradaDetalleDAO detalleDAO) {
        this.detalleDAO = detalleDAO;
    }

    public List<EntradaDetalleInventario> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<EntradaDetalleInventario> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getSubtotal12() {
        return subtotal12;
    }

    public void setSubtotal12(int subtotal12) {
        this.subtotal12 = subtotal12;
    }

    public int getSubtotal0() {
        return subtotal0;
    }

    public void setSubtotal0(int subtotal0) {
        this.subtotal0 = subtotal0;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getIce() {
        return ice;
    }

    public void setIce(int ice) {
        this.ice = ice;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public EntradaInventario getEntrada() {
        return entrada;
    }

    public void setEntrada(EntradaInventario entrada) {
        this.entrada = entrada;
    }

    public EntradaDao getEntradaDao() {
        return entradaDAO;
    }

    public void setEntradaDao(EntradaDao entradaDao) {
        this.entradaDAO = entradaDao;
    }

    public int getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(int efectivo) {
        this.efectivo = efectivo;
    }

    public int getCambio() {
        return cambio;
    }

    public void setCambio(int cambio) {
        this.cambio = cambio;
    }

    public int getDiasPago() {
        return diasPago;
    }

    public void setDiasPago(int diasPago) {
        this.diasPago = diasPago;
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

    public Boolean getSiICE() {
        return SiICE;
    }

    public void setSiICE(Boolean SiICE) {
        this.SiICE = SiICE;
    }

    public Boolean getSiIVA() {
        return SiIVA;
    }

    public void setSiIVA(Boolean SiIVA) {
        this.SiIVA = SiIVA;
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }


    public List<EntradaInventario> getListaEntradas() {
        return listaEntradas;
    }

    public void setListaEntradas(List<EntradaInventario> listaEntradas) {
        this.listaEntradas = listaEntradas;
    }

    public void aleatorioCod() {
        String uuid = java.util.UUID.randomUUID().toString().substring(4, 7).toUpperCase();
        String uuid2 = java.util.UUID.randomUUID().toString().substring(4, 7);
        this.numeroComprobante = ("ENT-" + uuid + uuid2).toUpperCase();
    }

}