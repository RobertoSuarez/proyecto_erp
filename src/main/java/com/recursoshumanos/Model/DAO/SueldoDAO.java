/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Empleado;
import com.recursoshumanos.Model.Entidad.Sueldo;
import com.recursoshumanos.Model.Interfaces.IDAO;
import org.jetbrains.annotations.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kestradalp
 * @author rturr
 * @author ClasK7
 * 
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades
 * para todo lo que tenga que ver con Sueldos.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class SueldoDAO  implements IDAO<Sueldo> {

    private Conexion conexion;
    private Sueldo sueldo;

    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y Sucursal.
     */
    public SueldoDAO() {
        conexion = new Conexion();
        sueldo = new Sueldo();
    }

    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y Sueldo.
     * 
     * @param sueldo Objeto de tipo Sueldo con la información de un sueldo.
     */
    public SueldoDAO(Sueldo sueldo) {
        conexion = new Conexion();
        this.sueldo = sueldo;
    }

    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y Sueldo.
     * 
     * @param conexion Objeto de tipo Conexion con la información de la
     *                 conexion.
     */
    public SueldoDAO(Conexion conexion) {
        this.conexion = conexion;
        sueldo = new Sueldo();
    }

    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y Sueldo.
     * 
     * @param conexion Objeto de tipo Conexion con la información de la
     *                 conexion.
     * @param sueldo Objeto de tipo Sueldo con la información del sueldo.
     */
    public SueldoDAO(Sueldo sueldo, Conexion conexion) {
        this.conexion = conexion;
        this.sueldo = sueldo;
    }
    
    public Sueldo getSueldo() {
        return sueldo;
    }

    public void setSueldo(Sueldo sueldo) {
        this.sueldo = sueldo;
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

    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
    }

    /**
     * A continuación se crea la función insertar
     * @return sueldo.getId() devuelve el ID del sueldo
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            sueldo.setId(conexion.insertar("sueldo",
                    "id_empleado, valor, fecha_actualizacion, estado",
                    sueldo.getEmpleado().getId() + ", " + sueldo.getValor()+ 
                    ", CURRENT_TIMESTAMP, " + sueldo.isEstado(),
                    "id_sueldo"));
            return sueldo.getId();
        }
        return -1;
    }

    /**
     * A continuación se crea la función insertar
     * @param entity Contiene la información de la entidad Sueldo
     * @return insertar() Devuelve la función con la entidad dentro
     */
    @Override
    public int insertar(Sueldo entity) {
        this.sueldo = entity;
        return insertar();
    }

    /**
     * A continuación se crea la función actualizar
     * @return conexion.modificar() Devuelve el resultado de la función
     *                              modificar para actualizar un sueldo.
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("sueldo",
                    "id_empleado = " + sueldo.getEmpleado().getId() 
                                     + ", valor = " + sueldo.getValor()
                    + ", fecha_actualizacion = '" 
                    + sueldo.getFechaActualizacion()+ "', estado = " 
                    + sueldo.isEstado(),
                    "id_sueldo = " + sueldo.getId());
        }
        return -1;
    }

    /**
     * A continuación se crea la función actualizar
     * @param entity Contiene la información de la entidad Sueldo
     * @return actualizar() Devuelve el resultado de la función actualizar
     *                      para modificar la entidad de un Sueldo.
     */
    @Override
    public int actualizar(Sueldo entity) {
        this.sueldo = entity;
        return actualizar();
    }

    public void desactivar() {
        if (conexion.isEstado()) {
            conexion.ejecutarProcedure("desactivarsueldo", "" 
                                       + sueldo.getEmpleado().getId());
        }
    }

    /**
     * A continuación se crea la función buscarPorId
     * @param id Contiene el id del sueldo a buscar
     * @return lista.get() Devuelve el resultado de la búsqueda, dada por una
     *                     lista
     */
    @Override
    public Sueldo buscarPorId(Object id) {
        List<Sueldo> lista = buscar("id_sueldo = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return new Sueldo();
    }

    /**
     * A continuación se crea la función Listar
     * @return buscar Devuelve el resultado de la búsqueda, con una condición
     *                de ordenar el ID del sueldo de forma descendente
     */
    @Override
    public List<Sueldo> Listar() {
        return buscar(null, "fecha_actualizacion DESC");
    }
    
    /**
     * A continuación se crea la función Actual
     * @param empleado Contiene la información del Empleado al cual
     *                 se le solicita su sueldo
     * @return sueldo Devuelve un objeto de tipo Sueldo con la información del
     *                sueldo
     */
    public Sueldo Actual(Empleado empleado) {
        sueldo = new Sueldo();
        sueldo.setEmpleado(empleado);
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("sueldo", "id_sueldo, valor, "
                                           + "fecha_actualizacion",
                                             "estado = true AND id_empleado = " 
                                                     + empleado.getId(), null);
                while (result.next()) {
                    sueldo.setId(result.getInt("id_sueldo"));
                    sueldo.setValor(result.getFloat("valor"));
                    sueldo.setFechaActualizacion(result.getDate("fecha_actualizacion"));
                    sueldo.setEstado(true);
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return sueldo;
    }
    
    /**
     * A continuación se crea la función historial
     * @param empleado Contiene la información del Empleado al cual
     *                 se le solicita su historial de sueldo
     * @return sueldos Devuelve una lista de tipo Sueldo con el historial de
     *                 sueldos
     */
    public List<Sueldo> historial(Empleado empleado) {
        List<Sueldo> sueldos = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("sueldo", "id_sueldo, valor, "
                                           + "fecha_actualizacion, estado",
                                             "id_empleado = " + empleado.getId(), 
                                             "fecha_actualizacion DESC");
                while (result.next()) {
                    sueldos.add( new Sueldo(result.getInt("id_sueldo"), 
                                    empleado, result.getFloat("valor"), 
                                    result.getDate("fecha_actualizacion"), 
                                    result.getBoolean("estado")));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return sueldos;
    }

    /**
     * A continuación se crea la función buscar
     * @param restricciones Contiene las restricciones de la búsqueda en la 
     *                      consulta de la BD
     * @param OrdenarAgrupar Contiene la restricción de agrupamiento de la
     *                       búsqueda en la consulta de la BD
     * @return sueldos Devuelve la lista de los sueldos encontrados en la
     *                    búsqueda.
     */
    private List<Sueldo> buscar(@Nullable String restricciones, 
                                @Nullable String OrdenarAgrupar) {
        List<Sueldo> sueldos = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("sueldo", "id_sueldo, id_empleado, "
                                           + "valor, fecha_actualizacion, estado", 
                                             restricciones, OrdenarAgrupar);
                EmpleadoDAO edao = new EmpleadoDAO();
                while (result.next()) {
                    sueldos.add(new Sueldo(
                            result.getInt("id_sueldo"),
                            (sueldo.getEmpleado().getId()>0? 
         sueldo.getEmpleado() : edao.buscarPorId(result.getInt("id_empleado"))),
                            result.getFloat("valor"),
                            result.getDate("fecha_actualizacion"),
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
        return sueldos;
    }
}