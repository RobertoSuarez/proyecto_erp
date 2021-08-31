/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.DetalleVenta;
import com.ventas.models.Producto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andres
 */
public class DetalleVentaDAO {

    private DetalleVenta detalleVenta;
    private Conexion con;

    public DetalleVentaDAO() {
        this.con = new Conexion();
    }

    public void RegistrarProductos(int idVenta, int idProducto, double cantidad, double descuento, double precio) {
        try {
            int idDetalle = 1;
            ResultSet rs = null;
            this.con.abrirConexion();
            
            //Recibir siguiente código de detalle venta
            String query = "select iddetalleventa from public.detalleventa order by iddetalleventa desc limit 1;";
            rs = this.con.consultar(query);

            while (rs.next()) {
                idDetalle = rs.getInt(1) + 1;
            }

            //insertar detalle venta
            query = "insert into public.detalleventa(iddetalleventa, idventa, codprincipal, cantidad, descuento, precio) values(" + idDetalle + "," + idVenta + ","
                    + idProducto + "," + cantidad + "," + descuento + "," + precio + ")";
            System.out.println(query);
            this.con.consultar(query);

            
            //Reducir stock
            int cantidadActual = 0;
            query = "select cantidad from public.productos where codprincipal = " + idProducto + ";";
            rs = this.con.consultar(query);
            while (rs.next()) {
                cantidadActual = rs.getInt(1);
            }
            query = "update public.productos set cantidad = " + (cantidadActual - cantidad) + " where codprincipal = " + idProducto + ";";
            this.con.ejecutar(query);
            

            this.con.cerrarConexion();
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        } finally {
            this.con.cerrarConexion();
        }
    }
    
    public List<DetalleVenta> ObtenerDetalleVentas(int idVenta){
        try{
            this.con.abrirConexion();
            DetalleVenta detail = new DetalleVenta();
            List<DetalleVenta> lista = new ArrayList<>();
            String query = "select d.iddetalleventa, d.cantidad, pr.descripcion, d.precio, CAST((d.cantidad * d.precio) as DOUBLE PRECISION) as Subtotal from public.detalleventa d inner join public.productos pr on d.codprincipal = pr.codprincipal where idventa = " + idVenta + ";";
            System.out.println(query);
            ResultSet rs = this.con.consultar(query);
            
            while(rs.next()){
                detail = new DetalleVenta();
                detail.setIddetalleventa(rs.getInt(1));
                detail.setCantidad(rs.getInt(2));
                detail.setNombreProducto(rs.getString(3));
                detail.setPrecio(rs.getDouble(4));
                detail.setSubTotal(rs.getDouble(5));
                lista.add(detail);
            }
            
            this.con.cerrarConexion();
            
            return lista;
        }catch(Exception e){
            System.out.println(e.getMessage().toString());
        }finally{
            this.con.cerrarConexion();
        }
        return null;
    }
}
