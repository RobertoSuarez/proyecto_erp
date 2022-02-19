/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Amonestacion;
import com.recursoshumanos.Model.Entidad.Empleado;
import com.recursoshumanos.Model.Interfaces.IDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/**
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 *
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades para
 * todo lo que tenga que ver con Amonestaciones. Y se encarga de administrar las
 * sentencias de la BD, utilizando las clases de los modelos
 */
public class AmonestacionDAO implements IDAO<Amonestacion> {

    private final Conexion conexion;
    private Amonestacion amonestacion;

    /**
     * Constructor que inicia una nueva conexion e indica una nueva variable
     * Amonestacion.
     */
    public AmonestacionDAO() {
        this.conexion = new Conexion();
        this.amonestacion = new Amonestacion();
    }

    /**
     * Constructor que indica un nuevo objeto de Conexión e indica una nueva
     * Amonestacion.
     *
     * @param conexion Objeto con información de una conexion.
     */
    public AmonestacionDAO(Conexion conexion) {
        this.conexion = conexion;
        this.amonestacion = new Amonestacion();
    }

    /**
     * Constructor que indica un nuevo objeto de amonestacion e indica una nueva
     * Conexión.
     *
     * @param amonestacion Objeto con información de una amonestacion.
     */
    public AmonestacionDAO(Amonestacion amonestacion) {
        this.conexion = new Conexion();
        this.amonestacion = amonestacion;
    }

    /**
     * Constructor que recibe todas la variables de los anteriores
     * constructores.
     *
     * @param conexion Objeto con información de una conexion.
     * @param amonestacion Objeto com información de una amomnestacion
     */
    public AmonestacionDAO(Conexion conexion, Amonestacion amonestacion) {
        this.conexion = conexion;
        this.amonestacion = amonestacion;
    }

    /**
     * El @override nos ayuda a sobre-escribir el método en el que se esta
     * trabajando
     */
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
            return conexion.insertar("amonestacion",
                    "id_empleado, tipo, valor, detalle, estado",
                    amonestacion.getEmpleado().getId() + ",'"
                    + amonestacion.getTipo() + "',"
                    + amonestacion.getValor() + ",'"
                    + amonestacion.getDetalle() + "',"
                    + amonestacion.isEstado(), "id_amonestacion");
        }
        return -1;
    }

    /**
     * A continuación se crea el método insertar con una amonestación vacia para
     * controles (validaciones):
     *
     * @param entity Identifica la entidad.
     * @return insertar maneja la acciòn de INSERT
     */
    @Override
    public int insertar(Amonestacion entity) {
        this.amonestacion = entity;
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
            return conexion.modificar("amonestacion",
                    "tipo = '"
                    + amonestacion.getTipo() + "', valor = "
                    + amonestacion.getValor() + ", detalle = '"
                    + amonestacion.getDetalle() + "', estado ="
                    + amonestacion.isEstado(), "id_amonestacion = "
                    + amonestacion.getId() + " AND id_empleado = "
                    + amonestacion.getEmpleado().getId());
        }
        return -1;
    }

    /**
     * A continuación se crea el método actualizar con una amonestación vacia
     * para controles (validaciones):
     *
     * @param entity Identifica la entidad.
     * @return actualizar maneja la acciòn de UPDATE
     */
    @Override
    public int actualizar(Amonestacion entity) {
        this.amonestacion = entity;
        return actualizar();
    }

    /**
     *
     * @param id Contiene el dato del ID que sirve para realizar la acción de
     * buscar a cierto dato de acuerdo al tipo de identificacón.
     * @return lista retorna la lista de la amonestación consultada.
     */
    @Override
    public Amonestacion buscarPorId(Object id) {
        List<Amonestacion> lista = buscar("id_amonestacion = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    /**
     * Método que controla los nulls (nulos) en los resultados de busquedas que
     * se realicen.
     *
     * @return buscar retorna la lista de la amonestación consultada.
     */
    @Override
    public List<Amonestacion> Listar() {
        return buscar(null, null);
    }

    /**
     * Muestra el historial del empleado.
     *
     * @param empleado variable que registra el ID del empleado
     * @return amonestaciones devuelve el listado de las amonestaciones del
     * empleado.
     */
    public List<Amonestacion> historial(Empleado empleado) {
        List<Amonestacion> amonestaciones = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("amonestacion",
                        "id_amonestacion, tipo, valor, detalle, estado",
                        "id_empleado = " + empleado.getId(), "tipo, valor DESC");
                while (result.next()) {
                    amonestaciones.add(new Amonestacion(
                            result.getInt("id_amonestacion"),
                            empleado,
                            result.getString("tipo"),
                            result.getString("detalle"),
                            result.getFloat("valor"),
                            result.getBoolean("estado")
                    ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return amonestaciones;
    }

    /**
     * Lista a los empleados de acuerdo a su ID de registro, los ordena para una
     * mejor vista.
     *
     * @param empleado objeto empleado para obtener el dato.
     * @return amonestaciones retorna la lista del empleado para sus
     * amonestaciones.
     */
    public List<Amonestacion> Listar(Empleado empleado) {
        List<Amonestacion> amonestaciones = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("amonestacion",
                        "id_amonestacion, tipo, valor, detalle",
                        "id_empleado = " + empleado.getId()
                        + " AND estado = true", null);
                while (result.next()) {
                    amonestaciones.add(new Amonestacion(
                            result.getInt("id_amonestacion"),
                            empleado,
                            result.getString("tipo"),
                            result.getString("detalle"),
                            result.getFloat("valor"), true));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return amonestaciones;
    }

    /**
     * Permite buscar al empleado
     *
     * @param empleado variable que registra o almacena el ID del empleado que
     * se va ha buscar.
     * @return Amonestacion regresa el resultado de la busqueda.
     */
    public Amonestacion buscar(Empleado empleado) {
        amonestacion = new Amonestacion(empleado);
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("amonestacion",
                        "id_amonestacion, tipo, valor, detalle",
                        "id_empleado = " + empleado.getId()
                        + " AND estado = true", null);
                while (result.next()) {
                    amonestacion.setId(result.getInt("id_amonestacion"));
                    amonestacion.setTipo(result.getString("tipo"));
                    amonestacion.setDetalle(result.getString("detalle"));
                    amonestacion.setValor(result.getFloat("valor"));
                    amonestacion.setEstado(true);
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return amonestacion;
    }

    /**
     * Busca y realiza un ordenamiento de la misma
     *
     * @param restricciones las restricciones de busqueda
     * @param OrdenarAgrupar permite ordenar los datos
     * @return List<Amonestacion> regresan los datos de la busqueda de forma
     * ordenada.
     */
    private List<Amonestacion> buscar(@Nullable String restricciones,
            @Nullable String OrdenarAgrupar) {
        List<Amonestacion> amonestaciones = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("amonestacion", "id_amonestacion, "
                        + "id_empleado, tipo, valor, detalle, estado",
                        restricciones, OrdenarAgrupar);
                EmpleadoDAO eDAO = new EmpleadoDAO();
                while (result.next()) {
                    amonestaciones.add(new Amonestacion(
                            result.getInt("id_amonestacion"),
                            (amonestacion.getEmpleado().getId() > 0
                            ? amonestacion.getEmpleado()
                            : eDAO.buscarPorId(result.getInt("id_empleado"))),
                            result.getString("tipo"),
                            result.getString("detalle"),
                            result.getFloat("valor"),
                            result.getBoolean("estado")));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return amonestaciones;
    }
}
