/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.SolicitudOrden;
import com.produccion.models.productosOrden;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class solicitudOrdenDAO {

    Conexion conexion;
    private ResultSet resultSet;
    private String sentenciaSql;

    public solicitudOrdenDAO() {
        conexion = new Conexion();
    }

    public int insertarDetalleSolicitud(productosOrden producto) {
        try {
            conexion.conectar();
            sentenciaSql = "INSERT INTO public.registro_orden_produccion(\n"
                    + "	codigo_orden, cantidad, unidad_medida, estado, \"Codigo_producto\")\n"
                    + "	VALUES ("+producto.getCodigoOrden()+","+producto.getCantidad()+",'"+producto.getUnidadMedida()+"','"
                    +producto.getEstado()+"', "+producto.getCodigoProducto()+");";
            if (conexion.insertar(sentenciaSql) > 0) {
                return 1;
            } else {
                return -1;
            }

        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public int insertarSolicitud(SolicitudOrden orden) {
        try {
            conexion.conectar();
            sentenciaSql = "INSERT INTO public.orden_produccion(fecha_orden, fecha_fin, descripcion, estado)\n"
                    + "	VALUES ('" + orden.getFecha_orden() + "', '" + orden.getFecha_fin() + "', "
                    + "'" + orden.getDescripcion() + "', '" + orden.getEstado() + "');";
            if (conexion.insertar(sentenciaSql) > 0) {
                return 1;
            } else {
                return -1;
            }

        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public List<productosOrden> getAticulosOrden() {
        List<productosOrden> ordenProducto = new ArrayList<>();
        sentenciaSql = String.format("select * from articulos as a \n"
                + "	inner join tipo as t on a.id_tipo=t.cod\n"
                + "	where tipo='Producto Terminado'or tipo='Producto SemiElaborado'");
        try {
            //llamamos a la conexion
            conexion.conectar();
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                ordenProducto.add(new productosOrden(resultSet.getInt("id"),
                        resultSet.getString("nombre"), resultSet.getString("descripcion"),
                        resultSet.getFloat("cantidad"), resultSet.getFloat("costo"), resultSet.getString("tipo")));

            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return ordenProducto;
    }

    public int idSolicitud() {
        try {
            //llamamos a la conexion
            this.conexion.conectar();
            int id = -1;
            sentenciaSql = "select max(codigo_orden)as ultimo from orden_produccion;";
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            while (resultSet.next()) {
                id = resultSet.getInt("ultimo");
            }
            return id;
        } catch (SQLException e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

}
