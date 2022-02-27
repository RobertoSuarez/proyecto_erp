package com.cuentasporcobrar.daos;

import com.cuentasporcobrar.models.Abono;
import com.cuentasporpagar.models.Factura;
import com.global.config.Conexion;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades
 * para todo lo que tenga que ver con Abonos y Planes de pago, Esta clase 
 * tiene muchas funciones que son reutilizables.
 * @author Andy Ninasunta, Alexander Vega
 */
public class AbonoDAO implements Serializable {

    List<Abono> listaAbonos;
    Conexion conexion;
    Abono abono;
    ResultSet result;

    /**
     * Constructor sin parámetros, para iniciar una conexion.
     */
    public AbonoDAO() {
        conexion = new Conexion();
    }

    /**
     * Constructor que recibe el objeto Abono e inicia una nueva conexion.
     * @param abono  Objeto con información de un abono.
     */
    public AbonoDAO(Abono abono) {
        conexion = new Conexion();
        this.abono = abono;
    }

    /**
     * Método para enlistar todos los abonos de una determinada venta/plan de pago
     * @param idVenta Identificación única de una venta.
     * @return List Abono Lista con los abonos de una determinada venta
     */
    public List<Abono> obtenerAbonos(int idVenta) {
        listaAbonos = new ArrayList<>();

        //verificamos la conexion
        if (conexion.isEstado()) {
            try {
                /* Se obtiene una TABLA con todos los abonos que se pagaron 
                de un plan de pago correspondiente */
                String sentencia = "Select*from obtener_abonos_de_plan_de_pago(" + idVenta + ")";
                result = conexion.ejecutarSql(sentencia);

                //Recorremos la TABLA retornada y la almacenamos en la lista.
                while (result.next()) {

                    //Concatenamos la sucursal, el punto de emision y el numero de la factura
                    String numFact = obtenerConcatenacionFactura(result.getInt("id_sucursal_r"),
                            result.getInt("puntoemision_r"), result.getInt("secuencia_r"));

                    listaAbonos.add(new Abono(result.getInt("idabono_r"),
                            result.getInt("idventa_r"),
                            result.getDouble("totalabono_r"),
                            result.getObject("fechadeabono_r", LocalDate.class),
                            result.getString("nombrepago_r"),
                            numFact));

                }

            } catch (SQLException ex) {

                /*Enviamos su respectivo mensaje de error a su ves una lista 
                    con valores incorrectos.*/
                System.out.println(ex.getMessage());
                listaAbonos.add(new Abono(-1,
                        -1,
                        -1,
                        null,
                        "", ""));
            } finally {

                conexion.desconectar();

            }
        }
        //Retornamos una lista con todos los abonos de una venta.
        return listaAbonos;
    }

    /**
     * Método que devuelve un String con la concatenacion de la factura
     * @param sucursal Sucursal donde se emitio una factura
     * @param pntEmision Punto de Emision donde se realizó la factura.
     * @param secuencia Secuencia única de una factura
     * @return String Retorna el numero de la factura concatenado.
     */
    public String obtenerConcatenacionFactura(int sucursal, int pntEmision, int secuencia) {
        String numSucursal = "000", numEmision = "000", numSecuencia = "000000000";
        int longitud = 0;

        //Controlando la surcursal
        longitud = String.valueOf(sucursal).length();
        numSucursal = numSucursal.substring(0, (numSucursal.length()) - longitud) + String.valueOf(sucursal);

        //Controlando punto de emision
        longitud = String.valueOf(pntEmision).length();
        numEmision = numEmision.substring(0, (numEmision.length()) - longitud) + String.valueOf(pntEmision);

        //Controlando secuencia de la factura
        longitud = String.valueOf(secuencia).length();
        numSecuencia = numSecuencia.substring(0, (numSecuencia.length()) - longitud) + String.valueOf(secuencia);

        return numSucursal + "-" + numEmision + "-" + numSecuencia;

    }

    /**
     * Método para insertar un nuevo abono.
     * Nota:Al momento de insertar un nuevo abonos, automaticamente el valor
     * pendiente de el plan de pago se actualiza en el procedimiento de PostGre
     * @param idCliente ID único de un cliente.
     * @param idPlanPago ID único de un plan de pago.
     * @return int Retorna un entero, el cual sirve para saber si se insertó correctamente.
     */
    public int insertarNuevoAbono(int idCliente, int idPlanPago) {

        try {
            /*Se ubica en el siguiente orden: 
        (ID cliente, id plan de pago, forma de pago, valor abonado, fecha)*/
            String sentenciaSQL = "select ingresar_abono(" + idCliente + "," + idPlanPago + ","
                    + abono.getIdFormaDePago() + "," + abono.getValorAbonado() + ",'"
                    + abono.getFechaAbono() + "')";

            //Verificamos la conexion
            if (conexion.isEstado()) {
                /*Una vez se asegura que la conexion este correcta.
            Se ejecuta la sentencia ingresada.*/
                return conexion.ejecutarProcedimiento(sentenciaSQL);
            }
            
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            conexion.desconectar();
        }
        return -1;
    }
    
    //asiento contable
    public void insertasiento(int id) {
        if (conexion.isEstado())
        {
            try
            {
                int iddiario = 0;
                String cadena = "select iddiario from diariocontable where descripcion = 'Modulo cuentas por cobrar'";
                result = conexion.ejecutarSql(cadena);
                while (result.next())
                {
                    iddiario = result.getInt("iddiario");
                }
                //JSON asiento contable
                String sentencia1, sentencia;
                sentencia = "{\"idDiario\": \"" + iddiario + "\",\"total\": " + abono.getValorAbonado()
                        + ",\"documento\": \"CPC-ABN-" + id + "\",\"detalle\": "
                        + "\"Cuentas por cobrar cliente\",\"fechaCreacion\": \""
                        + abono.getFechaAbono().format(DateTimeFormatter.ofPattern("d/MM/uuuu")) + "\",\"fechaCierre\":\""
                        + abono.getFechaAbono().plusDays(30).format(DateTimeFormatter.ofPattern("d/MM/uuuu")) + "\"}";
                System.out.println(sentencia);
                //JSON un solo movimiento

                int formaPago;
                String tipomovimiento;
                switch (abono.getIdFormaDePago()) {
                    case 2:
                        formaPago=1;
                        tipomovimiento = "Caja";
                        break;
                    case 3:
                        formaPago=3;
                        tipomovimiento = "Banco";
                        break;
                    default:
                        formaPago=77;
                        tipomovimiento = "Ventas con tarifa 12%";
                        break;
                }
                    
                    sentencia1 = "[{\"idSubcuenta\":\"156\",\"debe\":\"0\",\"haber\":\""
                            + abono.getValorAbonado() + "\",\"tipoMovimiento\":\"Factura de venta\"},"
                            + "{\"idSubcuenta\":\""+formaPago+"\",\"debe\":\""+ abono.getValorAbonado() 
                            +"\",\"haber\":\"0\",\"tipoMovimiento\":\""+tipomovimiento+"\"}]";
                    System.out.println(sentencia1);
                    
                intJson(sentencia, sentencia1);
            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
    }
    
     public void intJson(String a, String b) {
        if (conexion.isEstado())
        {
            try
            {
                String cadena = "SELECT public.generateasientocotableexternal('"
                        + a + "','" + b + "')";
                System.out.println(cadena);
                result = conexion.ejecutarSql(cadena);
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
    }


    /**
     * Método para devolver el valor pendiente de cobro de un determinado plan/venta.
     * @param idVenta Identificación única de una venta.
     * @return double Double con el valor pendiente de una venta.  
     */
    public double obtenerValorPendiente(int idVenta) {
        double valorPendiente = 0;

        try {
            /*El valor pendiente de una venta se lo obtiene sumando todos los 
            los montos de los abonos menos el total de la factura.
             */
            String sentencia = "select*from "
                    + "obtener_valor_pendiente_de_una_venta"
                    + "(" + idVenta + ")";
            result = conexion.ejecutarSql(sentencia);

            result.next();

            //Almacenamos el valor obtenido en la variable
            valorPendiente = result.getDouble(1);

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
            //Si hay algun error o el valor es nulo, se retorna -1.
            return -1;

        } finally {

            conexion.desconectar();

        }

        return valorPendiente;
    }

    /**
     * Método para devolver la suma de todos los abonos de una venta.
     * @param idVenta Identificación única de una venta.
     * @return double Suma de todos los abonos de una venta.
     */
    public double obtenerSumAbonos(int idVenta) {
        double sumAbonos = 0;

        try {
            /*En este apartado obtendremos la suma de todos los abonos de una
            venta
             */
            String sentencia = "select*from "
                    + " obtener_sum_de_abonos_de_una_venta"
                    + "(" + idVenta + ")";
            result = conexion.ejecutarSql(sentencia);

            result.next();

            //Almacenamos el valor obtenido en la variable
            sumAbonos = result.getDouble(1);

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
            //Si hay algun error o el valor es nulo, se retorna -1.
            return -1;

        } finally {

            conexion.desconectar();

        }

        return sumAbonos;
    }

    /**
     * Método para devolver el id del plan de pago y poder validar.
     * @param idVenta Identificación única de una venta.
     * @return int Retorna el ID del plan de pago.
     */
    public int obtenerIdPlanPago(int idVenta) {
        int idPlanDePago = 0;

        try {
            /*El valor pendiente de una venta se lo obtiene sumando todos los 
            los montos de los abonos menos el total de la factura.
             */
            String sentencia = "select*from "
                    + "obtener_id_PlanDePago"
                    + "(" + idVenta + ")";
            result = conexion.ejecutarSql(sentencia);

            result.next();

            //Almacenamos el valor obtenido en la variable
            idPlanDePago = result.getInt(1);

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
            //Si hay algun error o el valor es nulo, se retorna -1.
            return -1;

        } finally {

            conexion.desconectar();

        }

        return idPlanDePago;
    }

    /**
     * Funcion para obtener la fecha de credito y vencimiento de un plan de pago.
     * Donde [0] es la fecha de credito y [1] la fecha de vencimiento.
     * @param idVenta Identificación única de una venta.
     * @return LocalDate[] Arreglo de tipo LocalDate con la fecha de credito y fecha
     * de vencimiento.
     */
    public LocalDate[] obtenerFechaCreditoVencimiento(int idVenta) {

        LocalDate[] fechasPlanPago = {null, null}; //[0] Total Venta, [1] Cartera P.

        //Verificamos la conexion
        if (conexion.isEstado()) {
            try {
                //Se obtiene una TABLA con 1 fila y 2 columnas.
                String sentencia = "Select *from "
                        + "obtener_fechas_plandepago("
                        + idVenta + ")";
                result = conexion.ejecutarSql(sentencia);

                result.next();

                //Almacenamos en sus respectiva posicion los resultados.
                fechasPlanPago[0] = result.getObject("fechacredito_r", LocalDate.class);
                fechasPlanPago[1] = result.getObject("fechavencimiento_r", LocalDate.class);

            } catch (SQLException ex) {
                /*Si hay algun error retornamos 0,0 para cada valor
                    y su respectivo mensaje de error.*/
                System.out.println(ex.getMessage());
                return fechasPlanPago;

            } finally {

                conexion.desconectar();

            }
        }

        return fechasPlanPago;
    }
}
