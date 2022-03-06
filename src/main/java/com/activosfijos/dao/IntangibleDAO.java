/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.activosfijos.dao;

import com.activosfijos.model.ActivoIntangible;
import com.activosfijos.model.ActivosFijos;
import com.activosfijos.model.ListarIntangible;
import com.global.config.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

/**
 *
 * @author desta
 */
public class IntangibleDAO {

//Método que nos permite guardar un activo intangible agregado
    public boolean guardarActivoIntangible(ActivosFijos activosFijos, ActivoIntangible activointangible) throws SQLException {

        Conexion conexion = new Conexion();
        try {
            //Declaramos la consulta
            String consulta = String.format("INSERT INTO activos_fijos(\n"
                    + "	detalle_de_activo,  valor_adquisicion, fecha_adquisicion,idproveedor,numero_factura,estado)\n"
                    + "	VALUES ('%s', '%s', '%s', '%s', '%s','habilitado')returning id_activo_fijo;", activosFijos.getDetalle_de_activo(),
                    activosFijos.getValor_adquisicion(), activosFijos.getFecha_adquisicion(), activosFijos.getIdproveedor(), activosFijos.getNumero_factura());
            //Capturamos el id de activos fijos de la consulta a ejecutar      
            String idactivofijo = conexion.obtenerValor(consulta, 1);
            //Declaramos la consulta junto con el id anteriormente obtenido
            String consulta2 = String.format("INSERT INTO public.fijo_intangible(\n"
                    + "	 id_activo_fijo)\n"
                    + "	VALUES ('%s');", idactivofijo);
            //Ejecutamos la consulta 
            conexion.ejecutarSql(consulta2);
            System.out.println(consulta + "\n" + consulta2);
        } catch (Exception e) {
            System.out.println("com.activosfijos.dao.IntangibleDAO.guardar3()");
        } finally {
            //Cerramos la conexion
            conexion.desconectar();
        }

        return true;
    }

//Método que permite listar los activos tangibles habilitados de la base de datos
    public List<ListarIntangible> listarIntangibles() throws Exception {
        List<ListarIntangible> listInta = new ArrayList<>();
        Conexion conexion = new Conexion();
        System.out.println("Conectado a la db");
        try {
            conexion.conectar();
            // Consulta.
            PreparedStatement st = conexion.connection.prepareStatement(
                    "select *from activos_fijos, fijo_intangible, proveedor\n"
                    + "where fijo_intangible.id_activo_fijo = activos_fijos.id_activo_fijo\n"
                    + "and activos_fijos.idproveedor=proveedor.idproveedor\n"
                    + "and activos_fijos.estado='habilitado';");
            // Ejecución
            ResultSet rs = st.executeQuery();
            //Recorremos la lista agg los datos de la consulta
            while (rs.next()) {
                ListarIntangible listaintangible = new ListarIntangible();
                listaintangible.setId_activo_fijo(rs.getInt("id_activo_fijo"));
                listaintangible.setDetalle_de_activo(rs.getString("detalle_de_activo"));
                listaintangible.setValor_adquisicion(rs.getInt("valor_adquisicion"));
                listaintangible.setFecha_adquisicion(rs.getObject("fecha_adquisicion", LocalDate.class));
                listaintangible.setId_empresa(rs.getInt("id_empresa"));
                listaintangible.setId_intangible(rs.getInt("id_intangible"));
                listaintangible.setIdproveedor(rs.getInt("idproveedor"));
                listaintangible.setProveedor(rs.getString("nombre"));
                listaintangible.setNumero_factura(rs.getString("numero_factura"));
                listInta.add(listaintangible);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            //cerramos la conexion
            conexion.desconectar();
        }
        //retornamos la lista llena 
        return listInta;
    }
//Lista la informacion que se visualizara en el reporte a imprimir 

    public List<ListarIntangible> listaIntangiblesReporte() throws Exception {
        List<ListarIntangible> listInta = new ArrayList<>();
        Conexion conexion = new Conexion();
        System.out.println("Conectado a la db");
        try {
            conexion.conectar();
            // Consulta.
            PreparedStatement st = conexion.connection.prepareStatement(
                    "select af.detalle_de_activo,\n"
                    + "af.valor_adquisicion,\n"
                    + "af.numero_factura,\n"
                    + "af.fecha_adquisicion,\n"
                    + "proveedor.nombre\n"
                    + "from activos_fijos af, fijo_intangible afi, proveedor\n"
                    + "where afi.id_activo_fijo = af.id_activo_fijo\n"
                    + "and af.idproveedor=proveedor.idproveedor\n"
                    + "and af.estado='habilitado';");
            // Ejecución
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ListarIntangible listaintangible = new ListarIntangible();
                listaintangible.setDetalle_de_activo(rs.getString("detalle_de_activo"));
                listaintangible.setValor_adquisicion(rs.getInt("valor_adquisicion"));
                listaintangible.setProveedor(rs.getString("nombre"));
                listaintangible.setNumero_factura(rs.getString("numero_factura"));
                listInta.add(listaintangible);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            //Cerramos la conexion
            conexion.desconectar();
        }

        return listInta;
    }
//Retorna una lista de activos Intangibles que se encunetran deshabilitados en la base de datos

    public List<ListarIntangible> listarIntangiblesDeshabilitados() throws Exception {
        List<ListarIntangible> listInta = new ArrayList<>();
        Conexion conexion = new Conexion();
        System.out.println("Conectado a la db");
        try {
            conexion.conectar();
            // Consulta.
            PreparedStatement st = conexion.connection.prepareStatement(
                    "select *from activos_fijos, fijo_intangible, proveedor\n"
                    + "where fijo_intangible.id_activo_fijo = activos_fijos.id_activo_fijo\n"
                    + "and activos_fijos.idproveedor=proveedor.idproveedor\n"
                    + "and activos_fijos.estado='deshabilitado';");
            // Ejecución
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ListarIntangible listaintangible = new ListarIntangible();
                listaintangible.setId_activo_fijo(rs.getInt("id_activo_fijo"));
                listaintangible.setDetalle_de_activo(rs.getString("detalle_de_activo"));
                listaintangible.setValor_adquisicion(rs.getInt("valor_adquisicion"));
                listaintangible.setFecha_adquisicion(rs.getObject("fecha_adquisicion", LocalDate.class));
                listaintangible.setId_empresa(rs.getInt("id_empresa"));
                listaintangible.setId_intangible(rs.getInt("id_intangible"));
                listaintangible.setIdproveedor(rs.getInt("idproveedor"));
                listaintangible.setProveedor(rs.getString("nombre"));
                listaintangible.setNumero_factura(rs.getString("numero_factura"));
                listInta.add(listaintangible);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            //Cerramos la conexion
            conexion.desconectar();
        }

        return listInta;
    }

//Nos retorna un balor boleano para validar que se ha editado algun dato del activo
    public boolean editarActivosIntangibles(ListarIntangible li) throws SQLException {

        Conexion conexion = new Conexion();
        try {
            //declaracion de la consulta
            String consulta = String.format("UPDATE public.activos_fijos\n"
                    + "	SET detalle_de_activo='%s', valor_adquisicion='%s', fecha_adquisicion='%s',   idproveedor='%s', numero_factura='%s'\n"
                    + "	WHERE id_activo_fijo='%s';", li.getDetalle_de_activo(), li.getValor_adquisicion(),
                    li.getFecha_adquisicion(), li.getIdproveedor(), li.getNumero_factura(), li.getId_activo_fijo());

            System.out.println("update Editar intangible: " + consulta);
            //ejecucion de la consulta
            conexion.ejecutarSql(consulta);
        } catch (Exception e) {
            System.out.println("com.activosfijos.dao.IntangibleDAO.editar2()");
        } finally {
            //cerrar conexion
            conexion.desconectar();
        }

        return true;
    }
//Retorna un balor boleano que deshabilita un activo intangible

    public boolean deshabilitarIntangible(ListarIntangible li) throws SQLException {

        Conexion conexion = new Conexion();
        try {
            //declaramos la consulta
            String consulta = String.format("UPDATE public.activos_fijos\n"
                    + "	SET  estado='deshabilitado'\n"
                    + "	WHERE id_activo_fijo='%s';", li.getId_activo_fijo());
            //ejecutamos la consulta
            conexion.ejecutarSql(consulta);
            System.out.println("update 1: " + consulta);
        } catch (Exception e) {
            System.out.println("com.activosfijos.dao.IntangibleDAO.deshabilitarintangible()");
        } finally {
            //cerramos la conexion 
            conexion.desconectar();
        }
        return true;
    }
//Retorna un balor boleano que habilita un activo intangible

    public boolean habilitarIntangible(ActivoIntangible li) throws SQLException {

        Conexion conexion = new Conexion();
        try {
            //declaracion de la consulta
            String consulta = String.format("UPDATE public.activos_fijos\n"
                    + "	SET  estado='habilitado'\n"
                    + "	WHERE id_activo_fijo='%s';", li.getId_activo_fijo());
            //ejecucion de la consulta
            conexion.ejecutarSql(consulta);
            System.out.println("update 1: " + consulta);
        } catch (Exception e) {
            System.out.println("com.activosfijos.dao.IntangibleDAO.habilitarintangible()");
        } finally {
            //cerramos la conexion
            conexion.desconectar();
        }
        return true;
    }

}
