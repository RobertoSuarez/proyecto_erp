/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contabilidad.dao;

import com.contabilidad.models.ejercicioContable;
import com.global.config.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class ejercicioContableDAO {

    private Conexion conexion = new Conexion();
    private ResultSet resultSet;
    private ejercicioContable ejercicioModel = new ejercicioContable();

    ;

    public ejercicioContable getEjercicioModel() {
        return ejercicioModel;
    }

    public void setEjercicioModel(ejercicioContable ejercicioModel) {
        this.ejercicioModel = ejercicioModel;
    }

    public void insertarEjercicioContable(ejercicioContable ejerCont) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            conexion.conectar();
            String sql = "INSERT INTO public.periodo_fiscal( fecha_inicio, fecha_fin, estado, nombreperiodo)\n"
                    + "	VALUES ('" +ejerCont.getFechaInicio() + "', '" + ejerCont.getFechaFin() + "', true, '" + ejerCont.getNombrePeriodo() + "');";
            conexion.ejecutarSql(sql);

        } catch (Exception e) {
            System.out.println("Insert");
            e.getMessage();
        } finally {
            conexion.desconectar();
        }
    }

    public List<ejercicioContable> listarEjercicioContable() {
        List<ejercicioContable> contables = new ArrayList<>();
        try {
            conexion.conectar();
            String query = "select* from periodo_fiscal;";
            resultSet = conexion.ejecutarSql(query);
            while (resultSet.next()) {
                contables.add(new ejercicioContable(
                        resultSet.getDate("fecha_inicio"),
                        resultSet.getDate("fecha_fin"),
                        resultSet.getBoolean("estado"),
                        resultSet.getString("nombreperiodo")));
            }
        } catch (SQLException e) {
            System.out.println("List");
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return contables;
    }

    public List<ejercicioContable> listarContable() {
        List<ejercicioContable> contables = new ArrayList<>();
        try {
            conexion.conectar();
            String query = "select* from periodo_fiscal;";
            resultSet = conexion.ejecutarSql(query);
            while (resultSet.next()) {
                contables.add(new ejercicioContable(
                        resultSet.getInt("id_periodo_fiscal"),
                        resultSet.getDate("fecha_inicio"),
                        resultSet.getDate("fecha_fin"),
                        resultSet.getBoolean("estado"),
                        resultSet.getString("nombreperiodo")));
            }
        } catch (SQLException e) {
            System.out.println("List");
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return contables;
    }

}
