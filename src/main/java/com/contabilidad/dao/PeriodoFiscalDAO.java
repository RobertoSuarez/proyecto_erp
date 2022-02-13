/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contabilidad.dao;

import com.global.config.Conexion;
import com.contabilidad.models.PeriodoFiscal;
import com.recursoshumanos.Model.Interfaces.IDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author cturriagos
 */
public class PeriodoFiscalDAO implements IDAO<PeriodoFiscal>  {
    private Conexion conexion;
    private PeriodoFiscal periodoFiscal;

    public PeriodoFiscalDAO() {
        this.conexion = new Conexion();
        this.periodoFiscal = new PeriodoFiscal();
    }

    public PeriodoFiscalDAO(Conexion conexion) {
        this.conexion = conexion;
        this.periodoFiscal = periodoFiscal;
    }

    public PeriodoFiscalDAO(PeriodoFiscal periodoFiscal) {
        this.conexion = new Conexion();
        this.periodoFiscal = periodoFiscal;
    }

    public PeriodoFiscalDAO(Conexion conexion, PeriodoFiscal periodoFiscal) {
        this.conexion = conexion;
        this.periodoFiscal = periodoFiscal;
    }

    /**
     * Crea el SET para el mètodo conexion
     * @param conexion Almacena la variable de la conexion
     */
    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
    }

    public PeriodoFiscal getPeriodoFiscal() {
        return periodoFiscal;
    }

    public void setPeriodoFiscal(PeriodoFiscal periodoFiscal) {
        this.periodoFiscal = periodoFiscal;
    }

    /**
     * Llama a la clase conexión.
     * @return conexion Objeto con los datos para validar la conexión.
     */
    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    /**
     * Creación del metedo INSERTAR, para registrar
     * los datos recopilados de los PeriodoFiscal.
     * @return int Retorna la confirmación
     * de un registro exitoso o erroneo.
     **/
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            periodoFiscal.setIdPeriodoFiscal(conexion.insertar("periodo_fiscal",
                    "fecha_inicio, fecha_fin, periodo, activo",
                    "'" + DateFormatUtils.format(periodoFiscal.getFechaInicio(), "yyyy-MM-dd HH:mm:SS") + "', '" +
                    DateFormatUtils.format(periodoFiscal.getFechaFin(), "yyyy-MM-dd HH:mm:SS") + "', "
                    + periodoFiscal.getPreriodo() + ", " + periodoFiscal.isActivo(),
                    "id_periodo_fiscal"));
            return periodoFiscal.getIdPeriodoFiscal();
        }
        return -1;
    }

    /**
     * Creación del metedo INSERTAR, para refrescar
     * los datos recopilados de los PeriodoFiscal (Sólamente se envía
     * la entidad como parámetro).
     * @return int Retorna la confirmación
     * de un registro exitoso o erróneo.
     **/
    @Override
    public int insertar(PeriodoFiscal entity) {
        this.periodoFiscal = entity;
        return insertar();
    }

    /**
     * Creación del metedo ACTUALIZAR, para refrescar
     * los datos recopilados de los PeriodoFiscal.
     * @return int Retorna la confirmación
     * de un registro exitoso o erróneo.
     **/
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("periodo_fiscal",
                    "fecha_inicio = '" + DateFormatUtils.format(periodoFiscal.getFechaInicio(), "yyyy-MM-dd HH:mm:SS") +
                    "', feccha_fin = '" + DateFormatUtils.format(periodoFiscal.getFechaFin(), "yyyy-MM-dd HH:mm:SS") +
                    "', periodo = " + periodoFiscal.getPreriodo() + ", activo = " + periodoFiscal.isActivo(),
                    "id_periodo_fiscal = " + periodoFiscal.getIdPeriodoFiscal());
        }
        return -1;
    }

    /**
     * Creación del metedo ACTUALIZAR, para refrescar
     * los datos recopilados de los PeriodoFiscal (Sólamente se envía
     * la entidad como parámetro).
     * @return int Retorna la confirmación
     * de un registro exitoso o erróneo.
     **/
    @Override
    public int actualizar(PeriodoFiscal entity) {
        this.periodoFiscal = entity;
        return actualizar();
    }
    
    /**
     * Creación del metedo DESACTIVAR, para desactivar
     * un PeriodoFiscal.
     * @return int Retorna la confirmación
     * de un registro exitoso o erróneo.
     **/
    public int descativar() {
        if (conexion.isEstado()) {
            return conexion.modificar("periodo_fiscal", "activo = false", "id_periodo_fiscal = " + periodoFiscal.getIdPeriodoFiscal());
        }
        return -1;
    }

    /**
     * Creación del método buscarPorId, para buscar
     * un PeriodoFiscal por ID.
     * @return PeriodoFiscal Retorna un objeto de este tipo.
     **/
    @Override
    public PeriodoFiscal buscarPorId(Object id) {
        List<PeriodoFiscal> lista = buscar("id_periodo_fiscal = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return new PeriodoFiscal();
    }

    /**
     * Creación del método Listar, para listar
     * un PeriodoFiscal en orden descendente.
     * @return PeriodoFiscal Retorna un objeto de este tipo.
     **/
    @Override
    public List<PeriodoFiscal> Listar() {
        return buscar(null, "periodo DESC");
    }

    /**
     * Creación del método Activos, para listar
     * un PeriodoFiscal activo.
     * @return  Retorna una lista de este tipo.
     **/
    public List<PeriodoFiscal> Activos() {
        return buscar("activo = true", "periodo DESC");
    }
    
    /**
     * Creación del metedo BUSCAR, para buscar
     * los datos recopilados de un PeriodoFiscal.
     * @return PeriodosFiscales Retorna una lista de este tipo.
     **/
    private List<PeriodoFiscal> buscar(@Nullable String restricciones, @Nullable String OrdenarAgrupar) {
        List<PeriodoFiscal> periodosFiscales = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("periodo_fiscal", "id_periodo_fiscal, fecha_inicio, fecha_fin, periodo, activo", restricciones, OrdenarAgrupar);
                while (result.next()) {
                    periodosFiscales.add(new PeriodoFiscal(
                            result.getInt("id_periodo_fiscal"),
                            result.getDate("fecha_inicio"),
                            result.getDate("fecha_fin"),
                            result.getInt("periodo"),
                            result.getBoolean("activo")
                        ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return periodosFiscales;
    }
}
