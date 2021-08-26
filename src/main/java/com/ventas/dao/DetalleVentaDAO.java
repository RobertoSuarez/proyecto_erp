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
            String query = "select iddetalleventa from public.detalleventa order by iddetalleventa desc limit 1;";
            rs = this.con.consultar(query);

            while (rs.next()) {
                idDetalle = rs.getInt(1) + 1;
            }

            query = "insert into public.detalleventa(iddetalleventa, idventa, codprincipal, cantidad, descuento, precio) values(" + idDetalle + "," + idVenta + ","
                    + idProducto + "," + cantidad + "," + descuento + "," + precio + ")";
            System.out.println(query);
            this.con.consultar(query);

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
}
