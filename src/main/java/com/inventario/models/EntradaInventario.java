/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.models;

import com.cuentasporpagar.models.Proveedor;
import java.util.Date;



/**
 *
 * @author angul
 */
public class EntradaInventario {
       private static final long serialVersionUID = 1L;

    private int cod;

    private String numComprobante;

    private Date fecha;

    private int idBodega;

    private int idProveedor;
    
    private Proveedor proveedor;

    
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

    
}