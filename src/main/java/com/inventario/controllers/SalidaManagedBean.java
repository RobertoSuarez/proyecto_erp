/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.controllers;

import com.cuentasporpagar.daos.ProveedorDAO;
import com.cuentasporpagar.models.Proveedor;
import com.inventario.DAO.ArticulosInventarioDAO;
import com.inventario.DAO.BodegaDAO;
import com.inventario.DAO.EntradaDao;
import com.inventario.DAO.EntradaDetalleDAO;

import com.inventario.DAO.SalidaDao;
import com.inventario.DAO.SalidaDetalleDao;
import com.inventario.models.ArticulosInventario;
import com.inventario.models.Bodega;
import com.inventario.models.EntradaDetalleInventario;
import com.inventario.models.EntradaInventario;
import com.inventario.models.SalidaDetalleInventario;
import com.inventario.models.SalidaInventario;

import com.inventario.report.ProductoReport;
import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
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
import javax.faces.context.ExternalContext;
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
import org.apache.commons.math3.util.Precision;
import org.primefaces.PrimeFaces;

/**
 *
 * @author angul
 */
@Named(value = "SalidaMB")
@ViewScoped
public class SalidaManagedBean implements Serializable {

    ArticulosInventarioDAO daoReport = new ArticulosInventarioDAO();

    List<ProductoReport> productosReport;

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

    private String numeroComprobante;
    private Date fechaComprobante;

    private EntradaDetalleInventario detalleEntrada;
    private EntradaDetalleDAO entradaDetalleDAO;
    
    private EntradaDetalleInventario productoSeleccionado;
    private Proveedor proveedorSeleccionado;
    private Bodega bodegaSeleccionada;

    private EntradaInventario entradaSeleccionada;

    private ArticulosInventarioDAO productoDao;
    private ArticulosInventario producto;
    private int codigoProducto;
    private String nombreProducto;
    private float precioProducto;
    private double subTotalSalida;

    private SalidaDetalleInventario detalleSalida;
    private SalidaDetalleDao detalleDAO;
    private List<SalidaDetalleInventario> listaDetalle;

    private int cantidadFacturada;
    private int cantidadReportada;

    private int subtotal12;
    private int subtotal0;
    private int descuento;
    private int ice;
    private int iva;
    private int total;

    private SalidaInventario salida;
    private SalidaDao salidaDAO;

    private EntradaInventario entrada;
    private EntradaDao entradaDAO;

    private int efectivo;
    private int cambio;
    private int diasPago;

    private List<Proveedor> listaProveedores;
    private List<ArticulosInventario> listaProductos;
    private List<Bodega> listaBodegas;

    private List<SalidaInventario> listaSalidas;

    private List<EntradaInventario> listaEntradas;
    private List<EntradaInventario> listaEntradasPermitidas;

    private String nombreCategoria;

    //Constructor
    @PostConstruct
    public void SalidaManagedBean() {

        this.productosReport = daoReport.getArticulosReport();

        this.idProveedor = 0;
        this.proveedor = new Proveedor();
        this.proveedorDAO = new ProveedorDAO();
        this.nombreCategoria = "XXXX";
        this.numeroComprobante = "";

        this.fechaComprobante = new Date();

        this.bodega = new Bodega();
        this.bodegaDAO = new BodegaDAO();

        this.producto = new ArticulosInventario();
        this.productoDao = new ArticulosInventarioDAO();
        this.codigoProducto = 0;
        this.nombreProducto = "XXXXXX";
        this.subTotalSalida = 0;

        this.subtotal12 = 0;
        this.subtotal0 = 0;
        this.descuento = 0;
        this.ice = 0;
        this.iva = 0;
        this.total = 0;

        this.listaDetalle = new ArrayList<>();
        this.cantidadReportada = 1;
        this.cantidadFacturada = 0;

        this.salida = new SalidaInventario();
        this.salidaDAO = new SalidaDao();
        this.listaSalidas = salidaDAO.getSalidas();

        this.entrada = new EntradaInventario();
        this.entradaDAO = new EntradaDao();
        this.listaEntradas = entradaDAO.getEntradas();
        this.listaEntradasPermitidas = entradaDAO.getEntradasPermitidas();

        this.detalleEntrada = new EntradaDetalleInventario();
        this.entradaDetalleDAO = new EntradaDetalleDAO();
        
        this.productoSeleccionado = null;
        this.proveedorSeleccionado = null;
        
        this.bodegaSeleccionada = null;

        this.entradaSeleccionada = null;

        this.efectivo = 0;
        this.cambio = 0;
        this.diasPago = 0;

        this.proveedorDAO = new ProveedorDAO();
        this.detalleDAO = new SalidaDetalleDao();
        this.listaProveedores = new ArrayList<>();
        this.listaProveedores = proveedorDAO.ListarProveedor();

        this.listaProductos = new ArrayList<>();

        this.SiICE = false;
        this.SiIVA = false;

        System.out.print(listaProveedores.get(0).getNombre());
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    //Mostrar algun mensaje
    @Asynchronous
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    //Buscar proveedor
////    @Asynchronous
////    public void BuscarProveedorEntrada() {
////        this.proveedor = proveedorDAO.BuscarProveedor(this.proveedordINum);
////        if (this.proveedor.getNombre() != null) {
////            this.proveedorNombre = this.proveedor.getNombre();
////        } else {
////            System.out.print("No hay proveedor");
////            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "El proveedor no existe o se encuentra inactivo.");
////        }
////
////        if (this.proveedor.getNombre() != null) {
////            System.out.print("proveedor: " + proveedorNombre);
////        } else {
////            System.out.print("Sin proveedor");
////        }
////    }
    //Buscar Producto
    @Asynchronous
    public void BuscarProducto() {
        this.producto = null;
        this.nombreProducto = "";
        this.nombreCategoria = "XXXXXX";
        this.cantidadReportada = 1;
        this.precioProducto = 0;

        this.producto = this.productoDao.ObtenerProducto(this.codigoProducto);
        if (producto != null) {
            this.nombreCategoria = this.productoDao.getCategoria(this.producto.getCat_cod()).getNom_categoria();
        }

        if (this.producto.getDescripcion() == null) {
            System.out.println("Producto nulo");
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "El producto no existe");
        } else {
            System.out.println("Existe el producto" + this.nombreProducto);

            this.nombreProducto = this.producto.getDescripcion();

            this.precioProducto = this.producto.getCosto();
        }
    }

    public JasperPrint verSalida(int idSalida) throws JRException {

        SalidaInventario salida = salidaDAO.getSalidas(idSalida).get(0);
        JasperPrint jaspert = null;
        if (salida != null) {
            List<SalidaDetalleInventario> detalleSalidas = new ArrayList<>();
            SalidaDetalleDao dao = new SalidaDetalleDao();
            detalleSalidas = dao.getSalidasDetalle(idSalida);

            List<ProductoReport> dataset = new ArrayList<>();
            double subtotal = 0.0f;
            double ice = 0.0f;
            double iva = 0.0f;
            double descuento = 0.0f;
            if (detalleSalidas.size() > 0) {
                for (SalidaDetalleInventario inv : detalleSalidas) {
                    dataset.add(new ProductoReport(String.valueOf(inv.getIdArticulo()), inv.getNombreProducto(), (int) inv.getCant(), inv.getCosto(), inv.getCosto() * inv.getCant()));
                    subtotal += inv.getCosto() * (double) inv.getCant();
                    ice += inv.getIce();
                    iva += inv.getIva();

                }
            }

            BodegaDAO bodegaDAO = new BodegaDAO();
            Bodega bod = bodegaDAO.obtenerBodega(salida.getIdBodega());
            Proveedor pro = proveedorDAO.BuscarProveedor(salida.getIdProveedor());
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();

            // Cabecera de la respuesta.
            /*ec.responseReset();
            ec.setResponseContentType("application/pdf");
            ec.setResponseHeader("Content-disposition", "attachment; "
                    + "filename=factura.pdf");
             */
            // tomamos el stream para llenarlo con el pdf.
            //try ( OutputStream stream = ec.getResponseOutputStream()) {
            // Parametros para el reporte.
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy",
                    new Locale("es_ES"));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("FECHA", dateFormat.format(salida.getFecha()));
            parametros.put("COMPROBANTE", salida.getNumComprobante());
            parametros.put("OBSERVACION", salida.getObservacion());
            /*parametros.put("NOMBREBODEGA",  );*/
            parametros.put("NOMBREBODEGA", bod.getNomBodega());
            parametros.put("RUCPROVEEDOR", pro.getRuc());
            parametros.put("NOMBREPROVEEDOR", pro.getNombre());

            parametros.put("SUBTOTAL", String.format("%.2f", subtotal));
            parametros.put("DESCUENTO", String.format("%.2f", descuento));
            parametros.put("IVA", String.format("%.2f", iva));
            parametros.put("ICE", String.format("%.2f", ice));
            parametros.put("TOTAL", String.format("%.2f", (subtotal + iva + ice)));

            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("PlantillasReportes/reportesalidas.jasper"));

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

    public void imprimirPDF(int idSalida) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition",
                    "attachment; filename=" + "reporteSalidas" + ".pdf");
            try ( ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(verSalida(idSalida), servletOutputStream);
                servletOutputStream.flush();
                FacesContext.getCurrentInstance().responseComplete();
            }
        } catch (Exception ex) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "hola", ex.getMessage()));
        }
    }

    //Agregar producto a la lista de detalle
    @Asynchronous
    public void AgregarProductoLista() {

        try {

             if (this.producto.getId() > 0) {
                int cantidadFacturada = this.cantidadFacturada;
                int _cantidad = this.cantidadReportada;
                if (cantidadFacturada < _cantidad) {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No puede agregar más unidades de las que estan en factura (" + this.cantidadFacturada + ")");
                } else {
                    if (this.cantidadReportada <= 0) {
                        addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese un valor válido");
                    } else {
                        if (!listaDetalle.isEmpty()) {
                            int productoid = 0;
                            for (SalidaDetalleInventario elemento : listaDetalle) {
                                if (elemento.getIdArticulo() == producto.getId()) {
                                    productoid = 1;
                                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ya se realizo el cambio en este producto");
                                }
                            }
                            System.out.println("insertar los valores");

                            if (productoid == 0) {
                                productoid = 0;
                                double iceProducto, ivaProducto;
                                iceProducto = this.producto.getCoast()* this.cantidadReportada * ((double) this.producto.getIceproducto() / 100);
                                ivaProducto = this.producto.getCoast()* 0.12 * this.cantidadReportada;
                                //Ingreso de valores al detalle de entrada
                                SalidaDetalleInventario detalle = new SalidaDetalleInventario();
                                detalle.setIdArticulo(this.producto.getId());
                                detalle.setCant(this.cantidadReportada);
                                detalle.setIva(ivaProducto);
                                detalle.setIce(iceProducto);
                                detalle.setCosto(this.producto.getCoast());
                                detalle.setSubtotal( (double) this.producto.getCoast()* (double) this.cantidadReportada);
                                detalle.setNombreProducto(nombreProducto);
                                detalle.setNombreCategoria(nombreCategoria);
                                detalle.setArticuloInventario(producto);
                                //Cálculo de los valores
                                this.subTotalSalida = this.subTotalSalida + detalle.getSubtotal();
                                this.listaDetalle.add(detalle);
                                this.cantidadReportada = 1;
                                this.codigoProducto = 0;
                                this.nombreProducto = "";
                                double subtemp = (this.producto.getCoast()* detalle.getCant());
                                if (this.producto.getIva() != 0) {
                                    this.subtotal12 += subtemp;
                                } else {
                                    this.subtotal0 += subtemp;
                                }

                                this.iva += this.producto.getIva() * detalle.getCant() * detalle.getCosto();
                                this.ice += this.producto.getIce() * detalle.getCant();

                                this.total = this.subtotal0 + this.subtotal12 + this.iva + this.ice;

                                this.nombreProducto = "XXXXXX";
                                this.cantidadReportada = 1;
                                this.precioProducto = 0;
                                this.producto = null;

                            }

                        } else {

                            double iceProducto, ivaProducto;
                            iceProducto = this.producto.getCoast()* this.cantidadReportada * ((double) this.producto.getIceproducto() / 100);
                            ivaProducto = this.producto.getCoast()* 0.12 * this.cantidadReportada;
                            //Ingreso de valores al detalle de entrada
                            SalidaDetalleInventario detalle = new SalidaDetalleInventario();
                            detalle.setIdArticulo(this.producto.getId());
                            detalle.setCant(this.cantidadReportada);
                            detalle.setIva(ivaProducto);
                            detalle.setIce(iceProducto);
                            detalle.setCosto(this.producto.getCoast());
                            detalle.setSubtotal(this.producto.getCoast() * this.cantidadReportada);
                            detalle.setNombreProducto(nombreProducto);
                            detalle.setNombreCategoria(nombreCategoria);

                            detalle.setArticuloInventario(producto);
                            //Cálculo de los valores
                            this.subTotalSalida = this.subTotalSalida + detalle.getSubtotal();
                            this.listaDetalle.add(detalle);
                            this.cantidadReportada = 1;
                            this.codigoProducto = 0;
                            this.nombreProducto = "";
                            double subtemp = (this.producto.getCosto() * detalle.getCant());
                            if (this.producto.getIva() != 0) {
                                this.subtotal12 += subtemp;
                            } else {
                                this.subtotal0 += subtemp;
                            }

                            this.iva += this.producto.getIva() * detalle.getCant() * detalle.getCosto();
                            this.ice += this.producto.getIce() * detalle.getCant();

                            this.total = this.subtotal0 + this.subtotal12 + this.iva + this.ice;

                            this.nombreProducto = "XXXXXX";
                            this.cantidadReportada = 1;
                            this.precioProducto = 0;

                            this.producto = null;

                        }
                    }
                }
            } else {
                System.out.println("No hay producto seleccionado");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
        }

    }

    //Eliminar un producto de la lista
    @Asynchronous
    public void EliminarProducto(SalidaDetalleInventario detalle) {
        try {
            double subtemp = detalle.getArticuloInventario().getCosto() * detalle.getCant();
            if (detalle.getArticuloInventario().getIva() != 0) {
                this.subtotal12 -= subtemp;
            } else {
                this.subtotal0 -= subtemp;
            }

            this.iva -= (detalle.getArticuloInventario().getIva() * detalle.getCant() * detalle.getCosto());
            this.ice -= (detalle.getArticuloInventario().getIce() * detalle.getCant());

            this.total = this.subtotal0 + this.subtotal12 + this.iva + this.ice;
            this.subTotalSalida -= detalle.getSubtotal();

            this.listaDetalle.remove(detalle);
//Actualizar en xhtml
            PrimeFaces.current().ajax().update("salidaForm");
            System.out.println("Eliminado");
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
        }
    }

    @Asynchronous
    public String RegistrarSalida() {
        try {
            SalidaInventario salidaActual = new SalidaInventario();
            int listSize = 0;
            if (this.listaDetalle.isEmpty()) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No puede  realizar una entrada nula");
            } else {
                if (this.proveedorNombre == null || this.proveedorNombre == "") {
                    //Debe elegir un proveedor para la entrada
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", " ");
                } else {
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    String currentDate = df.format(this.fechaComprobante);
                    Date currentDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(currentDate);

                    //Asignar valores a la salida
                    salidaActual.setProveedor(this.proveedor);
                    salidaActual.setIdProveedor(this.entrada.getIdProveedor());
                    salidaActual.setIdBodega(this.entrada.getIdBodega());
                    salidaActual.setNumComprobante(this.numeroComprobante);
                    salidaActual.setObservacion(this.salida.getObservacion());
                    salidaActual.setFecha(currentDate2);

                    //Verificación en consola
                    System.out.println(salidaActual.getProveedor().getNombre());

                    //Guardar la entrada desde DAO
                    int entradaRealizada = this.salidaDAO.GuardarSalida(salidaActual);

                    //Verificar que se haya ingresado la entrada
                    if (entradaRealizada == 0) {
                        addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo realizar la entrada. Revise los datos ingresados");
                    } else {
                        System.out.println("Entrada realizada con Factura #" + entradaRealizada);

                        SalidaDetalleDao daoDetail = new SalidaDetalleDao();

                        //Registro de cada producto (detalle) de la entrada en la BD
                        while (listSize < this.listaDetalle.size()) {
                            int codProd = this.listaDetalle.get(listSize).getArticuloInventario().getId();
                            double qty = this.listaDetalle.get(listSize).getCant();

                            double price = this.listaDetalle.get(listSize).getCosto();
                            double iva = this.listaDetalle.get(listSize).getIva();
                            double ice = this.listaDetalle.get(listSize).getIce();

                            System.out.println(this.listaDetalle.get(listSize).getArticuloInventario().getDescripcion());
                            System.out.println(entradaRealizada + "-" + codProd + "-" + qty + "-" + "-" + price);
                            //          daoDetail.GuardarEntrada(this.listaDetalle);
                            daoDetail.RegistrarProductos(entradaRealizada, codProd, qty, price, iva, ice, this.entrada.getIdBodega());
                            listSize += 1;
                        }

                        addMessage(FacesMessage.SEVERITY_INFO, "Exito", "Se realizo la salida");
                        return "listaSalida";
                    }
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
        }
        return null;
    }

    @Asynchronous
    public void SeleccionarEntrada(EntradaInventario entrada) {

        this.numeroComprobante = entrada.getNumComprobante();
        this.proveedorNombre = entrada.getNombreProveedor();

        this.listaProductos = productoDao.getArticulos(entrada.getIdProveedor(), numeroComprobante, entrada.getIdBodega());
        this.entrada = entrada;

    }

    @Asynchronous
    public void SeleccionarProveedor(Proveedor prov) {
        if (numeroComprobante.isEmpty()) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Es necesario escribir el comprobante que afecta");
            return;
        }
        this.idProveedor = prov.getIdProveedor();
        this.proveedorNombre = prov.getNombre();
        this.proveedordINum = prov.getRazonSocial();
        this.proveedor = prov;
        this.listaProductos = productoDao.getArticulos(prov.getIdProveedor(), numeroComprobante);

    }

    public void SeleccionarProducto(ArticulosInventario pr) {
        try {
            this.codigoProducto = pr.getId();
            this.nombreProducto = pr.getDescripcion();
            this.precioProducto = pr.getCoast();
            this.cantidadFacturada = pr.getCantidadFacturada();
            
            


            this.producto = pr;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Asynchronous
    public void LimpiarProducto() {
        this.producto = null;
        this.nombreProducto = "";
        this.cantidadReportada = 0;
        this.codigoProducto = 0;
        this.SiICE = false;
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

    public double getSubTotalSalida() {
        return subTotalSalida;
    }

    public void setSubTotalSalida(int subTotalSalida) {
        this.subTotalSalida = subTotalSalida;
    }

    public SalidaDetalleInventario getDetalleSalida() {
        return detalleSalida;
    }

    public void setDetalleSalida(SalidaDetalleInventario detalleSalida) {
        this.detalleSalida = detalleSalida;
    }

    public SalidaDetalleDao getDetalleDAO() {
        return detalleDAO;
    }

    public void setDetalleDAO(SalidaDetalleDao detalleDAO) {
        this.detalleDAO = detalleDAO;
    }

    public List<SalidaDetalleInventario> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<SalidaDetalleInventario> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public int getcantidadReportada() {
        return cantidadReportada;
    }

    public void setcantidadReportada(int cantidadReportada) {
        this.cantidadReportada = cantidadReportada;
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

    public SalidaInventario getSalida() {
        return salida;
    }

    public void setSalida(SalidaInventario salida) {
        this.salida = salida;
    }

    public SalidaDao getSalidaDao() {
        return salidaDAO;
    }

    public void setSalidaDao(SalidaDao salidaDAO) {
        this.salidaDAO = salidaDAO;
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

    public Date getFechaComprobante() {
        return fechaComprobante;
    }

    public void setFechaComprobante(Date fechaComprobante) {
        this.fechaComprobante = fechaComprobante;
    }

    public List<SalidaInventario> getListaSalidas() {
        return listaSalidas;
    }

    public void setListaSalidas(List<SalidaInventario> listaSalidas) {
        this.listaSalidas = listaSalidas;
    }

    public EntradaInventario getEntrada() {
        return entrada;
    }

    public void setEntrada(EntradaInventario entrada) {
        this.entrada = entrada;
    }

    public EntradaDao getEntradaDAO() {
        return entradaDAO;
    }

    public void setEntradaDAO(EntradaDao entradaDAO) {
        this.entradaDAO = entradaDAO;
    }

    public List<EntradaInventario> getListaEntradas() {
        return listaEntradas;
    }

    public void setListaEntradas(List<EntradaInventario> listaEntradas) {
        this.listaEntradas = listaEntradas;
    }

    public EntradaInventario getEntradaSeleccionada() {
        return entradaSeleccionada;
    }

    public void setEntradaSeleccionada(EntradaInventario entradaSeleccionada) {
        this.entradaSeleccionada = entradaSeleccionada;
    }

    public int getCantidadFacturada() {
        return cantidadFacturada;
    }

    public void setCantidadFacturada(int cantidadFacturada) {
        this.cantidadFacturada = cantidadFacturada;
    }

    public int getCantidadReportada() {
        return cantidadReportada;
    }

    public void setCantidadReportada(int cantidadReportada) {
        this.cantidadReportada = cantidadReportada;
    }

    public List<EntradaInventario> getListaEntradasPermitidas() {
        return listaEntradasPermitidas;
    }

    public void setListaEntradasPermitidas(List<EntradaInventario> listaEntradasPermitidas) {
        this.listaEntradasPermitidas = listaEntradasPermitidas;
    }

}
