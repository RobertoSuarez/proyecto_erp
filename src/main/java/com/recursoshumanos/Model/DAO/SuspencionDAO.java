/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Empleado;
import com.recursoshumanos.Model.Entidad.Suspencion;
import com.recursoshumanos.Model.Interfaces.IDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author kestradalp
 * @author rturr
 * @author ClasK7
 * 
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades
 * para todo lo que tenga que ver con Suspensiones.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class SuspencionDAO implements IDAO<Suspencion> {

    private final Conexion conexion;
    private Suspencion suspencion;

    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y Suspencion.
     */
    public SuspencionDAO() {
        this.conexion = new Conexion();
        this.suspencion = new Suspencion();
    }
    
    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y Suspencion.
     * 
     * @param conexion Objeto de tipo Conexion con la información de la
     *                 conexion.
     */
    public SuspencionDAO(Conexion conexion) {
        this.conexion = conexion;
        this.suspencion = new Suspencion();
    }
    
    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y Suspencion.
     * 
     * @param suspencion Objeto de tipo Suspencion con la información de una
     *                   suspencion.
     */
    public SuspencionDAO(Suspencion suspencion) {
        this.conexion = new Conexion();
        this.suspencion = suspencion;
    }

    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y Suspencion.
     * 
     * @param conexion Objeto de tipo Conexion con la información de la
     *                 conexion.
     * @param suspencion Objeto de tipo Suspencion con la información de la
     *                   suspencion.
     */
    public SuspencionDAO(Conexion conexion, Suspencion suspencion) {
        this.conexion = conexion;
        this.suspencion = suspencion;
    }

    /**
     * El @override nos ayuda a sobre-escribir el método
     * en el que se esta trabajando
     */

    /**
     * Función que devuelve la conexion presente
     * 
     * @return conexion Objeto de tipo Conexion con la información de la
     *                  conexion.
     */
    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    /**
     * A continuación se crea la función insertar
     * @return insertar devuelve el resultado de la funcion
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            return conexion.insertar("suspencion", "id_empleado, cantidad_dias, "
                                   + "valor, detalle, estado", 
                    suspencion.getEmpleado().getId()+ "," 
                    + suspencion.getCantidadDias() + ","
                    + suspencion.getValor() + ",'"
                    + suspencion.getDetalle() + "'," 
                    + suspencion.isEstado(), "id_suspencion");
        }
        return -1;
    }

    /**
     * A continuación se crea la función insertar
     * @param entity Contiene la información de la entidad Suspencion
     * @return insertar() Devuelve la función con la entidad dentro
     */
    @Override
    public int insertar(Suspencion entity) {
        this.suspencion = entity;
        return insertar();
    }

    /**
     * A continuación se crea la función actualizar
     * @return conexion.modificar() Devuelve el resultado de la función
     *                              modificar para actualizar una suspencion.
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("suspencion",
                    "cantidad_dias = " + suspencion.getCantidadDias()+ ", "
                                       + "valor = " + suspencion.getValor() 
                                       + ", detalle = '"
                    + suspencion.getDetalle() + "', estado =" 
                            + suspencion.isEstado(),
                    "id_suspencion = " + suspencion.getId() 
                            + " AND id_empleado = " 
                            + suspencion.getEmpleado().getId());
        }
        return -1;
    }

    /**
     * A continuación se crea la función actualizar
     * @param entity Contiene la información de la entidad Suspencion
     * @return actualizar() Devuelve el resultado de la función actualizar
     *                      para modificar la entidad de una suspencion.
     */
    @Override
    public int actualizar(Suspencion entity) {
        this.suspencion = entity;
        return actualizar();
    }

    /**
     * A continuación se crea la función buscarPorId
     * @param id Contiene el id del sueldo a buscar
     * @return lista.get() Devuelve el resultado de la búsqueda, dada por una
     *                     lista
     */
    @Override
    public Suspencion buscarPorId(Object id) {
        List<Suspencion> lista = buscar("id_suspencion = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    /**
     * A continuación se crea la función Listar
     * @return buscar Devuelve el resultado de la búsqueda, con una condición
     *                de ordenar el ID de la suspencion de forma descendente
     */
    @Override
    public List<Suspencion> Listar() {
        return buscar(null, null);
    }
    
    /**
     * A continuación se crea la función historial
     * @param empleado Contiene la información del Empleado al cual
     *                 se le solicita su historial de suspenciones
     * @return suspenciones Devuelve una lista de tipo Suspencion con el 
     *                      historial de suspenciones
     */
    public List<Suspencion> historial(Empleado empleado) {
        List<Suspencion> suspenciones = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("suspencion", "id_suspencion, "
                                           + "valor, cantidad_dias, "
                                           + "detalle, estado",
                        "id_empleado = " + empleado.getId(), 
                        "valor, cantidad_dias DESC");
                while (result.next()) {
                    suspenciones.add(new Suspencion(
                            result.getInt("id_suspencion"),
                            result.getInt("cantidad_dias"),
                            empleado,
                            result.getFloat("valor"),
                            result.getString("detalle"),
                            result.getBoolean("estado"))
                    );
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return suspenciones;
    }
    
    /**
     * A continuación se crea la función listar
     * @param empleado Contiene la información del Empleado al cual
     *                 se le solicita su historial de suspenciones
     * @return suspenciones Devuelve una lista de tipo Suspencion con 
     *                      el historial de suspenciones
     */
    public List<Suspencion> Listar(Empleado empleado) {
        List<Suspencion> suspenciones = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("suspencion", "id_suspencion, "
                                           + "cantidad_dias, valor, detalle",
                        "id_empleado = " + empleado.getId() + 
                                " AND estado = true", null);
                while (result.next()) {
                    suspenciones.add(new Suspencion(
                            result.getInt("id_suspencion"),
                            result.getInt("cantidad_dias"),
                            empleado,
                            result.getFloat("valor"),
                            result.getString("detalle"), true));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return suspenciones;
    }
    
    /**
     * A continuación se crea la función buscar
     * @param empleado Contiene el objeto con la información del empleado
     * @return suspencion Devuelve la lista de las suspenciones 
     *                    encontradas en la búsqueda.
     */
public Suspencion buscar(Empleado empleado) {
        suspencion = new Suspencion(empleado);
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("suspencion", "id_suspencion, "
                                           + "cantidad_dias, valor, detalle",
                        "id_empleado = " + empleado.getId() + 
                                " AND estado = true", null);
                while (result.next()) {
                    suspencion.setId(result.getInt("id_suspencion"));
                    suspencion.setCantidadDias(result.getInt("cantidad_dias"));
                    suspencion.setValor(result.getFloat("valor"));
                    suspencion.setDetalle(result.getString("detalle"));
                    suspencion.setEstado(true);
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return suspencion;
    }

    /**
     * A continuación se crea la función buscar
     * @param restricciones Contiene las restricciones de la búsqueda en la 
     *                      consulta de la BD
     * @param OrdenarAgrupar Contiene la restricción de agrupamiento de la
     *                       búsqueda en la consulta de la BD
     * @return suspenciones Devuelve la lista de las suspenciones 
     *                      encontradas en la búsqueda.
     */
    private List<Suspencion> buscar(@Nullable String restricciones, 
                                    @Nullable String OrdenarAgrupar) {
        List<Suspencion> suspenciones = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("suspencion", "id_suspencion, "
                                           + "id_empleado, cantidad_dias, "
                                           + "valor, detalle, estado",
                        restricciones, OrdenarAgrupar);
                suspenciones = new ArrayList<>();
                EmpleadoDAO eDAO = new EmpleadoDAO();
                while (result.next()) {
                    suspenciones.add(new Suspencion(
                            result.getInt("id_suspencion"),
                            result.getInt("cantidad_dias"),
                            (suspencion.getEmpleado().getId()>0? suspencion.getEmpleado() : eDAO.buscarPorId(result.getInt("id_empleado"))),
                            result.getFloat("valor"),
                            result.getString("detalle"),
                            result.getBoolean("estado")));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return suspenciones;
    }
}