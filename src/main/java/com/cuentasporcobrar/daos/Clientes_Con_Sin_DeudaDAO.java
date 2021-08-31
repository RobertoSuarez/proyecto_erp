/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.daos;

import com.cuentasporcobrar.models.Clientes_Con_Sin_Deuda;
import com.global.config.Conexion;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Una clase Clientes_Con_Sin_DeudaDAO que se va a encargar de la lógica de
 * negocio que lleva consigo tener acceso a la BD y al modelo.
 *
 * @author Alexander Vega, Andy Ninasunta.
 */

public class Clientes_Con_Sin_DeudaDAO implements Serializable {

    //Declaro una lista_Clientes_Con_Sin_Deudas.
    List<Clientes_Con_Sin_Deuda> lista_Clientes_Con_Sin_Deudas;
    
    //Declaro una lista_Clientes_Con_Deudas.
    List<Clientes_Con_Sin_Deuda> lista_Clientes_Con_Deudas;
    
    //Declaro una lista_Clientes_Sin_Deudas.
    List<Clientes_Con_Sin_Deuda> lista_Clientes_Sin_Deudas;
    
    //Declaro la clase conexion
    Conexion conex;
    
    //Declaro la clase Clientes_Con_Sin_Deuda.
    Clientes_Con_Sin_Deuda cliente_Con_Sin_Deuda;
    
    //Declaro un ResultSet.
    ResultSet result;

    /**
     * Constructor que inicializa solamente la conexión.
     */
    public Clientes_Con_Sin_DeudaDAO() {
        conex = new Conexion();
    }

    /**
     * Constructor que recibe un objeto Clientes_Con_Sin_Deuda y que inicializa 
     * la conexión.
     *
     * @param cliente_Con_Sin_Deuda Se obtiene los campos y contructores 
     * declarados.
     */
    public Clientes_Con_Sin_DeudaDAO(Clientes_Con_Sin_Deuda cliente_Con_Sin_Deuda) {
        conex = new Conexion();
        this.cliente_Con_Sin_Deuda = cliente_Con_Sin_Deuda;
    }

    /**
     * Constructor que obtiene un objeto lista_Clientes_Con_Sin_Deudas, una
     * cliente_Con_Sin_Deuda, la Conexion y ResultSet.
     *
     * @param conex Obtiene la conexión a la base de datos.
     * @param cliente_Con_Sin_Deuda Se carga el listado de cliente_Con_Sin_Deuda.
     * @param lista_Clientes_Con_Sin_Deudas Se obtiene los campos y 
     * constructores declarados.
     * @param result Se realiza la lectura a las consultas que se realizan.
     */
    public Clientes_Con_Sin_DeudaDAO(
                    List<Clientes_Con_Sin_Deuda> lista_Clientes_Con_Sin_Deudas, 
                    Conexion conex, Clientes_Con_Sin_Deuda cliente_Con_Sin_Deuda, 
                    ResultSet result) {
        
        this.lista_Clientes_Con_Sin_Deudas = lista_Clientes_Con_Sin_Deudas;
        this.conex = conex;
        this.cliente_Con_Sin_Deuda = cliente_Con_Sin_Deuda;
        this.result = result;
    }

    /**
     * Método que obtiene un listado de todos los clientes con y sin deudas.
     * @return El listado de todos los cliente con y sin deudas que se
     * encuentren registrado en la base de datos.
     */
    public List<Clientes_Con_Sin_Deuda> obtenerClientesConSinDeudas() {
        
        //Se instancia la lista_Clientes_Con_Sin_Deudas.
        lista_Clientes_Con_Sin_Deudas = new ArrayList<>();

        //Se valida que el estado de la conexion sea true(conexion abierta).
        if (conex.isEstado()) {
            try {
                String sentencia = "select*from obtener_clientes_con_y_sin_deudas()";
                result = conex.ejecutarConsulta(sentencia);

                //Se realiza el llenado de la lista_Clientes_Con_Sin_Deudas.
                while (result.next()) {
                    lista_Clientes_Con_Sin_Deudas.add(new Clientes_Con_Sin_Deuda(
                            result.getDouble("valorpendiente_r"),
                            result.getInt("idcliente_r"),
                            result.getString("identificacion_r"),
                            result.getString("razon_nombres_r"),
                            result.getString("direccion"),
                            result.getString("tlf1_r"),
                            result.getString("tlf2_r"),
                            result.getString("correo1")));

                }

            } catch (SQLException ex) {

                /*Enviamos su respectivo mensaje de error a su ves una lista 
                    con valores incorrectos.*/
                System.out.println(ex.getMessage());
                lista_Clientes_Con_Sin_Deudas.add(new Clientes_Con_Sin_Deuda(
                        -1,
                        -1,
                        "",
                        "",
                        "",
                        "",
                        "",
                        ""));

            } finally {

                //Se cierra la conexion.
                conex.cerrarConexion();

            }
        }
        return lista_Clientes_Con_Sin_Deudas;
    }

    /**
     * Método que obtiene un listado de todos los cliente con deudas.
     * @return El listado de todos los clientes con deudas que se encuentren 
     * registrado en la base de datos.
     */
    public List<Clientes_Con_Sin_Deuda> obtenerClientesConDeudas() {
        
        //Se instancia la lista_Clientes_Con_Deudas.
        lista_Clientes_Con_Deudas = new ArrayList<>();

        //Se valida que el estado de la conexion sea true(conexion abierta).
        if (conex.isEstado()) {
            try {

                /* Se obtiene una TABLA con todos los cliente que deben y no 
                deben*/
                String sentencia = "select*from obtener_cliente_con_deudas()";
                result = conex.ejecutarConsulta(sentencia);

                while (result.next()) {

                    lista_Clientes_Con_Deudas.add(new Clientes_Con_Sin_Deuda(
                            result.getDouble("valorpendiente_r"),
                            result.getInt("idcliente_r"),
                            result.getString("identificacion_r"),
                            result.getString("razon_nombres_r"),
                            result.getString("direccion"),
                            result.getString("tlf1_r"),
                            result.getString("tlf2_r"),
                            result.getString("correo1")));

                }

            } catch (SQLException ex) {

                /*Enviamos su respectivo mensaje de error a su ves una lista 
                    con valores incorrectos.*/
                System.out.println(ex.getMessage());
                lista_Clientes_Con_Deudas.add(new Clientes_Con_Sin_Deuda(
                        -1,
                        -1,
                        "",
                        "",
                        "",
                        "",
                        "",
                        ""));

            } finally {

                conex.cerrarConexion();

            }
        }
        return lista_Clientes_Con_Deudas;
    }

    //Funcion para enlistar los cliente condeuda
    public List<Clientes_Con_Sin_Deuda> obtenerClientesSinDeudas() {
        lista_Clientes_Sin_Deudas = new ArrayList<>();

        //verificamos la conexión
        if (conex.isEstado()) {
            try {

                /* Se obtiene una TABLA con todos los cliente que deben y no 
                deben*/
                String sentencia = "select*from obtener_cliente_sin_deudas()";
                result = conex.ejecutarConsulta(sentencia);

                while (result.next()) {

                    lista_Clientes_Sin_Deudas.add(new Clientes_Con_Sin_Deuda(
                            result.getDouble("valorpendiente_r"),
                            result.getInt("idcliente_r"),
                            result.getString("identificacion_r"),
                            result.getString("razon_nombres_r"),
                            result.getString("direccion"),
                            result.getString("tlf1_r"),
                            result.getString("tlf2_r"),
                            result.getString("correo1")));

                }

            } catch (SQLException ex) {

                /*Enviamos su respectivo mensaje de error a su ves una lista 
                    con valores incorrectos.*/
                System.out.println(ex.getMessage());
                lista_Clientes_Sin_Deudas.add(new Clientes_Con_Sin_Deuda(
                        -1,
                        -1,
                        "",
                        "",
                        "",
                        "",
                        "",
                        ""));

            } finally {

                conex.cerrarConexion();

            }
        }
        return lista_Clientes_Sin_Deudas;
    }
}
