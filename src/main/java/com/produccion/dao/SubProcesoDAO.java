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
import com.produccion.models.dSubproceso;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubProcesoDAO {

    private Conexion conexion;
    private ResultSet resultSet;
    
    public SubProcesoDAO() {
        conexion = new Conexion();
    }

    public List<ProcesoProduccion> getProcesosProduccion() {
        List<ProcesoProduccion> procesos = new ArrayList<>();
        String sentenciaSql = String.format("select * from getProcesosProduccion();");
        try {
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                procesos.add(new ProcesoProduccion(resultSet.getInt("codigo_proceso"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion"), resultSet.getString("identificador")));
            }
        } catch (SQLException e) {
        }finally {
            conexion.desconectar();
        }
        return procesos;
    }

    public int insertardSubproceso(SubProceso proceso) {
        try {
            String sentenciaSql = "INSERT INTO public.detalle_proceso_p(\n"
                    + "	codigo_proceso, codigo_subproceso, horas, cantidad_estimada)\n"
                    + "	VALUES (" + proceso.getId_codigo_proceso() + "," + proceso.getCodigo_subproceso() + ",'"+proceso.getHora()+"',"+proceso.getRendimiento()+");";
            return conexion.insertar(sentenciaSql);
        } catch (Exception e) {
            return -1;
        }finally {
            conexion.desconectar();
        }
    }
    public int insertarDetalleSubproceso(dSubproceso subproceso) {
        try {
            String sentenciaSql = "INSERT INTO public.detalle_subproceso(\n" +
"	codigo_subproceso, codigo_costos, costo_mano_obra, costo_indirecto, hora_costo)\n" +
"	VALUES ("+subproceso.getCodigo_subproceso()+", "+subproceso.getCodigo_costos()+", "+subproceso.getCosto_mano_obra()+", "+subproceso.getCosto_indirecto()+", "+
                    subproceso.getHora_costo()+");";
            return conexion.insertar(sentenciaSql);
        } catch (Exception e) {
            return -1;
        }finally {
            conexion.desconectar();
        }
    }

    public int insertarSubproceso(SubProceso proceso) {
        try {
            String sentenciaSql = "INSERT INTO public.subproceso(\n"
                    + "	codigo_subproceso, nombre, descripcion)\n"
                    + "	VALUES ("+proceso.getCodigo_subproceso()+",'"+proceso.getNombre()+"', '"+proceso.getDescripcion()+"');";
            return conexion.insertar(sentenciaSql);
        } catch (Exception e) {
            return -1;
        }finally {
            conexion.desconectar();
        }
    }

    public int idSubproceso() {
        try {

            int id = -1;
            String sentenciaSql = "select max(codigo_subproceso)+1 as id from public.subproceso;";
            resultSet = conexion.ejecutarSql(sentenciaSql);
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            return id;
        } catch (SQLException e) {
            return -1;
        }finally {
            conexion.desconectar();
        }

    }
}
