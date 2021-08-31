/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.daos;

import com.cuentasporcobrar.models.Persona;
import com.global.config.Conexion;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PersonaDAO es donde se realiza las consultas a la Base de Datos.
 * @author Alexander Vega, Andy Ninasunta.
 */

public class PersonaDAO implements Serializable {

    //Declaro la clase Conexion.
    Conexion conex;

    //Declaro la clase Persona.
    Persona persona;

    //Declaro un ResultSet para realizar la consultas a la Base de datos.
    ResultSet result;

    //Declaro un lista_Personas.
    List<Persona> lista_Personas;

    /**
     * Constructor que recibe la conexion, el objeto persona y el resulset.
     *
     * @param conex Obtiene la conexion a la base de datos.
     * @param persona Instancia al objeto persona.
     * @param result Instancia al objeto resultset para lectura de sentencias.
     */
    public PersonaDAO(Conexion conex, Persona persona, ResultSet result) {
        this.conex = conex;
        this.persona = persona;
        this.result = result;
    }

    /**
     * Constructo que obtiene la conexion.
     */
    public PersonaDAO() {
        conex = new Conexion();
    }

    /**
     * Constructor que obtiene la conexion e instancia el objeto persona.
     * @param persona Instancia al objeto persona.
     */
    public PersonaDAO(Persona persona) {
        conex = new Conexion();
        this.persona = persona;
    }

    /**
     * Método que obtiene un listado de todos los clientes activos.
     * @return El listado de todos los clientes que se encuentren registrado en
     * la base de datos.
     */
    public List<Persona> obtenerTodosLosClientes() {

        //Se instancia la lista_Personas.
        lista_Personas = new ArrayList<>();

        //Se valida que el estado de la conexion sea true(conexion abierta).
        if (conex.isEstado()) {
            try {
                String sentencia = "Select * from Mostrar_Todos_los_Clientes() "
                        + "where estado_r='Activo'";

                //Se ejecuta la sentencia.
                result = conex.ejecutarConsulta(sentencia);

                //Se realiza el llenado de la lista_Personas.
                while (result.next()) {
                    lista_Personas.add(new Persona(result.getInt("id"),
                            result.getString("identificacion"),
                            result.getString("tipo_identificacion"),
                            result.getString("razon_nombres"),
                            result.getString("direccion_r"),
                            result.getString("tlf1"),
                            result.getString("tlf2"),
                            result.getString("correo1_r"),
                            result.getString("tipo_cliente_r"),
                            result.getString("estado_r")));

                }
            } catch (SQLException ex) {
                lista_Personas.add(new Persona(-1, "-", "-", "-", "-", "-", "-", "-", "-", "-"));
            } finally {

                //Se realiza un cierre de conexion de la base de datos.
                conex.cerrarConexion();
            }
        }
        return lista_Personas;
    }

    /**
     * Método que obtiene un listado de todos los clientes inactivos.
     * @return El listado de todos los clientes que se encuentren registrado en
     * la base de datos.
     */
    public List<Persona> obtenerClientesInactivos() {

        //Se instancia la lista_Personas.
        lista_Personas = new ArrayList<>();

        //Se valida que el estado de la conexion sea true(conexion abierta).
        if (conex.isEstado()) {
            try {
                String sentencia = "Select * from Mostrar_Todos_los_Clientes() "
                        + "where estado_r='Inactivo'";

                //Se ejecuta la sentencia.
                result = conex.ejecutarConsulta(sentencia);

                //Se realiza el llenado de la lista_Personas.
                while (result.next()) {
                    lista_Personas.add(new Persona(result.getInt("id"),
                            result.getString("identificacion"),
                            result.getString("tipo_identificacion"),
                            result.getString("razon_nombres"),
                            result.getString("direccion_r"),
                            result.getString("tlf1"),
                            result.getString("tlf2"),
                            result.getString("correo1_r"),
                            result.getString("tipo_cliente_r"),
                            result.getString("estado_r")));

                }
            } catch (SQLException ex) {
                lista_Personas.add(new Persona(-1, "-", "-", "-", "-", "-", "-", "-", "-", "-"));
            } finally {

                //Se realiza un cierre de conexion de la base de datos.
                conex.cerrarConexion();
            }
        }
        return lista_Personas;
    }

    /**
     * Método que obtiene un listado de los nombres de todos los clientes.
     * @return El listado de todos los nombre de los clientes.
     */
    public List<Persona> obtenerNombresClientes() {

        //Se instancia la lista_Personas.
        lista_Personas = new ArrayList<>();

        //Se valida que el estado de la conexion sea true(conexion abierta).
        if (conex.isEstado()) {
            try {
                String sentencia = "Select id, razon_nombres from Mostrar_Todos_los_Clientes()"
                        + " Where estado_r='Activo'";

                //Se ejecuta la sentencia.
                result = conex.ejecutarConsulta(sentencia);

                //Se realiza el llenado de la lista_Personas.
                while (result.next()) {
                    lista_Personas.add(new Persona(result.getInt("id"),
                            result.getString("razon_nombres")));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                lista_Personas.add(new Persona(-1, "-"));
            } finally {

                //Se realiza un cierre de conexion de la base de datos.
                conex.cerrarConexion();
            }
        }
        return lista_Personas;
    }

    /**
     * Método que obtiene un listado de los nombres e identificación de 
     * todos los clientes.
     * @return El listado de todos los nombre de los clientes y su 
     * respectiva identificación.
     */
    public List<Persona> obtenerNombresIdentiClientes() {

        //Se instancia la lista_Personas.
        lista_Personas = new ArrayList<>();

        //Se valida que el estado de la conexion sea true(conexion abierta).
        if (conex.isEstado()) {
            try {
                String sentencia = "Select identificacion, razon_nombres from "
                        + "Mostrar_Todos_los_Clientes()"
                        + " Where estado_r='Activo'";

                //Se ejecuta la sentencia.
                result = conex.ejecutarConsulta(sentencia);

                //Se realiza el llenado de la lista_Personas.
                while (result.next()) {
                    lista_Personas.add(new Persona(result.getInt("identificacion"),
                            result.getString("razon_nombres")));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                lista_Personas.add(new Persona(-1, "-"));
            } finally {

                //Se realiza un cierre de conexion de la base de datos.
                conex.cerrarConexion();
            }
        }
        return lista_Personas;
    }

    /**
     * Método que obtiene el nombre de un cliente por medio de su identificación.
     * @param identificacion Obtiene el nombre del cliente a buscar.
     * @return El nombre e identificación del cliente consultado.
     */
    public Persona obtenerNombreClienteXIdentificacion(String identificacion) {
        //Se instancia la clase Persona
        persona = new Persona();

        //Se valida que el estado de la conexion sea true(conexion abierta).
        if (conex.isEstado()) {
            try {
                String sentencia = "Select id,identificacion, razon_nombres "
                        + "from Mostrar_Todos_los_Clientes() \n"
                        + "Where estado_r='Activo' and identificacion='" + identificacion + "'";

                //Se ejecuta la sentencia.
                result = conex.ejecutarConsulta(sentencia);

                //Se realiza el llenado de la lista_Personas.
                while (result.next()) {
                    persona = new Persona(result.getString("identificacion"), result.getInt("id"),
                            result.getString("razon_nombres"));

                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                persona = new Persona("", -1,
                        "");
            } finally {

                //Se realiza un cierre de conexion de la base de datos.
                conex.cerrarConexion();
            }
        }
        return persona;
    }

    /**
     * Método que realiza la inactivación del cliente por medio de su id.
     * @param id La identificación del cliente al que se desea inactivar.
     * @return El retorno es 1 o -1.
     */
    public int deshabilitarCliente(int id) {
        try {
            String sentencia = "select Anular_Cliente(" + id + ")";

            //Se valida que el estado de la conexion sea true(conexion abierta). 
            if (conex.isEstado()) {
                return conex.ejecutarProcedimiento(sentencia);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {

            //Se realiza un cierre de conexion de la base de datos.
            conex.cerrarConexion();
        }
        return -1;
    }

    /**
     * Método que realiza la activación del cliente por medio de su id.
     * @param id La identificación del cliente al que se desea activar.
     * @return El retorno es 1 o -1.
     */
    public int habilitarCliente(int id) {
        try {
            String sentencia = "select activar_cliente(" + id + ")";
            if (conex.isEstado()) {
                return conex.ejecutarProcedimiento(sentencia);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {

            //Se realiza un cierre de conexion de la base de datos.
            conex.cerrarConexion();
        }
        return -1;
    }

    /**
     * Método que indica que tipo de cliente es (natural o juridico) por medio de su id.
     * @param idCliente La identificación del cliente al que se desea obtener.
     * @return El retorno es N (Natural) o J (Juridica).
     */
    public String identificar_cliente(int idCliente) {
        String tipo = "0";

        try {
            String sentencia = "Select Case when id_persona_juridica > 0 then  CAST('J' AS varchar)\n"
                    + "ELSE  CAST('N' AS varchar) END as tipo \n"
                    + "from clientes where idcliente=" + idCliente;
            
            //Se valida que el estado de la conexion sea true(conexion abierta).
            if (conex.isEstado()) {
                result = conex.ejecutarConsulta(sentencia);
                result.next();
                tipo = result.getString("tipo");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return tipo;
        } finally {

            //Se realiza un cierre de conexion de la base de datos.
            conex.cerrarConexion();
        }
        return tipo;
    }

    //Getter y Setter de la clase Persona.
    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

}
