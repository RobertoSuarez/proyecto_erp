/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.controllers;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import com.ventas.dao.ClienteVentaDao;
import com.ventas.dao.DetalleVentaDAO;
import com.ventas.dao.ProductoDAO;
import com.ventas.dao.ProformaDAO;
import com.ventas.models.ClienteVenta;
import com.ventas.models.DetalleProforma;
import com.ventas.models.DetalleVenta;
import com.ventas.models.Producto;
import com.ventas.models.Proforma;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

@Named(value = "ProformaMB")
@ViewScoped

public class ProformaManageBean implements Serializable {

    private ClienteVenta cliente;
     ClienteVenta client;
    private ClienteVentaDao clienteDAO;
    private String clienteIdNum;
    private String clienteNombre;
    String Identificacion;
    String nombrecliente;

    private DetalleVenta productoSeleccionado;
    private ClienteVenta clienteSeleccionado;
    private Proforma proformaSeleccionada;

    private ProductoDAO productoDao;
    private Producto producto;
    private int codigoProducto;
    private String nombreProducto;
    private float precioProducto;
    private double subTotalVenta;

    private Proforma proformas;
    Proforma proformaActual;
    private ProformaDAO profDao;
    private DetalleVenta detalleVenta;
    private DetalleVentaDAO detalleDAO;
    private List<DetalleVenta> listaDetalle;
    private List<Proforma> listaProformas;
    private List<DetalleProforma> listaDetalles;

    private double cantidad;

    private double subtotal12;
    private double subtotal0;
    private double descuento;
    private double ice;
    private double iva;
    private double total;

    private List<ClienteVenta> listaClientes;
    private List<Producto> listaProductos;

    //Constructor
    @PostConstruct
    public void ProformaManagedBean() {
        this.cliente = new ClienteVenta();
        this.clienteDAO = new ClienteVentaDao();
        this.client = new ClienteVenta();

        this.producto = new Producto();
        this.productoDao = new ProductoDAO();
        this.proformas = new Proforma();

        this.codigoProducto = 0;
        this.nombreProducto = "XXXXXX";
        this.subTotalVenta = 0;
        this.nombrecliente = "";
        this.Identificacion = "";

        this.subtotal12 = 0;
        this.subtotal0 = 0;
        this.descuento = 0;
        this.ice = 0;
        this.iva = 0;
        this.total = 0;

        this.listaDetalle = new ArrayList<>();
        this.listaProformas = new ArrayList<>();
        this.cantidad = 1;

        this.productoSeleccionado = null;
        this.clienteSeleccionado = null;
        this.proformaSeleccionada = null;
        this.listaClientes = new ArrayList<>();
        this.listaClientes = clienteDAO.ListarClientes();
        this.listaProductos = productoDao.ListarProductos();
        this.listaDetalles = new ArrayList<>();
        listarProformas();
    }

    //Buscar cliente
    @Asynchronous
    public void BuscarClienteVenta() {
        this.cliente = clienteDAO.BuscarCliente(this.clienteIdNum);
        if (this.cliente != null) {
            this.clienteNombre = this.cliente.getNombre();
        } else {
            System.out.print("No hay cliente");
        }

        if (this.cliente.getNombre() != null) {
            System.out.print("Cliente: " + clienteNombre);
        } else {
            System.out.print("Sin cliente");
        }
    }

    //Buscar Producto
    @Asynchronous
    public void BuscarProducto() {
        this.producto = null;
        this.nombreProducto = "XXXXXX";
        this.cantidad = 1;
        this.precioProducto = 0;

        this.producto = this.productoDao.ObtenerProducto(this.codigoProducto);

        if (this.producto.getDescripcion() == null || this.producto.getDescripcion() == "") {
            addMessage(FacesMessage.SEVERITY_ERROR, "El producto no existe", "Message Content");
        } else {
            this.nombreProducto = this.producto.getDescripcion();
            this.precioProducto = new BigDecimal(this.producto.getPrecioUnitario()).setScale(2, RoundingMode.UP).floatValue();
        }
    }

    //Agregar producto a la lista de detalle
    @Asynchronous
    public void AgregarProductoLista() {
        try {
            if (this.codigoProducto > 0) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setCodprincipal(this.producto.getCodigo());
                detalle.setCantidad(this.cantidad);
                detalle.setDescuento(this.producto.getDescuento());
                detalle.setPrecio(this.precioProducto);
                detalle.setProducto(this.producto);
                detalle.getProducto().setStock((int)this.cantidad);

                detalle.setSubTotal(new BigDecimal(this.precioProducto * this.cantidad).setScale(2, RoundingMode.UP).doubleValue());

                this.producto.setPrecioUnitario(this.precioProducto);
                this.subTotalVenta = this.subTotalVenta + detalle.getSubTotal();
                this.listaDetalle.add(detalle);
                this.cantidad = 1;
                this.codigoProducto = 0;
                this.nombreProducto = "";

                double subtemp = new BigDecimal(this.producto.getPrecioUnitario() * detalle.getCantidad()).setScale(2, RoundingMode.UP).doubleValue();
                if (this.producto.getIva() != 0) {
                    this.subtotal12 += subtemp;
                } else {
                    this.subtotal0 += subtemp;
                }

                this.iva += new BigDecimal(this.producto.getIva() * detalle.getCantidad() * detalle.getPrecio()).setScale(2, RoundingMode.UP).doubleValue();
                this.ice += new BigDecimal(this.producto.getIce() * detalle.getCantidad()).setScale(2, RoundingMode.UP).doubleValue();

                this.total = this.subtotal0 + this.subtotal12 + this.iva + this.ice;

                this.nombreProducto = "XXXXXX";
                this.cantidad = 1;
                this.precioProducto = 0;

                this.producto = null;
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Debe escoger un producto en primera instancia", "Message Content");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Message Content");
        }

    }

    //Eliminar un producto de la lista
    @Asynchronous
    public void EliminarProducto(DetalleVenta detalle) {
        try {
            double subtemp = new BigDecimal(detalle.getProducto().getPrecioUnitario() * detalle.getCantidad()).setScale(2, RoundingMode.UP).doubleValue();
            if (detalle.getProducto().getIva() != 0) {
                this.subtotal12 -= subtemp;
            } else {
                this.subtotal0 -= subtemp;
            }

            this.iva -= new BigDecimal(detalle.getProducto().getIva() * detalle.getCantidad() * detalle.getPrecio()).setScale(2, RoundingMode.UP).doubleValue();
            this.ice -= new BigDecimal(detalle.getProducto().getIce() * detalle.getCantidad()).setScale(2, RoundingMode.UP).doubleValue();

            this.total = this.subtotal0 + this.subtotal12 + this.iva + this.ice;
            this.subTotalVenta -= detalle.getSubTotal();

            this.listaDetalle.remove(detalle);

            PrimeFaces.current().ajax().update("ventaForm");
            addMessage(FacesMessage.SEVERITY_ERROR, "Producto eliminado de la lista", "Message Content");
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().toString(), "Message Content");
        }
    }

    //Mostrar algun mensaje
    @Asynchronous
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    @Asynchronous
    public void RegistrarProforma() {
        try {
            int listSize = 0;
            int codigo = 0;
            boolean estado = true;
            Proforma profor = new Proforma();
            ProformaDAO profordao = new ProformaDAO();
            System.out.println(this.listaDetalle.size());
            if (this.listaDetalle.isEmpty()) {
                addMessage(FacesMessage.SEVERITY_ERROR, "No puede  realizar una proforma nula", "Message Content");
            } else {
                profor.setId_cliente(this.cliente.getIdCliente());
                profor.setId_empleado(1);
                codigo = profordao.codigoproforma();
                profor.setId_proforma(codigo);
                profor.setFecha_actualizacion(ObtenerFecha());
                profor.setFecha_creacion(ObtenerFecha());
                profor.setFecha_expiracion(ObtenerFecha());
                profor.setFecha_autorizacion(ObtenerFecha());
                profor.setProforma_terminada(estado);
                profor.setAceptacion_cliente(estado);
                profor.setEstado("P");
                profor.setBase12((float) this.subtotal12);
                profor.setBase0((float) this.subtotal0);
                profor.setBase_excento_iva(0);
                profor.setIva12((float) this.iva);
                profor.setIce((float) this.ice);
                profor.setTotalproforma((float) this.total);
                profordao.IngresarProforma(profor);
                Producto produc;
                while (listSize <= this.listaDetalle.size()) {
                    produc = new Producto();
                    produc = this.listaDetalle.get(listSize).getProducto();
                    profordao.ingresarDetalleProforma(produc, profor);
                    listSize += 1;
                }
            }
        } catch (SQLException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Message Content");
        }

    }

    @Asynchronous
    public void listarProformas() {
        profDao = new ProformaDAO();
        this.listaProformas = new ArrayList<>();
        try {
            this.listaProformas = this.profDao.retornarProformas();
            if (listaProformas.isEmpty()) {
                addMessage(FacesMessage.SEVERITY_ERROR, "No existe proformas en la Base de Datos", "Message Content");
            }
        } catch (SQLException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Message Content");
        }
    }

    public String ObtenerFecha() {
        String fecha = "";
        String dia;
        String mes;
        Calendar c1 = Calendar.getInstance();
        if (Integer.parseInt(Integer.toString(c1.get(Calendar.DATE))) < 10) {
            dia = "0" + Integer.toString(c1.get(Calendar.DATE)).toString();
        } else {
            dia = Integer.toString(c1.get(Calendar.DATE)).toString();
        }
        if (Integer.parseInt(Integer.toString(c1.get(Calendar.MONTH))) < 10) {
            mes = "0" + Integer.toString(c1.get(Calendar.MONTH) + 1).toString();
        } else {
            mes = Integer.toString(c1.get(Calendar.MONTH)).toString();
        }
        fecha = Integer.toString(c1.get(Calendar.YEAR)).toString() + "/" + mes + "/" + dia;
        return fecha;
    }

    public void SeleccionarCliente(ClienteVenta cl) {
        this.clienteNombre = cl.getNombre();
        this.clienteIdNum = cl.getIdentificacion();
        this.cliente = cl;
    }

    public void SeleccionarProducto(Producto pr) {
        this.codigoProducto = pr.getCodigoAux();
        this.nombreProducto = pr.getDescripcion();
        this.precioProducto = pr.getPrecioUnitario();
        this.producto = pr;
    }

    @Asynchronous
    public void detalleProforma(Proforma profor) throws SQLException {
        this.client = new ClienteVenta();
        this.listaDetalles = new ArrayList<>();
        this.proformaActual = profor;
        this.listaDetalles = this.profDao.listaDetalleProforma(profor.id_proforma);
        this.nombrecliente = profor.getNombreCliente();
    }

    @Asynchronous
    public void rechazarProforma(Proforma profor) throws SQLException {
        String rechazar = "R";
        this.profDao.cambiarEstadoProforma(rechazar, profor.id_proforma);
        listarProformas();
        addMessage(FacesMessage.SEVERITY_ERROR, "Proforma rechazada", "Message Content");
    }
    
     @Asynchronous
    public void aceptarProforma(Proforma profor) throws SQLException {
        String rechazar = "A";
        int factura;
        factura = this.profDao.aceptarProforma(rechazar, profor, ObtenerFecha());
        listarProformas();
        addMessage(FacesMessage.SEVERITY_ERROR, "Proforma aceptada con dactura" + factura , "Message Content");
    }

    //--------------------Getter y Setter-------------------//
    public ClienteVenta getCliente() {
        return cliente;
    }

    public void setCliente(ClienteVenta cliente) {
        this.cliente = cliente;
    }

    public ClienteVentaDao getClienteDAO() {
        return clienteDAO;
    }

    public String getClienteIdNum() {
        return clienteIdNum;
    }

    public void setClienteIdNum(String clienteIdNum) {
        this.clienteIdNum = clienteIdNum;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public ProductoDAO getProductoDao() {
        return productoDao;
    }

    public void setProductoDao(ProductoDAO productoDao) {
        this.productoDao = productoDao;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public DetalleVenta getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(DetalleVenta detalleVenta) {
        this.detalleVenta = detalleVenta;
    }

    public DetalleVentaDAO getDetalleDAO() {
        return detalleDAO;
    }

    public void setDetalleDAO(DetalleVentaDAO detalleDAO) {
        this.detalleDAO = detalleDAO;
    }

    public List<DetalleVenta> getListaDetalle() {
        return listaDetalle;
    }

    public Proforma getProformas() {
        return proformas;
    }

    public void setProformas(Proforma proformas) {
        this.proformas = proformas;
    }

    public List<Proforma> getListaProformas() {
        return listaProformas;
    }

    public void setListaProformas(List<Proforma> listaProformas) {
        this.listaProformas = listaProformas;
    }

    public void setListaDetalle(List<DetalleVenta> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubTotalVenta() {
        return subTotalVenta;
    }

    public void setSubTotalVenta(double subTotalVenta) {
        this.subTotalVenta = subTotalVenta;
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

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getIce() {
        return ice;
    }

    public void setIce(double ice) {
        this.ice = ice;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public DetalleVenta getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(DetalleVenta productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public ProformaDAO getProfDao() {
        return profDao;
    }

    public void setProfDao(ProformaDAO profDao) {
        this.profDao = profDao;
    }

    public List<ClienteVenta> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<ClienteVenta> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public ClienteVenta getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(ClienteVenta clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public Proforma getProformaSeleccionada() {
        return proformaSeleccionada;
    }

    public void setProformaSeleccionada(Proforma proformaSeleccionada) {
        this.proformaSeleccionada = proformaSeleccionada;
    }

    public void setClienteDAO(ClienteVentaDao clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public String getIdentificacion() {
        return Identificacion;
    }

    public void setIdentificacion(String Identificacion) {
        this.Identificacion = Identificacion;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public Proforma getProformaActual() {
        return proformaActual;
    }

    public void setProformaActual(Proforma proformaActual) {
        this.proformaActual = proformaActual;
    }

    public List<DetalleProforma> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<DetalleProforma> listaDetalles) {
        this.listaDetalles = listaDetalles;
    } 

    public ClienteVenta getClient() {
        return client;
    }

    public void setClient(ClienteVenta client) {
        this.client = client;
    }
    
    

    //Agregar producto a la lista de detalle
    public void AgregarProductoLista2() {
        if (this.producto.getCodigo() > 0) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setCodprincipal(this.producto.getCodigo());
            detalle.setCantidad(this.cantidad);
            detalle.setDescuento(this.producto.getDescuento());
            detalle.setPrecio(this.precioProducto);
            detalle.setProducto(this.producto);

            BigDecimal controldecimal = new BigDecimal((this.cantidad * this.precioProducto)).setScale(2, RoundingMode.UP);
            detalle.setSubTotal(controldecimal.doubleValue());
            this.subTotalVenta = this.subTotalVenta + controldecimal.doubleValue();

            this.listaDetalle.add(detalle);
            this.cantidad = 1;
            this.codigoProducto = 0;
            this.nombreProducto = "XXXXXX";

            if (this.producto.getIva() != 0) {
                this.subtotal12 += this.precioProducto * detalle.getCantidad();
            } else {
                this.subtotal0 += this.precioProducto * detalle.getCantidad();
            }
            this.subtotal12 = Math.round(this.subtotal12 * 100.0) / 100.0;
            this.subtotal0 = Math.round(this.subtotal0 * 100.0) / 100.0;
            this.iva = Math.round(((this.iva + this.producto.getIva()) * detalle.getCantidad()) * 100.0) / 100.0;
            this.ice = Math.round(((this.ice + this.producto.getIce()) * detalle.getCantidad()) * 100.0) / 100.0;

            this.total = this.subtotal0 + this.subtotal12 + this.iva + this.ice;
            this.precioProducto = 0;
            this.producto = null;
        } else {
            System.out.println("No hay producto seleccionado");
        }
    }

}
