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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author desta
 */
public class PeriodosActivosFijosDAO {

    public List<ListaPeriodosActivosFijos> listarActivosDepreciablesReporte() throws Exception {
        List<ListaPeriodosActivosFijos> listperiodo = new ArrayList<>();
        Conexion conexion = new Conexion();
        System.out.println("Conectado a la db");
        try {
            conexion.conectar();
            // Consulta.
            PreparedStatement st = conexion.connection.prepareStatement(
                    "select af.detalle_de_activo, \n"
                    + "af.valor_adquisicion, (select (afd.cuota_depresiacion)),\n"
                    + "(select(afd.valor_neto_libros)), afd.depreciacion_meses,\n"
                    + "afd.porcentaje_depreciacion,\n"
                    + "af.fecha_adquisicion,\n"
                    + "af.numero_factura\n"
                    + "from activos_fijos af, fijo_tangible_depreciable afd, proveedor pro\n"
                    + "where afd.id_activo_fijo = af.id_activo_fijo\n"
                    + "and af.idproveedor=pro.idproveedor\n"
                    + "and af.estado='habilitado';");
            // Ejecución
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ListaPeriodosActivosFijos listarperiodosactivosfijos = new ListaPeriodosActivosFijos();
                listarperiodosactivosfijos.setDetalle_de_activo(rs.getString("detalle_de_activo"));
                listarperiodosactivosfijos.setValor_adquisicion(rs.getInt("valor_adquisicion"));
                listarperiodosactivosfijos.setCuota_depresiacion(rs.getDouble("cuota_depresiacion"));
                listarperiodosactivosfijos.setValor_neto_libros(rs.getDouble("valor_neto_libros"));
                listarperiodosactivosfijos.setDepreciacion_meses(rs.getInt("depreciacion_meses"));
                listarperiodosactivosfijos.setPorcentaje_depreciacion(rs.getDouble("porcentaje_depreciacion"));
                listarperiodosactivosfijos.setNumero_factura(rs.getString("numero_factura")
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
