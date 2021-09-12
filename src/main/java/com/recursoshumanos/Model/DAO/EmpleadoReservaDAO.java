/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Empleado;
import com.recursoshumanos.Model.Entidad.EmpleadoReserva;
import com.recursoshumanos.Model.Interfaces.IDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/**
 * 
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 * 
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades
 * para todo lo que tenga que ver con Empleado Reserva.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class EmpleadoReservaDAO implements IDAO<EmpleadoReserva> {

    private Conexion conexion;
    private EmpleadoReserva empleadoReserva;

    public EmpleadoReservaDAO() {
        conexion = new Conexion();
        empleadoReserva = new EmpleadoReserva();
    }

    public EmpleadoReservaDAO(EmpleadoReserva empleadoReserva) {
        conexion = new Conexion();
        this.empleadoReserva = empleadoReserva;
    }

    public EmpleadoReservaDAO(Conexion conexion) {
        this.conexion = conexion;
        empleadoReserva = new EmpleadoReserva();
    }

    public EmpleadoReservaDAO(EmpleadoReserva empleadoReserva, Conexion conexion) {
        this.conexion = conexion;
        this.empleadoReserva = empleadoReserva;
    }

    public EmpleadoReserva getEmpleadoReserva() {
        return empleadoReserva;
    }

    public void setEmpleadoReserva(EmpleadoReserva empleadoReserva) {
        this.empleadoReserva = empleadoReserva;
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
            conexion.insertar("empleado_reserva",
                    "id_empleado, fecha_solicitud, forma_pago, detalle",
                    empleadoReserva.getEmpleado().getId() + ", '" + empleadoReserva.getFechaSolicitud()+ "', " + 
                    empleadoReserva.getFormaPago() + ", '" + empleadoReserva.getDetalle() + "'" ,
                    "id_empleado");
            return empleadoReserva.getEmpleado().getId();
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
    public int insertar(EmpleadoReserva entity) {
        this.empleadoReserva = entity;
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
            return conexion.modificar("empleado_reserva",
                    "fecha_solicitud = '" + empleadoReserva.getFechaSolicitud() + "', detalle = '" + empleadoReserva.getDetalle() 
                    + "', forma_pago = " + empleadoReserva.getFormaPago(),
                    "id_empleado = " + empleadoReserva.getEmpleado().getId());
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
    public int actualizar(EmpleadoReserva entity) {
        this.empleadoReserva = entity;
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
    public EmpleadoReserva buscarPorId(Object id) {
        List<EmpleadoReserva> lista = buscar("id_empleado = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return new EmpleadoReserva();
    }

    /**
     * Valida que no exista nulo en la busqueda
     * @return buscar envia la lista del resultado
     */
    @Override
    public List<EmpleadoReserva> Listar() {
        return buscar(null, "fecha_solicitud DESC");
    }
    
    /**
     * Permite buscar al empleado en reserva usando una sentencia
     * SELECT que es llamada a la base de datos, otorgando los registros
     * necesarios para ejecutrar el método.
     * @param empleado Contiene los datos necesarios en esta caso es usado
     * el ID del empleado
     * @return empleadoReserva Retorna el empleado dentro de reserva o el
     * que se encuentre disponible dentro de los registros 
     * de la Base de datos.
     */
    public EmpleadoReserva buscar(Empleado empleado) {
        empleadoReserva = new EmpleadoReserva();
        empleadoReserva.setEmpleado(empleado);
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("empleado_reserva", "fecha_solicitud, forma_pago, detalle",
                                             "id_empleado = " + empleado.getId(), null);
                while (result.next()) {
                    empleadoReserva.setFechaSolicitud(result.getDate("fecha_solicitud"));
                    empleadoReserva.setFormaPago(result.getFloat("forma_pago"));
                    empleadoReserva.setDetalle(result.getString("detalle"));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return empleadoReserva;
    }

    /**
     * Busca el departamento de acuerdo a su registro
     * @param restricciones Objeto con las restricciones
     * o validaciones de las mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar
     * los resultados. 
     * @return List<EmpleadoReserva> Muestra la lista.
     */
    private List<EmpleadoReserva> buscar(@Nullable String restricciones, @Nullable String OrdenarAgrupar) {
        List<EmpleadoReserva> empleadoReservas = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("empleado_reserva", "id_empleado, fecha_solicitud, forma_pago, detalle", restricciones, OrdenarAgrupar);
                EmpleadoDAO edao = new EmpleadoDAO();
                while (result.next()) {
                    empleadoReservas.add(new EmpleadoReserva(
                            edao.buscarPorId(result.getInt("id_empleado")),
                            result.getDate("fecha_actualizacion"),
                            result.getFloat("forma_pago"),
                            result.getString("detalle")
                    ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return empleadoReservas;
    }   
}