/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.ProcesoProduccion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class ProcesoProduccionDAO {

    private Conexion conexion;
    private ResultSet resultSet;

    public ProcesoProduccionDAO() {
        conexion = new Conexion();

    }

    public List<ProcesoProduccion> getProcesosProduccion() {
        List<ProcesoProduccion> procesos = new ArrayList<>();
        String sql = String.format("select * from getProcesosProduccion();");
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                procesos.add(new ProcesoProduccion(resultSet.getInt("codigo_proceso"), resultSet.getString("nombre"),
                        resultSet.getString("descripcion"), resultSet.getString("identificador")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return procesos;
    }

    public void insertarp(ProcesoProduccion proceso) {
        try {

            String sql = "INSERT INTO public.proceso_produccion(nombre, descripcion, identificador)\n"
                    + "	VALUES ('" + proceso.getNombre() + "', '" + proceso.getDescripcion() + "', '" + proceso.getIdentificador() + "')";

            conexion.Ejecutar2(sql);

        } catch (Exception e) {
        }
    }

    public void update(ProcesoProduccion proceso) throws SQLException {
        try {
            this.conexion.Conectar();

            String sql = "UPDATE public.proceso_produccion\n"
                    + "	SET nombre='" + proceso.getNombre() + "', descripcion='" + proceso.getDescripcion() + "', identificador='" + proceso.getIdentificador() + "'\n"
                    + "	WHERE identificador = '" + proceso.getIdentificador()+ "'";

            conexion.ejecutar(sql);
            conexion.cerrarConexion();

        } catch (SQLException e) {
            throw e;
        } finally {
            this.conexion.cerrarConexion();
        }
    }

    public void delete(ProcesoProduccion proceso, String aux) throws SQLException{
        
        try {
            this.conexion.Conectar();
            String sql = ("DELETE FROM public.proceso_produccion WHERE identificador = '" + aux + "'");
            
            conexion.ejecutar(sql);
            conexion.cerrarConexion();
            
        } catch (Exception e) {
            throw e;
        }finally{
            this.conexion.cerrarConexion();
        }
    }
}
