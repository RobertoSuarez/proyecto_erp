/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.ProductoVenta;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;

@ManagedBean
@RequestScoped
public class ProductoVentaDAO {

    private ProductoVenta product;
    Conexion con;

    public ProductoVentaDAO(ProductoVenta product) throws SQLException {
        con.conectar();
        this.product = product;
    }

    public ProductoVentaDAO() {
        this.con = new Conexion();
    }

    public ProductoVenta getProduct() {
        return product;
    }

    public void setProduct(ProductoVenta product) {
        this.product = product;
    }

    public ProductoVenta ObtenerProducto(int id) {
        ResultSet rs;
        ProductoVenta temp = new ProductoVenta();
        
        try {
            String code = String.valueOf(id);
            con.conectar();
            rs = con.ejecutarSql("Select a.*, sum(ab.cant) as cantidad, iv.valor as iva, um.unidad_medida\n" +
                                "from articulos a inner join articulo_bodega ab on a.id = ab.id_articulo\n" +
                                "inner join porcentajes_iva iv on iv.id = a.id_porcentajeiva\n" +
                                "inner join unidades_medidas um on um.id = a.id_unidadmedida\n" +
                                "where a.id = " + String.valueOf(id) + " group by a.id, iv.id, um.id;");
            
            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                System.out.println("Existen registros");
                
                while (rs.next()) {
                    System.out.print("Producto " + rs.getInt("id") + ": " + rs.getString("nombre"));
                    temp.setCodigo(rs.getInt("id"));
                    temp.setNombre(rs.getString("nombre"));
                    temp.setIdCategoria(rs.getInt("id_categoria"));
                    temp.setIdTipo(rs.getInt("id_tipo"));
                    temp.setDescripcion(rs.getString("descripcion"));
                    //temp.setIdBodega(rs.getInt("id_bodega"));
                    temp.setStock(rs.getInt("cantidad"));
                    temp.setIva(rs.getFloat("iva") * 100);
                    temp.setIce(rs.getFloat("ice"));
                    temp.setIdSubcuenta(rs.getInt("id_subcuenta"));
                    temp.setUnidadMedida(rs.getString("unidad_medida"));
                    temp.setPrecioUnitario(rs.getFloat("precio_venta"));
                    temp.setEsServicio(rs.getBoolean("es_servicio"));
                    temp.setStockeable(rs.getBoolean("stockeable"));
                }
            }
            con.desconectar();

            return temp;
        } catch (Exception e) {
            if (con.isEstado()) {
                con.desconectar();
            }
        }
        finally{
            con.desconectar();
        }

        return null;
    }
    
    public List<ProductoVenta> ListarProductos(){
        List<ProductoVenta> listaventa = new ArrayList<>();
        ProductoVenta temp;
        ResultSet rs;
        try{
            con.conectar();
            rs=con.ejecutarSql("Select a.*, sum(ab.cant) as cantidad, iv.valor as iva, um.unidad_medida\n" +
                                "from articulos a inner join articulo_bodega ab on a.id = ab.id_articulo\n" +
                                "inner join porcentajes_iva iv on iv.id = a.id_porcentajeiva\n" +
                                "inner join unidades_medidas um on um.id = a.id_unidadmedida\n" +
                                "group by a.id, iv.id, um.id;;");
            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                System.out.println("Existen registros");
                
                while (rs.next()) {
                    temp = new ProductoVenta();
                    temp.setCodigo(rs.getInt("id"));
                    temp.setNombre(rs.getString("nombre"));
                    temp.setIdCategoria(rs.getInt("id_categoria"));
                    temp.setIdTipo(rs.getInt("id_tipo"));
                    temp.setDescripcion(rs.getString("descripcion"));
                    temp.setCosto(rs.getDouble("costo"));
                    temp.setStock(rs.getInt("cantidad"));
                    temp.setIva(rs.getFloat("iva") * 100);
                    temp.setIce(rs.getFloat("ice"));
                    temp.setIdSubcuenta(rs.getInt("id_subcuenta"));
                    temp.setUnidadMedida(rs.getString("unidad_medida"));
                    temp.setPrecioUnitario(rs.getFloat("precio_venta"));
                    temp.setEsServicio(rs.getBoolean("es_servicio"));
                    temp.setStockeable(rs.getBoolean("stockeable"));
                    listaventa.add(temp);
                }
                con.desconectar();
        }
        }
        catch(Exception e){
            if (con.isEstado()) {
                con.desconectar();
            }
        }
        finally{
            con.desconectar();
        }
        return listaventa;
    }
}
