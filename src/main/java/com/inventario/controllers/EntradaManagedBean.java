/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.controllers;

import com.cuentasporpagar.daos.ProveedorDAO;
import com.cuentasporpagar.models.Proveedor;
import com.inventario.DAO.ArticulosInventarioDAO;
import com.inventario.DAO.EntradaDao;
import com.inventario.DAO.EntradaDetalleDAO;
import com.inventario.models.ArticulosInventario;
import com.inventario.models.EntradaDetalleInventario;
import com.inventario.models.EntradaInventario;
import java.io.Serializable;
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
import java.util.Date;

/**
 *
 * @author angul
 */
@Named(value = "EntradaMB")
@ViewScoped
public class EntradaManagedBean implements Serializable {
    
    private Proveedor cliente;
    private ProveedorDAO clienteDAO;
    private String clienteIdNum;
    private String clienteNombre;
    
    private EntradaDao enntradaDAO;
    
    private EntradaDetalleInventario productoSeleccionado;
    private Proveedor clienteSeleccionado;

    private ArticulosInventarioDAO productoDao;
    private ArticulosInventario producto;
    private int codigoProducto;
    private String nombreProducto;
    private int precioProducto;
    private int subTotalVenta;

    private EntradaDetalleInventario detalleVenta;
    private EntradaDetalleDAO detalleDAO;
    private List<EntradaDetalleInventario> listaDetalle;
    private int cantidad;

    private int subtotal12;
    private int subtotal0;
    private int descuento;
    private int ice;
    private int iva;
    private int total;

    private EntradaInventario venta;
    private EntradaDao ventaDao;

    private int efectivo;
    private int cambio;
    private int diasPago;
    
    private List<Proveedor> listaClientes;
    private List<ArticulosInventario> listaProductos;

    public List<EntradaInventario> getListaEntradas() {
        return listaEntradas;
    }

    public void setListaEntradas(List<EntradaInventario> listaEntradas) {
        this.listaEntradas = listaEntradas;
    }
    private List<EntradaInventario> listaEntradas;
    
    //Constructor
    @PostConstruct
    public void VentaManagedBean() {
        this.cliente = new Proveedor();
        this.clienteDAO = new ProveedorDAO();

        this.producto = new ArticulosInventario();
        this.productoDao = new ArticulosInventarioDAO();
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
        
        this.venta = new EntradaInventario();
        this.ventaDao = new EntradaDao();
        
        this.listaEntradas = ventaDao.getEntradas();
        this.productoSeleccionado = null;
        this.clienteSeleccionado=null;

        

        this.efectivo = 0;
        this.cambio = 0;
        this.diasPago = 0;

        this.clienteDAO = new ProveedorDAO();
        this.detalleDAO = new EntradaDetalleDAO();
        this.listaClientes = new ArrayList<>();
        this.listaClientes = clienteDAO.ListarProveedor();
        this.listaProductos = productoDao.getArticulos();
        
        System.out.print(listaClientes.get(0).getNombre());
    }
    //Mostrar algun mensaje
    @Asynchronous
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    //Buscar cliente
    @Asynchronous
    public void BuscarClienteVenta() {
        this.cliente = clienteDAO.BuscarProveedor(this.clienteIdNum);
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
            this.precioProducto =  this.producto.getCosto();
        }
    }

    //Agregar producto a la lista de detalle
    @Asynchronous
    public void AgregarProductoLista() {
        try {
            if (this.producto.getId()> 0) {
                if (this.producto.getMax_stock()< this.cantidad) {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No puede agregar más unidades de las existentes (" + this.producto.getMax_stock()+ ")");
                } else {
                    if (this.cantidad <= 0) {
                        addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese un valor válido");
                    } else {

                        //Ingreso de valores al detalle de entrada
                        EntradaDetalleInventario detalle = new EntradaDetalleInventario();
                        detalle.setIdEntrada(this.producto.getCod());
                        detalle.setCant(this.cantidad);
                        detalle.setIva(this.producto.getIva());
                        detalle.setCosto(this.producto.getCosto());
                        detalle.setSubtotal(this.producto.getCosto()* this.cantidad);
          

                        //Cálculo de los valores
                        this.subTotalVenta = this.subTotalVenta + detalle.getSubtotal() ;
                        this.listaDetalle.add(detalle);
                        this.cantidad = 1;
                        this.codigoProducto = 0;
                        this.nombreProducto = "";
                        double subtemp =  (this.producto.getCosto()* detalle.getCant());
                        if (this.producto.getIva() != 0) {
                            this.subtotal12 += subtemp;
                        } else {
                            this.subtotal0 += subtemp;
                        }

                        this.iva += this.producto.getIva() * detalle.getCant()* detalle.getCosto();
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
            double subtemp = detalle.getArticulosInventario().getCosto()* detalle.getCant();
            if (detalle.getArticulosInventario().getIva() != 0) {
                this.subtotal12 -= subtemp;
            } else {
                this.subtotal0 -= subtemp;
            }

            this.iva -= (detalle.getArticulosInventario().getIva() * detalle.getCant()* detalle.getCosto());
            this.ice -= (detalle.getArticulosInventario().getIce() * detalle.getCant());

            this.total = this.subtotal0 + this.subtotal12 + this.iva + this.ice;
            this.subTotalVenta -= detalle.getSubtotal();

            this.listaDetalle.remove(detalle);

            PrimeFaces.current().ajax().update("ventaForm");
            System.out.println("Eliminado");
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
        }
    }


    @Asynchronous
    public String RegistrarVenta(int formaPago) {
        try {
            EntradaInventario ventaActual = new EntradaInventario();
            int listSize = 0;
            if (this.listaDetalle.isEmpty()) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No puede  realizar una venta nula");
            } else {
                if (this.clienteNombre == null || this.clienteNombre == "") {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe elegir un cliente para la venta");
                } else {
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    String currentDate = df.format(new Date());
                    Date currentDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(currentDate);

                    //Asignar valores a la venta
                    ventaActual.setProveedor(this.cliente);
                    ventaActual.setIdProveedor(this.cliente.getIdProveedor());
                    ventaActual.setIdBodega(1);
                    ventaActual.setNumComprobante("5478");
                    ventaActual.setFecha(currentDate2);


                    //Verificación en consola
                    System.out.println(ventaActual.getProveedor().getNombre());
       

                    //Guardar la venta desde DAO
                    int ventaRealizada = this.ventaDao.GuardarEntrada(ventaActual);

                    //Verificar que se haya ingresado la venta
                    if (ventaRealizada == 0) {
                        addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo realizar la venta. Revise los datos ingresados");
                    } else {
                        System.out.println("Venta realizada con Factura #" + ventaRealizada);

                        EntradaDetalleDAO daoDetail = new EntradaDetalleDAO();

                        //Registro de cada producto (detalle) de la venta en la BD
                        while (listSize < this.listaDetalle.size()) {
                            int codProd = this.listaDetalle.get(listSize).getArticulosInventario().getId();
                            double qty = this.listaDetalle.get(listSize).getCant();
                       
                            double price = this.listaDetalle.get(listSize).getCosto();

                            System.out.println(this.listaDetalle.get(listSize).getArticulosInventario().getDescripcion());
                            System.out.println(ventaRealizada + "-" + codProd + "-" + qty + "-"  + "-" + price);
                            daoDetail.RegistrarProductos(ventaRealizada, codProd, qty, price);
                            listSize += 1;
                        }
                        
                        return "listaVenta";
                    }
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
        }
        return null;
    }
    
    public void SeleccionarCliente(Proveedor cl){
        this.clienteNombre = cl.getNombre();
        this.clienteIdNum = cl.getRazonSocial();
        this.cliente = cl;
    }
    
    public void SeleccionarProducto(ArticulosInventario pr){
    this.codigoProducto = pr.getCod();
    this.nombreProducto = pr.getDescripcion();
    this.precioProducto = pr.getCosto();
    this.producto = pr;
}

    public Proveedor getCliente() {
        return cliente;
    }

    public void setCliente(Proveedor cliente) {
        this.cliente = cliente;
    }

    public ProveedorDAO getClienteDAO() {
        return clienteDAO;
    }

    public void setClienteDAO(ProveedorDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
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

    public EntradaDetalleInventario getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(EntradaDetalleInventario productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public Proveedor getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Proveedor clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
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

    public int getSubTotalVenta() {
        return subTotalVenta;
    }

    public void setSubTotalVenta(int subTotalVenta) {
        this.subTotalVenta = subTotalVenta;
    }

    public EntradaDetalleInventario getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(EntradaDetalleInventario detalleVenta) {
        this.detalleVenta = detalleVenta;
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

    public EntradaInventario getVenta() {
        return venta;
    }

    public void setVenta(EntradaInventario venta) {
        this.venta = venta;
    }

    public EntradaDao getVentaDao() {
        return ventaDao;
    }

    public void setVentaDao(EntradaDao ventaDao) {
        this.ventaDao = ventaDao;
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

    public List<Proveedor> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Proveedor> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<ArticulosInventario> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ArticulosInventario> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public EntradaDao getEnntradaDAO() {
        return enntradaDAO;
    }

    public void setEnntradaDAO(EntradaDao enntradaDAO) {
        this.enntradaDAO = enntradaDAO;
    }

   
}
