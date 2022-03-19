/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.activosfijos.dao;

import com.activosfijos.model.ParametroActivoDepreciable;
import com.contabilidad.dao.SubCuentaDAO;
import com.global.config.Conexion;
import com.recursoshumanos.Model.Interfaces.IDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author cturriagos
 */
public class ParametroActivoDepreciableDAO implements IDAO<ParametroActivoDepreciable>  {
    private final Conexion conexion;
    private ParametroActivoDepreciable parametroActivoDepreciable;

    public ParametroActivoDepreciableDAO(Conexion conexion, ParametroActivoDepreciable parametroActivoDepreciable) {
        this.conexion = conexion;
        this.parametroActivoDepreciable = parametroActivoDepreciable;
    }

    public ParametroActivoDepreciableDAO(Conexion conexion) {
        this.conexion = conexion;
        this.parametroActivoDepreciable = new ParametroActivoDepreciable();
    }

    public ParametroActivoDepreciableDAO(ParametroActivoDepreciable parametroActivoDepreciable) {
        this.conexion = new Conexion();
        this.parametroActivoDepreciable = parametroActivoDepreciable;
    }

    public ParametroActivoDepreciableDAO() {
        this.conexion = new Conexion();
        this.parametroActivoDepreciable = new ParametroActivoDepreciable();
    }

    public ParametroActivoDepreciable getParametroActivoDepreciable() {
        return parametroActivoDepreciable;
    }

    public void setParametroActivoDepreciable(ParametroActivoDepreciable parametroActivoDepreciable) {
        this.parametroActivoDepreciable = parametroActivoDepreciable;
    }

    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            return conexion.insertar("parametro_activo_depreciable",
                    "activo_deprecianle, anios, porcentaje",
                    parametroActivoDepreciable.getActivoDepreciable().getId() + ","
                    + parametroActivoDepreciable.getAnios() + ","
                    + parametroActivoDepreciable.getPorcentajeAnual(), "idprametro");
        }
        return -1;
    }

    @Override
    public int insertar(ParametroActivoDepreciable entity) {
        this.parametroActivoDepreciable = entity;
        return insertar();
    }

    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("parametro_activo_depreciable",
                    "anios = " + parametroActivoDepreciable.getAnios()+ ", porcentaje = "
                    + parametroActivoDepreciable.getPorcentajeAnual(), "idprametro = "
                    + parametroActivoDepreciable.getIdParametro()+ " AND activo_deprecianle = "
                    + parametroActivoDepreciable.getActivoDepreciable().getId());
        }
        return -1;
    }

    @Override
    public int actualizar(ParametroActivoDepreciable entity) {
        this.parametroActivoDepreciable = entity;
        return actualizar();
    }

    @Override
    public ParametroActivoDepreciable buscarPorId(Object id) {
        List<ParametroActivoDepreciable> lista = buscar("idprametro = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }
    
    public ParametroActivoDepreciable buscarPorCuenta(int id) {
        List<ParametroActivoDepreciable> lista = buscar("activo_deprecianle = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    @Override
    public List<ParametroActivoDepreciable> Listar() {
        return buscar(null, null);
    }
    
    private List<ParametroActivoDepreciable> buscar(@Nullable String restricciones, @Nullable String OrdenarAgrupar) {
        List<ParametroActivoDepreciable> parametros = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("parametro_activo_depreciable", "idprametro, "
                        + "activo_deprecianle, anios, porcentaje",
                        restricciones, OrdenarAgrupar);
                SubCuentaDAO scDAO = new SubCuentaDAO();
                while (result.next()) {
                    parametros.add(new ParametroActivoDepreciable(
                            result.getInt("idprametro"),
                            scDAO.getSubCuenta(result.getInt("activo_deprecianle")),
                            result.getInt("anios"),
                            result.getDouble("porcentaje")
                    ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return parametros;
    }
}
