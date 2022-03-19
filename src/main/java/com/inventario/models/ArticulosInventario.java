/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.models;

import com.contabilidad.models.SubCuenta;
import com.produccion.models.FormulaMateriales;
import java.io.Serializable;
import java.util.List;

public class ArticulosInventario implements Serializable {

    private int id;
    private int cat_cod;
    private String nombre;
    private Bodega bodega;
    private Categoria categoria;
    private Tipo tipo;
    private int id_categoria;
    private int id_tipo;
    private int cod;
    private String descripcion;
    private int id_bodega;
    private String nomBodega;
    private int min_stock;
    private int max_stock;
    private int cantidad;
    private int costo;
    private float coast;
    private int iva;
    private String UnidadMedida;
    private String tipoP;
    private String nom_categoria;
    private Boolean isIva;
    private float precioventa;
    private float iceproducto;
    private SubCuenta subCuenta;
    private int idSubCuenta;
    private String nom_subcuenta;
    private int ice;
    private float impuestoIVA;
    private boolean is_Servicio;
    private boolean stockeable;
    private int cantidadFacturada;

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

    public ArticulosInventario(int id, int cat_cod, String nombre, int id_tipo, String descripcion, int id_bodega, int cantidad, int costo, int iva, int ice, int max_stock) {
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
        this.max_stock = max_stock;
    }

    public ArticulosInventario(int id, String nombre, String descripcion, String nomBodega, int cantidad, float coast, String UnidadMedida) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nomBodega = nomBodega;
        this.cantidad = cantidad;
        this.coast = coast;
        this.UnidadMedida = UnidadMedida;
    }

    public ArticulosInventario(int id, String nombre, String descripcion, String nomBodega, int cantidad, float coast, int iva, String UnidadMedida, String tipoP, String nom_categoria, String nom_subcuenta, float iceproducto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nomBodega = nomBodega;
        this.cantidad = cantidad;
        this.coast = coast;
        this.iva = iva;
        this.UnidadMedida = UnidadMedida;
        this.tipoP = tipoP;
        this.nom_categoria = nom_categoria;
        this.nom_subcuenta = nom_subcuenta;
        this.iceproducto = iceproducto;
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
    
    // Utilizado para el ice del producto
        public ArticulosInventario(int id, String nombre, 
            int id_categoria, int id_tipo, String descripcion, 
            int min_stock, int max_stock, int cantidad, 
            float costo, float ice) {
        this.id = id;
        this.nombre = nombre;
        this.id_categoria = id_categoria;
        this.id_tipo = id_tipo;
        this.descripcion = descripcion;
        this.min_stock = min_stock;
        this.max_stock = max_stock;
        this.cantidad = cantidad;
        this.coast = costo;
        this.iceproducto = ice;
    }
    
            public ArticulosInventario(int id, String nombre, 
            int id_categoria, int id_tipo, String descripcion, 
            int min_stock, int max_stock, int cantidad, int cantidadFacturada,
            float costo, float ice) {
        this.id = id;
        this.nombre = nombre;
        this.id_categoria = id_categoria;
        this.id_tipo = id_tipo;
        this.descripcion = descripcion;
        this.min_stock = min_stock;
        this.max_stock = max_stock;
        this.cantidad = cantidad;
        this.cantidadFacturada = cantidadFacturada;
        this.coast = costo;
        this.iceproducto = ice;
    }   
     
    
    
    
    //para salidas

    public ArticulosInventario(String nombre, int id_categoria, int id_tipo, String descripcion, int id_bodega, int max_stock, int cantidad, int costo, int iva, float iceproducto, float precioventa) {
        this.nombre = nombre;
        this.id_categoria = id_categoria;
        this.id_tipo = id_tipo;
        this.descripcion = descripcion;
        this.id_bodega = id_bodega;
        this.max_stock = max_stock;
        this.cantidad = cantidad;
        this.costo = costo;
        this.iva = iva;
        this.iceproducto = ice;
        this.precioventa = precioventa;
    }

    public List<FormulaMateriales> getFormulaMateriales() {
        return formulaMateriales;
    }

    public float getCoast() {
        return coast;
    }

    public void setCoast(float coast) {
        this.coast = coast;
    }

    public String getNom_categoria() {
        return nom_categoria;
    }

    public void setNom_categoria(String nom_categoria) {
        this.nom_categoria = nom_categoria;
    }

    public void setFormulaMateriales(List<FormulaMateriales> formulaMateriales) {
        this.formulaMateriales = formulaMateriales;
    }

    public float getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(float precioventa) {
        this.precioventa = precioventa;
    }

    public float getIceproducto() {
        return iceproducto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public boolean isIs_Servicio() {
        return is_Servicio;
    }

    public void setIs_Servicio(boolean is_Servicio) {
        this.is_Servicio = is_Servicio;
    }

    public boolean isStockeable() {
        return stockeable;
    }

    public void setStockeable(boolean stockeable) {
        this.stockeable = stockeable;
    }
    

    public float getImpuestoIVA() {
        return impuestoIVA;
    }

    public void setImpuestoIVA(float impuestoIVA) {
        this.impuestoIVA = impuestoIVA;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getTipoP() {
        return tipoP;
    }

    public void setTipoP(String tipoP) {
        this.tipoP = tipoP;
    }

    public void setIceproducto(float iceproducto) {
        this.iceproducto = iceproducto;
    }

    public SubCuenta getSubCuenta() {
        return subCuenta;
    }

    public void setSubCuenta(SubCuenta subCuenta) {
        this.subCuenta = subCuenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSubCuenta() {
        return idSubCuenta;
    }

    public void setIdSubCuenta(int idSubCuenta) {
        this.idSubCuenta = idSubCuenta;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public String getNomBodega() {
        return nomBodega;
    }

    public void setNomBodega(String nomBodega) {
        this.nomBodega = nomBodega;
    }

    public String getNom_subcuenta() {
        return nom_subcuenta;
    }

    public void setNom_subcuenta(String nom_subcuenta) {
        this.nom_subcuenta = nom_subcuenta;
    }

    public int getCat_cod() {
        return cat_cod;
    }

    public void setCat_cod(int cat_cod) {
        this.cat_cod = cat_cod;
    }

    public String getUnidadMedida() {
        return UnidadMedida;
    }

    public void setUnidadMedida(String UnidadMedida) {
        this.UnidadMedida = UnidadMedida;
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

    public Boolean getIsIva() {
        return isIva;
    }

    public void setIsIva(Boolean isIva) {
        this.isIva = isIva;
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

    public int getCantidadFacturada() {
        return cantidadFacturada;
    }

    public void setCantidadFacturada(int cantidadFacturada) {
        this.cantidadFacturada = cantidadFacturada;
    }
    
    

}
