
package com.cuentasporcobrar.daos;

import com.cuentasporcobrar.models.Facturas_Pendientes;
import com.global.config.Conexion;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de tipo DAO que proporciona ciertas funcionalidades para poder obtener
 * de la base de datos las facturas pendientes y realizar sus respectivos 
 * reportes.
 * @author Andy Ninasunta, Alexander Vega
 */
public class Facturas_PendientesDAO implements Serializable{
    
    List<Facturas_Pendientes> lista_facturas_Pendientes;
    Facturas_Pendientes facturas_Pendientes; 
    Conexion conex;
    ResultSet result;

    /**
     * Constructor sin parámetros, para iniciar una conexion.
     */
    public Facturas_PendientesDAO() {
        conex=new Conexion();
    }
    
    /**
     * Constructor que recibe el objeto Facturas_Pendientes e inicia una nueva conexion.
     * @param facturas_Pendientes Objeto con una factura pendiente.
     */
    public Facturas_PendientesDAO(Facturas_Pendientes facturas_Pendientes) {
        conex=new Conexion();
        this.facturas_Pendientes = facturas_Pendientes;
    }

    /**
     * Método para en enlistar las facturas pendientes.
     * @return List Facturas_Pendientes Lista con todas las facturas pendientes 
     * de todos los clientes.
     */
    public List<Facturas_Pendientes> obtenerFacturasPendientes() {
        lista_facturas_Pendientes = new ArrayList<>();

        //Verificamos la conexion
        if (conex.isEstado()) {
            try {
                /* Se obtiene una TABLA con todas las facturas que se pagaron a
                credito, con sus respectivo datos calculados como la 
                fecha de vencimiento =fecha actual+diascredito */
                String sentencia = "Select*from Obtener_Facturas_Pendientes()";
                result = conex.ejecutarSql(sentencia);

                //Instanciamos la clase AbonoDAO.        
                AbonoDAO abonoDAO= new AbonoDAO();

                //Recorremos la TABLA retornada y la almacenamos en la lista.
                while (result.next()) {

                    //Concatenamos la sucursal, el punto de emision y el numero de la factura
                    String numFact =abonoDAO.obtenerConcatenacionFactura(result.getInt("id_sucursal_r"),
                            result.getInt("puntoemision_r"), result.getInt("secuencia_r"));
                    
                    lista_facturas_Pendientes.add(
                            new Facturas_Pendientes(result.getObject("fechacredito_r", LocalDate.class),
                                    result.getInt("diasdecredito_r"),
                                    result.getObject("fechavencimiento_r", LocalDate.class),
                                    result.getString("nombre_clientes_r"),
                                    result.getInt("idventa_r"),
                                    result.getDouble("valortotalfactura_r"),
                                    result.getDouble("valorpendiente_r"),
                                    result.getObject("fechapagofinal_r", LocalDate.class),
                                    result.getString("descripcionestado_r"),
                                    result.getInt("diasmora_r"),numFact));

                }
            } catch (SQLException ex) {
                /*Enviamos su respectivo mensaje de error a su ves una lista 
                    con valores incorrectos.*/
                System.out.println(ex.getMessage());
                lista_facturas_Pendientes.add(
                        new Facturas_Pendientes(null,
                                -1,
                                null,
                                null,
                                -1,
                                -1,
                                -1, 
                                null,
                                "",
                                -1,
                        ""));
            } finally {

                conex.desconectar();

            }
        }
        return lista_facturas_Pendientes;
    }

    /**
     * Método para obtener el total de Venta de la empresa, así como el total de
     * lo que debe cobrar a los clientes pos[0] es la venta y la pos[1] 
     * la cartera pendiente.
     * @return double[] Arreglo de tipo double con el total de la venta y la cartera
     * pendiente.
     */
    public double[] obtenerTotalVentayCarteraPendiente() {
        double[] tVentaCarteraP = {0, 0}; //[0] Total Venta, [1] Cartera P.

        //Verificamos la conexion
        if (conex.isEstado()) {
            try {
                //Se obtiene una TABLA con 1 fila y 2 columnas.
                String sentencia = "Select*From obtener_ventas_y_cartera_pendiente()";
                result = conex.ejecutarSql(sentencia);

                result.next();

                //Almacenamos en sus respectiva posicion los resultados.
                tVentaCarteraP[0] = result.getDouble("total_venta_r");
                tVentaCarteraP[1] = result.getDouble("cartera_pendiente_r");

            } catch (SQLException ex) {
                /*Si hay algun error retornamos 0,0 para cada valor
                    y su respectivo mensaje de error.*/
                System.out.println(ex.getMessage());
                return tVentaCarteraP;

            } finally {

                conex.desconectar();

            }
        }

        return tVentaCarteraP;
    }
  
}
