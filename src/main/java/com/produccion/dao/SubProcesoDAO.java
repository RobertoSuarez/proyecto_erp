
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.Costo;
import com.produccion.models.ProcesoProduccion;
import com.produccion.models.SubProceso;
import com.produccion.models.dSubproceso;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubProcesoDAO {

    private Conexion conexion;
    private ResultSet resultSet;

    /**
     * Constructor en donde instanciamos conexion
     */
    public SubProcesoDAO() {
        conexion = new Conexion();
    }

    /**
     * Método para Listar la lista todos los subprocesos
     */
    public List<ProcesoProduccion> getProcesosProduccion() {
        List<ProcesoProduccion> procesos = new ArrayList<>();
        String sentenciaSql = String.format("select * from getProcesosProduccion();");
        try {
            //llamamos a la conexion
            conexion.conectar();
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
     * Método para insertar un centro de costo recibiendo un parámetro, dicha
     * clase implementa try and catch
     *
     * @param proceso objeto SubProceso
     */
    public int insertardSubproceso(SubProceso proceso) {
        try {
            //llamamos a la conexion
            this.conexion.Conectar();
            String sentenciaSql = "INSERT INTO public.detalle_proceso_p(\n"
                    + "	codigo_proceso, codigo_subproceso, horas, cantidad_estimada)\n"
                    + "	VALUES (" + proceso.getId_codigo_proceso() + "," + proceso.getCodigo_subproceso()
                    + ",'" + proceso.getHora() + "'," + proceso.getRendimiento() + ");";
            //enviamos la sentencia
            return conexion.insertar(sentenciaSql);
        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }
    /**
     * Método para insertar un centro de costo recibiendo un parámetro, dicha
     * clase implementa try and catch
     *
     * @param subproceso objeto SubProceso
     */
    public int insertarDetalleSubproceso(dSubproceso subproceso) {
        try {
            //llamamos a la conexion
            this.conexion.Conectar();
            String sentenciaSql = "INSERT INTO public.detalle_subproceso(\n"
                    + "	codigo_subproceso, codigo_costos, costo_mano_obra, costo_indirecto, hora_costo)\n"
                    + "	VALUES (" + subproceso.getCodigo_subproceso() + ", " + subproceso.getCodigo_costos() + ", " + subproceso.getCosto_mano_obra() + ", " + subproceso.getCosto_indirecto() + ", "
                    + subproceso.getHora_costo() + ");";
            //enviamos la sentencia
            return conexion.insertar(sentenciaSql);
        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }
    /**
     * Método para insertar un subproceso recibiendo un parámetro, dicha
     * clase implementa try and catch
     *
     * @param proceso objeto SubProceso
     */
    public int insertarSubproceso(SubProceso proceso) {
        try {
            //llamamos a la conexion
            this.conexion.Conectar();
            String sentenciaSql = "INSERT INTO public.subproceso(\n"
                    + "	codigo_subproceso, nombre, descripcion)\n"
                    + "	VALUES (" + proceso.getCodigo_subproceso() + ",'" 
                    + proceso.getNombre() + "', '" + proceso.getDescripcion() + "');";
            //enviamos la sentencia
            return conexion.insertar(sentenciaSql);
        } catch (Exception e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }
    /**
     * Método para insertar un subproceso recibiendo un parámetro
     */
    public int idSubproceso() {
        try {
            //llamamos a la conexion
            this.conexion.Conectar();
            int id = -1;
            String sentenciaSql = "select max(codigo_subproceso)+1 as id from public.subproceso;";
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            return id;
        } catch (SQLException e) {
            return -1;
        } finally {
            conexion.desconectar();
        }
    }
}
