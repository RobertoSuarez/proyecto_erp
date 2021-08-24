/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


public class Venta {
    private int idVenta;
    private int idCliente;
    private int idEmpleado;
    private int idFormaPago;
    private int idDocumento;
    private int sucursal;
    private Timestamp fechaVenta;
    private int puntoEmision;
    private int secuencia;
    private String autorizacion;
    private Timestamp fechaEmision;
    private Timestamp fechaAutorizacion;
    private double base12;
    private double base0;
    private double ice;
    private double iva;
    private double totalFactura;
    
    private ClienteVenta cliente;
    private DetalleVenta productoActual;
    private List<DetalleVenta> productos; 

    public Venta() {
        
    }

    public Venta(int idVenta, int idCliente, int idEmpleado, int idFormaPago, int idDocumento, int sucursal, Timestamp fechaVenta, int puntoEmision, int secuencia, String autorizacion, Timestamp fechaEmision, Timestamp fechaAutorizacion, double base12, double base0, double iva, double ice, double totalFactura, ClienteVenta cliente, List<DetalleVenta> productos) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.idFormaPago = idFormaPago;
        this.idDocumento = idDocumento;
        this.sucursal = sucursal;
        this.fechaVenta = fechaVenta;
        this.puntoEmision = puntoEmision;
        this.secuencia = secuencia;
        this.autorizacion = autorizacion;
        this.fechaEmision = fechaEmision;
        this.fechaAutorizacion = fechaAutorizacion;
        this.base12 = base12;
        this.base0 = base0;
        this.iva = iva;
        this.ice = ice;
        this.totalFactura = totalFactura;
        this.cliente = cliente;
        this.productos = productos;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public int getSucursal() {
        return sucursal;
    }

    public void setSucursal(int sucursal) {
        this.sucursal = sucursal;
    }

    public Timestamp getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Timestamp fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(int puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public Timestamp getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Timestamp fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Timestamp getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(Timestamp fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public double getBase12() {
        return base12;
    }

    public void setBase12(double base12) {
        this.base12 = base12;
    }

    public double getBase0() {
        return base0;
    }

    public void setBase0(double base0) {
        this.base0 = base0;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    
    
    public double getIce() {
        return ice;
    }

    public void setIce(double ice) {
        this.ice = ice;
    }

    public double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public ClienteVenta getCliente() {
        return cliente;
    }

    public void setCliente(ClienteVenta cliente) {
        this.cliente = cliente;
    }

    public DetalleVenta getProductoActual() {
        return productoActual;
    }

    public void setProductoActual(DetalleVenta productoActual) {
        this.productoActual = productoActual;
    }

    public List<DetalleVenta> getProductos() {
        return productos;
    }

    public void setProductos(List<DetalleVenta> productos) {
        this.productos = productos;
    }
    
    
    
    
}
