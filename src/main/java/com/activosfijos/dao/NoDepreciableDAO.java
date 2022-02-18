/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.dao;

import java.sql.SQLException;
import com.activosfijos.model.ActivosFijos;
import com.activosfijos.model.ActivoNoDepreciable;
import com.activosfijos.model.ListaDepreciable;
import com.activosfijos.model.ListaNoDepreciable;
import com.global.config.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author desta
 */
public class NoDepreciableDAO {

    Conexion conexion = new Conexion();
    ResultSet result;

    public boolean guardar1(ActivosFijos activosFijos, ActivoNoDepreciable activoNoDepreciable) throws SQLException {

        Conexion conexion = new Conexion();
        String consulta = String.format("INSERT INTO activos_fijos(\n"
                + "	detalle_de_activo,  valor_adquisicion, fecha_adquisicion,idproveedor,numero_factura,estado)\n"
                + "	VALUES ('%s', '%s', '%s', '%s', '%s','habilitado')returning id_activo_fijo;", activosFijos.getDetalle_de_activo(),
                activosFijos.getValor_adquisicion(), activosFijos.getFecha_adquisicion(), activosFijos.getIdproveedor(), activosFijos.getNumero_factura());
        String idactivofijo = conexion.obtenerValor(consulta, 1);
        String consulta2 = String.format("INSERT INTO public.fijo_tanginle_no_depreciable(\n"
                + "	 id_activo_fijo,    plusvalia)"
                + "VALUES ( '%s', '%s');", idactivofijo, activoNoDepreciable.getPlusvalia());
        conexion.ejecutar(consulta2);
        String consulta3 = String.format("select *from listarnodepreciables();");
        conexion.ejecutar(consulta3);
        System.out.println(consulta + "\n" + consulta2 + "\n funcion : " + consulta3);
        return true;
    }

    public boolean editar1(ListaNoDepreciable li) throws SQLException {

        Conexion conexion = new Conexion();
        String consulta = String.format("UPDATE public.activos_fijos\n"
                + "	SET detalle_de_activo='%s', valor_adquisicion='%s', fecha_adquisicion='%s',   idproveedor='%s', numero_factura='%s'\n"
                + "	WHERE id_activo_fijo='%s';", li.getDetalle_de_activo(), li.getValor_adquisicion(),
                li.getFecha_adquisicion(), li.getIdproveedor(), li.getNumero_factura(), li.getId_activo_fijo());
        //String idactivofijo = conexion.obtenerValor(consulta, 1);
        conexion.ejecutar(consulta);
        String consulta2 = String.format("UPDATE public.fijo_tanginle_no_depreciable\n"
                + "	SET  plusvalia='%s'\n"
                + "	WHERE id_activo_fijo='%s' ;", li.getPlusvalia(), li.getId_activo_fijo());
        conexion.ejecutar(consulta2);
        String consulta3 = String.format("select *from listarnodepreciables();");
        conexion.ejecutar(consulta3);
        System.out.println("update 1: " + consulta + "\n update 2: " + consulta2 + "\n funcion : " + consulta3);
        return true;
    }

    public List<ListaNoDepreciable> ListarNodepreciable() throws Exception {
        List<ListaNoDepreciable> listaNP = new ArrayList<>();
        String sentencia = "";
        System.out.println("Conectado a la db");
        try {
            conexion.abrirConexion();
            // Consulta.
            sentencia
                    = "select *from activos_fijos, fijo_tanginle_no_depreciable, proveedor\n"
                    + "where fijo_tanginle_no_depreciable.id_activo_fijo = activos_fijos.id_activo_fijo\n"
                    + "and activos_fijos.idproveedor=proveedor.idproveedor\n"
                    + "and activos_fijos.estado='habilitado';";
            // Ejecución
            result = conexion.ejecutarConsulta(sentencia);

            while (result.next()) {
                ListaNoDepreciable listaNoDepreciable = new ListaNoDepreciable();
                listaNoDepreciable.setId_activo_fijo(result.getInt("id_activo_fijo"));
                listaNoDepreciable.setDetalle_de_activo(result.getString("detalle_de_activo"));
                listaNoDepreciable.setValor_adquisicion(result.getInt("valor_adquisicion"));
                listaNoDepreciable.setFecha_adquisicion(result.getObject("fecha_adquisicion", LocalDate.class));
                listaNoDepreciable.setId_empresa(result.getInt("id_empresa"));
                listaNoDepreciable.setTiempo_amortizacion(result.getInt("tiempo_amortizacion"));
                listaNoDepreciable.setPorcentaje_amortizacion(result.getDouble("porcentaje_amortizacion"));
                listaNoDepreciable.setCapitalizacion_meses(result.getInt("capitalizacion_meses"));
                listaNoDepreciable.setRevalorizar(result.getDouble("revalorizar"));
                listaNoDepreciable.setPlusvalia(result.getDouble("plusvalia"));
                listaNoDepreciable.setIdproveedor(result.getInt("idproveedor"));
                // listaNoDepreciable.setProveedor(rs.getString("proveedor"));
                listaNoDepreciable.setNumero_factura(result.getString("numero_factura"));
                listaNP.add(listaNoDepreciable);

            }
            conexion.cerrarConexion();
        } catch (SQLException e) {
            throw e;
        } finally {
            conexion.cerrarConexion();
        }

        return listaNP;
    }

    public List<ListaNoDepreciable> ListarNodepreciableDeshabilitados() throws Exception {
        List<ListaNoDepreciable> listaNP = new ArrayList<>();
        String sentencia = "";
        try {
            conexion.abrirConexion();
            // Consulta.
            sentencia
                    = "select *from activos_fijos, fijo_tanginle_no_depreciable, proveedor\n"
                    + "where fijo_tanginle_no_depreciable.id_activo_fijo = activos_fijos.id_activo_fijo\n"
                    + "and activos_fijos.idproveedor=proveedor.idproveedor\n"
                    + "and activos_fijos.estado='deshabilitado';";
            // Ejecución
            result = conexion.ejecutarSql(sentencia);

            while (result.next()) {
                ListaNoDepreciable listaNoDepreciable = new ListaNoDepreciable();
                listaNoDepreciable.setId_activo_fijo(result.getInt("id_activo_fijo"));
                listaNoDepreciable.setDetalle_de_activo(result.getString("detalle_de_activo"));
                listaNoDepreciable.setValor_adquisicion(result.getInt("valor_adquisicion"));
                listaNoDepreciable.setFecha_adquisicion(result.getObject("fecha_adquisicion", LocalDate.class));
                listaNoDepreciable.setId_empresa(result.getInt("id_empresa"));
                listaNoDepreciable.setTiempo_amortizacion(result.getInt("tiempo_amortizacion"));
                listaNoDepreciable.setPorcentaje_amortizacion(result.getDouble("porcentaje_amortizacion"));
                listaNoDepreciable.setCapitalizacion_meses(result.getInt("capitalizacion_meses"));
                listaNoDepreciable.setRevalorizar(result.getDouble("revalorizar"));
                listaNoDepreciable.setPlusvalia(result.getDouble("plusvalia"));
                listaNoDepreciable.setIdproveedor(result.getInt("idproveedor"));
                // listaNoDepreciable.setProveedor(rs.getString("proveedor"));
                listaNoDepreciable.setNumero_factura(result.getString("numero_factura"));
                listaNP.add(listaNoDepreciable);
            }
            conexion.cerrarConexion();
        } catch (SQLException e) {
            e.toString();
            conexion.cerrarConexion();
        } finally {
            conexion.cerrarConexion();
        }
        return listaNP;
    }

    public boolean deshabilitarnoDepreciable(ListaNoDepreciable li) throws SQLException {
        String consulta = "";
        try {

            conexion.abrirConexion();
            consulta = String.format("SELECT public.deshabilitarnodepreciable(" + li.getId_activo_fijo() + ")");
            conexion.ejecutar(consulta);
            conexion.cerrarConexion();
        } catch (SQLException e) {
            System.out.println(consulta);
        } finally {
            conexion.cerrarConexion();
        }
        return true;
    }

    public boolean habilitarnoDepreciable(ActivoNoDepreciable li) throws SQLException {
        String consulta = "";
        try {
            conexion.abrirConexion();
            consulta = String.format("SELECT public.habilitarnodepreciable(" + li.getId_activo_fijo() + ")");
            conexion.ejecutar(consulta);
            conexion.cerrarConexion();
        } catch (SQLException e) {
            System.out.println(consulta);
        } finally {
            conexion.cerrarConexion();
        }
        return true;
    }
}
