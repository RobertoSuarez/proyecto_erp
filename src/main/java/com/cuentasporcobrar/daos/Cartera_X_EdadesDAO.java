/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporcobrar.daos;

import com.cuentasporcobrar.models.Cartera_X_Edades;
import com.global.config.Conexion;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Una clase Cartera_X_EdadesDAO que se va a encargar de la lógica de
 * negocio que lleva consigo tener acceso a la BD y al modelo.
 *
 * @author Alexander Vega, Andy Ninasunta.
 */

public class Cartera_X_EdadesDAO implements Serializable {

    //Declaro una lista_CarteraxEdades.
    List<Cartera_X_Edades> lista_CarteraxEdades;
    
    //Declaro una conexión.
    Conexion conex;
    
    //Declaro una clase Cartera_X_Edades.
    Cartera_X_Edades cartera_X_Edades;
    
    //Declaro un ResultSet
    ResultSet result;

    /**
     * Constructor que inicializa solamente la conexión.
     */
    public Cartera_X_EdadesDAO() {
        conex = new Conexion();
    }

    /**
     * Constructor que recibe un objeto Cartera_X_Edades y que inicializa la
     * conexión.
     *
     * @param cartera_X_Edades Se obtiene los campos y contructores declarados.
     */
    public Cartera_X_EdadesDAO(Cartera_X_Edades cartera_X_Edades) {
        conex = new Conexion();
        this.cartera_X_Edades = cartera_X_Edades;
    }

    /**
     * Constructor que obtiene un objeto lista_CarteraxEdades, una
     * Cartera_X_EdadesDAO, la Conexion y ResultSet.
     *
     * @param conex Obtiene la conexión a la base de datos.
     * @param lista_CarteraxEdades Se carga el listado de cartera x edades de
     * vencimiento.
     * @param cartera_X_Edades Se obtiene los campos y constructores declarados.
     * @param result Se realiza la lectura a las consultas que se realizan.
     */
    public Cartera_X_EdadesDAO(List<Cartera_X_Edades> lista_CarteraxEdades, 
            Conexion conex, Cartera_X_Edades cartera_X_Edades, ResultSet result) {
        this.lista_CarteraxEdades = lista_CarteraxEdades;
        this.conex = conex;
        this.cartera_X_Edades = cartera_X_Edades;
        this.result = result;
    }

    /**
     * Método que obtiene un listado de todos los saldos.
     * @return El listado de todos los saldos que se encuentren registrado en
     * la base de datos.
     */
    public List<Cartera_X_Edades> obtenerCarteraxEdades() {
        
        //Se instancia la lista_CarteraxEdades.
        lista_CarteraxEdades = new ArrayList<>();

        //Se valida que el estado de la conexion sea true(conexion abierta).
        if (conex.isEstado()) {
            try {
                String sentencia = "select*from obtener_cartera_vencidaxedades()";
                result = conex.ejecutarConsulta(sentencia);

                 //Instanciamos la clase AbonoDAO.        
                AbonoDAO abonoDAO= new AbonoDAO();
                
                //Se realiza el llenado de la lista_CarteraxEdades.
                while (result.next()) {

                    /*Concatenamos la sucursal, el punto de emision y el numero 
                      de la factura. */
                    String numFact =abonoDAO.obtenerConcatenacionFactura(result.getInt("id_sucursal_r"),
                            result.getInt("puntoemision_r"), result.getInt("secuencia_r"));
                    
                    lista_CarteraxEdades.add(new Cartera_X_Edades(
                            result.getObject("fechaemision_r", LocalDate.class),
                            result.getInt("documento_r"),
                            result.getInt("idcliente_i"),
                            result.getString("nombres"),
                            result.getInt("diasdecredito_"),
                            result.getObject("fechavencimiento_r", LocalDate.class),
                            result.getDouble("valortotaldoc_r"),
                            result.getDouble("valoracobrar_r"),
                            result.getInt("diasmora_r"),
                            result.getDouble("vencido30dias_r"),
                            result.getDouble("vencido60dias_r"),
                            result.getDouble("vencidomas60dias_r"),
                    numFact));

                }

            } catch (SQLException ex) {
                /*Enviamos su respectivo mensaje de error a su ves una lista 
                    con valores incorrectos.*/
                System.out.println(ex.getMessage());
                lista_CarteraxEdades.add(new Cartera_X_Edades(
                            null,
                            -1,
                            -1,
                            "",
                            -1,
                            null,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1,
                ""));
                
            } finally {

                //Se cierra la conéxión.
                conex.cerrarConexion();

            }
        }
        return lista_CarteraxEdades;
    }
    
    /**
     * Método que obtiene las sumas totales de los saldos que mantienen
     * el cliente por medio de su id.
     * @param idCliente El idCliente que es único en la base de datos.
     * @return El listado de las sumas totales de los saldos que se 
     * encuentren registrado en la base de datos.
     */
    public List<Cartera_X_Edades> obtenerSumCarteraxEdades(int idCliente) {
        
        //Se instancia la lista_CarteraxEdades.
        lista_CarteraxEdades = new ArrayList<>();

        //Se valida que el estado de la conexion sea true(conexion abierta).
        if (conex.isEstado()) {
            try {
                String sentencia = "select*from obtener_sum_carteraxedades("+idCliente+")";
                
                //Se ejecuta la sentencia.
                result = conex.ejecutarConsulta(sentencia);

                //Se realiza el llenado de la lista_CarteraxEdades.
                while (result.next()) {

                    lista_CarteraxEdades.add(new Cartera_X_Edades(
                            result.getDouble("totalvalordoc_r"),
                            result.getDouble("totalvalorcobrar_r"),
                            result.getDouble("totalvencido30_r"),
                            result.getDouble("totalvencido60_r"),
                            result.getDouble("totalvencidomas60_r")));

                }

            } catch (SQLException ex) {
                /*Enviamos su respectivo mensaje de error a su ves una lista 
                    con valores incorrectos.*/
                System.out.println(ex.getMessage());
                lista_CarteraxEdades.add(new Cartera_X_Edades(
                            -1,
                            -1,
                            -1,
                            -1,
                            -1));
                
            } finally {

                //Se cierra la conexión.
                conex.cerrarConexion();

            }
        }
        return lista_CarteraxEdades;
    }
    
}
