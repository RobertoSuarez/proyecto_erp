/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.models;

import com.produccion.models.FormulaMateriales;
import java.util.List;

public class ArticulosInventario {
    
    private int id;
    private int cat_cod;
    private String nombre;
    private int id_categoria;
    private int id_tipo;
    private int cod;
    private String descripcion;
    private int id_bodega;
    private int min_stock;
    private int max_stock;
    private int cantidad;
    private int costo;
    private int iva;
    private int ice ;
    private List<FormulaMateriales> formulaMateriales;

    public ArticulosInventario() {
    }

    public ArticulosInventario(int id, int cat_cod, String nombre, int id_tipo, String descripcion, int id_bodega, int cantidad, int costo, int iva, int ice) {
        this.id = id;
        this.cat_cod = cat_cod;
        this.nombre = nombre;
        this.id_tipo = id_tipo;
        this.descripcion = descripcion;
        this.id_bodega = id_bodega;
        this.cantidad = cantidad;
        this.costo = costo;
        this.iva = iva;
        this.ice = ice;
    }

    
    public ArticulosInventario(int id, int cat_cod, String nombre, 
            int id_categoria, int id_tipo, int cod, String descripcion, 
            int id_bodega, int min_stock, int max_stock, int cantidad, 
            int costo, int iva, int ice) {
        this.id = id;
        this.cat_cod = cat_cod;
        this.nombre = nombre;
        this.id_categoria = id_categoria;
        this.id_tipo = id_tipo;
        this.cod = cod;
        this.descripcion = descripcion;
        this.id_bodega = id_bodega;
        this.min_stock = min_stock;
        this.max_stock = max_stock;
        this.cantidad = cantidad;
        this.costo = costo;
        this.iva = iva;
        this.ice = ice;
    }

    public List<FormulaMateriales> getFormulaMateriales() {
        return formulaMateriales;
    }

    public void setFormulaMateriales(List<FormulaMateriales> formulaMateriales) {
        this.formulaMateriales = formulaMateriales;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat_cod() {
        return cat_cod;
    }

    public void setCat_cod(int cat_cod) {
        this.cat_cod = cat_cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_bodega() {
        return id_bodega;
    }

    public void setId_bodega(int id_bodega) {
        this.id_bodega = id_bodega;
    }

    public int getMin_stock() {
        return min_stock;
    }

    public void setMin_stock(int min_stock) {
        this.min_stock = min_stock;
    }

    public int getMax_stock() {
        return max_stock;
    }

    public void setMax_stock(int max_stock) {
        this.max_stock = max_stock;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public int getIce() {
        return ice;
    }

    public void setIce(int ice) {
        this.ice = ice;
    }

   
    
    
    
    
    
    
    
    
}
