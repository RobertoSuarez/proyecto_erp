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
import com.contabilidad.dao.SubCuentaDAO;
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
//funcion boleana para registrar un activo tangible depreciable el cual 
// recibe 2 parametros de tipo activo fijo y de activo depreciable

    public boolean registrarTangibleDepreciable(ActivosFijos activosFijos, ActivoDepreciable activodepreciable) throws SQLException {

        Conexion conexion = new Conexion();
        try{
        String consulta = String.format("INSERT INTO activos_fijos(\n"
                + "	detalle_de_activo,  valor_adquisicion, fecha_adquisicion,idproveedor,numero_factura,estado)\n"
                + "	VALUES ('%s', '%s', '%s', '%s', '%s','habilitado')returning id_activo_fijo;", activosFijos.getDetalle_de_activo(),
                activosFijos.getValor_adquisicion(), activosFijos.getFecha_adquisicion(), activosFijos.getIdproveedor(), activosFijos.getNumero_factura());
        String idactivofijo = conexion.obtenerValor(consulta, 1);
        String consulta2 = String.format("INSERT INTO public.fijo_tangible_depreciable(\n"
                + "	 id_activo_fijo, depreciacion_meses, porcentaje_depreciacion, id_subcuenta)\n"
                + "	VALUES ( '%s', '%s', '%s', '%s');", idactivofijo, activodepreciable.getDepreciacion_meses(), activodepreciable.getPorcentaje_depreciacion(), activodepreciable.getSubCuenta().getId());
        conexion.ejecutarSql(consulta2);
        String consulta3 = String.format("select *from listardepreciables();");
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

//retornar un boleano si el activo ha sido editable
    public boolean editarTangibleDepreciable(ListaDepreciable li) throws SQLException {

        Conexion conexion = new Conexion();
        try{
        String consulta = String.format("UPDATE public.activos_fijos\n"
                + "	SET detalle_de_activo='%s', valor_adquisicion='%s', fecha_adquisicion='%s', idproveedor='%s', numero_factura='%s'\n"
                + "	WHERE id_activo_fijo='%s';", li.getDetalle_de_activo(), li.getValor_adquisicion(),
                li.getFecha_adquisicion(), li.getIdproveedor(), li.getNumero_factura(), li.getId_activo_fijo());
        //String idactivofijo = conexion.obtenerValor(consulta, 1);
        conexion.ejecutarSql(consulta);
        String consulta2 = String.format("UPDATE public.fijo_tangible_depreciable\n"
                + "	SET  depreciacion_meses='%s',  porcentaje_depreciacion='%s',  id_subcuenta='%s' \n"
                + "	WHERE id_activo_fijo='%s';", li.getDepreciacion_meses(),
                li.getPorcentaje_depreciacion(), li.getSubCuenta().getId(), li.getId_activo_fijo());
        conexion.ejecutarSql(consulta2);
        String consulta3 = String.format("select *from listardepreciables();");
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

    //Operacion para la depreciacion
    public int actualizarcuotadepresiacion() {
            Conexion conexion = new Conexion();
        try {
            String consulta = String.format("update fijo_tangible_depreciable set cuota_depresiacion=\n"
                    + "(select (af.valor_adquisicion*(ftd.porcentaje_depreciacion/100))/ftd.depreciacion_meses as calculo from activos_fijos as af \n"
                    + "inner join fijo_tangible_depreciable as ftd\n"
                    + "on af.id_activo_fijo=ftd.id_activo_fijo\n"
                    + "where id_depreciable=(select max(id_depreciable) from fijo_tangible_depreciable))\n"
                    + "where id_depreciable=(select max(id_depreciable) from fijo_tangible_depreciable)");

            conexion.ejecutarSql(consulta);
            return 1;
        } catch (Exception e) {
            return -1;
        }
        finally{
            conexion.desconectar();
        }
    }

    public boolean eliminar(ListaDepreciable li) throws SQLException {

        Conexion conexion = new Conexion();
        try{
        String consulta = String.format("UPDATE public.activos_fijos\n"
                + "	SET  estado='deshabilitado'\n"
                + "	WHERE id_activo_fijo='%s';", li.getId_activo_fijo());
        //String idactivofijo = conexion.obtenerValor(consulta, 1);
        conexion.ejecutarSql(consulta);

        System.out.println("update 1: " + consulta);
        return true;
        } catch (Exception e) {
            return false;
        }
        finally{
            conexion.desconectar();
        }
    }
// lsitamos los activos depreciables 

    public List<ListaDepreciable> Listardepreciable() throws Exception {
        List<ListaDepreciable> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        System.out.println("Conectado a la db");
        try {
            conexion.conectar();
            // Consulta.
            PreparedStatement st = conexion.connection.prepareStatement(
                    "select *from activos_fijos, fijo_tangible_depreciable, proveedor\n"
                    + "where fijo_tangible_depreciable.id_activo_fijo = activos_fijos.id_activo_fijo\n"
                    + "and activos_fijos.idproveedor=proveedor.idproveedor\n"
                    + "and activos_fijos.estado='habilitado';");
            // Ejecución
            ResultSet rs = st.executeQuery();
            SubCuentaDAO subCuentaDAO = new SubCuentaDAO();
            DepreciacionActivosFijosDAO depreciacionActivosFijosDAO = new DepreciacionActivosFijosDAO();
            while (rs.next()) {
                ListaDepreciable listadepreciablevacia = new ListaDepreciable();
                listadepreciablevacia.setIdDepreciable(rs.getInt("id_depreciable"));
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
                listadepreciablevacia.setSubCuenta(subCuentaDAO.getSubCuenta(rs.getInt("id_subcuenta")));
                listadepreciablevacia.setFaltanDepreciacion(depreciacionActivosFijosDAO.FaltaDepreciacion(listadepreciablevacia.getIdDepreciable(), listadepreciablevacia.getDepreciacion_meses()));
                lista.add(listadepreciablevacia);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            conexion.desconectar();
        }
        return lista;
    }

    public ListaDepreciable ObtenerdepreciablePorId(int idFijoTangibleDepreciable) {
        ListaDepreciable listaDepreciable = new ListaDepreciable();
        Conexion conexion = new Conexion();
        System.out.println("Conectado a la db");
        try {
            conexion.conectar();
            // Consulta.
            PreparedStatement st = conexion.connection.prepareStatement(
                    "select *from activos_fijos, fijo_tangible_depreciable, proveedor\n"
                    + "where fijo_tangible_depreciable.id_activo_fijo = activos_fijos.id_activo_fijo\n"
                    + "and activos_fijos.idproveedor=proveedor.idproveedor\n"
                    + "and activos_fijos.estado='habilitado'\n"
                    + "and fijo_tangible_depreciable.id_depreciable = " + idFijoTangibleDepreciable + ";");
            // Ejecución
            ResultSet rs = st.executeQuery();
            SubCuentaDAO subCuentaDAO = new SubCuentaDAO();
            while (rs.next()) {
                listaDepreciable = new ListaDepreciable();
                listaDepreciable.setIdDepreciable(rs.getInt("id_depreciable"));
                listaDepreciable.setId_activo_fijo(rs.getInt("id_activo_fijo"));
                listaDepreciable.setDetalle_de_activo(rs.getString("detalle_de_activo"));
                listaDepreciable.setValor_adquisicion(rs.getInt("valor_adquisicion"));
                listaDepreciable.setFecha_adquisicion(rs.getObject("fecha_adquisicion", LocalDate.class));
                listaDepreciable.setId_empresa(rs.getInt("id_empresa"));
                listaDepreciable.setDepreciacion_meses(rs.getInt("depreciacion_meses"));
                listaDepreciable.setCuota_depresiacion(rs.getDouble("cuota_depresiacion"));
                listaDepreciable.setPorcentaje_depreciacion(rs.getDouble("porcentaje_depreciacion"));
                listaDepreciable.setValor_neto_libros(rs.getDouble("valor_neto_libros"));
                listaDepreciable.setIdproveedor(rs.getInt("idproveedor"));
                listaDepreciable.setProveedor(rs.getString("nombre"));
                listaDepreciable.setNumero_factura(rs.getString("numero_factura"));
                listaDepreciable.setSubCuenta(subCuentaDAO.getSubCuenta(rs.getInt("id_subcuenta")));
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return listaDepreciable;
    }
// deshabilitamos un activo depreciable

    public boolean deshabilitartangible(ListaDepreciable li) throws SQLException {

        Conexion conexion = new Conexion();
        try{
        String consulta = String.format("UPDATE public.activos_fijos\n"
                + "	SET  estado='deshabilitado'\n"
                + "	WHERE id_activo_fijo='%s';", li.getId_activo_fijo());
        //String idactivofijo = conexion.obtenerValor(consulta, 1);
        conexion.ejecutarSql(consulta);
        System.out.println("update 1: " + consulta);
        return true;
        } catch (Exception e) {
            return false;
        }
        finally{
            conexion.desconectar();
        }
    }
//listamos un activo depreciable deshabilitado 

    public List<ListaDepreciable> listaradepreciablesdeshabilitados() throws Exception {
        List<ListaDepreciable> listtang = new ArrayList<>();
        Conexion conexion = new Conexion();
        System.out.println("Conectado a la db");
        try {
            conexion.conectar();
            // Consulta.
            PreparedStatement st = conexion.connection.prepareStatement(
                    "select *from activos_fijos, fijo_tangible_depreciable, proveedor\n"
                    + "where fijo_tangible_depreciable.id_activo_fijo = activos_fijos.id_activo_fijo\n"
                    + "and activos_fijos.idproveedor=proveedor.idproveedor\n"
                    + "and activos_fijos.estado='deshabilitado';");
            // Ejecución
            ResultSet rs = st.executeQuery();
            SubCuentaDAO subCuentaDAO = new SubCuentaDAO();
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
                listadepreciablevacia.setSubCuenta(subCuentaDAO.getSubCuenta(rs.getInt("id_subcuenta")));
                listtang.add(listadepreciablevacia);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            conexion.desconectar();
        }

        return listtang;
    }
//funsion para habilitar un activo depreciable

    public boolean habilitardepreciable(ActivoDepreciable li) throws SQLException {

        Conexion conexion = new Conexion();
        try{
        String consulta = String.format("UPDATE public.activos_fijos\n"
                + "	SET  estado='habilitado'\n"
                + "	WHERE id_activo_fijo='%s';", li.getId_activo_fijo());
        //String idactivofijo = conexion.obtenerValor(consulta, 1);
        conexion.ejecutarSql(consulta);
        System.out.println("update 1: " + consulta);
        return true;
        } catch (Exception e) {
            return false;
        }
        finally{
            conexion.desconectar();
        }
    }
}
