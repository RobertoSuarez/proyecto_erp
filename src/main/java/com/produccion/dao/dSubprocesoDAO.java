
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.Costo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class dSubprocesoDAO {
    private Conexion conexion;
    private ResultSet resultSet;
    private String sentenciaSql;
    /**
     * Constructor en donde instanciamos conexion
     */
    public dSubprocesoDAO() {
        conexion = new Conexion();
    }
    /**
     * Método para Listar la lista todos los costos
     */
    public List<Costo> getCosto(String tipo) {
        List<Costo> costo = new ArrayList<>();
        sentenciaSql = String.format("select * from costos where tipo='"+tipo+"';");
        try {
            //llamamos a la conexion
            conexion.conectar();
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                costo.add(new Costo(resultSet.getInt("codigo_costos"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion"), resultSet.getString("tipo"), resultSet.getString("identificador")));
            }
        } catch (SQLException e) {
        }finally {
            conexion.desconectar();
        }
        return costo;
    }
}
