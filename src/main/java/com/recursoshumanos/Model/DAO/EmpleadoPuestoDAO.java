/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Empleado;
import com.recursoshumanos.Model.Entidad.EmpleadoPuesto;
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
 * para todo lo que tenga que ver con Empleados Puesto.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class EmpleadoPuestoDAO implements IDAO<EmpleadoPuesto> {

    private Conexion conexion;
    private EmpleadoPuesto empleadoPuesto;

    public EmpleadoPuestoDAO() {
        conexion = new Conexion();
        empleadoPuesto = new EmpleadoPuesto();
    }

    public EmpleadoPuestoDAO(EmpleadoPuesto empleadoPuesto) {
        conexion = new Conexion();
        this.empleadoPuesto = empleadoPuesto;
    }

    public EmpleadoPuestoDAO(Conexion conexion) {
        this.conexion = conexion;
        empleadoPuesto = new EmpleadoPuesto();
    }

    public EmpleadoPuestoDAO(EmpleadoPuesto empleadoPuesto, Conexion conexion) {
        this.conexion = conexion;
        this.empleadoPuesto = empleadoPuesto;
    }

    public EmpleadoPuesto getEmpleadoPuesto() {
        return empleadoPuesto;
    }

    public void setEmpleadoPuesto(EmpleadoPuesto empleadoPuesto) {
        this.empleadoPuesto = empleadoPuesto;
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
     * los datos recopilados de los Empleados_Puesto.
     * @return empleadoPuesto Retorna la confirmación
     * de un registro exitoso o erroneo.
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            empleadoPuesto.setId(conexion.insertar("empleado_puesto",
                    "id_empleado, id_puesto, id_horario_laboral, fecha_cambio, estado, observaciones",
                    empleadoPuesto.getEmpleado().getId() + ", " + empleadoPuesto.getPuestoLaboral().getId() + ", "
                    + empleadoPuesto.getHorarioLaboral().getId() + ", CURRENT_TIMESTAMP, "
                    + empleadoPuesto.isEstado()+ ", '" + empleadoPuesto.getObservaciones()+ "'",
                    "id_empleado_puesto"));
            return empleadoPuesto.getId();
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
    public int insertar(EmpleadoPuesto entity) {
        this.empleadoPuesto = entity;
        return insertar();
    }

    /**
     * A continuación se crea el método actualizar.
     * @return conexion Objeto con la conexion
     * con los datos correspondientes para su modificación.
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("empleado_puesto",
                    "id_empleado = " + empleadoPuesto.getEmpleado().getId() + ", id_puesto = " + empleadoPuesto.getPuestoLaboral().getId()
                    + ", id_horario_laboral = " + empleadoPuesto.getHorarioLaboral().getId() + ", fecha_cambio = '"
                    + empleadoPuesto.getFechaCambio()+ "', estado = " + empleadoPuesto.isEstado()+ ", observaciones = '" + empleadoPuesto.getObservaciones()+ "'",
                    "id_empleado_puesto = " + empleadoPuesto.getId());
        }
        return -1;
    }
    
    /**
     * Modifica el estado del empelado, ya sea
     * ha Activado o Desactivado
     */
    public void desactivar() {
        if (conexion.isEstado()) {
            conexion.modificar("empleado_puesto", "estado = false ",
                    "estado = true AND id_empleado = " + empleadoPuesto.getEmpleado().getId());
        }
    }

    /**
     * Metodo que verifica y controla las entidades o registros
     * vacios que se realicen al momento de actualizar.
     * @param entity Objeto que valida campos vacios
     * @return actualizar Objeto que retorna la actualización de o los
     * elementos en la BD.
     */
    @Override
    public int actualizar(EmpleadoPuesto entity) {
        this.empleadoPuesto = entity;
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
    public EmpleadoPuesto buscarPorId(Object id) {
        List<EmpleadoPuesto> lista = buscar("id_empleado_puesto = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return new EmpleadoPuesto();
    }

    /**
     * Valida que no exista nulo en la busqueda
     * @return buscar envia la lista del resultado
     */
    @Override
    public List<EmpleadoPuesto> Listar() {
        return buscar(null, "fecha_cambio DESC");
    }
    
    /**
     * Método que permite realizar una busqueda de empleado
     * par poder asignarle ha este un puesto laboral, mediante
     * una consulta SELECT a la base de datos.
     * @param empleado Objeto que contiene los datos
     * @return empleadoPuesto Retorna el empleado con el puesto
     */
    public EmpleadoPuesto buscar(Empleado empleado){
        empleadoPuesto = new EmpleadoPuesto();
        empleadoPuesto.setEmpleado(empleado);
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("empleado_puesto", 
                        "id_empleado_puesto, id_puesto, id_horario_laboral, fecha_cambio, observaciones", 
                        "estado = true AND id_empleado = " + empleado.getId(), null);
                PuestoLaboralDAO pldao = new PuestoLaboralDAO();
                HorarioLaboralDAO hldao = new HorarioLaboralDAO();
                while (result.next()) {
                     empleadoPuesto.setId(result.getInt("id_empleado_puesto"));
                     empleadoPuesto.setPuestoLaboral(pldao.buscarPorId(result.getInt("id_puesto")));
                     empleadoPuesto.setHorarioLaboral(hldao.buscarPorId(result.getInt("id_horario_laboral")));
                     empleadoPuesto.setFechaCambio(result.getDate("fecha_cambio"));
                     empleadoPuesto.setObservaciones(result.getString("observaciones"));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return empleadoPuesto;
    }
    

    /**
     * Busca el departamento de acuerdo a su registro
     * @param restricciones Objeto con las restricciones
     * o validaciones de las mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar
     * los resultados. 
     * @return List<EmpleadoPuesto> Muestra la lista.
     */
    private List<EmpleadoPuesto> buscar(@Nullable String restricciones, @Nullable String OrdenarAgrupar) {
        List<EmpleadoPuesto> empleadoPuestos = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("empleado_puesto", "id_empleado_puesto, id_empleado, id_puesto, id_horario_laboral, fecha_cambio, estado, observaciones", restricciones, OrdenarAgrupar);
                EmpleadoDAO edao = new EmpleadoDAO();
                PuestoLaboralDAO pldao = new PuestoLaboralDAO();
                HorarioLaboralDAO hldao = new HorarioLaboralDAO();
                while (result.next()) {
                    empleadoPuestos.add(new EmpleadoPuesto(
                            result.getInt("id_empleado_puesto"),
                            edao.buscarPorId(result.getInt("id_empleado")),
                            pldao.buscarPorId(result.getInt("id_puesto")),
                            hldao.buscarPorId(result.getInt("id_horario_laboral")),
                            result.getDate("fecha_cambio"),
                            result.getBoolean("estado"),
                            result.getString("observaciones")
                    ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return empleadoPuestos;
    }
}
