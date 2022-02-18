package com.cuentasporcobrar.daos;

import com.cuentasporcobrar.models.Persona;
import com.cuentasporcobrar.models.Persona_Natural;
import com.global.config.Conexion;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Una clase Persona_NaturalDAO que se va a encargar de la lógica de negocio que
 * lleva consigo tener acceso a la BD y al modelo.
 *
 * @author Alexander Vega, Andy Ninasunta.
 */
public class Persona_NaturalDAO extends PersonaDAO implements Serializable {

    //Declaro la clase Persona_Natural.
    Persona_Natural person_Natural;

    //Declaro una lista_ClientesNaturales.
    List<Persona_Natural> lista_ClientesNaturales;

    /**
     * Constructor que obtiene un objeto Persona_Natural, una
     * lista_ClientesNaturales, la Conexion, el objeto Persona y un ResultSet.
     *
     * @param conex Obtiene la conexión a la base de datos.
     * @param lista_ClientesNaturales Se carga el listado de clientes naturales.
     * @param person_Natural Se obtiene los campos y constructores declarados.
     * @param persona Se obtiene los campos y constructores declarados.
     * @param result Se realiza la lectura a las consultas que se realizan.
     */
    public Persona_NaturalDAO(Persona_Natural person_Natural, List<Persona_Natural> lista_ClientesNaturales, Conexion conex, Persona persona, ResultSet result) {
        super(conex, persona, result);
        this.person_Natural = person_Natural;
        this.lista_ClientesNaturales = lista_ClientesNaturales;
    }

    /**
     * Constructor que inicializa solamente la conexión.
     */
    public Persona_NaturalDAO() {
        conex = new Conexion();
    }

    /**
     * Constructor que recibe un objeto Persona_Natural y que inicializa la
     * conexión.
     *
     * @param person_Natural Se obtiene los campos y contructores declarados.
     */
    public Persona_NaturalDAO(Persona_Natural person_Natural) {
        conex = new Conexion();
        this.person_Natural = person_Natural;
    }

    /**
     * Se registran los clientes naturales.
     *
     * @return Se retorna 1 o -1.
     */
    public int insertarClienteNatural() {
        try {

            /*Se guarda en una variable de tipo string el procedimiento 
              almacenado. */
            String sentenciaSQL = "Select Ingresar_Cliente_Natural "
                    + "(" + person_Natural.getIdTipoIdenficacion() + ",'"
                    + person_Natural.getIdentificacion() + "','"
                    + person_Natural.getNombre1() + "','"
                    + person_Natural.getNombre2() + "','"
                    + person_Natural.getApellido1() + "','"
                    + person_Natural.getApellidos2() + "','"
                    + person_Natural.getDireccion() + "','"
                    + person_Natural.getTlf1() + "','"
                    + person_Natural.getTlf2() + "','"
                    + person_Natural.getCorreo() + "','"
                    + person_Natural.getSexo() + "','"
                    + person_Natural.getGenero() + "','"
                    + person_Natural.getFechaNacimiento() + "',"
                    + person_Natural.getIdTipoCliente() + ")";

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
     * Se modifican los clientes naturales.
     *
     * @return Se retorna 1 o -1.
     */
    public int actualizarClienteNatural() {
        try {
            
            /*Se guarda en una variable de tipo string el procedimiento 
              almacenado. */
            String sentenciaSQL = "Select actualizar_persona_natural(" + person_Natural.getIdCliente() + ","
                    + person_Natural.getIdTipoIdenficacion() + ",'"
                    + person_Natural.getIdentificacion() + "','"
                    + person_Natural.getDireccion() + "','"
                    + person_Natural.getTlf1() + "','"
                    + person_Natural.getTlf2() + "','"
                    + person_Natural.getCorreo() + "','"
                    + person_Natural.getNombre1() + "','"
                    + person_Natural.getNombre2() + "','"
                    + person_Natural.getApellido1() + "','"
                    + person_Natural.getApellidos2() + "','"
                    + person_Natural.getSexo() + "','"
                    + person_Natural.getGenero() + "','"
                    + person_Natural.getFechaNacimiento() + "',"
                    + person_Natural.getIdTipoCliente() + ")";

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
     * Se obtienen todos los clientes naturales por medio de su id.
     *
     * @param idCliente El id del cliente que es único en la base de datos.
     * @return Un objeto Persona_Natural.
     */
    public Persona_Natural obtenerClienteNatural(int idCliente) {

        //Se inicializa un objeto de Persona_Natural.
        Persona_Natural p_natural = new Persona_Natural();

        //Verificamos el estado de la conexión.
        if (conex.isEstado()) {
            try {
                
                /*Se guarda en una variable de tipo string el procedimiento 
                  almacenado. */
                String sentencia = "select*from obtener_cliente_natural("
                        + idCliente + ")";

                //Se da lectura del procemiento almacenado.
                result = conex.ejecutarSql(sentencia);

                //Se realiza el llenado del objeto Persona_Natural.
                while (result.next()) {
                    p_natural = new Persona_Natural(
                            result.getString("sexo_r"),
                            result.getString("genero_r"),
                            result.getString("nombre1_r"),
                            result.getString("nombre2_r"),
                            result.getString("apellido1_r"),
                            result.getString("apellido2_r"),
                            result.getObject("fecha_nacimiento_r", LocalDate.class),
                            result.getInt("idtipoidentificacion_r"),
                            result.getString("direccion_r"),
                            result.getString("identificacion_r"),
                            result.getBoolean("estado_r"),
                            result.getString("telefono1_r"),
                            result.getString("telefono2_r"),
                            result.getString("correo1_r"),
                            result.getInt("idtipocliente_r"));

                }
            } catch (SQLException ex) {

                System.out.println("Error al Obtener los datos del cliente: "
                        + ex.getMessage());

            } finally {

                //Se cierra la conexión.
                conex.desconectar();

            }
        }
        return p_natural;
    }
}
