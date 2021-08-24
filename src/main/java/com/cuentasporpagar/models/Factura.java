/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.models;
import java.time.LocalDate;


/**
 *
 * @author ninat
 */

public class Factura{
    private int id;
    private String nfactura;
    private String descripcion;
    private float importe;
    private float pagado;
    private LocalDate fecha;
    private LocalDate vencimiento;
    private int estado;
    private int idproveedor;
    private String estado_string;
    private Proveedor proveedor;
    private String nombre;
    private String ruc;
    private int idasiento;
    private float pendiente;
    private int habilitar;
    private float por_pagar;
    // detalle de factura
    private String id_detalle;
    private Float importeD;
    private String detalle;
    private String cuenta;
    private int aux;
    
    public Factura() {
    }
    
    public Factura(String cuenta){
        this.cuenta = cuenta;
    }

    //Paola: Usa este Constructor
    public Factura(String nfactura, float importe, float pagado, LocalDate fecha, LocalDate vencimiento, float pendiente) {
        this.nfactura = nfactura;
        this.importe = importe;
        this.pagado = pagado;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.pendiente = pendiente;
    }

    //Autorizar factura
    public Factura(String nfactura, String descripcion, float importe, LocalDate fecha, LocalDate vencimiento, int estado, String nombre, int habilitar) {
        this.nfactura = nfactura;
        this.descripcion = descripcion;
        this.importe = importe;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.estado = estado;
        this.nombre = nombre;
        this.habilitar = habilitar;
    }
    
    //Diana Constructor para Buscar proveedor e insertar 
    public Factura(int id, String nfactura, String descripcion, float importe, float pendiente, LocalDate fecha, LocalDate vencimiento, int estado, String ruc, String nombre) {
        this.id = id;
        this.nfactura = nfactura;
        this.descripcion = descripcion;
        this.importe = importe;
        this.pendiente = pendiente;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.estado = estado;
        this.ruc = ruc;
        this.nombre = nombre;
    }
    
    //Diana: Constructor para mostrar
    public Factura(int id, String nfactura, String descripcion, float importe, float pagado, float pendiente, LocalDate fecha, LocalDate vencimiento, int estado, String nombre, int habilitar) {
        this.id = id;
        this.nfactura = nfactura;
        this.descripcion = descripcion;
        this.importe = importe;
        this.pagado = pagado;
        this.pendiente = pendiente;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.estado = estado;
        this.nombre = nombre;
        this.habilitar = habilitar;
    }

    // Buscar factura
    public Factura(String nfactura, String descripcion, float importe, float pagado, LocalDate fecha, LocalDate vencimiento) {
        this.nfactura = nfactura;
        this.descripcion = descripcion;
        this.importe = importe;
        this.pagado = pagado;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
    }

    //detalle factura
    public Factura(float importeD, String detalle, String id_detalle) {
        this.id_detalle = id_detalle;
        this.importeD = importeD;
        this.detalle = detalle;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNfactura() {
        return nfactura;
    }

    public void setNfactura(String nfactura) {
        this.nfactura = nfactura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public float getPagado() {
        return pagado;
    }

    public void setPagado(float pagado) {
        this.pagado = pagado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(LocalDate vencimiento) {
        this.vencimiento = vencimiento;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getEstado_string() {
        return estado_string;
    }

    public void setEstado_string(String estado_string) {
        this.estado_string = estado_string;
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdasiento() {
        return idasiento;
    }

    public void setIdasiento(int idasiento) {
        this.idasiento = idasiento;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public float getPendiente() {
        return pendiente;
    }

    public void setPendiente(float pendiente) {
        this.pendiente = pendiente;
    }

    public int getHabilitar() {
        return habilitar;
    }

    public void setHabilitar(int habilitar) {
        this.habilitar = habilitar;
    }

    public String getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(String id_detalle) {
        this.id_detalle = id_detalle;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Float getImporteD() {
        return importeD;
    }

    public void setImporteD(Float importeD) {
        this.importeD = importeD;
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
    
    

    public float getPor_pagar() {
        return por_pagar;
    }

    public void setPor_pagar(float por_pagar) {
        this.por_pagar = por_pagar;
    }
    
    
    
    
    public Factura GetdbProveedor() {
        this.setProveedor(Proveedor.getOneProveedor(this.idproveedor));
        return this;
    }
    
    
}
