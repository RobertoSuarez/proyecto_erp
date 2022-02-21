/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.DetalleTransferencia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransferenciasDAO {

    Conexion conexion;
    private ResultSet resultSet;
    private String sentenciaSql;

    public TransferenciasDAO() {
        conexion = new Conexion();
    }

    public List<DetalleTransferencia> getArticulos() {
        List<DetalleTransferencia> listarticulos = new ArrayList<>();
        sentenciaSql = String.format("select id, nombre, cantidad,costo\n"
                + "from articulos");
        try {
            //llamamos a la conexion
            conexion.conectar();
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sentenciaSql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                listarticulos.add(new DetalleTransferencia(resultSet.getInt("id"),
                        resultSet.getInt("cantidad"), resultSet.getDouble("costo"), resultSet.getString("nombre")));

            }
        } catch (SQLException e) {
        } finally {
            conexion.desconectar();
        }
        return listarticulos;
    }

}
