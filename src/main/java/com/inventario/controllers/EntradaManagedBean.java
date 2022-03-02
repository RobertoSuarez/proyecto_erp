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
import com.inventario.DAO.ArticulosInventarioDAO;
import com.inventario.DAO.BodegaDAO;
import com.inventario.DAO.EntradaDao;
import com.inventario.DAO.EntradaDetalleDAO;
import com.inventario.models.ArticulosInventario;
import com.inventario.models.Bodega;
import com.inventario.models.EntradaDetalleInventario;
import com.inventario.models.EntradaInventario;
import com.inventario.report.ProductoReport;
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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.ExternalContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author angul
 */
@Named(value = "EntradaMB")
@ViewScoped
public class EntradaManagedBean implements Serializable {

    ArticulosInventarioDAO daoReport = new ArticulosInventarioDAO();

    List<ProductoReport> productosReport;

    private Proveedor proveedor;
    private ProveedorDAO proveedorDAO;
    private String proveedordINum;
    private String proveedorNombre;

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
    private Boolean SiICE;
    private Boolean SiIVA;

    private Bodega bodega;
    private BodegaDAO bodegaDAO;
    private int codBodega;
    private String nombreBodega;
    private String direccionBodega;
    private String ciudadBodega;

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
    private String numeroComprobante;
    private Date fechaComprobante;

    private EntradaDetalleInventario productoSeleccionado;
    private Proveedor proveedorSeleccionado;
    private Bodega bodegaSeleccionada;

    private ArticulosInventarioDAO productoDao;
    private ArticulosInventario producto;
    private int codigoProducto;
    private String nombreProducto;
    private int precioProducto;
    private int subTotalEntrada;

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

    public List<EntradaInventario> getListaEntradas() {
        return listaEntradas;
    }

    public void setListaEntradas(List<EntradaInventario> listaEntradas) {
        this.listaEntradas = listaEntradas;
    }
    private List<EntradaInventario> listaEntradas;

    //Constructor
    @PostConstruct
    public void EntradaManagedBean() {

        this.productosReport = daoReport.getArticulosReport();
        
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
        this.listaProductos = productoDao.getArticulos();

        System.out.print(listaProveedores.get(0).getNombre());
    }

    //Mostrar algun mensaje
    @Asynchronous
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    //Buscar proveedor
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
        this.nombreProducto = "XXXXXX";
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

            this.precioProducto = this.producto.getCosto();
        }
    }

    
    public void verFactura() {

        int idEntrada = 1;
        EntradaInventario entrada = entradaDAO.getEntradas(idEntrada).get(0);

        if (entrada != null) {
            List<EntradaDetalleInventario> detalleEntradas = new ArrayList<>();
            EntradaDetalleDAO dao = new EntradaDetalleDAO();
            detalleEntradas = dao.getEntradasDetalle(idEntrada);

            List<ProductoReport> dataset = new ArrayList<>();
            float subtotal = 0.0f;
            float ice = 0.0f;
            float iva = 0.0f;
            float descuento = 0.0f;
            if (detalleEntradas.size() > 0) {
                for (EntradaDetalleInventario inv : detalleEntradas) {
                    dataset.add(new ProductoReport(String.valueOf(inv.getIdArticulo()), inv.getNombreProducto(), inv.getCant(), inv.getCosto(), inv.getCosto() * inv.getCant()));
                    subtotal += Float.valueOf(inv.getCosto()) * Float.valueOf(inv.getCant());
                    ice += Float.valueOf(inv.getIce());
                    iva += Float.valueOf(inv.getIva());

                }
            }

            Bodega bod = bodegaDAO.obtenerBodega(entrada.getIdBodega());
            Proveedor pro = proveedorDAO.BuscarProveedor(entrada.getIdProveedor());
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();

            // Cabecera de la respuesta.
            ec.responseReset();
            ec.setResponseContentType("application/pdf");
            ec.setResponseHeader("Content-disposition", "attachment; "
                    + "filename=factura.pdf");

            // tomamos el stream para llenarlo con el pdf.
            try ( OutputStream stream = ec.getResponseOutputStream()) {

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

                parametros.put("SUBTOTAL", String.format("%.2f", subtotal));
                parametros.put("DESCUENTO", String.format("%.2f", descuento));
                parametros.put("IVA", String.format("%.2f", iva));
                parametros.put("ICE", String.format("%.2f", ice));
                parametros.put("TOTAL", String.format("%.2f", (subtotal + iva + ice)));

                // leemos la plantilla para el reporte.
                File filetext = new File(FacesContext
                        .getCurrentInstance()
                        .getExternalContext()
                        .getRealPath("/PlantillasReportes/entradas.jasper"));

                // llenamos la plantilla con los datos.
                 JasperPrint jasperPrint = JasperFillManager.fillReport(
                        filetext.getPath(),
                        parametros,
                        new JRBeanCollectionDataSource(this.productosReport)
                );

                // exportamos a pdf.
                JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
                //JasperExportManager.exportReportToXmlStream(jasperPrint, outputStream);

                stream.flush();
                stream.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage().toString());
            } finally {
                // enviamos la respuesta.
                fc.responseComplete();
            }
        }

    }

    //Agregar producto a la lista de detalle
    @Asynchronous
    public void AgregarProductoLista() {
        try {
            if (this.producto.getId() > 0) {
                int stock_maximo = this.producto.getMax_stock();
                int _cantidad = this.cantidad;
                if (stock_maximo < _cantidad) {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No puede agregar más unidades de las existentes (" + this.producto.getMax_stock() + ")");
                } else {
                    if (this.cantidad <= 0) {
                        addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese un valor válido");
                    } else {

                        //Ingreso de valores al detalle de entrada
                        EntradaDetalleInventario detalle = new EntradaDetalleInventario();
                        detalle.setIdEntrada(this.producto.getCod());
                        detalle.setCant(this.cantidad);
                        detalle.setIva(this.producto.getIva());
                        detalle.setIce(this.producto.getIce());
                        detalle.setCosto(this.producto.getCosto());
                        detalle.setSubtotal(this.producto.getCosto() * this.cantidad);
                        detalle.setNombreProducto(nombreProducto);
                        detalle.setNombreCategoria(nombreCategoria);

                        detalle.setArticuloInventario(producto);
                        //Cálculo de los valores
                        this.subTotalEntrada = this.subTotalEntrada + detalle.getSubtotal();
                        this.listaDetalle.add(detalle);
                        this.cantidad = 1;
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
                        this.cantidad = 1;
                        this.precioProducto = 0;

                        this.producto = null;
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
    public void EliminarProducto(EntradaDetalleInventario detalle) {
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
    public String RegistrarEntrada() {
        try {
            EntradaInventario entradaActual = new EntradaInventario();
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

                    //Asignar valores a la entrada
                    entradaActual.setProveedor(this.proveedor);
                    entradaActual.setIdProveedor(this.proveedor.getIdProveedor());
                    entradaActual.setIdBodega(this.bodega.getCod());
                    entradaActual.setNumComprobante(numeroComprobante);
                    entradaActual.setFecha(currentDate2);

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

                            System.out.println(this.listaDetalle.get(listSize).getArticuloInventario().getDescripcion());
                            System.out.println(entradaRealizada + "-" + codProd + "-" + qty + "-" + "-" + price);
                            daoDetail.RegistrarProductos(entradaRealizada, codProd, qty, price);
                            listSize += 1;
                        }

                        return "listaEntrada";
                    }
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
        }
        return null;
    }

    public void SeleccionarProveedor(Proveedor prov) {
        this.proveedorNombre = prov.getNombre();
        this.proveedordINum = prov.getRazonSocial();
        this.proveedor = prov;
    }

    public void SeleccionarProducto(ArticulosInventario pr) {
        this.codigoProducto = pr.getId();
        this.nombreProducto = pr.getDescripcion();
        this.precioProducto = pr.getCosto();
        if (this.SiICE) {
            this.ice = pr.getCosto() * 15 / 100;
        }
        if (this.SiIVA) {
            this.iva = pr.getCosto() * 12 / 100;
        }

        this.producto = pr;
    }

    public void SeleccionarBodega(Bodega bod) {
        this.codBodega = bod.getCod();
        this.nombreBodega = bod.getNomBodega();
        this.ciudadBodega = bod.getNomCiudad();
        this.direccionBodega = bod.getDireccion();
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

    public int getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(int precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getSubTotalEntrada() {
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

}
