package com.contabilidad.dao;

import java.util.List;
import com.contabilidad.models.Grupo;
import com.global.config.Conexion;
import com.google.gson.Gson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GrupoDAO {

    private Conexion conexion;
    private List<Grupo> listaGrupo;
    private ResultSet result;
    private Gson gson;

    public GrupoDAO() {
        conexion = new Conexion();
        listaGrupo = new ArrayList<>();
        gson = new Gson();
    }

    public List<Grupo> getGrupoCuenta() {
        listaGrupo = new ArrayList<>();
        result = conexion.consultar("select getgrupocuenta()");
        try {
            while (result.next()) {
                //System.out.println(result.getString("getgrupocuenta"));
                String cadenaJSON = result.getString("getgrupocuenta");
                Grupo g = gson.fromJson(cadenaJSON, Grupo.class);
                listaGrupo.add(g);
            }
            
            return listaGrupo;
        } catch (SQLException ex) {
            System.out.println("Error getgrupocuenta: " + ex.getMessage());
            return null;
        } finally {
            conexion.desconectar();
        }
    }
    
    public Grupo getGrupoById(int id) {
        Grupo g = null;
        result = conexion.consultar("select getgrupocuentabyid("+id+")");
        try {
            if (result.next()) {
                String cadenaJSON = result.getString("getgrupocuentabyid");
                g = gson.fromJson(cadenaJSON, Grupo.class);
            }
        } catch (SQLException ex) {
            System.out.println("Error getgrupocuenta: " + ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return g;
    }

    public int getUltimoCodigo() {
        result = conexion.consultar("select getultimocodigogrupo()");
        try {
            if (result.next()) {
                return result.getInt("getultimocodigogrupo");
            }
        } catch (SQLException ex) {
            System.out.println("Error getgrupocuenta: " + ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return -1;
    }

    public boolean insert(Grupo grupo) {
        try {
            String sql = String.format("select insertgrupo('%1$s', '%2$s')",
                    grupo.getCodigo(), grupo.getNombre());
            result = conexion.ejecutarSql(sql);
            result.next();
            return true;
        } catch (SQLException e) {
            System.out.println("Error insertar Grupo" + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return false;
    }

    public boolean update(Grupo grupo) {
        try {
            String obj = gson.toJson(grupo);
            String sql = String.format("select updategrupocuenta('%1$s')", obj);
            result = conexion.ejecutarSql(sql);
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error update Grupo" + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return false;
    }
    
    public boolean delete(int id) {
        String sql = String.format("delete from grupocuenta where idgrupo = '%1$d'", id);
        return conexion.eliminar(sql) != -1;
    }
    
    public boolean isReference(int id) {
        result = conexion.consultar("select is_reference('"+id+"')");
        try {
            if (result.next()) {
                System.out.println("result:" + result.getBoolean("is_reference"));
                return result.getBoolean("is_reference");
            }
        } catch (SQLException ex) {
            System.out.println("Error is_reference: " + ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return false;
    }
}
