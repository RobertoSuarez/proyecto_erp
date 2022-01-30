/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.SubProceso;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class SubProcesoDAO {
    private Conexion conexion;
    private ResultSet resultSet;

    public SubProcesoDAO() {
        conexion = new Conexion();
    }
    
    public List<SubProceso> getSubProceso() {
        List<SubProceso> sub = new ArrayList<>();
        String sql = String.format("select * from getProcesosProduccion();");
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                sub.add(new SubProceso(resultSet.getInt("codigo_proceso"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return sub;
    }
}
