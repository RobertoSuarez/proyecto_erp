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
import java.text.SimpleDateFormat;
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
            this.conexion.Conectar();
            String sql = "INSERT INTO public.proceso_produccion(\n"
                    + "	nombre, descripcion, identificador\n"
                    + "	VALUES ('" + proceso.getNombre() + "','"+proceso.getDescripcion()+ "'"
                    + ",'" + proceso.getIdentificador()+ "');";
            
            String sql2="INSERT INTO public.proceso_produccion(nombre, descripcion, identificador)\n" +
"	VALUES ('" + proceso.getNombre() + "', '"+proceso.getDescripcion()+ "', '" + proceso.getIdentificador()+ "')";

            conexion.Ejecutar2(sql2);
            conexion.cerrarConexion();

        } catch (Exception e) {
        } finally {
            conexion.cerrarConexion();
        }
    }

    public void update(ProcesoProduccion proceso, int codigo) throws SQLException {
        try {
            this.conexion.Conectar();
            String sql = "UPDATE public.proceso_produccion\n"
            +" SET nombre='"+proceso.getNombre()+"',"
            +" descripcion='"+proceso.getNombre()+"', "
            +" identificador='"+proceso.getNombre()+"', "
            +"Where codigo_proceso= "+codigo+"";
            
            conexion.ejecutar(sql);
            conexion.cerrarConexion();
            
        } catch (SQLException e) {
               throw e;
          } finally {
               this.conexion.cerrarConexion();
          }
    }

}
