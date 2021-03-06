/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.dao;

import com.global.config.Conexion;
import com.inventario.models.Bodega;
import com.produccion.models.OrdenTrabajo;
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
            sentenciaSql = "INSERT INTO public.registro_orden_produccion(\n"
                    + "	codigo_orden, cantidad, unidad_medida, estado, \"Codigo_producto\", codigo_formula)\n"
                    + "	VALUES (" + producto.getCodigoOrden() + "," + producto.getCantidad() + ",'" + producto.getUnidadMedida() + "','"
                    + producto.getEstado() + "', " + producto.getCodigoProducto() +", " + producto.getCodigoFormula()+ ");";
            return conexion.insertar(sentenciaSql);
        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public int insertarSolicitud(SolicitudOrden orden) {
        try {
            sentenciaSql = "INSERT INTO public.orden_produccion(fecha_orden, fecha_fin, descripcion, estado, codigo_secundario, codido_bodega)\n"
                    + "	VALUES ('" + orden.getFecha_orden() + "', '" + orden.getFecha_fin() + "', "
                    + "'" + orden.getDescripcion() + "', '" + orden.getEstado() + "', '" + orden.getCodigoSecundario() + "'," + orden.getCodigo_bodega() + ");";
            return conexion.insertar(sentenciaSql);
        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }

    public List<productosOrden> getAticulosOrden() {
        List<productosOrden> ordenProducto = new ArrayList<>();
        sentenciaSql = String.format("select distinct a.id,a.nombre,a.descripcion,ab.cant,a.costo,t.tipo,um.unidad_medida\n"
                + "		 from articulos as a \n"
                + "		 inner join articulo_bodega as ab on ab.id_articulo=a.id\n"
                + "		 inner join tipo as t on a.id_tipo=t.cod\n"
                + "		 inner join unidades_medidas as um on a.id_unidadmedida=um.id\n"
                + "		 inner join formula as f on a.id=f.codigo_producto\n"
                + "		 where tipo='Producto Terminado'or tipo='Producto SemiElaborado'");
        try {
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                ordenProducto.add(new productosOrden(resultSet.getInt("id"),
                        resultSet.getString("nombre"), resultSet.getString("descripcion"),
                        resultSet.getFloat("cant"), resultSet.getFloat("costo"), resultSet.getString("tipo"), resultSet.getString("unidad_medida")));

            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return ordenProducto;
    }

    public int idSolicitud() {
        try {
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

    public List<Bodega> getBodega() {
        List<Bodega> bodega = new ArrayList<>();
        sentenciaSql = String.format("select * from bodega;");
        try {
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                bodega.add(new Bodega(resultSet.getInt("cod"),
                        resultSet.getString("nombre_bodega")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return bodega;
    }
    public List<OrdenTrabajo> getListaFormula(int codigo_producto) {
        List<OrdenTrabajo> ordenProducto = new ArrayList<>();
        sentenciaSql = String.format("select f.codigo_formula,f.nombre_formula from formula as f \n"
                + "	inner join articulos as a on f.codigo_producto=a.id\n"
                + "	where f.codigo_producto=" + codigo_producto + ";");
        try {
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                ordenProducto.add(new OrdenTrabajo(resultSet.getInt("codigo_formula"),
                        resultSet.getString("nombre_formula")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return ordenProducto;
    }
}
