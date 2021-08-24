/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.ClienteVenta;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.HashSet;

public class ClienteVentaDao implements Serializable {

    Conexion con;
    ClienteVenta clienteVenta;

    public ClienteVentaDao() {
        this.con = new Conexion();
    }

    public ClienteVenta BuscarCliente(String id) {
        ResultSet rs;
        ClienteVenta temp = new ClienteVenta();

        System.out.print("Consultando " + id);

        try {
            con.abrirConexion();
            if (id.length() <= 10) {
                rs = con.ejecutarConsulta("select * from public.buscarclientenatural('" + id.trim() + "')");
            } else if (id.length() > 10) {
                rs = con.ejecutarConsulta("select * from public.buscarclientejuridico('" + id.trim() + "')");
            } else {
                rs = null;
            }

            if (rs == null) {
                System.out.println("No existen registros");
            } else {
                System.out.println("Existen registros ");

                while (rs.next()) {
                    temp.setIdCliente(rs.getInt(1));
                    temp.setIdentificacion(rs.getString(2));
                    temp.setNombre(rs.getString(3));
                    temp.setIdTipoCliente(rs.getInt(4));
                    temp.setTipoCliente(rs.getString(5));
                    temp.setDireccion(rs.getString(6));
                    temp.setContacto(rs.getString(7));
                }
            }
            con.cerrarConexion();
            
            return temp;
        } catch (Exception e) {
            System.out.println(e.toString());
            if(con.isEstado())
                con.cerrarConexion();
            con.cerrarConexion();
            return null;
            
            //colocar FINALLY
        }
    }
}
