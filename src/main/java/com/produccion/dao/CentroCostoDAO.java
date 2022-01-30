/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public CentroCostoDAO() {
        conexion = new Conexion();
    }

    public List<CentroCosto> getCentroCosots() {
        List<CentroCosto> centro = new ArrayList<>();
        String sql = String.format("SELECT * FROM getcentrocostos();");
        try {
            conexion.conectar();
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

    public void insertarc(CentroCosto centro) {
        try {
            this.conexion.Conectar();

            String sql = "INSERT INTO public.centro_costo(nombre, descripcion, identificador)\n"
                    + "	VALUES ('" + centro.getNombre() + "', '" + centro.getDescripcion() + "', '" + centro.getIdentificador() + "')";

            

            conexion.Ejecutar2(sql);
            conexion.cerrarConexion();

        } catch (Exception e) {
        } finally {
            conexion.cerrarConexion();
        }
    }

    public void updatec(CentroCosto centro) throws SQLException {
        try {
            this.conexion.Conectar();

            String sql = "UPDATE public.centro_costo\n"
                    + "	SET nombre='" + centro.getNombre() + "', descripcion='" + centro.getDescripcion() + "', identificador='" + centro.getIdentificador() + "'\n"
                    + "	WHERE identificador = '" + centro.getIdentificador() + "'";

            conexion.ejecutar(sql);
            conexion.cerrarConexion();

        } catch (SQLException e) {
            throw e;
        } finally {
            this.conexion.cerrarConexion();
        }
    }

    public void deletec(CentroCosto centro, String aux) throws SQLException {

        try {
            this.conexion.Conectar();
            String sql = ("DELETE FROM public.centro_costo WHERE identificador = '" + aux + "'");

            conexion.ejecutar(sql);
            conexion.cerrarConexion();

        } catch (Exception e) {
            throw e;
        } finally {
            this.conexion.cerrarConexion();
        }
    }
}
