/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.dao;

import com.global.config.Conexion;
import com.seguridad.models.Vista;
import com.seguridad.dao.ModuleDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Crisito
 */
public class VistaDAO {

    Conexion conexion;
    ModuleDAO moduleDAO;

    public VistaDAO() {
        conexion = new Conexion();
        moduleDAO = new ModuleDAO();
    }

    public List<Vista> GetAllViews() {
        List<Vista> lstViews = new ArrayList<>();
        Vista views;
        String query = "SELECT * FROM security.vista\n"
                + "ORDER BY \"idVista\" ASC ";
        ResultSet rs;
        try {
            this.conexion.conectar();
            rs = this.conexion.ejecutarSql(query);
            while (rs.next()) {
                views = new Vista();
                views.setId_Vista(rs.getInt(1));
                views.setId_Modulo(rs.getInt(2));
                views.setName_Vista(rs.getString(3));
                views.setDescription_Vista(rs.getString(4));
                views.setName_Modulo(this.moduleDAO.invokeModuleName(rs.getInt(2)));
                lstViews.add(views);
            }
            rs.close();
        } catch (SQLException E) {
            E.toString();
        } finally {
            this.conexion.desconectar();
        }
        return lstViews;
    }

    public void insertViews(Vista vst) {
        String query = "INSERT INTO security.vista(\n"
                + "	\"idVista\", \"idModulo\", \"nombreVista\", descripcion)\n"
                + "	VALUES ((Select CASE WHEN MAX(\"idVista\") IS NULL then 0 else MAX(\"idVista\") END as idModulo from security.vista)+1, \n"
                + "	" + String.valueOf(vst.getId_Modulo()) + ", '" + String.valueOf(vst.getName_Vista()) + "'"
                + "     , '" + String.valueOf(vst.getDescription_Vista()) + "');";
        try {
            this.conexion.conectar();
            this.conexion.ejecutarSql(query);
        } catch (Exception e) {
            e.toString();
        } finally {
            this.conexion.desconectar();
        }
    }

    public void updateViews(Vista vst) {
        String query = "UPDATE security.vista\n"
                + "	SET \"nombreVista\"='" + String.valueOf(vst.getName_Vista()) + "', descripcion='" + String.valueOf(vst.getDescription_Vista()) + "'\n"
                + "	WHERE \"idVista\"=" + String.valueOf(vst.getId_Vista()) + ";";
        try {
            this.conexion.conectar();
            this.conexion.ejecutarSql(query);
        } catch (Exception e) {
            e.toString();
        } finally {
            this.conexion.desconectar();
        }
    }

    public void deleteViews(Vista vst) {
        String query = "DELETE FROM security.vista\n"
                + "	WHERE \"idVista\"="+String.valueOf(vst.getId_Vista()) +";";
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
