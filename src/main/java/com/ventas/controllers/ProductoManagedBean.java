/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.controllers;

import com.ventas.dao.ProductoDAO;
import com.ventas.models.Producto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named(value = "productoManagedBean")
@ViewScoped
public class ProductoManagedBean implements Serializable{
    String codigoproducto;
    String nombreProducto;
    float precioProducto;
    int cantidad;

    public ProductoDAO getProductdao() {
        return productdao;
    }

    public void setProductdao(ProductoDAO productdao) {
        this.productdao = productdao;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public float getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(float precio) {
        this.precioProducto = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    ProductoDAO productdao;
    Producto product;
    List<Producto> listaproforma = new ArrayList();
    public ProductoManagedBean() {
        productdao= new ProductoDAO();
        product=new Producto();
        
    }

    public String getCodigoproducto() {
        return codigoproducto;
    }

    public void setCodigoproducto(String codigoproducto) {
        this.codigoproducto = codigoproducto;
    }

    public Producto getProduct() {
        return product;
    }

    public void setProduct(Producto product) {
        this.product = product;
    }
    
    
    public void obtenerProducto(){
        int codigo = Integer.parseInt(this.codigoproducto);
        this.product = this.productdao.ObtenerProducto(codigo);
        System.out.print("No hay errores");
        if(this.product!=null){
            this.nombreProducto=this.product.getDescripcion();
            this.precioProducto=this.product.getPrecioUnitario();
            System.out.print("ProductoObtenido");
        }
        else{
            System.err.print("Codigo de producto inexistente");
        }
    }
    
}
