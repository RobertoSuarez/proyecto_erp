/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.Bodega;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jimmy
 */
public class BodegaDAO {

    Conexion conexion = new Conexion();
    private Bodega bodega;
    private ResultSet resultSet;
    private List<Bodega> listaBodegas;
    private List auxlista = new ArrayList<>();

    public BodegaDAO() {
        bodega = new Bodega();
        conexion = new Conexion();
        listaBodegas = new ArrayList<>();
    }

    public BodegaDAO(Bodega b) {
        conexion = new Conexion();
        this.bodega = b;
    }

    public List<Bodega> getBodega() {
        List<Bodega> ListaBodega = new ArrayList<>();
        String sql = String.format("select cod, nombre_bodega,cod_ciudad, c.nombre, direccion, telefono\n"
                + "from bodega b inner join ciudad c on b.cod_ciudad=c.id_ciudad");
        try {
            resultSet = conexion.ejecutarSql(sql);
            //LLenar la lista de datos
            while (resultSet.next()) {
                ListaBodega.add(new Bodega(resultSet.getInt("cod"),
                        resultSet.getString("nombre_bodega"),
                        resultSet.getString("direccion"),
                        resultSet.getString("telefono"),
                        resultSet.getInt("cod_ciudad"),
                        resultSet.getString("nombre")));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ListaBodega;
    }

}
