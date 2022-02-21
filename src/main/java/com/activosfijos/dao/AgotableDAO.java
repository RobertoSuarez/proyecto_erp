/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.dao;

import com.activosfijos.model.ActivoAgotable;
import com.activosfijos.model.ActivosFijos;
import com.activosfijos.model.ListaAgotable;
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
public class AgotableDAO {

    public boolean guardar2(ActivosFijos activosFijos, ActivoAgotable activoagotable) throws SQLException {

        Conexion conexion = new Conexion();
        String consulta = String.format("INSERT INTO activos_fijos(\n"
                + "	detalle_de_activo,  valor_adquisicion, fecha_adquisicion,idproveedor,numero_factura,estado)\n"
                + "	VALUES ('%s', '%s', '%s', '%s', '%s','habilitado')returning id_activo_fijo;", activosFijos.getDetalle_de_activo(),
                activosFijos.getValor_adquisicion(), activosFijos.getFecha_adquisicion(), activosFijos.getIdproveedor(), activosFijos.getNumero_factura());
        String idactivofijo = conexion.obtenerValor(consulta, 1);
        String consulta2 = String.format("INSERT INTO public.fijo_tangible_agotable(\n"
                + "	id_activo_fijo, stock)\n"
                + "	VALUES ('%s', '%s');", idactivofijo, activoagotable.getStock());
        conexion.ejecutarSql(consulta2);
        System.out.println(consulta + "\n" + consulta2);
        return true;
    }

    public List<ListaAgotable> listaragotables() throws Exception {
        List<ListaAgotable> listaago = new ArrayList<>();
        Conexion conexion = new Conexion();
        System.out.println("Conectado a la db");
        try {
            conexion.conectar();
            // Consulta.
            PreparedStatement st = conexion.connection.prepareStatement(
                    "Select *from proveedor, activos_fijos, fijo_tangible_agotable\n"
                    + "where fijo_tangible_agotable.id_activo_fijo = activos_fijos.id_activo_fijo \n"
                    + "and activos_fijos.idproveedor= proveedor.idproveedor\n"
                    + "and activos_fijos.estado='habilitado';	");
            // Ejecución
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ListaAgotable listaagotable = new ListaAgotable();
                listaagotable.setId_activo_fijo(rs.getInt("id_activo_fijo"));
                listaagotable.setDetalle_de_activo(rs.getString("detalle_de_activo"));
                listaagotable.setValor_adquisicion(rs.getInt("valor_adquisicion"));
                listaagotable.setFecha_adquisicion(rs.getObject("fecha_adquisicion", LocalDate.class));
                listaagotable.setId_empresa(rs.getInt("id_empresa"));
                listaagotable.setStock(rs.getInt("stock"));
                listaagotable.setIdproveedor(rs.getInt("idproveedor"));
                listaagotable.setProveedor(rs.getString("nombre"));
                listaagotable.setNumero_factura(rs.getString("numero_factura"));
                listaago.add(listaagotable);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            conexion.desconectar();
        }

        return listaago;
    }

    public List<ListaAgotable> listaragotablesDeshabilitados() throws Exception {
        List<ListaAgotable> listaago = new ArrayList<>();
        Conexion conexion = new Conexion();
        System.out.println("Conectado a la db");
        try {
            conexion.conectar();
            // Consulta.
            PreparedStatement st = conexion.connection.prepareStatement(
                    "Select *from proveedor, activos_fijos, fijo_tangible_agotable\n"
                    + "where fijo_tangible_agotable.id_activo_fijo = activos_fijos.id_activo_fijo \n"
                    + "and activos_fijos.idproveedor= proveedor.idproveedor\n"
                    + "and activos_fijos.estado='deshabilitado';					\n"
                    + "					");
            // Ejecución
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ListaAgotable listaagotable = new ListaAgotable();
                listaagotable.setId_activo_fijo(rs.getInt("id_activo_fijo"));
                listaagotable.setDetalle_de_activo(rs.getString("detalle_de_activo"));
                listaagotable.setValor_adquisicion(rs.getInt("valor_adquisicion"));
                listaagotable.setFecha_adquisicion(rs.getObject("fecha_adquisicion", LocalDate.class));
                listaagotable.setId_empresa(rs.getInt("id_empresa"));
                listaagotable.setStock(rs.getInt("stock"));
                listaagotable.setIdproveedor(rs.getInt("idproveedor"));
                listaagotable.setProveedor(rs.getString("nombre"));
                listaagotable.setNumero_factura(rs.getString("numero_factura"));
                listaago.add(listaagotable);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            conexion.desconectar();
        }

        return listaago;
    }

    public boolean editar2(ListaAgotable li) throws SQLException {

        Conexion conexion = new Conexion();
        String consulta = String.format("UPDATE public.activos_fijos\n"
                + "	SET detalle_de_activo='%s', valor_adquisicion='%s', fecha_adquisicion='%s',   idproveedor='%s', numero_factura='%s'\n"
                + "	WHERE id_activo_fijo='%s';", li.getDetalle_de_activo(), li.getValor_adquisicion(),
                li.getFecha_adquisicion(), li.getProveedor(), li.getNumero_factura(), li.getId_activo_fijo());
        //String idactivofijo = conexion.obtenerValor(consulta, 1);
        conexion.ejecutarSql(consulta);
        String consulta2 = String.format("UPDATE public.fijo_tangible_agotable\n"
                + "	SET  stock='%s'\n"
                + "	WHERE id_activo_fijo='%s';", li.getStock(), li.getId_activo_fijo());
        conexion.ejecutarSql(consulta2);
        System.out.println("update 1: " + consulta + "\n update 2: " + consulta2);
        return true;
    }

    public boolean deshabilitarnoagotable(ListaAgotable li) throws SQLException {

        Conexion conexion = new Conexion();
        String consulta = String.format("UPDATE public.activos_fijos\n"
                + "	SET  estado='deshabilitado'\n"
                + "	WHERE id_activo_fijo='%s';", li.getId_activo_fijo());
        //String idactivofijo = conexion.obtenerValor(consulta, 1);
        conexion.ejecutarSql(consulta);
        System.out.println("update 1: " + consulta);
        return true;
    }

    public boolean habilitarnoAgotable(ActivoAgotable li) throws SQLException {

        Conexion conexion = new Conexion();
        String consulta = String.format("UPDATE public.activos_fijos\n"
                + "	SET  estado='habilitado'\n"
                + "	WHERE id_activo_fijo='%s';", li.getId_activo_fijo());
        //String idactivofijo = conexion.obtenerValor(consulta, 1);
        conexion.ejecutarSql(consulta);
        System.out.println("update 1: " + consulta);
        return true;
    }
}
