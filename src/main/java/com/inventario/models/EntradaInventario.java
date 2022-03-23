/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.models;

import com.cuentasporpagar.models.Proveedor;
import java.io.Serializable;
import java.util.Date;
import org.joda.time.DateTime;



/**
 *
 * @author angul
 */
public class EntradaInventario implements Serializable{
       private static final long serialVersionUID = 1L;

    private int cod;

    private String numComprobante;

    private Date fecha;

    private int idBodega;

    private int idProveedor;
    
    private Proveedor proveedor;
    
    private String nombreProveedor;
    
    private String nombreBodega;
    
    private String Observacion;
    
    private DateTime fechaHora;

    
    public EntradaInventario() {
    }

    public EntradaInventario(Integer cod) {
        this.cod = cod;
    }

    public EntradaInventario(int cod, String numComprobante, Date fecha, int idBodega, int idProveedor) {
        this.cod = cod;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.idBodega = idBodega;
        this.idProveedor = idProveedor;
       }
    
        public EntradaInventario(int cod, String numComprobante, Date fecha, int idBodega, int idProveedor, String nombreProveedor, String nombreBodega  ) {
        this.cod = cod;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.idBodega = idBodega;
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.nombreBodega = nombreBodega;
       }
        
        
        


    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNumComprobante() {
        return numComprobante;
    }

    public void setNumComprobante(String numComprobante) {
        this.numComprobante = numComprobante;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(int idBodega) {
        this.idBodega = idBodega;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String Observacion) {
        this.Observacion = Observacion;
    }

    public DateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(DateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    
}
