/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.DetalleHorario;
import com.recursoshumanos.Model.Entidad.EmpleadoPuesto;
import com.recursoshumanos.Model.Entidad.HorarioLaboral;
import com.recursoshumanos.Model.Interfaces.IDAO;
import org.jetbrains.annotations.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 *
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades para
 * todo lo que tenga que ver con Departamentos. Y se encarga de administrar las
 * sentencias de la BD, utilizando las clases de los modelos
 */
public class DetalleHorarioDAO implements IDAO<DetalleHorario> {

    private final Conexion conexion;
    private DetalleHorario detalleHorario;

    public DetalleHorarioDAO() {
        conexion = new Conexion();
        detalleHorario = new DetalleHorario();
    }

    public DetalleHorarioDAO(DetalleHorario detalleHorario) {
        conexion = new Conexion();
        this.detalleHorario = detalleHorario;
    }

    public DetalleHorarioDAO(Conexion conexion) {
        this.conexion = conexion;
        detalleHorario = new DetalleHorario();
    }

    public DetalleHorarioDAO(DetalleHorario detalleHorario, Conexion conexion) {
        this.conexion = conexion;
        this.detalleHorario = detalleHorario;
    }

    public DetalleHorario getDetalleHorario() {
        return detalleHorario;
    }

    public void setDetalleHorario(DetalleHorario detalleHorario) {
        this.detalleHorario = detalleHorario;
    }

    /**
     * Llama a la clase conexión.
     *
     * @return conexion Objeto con los datos para validar la conexión.
     */
    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    /**
     * Creación del metedo INSERTAR, para registrar los datos recopilados de la
     * clase de DetalleHorarioDAO.
     *
     * @return conexion.insertar Retorna la confirmación de un registro exitoso
     * o erroneo.
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            return conexion.insertar("detalle_horario",
                    "id_ingreso_salida, id_horario_laboral, id_dia_semana, "
                    + "estado", detalleHorario.getIngresoSalida().getId() + ","
                    + detalleHorario.getHorarioLaboral().getId() + ","
                    + detalleHorario.getDiaSemana().getId() + ","
                    + detalleHorario.isEstado(),
                    "id_detalle_horario");
        }
        return -1;
    }

    /**
     * Metodo que verifica y controla las entidades o registros vacios que se
     * realicen al momento de insertar.
     *
     * @param entity Objeto con la información para la validación
     * correspondiente.
     * @return insertar Objeto con la información.
     */
    @Override
    public int insertar(DetalleHorario entity) {
        this.detalleHorario = entity;
        return insertar();
    }

    /**
     * A continuación se crea el método actualizar:
     *
     * @return conexion Objeto con la conexion con los datos correspondientes
     * para su modificación.
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("detalle_horario",
                    "id_ingreso_salida = "
                    + detalleHorario.getIngresoSalida().getId()
                    + ", id_horario_laboral = "
                    + detalleHorario.getHorarioLaboral().getId()
                    + ", id_dia_semana = "
                    + detalleHorario.getDiaSemana().getId() + ", estado = "
                    + detalleHorario.isEstado(),
                    "id_detalle_horario = " + detalleHorario.getId());
        }
        return -1;
    }

    /**
     * Metodo que verifica y controla las entidades o registros vacios que se
     * realicen al momento de actualizar.
     *
     * @param entity Objeto que valida campos vacios
     * @return actualizar Objeto que retorna la actualización de o los elementos
     * en la BD.
     */
    @Override
    public int actualizar(DetalleHorario entity) {
        this.detalleHorario = entity;
        return actualizar();
    }

    /**
     * Modifica el estado del Departamento, ya sea ha Activado o Desactivado
     */
    public void cambiarEstado() {
        if (conexion.isEstado()) {
            conexion.modificar("detalle_horario", "estado = NOT estado", "id_detalle_horario = " + detalleHorario.getId());
        }
    }

    /**
     * Metodo que permite buscar mediante el ID dentro de los registros.
     *
     * @param id Objeto encargado del ID del parametro de busqueda.
     * @return lista Retorna la lista de busqueda indicada
     */
    @Override
    public DetalleHorario buscarPorId(Object id) {
        List<DetalleHorario> lista = buscar("id_detalle_horario = " + id + " AND id_horario_laboral = " + detalleHorario.getHorarioLaboral().getId(), null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    /**
     * Aparte de identificar por ID se realiza una variable que nos permite
     * obtener su horario laboral del dato indicado.
     *
     * @param id Objeto encargado del ID del parametro de busqueda.
     * @param horarioLaboral Objeto encargado de identificar el registro (ID)
     * del dato.
     * @return detalleHorario Regresa el detalle del horario (su lista).
     */
    public DetalleHorario buscarPorId(int id, HorarioLaboral horarioLaboral) {
        detalleHorario = new DetalleHorario();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("detalle_horario",
                        "id_ingreso_salida, id_dia_semana, estado", "id_horario_laboral = "
                        + horarioLaboral.getId() + " AND id_detalle_horario = " + id, null);
                DiaSemanaDAO diaDAO = new DiaSemanaDAO();
                IngresosSalidasDAO isDAO = new IngresosSalidasDAO();
                while (result.next()) {
                    detalleHorario.setId(id);
                    detalleHorario.setIngresoSalida(isDAO.buscarPorId(result.getInt("id_ingreso_salida")));
                    detalleHorario.setHorarioLaboral(horarioLaboral);
                    detalleHorario.setDiaSemana(diaDAO.buscarPorId(result.getInt("id_dia_semana")));
                    detalleHorario.setEstado(result.getBoolean("estado"));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return detalleHorario;
    }

    /**
     * Valida que no exista nulo en la busqueda
     *
     * @return ListDetalleHorario envia la lista del resultado
     */
    @Override
    public List<DetalleHorario> Listar() {
        return buscar(null, null);
    }

    /**
     * Valida que sea diferente a nulo dentro del método Listar el detalle de
     * horario.
     *
     * @param idHorarioLaboral Objeto con el ID del horario laboral.
     * @return ListDetalleHorario envia la lista del resultado.
     */
    public List<DetalleHorario> Listar(int idHorarioLaboral) {
        return buscar("id_horario_laboral = " + idHorarioLaboral, null);
    }

    /**
     * Busca el puesto del empleado de acuerdo a su registro
     *
     * @param empleadoPuesto Objeto que almacena el puesto del empleado.
     * @return ListDetalleHorario envia la lista del resultado.
     */
    public List<DetalleHorario> buscar(EmpleadoPuesto empleadoPuesto) {
        if (conexion.isEstado()) {
            ResultSet result;
            List<DetalleHorario> detalles;
            try {
                detalleHorario.setHorarioLaboral(empleadoPuesto.getHorarioLaboral());
                result = conexion.selecionar("detalle_horario AS dh INNER JOIN public.horario_laboral AS hl ON hl.id_horario_laboral = dh.id_horario_laboral",
                        "id_detalle_horario, id_ingreso_salida, dh.id_horario_laboral, id_dia_semana, dh.estado",
                        "hl.id_horario_laboral = " + detalleHorario.getHorarioLaboral().getId()
                        + " AND hl.estado = true AND dh.estado = true",
                        null);
                detalles = new ArrayList<>();
                DiaSemanaDAO diaDAO = new DiaSemanaDAO();
                IngresosSalidasDAO ingresoSalidaDAO = new IngresosSalidasDAO();
                while (result.next()) {
                    detalles.add(new DetalleHorario(result.getInt("id_detalle_horario"),
                            ingresoSalidaDAO.buscarPorId(result.getInt("id_ingreso_salida")),
                            detalleHorario.getHorarioLaboral(),
                            diaDAO.buscarPorId(result.getInt("id_dia_semana")),
                            result.getBoolean("estado")
                    ));
                }
                result.close();
                return detalles;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return null;
    }

    /**
     * Busca el DetalleHorario de acuerdo a su registro
     *
     * @param restricciones Objeto con las restricciones o validaciones de las
     * mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar los resultados.
     * @return List<DetalleHorario> Muestra la lista.
     */
    private List<DetalleHorario> buscar(@Nullable String restricciones, @Nullable String OrdenarAgrupar) {
        if (conexion.isEstado()) {
            ResultSet result;
            List<DetalleHorario> detalles;
            try {
                result = conexion.selecionar("detalle_horario", "id_detalle_horario, id_ingreso_salida, id_horario_laboral, id_dia_semana, estado", restricciones, OrdenarAgrupar);
                detalles = new ArrayList<>();
                DiaSemanaDAO diaDAO = new DiaSemanaDAO();
                IngresosSalidasDAO ingresoSalidaDAO = new IngresosSalidasDAO();
                while (result.next()) {
                    detalles.add(new DetalleHorario(result.getInt("id_detalle_horario"),
                            ingresoSalidaDAO.buscarPorId(result.getInt("id_ingreso_salida")),
                            detalleHorario.getHorarioLaboral(),
                            diaDAO.buscarPorId(result.getInt("id_dia_semana")),
                            result.getBoolean("estado")
                    ));
                }
                result.close();
                return detalles;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return null;
    }
}
