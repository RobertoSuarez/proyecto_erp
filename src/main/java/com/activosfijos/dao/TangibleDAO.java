/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.dao;

import java.sql.SQLException;
import com.activosfijos.model.ActivosFijos;
import com.activosfijos.model.ActivoDepreciable;
import com.activosfijos.model.ListaDepreciable;
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
public class TangibleDAO {

    public boolean guardar(ActivosFijos activosFijos, ActivoDepreciable activodepreciable) throws SQLException {

        Conexion conexion = new Conexion();
        String consulta = String.format("INSERT INTO activos_fijos(\n"
                + "	detalle_de_activo,  valor_adquisicion, fecha_adquisicion,idproveedor,numero_factura,estado)\n"
                + "	VALUES ('%s', '%s', '%s', '%s', '%s','habilitado')returning id_activo_fijo;", activosFijos.getDetalle_de_activo(),
                activosFijos.getValor_adquisicion(), activosFijos.getFecha_adquisicion(), activosFijos.getIdproveedor(), activosFijos.getNumero_factura());
        String idactivofijo = conexion.obtenerValor(consulta, 1);
        String consulta2 = String.format("INSERT INTO public.fijo_tangible_depreciable(\n"
                + "	 id_activo_fijo, depreciacion_meses, porcentaje_depreciacion)\n"
                + "	VALUES ( '%s', '%s', '%s');", idactivofijo, activodepreciable.getDepreciacion_meses(), activodepreciable.getPorcentaje_depreciacion());
        conexion.ejecutarSql(consulta2);
        String consulta3 = String.format("select *from listardepreciables();");
        conexion.ejecutarSql(consulta3);
        System.out.println(consulta + "\n" + consulta2 + "\n funcion : " + consulta3);
        return true;
    }

    public boolean editar(ListaDepreciable li) throws SQLException {

        Conexion conexion = new Conexion();
        String consulta = String.format("UPDATE public.activos_fijos\n"
                + "	SET detalle_de_activo='%s', valor_adquisicion='%s', fecha_adquisicion='%s', idproveedor='%s', numero_factura='%s'\n"
                + "	WHERE id_activo_fijo='%s';", li.getDetalle_de_activo(), li.getValor_adquisicion(),
                li.getFecha_adquisicion(), li.getIdproveedor(), li.getNumero_factura(), li.getId_activo_fijo());
        //String idactivofijo = conexion.obtenerValor(consulta, 1);
        conexion.ejecutarSql(consulta);
        String consulta2 = String.format("UPDATE public.fijo_tangible_depreciable\n"
                + "	SET  depreciacion_meses='%s',  porcentaje_depreciacion='%s' \n"
                + "	WHERE id_activo_fijo='%s';", li.getDepreciacion_meses(),
                li.getPorcentaje_depreciacion(), li.getId_activo_fijo());
        conexion.ejecutarSql(consulta2);
        String consulta3 = String.format("select *from listardepreciables();");
        conexion.ejecutarSql(consulta3);
        System.out.println("update 1: " + consulta + "\n update 2: " + consulta2 + "\n funcion : " + consulta3);
        return true;
    }

    public boolean eliminar(ListaDepreciable li) throws SQLException {

        Conexion conexion = new Conexion();
        String consulta = String.format("UPDATE public.activos_fijos\n"
                + "	SET  estado='deshabilitado'\n"
                + "	WHERE id_activo_fijo='%s';", li.getId_activo_fijo());
        //String idactivofijo = conexion.obtenerValor(consulta, 1);
        conexion.ejecutarSql(consulta);

        System.out.println("update 1: " + consulta);
        return true;
    }

    public List<ListaDepreciable> Listardepreciable() throws Exception {
        List<ListaDepreciable> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        System.out.println("Conectado a la db");
        try {
            conexion.conectar();
            // Consulta.
            PreparedStatement st = conexion.conex.prepareStatement(
                    "select *from activos_fijos, fijo_tangible_depreciable, proveedor\n" +
"where fijo_tangible_depreciable.id_activo_fijo = activos_fijos.id_activo_fijo\n" +
"and activos_fijos.idproveedor=proveedor.idproveedor\n" +
"and activos_fijos.estado='habilitado';");
            // Ejecución
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ListaDepreciable listadepreciablevacia = new ListaDepreciable();
                listadepreciablevacia.setId_activo_fijo(rs.getInt("id_activo_fijo"));
                listadepreciablevacia.setDetalle_de_activo(rs.getString("detalle_de_activo"));
                listadepreciablevacia.setValor_adquisicion(rs.getInt("valor_adquisicion"));
                listadepreciablevacia.setFecha_adquisicion(rs.getObject("fecha_adquisicion", LocalDate.class));
                listadepreciablevacia.setId_empresa(rs.getInt("id_empresa"));
                listadepreciablevacia.setDepreciacion_meses(rs.getInt("depreciacion_meses"));
                listadepreciablevacia.setCuota_depresiacion(rs.getDouble("cuota_depresiacion"));
                listadepreciablevacia.setPorcentaje_depreciacion(rs.getDouble("porcentaje_depreciacion"));
                listadepreciablevacia.setValor_neto_libros(rs.getDouble("valor_neto_libros"));
                listadepreciablevacia.setIdproveedor(rs.getInt("idproveedor"));
                listadepreciablevacia.setProveedor(rs.getString("nombre"));
                listadepreciablevacia.setNumero_factura(rs.getString("numero_factura"));
                lista.add(listadepreciablevacia);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            conexion.desconectar();
        }
        return lista;
    }

    public boolean deshabilitartangible(ListaDepreciable li) throws SQLException {

        Conexion conexion = new Conexion();
        String consulta = String.format("UPDATE public.activos_fijos\n"
                + "	SET  estado='deshabilitado'\n"
                + "	WHERE id_activo_fijo='%s';", li.getId_activo_fijo());
        //String idactivofijo = conexion.obtenerValor(consulta, 1);
        conexion.ejecutarSql(consulta);
        System.out.println("update 1: " + consulta);
        return true;
    }

    public List<ListaDepreciable> listaradepreciablesdeshabilitados() throws Exception {
        List<ListaDepreciable> listtang = new ArrayList<>();
        Conexion conexion = new Conexion();
        System.out.println("Conectado a la db");
        try {
            conexion.conectar();
            // Consulta.
            PreparedStatement st = conexion.conex.prepareStatement(
                    "select *from activos_fijos, fijo_tangible_depreciable, proveedor\n" +
"where fijo_tangible_depreciable.id_activo_fijo = activos_fijos.id_activo_fijo\n" +
"and activos_fijos.idproveedor=proveedor.idproveedor\n" +
"and activos_fijos.estado='deshabilitado';");
            // Ejecución
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ListaDepreciable listadepreciablevacia = new ListaDepreciable();
                listadepreciablevacia.setId_activo_fijo(rs.getInt("id_activo_fijo"));
                listadepreciablevacia.setDetalle_de_activo(rs.getString("detalle_de_activo"));
                listadepreciablevacia.setValor_adquisicion(rs.getInt("valor_adquisicion"));
                listadepreciablevacia.setFecha_adquisicion(rs.getObject("fecha_adquisicion", LocalDate.class));
                listadepreciablevacia.setId_empresa(rs.getInt("id_empresa"));
                listadepreciablevacia.setDepreciacion_meses(rs.getInt("depreciacion_meses"));
                listadepreciablevacia.setCuota_depresiacion(rs.getDouble("cuota_depresiacion"));
                listadepreciablevacia.setPorcentaje_depreciacion(rs.getDouble("porcentaje_depreciacion"));
                listadepreciablevacia.setValor_neto_libros(rs.getDouble("valor_neto_libros"));
                listadepreciablevacia.setIdproveedor(rs.getInt("idproveedor"));
                listadepreciablevacia.setProveedor(rs.getString("nombre"));
                listadepreciablevacia.setNumero_factura(rs.getString("numero_factura"));
                listtang.add(listadepreciablevacia);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            conexion.desconectar();
        }

        return listtang;
    }

    public boolean habilitardepreciable(ActivoDepreciable li) throws SQLException {

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
