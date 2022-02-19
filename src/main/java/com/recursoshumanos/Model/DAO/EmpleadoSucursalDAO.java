/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Empleado;
import com.recursoshumanos.Model.Entidad.EmpleadoSucursal;
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
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades
 * para todo lo que tenga que ver con Empleado Sucursal.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class EmpleadoSucursalDAO implements IDAO<EmpleadoSucursal> {

    private Conexion conexion;
    private EmpleadoSucursal empleadoSucursal;

    public EmpleadoSucursalDAO() {
        conexion = new Conexion();
        empleadoSucursal = new EmpleadoSucursal();
    }

    public EmpleadoSucursalDAO(EmpleadoSucursal empleadoSucursal) {
        conexion = new Conexion();
        this.empleadoSucursal = empleadoSucursal;
    }

    public EmpleadoSucursalDAO(Conexion conexion) {
        this.conexion = conexion;
        empleadoSucursal = new EmpleadoSucursal();
    }

    public EmpleadoSucursalDAO(EmpleadoSucursal empleadoSucursal, Conexion conexion) {
        this.conexion = conexion;
        this.empleadoSucursal = empleadoSucursal;
    }

    public EmpleadoSucursal getEmpleadoSucursal() {
        return empleadoSucursal;
    }

    public void setEmpleadoSucursal(EmpleadoSucursal empleadoSucursal) {
        this.empleadoSucursal = empleadoSucursal;
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
     * Crea el SET para el mètodo conexion
     * @param conexion Almacena la variable de la conexion
     */
    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Creación del metedo INSERTAR, para registrar
     * los datos recopilados de los Empleados.
     * @return empleado Retorna la confirmación
     * de un registro exitoso o erroneo.
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            empleadoSucursal.setId(conexion.insertar("empleado_sucursal",
                    "id_empleado, id_sucursal, fecha_cambio, detalle_cambio, estado",
                    empleadoSucursal.getEmpleado().getId() + ", " + empleadoSucursal.getSucursal().getId()
                    + ", CURRENT_TIMESTAMP, '" + empleadoSucursal.getDetalle() + "', " + empleadoSucursal.isEstado(),
                    "id_empleado_sucursal"));
            return empleadoSucursal.getId();
        }
        return -1;
    }

    /**
     * Metodo que verifica y controla las entidades o registros
     * vacios que se realicen al momento de insertar.
     * @param entity Objeto con la información para
     * la validación correspondiente.
     * @return insertar Objeto con la información.
     */
    @Override
    public int insertar(EmpleadoSucursal entity) {
        this.empleadoSucursal = entity;
        return insertar();
    }

    /**
     * A continuación se crea el método actualizar:
     * @return conexion Objeto con la conexion
     * con los datos correspondientes para su modificación.
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("empleado_sucursal",
                    "id_empleado = " + empleadoSucursal.getEmpleado().getId() + ", id_sucursal = " + empleadoSucursal.getSucursal().getId()
                    + ", fecha_cambio = '" + empleadoSucursal.getFechaCambio() + ", detalle_cambio = '"
                    + empleadoSucursal.getDetalle() + "', estado = " + empleadoSucursal.isEstado(),
                    "id_empleado_sucursal = " + empleadoSucursal.getId());
        }
        return -1;
    }

    /**
     * Metodo que verifica y controla las entidades o registros
     * vacios que se realicen al momento de actualizar.
     * @param entity Objeto que valida campos vacios
     * @return actualizar Objeto que retorna la actualización de o los
     * elementos en la BD.
     */
    @Override
    public int actualizar(EmpleadoSucursal entity) {
        this.empleadoSucursal = entity;
        return actualizar();
    }

    /**
     * Metodo que permite buscar mediante el ID
     * dentro de los registros.
     * @param id Objeto encargado del ID del parametro
     * de busqueda.
     * @return lista Retorna la lista de busqueda
     */
    @Override
    public EmpleadoSucursal buscarPorId(Object id) {
        List<EmpleadoSucursal> lista = buscar("id_empleado_sucursal = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return new EmpleadoSucursal();
    }

    /**
     * Modifica el estado del Empleado, ya sea
     * ha Activado o Desactivado
     */
    public void desactivar() {
        if (conexion.isEstado()) {
            conexion.modificar("empleado_sucursal", "estado = false ",
                    "estado = true AND id_empleado = " + empleadoSucursal.getEmpleado().getId());
        }
    }

    /**
     * Valida que no exista nulo en la busqueda
     * @return buscar envia la lista del resultado
     */
    @Override
    public List<EmpleadoSucursal> Listar() {
        return buscar(null, "fecha_cambio DESC");
    }

    /**
     * Permite buscar al empleado en sucursal usando una sentencia
     * SELECT que es llamada a la base de datos, otorgando los registros
     * necesarios para ejecutrar el método.
     * @param empleado Contiene los datos necesarios en esta caso es usado
     * el ID del empleado
     * @return empleadoSucursal Retorna el empleado dentro de sucursal o el
     * que se encuentre disponible dentro de los registros 
     * de la Base de datos.
     */
    public EmpleadoSucursal buscar(Empleado empleado) {
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("empleado_sucursal",
                                             "id_empleado_sucursal, id_sucursal, fecha_cambio, detalle_cambio",
                                             "estado = true AND id_empleado = " + empleado.getId(), null);
                SucursalDAO sdao = new SucursalDAO();
                while (result.next()) {
                    empleadoSucursal = new EmpleadoSucursal(
                            result.getInt("id_empleado_sucursal"),
                                          empleado, sdao.buscarPorId(result.getInt("id_sucursal")),
                                          result.getDate("fecha_cambio"), true, result.getString("detalle_cambio"));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return empleadoSucursal;
    }

    /**
     * Busca el departamento de acuerdo a su registro
     * @param restricciones Objeto con las restricciones
     * o validaciones de las mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar
     * los resultados. 
     * @return List<EmpleadoSucursal> Muestra la lista.
     */
    private List<EmpleadoSucursal> buscar(@Nullable String restricciones, @Nullable String OrdenarAgrupar) {
        List<EmpleadoSucursal> empleadoSucursals = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("empleado_sucursal", "id_empleado_sucursal, id_empleado, id_sucursal, fecha_cambio, detalle_cambio, estado", restricciones, OrdenarAgrupar);
                EmpleadoDAO edao = new EmpleadoDAO();
                SucursalDAO sdao = new SucursalDAO();
                while (result.next()) {
                    empleadoSucursals.add(new EmpleadoSucursal(
                            result.getInt("id_empleado_sucursal"),
                            edao.buscarPorId(result.getInt("id_empleado")),
                            sdao.buscarPorId(result.getInt("id_sucursal")),
                            result.getDate("fecha_cambio"),
                            result.getBoolean("estado"),
                            result.getString("detalle_cambio")
                    ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return empleadoSucursals;
    }
}
