package com.contabilidad.dao;

import com.contabilidad.models.CuentaContable;
import com.contabilidad.models.Grupo;
import com.contabilidad.models.Cuenta;
import com.contabilidad.models.SubCuenta;
import com.contabilidad.models.SubGrupo;
import com.global.config.Conexion;
import com.google.gson.Gson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LUIS ALFREDO
 */
public class PlanContableDAO {

    private Conexion conexion = new Conexion();
    private List<CuentaContable> cuentasContables;
    private List<Grupo> grupos;
    private List<SubGrupo> subgrupos;
    private List<Cuenta> cuentas;
    private Gson gson;
    private ResultSet result;

    public PlanContableDAO() {
        gson = new Gson();
    }

//    public List<CuentaContable> getSubCuentas() {
//        cuentasContables = new ArrayList<>();
//        result = conexion.ejecutarSql("select * from public.getsubcuentas()");
//        try {
//            //(String codigo, String grupo, String subgrupo, String cuenta, String subcuenta)
//            while (result.next()) {
//                cuentasContables.add(new CuentaContable(
//                        result.getString("codigo"),
//                        result.getString("grupo"),
//                        result.getString("subgrupo"),
//                        result.getString("cuenta"),
//                        result.getString("nombre")
//                ));
//            }
//        } catch (SQLException ex) {
//            System.out.println("Hubo un problema en getAllClientes: " + ex.getMessage());
//        } finally {
//            conexion.desconectar();
//        }
//        return cuentasContables;
//    }
    public List<CuentaContable> getSubCuentas() {
        cuentasContables = new ArrayList<>();
        try {
            conexion.conectar();
            result = conexion.ejecutarSql("select getsubcuentas()");
            //(String codigo, String grupo, String subgrupo, String cuenta, String subcuenta)
            while (result.next()) {
                String cadenaJSON = result.getString("getsubcuentas");
                CuentaContable cuentaContable = gson.fromJson(cadenaJSON, CuentaContable.class);
                cuentasContables.add(cuentaContable);
            }
        } catch (SQLException ex) {
            System.out.println("Hubo un problema en getAllClientes: " + ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return cuentasContables;
    }

    public List<Grupo> getGrupos() {
        grupos = new ArrayList<>();

        try {
            conexion.conectar();
            result = conexion.ejecutarSql("select getgrupocuenta();");
            //(String codigo, String grupo, String subgrupo, String cuenta, String subcuenta)
            while (result.next()) {
                //System.out.println(result.getString("getgrupocuenta"));
                String cadenaJSON = result.getString("getgrupocuenta");
                Grupo g = gson.fromJson(cadenaJSON, Grupo.class);
                grupos.add(g);
            }
        } catch (SQLException ex) {
            System.out.println("Hubo un problema en getAllClientes: " + ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return grupos;
    }

    public List<SubGrupo> getSubGrupos(String codigo) {
        subgrupos = new ArrayList<>();
        try {
            conexion.conectar();
            result = conexion.ejecutarSql("select * from public.subgrupo where idgrupo = " + codigo + "");
            while (result.next()) {
                subgrupos.add(new SubGrupo(
                        result.getInt("idsubgrupo"),
                        result.getInt("idgrupo"),
                        result.getString("codigo"),
                        result.getString("nombre")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Hubo un problema en getAllClientes: " + ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return subgrupos;
    }

    public List<Cuenta> getCuentas(String codigo) {
        cuentas = new ArrayList<>();
        try {
            conexion.conectar();
            result = conexion.ejecutarSql("select * from public.cuenta where idsubgrupo = " + codigo + "");
            // int id, int subgrupo, String codigo, String nombre
            while (result.next()) {
                cuentas.add(new Cuenta(
                        result.getInt("idcuenta"),
                        result.getInt("idsubgrupo"),
                        result.getString("codigo"),
                        result.getString("nombre")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Hubo un problema en getAllClientes: " + ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return cuentas;
    }

    public int getCountSubCuentas(String codigo) {
        int count = -1;
        System.out.println("####################################" + codigo);
        try {
            conexion.conectar();
            result = conexion.ejecutarSql("select count(*) from public.subcuenta where idcuenta = " + codigo + "");
            while (result.next()) {
                count = result.getInt("count");
            }
        } catch (SQLException ex) {
            System.out.println("Hubo un problema en getAllClientes: " + ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return count;
    }

    public int insertSubCuenta(SubCuenta subcuenta) {
        try {
            conexion.conectar();
            String sql = String.format("select insertsubcuenta('%1$d', '%2$s', '%3$s', '%4$s')",
                    subcuenta.getCuenta(), subcuenta.getCodigo(), subcuenta.getNombre(), subcuenta.getTipo());
            conexion.ejecutarSql(sql);
            return 1;
        } catch (Exception e) {
            System.out.println("Error al isert subcuenta: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return -1;
    }

    public int insertGrupo(Grupo grupo) {
        try {
            conexion.conectar();
            String sql = String.format("select insertgrupo('%1$s', '%2$s')",
                    grupo.getCodigo(), grupo.getNombre());
            result = conexion.ejecutarSql(sql);
            result.next();
            return 1;
        } catch (SQLException e) {
            System.out.println("Error insertar Grupo" + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return -1;
    }

    public int insertSubGrupo(SubGrupo subGrupo) {
        try {
            conexion.conectar();
            String sql = String.format("select insertsubgrupo('%1$d', '%2$s', '%3$s')",
                    subGrupo.getGrupo(), subGrupo.getCodigo(), subGrupo.getNombre());
            result = conexion.ejecutarSql(sql);
            result.next();
            return 1;
        } catch (SQLException e) {
            System.out.println("Error insertar Subgrupo: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return -1;
    }

    public int insertCuenta(Cuenta cuenta) {
        try {
            conexion.conectar();
            String sql = String.format("select insertcuenta('%1$d', '%2$s', '%3$s')",
                    cuenta.getIdsubgrupo(), cuenta.getCodigo(), cuenta.getNombre());
            result = conexion.ejecutarSql(sql);
            result.next();
            return 1;
        } catch (SQLException e) {
            System.out.println("Error insertar Subgrupo: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return -1;
    }

    public boolean updateSubCuenta(SubCuenta subCuenta) {
        try {
            conexion.conectar();
            String obj = gson.toJson(subCuenta);
            String sql = String.format("select updatesubcuenta('%1$s')", obj);
            result = conexion.ejecutarSql(sql);
            return result.next();
        } catch (SQLException e) {
            System.out.println("Error update SubGrupo" + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return false;
    }

    public boolean isReferenceSubCuenta(int id) {
        try {
            conexion.conectar();
            result = conexion.ejecutarSql("select is_reference_subcuenta('" + id + "')");
            if (result.next()) {
                return result.getBoolean("is_reference_subcuenta");
            }
        } catch (SQLException ex) {
            System.out.println("Error is_reference_subcuenta: " + ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return false;
    }
}
