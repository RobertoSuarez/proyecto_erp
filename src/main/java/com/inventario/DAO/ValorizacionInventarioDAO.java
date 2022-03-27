/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.DAO;

import com.global.config.Conexion;
import com.inventario.models.ValorizacionInventario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angul
 */
public class ValorizacionInventarioDAO {

    Conexion conexion;
    private ValorizacionInventario valorizacionInventario;
    private ResultSet resultSet;
    private List<ValorizacionInventario> listaValorizacionInventario;
    private List auxlista = new ArrayList<>();

    public ValorizacionInventarioDAO() {
        valorizacionInventario = new ValorizacionInventario();
        conexion = new Conexion();
    }

    public ValorizacionInventarioDAO(ValorizacionInventario v) {
        conexion = new Conexion();
        this.valorizacionInventario = v;
    }

    public ValorizacionInventario getValorizacionInventario() {
        return valorizacionInventario;
    }

    public void setValorizacionInventario(ValorizacionInventario valorizacionInventario) {
        this.valorizacionInventario = valorizacionInventario;
    }

    public List<ValorizacionInventario> getValorizacionI(int idbodega) {
        List<ValorizacionInventario> ListaValorizacion = new ArrayList<>();

        String sql = String.format("select t.cod as codigoTipo, t.tipo as nombreTipo, a.id as codigoProducto, a.nombre as nombreProducto, um.unidad_medida as nombreUM,\n"
                + "ab.cant as cantidadProducto, a.costo as costoProducto, (ab.cant * a.costo) as valor\n"
                + "from articulo_bodega ab\n"
                + "inner join bodega b on ab.id_bodega = b.cod\n"
                + "inner join articulos a on a.id = ab.id_articulo\n"
                + "inner join tipo t on a.id_tipo = t.cod\n"
                + "inner join unidades_medidas um on um.id = a.id_unidadmedida\n"
                + "where ab.id_bodega = "+ idbodega +"  \n"
                + "order by t.cod, t.tipo");
        try {
            resultSet = conexion.ejecutarSql(sql);
            while (resultSet.next()) {

                ValorizacionInventario valorizacionInventario = new ValorizacionInventario();

                valorizacionInventario.setCodigoTipo(resultSet.getInt("codigoTipo"));
                valorizacionInventario.setNombreTipo(resultSet.getString("nombreTipo"));
                valorizacionInventario.setCodigoProducto(resultSet.getInt("codigoProducto"));
                valorizacionInventario.setNombreProducto(resultSet.getString("nombreProducto"));
                valorizacionInventario.setNombreUM(resultSet.getString("nombreUM"));
                valorizacionInventario.setCantidadProducto(resultSet.getDouble("cantidadProducto"));
                valorizacionInventario.setCostoProducto(resultSet.getDouble("costoProducto"));
                valorizacionInventario.setValor(resultSet.getDouble("valor"));

                ListaValorizacion.add(valorizacionInventario);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return ListaValorizacion;
    }

}
