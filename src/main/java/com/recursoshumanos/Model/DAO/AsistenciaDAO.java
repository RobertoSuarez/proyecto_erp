/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Asistencia;
import com.recursoshumanos.Model.Entidad.DetalleHorario;
import com.recursoshumanos.Model.Entidad.EmpleadoPuesto;
import com.recursoshumanos.Model.Interfaces.IDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/**
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 *
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades para
 * todo lo que tenga que ver con Asistencias. Y se encarga de administrar las
 * sentencias de la BD, utilizando las clases de los modelos
 */
public class AsistenciaDAO implements IDAO<Asistencia> {

    private final Conexion conexion;
    private Asistencia asistencia;

    public AsistenciaDAO() {
        this.conexion = new Conexion();
        this.asistencia = new Asistencia();
    }

    public AsistenciaDAO(Asistencia asistencia) {
        this.conexion = new Conexion();
        this.asistencia = asistencia;
    }

    public AsistenciaDAO(Conexion conexion) {
        this.conexion = conexion;
        this.asistencia = new Asistencia();
    }

    public AsistenciaDAO(Conexion conexion, Asistencia asistencia) {
        this.conexion = conexion;
        this.asistencia = asistencia;
    }

    public Asistencia getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
    }

    /**
     * A continuación se crea el método obtenerconexion:
     *
     * @return Conexion variable con los datos de la conexión
     */
    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    /**
     * A continuación se crea el método insertar:
     *
     * @return insertar maneja la acciòn de INSERT
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            conexion.insertar("asistencia",
                    "id_empleado_puesto, reg_hora_ingreso, reg_hora_salida, "
                    + "fecha, id_detalle_horario",
                    asistencia.getEmpleadoPuesto().getId()
                    + ", CAST('" + asistencia.getIngreso()
                    + "'::timestamp AS time), null, '"
                    + asistencia.getFecha() + "', "
                    + asistencia.getDetalleHorario().getId(),
                    "id_empleado_puesto");
            return asistencia.getDetalleHorario().getId();
        }
        return -1;
    }

    /**
     * A continuación se crea el método insertar con una asistencia vacia para
     * controles (validaciones):
     *
     * @param entity Identifica la entidad de asistencia
     * @return insertar maneja la acciòn de INSERT
     */
    @Override
    public int insertar(Asistencia entity) {
        this.asistencia = entity;
        return insertar();
    }

    /**
     * A continuación se crea el método actualizar:
     *
     * @return actualizar maneja la acciòn de UPDATE
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("asistencia",
                    "reg_hora_salida = CAST('" + asistencia.getSalida()
                    + "'::timestamp AS time)",
                    "id_empleado_puesto = "
                    + asistencia.getEmpleadoPuesto().getId()
                    + " AND reg_hora_ingreso = CAST('"
                    + asistencia.getIngreso()
                    + "'::timestamp AS time) AND fecha = '"
                    + asistencia.getFecha()
                    + "' AND id_detalle_horario = "
                    + asistencia.getDetalleHorario().getId());
        }
        return -1;
    }

    /**
     * A continuación se crea el método insertar con una asistencia vacia para
     * controles (validaciones):
     *
     * @param entity Identifica la entidad
     * @return actualizar maneja la acciòn de UPDATE
     */
    @Override
    public int actualizar(Asistencia entity) {
        this.asistencia = entity;
        return actualizar();
    }

    /**
     *
     * @param id Contiene el dato del ID que sirve para realizar la acción de
     * buscar a cierto dato de acuerdo al tipo de identificacón.
     * @return lista retorna la lista de la asistencia consultada.
     */
    @Override
    public Asistencia buscarPorId(Object id) {
        List<Asistencia> lista = buscar("id_cargaf = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return new Asistencia(new EmpleadoPuesto(), null, null, new Date(),
                new DetalleHorario());
    }

    /**
     * Busca al empleado de acuerdo a su registri
     *
     * @param empleadoPuesto mantienen el puesto del empleado
     * @param fecha mite la fecha de formación.
     * @param detalleHorario Muestra todo el horario
     * @return Asistencia buscar
     */
    public Asistencia buscar(EmpleadoPuesto empleadoPuesto, Date fecha,
            DetalleHorario detalleHorario) {
        asistencia = new Asistencia(empleadoPuesto, null, null, fecha,
                detalleHorario);
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("asistencia", "reg_hora_ingreso, "
                        + "reg_hora_salida",
                        "id_empleado_puesto = " + empleadoPuesto.getId()
                        + " AND fecha = '"
                        + asistencia.getFecha() + "' AND id_detalle_horario = "
                        + detalleHorario.getId(), null);
                while (result.next()) {
                    asistencia.setIngreso(result.getTime("reg_hora_ingreso"));
                    asistencia.setSalida(result.getTime("reg_hora_salida"));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return asistencia;
    }

    /**
     * Lista a los empleados de acuerdo a su ID de registro, los ordena para una
     * mejor vista.
     *
     * @param empleadoPuesto objeto del puesto laboral
     * @param fecha objeto de fecha
     * @return asistencias regresa la lista de asistencias de acuerdo o por
     * usuario.
     */
    public List<Asistencia> buscar(EmpleadoPuesto empleadoPuesto, Date fecha) {
        List<Asistencia> asistencias = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("asistencia",
                        "fecha, reg_hora_ingreso, reg_hora_salida, "
                        + "id_detalle_horario",
                        "id_empleado_puesto = " + empleadoPuesto.getId()
                        + " AND EXTRACT(YEAR FROM fecha) = EXTRACT(YEAR FROM '"
                        + fecha.toString()
                        + "'::DATE) AND EXTRACT(MONTH FROM fecha) = "
                        + "EXTRACT(MONTH FROM '" + fecha.toString()
                        + "'::DATE)", "fecha DESC");
                DetalleHorarioDAO dhdao = new DetalleHorarioDAO();
                while (result.next()) {
                    asistencias.add(new Asistencia(empleadoPuesto,
                            result.getTime("reg_hora_ingreso"),
                            result.getTime("reg_hora_salida"),
                            result.getDate("fecha"),
                            dhdao.buscarPorId(result.getInt(
                                    "id_detalle_horario"),
                                    empleadoPuesto.getHorarioLaboral())));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return asistencias;
    }

    @Override
    public List<Asistencia> Listar() {
        return buscar(null, "fecha DESC");
    }

    private List<Asistencia> buscar(@Nullable String restricciones,
            @Nullable String OrdenarAgrupar) {
        List<Asistencia> asistencias = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("asistencia",
                        "id_empleado_puesto, reg_hora_ingreso, reg_hora_salida, "
                        + "fecha, id_detalle_horario",
                        restricciones, OrdenarAgrupar);
                EmpleadoPuestoDAO epdao = new EmpleadoPuestoDAO();
                DetalleHorarioDAO dhdao = new DetalleHorarioDAO();
                while (result.next()) {
                    asistencias.add(new Asistencia(
                            epdao.buscarPorId(result.getInt(
                                    "id_empleado_puesto")),
                            result.getTime("reg_hora_ingreso"),
                            result.getTime("reg_hora_salida"),
                            result.getDate("fecha"), dhdao.buscarPorId(
                            result.getInt("id_detalle_horario"))
                    ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return asistencias;
    }
}
