/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.dao;

import com.global.config.Conexion;
import com.produccion.models.Costo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class CostoDAO {
    private Conexion conexion;
    private ResultSet resultSet;
    /**
     * Constructor en donde instanciamos conexion
     */
    public CostoDAO() {
        conexion = new Conexion();
    }
    /**
      * Método para Listar la lista todos los costos
      */
    public List<Costo> getCosto(){
        List<Costo> costo = new ArrayList<>();
        String sql = String.format("SELECT * FROM public.costos;");
        try {
            conexion.conectar();
            //enviamos la sentencia
            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                costo.add(new Costo(resultSet.getInt("codigo_costos"), resultSet.getString("nombre"),
                resultSet.getString("descripcion"), resultSet.getString("tipo"), resultSet.getString("identificador")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally{
            conexion.desconectar();
        }
        return costo;
    }
    /**
      * Método para insertar un costo recibiendo un parámetro, dicha clase implementa try and
      * catch
      *
      * @param costo objeto Costo
      */
    public void insertarCosto(Costo costo) {
        try {
            //llamamos a la conexion
            this.conexion.conectar();

            String sql = "INSERT INTO public.costos(nombre, descripcion, tipo, identificador)\n"
                    + "	VALUES ('" + costo.getNombre_subcuenta() + "', '" + costo.getDescripcion_subgrupo() + "', '" + costo.getTipo_cuenta()+ "', '" + costo.getIdentificador_subcuenta() + "')";

            String sql2 = "Insert into public.costos (nombre, descripcion, tipo, identificador) \n"
                    + "values('" + costo.getNombre_subcuenta() + "', '" + costo.getDescripcion_subgrupo() + "', '" + costo.getTipo_cuenta()+ "','" + costo.getIdentificador_subcuenta() + "')";
            //enviamos la sentencia
            conexion.Ejecutar2(sql2);
            conexion.desconectar();

        } catch (Exception e) {
        } finally {
            conexion.desconectar();
        }
    }
    /**
      * Método para actualizar un Centro de costo recibiendo un parametro
      * Dicho metodo implementa throws
      *
      * @param costo obejto Costo
      * @throws SQLException validador
      */

    ///AQUI-----
    public void updateCosto(Costo costo) throws SQLException {
        try {
            //llamamos a la conexion
            this.conexion.conectar();

            String sql = "UPDATE public.costos\n"
                    + "	SET nombre='" + costo.getNombre_subcuenta() + "', descripcion='" + costo.getDescripcion_subgrupo() + "', identificador='" + costo.getIdentificador_subcuenta() + "'\n"
                    + "	WHERE identificador = '" + costo.getIdentificador_subcuenta()+ "'";
            //enviamos la sentencia
            conexion.ejecutarSql(sql);

        } finally {
            this.conexion.desconectar();
        }
    }
    /**
      * Método para actualizar un Centro de costo recibiendo dos parametro
      * Dicho metodo implementa throws
      *
      * @param costo obejto Costo
      * @param aux variable tipo String
      * @throws SQLException validador
      */
    public void deletecosto(Costo costo, String aux) throws SQLException{

        try {
            //llamamos a la conexion
            this.conexion.conectar();
            String sql = ("DELETE FROM public.costos WHERE identificador = '" + aux + "'");
            //enviamos la sentencia
            conexion.ejecutarSql(sql);

        } catch (Exception e) {
            throw e;
        }finally{
            this.conexion.desconectar();
        }
    }
}
