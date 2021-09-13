/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Empleado;
import com.recursoshumanos.Model.Entidad.Persona;
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
 * para todo lo que tenga que ver con Departamentos.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class EmpleadoDAO implements IDAO<Empleado> {

    private Conexion conexion;
    private Empleado empleado;
    private PersonaDAO personaDAO;

    public EmpleadoDAO() {
        conexion = new Conexion();
        empleado = new Empleado();
        personaDAO = new PersonaDAO(conexion);
    }

    public EmpleadoDAO(Empleado empleado) {
        conexion = new Conexion();
        this.empleado = empleado;
    }

    public EmpleadoDAO(Conexion conexion) {
        this.conexion = conexion;
        empleado = new Empleado();
    }

    public EmpleadoDAO(Empleado empleado, Conexion conexion) {
        this.conexion = conexion;
        this.empleado = empleado;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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
            empleado.setId(conexion.insertar("empleado_bck_rrhh",
                    "id_persona, nombre1, nombre2, apellido1, apellido2, sexo, genero, detalle, fecha_nacimiento, fecha_ingreso, fecha_egreso",
                    empleado.getPersona().getId() + ", '" + empleado.getNombre1() + "', '" + empleado.getNombre2() + "', '" + empleado.getApellido1() 
                    + "', '" + empleado.getApellido2() + "', '" + empleado.getSexo() + "', '" + empleado.getGenero() + "', '" + empleado.getDetalle()
                    + "', '" + empleado.getFechaNacimiento() + "', CURRENT_TIMESTAMP, NULL",
                    "id_empleado"));
            return empleado.getId();
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
    public int insertar(Empleado entity) {
        this.empleado = entity;
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
            return conexion.modificar("empleado_bck_rrhh",
                    "id_persona = " + empleado.getPersona().getId() + ", nombre1 = '" + empleado.getNombre1() + "', nombre2 = '"
                    + empleado.getNombre2() + "', apellido1 = '" + empleado.getApellido1() + "', apellido2 = '" + empleado.getApellido2() + "', sexo = '" 
                    + empleado.getSexo() + "', genero = '" + empleado.getGenero() + "', detalle = '" + empleado.getDetalle() + "', fecha_nacimiento = '" 
                    + empleado.getFechaNacimiento() + "', fecha_ingreso = '" + empleado.getFechaIngreso() + "'",
                    "id_empleado = " + empleado.getId());
        }
        return -1;
    }
    
    /**
     * Modifica el estado del Empleado, ya sea
     * ha Activado o Desactivado
     */
    public void cambiarEstado() {
        if (conexion.isEstado()) {
            conexion.modificar("persona_bck_rrhh",
                               "estado = NOT estado", "id_persona = " + empleado.getPersona().getId());
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
    public int actualizar(Empleado entity) {
        this.empleado = entity;
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
    public Empleado buscarPorId(Object id) {
        List<Empleado> lista = buscar("id_empleado = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return new Empleado();
    }

    /**
     * Valida que no exista nulo en la busqueda
     * @return buscar envia la lista del resultado
     */
    @Override
    public List<Empleado> Listar() {
        return buscar(null, "apellido1, apellido2, nombre1, nombre2  ASC");
    }

    /**
     * Genera un resumen de los datos de los Empleados
     * @return empleados El objeto encargado de los datos a mostrar.
     */
    public List<Empleado> resumen() {
        List<Empleado> empleados = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("empleado_bck_rrhh",
                        "id_empleado, nombre1, nombre2, apellido1, apellido2, fecha_ingreso, fecha_egreso",
                        null, "nombre1, nombre2, apellido1, apellido2 ASC");
                
                while (result.next()) {
                    empleados.add(new Empleado(
                            result.getInt("id_empleado"),
                            new Persona(),
                            result.getString("nombre1"),
                            result.getString("nombre2"),
                            result.getString("apellido1"),
                            result.getString("apellido2"),
                            "","","", null,
                            result.getDate("fecha_ingreso"),
                            result.getDate("fecha_egreso")
                    ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return empleados;
    }
    
    /**
     * Se encarga de los empleados activos, realiza una consulta SQL de
     * SELECT para obtener los datos.
     * @return empleados Objeto encargado de los datos
     */
    public List<Empleado> activos() {
        List<Empleado> empleados = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("empleado_bck_rrhh AS e INNER JOIN public.persona_bck_rrhh AS p ON p.id_persona = e.id_persona",
                                             "id_empleado, e.id_persona, nombre1, nombre2, apellido1, apellido2, sexo, genero, detalle, fecha_nacimiento, fecha_ingreso, fecha_egreso",
                                             "p.estado = true",
                                             "nombre1, nombre2, apellido1, apellido2 ASC");
                while (result.next()) {
                    empleados.add(new Empleado(
                            result.getInt("id_empleado"),
                            personaDAO.buscarPorId(result.getInt("id_persona")),
                            result.getString("nombre1"),
                            result.getString("nombre2"),
                            result.getString("apellido1"),
                            result.getString("apellido2"),
                            result.getString("sexo"),
                            result.getString("genero"),
                            result.getString("detalle"),
                            result.getDate("fecha_nacimiento"),
                            result.getDate("fecha_ingreso"),
                            result.getDate("fecha_egreso")
                    ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return empleados;
    }

    /**
     * Busca el departamento de acuerdo a su registro
     * @param restricciones Objeto con las restricciones
     * o validaciones de las mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar
     * los resultados. 
     * @return List<Empleado> Muestra la lista.
     */
    private List<Empleado> buscar(@Nullable String restricciones, @Nullable String OrdenarAgrupar) {
        List<Empleado> empleados = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("empleado_bck_rrhh", "id_empleado, id_persona, nombre1, nombre2, apellido1, apellido2, sexo, genero, detalle, fecha_nacimiento, fecha_ingreso, fecha_egreso", restricciones, OrdenarAgrupar);
                while (result.next()) {
                    empleados.add(new Empleado(
                            result.getInt("id_empleado"),
                            personaDAO.buscarPorId(result.getInt("id_persona")),
                            result.getString("nombre1"),
                            result.getString("nombre2"),
                            result.getString("apellido1"),
                            result.getString("apellido2"),
                            result.getString("sexo"),
                            result.getString("genero"),
                            result.getString("detalle"),
                            result.getDate("fecha_nacimiento"),
                            result.getDate("fecha_ingreso"),
                            result.getDate("fecha_egreso")
                    ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return empleados;
    }
}