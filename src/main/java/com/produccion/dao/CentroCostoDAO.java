
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.CentroCosto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class CentroCostoDAO {

    private Conexion conexion;
    private ResultSet resultSet;
    /**
     * Constructor en donde instanciamos conexion
     */
    public CentroCostoDAO() {
        conexion = new Conexion();
    }
    /**
      * Método para Listar la lista todos los centros de costos
      */
    public List<CentroCosto> getCentroCosots() {
        List<CentroCosto> centro = new ArrayList<>();
        String sql = String.format("SELECT * FROM centro_costo;");
        try {
            //llamamos a la conexion
            conexion.conectar();
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                centro.add(new CentroCosto(resultSet.getInt("codigo_centroc"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion"), resultSet.getString("identificador")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return centro;
    }
    /**
      * Método para insertar un centro de costo recibiendo un parámetro, dicha clase implementa try and
      * catch
      *
      * @param centro objeto CentroCosto
      */
    public void insertarc(CentroCosto centro) {
        try {
            //llamamos a la conexion
            this.conexion.Conectar();

            String sql = "INSERT INTO public.centro_costo(nombre, descripcion, identificador)\n"
                    + "	VALUES ('" + centro.getNombre() + "', '" + centro.getDescripcion() + "', '" + centro.getIdentificador() + "')";
            //enviamos la sentencia
            conexion.Ejecutar2(sql);
            conexion.cerrarConexion();

        } catch (Exception e) {
        } finally {
            conexion.cerrarConexion();
        }
    }
    /**
      * Método para actualizar un Centro de costo recibiendo un parametro 
      * Dicho metodo implementa throws
      *
      * @param centro obejto CentroCosto
      * @throws SQLException validador
      */
    public void updatec(CentroCosto centro) throws SQLException {
        try {
            //llamamos a la conexion
            this.conexion.Conectar();

            String sql = "UPDATE public.centro_costo\n"
                    + "	SET nombre='" + centro.getNombre() + "', descripcion='" + centro.getDescripcion() + "', identificador='" + centro.getIdentificador() + "'\n"
                    + "	WHERE identificador = '" + centro.getIdentificador() + "'";
            //enviamos la sentencia
            conexion.ejecutar(sql);
            conexion.cerrarConexion();

        } catch (SQLException e) {
            throw e;
        } finally {
            this.conexion.cerrarConexion();
        }
    }
    /**
      * Método para actualizar un Centro de costo recibiendo dos parametro 
      * Dicho metodo implementa throws
      *
      * @param centro obejto CentroCosto
      * @param aux variable tipo String
      * @throws SQLException validador
      */
    public void deletec(CentroCosto centro, String aux) throws SQLException {

        try {
            //llamamos a la conexion
            this.conexion.Conectar();
            String sql = ("DELETE FROM public.centro_costo WHERE identificador = '" + aux + "'");
            //enviamos la sentencia
            conexion.ejecutar(sql);
            conexion.cerrarConexion();

        } catch (Exception e) {
            throw e;
        } finally {
            this.conexion.cerrarConexion();
        }
    }
}
