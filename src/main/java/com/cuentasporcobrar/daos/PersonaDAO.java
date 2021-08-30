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

public class PersonaDAO implements Serializable {

    Conexion conex;
    Persona persona;
    ResultSet result;
    List<Persona> lista_Personas;

    public PersonaDAO(Conexion conex, Persona persona, ResultSet result) {
        this.conex = conex;
        this.persona = persona;
        this.result = result;
    }

    public PersonaDAO() {
        conex = new Conexion();
    }

    public PersonaDAO(Persona persona) {
        conex = new Conexion();
        this.persona = persona;
    }

    public List<Persona> obtenerTodosLosClientes() {
        lista_Personas = new ArrayList<>();
        if (conex.isEstado()) {
            try {
                String sentencia = "Select * from Mostrar_Todos_los_Clientes() "
                        + "where estado_r='Activo'";
                result = conex.ejecutarConsulta(sentencia);
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
                conex.cerrarConexion();
            }
        }
        return lista_Personas;
    }
    
    public List<Persona> obtenerClientesInactivos() {
        lista_Personas = new ArrayList<>();
        if (conex.isEstado()) {
            try {
                String sentencia = "Select * from Mostrar_Todos_los_Clientes() "
                        + "where estado_r='Inactivo'";
                result = conex.ejecutarConsulta(sentencia);
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
                conex.cerrarConexion();
            }
        }
        return lista_Personas;
    }
    
    public List<Persona> obtenerNombresClientes() {
        lista_Personas = new ArrayList<>();
        if (conex.isEstado()) {
            try {
                String sentencia = "Select id, razon_nombres from Mostrar_Todos_los_Clientes()"
                        + " Where estado_r='Activo'";
                result = conex.ejecutarConsulta(sentencia);
                while (result.next()) {
                    lista_Personas.add(new Persona(result.getInt("id"),
                            result.getString("razon_nombres")));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                lista_Personas.add(new Persona(-1, "-"));
            } finally {
                conex.cerrarConexion();
            }
        }
        return lista_Personas;
    }
    
    public List<Persona> obtenerNombresIdentiClientes() {
        lista_Personas = new ArrayList<>();
        if (conex.isEstado()) {
            try {
                String sentencia = "Select identificacion, razon_nombres from "
                        + "Mostrar_Todos_los_Clientes()"
                        + " Where estado_r='Activo'";
                result = conex.ejecutarConsulta(sentencia);
                while (result.next()) {
                    lista_Personas.add(new Persona(result.getInt("identificacion"),
                            result.getString("razon_nombres")));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                lista_Personas.add(new Persona(-1, "-"));
            } finally {
                conex.cerrarConexion();
            }
        }
        return lista_Personas;
    }

    public Persona obtenerNombreClienteXIdentificacion(String identificacion) {
        persona = new Persona();
        if (conex.isEstado()) {
            try {
                String sentencia = "Select id,identificacion, razon_nombres "
                        + "from Mostrar_Todos_los_Clientes() \n"
                        + "Where estado_r='Activo' and identificacion='" + identificacion + "'";
                result = conex.ejecutarConsulta(sentencia);
                while (result.next()) {
                    persona = new Persona(result.getString("identificacion"), result.getInt("id"),
                            result.getString("razon_nombres"));

                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                persona = new Persona("", -1,
                        "");
            } finally {
                conex.cerrarConexion();
            }
        }
        return persona;
    }

    public int deshabilitarCliente(int id) {
        try {
            String sentencia = "select Anular_Cliente(" + id + ")";
            if (conex.isEstado()) {
                return conex.ejecutarProcedimiento(sentencia);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            conex.cerrarConexion();
        }
        return -1;
    }

    public int habilitarCliente(int id) {
        try {
            String sentencia = "select activar_cliente(" + id + ")";
            if (conex.isEstado()) {
                return conex.ejecutarProcedimiento(sentencia);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            conex.cerrarConexion();
        }
        return -1;
    }

    public String identificar_cliente(int idCliente) {
        String tipo = "0";

        try {
            String sentencia = "Select Case when id_persona_juridica > 0 then  CAST('J' AS varchar)\n"
                    + "ELSE  CAST('N' AS varchar) END as tipo \n"
                    + "from clientes where idcliente=" + idCliente;
            if (conex.isEstado()) {
                result = conex.ejecutarConsulta(sentencia);
                result.next();
                tipo = result.getString("tipo");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return tipo;
        } finally {
            conex.cerrarConexion();
        }
        return tipo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

}
