/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.Producto;
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
public class ProductoDAO {

    private Producto product;
    Conexion con;

    public ProductoDAO(Producto product) throws SQLException {
        con.conectar();
        this.product = product;
    }

    public ProductoDAO() {
        this.con = new Conexion();
    }

    public Producto getProduct() {
        return product;
    }

    public void setProduct(Producto product) {
        this.product = product;
    }

    public Producto ObtenerProducto(int id) {
        ResultSet rs;
        Producto temp = new Producto();
        
        try {
            String code = String.valueOf(id);
            con.conectar();
            rs = con.ejecutarSql("select * from public.buscarproductocodigo(" + code.trim() + ")");
            
            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                System.out.println("Existen registros");
                
                while (rs.next()) {
                    System.out.print("Producto " + rs.getInt(1));
                    temp.setCodigo(rs.getInt(1));
                    temp.setCodigoAux(rs.getInt(2));
                    temp.setStock(rs.getInt(3));
                    temp.setDescripcion(rs.getString(4));
                    temp.setPrecioUnitario(rs.getFloat(5));
                    temp.setSubsidio(rs.getFloat(6));
                    temp.setIce(rs.getFloat(7));
                    temp.setIva(rs.getFloat(8));
                    temp.setDescuento(rs.getFloat(9));
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
    
    public List<Producto> ListarProductos(){
        List<Producto> listaventa = new ArrayList<>();
        Producto temp;
        ResultSet rs;
        try{
            con.conectar();
            rs=con.ejecutarSql("Select * from productos");
            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                System.out.println("Existen registros");
                
                while (rs.next()) {
                    temp = new Producto();
                    temp.setCodigo(rs.getInt(1));
                    temp.setCodigoAux(rs.getInt(2));
                    temp.setStock(rs.getInt(3));
                    temp.setDescripcion(rs.getString(4));
                    temp.setDetalleAdicional(rs.getString(5));
                    temp.setPrecioUnitario(rs.getFloat(6));
                    temp.setSubsidio(rs.getFloat(7));
                    temp.setIce(rs.getFloat(8));
                    temp.setIva(rs.getFloat(9));
                    temp.setDescuento(rs.getFloat(10));
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
