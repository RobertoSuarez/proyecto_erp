/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.Costo;
import com.produccion.models.ProcesoProduccion;
import com.produccion.models.SubProceso;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class SubProcesoDAO {

    private Conexion conexion;
    private ResultSet resultSet;
    private String sentenciaSql;

    public SubProcesoDAO() {
        conexion = new Conexion();
    }

    /*public List<SubProceso> getSubProceso() {
        List<SubProceso> sub = new ArrayList<>();
        String sql = String.format("SELECT * FROM public.subproceso;");
        try {
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                costo.add(new Costo(resultSet.getInt("codigo_costos"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion"), resultSet.getString("tipo"), resultSet.getString("identificador")));
            }
        } catch (SQLException e) {
        }
        return costo;
    }*/


    public int idSubproceso() {
        try {

            int id = -1;
            sentenciaSql = "select max(codigo_subproceso)+1 as id from public.subproceso;";
            resultSet = conexion.ejecutarSql(sentenciaSql);
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            return id;
        } catch (SQLException e) {
            return -1;
        }

    }
}
