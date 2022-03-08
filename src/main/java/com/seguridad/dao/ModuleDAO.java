/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.dao;

import com.global.config.Conexion;
import com.seguridad.models.Modulo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Crisito
 */
public class ModuleDAO {

    Conexion conexion;

    public ModuleDAO() {
        this.conexion = new Conexion();
    }

    public List<Modulo> invokeAllModules() {
        List<Modulo> lstModules = new ArrayList<>();
        Modulo moduleAux;
        String query = "SELECT * FROM public.modulo\n"
                + "ORDER BY \"idModulo\" ASC ";
        ResultSet rs;
        try {
            this.conexion.conectar();
            rs = this.conexion.ejecutarSql(query);
            while (rs.next()) {
                moduleAux = new Modulo();
                moduleAux.setIdModule(rs.getInt(1));
                moduleAux.setNameModule(rs.getString(2));
                moduleAux.setDescriptionModule(rs.getString(3));
                moduleAux.setEnabled(rs.getBoolean(4));
                lstModules.add(moduleAux);
            }
            rs.close();
        } catch (SQLException e) {
            e.toString();
        } finally {
            this.conexion.desconectar();
        }
        return lstModules;
    }

    public List<Modulo> invokeModulesForRol(int idRol) {
        List<Modulo> lstModules = new ArrayList<>();
        Modulo moduleAux;
        String query = "select md.*\n"
                + "from public.rol r inner join public.\"rolModulo\" rm on rm.\"idRol\"=r.\"idRol\" \n"
                + "inner join public.modulo md on rm.\"idModulo\"=md.\"idModulo\"\n"
                + "where r.\"idRol\" = " + String.valueOf(idRol) + "";
        ResultSet rs;
        try {
            this.conexion.conectar();
            rs = this.conexion.ejecutarSql(query);
            while (rs.next()) {
                moduleAux = new Modulo();
                moduleAux.setIdModule(rs.getInt("idModule"));
                moduleAux.setNameModule(rs.getString("nombreModulo"));
                moduleAux.setDescriptionModule(rs.getString("descripcion"));
                moduleAux.setEnabled(rs.getBoolean("habilitado"));
                lstModules.add(moduleAux);
            }
            rs.close();
        } catch (SQLException e) {
            e.toString();
        } finally {
            this.conexion.desconectar();
        }
        return lstModules;
    }

}
