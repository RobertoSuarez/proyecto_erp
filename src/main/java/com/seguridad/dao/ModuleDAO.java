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
        String query = "SELECT * FROM security.\"Modulo\" \n"
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

    public void insertModule(Modulo mod) {
        String query = "INSERT INTO security.\"Modulo\"(\n"
                + "	\"idModulo\", \"nombreModulo\", descripcion, habilitado)\n"
                + "	VALUES ((Select CASE WHEN MAX(\"idModulo\") IS NULL then 0 else MAX(\"idModulo\") END as idModulo from security.\"Modulo\")+1, '" 
                + String.valueOf(mod.getNameModule()) + "', '"
                + String.valueOf(mod.getDescriptionModule()) + "','true');";
        try {
            this.conexion.conectar();
            this.conexion.ejecutarSql(query);
        } catch (Exception e) {
            e.toString();
        } finally {
            this.conexion.desconectar();
        }
    }

    public void editModule(Modulo module) {
        String query = "UPDATE public.modulo\n"
                + "	SET \"nombreModulo\"='" + String.valueOf(module.getNameModule()) + "'"
                + ", descripcion='" + String.valueOf(module.getDescriptionModule()) + "'"
                + "	WHERE \"idModulo\"= " + String.valueOf(module.getIdModule()) + ";";
        try {
            this.conexion.conectar();
            this.conexion.ejecutarSql(query);
        } catch (Exception e) {
            e.toString();
        } finally {
            this.conexion.desconectar();
        }
    }
    
    public void deleteModule(Modulo module) {
        String query = "DELETE FROM public.modulo\n" +
"	WHERE \"idModulo\"= "+String.valueOf(module.getIdModule())+";";
        try {
            this.conexion.conectar();
            this.conexion.ejecutarSql(query);
        } catch (Exception e) {
            e.toString();
        } finally {
            this.conexion.desconectar();
        }
    }
}
