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
import com.ventas.dao.PreciosDAO;
import com.ventas.dao.ProductoVentaDAO;
import com.ventas.dao.ProformaDAO;
import com.ventas.dao.VentaDAO;
import com.ventas.models.ClienteVenta;
import com.ventas.models.DetalleProforma;
import com.ventas.models.DetalleProforma;
import com.ventas.models.ProductoVenta;
import com.ventas.models.Proforma;
import com.ventas.models.Venta;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
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

    //Objetos que se usan en la venta
    private ClienteVenta cliente;
    private ProductoVenta productoActual;
    private DetalleProforma productoSeleccionado;
    private Proforma proforma;

    //Objetos para acceder a datos
    private ClienteVentaDao clienteDAO;
    private ProductoVentaDAO productoDao;
    private ProformaDAO proformaDao;
    private PreciosDAO preciosDAO;

    //Variables para manejar el descuento
    private double descuentoGeneral;
    private double descuentoActual;
    private double descuentoAcumulado;

    //Variables para controlar valores
    private int codigoProdBuscar;
    private double cantidad;
    private double subTotalProforma;
    private Date fechaVencimiento;
    private Date fechaActual;

    //Variables acumulativas para información
    private double subtotal12;
    private double subtotal0;
    private double descuento;
    private double ice;
    private double iva;
    private double total;

    //Listas que se usan en la venta
    private List<ClienteVenta> listaClientes;
    private List<ProductoVenta> listaProductos;
    private List<DetalleProforma> listaDetalle;

    private String visible;
    private SimpleDateFormat dateFormat;

    //Constructor
    @PostConstruct
    public void ProformaManagedBean() {
        this.cliente = new ClienteVenta();
        this.productoActual = new ProductoVenta();

        this.clienteDAO = new ClienteVentaDao();
        this.preciosDAO = new PreciosDAO();

        this.productoDao = new ProductoVentaDAO();
        this.subTotalProforma = 0;
        this.visible = "false";
        this.fechaVencimiento = new Date(System.currentTimeMillis());
        this.fechaActual = new Date(System.currentTimeMillis());
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                
        this.descuentoGeneral = 0;
        this.descuentoActual = 0;
        this.descuentoAcumulado = 0;

        this.subtotal12 = convertTwoDecimal(0);
        this.subtotal0 = convertTwoDecimal(0);
        this.descuento = convertTwoDecimal(0);
        this.ice = convertTwoDecimal(0);
        this.iva = convertTwoDecimal(0);
        this.total = convertTwoDecimal(0);

        this.listaDetalle = new ArrayList<>();
        this.cantidad = 1;

        this.clienteDAO = new ClienteVentaDao();
        this.listaClientes = new ArrayList<>();
        this.listaClientes = clienteDAO.ListarClientes();
        this.listaProductos = productoDao.ListarProductos();

        this.proforma = new Proforma();
        this.proformaDao = new ProformaDAO();
    }

    /**
     * Recibe un cliente de venta como parámetro y carga sus datos en la clase
     * controlador, en el objeto cienteSeleccionado y muestra datos en la
     * interfaz de usuario.
     *
     * @param cliente
     */
    public void SeleccionarCliente(ClienteVenta clienteSelect) {
        int tipoAnterior = cliente.getIdTipoCliente();
        this.cliente = clienteSelect;

        this.descuentoGeneral = preciosDAO.getGeneralDiscount(cliente.getIdTipoCliente());
        this.visible = "true";

        if (!listaDetalle.isEmpty() && cliente.getIdTipoCliente() != tipoAnterior) {
            recalcularDescuentos();
        }

        descuento = descuentoGeneral + descuentoActual;
    }

    /**
     * Recibe un producto que es guardado en la clase controladora como el
     * producto actual que podrá ser agregado a la venta
     *
     * @param producto
     */
    public void SeleccionarProducto(ProductoVenta producto) {
        this.productoActual = producto;
        this.descuentoActual = preciosDAO.getDiscountByProduct(cliente.getIdTipoCliente(), producto.getCodigo());
        descuento = descuentoGeneral + descuentoActual;
        this.visible = "true";
    }

    /**
     * Agrega un producto a la lista. El producto que hayamos buscado
     * previamente en la interfaz será el que se añada.
     */
    public void AgregarProductoLista() {
        try {
            if (this.productoActual.getCodigo() > 0) {
                if (this.cantidad <= 0) {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese un valor válido");
                } else {
                    if (this.productoActual.isStockeable() && this.productoActual.getStock() < this.cantidad) {
                        addMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Podría haber menor unidades disponiblesd e las que desea al momento de facturar. Consulte antes de generar una factura.");
                    }

                    DetalleProforma detalle = new DetalleProforma();

                    this.listaProductos.remove(productoActual);

                    //Ingreso de valores al detalle
                    detalle.setProducto(this.productoActual);
                    detalle.setCodigoProducto(this.productoActual.getCodigo());
                    detalle.setCantidad(this.cantidad);
                    detalle.setPrice(convertTwoDecimal(this.productoActual.getPrecioUnitario()));
                    detalle.setDescuento(convertTwoDecimal(detalle.getPrice() * ((this.descuentoGeneral + this.descuentoActual) / 100)));
                    detalle.setSubtotal(convertTwoDecimal((detalle.getPrice() - detalle.getDescuento()) * detalle.getCantidad()));

                    //Cálculo de los valores
                    this.subTotalProforma += detalle.getSubtotal();
                    this.descuentoAcumulado += convertTwoDecimal(detalle.getDescuento() * detalle.getCantidad());
                    this.listaDetalle.add(detalle);

                    if (this.productoActual.getIva() != 0) {
                        this.subtotal12 += convertTwoDecimal(detalle.getSubtotal());
                    } else {
                        this.subtotal0 += convertTwoDecimal(detalle.getSubtotal());
                    }

                    this.iva += convertTwoDecimal(this.productoActual.getIva() / 100 * detalle.getSubtotal());
                    this.ice += convertTwoDecimal(this.productoActual.getIce() * detalle.getCantidad());
                    this.total = convertTwoDecimal(this.subtotal0 + this.subtotal12 + this.iva + this.ice);

                    this.descuentoActual = 0;
                    descuento = descuentoGeneral + descuentoActual;

                    this.productoActual = new ProductoVenta();
                    this.cantidad = 1;
                }
            } else {
                System.out.println("No hay producto seleccionado");
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
        }

    }

    /**
     * Elimina uno de los items que se hayan agregado a la lista de venta.
     * Recibe dicho item como parámetro
     *
     * @param detalle
     */
    public void EliminarProducto(DetalleProforma detalle) {
        try {
            if (detalle.getProducto().getIva() != 0) {
                this.subtotal12 -= convertTwoDecimal(detalle.getSubtotal());
            } else {
                this.subtotal0 -= convertTwoDecimal(detalle.getSubtotal());
            }

            this.iva -= convertTwoDecimal(detalle.getProducto().getIva() / 100 * detalle.getSubtotal());
            this.ice -= convertTwoDecimal(detalle.getProducto().getIce() * detalle.getCantidad());
            this.descuentoAcumulado -= convertTwoDecimal(detalle.getDescuento() * detalle.getCantidad());
            this.total = convertTwoDecimal(this.subtotal0 + this.subtotal12 + this.iva + this.ice);
            this.subTotalProforma -= convertTwoDecimal(detalle.getSubtotal());

            detalle.getProducto().setStock((int) (detalle.getCantidad() + detalle.getProducto().getStock()));
            this.listaDetalle.remove(detalle);
            this.listaProductos.add(detalle.getProducto());

            PrimeFaces.current().ajax().update("ventaForm");
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
        }
    }

    /**
     * Muestra un mensaje en pantalla. Recibe como parámetros la sveridad que
     * determina el color, un título y un detalle.
     *
     * @param severity
     * @param summary
     * @param detail
     */
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    /**
     * Recible un valor de tipo double y lo transforma para que tenga únicamente
     * 2 decimales
     *
     * @param doubleNumero
     * @return double decimalConvertido
     */
    public double convertTwoDecimal(double doubleNumero) {
        double temp = new BigDecimal(doubleNumero).setScale(3, RoundingMode.HALF_UP).doubleValue();
        return temp < 0 ? 0 : temp;
    }

    public String RegistrarProforma() throws IOException, SQLException {
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
                profor.setFecha_expiracion(dateFormat.format(fechaVencimiento));
                profor.setBase12((float) this.subtotal12);
                profor.setBase0((float) this.subtotal0);
                profor.setBase_excento_iva(0);
                profor.setIva12((float) this.iva);
                profor.setIce((float) this.ice);
                profor.setTotalproforma((float) this.total);
                profordao.IngresarProforma(profor);
                ProductoVenta produc;
                for(DetalleProforma det: listaDetalle){
                    profordao.ingresarDetalleProforma(det, profor);
                }
            }

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado", "Proforma Guardada exitosamente"));
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.getExternalContext().redirect("/proyecto_erp/View/ventas/listaProformaPendiente.xhtml?faces-redirect=true");
            return "listaProforma.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Message Content");
        }
        return null;
    }

    private void recalcularDescuentos() {
        try {
            this.iva = convertTwoDecimal(0);
            this.ice = convertTwoDecimal(0);
            this.subTotalProforma = convertTwoDecimal(0);
            this.total = convertTwoDecimal(0);
            this.subtotal0 = convertTwoDecimal(0);
            this.subtotal12 = convertTwoDecimal(0);

            for (DetalleProforma detalle : listaDetalle) {

                detalle.setDescuento(convertTwoDecimal(detalle.getPrice() * ((this.descuentoGeneral + preciosDAO.getDiscountByProduct(cliente.getIdTipoCliente(), detalle.getProducto().getCodigo())) / 100)));
                detalle.setSubtotal(convertTwoDecimal((detalle.getPrice() - detalle.getDescuento()) * detalle.getCantidad()));

                //Cálculo de los valores
                this.subTotalProforma += detalle.getSubtotal();
                this.descuentoAcumulado += convertTwoDecimal(detalle.getDescuento() * detalle.getCantidad());

                if (detalle.getProducto().getIva() != 0) {
                    this.subtotal12 += convertTwoDecimal(detalle.getSubtotal());
                } else {
                    this.subtotal0 += convertTwoDecimal(detalle.getSubtotal());
                }

                this.iva += convertTwoDecimal(detalle.getProducto().getIva() / 100 * detalle.getSubtotal());
                this.ice += convertTwoDecimal(detalle.getProducto().getIce() * detalle.getCantidad());
                this.total = convertTwoDecimal(this.subtotal0 + this.subtotal12 + this.iva + this.ice);
            }
            addMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Se han actualizado los valores de acuerdo con el tipo de cliente");
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
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

    //--------------------Getter y Setter-------------------//
    public ClienteVenta getCliente() {
        return cliente;
    }

    public void setCliente(ClienteVenta cliente) {
        this.cliente = cliente;
    }

    public ProductoVenta getProductoActual() {
        return productoActual;
    }

    public void setProductoActual(ProductoVenta productoActual) {
        this.productoActual = productoActual;
    }

    public DetalleProforma getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(DetalleProforma productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public Proforma getProforma() {
        return proforma;
    }

    public void setProforma(Proforma proforma) {
        this.proforma = proforma;
    }

    public PreciosDAO getPreciosDAO() {
        return preciosDAO;
    }

    public void setPreciosDAO(PreciosDAO preciosDAO) {
        this.preciosDAO = preciosDAO;
    }

    public double getDescuentoAcumulado() {
        return descuentoAcumulado;
    }

    public void setDescuentoAcumulado(double descuentoAcumulado) {
        this.descuentoAcumulado = descuentoAcumulado;
    }

    public int getCodigoProdBuscar() {
        return codigoProdBuscar;
    }

    public void setCodigoProdBuscar(int codigoProdBuscar) {
        this.codigoProdBuscar = codigoProdBuscar;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubTotalProforma() {
        return subTotalProforma;
    }

    public void setSubTotalProforma(double subTotalProforma) {
        this.subTotalProforma = subTotalProforma;
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

    public List<ClienteVenta> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<ClienteVenta> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<ProductoVenta> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoVenta> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<DetalleProforma> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<DetalleProforma> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public double getDescuentoGeneral() {
        return descuentoGeneral;
    }

    public void setDescuentoGeneral(double descuentoGeneral) {
        this.descuentoGeneral = descuentoGeneral;
    }

    public double getDescuentoActual() {
        return descuentoActual;
    }

    public void setDescuentoActual(double descuentoActual) {
        this.descuentoActual = descuentoActual;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }
}
