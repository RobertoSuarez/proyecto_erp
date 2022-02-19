package com.cuentasporcobrar.daos;

import com.cuentasporcobrar.models.Plan_Pago;
import com.global.config.Conexion;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Una clase Plan_PagoDAO que se va a encargar de la lógica de negocio que lleva
 * consigo tener acceso a la BD y al modelo.
 *
 * @author Alexander Vega, Andy Ninasunta.
 */
public class Plan_PagoDAO implements Serializable {

    //Declaro un lista_cobros que obtiene un objeto Plan_Pago.
    List<Plan_Pago> lista_cobros;

    //Declaro la clase Conexion.
    Conexion conex;

    //Declaro la clase Plan_Pago.
    Plan_Pago plan_pago;
    ResultSet result;

    /**
     * Constructor que obtiene la conexion.
     */
    public Plan_PagoDAO() {
        conex = new Conexion();
    }

    /**
     * Constructor que obtiene la conexion e instancia el objeto persona.
     *
     * @param planPago Instancia al objeto Plan_Pago.
     */
    public Plan_PagoDAO(Plan_Pago planPago) {
        conex = new Conexion();
        this.plan_pago = planPago;
    }

    /**
     * Constructor que recibe la conexion, el objeto Plan_Pago y el Resulset.
     *
     * @param conex Obtiene la conexion a la base de datos.
     * @param planPago Instancia al objeto Plan_Pago.
     * @param result Instancia al objeto resultset para lectura de sentencias.
     */
    public Plan_PagoDAO(Conexion conex, Plan_Pago planPago, ResultSet result) {
        this.conex = conex;
        this.plan_pago = planPago;
        this.result = result;
    }

    /**
     * Método que inserta un nuevo plan de pago.
     *
     * @return El retorno es 1 o -1.
     */
    public int insertarPlanDePago() {
        try {

            /*Se guarda en una variable de tipo string el procedimiento 
              almacenado. */
            String sentenciaSQL = "Select ingresar_plan_de_pago(" + plan_pago.getIdFactura()
                    + "," + plan_pago.getDiasCredito()
                    + ",'" + plan_pago.getFechaFacturacion()
                    + "'," + plan_pago.getValorTotalFactura() + "," + plan_pago.getIntereses() + ")";

            //Verificamos el estado de la conexión.
            if (conex.isEstado()) {

                /*Una vez se asegura que la conexion este correcta y
                  se ejecuta la sentencia ingresada. */
                return conex.ejecutarProcedimiento(sentenciaSQL);

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {

            //Se cierra la conexion.
            conex.desconectar();
        }

        //Se retorna -1 indicando que la conexión esta en estado Falso.
        return -1;
    }

    /**
     * Se modifican los Planes de Pago por medio de su id .
     *
     * @param idPlanDePago Se guarda el id de plan de pago que es único de la
     * base de datos.
     * @return Se retorna 1 o -1.
     */
    public int actualizarPlanDePago(int idPlanDePago) {
        try {

            /*Se guarda en una variable de tipo string el procedimiento 
              almacenado. */
            String sentenciaSQL = "Select actualizar_plan_de_pago(" + idPlanDePago + ","
                    + plan_pago.getIdFactura()
                    + "," + plan_pago.getDiasCredito()
                    + ",'" + plan_pago.getFechaFacturacion()
                    + "'," + plan_pago.getValorTotalFactura() + ","
                    + plan_pago.getIntereses() + ")";

            //Verificamos el estado de la conexión.
            if (conex.isEstado()) {

                /*Una vez se asegura que la conexion este correcta y
                  se ejecuta la sentencia ingresada. */
                return conex.ejecutarProcedimiento(sentenciaSQL);

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {

            //Se cierra la conexión.
            conex.desconectar();
        }

        //Se retorna -1 indicando que la conexión esta en estado Falso.
        return -1;
    }

    /**
     * Se obtienen todos los cobros por medio del idCliente.
     *
     * @param idCliente El id del cliente que es único en la base de datos.
     * @return Un objeto Lista Plan_Pago.
     */
    public List<Plan_Pago> obtenerCobrosCliente(int idCliente) {

        //Se inicializa un objeto de lista_cobros.
        lista_cobros = new ArrayList<>();

        //Verificamos el estado de la conexión.
        if (conex.isEstado()) {
            try {

                /*Se guarda en una variable de tipo string el procedimiento 
                  almacenado. */
                String sentencia = "select*from obtener_cobros_x_cliente(" + idCliente + ")";
                result = conex.ejecutarSql(sentencia);

                //Instanciamos la clase AbonoDAO.        
                AbonoDAO abonoDAO = new AbonoDAO();

                //Recorremos la TABLA retornada y la almacenamos en la lista.
                while (result.next()) {

                    //Concatenamos la sucursal, el punto de emision y el numero de la factura
                    String numFact = abonoDAO.obtenerConcatenacionFactura(result.getInt("id_sucursal_r"),
                            result.getInt("puntoemision_r"), result.getInt("secuencia_r"));

                    lista_cobros.add(
                            new Plan_Pago(result.getObject("fechacredito_i", LocalDate.class),
                                    result.getInt("diasdecredito_i"),
                                    result.getObject("fechavencimiento_i", LocalDate.class),
                                    result.getInt("idventa_i"),
                                    result.getDouble("valortotalfactura_i"),
                                    result.getDouble("saldopendiente_i"),
                                    result.getDouble("totalabonos_i"),
                                    result.getString("descripcionestado_i"),
                                    result.getInt("diasmora_i"),
                                    numFact));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                lista_cobros.add(
                        new Plan_Pago(null, -1, null, -1, -1, -1, -1, "", -1, ""));
            } finally {

                //Se cierra la conexión.
                conex.desconectar();

            }
        }
        return lista_cobros;
    }

}
