package com.cuentasporcobrar.daos;

import com.cuentasporcobrar.models.Persona;
import com.cuentasporcobrar.models.Persona_Juridica;
import com.global.config.Conexion;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Una clase Persona_JuridicaDAO que se va a encargar de la lógica de negocio
 * que lleva consigo tener acceso a la BD y al modelo.
 *
 * @author Alexander Vega, Andy Ninasunta.
 */
public class Persona_JuridicaDAO extends PersonaDAO implements Serializable {

    //Declaro la clase Persona_Juridica.
    Persona_Juridica person_Juridica;

    //Declaro una lista_ClientesJuridicos.
    List<Persona_Juridica> lista_ClientesJuridicos;

    /**
     * Constructor que obtiene un objeto Persona_Natural, una
     * lista_ClientesNaturales, la Conexion, el objeto Persona y un ResultSet.
     *
     * @param conex Obtiene la conexión a la base de datos.
     * @param lista_ClientesJuridicos Se carga el listado de clientes juridicos.
     * @param person_Juridica Se obtiene los campos y constructores declarados.
     * @param persona Se obtiene los campos y constructores declarados.
     * @param result Se realiza la lectura a las consultas que se realizan.
     */
    public Persona_JuridicaDAO(Persona_Juridica person_Juridica, List<Persona_Juridica> lista_ClientesJuridicos, Conexion conex, Persona persona, ResultSet result) {
        super(conex, persona, result);
        this.person_Juridica = person_Juridica;
        this.lista_ClientesJuridicos = lista_ClientesJuridicos;
    }

    /**
     * Constructor que inicializa solamente la conexión.
     */
    public Persona_JuridicaDAO() {
        conex = new Conexion();
    }

    /**
     * Constructor que recibe un objeto Persona_Juridica y que inicializa la
     * conexión.
     *
     * @param person_Juridica Se obtiene los campos y contructores declarados.
     */
    public Persona_JuridicaDAO(Persona_Juridica person_Juridica) {
        conex = new Conexion();
        this.person_Juridica = person_Juridica;
    }

    /**
     * Se registran los clientes juridicos.
     *
     * @return Se retorna 1 o -1.
     */
    public int insertarClienteJuridico() {
        try {

            /*Se guarda en una variable de tipo string el procemiento 
              almacenado. */
            String sentenciaSQL = "Select Ingresar_Cliente_Juridico"
                    + "(" + person_Juridica.getIdTipoIdenficacion() + ",'"
                    + person_Juridica.getIdentificacion() + "','"
                    + person_Juridica.getDireccion() + "','"
                    + person_Juridica.getTlf1() + "','"
                    + person_Juridica.getTlf2() + "','"
                    + person_Juridica.getCorreo() + "','"
                    + person_Juridica.getRazonSocial() + "',"
                    + person_Juridica.getIdTipoCliente() + ",'"
                    + person_Juridica.getNomContacto() + "','"
                    + person_Juridica.getCargoContacto() + "','"
                    + person_Juridica.getPaginaWeb() + "','"
                    + person_Juridica.getFechaCreacion() + "')";

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
            conex.cerrarConexion();
        }

        //Se retorna -1 indicando que la conexión esta en estado Falso.
        return -1;
    }

    /**
     * Se modifican los clientes juridicos.
     *
     * @return Se retorna 1 o -1.
     */
    public int actualizarClienteJuridico() {
        try {

            /*Se guarda en una variable de tipo string el procemiento 
              almacenado. */
            String sentenciaSQL = "Select actualizar_persona_juridica("
                    + person_Juridica.getIdCliente() + ","
                    + person_Juridica.getIdTipoIdenficacion() + ",'"
                    + person_Juridica.getIdentificacion() + "','"
                    + person_Juridica.getDireccion() + "','"
                    + person_Juridica.getTlf1() + "','"
                    + person_Juridica.getTlf2() + "','"
                    + person_Juridica.getCorreo() + "','"
                    + person_Juridica.getRazonSocial() + "',"
                    + person_Juridica.getIdTipoCliente() + ",'"
                    + person_Juridica.getNomContacto() + "','"
                    + person_Juridica.getCargoContacto() + "','"
                    + person_Juridica.getPaginaWeb() + "','"
                    + person_Juridica.getFechaCreacion() + "')";

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
            conex.cerrarConexion();
        }

        //Se retorna -1 indicando que la conexión esta en estado Falso.
        return -1;
    }

    /**
     * Se obtienen todos los clientes juridicos por medio de su id.
     *
     * @param idCliente El id del cliente que es único en la base de datos.
     * @return Un objeto Persona_Juridica.
     */
    public Persona_Juridica obtenerClienteJuridico(int idCliente) {

        //Se inicializa un objeto de Persona_Juridica.
        Persona_Juridica p_juridica = new Persona_Juridica();

        //Verificamos el estado de la conexión.
        if (conex.isEstado()) {
            try {

                /*Se guarda en una variable de tipo string el procemiento 
                  almacenado. */
                String sentencia = "select*from obtener_cliente_juridico("
                        + idCliente + ")";

                //Se da lectura del procemiento almacenado.
                result = conex.ejecutarConsulta(sentencia);

                //Se realiza el llenado del objeto Persona_Juridica.
                while (result.next()) {
                    p_juridica = new Persona_Juridica(
                            result.getString("razon_social_r"),
                            result.getInt("idtipoidentificacion_r"),
                            result.getString("direccion_r"),
                            result.getString("identificacion_r"),
                            result.getBoolean("estado_r"),
                            result.getString("telefono1_r"),
                            result.getString("telefono2_r"),
                            result.getString("correo1_r"),
                            result.getInt("idtipocliente_r"),
                            result.getString("nom_contacto_r"),
                            result.getString("cargo_contacto_r"),
                            result.getString("pagina_web_r"),
                            result.getObject("fecha_creacion_r", LocalDate.class));

                }
            } catch (SQLException ex) {
                p_juridica = new Persona_Juridica("", -1, "", "", false, "", "",
                        "", -1, "", "", "", null);
            } finally {

                //Se cierra la conexión.
                conex.cerrarConexion();
            }
        }
        return p_juridica;
    }
}
