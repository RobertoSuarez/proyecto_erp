package com.seguridad.dao;

import com.global.config.Conexion;
import com.seguridad.models.Rol;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {
    Conexion conexion;

    public RolDAO() {
        this.conexion = new Conexion();
    }

    public List<Rol> getRolesByUsers(int idUsuario) {
        List<Rol> roles = new ArrayList<>();
        Rol rolAux;
        String query = "select r.* from public.\"rolUsuario\" ru inner join public.rol r on ru.\"idRol\" = r.\"idRol\" where ru.\"idUsuario\" = " + String.valueOf(idUsuario) + ";";
        ResultSet rs;
        try {
            this.conexion.Conectar();

            rs = conexion.consultar(query);
            
            while(rs.next()){
                rolAux = new Rol();
                rolAux.setId(rs.getInt("idRol"));
                rolAux.setNombre(rs.getString("nombreRol"));
                rolAux.setDetalle(rs.getString("detalleRol"));
                rolAux.setHabilitado(rs.getBoolean("enable"));
                roles.add(rolAux);
            }
            
            rs.close();
            
            conexion.cerrarConexion();
            
            return roles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
