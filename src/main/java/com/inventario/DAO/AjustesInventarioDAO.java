/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.DetalleAjuste;
import com.inventario.models.DetalleTransferencia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AjustesInventarioDAO {

    Conexion conexion;
    private ResultSet resultSet;
    private String sentenciaSql;

    public List<DetalleAjuste> getArticulos() {
        List<DetalleAjuste> listarticulos = new ArrayList<>();
        sentenciaSql = String.format("select id, nombre, cantidad,costo\n"
                + "from articulos");
        try {
            //llamamos a la conexion
            conexion.conectar();
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                listarticulos.add(new DetalleAjuste(resultSet.getInt("codDetalle"),
                        resultSet.getInt("codAjuste"), resultSet.getInt("codArticulo"), resultSet.getString("nomArticulo"),
                        resultSet.getInt("cantActual"), resultSet.getInt("idTipo"), resultSet.getString("tipo"), resultSet.getInt("cantAjustada"), resultSet.getDouble("costo"), resultSet.getInt("Cantfinal")));

            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return listarticulos;
    }

}
