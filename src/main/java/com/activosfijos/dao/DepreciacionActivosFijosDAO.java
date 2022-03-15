/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.activosfijos.dao;

import com.activosfijos.model.DepreciacionActivosFijos;
import com.activosfijos.model.ListaDepreciable;
import com.contabilidad.dao.CuentaDAO;
import com.contabilidad.dao.DiarioDAO;
import com.global.config.Conexion;
import com.recursoshumanos.Model.Interfaces.IDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author cturriagos
 */
public class DepreciacionActivosFijosDAO implements IDAO<DepreciacionActivosFijos> {
    private final Conexion conexion;
    private final CuentaDAO cuentaDAO;
    private DepreciacionActivosFijos depreciacionActivosFijos;
    private int idDiario, idSubCuenta;

    public DepreciacionActivosFijosDAO() {
        DiarioDAO diarioDAO = new DiarioDAO();
        this.cuentaDAO = new CuentaDAO();
        this.conexion = new Conexion();
        this.depreciacionActivosFijos = new DepreciacionActivosFijos();
        this.idDiario = diarioDAO.obtenerDiarioByNombre("DIA-MAF-01").getIdDiario();
        this.idSubCuenta = this.cuentaDAO.ObtenerIdSubCuentaPorNombre("Depreciación Acumulada");
    }

    public DepreciacionActivosFijosDAO(Conexion conexion) {
        DiarioDAO diarioDAO = new DiarioDAO();
        this.cuentaDAO = new CuentaDAO();
        this.conexion = conexion;
        this.depreciacionActivosFijos = new DepreciacionActivosFijos();
        this.idDiario = diarioDAO.obtenerDiarioByNombre("DIA-MAF-01").getIdDiario();
        this.idSubCuenta = this.cuentaDAO.ObtenerIdSubCuentaPorNombre("Depreciación Acumulada");
    }

    public DepreciacionActivosFijosDAO(DepreciacionActivosFijos depreciacionActivosFijos) {
        DiarioDAO diarioDAO = new DiarioDAO();
        this.cuentaDAO = new CuentaDAO();
        this.conexion = new Conexion();
        this.depreciacionActivosFijos = depreciacionActivosFijos;
        this.idDiario = diarioDAO.obtenerDiarioByNombre("DIA-MAF-01").getIdDiario();
        this.idSubCuenta = this.cuentaDAO.ObtenerIdSubCuentaPorNombre("Depreciación Acumulada");
    }

    public DepreciacionActivosFijosDAO(Conexion conexion, DepreciacionActivosFijos depreciacionActivosFijos) {
        DiarioDAO diarioDAO = new DiarioDAO();
        this.cuentaDAO = new CuentaDAO();
        this.conexion = conexion;
        this.depreciacionActivosFijos = depreciacionActivosFijos;
        this.idDiario = diarioDAO.obtenerDiarioByNombre("DIA-MAF-01").getIdDiario();
        this.idSubCuenta = this.cuentaDAO.ObtenerIdSubCuentaPorNombre("Depreciación Acumulada");
    }

    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            return conexion.insertar("depreciacion_activos_fijos",
                    "codigo, mes, depreciacion, saldo, fecha, id_fijo_tangible_depreciable",
                    "'" + depreciacionActivosFijos.getCodigo() + "',"
                    + depreciacionActivosFijos.getMes()+ ","
                    + depreciacionActivosFijos.getDepreciacion() + ","
                    + depreciacionActivosFijos.getSaldo() + ",'"
                    + DateFormatUtils.format(depreciacionActivosFijos.getFecha(), "yyyy-mm-dd") + "',"
                    + depreciacionActivosFijos.getListaDepreciable().getIdDepreciable());
        }
        return -1;
    }

    @Override
    public int insertar(DepreciacionActivosFijos entity) {
        this.depreciacionActivosFijos = entity;
        return insertar();
    }

    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("depreciacion_activos_fijos",
                    "fecha = '" + DateFormatUtils.format(depreciacionActivosFijos.getFecha(), "yyyy-mm-dd") +"'",
                    "codigo = '" + depreciacionActivosFijos.getCodigo() + "'");
        }
        return -1;
    }

    @Override
    public int actualizar(DepreciacionActivosFijos entity) {
        this.depreciacionActivosFijos = entity;
        return actualizar();
    }

    @Override
    public DepreciacionActivosFijos buscarPorId(Object id) {
        List<DepreciacionActivosFijos> lista = buscar("codigo = " + id, null);
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }
    
    public DepreciacionActivosFijos ObtenerSiguienteDepreciacion(ListaDepreciable listaDepreciable){
        DepreciacionActivosFijos depreciacion = new DepreciacionActivosFijos(1,listaDepreciable.getCuota_depresiacion(),listaDepreciable.getValor_adquisicion() - listaDepreciable.getCuota_depresiacion(), new Date(), listaDepreciable);
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("depreciacion_activos_fijos", "mes, depreciacion, saldo",
                        "id_fijo_tangible_depreciable = " + listaDepreciable.getIdDepreciable(), "mes desc LIMIT 1" );
                while (result.next()) {
                    depreciacion = new DepreciacionActivosFijos(result.getInt("mes") + 1, result.getDouble("depreciacion") + listaDepreciable.getCuota_depresiacion(), result.getDouble("saldo") - listaDepreciable.getCuota_depresiacion(), new Date(), listaDepreciable);
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return depreciacion;
    }
    
    @Override
    public List<DepreciacionActivosFijos> Listar() {
        return buscar(null, null);
    }
    
    public List<DepreciacionActivosFijos> Listar(ListaDepreciable listaDepreciable) {
        this.depreciacionActivosFijos.setListaDepreciable(listaDepreciable);
        return buscar("id_fijo_tangible_depreciable = " + listaDepreciable.getIdDepreciable(), "mes, fecha");
    }
    
    public boolean FaltaDepreciacion(int idDepreciable, int meses) {
        int ultimoMes = 0;
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("depreciacion_activos_fijos", "MAX(mes) as ultimoMes",
                        "id_fijo_tangible_depreciable = " + idDepreciable, null );
                while (result.next()) {
                    ultimoMes = result.getInt("ultimoMes");
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return ultimoMes < meses;
    }

    private List<DepreciacionActivosFijos> buscar(@Nullable String restricciones, @Nullable String OrdenarAgrupar) {
        List<DepreciacionActivosFijos> depreciaciones = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("depreciacion_activos_fijos", "codigo, "
                        + "mes, depreciacion, saldo, fecha, id_fijo_tangible_depreciable",
                        restricciones, OrdenarAgrupar);
                TangibleDAO tanDAO = new TangibleDAO();
                while (result.next()) {
                    if(this.depreciacionActivosFijos.getListaDepreciable().getIdDepreciable() == 0){
                        this.depreciacionActivosFijos.setListaDepreciable(tanDAO.ObtenerdepreciablePorId(result.getInt("id_fijo_tangible_depreciable")));
                    }
                    depreciaciones.add(new DepreciacionActivosFijos(
                            result.getString("codigo"),
                            result.getInt("mes"),
                            result.getDouble("depreciacion"),
                            result.getDouble("saldo"),
                            result.getDate("fecha"),
                            this.depreciacionActivosFijos.getListaDepreciable()));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return depreciaciones;
    }
    
    

    public int insertarAsiento(DepreciacionActivosFijos entity) {
        int resultado = 0;
        if (conexion.isEstado()) {
            try {
                String sentencia1, sentencia;
                sentencia = "{\"idDiario\": \"" + this.idDiario + "\",\"total\": " + entity.getListaDepreciable().getCuota_depresiacion()
                            + ",\"documento\": \"" + entity.getCodigo() + "\",\"detalle\": \"Depreciación: " + entity.getListaDepreciable().getSubCuenta().getNombre() + " - "
                            + DateFormatUtils.format(entity.getFecha(), "yyyy-mm-dd") + "\",\"fechaCreacion\": \""
                            + DateFormatUtils.format(entity.getFecha(), "yyyy-mm-dd") + "\",\"fechaCierre\":\""
                            + DateFormatUtils.format(entity.getFecha(), "yyyy-mm-dd") + "\"}";

                sentencia1 = "[{\"idSubcuenta\":\"" +entity.getListaDepreciable().getSubCuenta().getId() + "\",\"debe\":\""
                            + entity.getListaDepreciable().getCuota_depresiacion() + "\",\"haber\":\"0\",\"tipoMovimiento\":\"Depreciación\"},"
                            + "{\"idSubcuenta\":\"" + this.idSubCuenta + "\",\"debe\":\"0\",\"haber\":\""
                            + entity.getListaDepreciable().getCuota_depresiacion() + "\",\"tipoMovimiento\":\"Depreciación\"}]";

                resultado = conexion.ejecutarProcedure("generateasientocotableexternal", "'" + sentencia + "','" + sentencia1 + "'");
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conexion.desconectar();
            }
        }
        return resultado;
    }

    public void deshabilitarAsiento(DepreciacionActivosFijos entity) {
        if (conexion.isEstado()) {
            try {
                String sentencia1, sentencia;
                sentencia = "{\"idDiario\": \"" + this.idDiario + "\",\"total\": " + entity.getListaDepreciable().getCuota_depresiacion()
                        + ",\"documento\": \"" +  entity.getCodigo() + " R\",\"detalle\": \"Depreciación:" + entity.getListaDepreciable().getSubCuenta().getNombre() + " - "
                        + DateFormatUtils.format(entity.getFecha(), "yyyy-mm-dd")  + " R\",\"fechaCreacion\": \""
                        + DateFormatUtils.format(entity.getFecha(), "yyyy-mm-dd")  + "\",\"fechaCierre\":\""
                        + DateFormatUtils.format(entity.getFecha(), "yyyy-mm-dd") + "\"}";

                sentencia1 = "[{\"idSubcuenta\":\"" + entity.getListaDepreciable().getSubCuenta().getId() + "\",\"debe\":\"0\",\"haber\":\""
                        + entity.getListaDepreciable().getCuota_depresiacion() + "\",\"tipoMovimiento\":\"Depreciación\"},"
                        + "{\"idSubcuenta\":\"" + this.idSubCuenta + "\",\"debe\":\""
                        + entity.getListaDepreciable().getCuota_depresiacion() + "\",\"haber\":\"0\",\"tipoMovimiento\":\"Depreciación\"}]";
                conexion.ejecutarProcedure("generateasientocotableexternal", "'" + sentencia + "','" + sentencia1 + "'");
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conexion.desconectar();
            }
        }
    }
}
