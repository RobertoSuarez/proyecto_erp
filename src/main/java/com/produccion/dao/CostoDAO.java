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

    public CostoDAO() {
        conexion = new Conexion();
    }

    public List<Costo> getCosto(){
        List<Costo> costo = new ArrayList<>();
        String sql = String.format("SELECT * FROM public.costos;");
        try {
            conexion.conectar();
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
    
    public void insertarCosto(Costo costo) {
        try {
            this.conexion.Conectar();

            String sql = "INSERT INTO public.costos(nombre, descripcion, tipo, identificador)\n"
                    + "	VALUES ('" + costo.getNombre() + "', '" + costo.getDescripcion() + "', '" + costo.getTipo()+ "', '" + costo.getIdentificador() + "')";
            
            String sql2 = "Insert into public.costos (nombre, descripcion, tipo, identificador) \n"
                    + "values('" + costo.getNombre() + "', '" + costo.getDescripcion() + "', '" + costo.getTipo()+ "','" + costo.getIdentificador() + "')";

            conexion.Ejecutar2(sql2);
            conexion.cerrarConexion();

        } catch (Exception e) {
        } finally {
            conexion.cerrarConexion();
        }
    }
    
    public void updateCosto(Costo costo) throws SQLException {
        try {
            this.conexion.Conectar();

            String sql = "UPDATE public.costos\n"
                    + "	SET nombre='" + costo.getNombre() + "', descripcion='" + costo.getDescripcion() + "', identificador='" + costo.getIdentificador() + "'\n"
                    + "	WHERE identificador = '" + costo.getIdentificador()+ "'";

            conexion.ejecutar(sql);
            conexion.cerrarConexion();

        } catch (SQLException e) {
            throw e;
        } finally {
            this.conexion.cerrarConexion();
        }
    }
    
    public void deletecosto(Costo costo, String aux) throws SQLException{
        
        try {
            this.conexion.Conectar();
            String sql = ("DELETE FROM public.costos WHERE identificador = '" + aux + "'");
            
            conexion.ejecutar(sql);
            conexion.cerrarConexion();
            
        } catch (Exception e) {
            throw e;
        }finally{
            this.conexion.cerrarConexion();
        }
    }
}
