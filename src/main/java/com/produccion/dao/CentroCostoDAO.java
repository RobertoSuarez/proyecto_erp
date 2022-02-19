
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.CentroCosto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CentroCostoDAO {

    private Conexion conexion;
    private ResultSet resultSet;

    public CentroCostoDAO() {
        conexion = new Conexion();
    }

    public List<CentroCosto> getCentroCosots() {
        List<CentroCosto> centro = new ArrayList<>();
        String sql = String.format("SELECT * FROM centro_costo;");
        try {
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                centro.add(new CentroCosto(resultSet.getInt("codigo_centroc"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion"), resultSet.getString("identificador")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return centro;
    }

    public void insertarc(CentroCosto centro) {
        try {

            String sql = "INSERT INTO public.centro_costo(nombre, descripcion, identificador)\n"
                    + "	VALUES ('" + centro.getNombre() + "', '" + centro.getDescripcion() + "', '" + centro.getIdentificador() + "')";
            //enviamos la sentencia
            conexion.ejecutarSql(sql);
        } catch (Exception e) {
        } finally {
            conexion.desconectar();
        }
    }

    public void updatec(CentroCosto centro) throws SQLException {
        try {

            String sql = "UPDATE public.centro_costo\n"
                    + "	SET nombre='" + centro.getNombre() + "', descripcion='" + centro.getDescripcion() + "', identificador='" + centro.getIdentificador() + "'\n"
                    + "	WHERE identificador = '" + centro.getIdentificador() + "'";
            //enviamos la sentencia
            conexion.ejecutarSql(sql);

        } catch (Exception e) {
            throw e;
        } finally {
            conexion.desconectar();
        }
    }

    public void deletec(CentroCosto centro, String aux) throws SQLException {

        try {
            String sql = ("DELETE FROM public.centro_costo WHERE identificador = '" + aux + "'");
            //enviamos la sentencia
            conexion.ejecutarSql(sql);

        } catch (Exception e) {
            throw e;
        } finally {
            conexion.desconectar();
        }
    }
}
