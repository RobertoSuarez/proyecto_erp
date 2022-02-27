package com.contabilidad.dao;

import com.contabilidad.models.Diario;
import com.global.config.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DiarioDAO {

    private Conexion conexion = new Conexion();
    private ResultSet resultSet;

    public List<Diario> getDiariosContables() {
        List<Diario> diarios = new ArrayList<>();
        String sql = String.format("select * from getDiariosContables();");
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sql);
            //Llena la lista de los datos
            while (resultSet.next()) {
                diarios.add(new Diario(resultSet.getInt("iddiario"), resultSet.getString("nombre"),
                        resultSet.getDate("fechaApertura"), resultSet.getDate("fechaCierre"), resultSet.getString("descripcion")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return diarios;
    }

    public Diario getDiarioById(int id) {
        Diario diario = new Diario();
        String sql = String.format("SELECT * FROM public.diariocontable where iddiario = '%1$d'", id);
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sql);
            while (resultSet.next()) {
                diario = new Diario(resultSet.getInt("iddiario"), resultSet.getString("nombre"),
                        resultSet.getDate("fechaApertura"), resultSet.getDate("fechaCierre"), resultSet.getString("descripcion"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return diario;
    }

    public Diario findDiarioByNombre(String nombre) {
        Diario diario = new Diario();
        String sql = String.format("SELECT * FROM public.diariocontable where nombre = '%1$s'", nombre);
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sql);
            while (resultSet.next()) {
                diario = new Diario(resultSet.getInt("iddiario"), resultSet.getString("nombre"),
                        resultSet.getDate("fechaApertura"), resultSet.getDate("fechaCierre"), resultSet.getString("descripcion"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return diario;
    }

    public Diario obtenerIdDiarioByNombre(String nombre) {
        Diario diario = new Diario();
        String sql = String.format("SELECT * FROM public.diariocontable where nombre like '%1%'", nombre);
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sql);
            while (resultSet.next()) {
                diario = new Diario(resultSet.getInt("iddiario"), resultSet.getString("nombre"),
                        resultSet.getDate("fechaApertura"), resultSet.getDate("fechaCierre"), resultSet.getString("descripcion"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return diario;
    }

    public boolean addNewDiario(Diario diario) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String sql = String.format("select addNewDiario('%1$s','%2$s','%3$s','%4$s')", diario.getNombre(),
                dateFormat.format(diario.getFechaApertura()), dateFormat.format(diario.getFechaCierre()), diario.getDescripcion());
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sql);
            resultSet.next();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            conexion.desconectar();
        }
    }

    public boolean updateDiario(Diario diario) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String sql = String.format("select updateDiarioContable('%5$d','%1$s','%2$s','%3$s','%4$s')", diario.getNombre(),
                dateFormat.format(diario.getFechaApertura()), dateFormat.format(diario.getFechaCierre()), diario.getDescripcion(), diario.getIdDiario());
        try {
            conexion.conectar();
            resultSet = conexion.ejecutarSql(sql);
            resultSet.next();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            conexion.desconectar();
        }
    }

    public String deleteDiario(int idDiario) {
        String sql = String.format("delete from diariocontable where iddiario = '%1$d'", idDiario);
        try {
            conexion.conectar();
            if (conexion.eliminar(sql) != -1) {
                return "Eliminacion Exitosa";
            }else{
                return "Error Eliminacion";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        } finally {
            conexion.desconectar();
        }
    }
}
