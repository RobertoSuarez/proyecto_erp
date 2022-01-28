/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.Costo;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class CostoDAO {
    private Conexion conexion;
    private ResultSet resultSet;

    public CostoDAO() {
        conexion = new Conexion();
    }

    public List<Costo> getCosto(){
        List<Costo> costo = new ArrayList<>();
        String sql = String.format("SELECT * FROM public.costos;");
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                costo.add(new Costo(resultSet.getInt("codigo_costos"), resultSet.getString("nombre"),
                resultSet.getString("descripcion"), resultSet.getString("tipo")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally{
            conexion.desconectar();
        }
        return costo;
    }
}
