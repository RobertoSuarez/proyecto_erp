
package com.cuentasporcobrar.daos;

import com.cuentasporcobrar.models.Estado_De_Cuenta;
import com.global.config.Conexion;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Clase tipo DAO del Estado de Cuenta que proporciona funcionalidades
 * para poder realizar un reporte.
 * @author Andy Ninasunta, Alexander Vega
 */
public class Estado_De_CuentaDAO implements Serializable {
    
    List<Estado_De_Cuenta> lista_Estado_de_Cuenta;
    Conexion conex;
    Estado_De_Cuenta estado_de_Cuenta;
    ResultSet result;
    
    /**
     * Constructor sin parámetros, para iniciar una conexion.
     */
    public Estado_De_CuentaDAO() {
        conex = new Conexion();
    }
    
    /**
     * Constructor que recibe el objeto Estado_De_Cuenta e inicia una nueva conexion.
     * @param estado_de_Cuenta Objeto con un estado de cuenta.
     */
    public Estado_De_CuentaDAO(Estado_De_Cuenta estado_de_Cuenta) {
        conex = new Conexion();
        this.estado_de_Cuenta = estado_de_Cuenta;
    }

    /**
     * Método para enlistar el estado de cuenta de todos los clientes.
     * @return List<Estado_De_Cuenta> Lista con el estado de cuenta general.
     */
    public List<Estado_De_Cuenta> obtenerTodosLosEstadosCuenta() {
        lista_Estado_de_Cuenta = new ArrayList<>();

        //verificamos la conexion
        if (conex.isEstado()) {
            try {
                /*Se obtiene una tabla con el estado de cuenta general de 
                todos los clientes en el siguiente orden: 
                idVenta/factura, fecha de credito, total facturas, total abonos,
                valor pendiente
                 */
                String sentencia = "select*from obtener_estado_cuenta_general()";
                result = conex.ejecutarConsulta(sentencia);
                
                //Instanciamos la clase AbonoDAO.        
                AbonoDAO abonoDAO= new AbonoDAO();
                
                while (result.next()) {
                    
                     //Concatenamos la sucursal, el punto de emision y el numero de la factura
                    String numFact =abonoDAO.obtenerConcatenacionFactura(result.getInt("id_sucursal_r"),
                            result.getInt("puntoemision_r"), result.getInt("secuencia_r"));
                    
                    
                    lista_Estado_de_Cuenta.add(new Estado_De_Cuenta(
                            result.getInt("idventa_r"),
                            result.getObject("fechacredito_r", LocalDate.class),
                            result.getDouble("totalfactura_r"),
                            result.getDouble("totalabonos_r"),
                            result.getDouble("valorpendiente_r"),numFact));
                }
                
            } catch (SQLException ex) {
                /*Enviamos su respectivo mensaje de error a su ves una lista 
                    con valores incorrectos.*/
                System.out.println(ex.getMessage());
                lista_Estado_de_Cuenta.add(new Estado_De_Cuenta(
                            -1,
                            null,
                            -1,
                            -1,
                            -1,
                ""));
                
            }finally {

                conex.cerrarConexion();

            }
        }
        return lista_Estado_de_Cuenta;
    }

    /**
     * Método para enlistar el estado de cuenta general de un cliente.
     * @param idCliente ID única de un cliente.
     * @return List<Estado_De_Cuenta> Lista con el estado de cuenta de un cliente.
     */
    public List<Estado_De_Cuenta> obtenerEstadosCuentaDeCliente(int idCliente) {
        lista_Estado_de_Cuenta = new ArrayList<>();

        //verificamos la conexion
        if (conex.isEstado()) {
            try {
                /*Se obtiene una tabla con el estado de cuenta del cliente 
                 en el siguiente orden: 
                idVenta/factura, fecha de credito, total facturas, total abonos,
                valor pendiente
                 */
                String sentencia = "select*from obtener_estado_cuenta_cliente("+idCliente+")";
                result = conex.ejecutarConsulta(sentencia);
                
                 //Instanciamos la clase AbonoDAO.        
                AbonoDAO abonoDAO= new AbonoDAO();
                
                while (result.next()) {
                    
                    
                     //Concatenamos la sucursal, el punto de emision y el numero de la factura
                    String numFact =abonoDAO.obtenerConcatenacionFactura(result.getInt("id_sucursal_r"),
                            result.getInt("puntoemision_r"), result.getInt("secuencia_r"));
                    
                    lista_Estado_de_Cuenta.add(new Estado_De_Cuenta(
                            result.getInt("idventa_r"),
                            result.getObject("fechacredito_r", LocalDate.class),
                            result.getDouble("totalfactura_r"),
                            result.getDouble("totalabonos_r"),
                            result.getDouble("valorpendiente_r"),
                            numFact));
                }
                
            } catch (SQLException ex) {
                /*Enviamos su respectivo mensaje de error a su ves una lista 
                    con valores incorrectos.*/
                System.out.println(ex.getMessage());
                lista_Estado_de_Cuenta.add(new Estado_De_Cuenta(
                            -1,
                            null,
                            -1,
                            -1,
                            -1,
                            ""));
                
            }finally {

                conex.cerrarConexion();

            }
        }
        return lista_Estado_de_Cuenta;
    }
}
