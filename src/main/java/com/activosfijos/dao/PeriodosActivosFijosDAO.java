/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.dao;

import com.activosfijos.model.ListaPeriodosActivosFijos;
import com.global.config.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author desta
 */
public class PeriodosActivosFijosDAO {

    public List<ListaPeriodosActivosFijos> listarperiodosactivofijo() throws Exception {
        List<ListaPeriodosActivosFijos> listperiodo = new ArrayList<>();
        Conexion conexion = new Conexion();
        System.out.println("Conectado a la db");
        try {
            conexion.conectar();
            // Consulta.
            PreparedStatement st = conexion.connection.prepareStatement(
                    "select CAST (periodos.anio AS INT),\n"
                    + "(select sum (valor_adquisicion) from activos_fijos where date_part('year',fecha_adquisicion) =periodos.anio) as monto_total,\n"
                    + "concat('01-01-',periodos.anio) as inicio, concat('31-12-',periodos.anio ) as fin ,\n"
                    + "(select sum (valor_adquisicion) from activos_fijos , fijo_tangible_depreciable\n"
                    + "where date_part('year',fecha_adquisicion) =periodos.anio\n"
                    + "and activos_fijos.id_activo_fijo=fijo_tangible_depreciable.id_activo_fijo and estado='habilitado') as total_depreciables,\n"
                    + "(select sum (valor_adquisicion) from activos_fijos , fijo_tanginle_no_depreciable\n"
                    + "where date_part('year',fecha_adquisicion) =periodos.anio\n"
                    + "and activos_fijos.id_activo_fijo=fijo_tanginle_no_depreciable.id_activo_fijo and estado='habilitado')  as total_no_depreciables,\n"
                    + "(select sum (valor_adquisicion) from activos_fijos , fijo_tangible_agotable\n"
                    + "where date_part('year',fecha_adquisicion) =periodos.anio\n"
                    + "and activos_fijos.id_activo_fijo=fijo_tangible_agotable.id_activo_fijo and  estado='habilitado')  as total_agotables,\n"
                    + "(select sum (valor_adquisicion) from activos_fijos , fijo_intangible\n"
                    + "where date_part('year',fecha_adquisicion) =periodos.anio\n"
                    + "and activos_fijos.id_activo_fijo=fijo_intangible.id_activo_fijo and  estado='habilitado')  as total_intangibles\n"
                    + "from (select distinct date_part('year',fecha_adquisicion) as anio from activos_fijos  where estado='habilitado'\n"
                    + "order by anio) as periodos;");
            // Ejecución
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ListaPeriodosActivosFijos listarperiodosactivosfijos = new ListaPeriodosActivosFijos(
                        rs.getInt("anio"),
                        rs.getInt("monto_total"),
                        rs.getString("inicio"),
                        rs.getString("fin"),
                        rs.getDouble("total_depreciables"),
                        rs.getDouble("total_no_depreciables"),
                        rs.getDouble("total_agotables"),
                        rs.getDouble("total_intangibles")
                );
                listperiodo.add(listarperiodosactivosfijos);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            conexion.desconectar();
        }

        return listperiodo;
    }

}
