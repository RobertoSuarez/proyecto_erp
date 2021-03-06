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
//funcion utilizada para el registro de un activo no depreciable
    public boolean registrarTangibleNoDepreciable(ActivosFijos activosFijos, ActivoNoDepreciable activoNoDepreciable) throws SQLException {

        Conexion conexion = new Conexion();
        try{
        String consulta = String.format("INSERT INTO activos_fijos(\n"
                + "	detalle_de_activo,  valor_adquisicion, fecha_adquisicion,idproveedor,numero_factura,estado)\n"
                + "	VALUES ('%s', '%s', '%s', '%s', '%s','habilitado')returning id_activo_fijo;", activosFijos.getDetalle_de_activo(),
                activosFijos.getValor_adquisicion(), activosFijos.getFecha_adquisicion(), activosFijos.getIdproveedor(), activosFijos.getNumero_factura());
        String idactivofijo = conexion.obtenerValor(consulta, 1);
        String consulta2 = String.format("INSERT INTO public.fijo_tanginle_no_depreciable(\n"
                + "	 id_activo_fijo,    plusvalia)"
                + "VALUES ( '%s', '%s');", idactivofijo, activoNoDepreciable.getPlusvalia());
        conexion.ejecutarSql(consulta2);
        String consulta3 = String.format("select *from listarnodepreciables();");
        conexion.ejecutarSql(consulta3);
        System.out.println(consulta + "\n" + consulta2 + "\n funcion : " + consulta3);
        return true;
        } catch (Exception e) {
            return false;
        }
        finally{
            conexion.desconectar();
        }
    }
//funcion,la cual nos servira para editar un activo no depreciable
    public boolean editarNoDepreciable(ListaNoDepreciable li) throws SQLException {

        Conexion conexion = new Conexion();
        try{
        String consulta = String.format("UPDATE public.activos_fijos\n"
                + "	SET detalle_de_activo='%s', valor_adquisicion='%s', fecha_adquisicion='%s',   idproveedor='%s', numero_factura='%s'\n"
                + "	WHERE id_activo_fijo='%s';", li.getDetalle_de_activo(), li.getValor_adquisicion(),
                li.getFecha_adquisicion(), li.getIdproveedor(), li.getNumero_factura(), li.getId_activo_fijo());
        //String idactivofijo = conexion.obtenerValor(consulta, 1);
        conexion.ejecutarSql(consulta);
        String consulta2 = String.format("UPDATE public.fijo_tanginle_no_depreciable\n"
                + "	SET  plusvalia='%s'\n"
                + "	WHERE id_activo_fijo='%s' ;", li.getPlusvalia(), li.getId_activo_fijo());
        conexion.ejecutarSql(consulta2);
        String consulta3 = String.format("select *from listarnodepreciables();");
        conexion.ejecutarSql(consulta3);
        System.out.println("update 1: " + consulta + "\n update 2: " + consulta2 + "\n funcion : " + consulta3);
        return true;
        } catch (Exception e) {
            return false;
        }
        finally{
            conexion.desconectar();
        }
    }

    public List<ListaNoDepreciable> ListarNodepreciable() throws Exception {
        List<ListaNoDepreciable> listaNP = new ArrayList<>();
        String sentencia = "";
        System.out.println("Conectado a la db");
        try {
            conexion.conectar();
            // Consulta.
            sentencia
                    = "select *from activos_fijos, fijo_tanginle_no_depreciable, proveedor\n"
                    + "where fijo_tanginle_no_depreciable.id_activo_fijo = activos_fijos.id_activo_fijo\n"
                    + "and activos_fijos.idproveedor=proveedor.idproveedor\n"
                    + "and activos_fijos.estado='habilitado';";
            // Ejecuci??n
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
                listaNoDepreciable.setProveedor(result.getString("nombre"));
                listaNoDepreciable.setNumero_factura(result.getString("numero_factura"));
                listaNP.add(listaNoDepreciable);

            }
            conexion.desconectar();
        } catch (SQLException e) {
            throw e;
        } finally {
            conexion.desconectar();
        }

        return listaNP;
    }
// funcion que retorna un lista de un activo no depreciable 
    public List<ListaNoDepreciable> ListarNodepreciableDeshabilitados() throws Exception {
        List<ListaNoDepreciable> listaNP = new ArrayList<>();
        String sentencia = "";
        try {
            conexion.conectar();
            // Consulta.
            sentencia
                    = "select *from activos_fijos, fijo_tanginle_no_depreciable, proveedor\n"
                    + "where fijo_tanginle_no_depreciable.id_activo_fijo = activos_fijos.id_activo_fijo\n"
                    + "and activos_fijos.idproveedor=proveedor.idproveedor\n"
                    + "and activos_fijos.estado='deshabilitado';";
            // Ejecuci??n
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
                listaNoDepreciable.setProveedor(result.getString("nombre"));
                listaNoDepreciable.setNumero_factura(result.getString("numero_factura"));
                listaNP.add(listaNoDepreciable);
            }
            conexion.desconectar();
        } catch (SQLException e) {
            e.toString();
            conexion.desconectar();
        } finally {
            conexion.desconectar();
        }
        return listaNP;
    }

    public List<ListaNoDepreciable> ListarNodepreciableReporte() throws Exception {
        List<ListaNoDepreciable> listaNP = new ArrayList<>();
        String sentencia = "";
        try {
            conexion.conectar();
            // Consulta.
            sentencia
                    = "select \n"
                    + "af.detalle_de_activo,\n"
                    + "af.valor_adquisicion,\n"
                    + "atnd.plusvalia,\n"
                    + "af.numero_factura,\n"
                    + "proveedor.nombre\n"
                    + "from activos_fijos af, fijo_tanginle_no_depreciable atnd, proveedor\n"
                    + "where atnd.id_activo_fijo = af.id_activo_fijo\n"
                    + "and af.idproveedor=proveedor.idproveedor\n"
                    + "and af.estado='habilitado';";
            // Ejecuci??n
            result = conexion.ejecutarSql(sentencia);

            while (result.next()) {
                ListaNoDepreciable listaNoDepreciable = new ListaNoDepreciable();
                listaNoDepreciable.setDetalle_de_activo(result.getString("detalle_de_activo"));
                listaNoDepreciable.setValor_adquisicion(result.getInt("valor_adquisicion"));
                listaNoDepreciable.setPlusvalia(result.getDouble("plusvalia"));
                listaNoDepreciable.setProveedor(result.getString("nombre"));
                listaNoDepreciable.setNumero_factura(result.getString("numero_factura"));
                listaNP.add(listaNoDepreciable);
            }
            conexion.desconectar();
        } catch (SQLException e) {
            e.toString();
            conexion.desconectar();
        } finally {
            conexion.desconectar();
        }
        return listaNP;
    }
//funcion que retorna un valor boleando el cual lo deshabilita
    public boolean deshabilitarnoDepreciable(ListaNoDepreciable li) throws SQLException {
        String consulta = "";
        try {

            conexion.conectar();
            consulta = String.format("SELECT public.deshabilitarnodepreciable(" + li.getId_activo_fijo() + ")");
            conexion.ejecutarSql(consulta);
            conexion.desconectar();
        } catch (Exception e) {
            System.out.println(consulta);
        } finally {
            conexion.desconectar();
        }
        return true;
    }
//funcion que retorna un valor boleano para habilitar un activo no depreciables
    public boolean habilitarnoDepreciable(ActivoNoDepreciable li) throws SQLException {
        String consulta = "";
        try {
            conexion.conectar();
            consulta = String.format("SELECT public.habilitarnodepreciable(" + li.getId_activo_fijo() + ")");
            conexion.ejecutarSql(consulta);
            conexion.desconectar();
        } catch (Exception e) {
            System.out.println(consulta);
        } finally {
            conexion.desconectar();
        }
        return true;
    }
}
