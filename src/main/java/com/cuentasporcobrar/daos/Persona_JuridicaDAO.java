
package com.cuentasporcobrar.daos;

import com.cuentasporcobrar.models.Persona;
import com.cuentasporcobrar.models.Persona_Juridica;
import com.global.config.Conexion;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Persona_JuridicaDAO extends PersonaDAO implements Serializable {

    Persona_Juridica person_Juridica;
    List<Persona_Juridica> lista_ClientesJuridicos;

    public Persona_JuridicaDAO(Persona_Juridica person_Juridica, List<Persona_Juridica> lista_ClientesJuridicos, Conexion conex, Persona persona, ResultSet result) {
        super(conex, persona, result);
        this.person_Juridica = person_Juridica;
        this.lista_ClientesJuridicos = lista_ClientesJuridicos;
    }

    public Persona_JuridicaDAO() {
        conex = new Conexion();
    }

    public Persona_JuridicaDAO(Persona_Juridica person_Juridica) {
        conex = new Conexion();
        this.person_Juridica = person_Juridica;
    }

    //--Ejecutando Funcion (Ingresando Persona Juridica)
    public int insertarClienteJuridico() {
        try {

            String sentenciaSQL = "Select Ingresar_Cliente_Juridico"
                    + "(" + person_Juridica.getIdTipoIdenficacion() + ",'"
                    + person_Juridica.getIdentificacion() + "','"
                    + person_Juridica.getDireccion() + "','"
                    + person_Juridica.getTlf1() + "','"
                    + person_Juridica.getTlf2() + "','"
                    + person_Juridica.getCorreo() + "','"
                    + person_Juridica.getRazonSocial() + "',"
                    + person_Juridica.getIdTipoCliente() + ",'"
                    + person_Juridica.getNomContacto()+"','"
                    + person_Juridica.getCargoContacto()+"','"
                    + person_Juridica.getPaginaWeb()+"','"
                    + person_Juridica.getFechaCreacion()+"')";
            //Verificamos la conexion
            if (conex.isEstado()) {
                //Una vez se asegura que la conexion este correcta.
                //Se ejecuta la sentencia ingresada.
                return conex.ejecutarProcedimiento(sentenciaSQL);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            conex.cerrarConexion();
        }
        //@return Caso contrario: Se retorna -1 indicando que la conexión está
        //en estado Falso
        return -1;
    }

    //Método que actualiza Cliente Juridico
    public int actualizarClienteJuridico() {
        try {
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
                    + person_Juridica.getNomContacto()+"','"
                    + person_Juridica.getCargoContacto()+"','"
                    + person_Juridica.getPaginaWeb()+"','"
                    + person_Juridica.getFechaCreacion()+"')";

            if (conex.isEstado()) {
                //Una vez se asegura que la conexion este correcta.
                //Se ejecuta la sentencia ingresada.
                return conex.ejecutarProcedimiento(sentenciaSQL);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            conex.cerrarConexion();
        }
        //@return Caso contrario: Se retorna -1 indicando que la conexión está
        //en estado Falso
        return -1;
    }

    //Método que retorna los clientes Juridicos
    public Persona_Juridica obtenerClienteJuridico(int idCliente) {
        Persona_Juridica p_juridica = new Persona_Juridica();
        if (conex.isEstado()) {
            try {
                String sentencia = "select*from obtener_cliente_juridico("
                        + idCliente + ")";
                result = conex.ejecutarConsulta(sentencia);
                while (result.next()) {

                    //Almacenamos en un objeto los datos personales de un 
                    //Cliente Juridico.
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
                        "", -1,"","","",null);
            } finally {
                conex.cerrarConexion();
            }
        }
        return p_juridica;
    }
}
