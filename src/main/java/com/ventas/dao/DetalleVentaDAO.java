/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.DetalleVenta;
import com.ventas.models.ProductoVenta;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
            this.con.conectar();
            
            //Recibir siguiente código de detalle venta
            String query = "select iddetalleventa from public.detalleventa order by iddetalleventa desc limit 1;";
            rs = this.con.ejecutarSql(query);

            while (rs.next()) {
                idDetalle = rs.getInt(1) + 1;
            }

            //insertar detalle venta
            query = "insert into public.detalleventa(iddetalleventa, idventa, codprincipal, cantidad, descuento, precio) values(" + idDetalle + "," + idVenta + ","
                    + idProducto + "," + cantidad + "," + descuento + "," + precio + ")";
            System.out.println(query);
            this.con.ejecutarSql(query);

            
            //Reducir stock
            int cantidadActual = 0;
            query = "select cantidad from public.productos where codprincipal = " + idProducto + ";";
            rs = this.con.ejecutarSql(query);
            while (rs.next()) {
                cantidadActual = rs.getInt(1);
            }
            query = "update public.productos set cantidad = " + (cantidadActual - cantidad) + " where codprincipal = " + idProducto + ";";
            this.con.ejecutarSql(query);
            

            this.con.desconectar();
        } catch (Exception e) {
            if (con.isEstado()) {
                con.desconectar();
            }
            System.out.println(e.getMessage().toString());
        } finally {
            this.con.desconectar();
        }
    }
    
    public List<DetalleVenta> ObtenerDetalleVentas(int idVenta){
        try{
            this.con.conectar();
            DetalleVenta detail = new DetalleVenta();
            List<DetalleVenta> lista = new ArrayList<>();
            String query = "select d.iddetalleventa, d.cantidad, ar.nombre, d.precio, CAST((d.cantidad * d.precio) - d.descuento as DOUBLE PRECISION) as Subtotal, d.descuento "
                    + "from public.detalleventa d inner join public.articulos ar on d.codprincipal = ar.id where idventa = " + idVenta + ";";
            System.out.println(query);
            ResultSet rs = this.con.ejecutarSql(query);
            
            while(rs.next()){
                detail = new DetalleVenta();
                detail.setIddetalleventa(rs.getInt(1));
                detail.setCantidad(rs.getInt(2));
                detail.setNombreProducto(rs.getString(3));
                detail.setPrecio(convertTwoDecimal(rs.getDouble(4)));
                detail.setSubTotal(convertTwoDecimal(rs.getDouble(5)));
                detail.setDescuento(convertTwoDecimal(rs.getDouble(6)));
                lista.add(detail);
            }
            
            this.con.desconectar();
            
            return lista;
        }catch(Exception e){
            if (con.isEstado()) {
                con.desconectar();
            }
            System.out.println(e.getMessage().toString());
        }finally{
            this.con.desconectar();
        }
        return null;
    }
    
    /**
     * Recible un valor de tipo double y lo transforma para que tenga únicamente
     * 2 decimales
     *
     * @param doubleNumero
     * @return double decimalConvertido
     */
    public double convertTwoDecimal(double doubleNumero) {
        return new BigDecimal(doubleNumero).setScale(2, RoundingMode.UP).doubleValue();
    }
}
