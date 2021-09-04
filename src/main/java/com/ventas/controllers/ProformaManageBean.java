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
    private ClienteVentaDao clienteDAO;
    private String clienteIdNum;
    private String clienteNombre;

    private DetalleVenta productoSeleccionado;
    private ClienteVenta clienteSeleccionado;

    private ProductoDAO productoDao;
    private Producto producto;
    private int codigoProducto;
    private String nombreProducto;
    private float precioProducto;
    private double subTotalVenta;

    private Proforma proformas;
    private ProformaDAO profDao;
    private DetalleVenta detalleVenta;
    private DetalleVentaDAO detalleDAO;
    private List<DetalleVenta> listaDetalle;
    private List<Proforma> listaProformas;
    private double cantidad;

    private double subtotal12;
    private double subtotal0;
    private double descuento;
    private double ice;
    private double iva;
    private double total;
    
    private List<ClienteVenta> listaClientes;

    //Constructor
    @PostConstruct
    public void ProformaManagedBean() {
        this.cliente = new ClienteVenta();
        this.clienteDAO = new ClienteVentaDao();

        this.producto = new Producto();
        this.productoDao = new ProductoDAO();
        this.proformas = new Proforma();
        
        this.codigoProducto = 0;
        this.nombreProducto = "XXXXXX";
        this.subTotalVenta = 0;

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
        this.clienteSeleccionado=null;
        this.listaClientes = new ArrayList<>();
        this.listaClientes = clienteDAO.ListarClientes();
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
            System.out.println("Producto nulo");
            addMessage(FacesMessage.SEVERITY_ERROR, "El producto no existe", "Message Content");
        } else {
            System.out.println("Existe el producto" + this.nombreProducto);
            this.nombreProducto = this.producto.getDescripcion();
            this.precioProducto = new BigDecimal(this.producto.getPrecioUnitario()).setScale(2, RoundingMode.UP).floatValue();
        }
    }

    //Agregar producto a la lista de detalle
    @Asynchronous
    public void AgregarProductoLista() {
        try {
            if (this.producto.getCodigo() > 0) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setCodprincipal(this.producto.getCodigo());
                detalle.setCantidad(this.cantidad);
                detalle.setDescuento(this.producto.getDescuento());
                detalle.setPrecio(this.precioProducto);
                detalle.setProducto(this.producto);

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
                System.out.println("No hay producto seleccionado");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().toString(), "Message Content");
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
            System.out.println("Eliminado");
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
            DetalleVenta temp = new DetalleVenta();
            if (this.listaDetalle.isEmpty()) {
                addMessage(FacesMessage.SEVERITY_ERROR, "No puede  realizar una proforma nula", "Message Content");
            } else {
                System.out.println("Registrando proforma . . .");
                profor.setId_cliente(this.cliente.getIdCliente());
                System.out.println("Proforma registrada con cliente:" + this.cliente.getIdCliente());
                profor.setId_empleado(1);
                System.out.println("Proforma registrada con empleado 1");
                codigo = profordao.codigoproforma();
                profor.setId_proforma(codigo);
                System.out.println("Proforma registrada con codigo:" + profor.getId_proforma());
                profor.setFecha_actualizacion(ObtenerFecha());
                System.out.println("Proforma registrada con fecha:" + ObtenerFecha());
                profor.setFecha_creacion(ObtenerFecha());
                System.out.println("Proforma registrada con fecha:" + ObtenerFecha());
                profor.setFecha_expiracion(ObtenerFecha());
                System.out.println("Proforma registrada con fecha:" + ObtenerFecha());
                profor.setFecha_autorizacion(ObtenerFecha());
                System.out.println("Proforma registrada con fecha:" + ObtenerFecha());
                profor.setProforma_terminada(estado);
                System.out.println("Proforma registrada con estado:" + estado);
                profor.setAceptacion_cliente(estado);
                System.out.println("Proforma registrada con estado:" + estado);
                profor.setEstado("P");
                System.out.println("Proforma registrada con estado pendiente");
                profor.setBase12((float) this.subtotal12);
                profor.setBase0((float) this.subtotal0);
                profor.setBase_excento_iva(0);
                profor.setIva12((float) this.iva);
                profor.setIce((float) this.ice);
                profor.setTotalproforma((float) this.total);
                profordao.IngresarProforma(profor);
                System.out.println("Proforma Registrada");
                while (listSize < this.listaDetalle.size()) {
                    temp = this.listaDetalle.get(listSize);
//                    this.proformaDAO.ingresarDetalleProforma(temp, profor);
//                    System.out.println("Detalle Proforma ingresada");
                    listSize += 1;
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().toString(), "Message Content");
        }

    }

    @Asynchronous
    public void listarProformas() {
        profDao = new ProformaDAO();
        this.listaProformas = new ArrayList<>();
        try {
            System.out.println("Llenando lista de proforma");
            this.listaProformas = this.profDao.retornarProformas();
            System.out.println("Lista llenada");
            if (listaProformas.isEmpty()) {
                addMessage(FacesMessage.SEVERITY_ERROR, "No existe proformas en la Base de Datos", "Message Content");
            }
        } catch (SQLException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage().toString(), "Message Content");
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

    public void SeleccionarCliente(ClienteVenta cl){
        this.clienteNombre = cl.getNombre();
        this.clienteIdNum = cl.getIdentificacion();
        this.cliente = cl;
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
