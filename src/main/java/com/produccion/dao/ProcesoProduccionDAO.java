/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.dao;

import com.global.config.Conexion;
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
public class ProcesoProduccionDAO {

    Conexion conexion;
    private ResultSet resultSet;
    private String sentenciaSql;

    public ProcesoProduccionDAO() {
        conexion = new Conexion();

    }

    public List<SubProceso> getsubProcesosProduccion(int id) {
        List<SubProceso> procesos = new ArrayList<>();
        sentenciaSql = String.format("select sp.codigo_subproceso, sp.nombre, sp.descripcion from public.detalle_proceso_p d inner join public.subproceso sp\n"
                + "	on d.codigo_subproceso=sp.codigo_subproceso where d.codigo_proceso=" + id + ";");
        try {
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                procesos.add(new SubProceso(resultSet.getInt("codigo_subproceso"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion")));
            }
        } catch (SQLException e) {
        }
        return procesos;
    }

    public List<ProcesoProduccion> getProcesosProduccion() {
        List<ProcesoProduccion> procesos = new ArrayList<>();
        sentenciaSql = String.format("select * from getProcesosProduccion();");
        try {
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                procesos.add(new ProcesoProduccion(resultSet.getInt("codigo_proceso"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion"), resultSet.getString("identificador")));
            }
        } catch (SQLException e) {
        }
        return procesos;
    }

    public void insertarp(ProcesoProduccion proceso) {
        try {
            sentenciaSql = "INSERT INTO public.proceso_produccion(nombre, descripcion, identificador)\n"
                    + "	VALUES ('" + proceso.getNombre() + "', '" + proceso.getDescripcion() + "', '" + proceso.getIdentificador() + "')";
            conexion.ejecutar(sentenciaSql);
        } catch (Exception e) {
        }
    }

    public void update(ProcesoProduccion proceso) throws SQLException {
        sentenciaSql = "UPDATE public.proceso_produccion\n"
                + "	SET nombre='" + proceso.getNombre() + "', descripcion='" + proceso.getDescripcion() + "', identificador='" + proceso.getIdentificador() + "'\n"
                + "	WHERE identificador = '" + proceso.getIdentificador() + "'";
        conexion.ejecutar(sentenciaSql);
    }

    public void delete(ProcesoProduccion proceso, String aux) throws SQLException {
        sentenciaSql = ("DELETE FROM public.proceso_produccion WHERE identificador = '" + aux + "'");
        conexion.ejecutar(sentenciaSql);
    }
}
