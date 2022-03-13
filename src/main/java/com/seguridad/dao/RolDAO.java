package com.seguridad.dao;

import com.global.config.Conexion;
import com.seguridad.models.Rol;
import com.seguridad.dao.ModuleDAO;
import com.seguridad.models.Modulo;
import com.seguridad.models.Permisos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

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
            this.conexion.conectar();
            rs = conexion.ejecutarSql(query);
            while (rs.next()) {
                rolAux = new Rol();
                rolAux.setId(rs.getInt("idRol"));
                rolAux.setNombre(rs.getString("nombreRol"));
                rolAux.setDetalle(rs.getString("detalleRol"));
                rolAux.setHabilitado(rs.getBoolean("enable"));
                roles.add(rolAux);
            }

            rs.close();

            conexion.desconectar();

            return roles;
        } catch (SQLException e) {
            e.toString();
        } finally {
            conexion.desconectar();
        }
        return null;
    }

    public List<Rol> GetRolsDifferentOfUser(int idUsuario) {
        List<Rol> roles = new ArrayList<>();
        Rol rolAux;
        String query = "select r.* \n"
                + "from public.\"rolUsuario\" ru inner join rol r on ru.\"idRol\" = r.\"idRol\" \n"
                + "where ru.\"idUsuario\" <> "+ String.valueOf(idUsuario) +"\n"
                + "group by r.\"idRol\"";
        ResultSet rs;
        try {
            this.conexion.conectar();
            rs = conexion.ejecutarSql(query);
            while (rs.next()) {
                rolAux = new Rol();
                rolAux.setId(rs.getInt("idRol"));
                rolAux.setNombre(rs.getString("nombreRol"));
                rolAux.setDetalle(rs.getString("detalleRol"));
                roles.add(rolAux);
            }
            rs.close();
            this.conexion.desconectar();
        } catch (SQLException e) {
            e.toString();
        } finally {
            conexion.desconectar();
        }
        return roles;
    }
    
    public void addRolUser(int idRol, int idUser){
        String query = "INSERT INTO public.\"rolUsuario\"(\n" +
"	 \"idRolUsuario\", \"idUsuario\", \"idRol\")\n" +
"	VALUES ((Select \"idRolUsuario\"\n" +
"	from public.\"rolUsuario\"\n" +
"	order by \"idRolUsuario\" desc\n" +
"	Limit 1)+1,"+ String.valueOf(idUser) +", "+ String.valueOf(idRol) +");";
        try{
            System.out.println("Entro al metodo");
            this.conexion.conectar();
            this.conexion.ejecutarSql(query);
            this.conexion.desconectar();
        }
        catch(Exception e){
            e.toString();
        }
    }
    
    public void deleteRolUser(int idRol, int idUser){
        String query = "DELETE FROM public.\"rolUsuario\"\n" +
"	WHERE \"idUsuario\"= "+String.valueOf(idUser) +" and \"idRol\" = "+ String.valueOf(idRol) +";";
        try{
            this.conexion.conectar();
            this.conexion.ejecutarSql(query);
            this.conexion.desconectar();
        }
        catch(Exception e){
            e.toString();
        }
    }
    
    public List<Rol> GetRols() {
        List<Rol> roles = new ArrayList<>();
        Rol rolAux;
        String query = "select * \n"
                + "from  security.rol";
        ResultSet rs;
        try {
            this.conexion.conectar();
            rs = conexion.ejecutarSql(query);
            while (rs.next()) {
                rolAux = new Rol();
                rolAux.setId(rs.getInt("idRol"));
                rolAux.setNombre(rs.getString("nombreRol"));
                rolAux.setDetalle(rs.getString("descripcion"));
                roles.add(rolAux);
            }
            rs.close();
            this.conexion.desconectar();
        } catch (SQLException e) {
            e.toString();
        } finally {
            conexion.desconectar();
        }
        return roles;
    }
    
    public List<SelectItem> GetRolsSelected(int[] lstCodigos) {
        List<SelectItem> roles = new ArrayList<>();
        SelectItem rolAux;
        ModuleDAO modDao = new ModuleDAO();
        try {
            if(lstCodigos.length>0){
                for(int i=0;i<lstCodigos.length;i++){
                    rolAux = new SelectItem();
                    rolAux.setValue(lstCodigos[i]);
                    rolAux.setLabel(modDao.invokeModuleName(lstCodigos[i]));
                    roles.add(rolAux);
                }
            }
        } catch (Exception e) {
            e.toString();
        } finally {
            conexion.desconectar();
        }
        return roles;
    }
    
    public List<Permisos> GetPermissionsRoles(int idRol, int idModule) {
        List<Permisos> permisos = new ArrayList<>();
        Permisos permisAux;
        String query = "SELECT * from security.obtener_vistas("+String.valueOf(idRol)+", "+String.valueOf(idModule)+")";
        ResultSet rs;
        try {
            this.conexion.conectar();
            rs = conexion.ejecutarSql(query);
            while (rs.next()) {
                permisAux = new Permisos();
                permisAux.setId_vista(rs.getInt(1));
                permisAux.setNameViews(rs.getString(2));
                permisAux.setCan_erased(rs.getBoolean(3));
                permisAux.setCan_insert(rs.getBoolean(4));
                permisAux.setCan_edit(rs.getBoolean(5));
                permisAux.setCan_views(rs.getBoolean(6));
                permisos.add(permisAux);
            }
            rs.close();
            this.conexion.desconectar();
        } catch (SQLException e) {
            e.toString();
        } finally {
            conexion.desconectar();
        }
        return permisos;
    }
}
