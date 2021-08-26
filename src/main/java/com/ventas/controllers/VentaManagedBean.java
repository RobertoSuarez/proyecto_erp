/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.controllers;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import com.ventas.dao.ClienteVentaDao;
import com.ventas.dao.DetalleVentaDAO;
import com.ventas.dao.ProductoDAO;
import com.ventas.dao.VentaDAO;
import com.ventas.models.ClienteVenta;
import com.ventas.models.DetalleVenta;
import com.ventas.models.Producto;
import com.ventas.models.Venta;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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

@Named(value = "VentaMB")
@ViewScoped
public class VentaManagedBean implements Serializable {

    private ClienteVenta cliente;
    private ClienteVentaDao clienteDAO;
    private String clienteIdNum;
    private String clienteNombre;

    private DetalleVenta productoSeleccionado;

    private ProductoDAO productoDao;
    private Producto producto;
    private int codigoProducto;
    private String nombreProducto;
    private float precioProducto;
    private double subTotalVenta;

    private DetalleVenta detalleVenta;
    private DetalleVentaDAO detalleDAO;
    private List<DetalleVenta> listaDetalle;
    private double cantidad;

    private double subtotal12;
    private double subtotal0;
    private double descuento;
    private double ice;
    private double iva;
    private double total;

    private Venta venta;
    private VentaDAO ventaDao;

    private double efectivo;
    private double cambio;
    private int diasPago;

    //Constructor
    @PostConstruct
    public void VentaManagedBean() {
        this.cliente = new ClienteVenta();
        this.clienteDAO = new ClienteVentaDao();

        this.producto = new Producto();
        this.productoDao = new ProductoDAO();
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
        this.cantidad = 1;

        this.productoSeleccionado = null;

        this.venta = new Venta();
        this.ventaDao = new VentaDAO();

        this.efectivo = 0;
        this.cambio = 0;
        this.diasPago = 0;

        this.detalleDAO = new DetalleVentaDAO();
    }

    //Buscar cliente
    @Asynchronous
    public void BuscarClienteVenta() {
        this.cliente = clienteDAO.BuscarCliente(this.clienteIdNum);
        if (this.cliente.getNombre() != null) {
            this.clienteNombre = this.cliente.getNombre();
        } else {
            System.out.print("No hay cliente");
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "El cliente no existe o se encuentra inactivo.");
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

        if (this.producto.getDescripcion() == null) {
            System.out.println("Producto nulo");
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "El producto no existe");
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
                if (this.producto.getStock() < this.cantidad) {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No puede agregar más unidades de las existentes (" + this.producto.getStock() + ")");
                } else {
                    if (this.cantidad <= 0) {
                        addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese un valor válido");
                    } else {
                        DetalleVenta detalle = new DetalleVenta();
                        detalle.setCodprincipal(this.producto.getCodigo());
                        detalle.setCantidad(this.cantidad);
                        detalle.setDescuento(this.producto.getDescuento());
                        detalle.setPrecio(new BigDecimal(this.producto.getPrecioUnitario()).setScale(2, RoundingMode.UP).doubleValue());
                        detalle.setProducto(this.producto);

                        detalle.setSubTotal(new BigDecimal(this.producto.getPrecioUnitario() * this.cantidad).setScale(2, RoundingMode.UP).doubleValue());

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
                    }
                }
            } else {
                System.out.println("No hay producto seleccionado");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
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
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
        }
    }

    //Mostrar algun mensaje
    @Asynchronous
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    @Asynchronous
    public void RegistrarVenta(int formaPago) {
        try {
            Venta ventaActual = new Venta();
            int listSize = 0;
            if (this.listaDetalle.isEmpty()) {
                addMessage(FacesMessage.SEVERITY_ERROR, "No puede  realizar una venta nula", "Message Content");
            } else {
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String currentDate = df.format(new Date());

                ventaActual.setCliente(this.cliente);
                ventaActual.setIdCliente(this.cliente.getIdCliente());
                ventaActual.setIdEmpleado(1);
                ventaActual.setIdFormaPago(formaPago);
                ventaActual.setIdDocumento(0);
                ventaActual.setSucursal(1);
                ventaActual.setFechaVenta(currentDate);
                ventaActual.setPuntoEmision(1);
                ventaActual.setSecuencia(0);
                ventaActual.setAutorizacion("849730964");
                ventaActual.setFechaEmision(currentDate);
                ventaActual.setFechaAutorizacion(currentDate);
                ventaActual.setBase12(this.subtotal12);
                ventaActual.setBase0(this.subtotal0);
                ventaActual.setIva(this.iva);
                ventaActual.setIce(this.ice);
                ventaActual.setTotalFactura(this.total);
                ventaActual.setDiasCredito(this.diasPago);

                System.out.println(ventaActual.getCliente().getNombre());
                System.out.println(ventaActual.getTotalFactura());

                int ventaRealizada = this.ventaDao.GuardarVenta(ventaActual);

                if (ventaRealizada == 0) {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo realizar la venta. Revise los datos ingresados");
                } else {
                    System.out.println("Venta realizada con Factura #" + ventaRealizada);

                    DetalleVentaDAO daoDetail = new DetalleVentaDAO();

                    while (listSize < this.listaDetalle.size()) {
                        int codProd = this.listaDetalle.get(listSize).getProducto().getCodigo();
                        double qty = this.listaDetalle.get(listSize).getCantidad();
                        double dsc = this.listaDetalle.get(listSize).getDescuento();
                        double price = this.listaDetalle.get(listSize).getPrecio();

                        System.out.println(this.listaDetalle.get(listSize).getProducto().getDescripcion());
                        System.out.println(ventaRealizada + "-" + codProd + "-" + qty + "-" + dsc + "-" + price);
                        daoDetail.RegistrarProductos(ventaRealizada, codProd, qty, dsc, price);
                        listSize += 1;
                    }
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/proyecto_erp/faces/View/ventas/listaVentas.xhtml");
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
        }
    }

    @Asynchronous
    public void ActualizarCambio() {
        if (this.total > 0) {
            this.cambio = this.total - this.efectivo;
        }
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

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public VentaDAO getVentaDao() {
        return ventaDao;
    }

    public void setVentaDao(VentaDAO ventaDao) {
        this.ventaDao = ventaDao;
    }

    public double getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(double efectivo) {
        this.efectivo = efectivo;
    }

    public double getCambio() {
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }

    public int getDiasPago() {
        return diasPago;
    }

    public void setDiasPago(int diasPago) {
        this.diasPago = diasPago;
    }

}
