package com.contabilidad.dao;

import com.contabilidad.models.Cuenta;
import com.contabilidad.models.Grupo;
import com.global.config.Conexion;
import com.google.gson.Gson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO {

    private Conexion conexion;
    private List<Cuenta> listaCuenta;
    private ResultSet result;
    private Gson gson;

    public CuentaDAO() {
        conexion = new Conexion();
        listaCuenta = new ArrayList<>();
        gson = new Gson();
    }

    public List<Cuenta> getCuentas() {
        listaCuenta = new ArrayList<>();
        result = conexion.consultar("select getcuentas()");
        try {
            while (result.next()) {
                //System.out.println(result.getString("getgrupocuenta"));
                String cadenaJSON = result.getString("getcuentas");
                Cuenta g = gson.fromJson(cadenaJSON, Cuenta.class);
                listaCuenta.add(g);
            }
            return listaCuenta;
        } catch (SQLException ex) {
            System.out.println("Error getCuentas: " + ex.getMessage());
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    public Cuenta getCuentaById(int id) {
        Cuenta cuenta = new Cuenta();
        result = conexion.consultar(String.format("select getCuenta('%1$d')", id));
        try {
            if (result.next()) {
                String cadenaJSON = result.getString("getcuenta");
                cuenta = gson.fromJson(cadenaJSON, Cuenta.class);
                cuenta.setCodigo(cuenta.getCodigo().trim());
            }
        } catch (SQLException ex) {
            System.out.println("Error getcuenta: " + ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return cuenta;
    }

    public boolean insert(Cuenta cuenta) {
        try {
            String sql = String.format("select insertcuenta('%1$d', '%2$s', '%3$s')",
                    cuenta.getIdsubgrupo(), cuenta.getCodigo(), cuenta.getNombre());
            result = conexion.ejecutarSql(sql);
            return result.next();
        } catch (SQLException e) {
            System.out.println("Error insertar Cuenta: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return false;
    }

    public boolean update(Cuenta cuenta) {
        try {
            String sql = String.format("select updateCuenta('%1$d','%2$s')", 
                    cuenta.getIdcuenta(), cuenta.getNombre());
            result = conexion.ejecutarSql(sql);
            return result.next();
        } catch (SQLException e) {
            System.out.println("Error update Grupo" + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = String.format("delete from cuenta where idcuenta = '%1$d'", id);
        return conexion.eliminar(sql) != -1;
    }
    
    public boolean isReference(int id) {
        result = conexion.consultar("select is_reference_cuenta('"+id+"')");
        try {
            if (result.next()) {
                return result.getBoolean("is_reference_cuenta");
            }
        } catch (SQLException ex) {
            System.out.println("Error is_reference_cuenta: " + ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return false;
    }
}
