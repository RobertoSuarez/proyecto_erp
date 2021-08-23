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
        con.abrirConexion();
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
            con.abrirConexion();
            rs = con.ejecutarConsulta("select * from public.buscarproductocodigo(" + code.trim() + ")");
            
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
            con.cerrarConexion();

            return temp;
        } catch (Exception e) {
            if (con.isEstado()) {
                con.cerrarConexion();
            }
        }
        finally{
            con.cerrarConexion();
        }

        return null;
    }
}
