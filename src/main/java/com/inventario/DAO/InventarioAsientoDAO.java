/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.global.config.JsonManager;
import com.inventario.models.AsientoInventario;
import com.inventario.models.MovimientoInventario;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author angul
 */
public class InventarioAsientoDAO {
    
    Conexion con = new Conexion();
    
   
    final String diario = "";
    
        public ResultSet insertarAsiento(AsientoInventario asiento, List<MovimientoInventario> movimientos) {
        if (con.isEstado()) {
            try {                       
                String cadena = "SELECT public.generateasientocotableexternal('"
                        + JsonManager.serialize(asiento) + "','" + JsonManager.serialize(movimientos) + "')";
                System.out.println(cadena);
                return con.ejecutarSql(cadena);
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                con.desconectar();
            }
        }
        return null;
    }

    
}
