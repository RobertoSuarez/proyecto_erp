/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.models;

import com.cuentasporpagar.models.Proveedor;
import java.time.LocalDate;

/**
 *
 * @author Lister Leonardo
 */
public class EntradaProducto {

    private int id;
    private String num_comprobante;
    private LocalDate fecha;
    private Proveedor proveedor;
    private int idproveedor;
    private String nombre_proveedor;
    private Bodega bodega;
    private int idBodega;
    private String nombreBodega;
    private int habilitar;
    
    //detalle de entrada
    private int id_detalle;
    private int idProducto;
    private String nombre_producto;
    private String detalleProduto;
    private int cantidad;
    private float costo;
    private float costoVenta;

    public EntradaProducto() {
    }
    
    
    
    
    //mostrar lista de entradas

    public EntradaProducto(String num_comprobante, LocalDate fecha, String nombre_proveedor, String nombreBodega) {
        this.num_comprobante = num_comprobante;
        this.fecha = fecha;
        this.nombre_proveedor = nombre_proveedor;
        this.nombreBodega = nombreBodega;
    }
    
    //detalle de las ordenes de entrada

    public EntradaProducto(int id_detalle, int idProducto, String nombre_producto, String detalleProduto, int cantidad, float costo,float costoVenta) {
        this.id_detalle=id_detalle;
        this.idProducto = idProducto;
        this.nombre_producto = nombre_producto;
        this.detalleProduto = detalleProduto;
        this.cantidad = cantidad;
        this.costo = costo;
        this.costoVenta=costoVenta;
    }

    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCostoVenta() {
        return costoVenta;
    }

    public void setCostoVenta(float costoVenta) {
        this.costoVenta = costoVenta;
    }
    

    public String getNum_comprobante() {
        return num_comprobante;
    }

    public void setNum_comprobante(String num_comprobante) {
        this.num_comprobante = num_comprobante;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public String getNombre_proveedor() {
        return nombre_proveedor;
    }

    public void setNombre_proveedor(String nombre_proveedor) {
        this.nombre_proveedor = nombre_proveedor;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public int getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(int idBodega) {
        this.idBodega = idBodega;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    public int getHabilitar() {
        return habilitar;
    }

    public void setHabilitar(int habilitar) {
        this.habilitar = habilitar;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getDetalleProduto() {
        return detalleProduto;
    }

    public void setDetalleProduto(String detalleProduto) {
        this.detalleProduto = detalleProduto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }
    
    
    
    
}
