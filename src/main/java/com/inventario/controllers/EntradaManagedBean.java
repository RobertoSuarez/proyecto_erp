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
import com.inventario.DAO.BodegaDAO;
import com.inventario.DAO.EntradaDao;
import com.inventario.DAO.EntradaDetalleDAO;
import com.inventario.models.ArticulosInventario;
import com.inventario.models.Bodega;
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
    
    private Proveedor proveedor;
    private ProveedorDAO proveedorDAO;
    private String proveedordINum;
    private String proveedorNombre;
    
    private Bodega bodega;
    private BodegaDAO bodegaDAO;
    private int codBodega;
    private String nombreBodega;
    private String direccionBodega;
    private String ciudadBodega;
    
    private EntradaDao enntradaDAO;
    
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
    private EntradaDao entradaDao;

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
        this.proveedor = new Proveedor();
        this.proveedorDAO = new ProveedorDAO();

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
        this.entradaDao = new EntradaDao();
        
        this.listaEntradas = entradaDao.getEntradas();
        this.listaBodegas = bodegaDAO.getBodega();
        this.productoSeleccionado = null;
        this.proveedorSeleccionado=null;
        this.bodegaSeleccionada= null;

        

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

    //Buscar cliente
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
                        this.subTotalEntrada = this.subTotalEntrada + detalle.getSubtotal() ;
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
    public String RegistrarEntrada(int formaPago) {
        try {
            EntradaInventario ventaActual = new EntradaInventario();
            int listSize = 0;
            if (this.listaDetalle.isEmpty()) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No puede  realizar una venta nula");
            } else {
                if (this.proveedorNombre == null || this.proveedorNombre == "") {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe elegir un cliente para la venta");
                } else {
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    String currentDate = df.format(new Date());
                    Date currentDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(currentDate);

                    //Asignar valores a la venta
                    ventaActual.setProveedor(this.proveedor);
                    ventaActual.setIdProveedor(this.proveedor.getIdProveedor());
                    ventaActual.setIdBodega(1);
                    ventaActual.setNumComprobante("5478");
                    ventaActual.setFecha(currentDate2);


                    //Verificación en consola
                    System.out.println(ventaActual.getProveedor().getNombre());
       

                    //Guardar la venta desde DAO
                    int ventaRealizada = this.entradaDao.GuardarEntrada(ventaActual);

                    //Verificar que se haya ingresado la venta
                    if (ventaRealizada == 0) {
                        addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo realizar la venta. Revise los datos ingresados");
                    } else {
                        System.out.println("Entrada realizada con Factura #" + ventaRealizada);

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
                        
                        return "listaEntrada";
                    }
                }
            }
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage().toString());
        }
        return null;
    }
    
    public void SeleccionarProveedor(Proveedor prov){
        this.proveedorNombre = prov.getNombre();
        this.proveedordINum = prov.getRazonSocial();
        this.proveedor = prov;
    }
    
    public void SeleccionarProducto(ArticulosInventario pr){
    this.codigoProducto = pr.getCod();
    this.nombreProducto = pr.getDescripcion();
    this.precioProducto = pr.getCosto();
    this.producto = pr;
}

    public void SeleccionarBodega(Bodega bod){
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

    public EntradaDao getEnntradaDAO() {
        return enntradaDAO;
    }

    public void setEnntradaDAO(EntradaDao enntradaDAO) {
        this.enntradaDAO = enntradaDAO;
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
        return entradaDao;
    }

    public void setEntradaDao(EntradaDao entradaDao) {
        this.entradaDao = entradaDao;
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
