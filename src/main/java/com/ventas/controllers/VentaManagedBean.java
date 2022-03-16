/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.controllers;

import com.ventas.dao.ClienteVentaDao;
import com.ventas.dao.DetalleVentaDAO;
import com.ventas.dao.PreciosDAO;
import com.ventas.dao.ProductoVentaDAO;
import com.ventas.dao.VentaDAO;
import com.ventas.models.ClienteVenta;
import com.ventas.models.DetalleVenta;
import com.ventas.models.ProductoVenta;
import com.ventas.models.Venta;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

@Named(value = "VentaMB")
@ViewScoped
public class VentaManagedBean implements Serializable {

    //Objetos que se usan en la venta
    private ClienteVenta cliente;
    private ProductoVenta productoActual;
    private DetalleVenta productoSeleccionado;
    private Venta venta;

    //Objetos para acceder a datos
    private ClienteVentaDao clienteDAO;
    private ProductoVentaDAO productoDao;
    private DetalleVentaDAO detalleDAO;
    private VentaDAO ventaDao;
    private PreciosDAO preciosDAO;

    //Variables para manejar el descuento
    private double descuentoGeneral;
    private double descuentoActual;
    private double descuentoAcumulado;

    //Variables para controlar valores
    private int codigoProdBuscar;
    private double cantidad;
    private double subTotalVenta;

    //Variables acumulativas para información
    private double subtotal12;
    private double subtotal0;
    private double descuento;
    private double ice;
    private double iva;
    private double total;

    //Variables para controlar la forma de pago
    private double efectivo;
    private double cambio;
    private int diasPago;

    //Listas que se usan en la venta
    private List<ClienteVenta> listaClientes;
    private List<ProductoVenta> listaProductos;
    private List<DetalleVenta> listaDetalle;

    //------------------------NO SE PLANEA USAR------------------------//
    private boolean tipo;
    private String visible;

    /**
     * Iniicaliza la clase controladora VentaManagedBean. Inicializa los objetos
     * que forman parte de la misma.
     */
    @PostConstruct
    public void VentaManagedBean() {
        this.cliente = new ClienteVenta();
        this.productoActual = new ProductoVenta();

        this.clienteDAO = new ClienteVentaDao();
        this.preciosDAO = new PreciosDAO();

        this.productoDao = new ProductoVentaDAO();
        this.subTotalVenta = 0;
        this.visible = "false";

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
        this.detalleDAO = new DetalleVentaDAO();
        this.listaClientes = new ArrayList<>();
        this.listaClientes = clienteDAO.ListarClientes();
        this.listaProductos = productoDao.ListarProductos();

        //Se podrian quitar
        this.venta = new Venta();
        this.ventaDao = new VentaDAO();

        this.efectivo = 0;
        this.cambio = 0;
        this.diasPago = 0;
    }

    /**
     * Recibe un cliente de venta como parámetro y carga sus datos en la clase
     * controlador, en el objeto cienteSeleccionado y muestra datos en la
     * interfaz de usuario.
     *
     * @param cliente
     */
    public void SeleccionarCliente(ClienteVenta cliente) {
        this.cliente = cliente;

        int idcliente = preciosDAO.idtipocliente(cliente.getIdentificacion());
        
        this.descuentoGeneral = preciosDAO.getGeneralDiscount(idcliente);
        this.visible = "true";

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
        this.descuentoActual = preciosDAO.getDiscountByProduct(preciosDAO.idtipocliente(cliente.getIdentificacion()), producto.getCodigo());
        descuento = descuentoGeneral + descuentoActual;
        this.visible = "true";
    }

    /**
     * Busca un producto por el identificador. Si existe el producto entonces lo
     * guarda en la clase controladora.
     */
    public void BuscarProducto() {
        this.productoActual = new ProductoVenta();
        this.cantidad = 1;

        this.productoActual = this.productoDao.ObtenerProducto(this.codigoProdBuscar);

        if (this.productoActual.getNombre() == null) {
            System.out.println("Producto nulo");
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "El producto no existe");
        } else {
            System.out.println("Existe el producto" + this.productoActual.getNombre());//Por borrar, solo controla
            this.productoActual.setPrecioUnitario((float) convertTwoDecimal(this.productoActual.getPrecioUnitario()));
        }
    }

    /**
     * Agrega un producto a la lista. El producto que hayamos buscado
     * previamente en la interfaz será el que se añada.
     */
    public void AgregarProductoLista() {
        try {
            if (this.productoActual.getCodigo() > 0) {
                if (this.productoActual.getStock() < this.cantidad) {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No puede agregar más unidades de las existentes (" + this.productoActual.getStock() + ")");
                } else {
                    if (this.cantidad <= 0) {
                        addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese un valor válido");
                    } else {
                        DetalleVenta detalle = new DetalleVenta();

                        //Ingreso de valores al detalle
                        detalle.setProducto(this.productoActual);
                        detalle.setCodigo(this.productoActual.getCodigo());
                        detalle.setCantidad(this.cantidad);
                        detalle.setPrecio(convertTwoDecimal(this.productoActual.getPrecioUnitario()));
                        detalle.setDescuento(convertTwoDecimal(detalle.getPrecio() * ((this.descuentoGeneral + this.descuentoActual) / 100)));
                        detalle.setSubTotal(convertTwoDecimal((detalle.getPrecio() - detalle.getDescuento()) * detalle.getCantidad()));
                        
                        //Cálculo de los valores
                        this.subTotalVenta += detalle.getSubTotal();
                        this.descuentoAcumulado += convertTwoDecimal(detalle.getDescuento() * detalle.getCantidad());
                        this.listaDetalle.add(detalle);

                        if (this.productoActual.getIva() != 0) {
                            this.subtotal12 += convertTwoDecimal(detalle.getSubTotal());
                        } else {
                            this.subtotal0 += convertTwoDecimal(detalle.getSubTotal());
                        }

                        this.iva += convertTwoDecimal(this.productoActual.getIva() / 100 * detalle.getSubTotal());
                        this.ice += convertTwoDecimal(this.productoActual.getIce() * detalle.getCantidad());
                        this.total = convertTwoDecimal(this.subtotal0 + this.subtotal12 + this.iva + this.ice);

                        this.descuentoActual = 0;
                        descuento = descuentoGeneral + descuentoActual;
                        
                        this.productoActual = new ProductoVenta();
                        this.cantidad = 1;
                    }
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
    public void EliminarProducto(DetalleVenta detalle) {
        try {
            if (detalle.getProducto().getIva() != 0) {
                this.subtotal12 -= convertTwoDecimal(detalle.getSubTotal());
            } else {
                this.subtotal0 -= convertTwoDecimal(detalle.getSubTotal());
            }

            this.iva -= convertTwoDecimal(detalle.getProducto().getIva() / 100 * detalle.getSubTotal());
            this.ice -= convertTwoDecimal(detalle.getProducto().getIce() * detalle.getCantidad());
            this.descuentoAcumulado -= convertTwoDecimal(detalle.getDescuento() * detalle.getCantidad());
            this.total = convertTwoDecimal(this.subtotal0 + this.subtotal12 + this.iva + this.ice);
            this.subTotalVenta -= convertTwoDecimal(detalle.getSubTotal());

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

    /**
     * Toma todos los datos de la interfaz de usuario y los envía a la base de
     * datos, registrando una nueva factura de venta.
     *
     * @param formaPago
     * @return String redireccion
     */
    public String RegistrarVenta(int formaPago) {
        try {
            Venta ventaActual = new Venta();
            if (this.listaDetalle.isEmpty()) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No puede  realizar una venta nula");
            } else {
                if (this.cliente.getNombre() == null || this.cliente.getNombre() == "") {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe elegir un cliente para la venta");
                } else {
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    String currentDate = df.format(new Date());

                    //Asignar valores a la venta
                    ventaActual = cargarDatosVenta(ventaActual, formaPago, currentDate);

                    //Guardar la venta y su detalle desde DAO
                    int ventaRealizada = this.ventaDao.GuardarVenta(ventaActual);
                    registrarItemsVenta(ventaRealizada);

                    //Verificar que se haya ingresado la venta
                    if (ventaRealizada == 0) {
                        addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo realizar la venta. Revise los datos ingresados");
                    } else {
                        //Registro de cada producto (detalle) de la venta en la BD
                        this.ventaDao.insertasiento(ventaActual);

                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado", "Compra Guardada satisfactoriamente"));
                        context.getExternalContext().getFlash().setKeepMessages(true);
                        return "listaVentas.xhtml?faces-redirect=true";
                    }
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
        }
        return null;
    }

    /**
     * Método interno que se encarga de registrar cada uno de los items de una
     * venta una vez que esta ya ha sido guardada. Recibe como parámetro el
     * código de dicha venta.
     *
     * @param codVenta
     */
    private void registrarItemsVenta(int codVenta) {
        int listSize = 0;
        while (listSize < this.listaDetalle.size()) {
            int codProd = this.listaDetalle.get(listSize).getProducto().getCodigo();
            double qty = convertTwoDecimal(this.listaDetalle.get(listSize).getCantidad());
            double dsc = convertTwoDecimal(this.listaDetalle.get(listSize).getDescuento());
            double price = convertTwoDecimal(this.listaDetalle.get(listSize).getPrecio());
            DetalleVentaDAO daoDetail = new DetalleVentaDAO();
            daoDetail.RegistrarProductos(codVenta, codProd, qty, dsc, price);
            listSize += 1;
        }
    }

    /**
     * Función privada para completar el registro de la venta. Recibe una objeto
     * venta, un entero que representa la forma de pago, y una cadena que
     * representa la fecha. Con estos datos se rellenará la venta obtenida en
     * los parámetros.
     *
     * @param venta
     * @param formaPago
     * @param currentDate
     * @return Venta venta
     */
    private Venta cargarDatosVenta(Venta venta, int formaPago, String currentDate) {
        venta.setCliente(this.cliente);
        venta.setIdCliente(this.cliente.getIdCliente());
        venta.setIdEmpleado(1);
        venta.setIdFormaPago(formaPago);
        venta.setSucursal(1);
        venta.setFechaVenta(currentDate);
        venta.setPuntoEmision(1);
        venta.setSecuencia(0);
        venta.setAutorizacion("849730964");
        venta.setFechaEmision(currentDate);
        venta.setFechaAutorizacion(currentDate);
        venta.setBase12(this.subtotal12);
        venta.setBase0(this.subtotal0);
        venta.setIva(this.iva);
        venta.setIce(this.ice);
        venta.setTotalFactura(this.total);
        venta.setDiasCredito(this.diasPago);
        return venta;
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

    public DetalleVenta getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(DetalleVenta productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
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

    public int getDiasPago() {
        return diasPago;
    }

    public void setDiasPago(int diasPago) {
        this.diasPago = diasPago;
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

    public List<DetalleVenta> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<DetalleVenta> listaDetalle) {
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

}
