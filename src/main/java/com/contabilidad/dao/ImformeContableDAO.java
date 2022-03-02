package com.contabilidad.dao;

import com.contabilidad.models.Libro;
import com.global.config.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ImformeContableDAO {

    private Conexion conexion = new Conexion();
    private ResultSet resultSet;

    public List<Libro> getImformeLibroMayor() {
        String sql = String.format("select * from generateLibroMayor();");
        List<Libro> libros = new ArrayList<>();
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                libros.add(new Libro(resultSet.getString("Codigo"), resultSet.getString("SubCuenta"), resultSet.getString("Fecha"),
                        resultSet.getString("Asiento"), resultSet.getString("Descripcion"), resultSet.getDouble("Debe"), resultSet.getDouble("Haber")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{ 
            conexion.desconectar();
        }
        return libros;
    }
    
    //Testeando nueva función
    public List<Libro> getImformeLibroMayorV2() {
        String sql = String.format("select * from generateLibroMayorV2();");
        List<Libro> libros = new ArrayList<>();
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                libros.add(new Libro(resultSet.getString("Codigo"), resultSet.getString("SubCuenta"), resultSet.getString("Fecha"),
                        resultSet.getString("Asiento"), resultSet.getString("Descripcion"), resultSet.getDouble("Debe"), resultSet.getDouble("Haber")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{ 
            conexion.desconectar();
        }
        return libros;
    }

    public List<Libro> filtrateLibroByDiario(int idiario) {
        String sql = String.format("select * from fillLibroMayor(%1$d);", idiario);
        List<Libro> libros = new ArrayList<>();
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                libros.add(new Libro(resultSet.getString("Codigo"), resultSet.getString("SubCuenta"), resultSet.getString("Fecha"),
                        resultSet.getString("Asiento"), resultSet.getString("Descripcion"), resultSet.getDouble("Debe"), resultSet.getDouble("Haber")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            conexion.desconectar();
        }
        return libros;
    }
}
