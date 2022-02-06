
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.ProcesoProduccion;
import com.produccion.models.SubProceso;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class ProcesoProduccionDAO {

    Conexion conexion;
    private ResultSet resultSet;
    private String sentenciaSql;

    /**
     * Constructor en donde instanciamos conexion
     */
    public ProcesoProduccionDAO() {
        conexion = new Conexion();

    }

    /**
     * Método para Listar todos los subprocesos
     */
    public List<SubProceso> getsubProcesosProduccion(int id) {
        List<SubProceso> procesos = new ArrayList<>();
        //llamamos a la conexion
        conexion.conectar();

        sentenciaSql = String.format("select sp.codigo_subproceso, sp.nombre, sp.descripcion from public.detalle_proceso_p d inner join public.subproceso sp\n"
                + "	on d.codigo_subproceso=sp.codigo_subproceso where d.codigo_proceso=" + id + ";");
        try {
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                procesos.add(new SubProceso(resultSet.getInt("codigo_subproceso"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return procesos;
    }

    /**
     * Método para Listar todos los procesos de produccion
     */
    public List<ProcesoProduccion> getProcesosProduccion() {
        List<ProcesoProduccion> procesos = new ArrayList<>();
        //llamamos a la conexion
        conexion.conectar();
        sentenciaSql = String.format("select * from getProcesosProduccion();");
        try {
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                procesos.add(new ProcesoProduccion(resultSet.getInt("codigo_proceso"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion"), resultSet.getString("identificador")));
            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return procesos;
    }

    /**
     * Método para insertar un proceso recibiendo un parámetro, dicha clase
     * implementa try and catch
     *
     * @param proceso objeto ProcesoProduccion
     */
    public void insertarp(ProcesoProduccion proceso) {
        try {
            //llamamos a la conexion
            conexion.conectar();
            sentenciaSql = "INSERT INTO public.proceso_produccion(nombre, descripcion, identificador)\n"
                    + "	VALUES ('" + proceso.getNombre() + "', '" + proceso.getDescripcion() + "', '" + proceso.getIdentificador() + "')";
            //enviamos la sentencia
            conexion.ejecutar(sentenciaSql);
        } catch (Exception e) {
        } finally {
            conexion.desconectar();
        }
    }

//    public int update(ProcesoProduccion proceso) throws SQLException {
//        sentenciaSql = "UPDATE public.proceso_produccion\n"
//                + "	SET nombre='" + proceso.getNombre() + "', descripcion='" + proceso.getDescripcion() + " WHERE codigo_proceso = " + proceso.getCodigo_proceso();
//        return conexion.ejecutar(sentenciaSql);
//    }
    public int actualizarProceso(ProcesoProduccion proceso) {
        try {
            //llamamos a la conexion
            conexion.conectar();
            sentenciaSql = "UPDATE public.proceso_produccion\n"
                    + "	SET nombre='" + proceso.getNombre() + "', descripcion='" + proceso.getDescripcion() + "'\n"
                    + "	WHERE codigo_proceso=" + proceso.getCodigo_proceso() + ";";
            //enviamos la sentencia
            return conexion.ejecutar(sentenciaSql);
        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }
    /**
     * Método para eliminar un proceso recibiendo dos parámetros
     */
    public void delete(ProcesoProduccion proceso, String aux) throws SQLException {
        sentenciaSql = ("DELETE FROM public.proceso_produccion WHERE identificador = '" + aux + "'");
        conexion.ejecutar(sentenciaSql);
        conexion.desconectar();
    }
}
