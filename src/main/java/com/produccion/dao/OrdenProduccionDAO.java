/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.ArticuloFormula;
import com.produccion.models.CentroCosto;
import com.produccion.models.FormulaProduccion;
import com.produccion.models.OrdenProduccion;
import com.produccion.models.OrdenTrabajo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class OrdenProduccionDAO {

    Conexion conexion;
    private ResultSet resultSet;
    private String sentenciaSql;

    public OrdenProduccionDAO() {
        conexion = new Conexion();
    }

    public List<OrdenProduccion> getListaOrden() {
        List<OrdenProduccion> ordenProduccion = new ArrayList<>();
        sentenciaSql = String.format("select * from orden_produccion;");
        try {
            //llamamos a la conexion
            conexion.conectar();
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                ordenProduccion.add(new OrdenProduccion(resultSet.getInt("codigo_orden"), resultSet.getDate("fecha_orden"),
                        resultSet.getDate("fecha_fin"), resultSet.getString("descripcion"), resultSet.getString("estado")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return ordenProduccion;
    }

    public List<OrdenTrabajo> getListaProducto(int codigo_orden) {
        List<OrdenTrabajo> ordenProducto = new ArrayList<>();
        sentenciaSql = String.format("select a.id,rop.codigo_registro,a.nombre,rop.cantidad from orden_produccion as op\n"
                + "	inner join registro_orden_produccion as rop on op.codigo_orden=rop.codigo_orden\n"
                + "	inner join articulos as a on rop.\"Codigo_producto\"=a.id\n"
                + "	where op.codigo_orden=" + codigo_orden + ";");
        try {
            //llamamos a la conexion
            conexion.conectar();
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                ordenProducto.add(new OrdenTrabajo(resultSet.getInt("id"), resultSet.getInt("codigo_registro"),
                        resultSet.getString("nombre"), resultSet.getFloat("cantidad")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return ordenProducto;
    }

    public List<OrdenTrabajo> getListaFormula(int codigo_producto) {
        List<OrdenTrabajo> ordenProducto = new ArrayList<>();
        sentenciaSql = String.format("select f.codigo_formula,f.nombre_formula from formula as f \n"
                + "	inner join articulos as a on f.codigo_producto=a.id\n"
                + "	where f.codigo_producto=" + codigo_producto + ";");
        try {
            //llamamos a la conexion
            conexion.conectar();
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

    public String getProceso(int codigo_formula) {
        String proceso = "";
        sentenciaSql = String.format("select pp.codigo_proceso,pp.nombre from formula as f\n"
                + "	inner join proceso_produccion as pp\n"
                + "	on f.codigo_proceso=pp.codigo_proceso\n"
                + "	where f.codigo_formula=" + codigo_formula + ";");
        try {
            //llamamos a la conexion
            conexion.conectar();
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                proceso = resultSet.getString("nombre");
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return proceso;
    }

    public List<CentroCosto> getListaCentro() {
        List<CentroCosto> centro = new ArrayList<>();
        sentenciaSql = String.format("select codigo_centroc,nombre from centro_costo;");
        try {
            //llamamos a la conexion
            conexion.conectar();
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                centro.add(new CentroCosto(resultSet.getInt("codigo_centroc"),
                        resultSet.getString("nombre")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return centro;
    }

    public List<ArticuloFormula> getListaConsumoMateriales(int codigo, float cantidad) {
        List<ArticuloFormula> articulosFormula = new ArrayList<>();
        sentenciaSql = String.format("select a.id,a.nombre,a.descripcion, df.\"cantidadUnidad\"*" + cantidad + " as cantidadUnidad,t.tipo ,df.unidad_medida "
                + ",a.costo from detalle_formula as df \n"
                + "	inner join articulos as a on df.codigo_producto=a.id\n"
                + "	inner join tipo as t on t.cod=a.id_tipo\n"
                + "	where df.codigo_formula=" + codigo + ";");
        try {
            //llamamos a la conexion
            conexion.conectar();
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                articulosFormula.add(new ArticuloFormula(resultSet.getInt("id"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion"), resultSet.getString("tipo"),
                        resultSet.getFloat("cantidadUnidad"), resultSet.getString("unidad_medida"), resultSet.getFloat("costo")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return articulosFormula;
    }

    public boolean renderProduccionDetallada(int id_formula) {
        boolean verifica = false;
        sentenciaSql = String.format("select estado from formula \n"
                + "	where codigo_formula=" + id_formula + ";");
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sentenciaSql);
            while (resultSet.next()) {
                verifica = resultSet.getBoolean("estado");
            }
            return verifica;
        } catch (SQLException e) {
            return verifica;
        } finally {
            conexion.desconectar();
        }
    }

    public List<FormulaProduccion> getListaCosto(int codigo_formula,float unidad) {
        List<FormulaProduccion> costos = new ArrayList<>();
        sentenciaSql = String.format("select nombre_formula, tiempo_unidad*"+unidad+" as tiempo,((tiempo_unidad*"+unidad+")*modunidad) as MO,\n"
                + "	((tiempo_unidad*"+unidad+")*cifunidad) as CIF from formula\n"
                + "	where codigo_formula="+codigo_formula+";");
        try {
            //llamamos a la conexion
            conexion.conectar();
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                costos.add(new FormulaProduccion(resultSet.getString("nombre_formula"), resultSet.getFloat("tiempo"),
                        resultSet.getFloat("mo"), resultSet.getFloat("cif")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return costos;
    }
}
