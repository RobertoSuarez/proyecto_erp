/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.controllers;

import com.contabilidad.models.BalanceGeneral;
import com.cuentasporpagar.daos.ProveedorDAO;
import com.cuentasporpagar.models.Proveedor;
import com.global.config.Extensiones;
import com.inventario.DAO.ArticulosInventarioDAO;
import com.inventario.DAO.BodegaDAO;
import com.inventario.DAO.EntradaDao;
import com.inventario.DAO.EntradaDetalleDAO;
import com.inventario.DAO.HistoricoPreciosDAO;
import com.inventario.DAO.InventarioAsientoDAO;
import com.inventario.models.ArticulosInventario;
import com.inventario.models.AsientoInventario;
import com.inventario.models.Bodega;
import com.inventario.models.EntradaDetalleInventario;
import com.inventario.models.EntradaInventario;
import com.inventario.models.HistoricoPrecios;
import com.inventario.models.MovimientoInventario;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
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
@Named(value = "EntradaMB")
@ViewScoped
public class EntradaManagedBean implements Serializable {

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
    private Date fechaComprobante;

    private String observacion;

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

    private double subtotal12;
    private double subtotal0;
    private int descuento;
    private double ice;
    private double iva;
    private double total;

    private EntradaInventario entrada;
    private EntradaDao entradaDAO;

    private int efectivo;
    private int cambio;
    private int diasPago;

    private List<Proveedor> listaProveedores;
    private List<ArticulosInventario> listaProductos;
    private List<Bodega> listaBodegas;

    private List<EntradaInventario> listaEntradas;

    //Constructor
    @PostConstruct
    public void EntradaManagedBean() {

        //    this.productosReport = daoReport.getArticulosReport();
        this.precioPromedio = 0;
        this.historico = new HistoricoPrecios();
        this.historicoDAO = new HistoricoPreciosDAO();

        this.idProveedor = 0;
        this.proveedor = new Proveedor();
        this.proveedorDAO = new ProveedorDAO();
        this.nombreCategoria = "XXXX";
        this.numeroComprobante = "";
        aleatorioCod();
        this.fechaComprobante = new Date();
        this.observacion = "";

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
        this.listaProductos = productoDao.getArticulosEntradas();

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
    @Asynchronous
    public void BuscarProveedorEntrada() {
        this.proveedor = proveedorDAO.BuscarProveedor(this.proveedordINum);
        if (this.proveedor.getNombre() != null) {
            this.proveedorNombre = this.proveedor.getNombre();
        } else {
            System.out.print("No hay proveedor");
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "El proveedor no existe o se encuentra inactivo.");
        }

        if (this.proveedor.getNombre() != null) {
            System.out.print("proveedor: " + proveedorNombre);
        } else {
            System.out.print("Sin proveedor");
        }
    }

    //Buscar Producto
    @Asynchronous
    public void BuscarProducto() {
        this.producto = null;
        this.nombreProducto = "";
        this.nombreCategoria = "XXXXXX";
        this.cantidad = 1;
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

            this.precioProducto = this.producto.getCoast();
        }
    }

    public JasperPrint verFactura(int idEntrada) throws JRException {

        EntradaInventario entrada = entradaDAO.getEntradas(idEntrada).get(0);
        JasperPrint jaspert = null;
        if (entrada != null) {
            List<EntradaDetalleInventario> detalleEntradas = new ArrayList<>();
            EntradaDetalleDAO dao = new EntradaDetalleDAO();
            detalleEntradas = dao.getEntradasDetalle(idEntrada);

            List<ProductoReport> dataset = new ArrayList<>();
            double subtotal = 0.0f;
            double ice = 0.0f;
            double iva = 0.0f;
            double descuento = 0.0f;
            if (detalleEntradas.size() > 0) {
                for (EntradaDetalleInventario inv : detalleEntradas) {
                    dataset.add(new ProductoReport(String.valueOf(inv.getIdArticulo()), inv.getNombreProducto(), (int) inv.getCant(), String.format("%.2f", inv.getCosto()), String.format("%.2f", inv.getCosto() * inv.getCant())));
                    subtotal += inv.getCosto() * (double) inv.getCant();
                    ice += inv.getIce();
                    iva += inv.getIva();

                }
            }

            Bodega bod = bodegaDAO.obtenerBodega(entrada.getIdBodega());
            Proveedor pro = proveedorDAO.BuscarProveedor(entrada.getIdProveedor());
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
            parametros.put("FECHA", dateFormat.format(entrada.getFecha()));
            parametros.put("COMPROBANTE", entrada.getNumComprobante());
            parametros.put("NOMBREBODEGA", bod.getNomBodega());
            parametros.put("RUCPROVEEDOR", pro.getRuc());
            parametros.put("NOMBREPROVEEDOR", pro.getNombre());
            parametros.put("OBSERVACION", entrada.getObservacion());

            parametros.put("SUBTOTAL", String.format("%.2f", subtotal));
            parametros.put("DESCUENTO", String.format("%.2f", descuento));
            parametros.put("IVA", String.format("%.2f", iva));
            parametros.put("ICE", String.format("%.2f", ice));
            parametros.put("TOTAL", String.format("%.2f", (subtotal + iva + ice)));

            // leemos la plantilla para el reporte.
            File filetext = new File(FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getRealPath("PlantillasReportes/entradas.jasper"));

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

    public void imprimirPDF(int idEntrada) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition",
                    "attachment; filename=" + "facturaEntradas" + ".pdf");
            try ( ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(verFactura(idEntrada), servletOutputStream);
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
            Proveedor prueba = this.proveedor;
            Bodega prueba2 = this.bodega;
            ArticulosInventario prueba3 = this.producto;
            if (this.producto.getId() > 0) {
                int stock_maximo = this.producto.getMax_stock();
                int _cantidad = this.cantidad;
                if (stock_maximo < _cantidad) {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No puede agregar más unidades de la permitidas (" + this.producto.getMax_stock() + ")");
                } else {
                    if (this.cantidad <= 0) {
                        addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese un valor válido");
                    } else {

                        if (!listaDetalle.isEmpty()) {
                            int productoid = 0;
                            for (EntradaDetalleInventario elemento : listaDetalle) {
                                if (elemento.getIdArticulo() == producto.getId()) {
                                    productoid = 1;
                                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ya se agrego este producto en la lista");
                                }
                            }

                            if (productoid == 0) {
                                productoid = 0;
                                double iceProducto, ivaProducto;
                                iceProducto = this.producto.getCoast() * this.cantidad * ((double) this.producto.getIceproducto() / 100);
                                ivaProducto = this.producto.getCoast() * 0.12 * this.cantidad;
                                //Ingreso de valores al detalle de entrada
                                EntradaDetalleInventario detalle = new EntradaDetalleInventario();
                                detalle.setIdArticulo(this.producto.getId());
                                detalle.setCant(this.cantidad);
                                detalle.setIva(ivaProducto);

                                detalle.setIce(iceProducto);
                                detalle.setCosto(this.producto.getCoast());
                                detalle.setSubtotal((double) this.producto.getCoast() * (double) this.cantidad);
                                detalle.setNombreProducto(nombreProducto);
                                detalle.setNombreCategoria(nombreCategoria);

                                detalle.setArticuloInventario(producto);
                                //Cálculo de los valores
                                this.subTotalEntrada = this.subTotalEntrada + detalle.getSubtotal();
                                this.listaDetalle.add(detalle);
                                this.cantidad = 1;
                                this.codigoProducto = 0;
                                this.precioPromedio = 0;
                                this.nombreProducto = "";
                                double subtemp = (this.producto.getCoast() * detalle.getCant());
                                if (this.producto.getIva() != 0) {
                                    this.subtotal12 += subtemp;
                                } else {
                                    this.subtotal0 += subtemp;
                                }

                                this.iva += this.producto.getIva() * detalle.getCant() * detalle.getCosto();
                                this.ice += this.producto.getIce() * detalle.getCant();

                                this.total = this.subtotal0 + this.subtotal12 + this.iva + this.ice;

                                this.nombreProducto = "XXXXXX";
                                this.cantidad = 1;
                                this.precioProducto = 0;

                                this.producto = null;
                            }
                        } else {
                            double iceProducto, ivaProducto;
                            iceProducto = this.producto.getCoast() * this.cantidad * ((double) this.producto.getIceproducto() / 100);
                            ivaProducto = this.producto.getCoast() * 0.12 * this.cantidad;
                            //Ingreso de valores al detalle de entrada
                            EntradaDetalleInventario detalle = new EntradaDetalleInventario();
                            detalle.setIdArticulo(this.producto.getId());
                            detalle.setCant(this.cantidad);
                            detalle.setIva(ivaProducto);

                            detalle.setIce(iceProducto);
                            detalle.setCosto(this.producto.getCoast());
                            detalle.setSubtotal((double) this.producto.getCoast() * (double) this.cantidad);
                            detalle.setNombreProducto(nombreProducto);
                            detalle.setNombreCategoria(nombreCategoria);

                            detalle.setArticuloInventario(producto);
                            //Cálculo de los valores
                            this.subTotalEntrada = this.subTotalEntrada + detalle.getSubtotal();
                            this.listaDetalle.add(detalle);
                            this.cantidad = 1;
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
                            this.cantidad = 1;
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
    public void EliminarProducto(EntradaDetalleInventario detalle
    ) {
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
            this.subTotalEntrada -= detalle.getSubtotal();

            this.listaDetalle.remove(detalle);
//Actualizar en xhtml
            PrimeFaces.current().ajax().update("entradaForm");
            System.out.println("Eliminado");
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
        }
    }

    @Asynchronous
    public int RegistrarEntrada() {
        try {
            EntradaInventario entradaActual = new EntradaInventario();
            List<MovimientoInventario> movimientos = new ArrayList<>();
            //(int idDiario, double total, String documento, String detalle, Date fechaCreacion, Date fechaCierre)
            Date fecha = new Date();

            Date fechaCierre = Date.from(LocalDateTime.of(LocalDate.now().getYear(), 12, 31, 0, 0).toInstant(ZoneOffset.UTC));

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, LocalDateTime.now().getYear());
            cal.set(Calendar.MONTH, Calendar.DECEMBER);
            cal.set(Calendar.DAY_OF_MONTH, 31);

            Date facheCierre2 = cal.getTime();
            
            
            AsientoInventario asiento = new AsientoInventario(2, total, this.numeroComprobante, this.observacion, Extensiones.ConvertFecha(this.fechaComprobante), Extensiones.ConvertFecha(facheCierre2));
            int listSize = 0;
            if (this.listaDetalle.isEmpty()) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No puede  realizar una entrada nula");
            } else {
                if (this.proveedorNombre == null || this.proveedorNombre == "") {
                    //Debe elegir un proveedor para la entrada
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", " ");
                } else {
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                    String currentDate = df.format(this.fechaComprobante);
                    Date currentDate2 = df.parse(currentDate);

                    //Asignar valores a la entrada
                    entradaActual.setProveedor(this.proveedor);
                    entradaActual.setIdProveedor(this.proveedor.getIdProveedor());
                    entradaActual.setIdBodega(this.bodega.getCod());
                    entradaActual.setNumComprobante(this.numeroComprobante);
                    entradaActual.setFecha(new Date());
                    entradaActual.setObservacion(this.observacion);

                    //Verificación en consola
                    System.out.println(entradaActual.getProveedor().getNombre());

                    //Guardar la entrada desde DAO
                    int entradaRealizada = this.entradaDAO.GuardarEntrada(entradaActual);

                    //Verificar que se haya ingresado la entrada
                    if (entradaRealizada == 0) {
                        addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo realizar la entrada. Revise los datos ingresados");
                    } else {
                        System.out.println("Entrada realizada con Factura #" + entradaRealizada);

                        EntradaDetalleDAO daoDetail = new EntradaDetalleDAO();

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
                            daoDetail.RegistrarProductos(entradaRealizada, codProd, qty, price, iva, ice, this.codBodega);

                            //   Registrar el historico
                            this.historico.setCosto(price);
                            this.historico.setFechaInicio(currentDate2);
                            this.historico.setId_articulo(codProd);
                            this.historico.setPrecioVenta(this.productoDao.getPrecioVenta(codProd));
                            this.historicoDAO.GuardarHistorico(historico);

                            listSize += 1;
                        }
                        InventarioAsientoDAO dao = new InventarioAsientoDAO();

                        /*
                sentencia1 = "[{\"idSubcuenta\":\"160\",\n" +
                                "\"debe\":\"" + venta.getTotalFactura() + "\",\n" +
                                "\"haber\":\"0\",\n" +
                                "\"tipoMovimiento\":\"Factura de venta\"},\n";
                        
                sentencia1 +=   "{\"idSubcuenta\":\"17\",\n" +
                                "\"debe\":\"0\",\n" +
                                "\"haber\":\"" + venta.getCosto() + "\",\n" +
                                "\"tipoMovimiento\":\"Reducción en inventario\"},\n" +
                                "{\"idSubcuenta\":\"151\",\n" +
                                "\"debe\":\"" + venta.getCosto() + "\",\n" +
                                "\"haber\":\"0\",\n" +
                                "\"tipoMovimiento\":\"Costo de venta\"}]";                            
                            
                         */
                        // public MovimientoInventario(int idSubcuenta, double debe, double haber, String tipoMovimiento)
                        movimientos.add(new MovimientoInventario(1, 0, this.subTotalEntrada, "Por concepto de compra de productos para el inventario"));

                        if (this.getSubtotal0() != 0) {
                            movimientos.add(new MovimientoInventario(1, this.subTotalEntrada, 0, "Ingreso de entrada base 0%"));
                        }
                        if (this.getSubtotal12() != 0) {
                            movimientos.add(new MovimientoInventario(1, this.subTotalEntrada, 0, "Ingreso de entrada base 12%"));
                        }
                        if (this.getIce() != 0) {
                            movimientos.add(new MovimientoInventario(1, this.subTotalEntrada, 0, "ICE de entradas"));
                        }
                        if (this.getIva() != 0) {
                            movimientos.add(new MovimientoInventario(1, this.subTotalEntrada, 0, "Iva en entradas"));
                        }
                        dao.insertarAsiento(asiento, movimientos);
                        addMessage(FacesMessage.SEVERITY_INFO, "Exito", "Se guardo la entrada");
                        return entradaRealizada;
                    }
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
        }
        return 0;
    }

    public void SeleccionarProveedor(Proveedor prov) {
        this.idProveedor = prov.getIdProveedor();
        this.proveedorNombre = prov.getNombre();
        this.proveedordINum = prov.getRazonSocial();
        this.proveedor = prov;

    }

    public void SeleccionarProducto(ArticulosInventario pr) {
        try {
            this.codigoProducto = pr.getId();
            this.nombreProducto = pr.getDescripcion();
            this.precioProducto = pr.getCoast();
            this.precioPromedio = productoDao.getPrecioPromedio(pr.getId());
            if (this.SiICE) {
                this.ice = (int) (pr.getCosto() * 15 / 100);
            }
            this.iva = (int) (pr.getCosto() * 12 / 100);

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

    public double getSubtotal12() {
        return subtotal12;
    }

    public void setSubtotal12(double subtotal12) {
        this.subtotal12 = subtotal12;
    }

    public double getSubtotal0() {
        return subtotal0;
    }

    public void setSubtotal0(double subtotal0) {
        this.subtotal0 = subtotal0;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public double getIce() {
        return ice;
    }

    public void setIce(int ice) {
        this.ice = ice;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public double getTotal() {
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

    public Date getFechaComprobante() {
        return fechaComprobante;
    }

    public void setFechaComprobante(Date fechaComprobante) {
        this.fechaComprobante = fechaComprobante;
    }

    public List<EntradaInventario> getListaEntradas() {
        return listaEntradas;
    }

    public void setListaEntradas(List<EntradaInventario> listaEntradas) {
        this.listaEntradas = listaEntradas;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void aleatorioCod() {
        String uuid = java.util.UUID.randomUUID().toString().substring(4, 7).toUpperCase();
        String uuid2 = java.util.UUID.randomUUID().toString().substring(4, 7);
        this.numeroComprobante = ("ENT-" + uuid + uuid2).toUpperCase();
    }
}
