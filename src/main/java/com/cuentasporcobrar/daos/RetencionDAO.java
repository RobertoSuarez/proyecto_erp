package com.cuentasporcobrar.daos;

import com.cuentasporcobrar.models.Retencion;
import com.global.config.Conexion;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase tipo DAO que se encarga de proporcionar ciertas funcionalidades para
 * insertar una nueva retencion, modificar una retención, obtener retenciones y
 * obtener ventas. (Esta ultima es un método reutilizable en otras clases.)
 *
 * @author Andy Ninasunta, Alexander Vega
 */
public class RetencionDAO implements Serializable {

    Conexion conex;
    Retencion retencion;
    ResultSet result;
    List<Retencion> lista_Retencion;

    /**
     * Constructor sin parámetros, para iniciar una conexion.
     */
    public RetencionDAO() {
        conex = new Conexion();
    }

    /**
     * Constructor que recibe el objeto Retención e inicia una nueva conexion.
     *
     * @param retencion Objeto con información de una retención.
     */
    public RetencionDAO(Retencion retencion) {
        conex = new Conexion();
        this.retencion = retencion;
    }

    /**
     * Método que retorna una lista con todas las retenciones de una venta.
     *
     * @param idVenta ID único de una venta.
     * @return List Retencion Lista con todas las retenciones de una venta.
     */
    public List<Retencion> obtenerRetenciones(int idVenta) {
        lista_Retencion = new ArrayList<>();

        //verificamos la conexion
        if (conex.isEstado()) {
            try {
                /*Se obtiene las retenciones de una venta*/
                String sentencia = "Select *from Obtener_Retenciones(" + idVenta + ") "
                        + "order by idretencion_r desc";
                result = conex.ejecutarConsulta(sentencia);

                //Recorremos la TABLA retornada y la almacenamos en la lista.
                while (result.next()) {

                    lista_Retencion.add(new Retencion(result.getInt("idretencion_r"),
                            result.getInt("idventa_r"),
                            result.getInt("porcentaje_r"),
                            result.getObject("fechaemision_r", LocalDate.class),
                            result.getDouble("baseimponible_r"),
                            result.getString("descripcion_r"),
                            result.getString("ejerciciofiscal_r"),
                            result.getDouble("total_r")));

                }
            } catch (SQLException ex) {
                /*Enviamos su respectivo mensaje de error a su ves una lista 
                    con valores incorrectos.*/
                System.out.println(ex.getMessage());
                lista_Retencion.add(new Retencion(-1,
                        -1,
                        0,
                        null,
                        0.0,
                        "",
                        "",
                        0.0));
            } finally {

                conex.cerrarConexion();

            }
        }
        return lista_Retencion;
    }

    /**
     * Método que recibe un ID del cliente y retorna todos los id de las ventas/
     * Facturas de un cliente en específico.
     *
     * @param idCliente ID único de un cliente.
     * @return List Retencion Lista con las facturas de un cliente.
     */
    public List<Retencion> obtenerVentas(int idCliente) {
        lista_Retencion = new ArrayList<>();

        //verificamos la conexion
        if (conex.isEstado()) {
            try {
                /*Se obtiene las facturas de un cliente*/
                String sentencia = "select*from obtener_idfacturas_de_Cliente(" + idCliente + ")";
                result = conex.ejecutarConsulta(sentencia);

                //Recorremos la TABLA retornada y la almacenamos en la lista.
                while (result.next()) {

                    lista_Retencion.add(new Retencion(result.getInt("idventa_r"),
                            result.getInt("id_sucursal_r"),
                            result.getInt("puntoemision_r"),
                            result.getInt("secuencia_r")));

                }

            } catch (SQLException ex) {
                /*Enviamos su respectivo mensaje de error a su ves una lista 
                    con valores incorrectos.*/
                lista_Retencion.add(new Retencion(-1,
                        -1,
                        -1,
                        -1));
                conex.cerrarConexion();
            } finally {

                conex.cerrarConexion();

            }
        }
        return lista_Retencion;
    }

    /**
     * Método para insertar una retención a una venta en específico.
     * @param idCliente ID único de un cliente.
     * @param idVenta ID único de una venta.
     * @return int Retorna un entero, el cual sirve para saber si se insertó correctamente.
     */
    public int insertarRetencion(int idCliente, int idVenta) {
        try {
            String sentenciaSQL = "Select Ingresar_Retencion(" + idCliente + ","
                    + idVenta + ",'"
                    + retencion.getFechaEmision() + "',"
                    + retencion.getBaseImponible() + ",'"
                    + retencion.getDescImpuesto() + "')";

            //Verificamos la conexion
            if (conex.isEstado()) {
                //Una vez se asegura que la conexion este correcta.
                //Se ejecuta la sentencia ingresada.
                System.out.println(sentenciaSQL);
                return conex.ejecutarProcedimiento(sentenciaSQL);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            conex.cerrarConexion();
        }
        //Caso contrario: Se retorna -1 indicando que la conexión está
        //en estado Falso
        return -1;

    }

    /**
     * Método para  modificar/actualizar una retención.
     * @param ret Objeto con la información de la Retencion
     * @param idcliente ID único de un cliente.
     * @return int Retorna un entero, el cual sirve para saber si se insertó correctamente.
     */
    public int actualizarRetencion(Retencion ret, int idcliente) {
        try {
            String sentenciaSQL = "Select actualizar_retencion(" + idcliente + ","
                    + ret.getIdRetencion() + ",'"
                    + ret.getFechaEmision() + "',"
                    + ret.getBaseImponible() + ",'"
                    + ret.getDescImpuesto() + "')";

            //Verificamos la conexion
            if (conex.isEstado()) {

                //Una vez se asegura que la conexion este correcta.
                //Se ejecuta la sentencia ingresada.
                System.out.println("update 1: " + sentenciaSQL);
                return conex.ejecutarProcedimiento(sentenciaSQL);

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            conex.cerrarConexion();
        }

        //Caso contrario: Se retorna -1 indicando que la conexión está
        //en estado Falso
        return -1;
    }
}
