/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Persona;
import com.recursoshumanos.Model.Interfaces.IDAO;
import org.jetbrains.annotations.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 *
 * Clase tipo DAO con las funciones para la manipulación de personas
 * Y se encarga de administrar las sentencias de la BD, utilizando las clases 
 * de los modelos
 */
public class PersonaDAO implements IDAO<Persona> {

    private Conexion conexion;
    private Persona persona;

    public PersonaDAO() {
        conexion = new Conexion();
        persona = new Persona();
    }

    public PersonaDAO(Persona persona) {
        conexion = new Conexion();
        this.persona = persona;
    }

    public PersonaDAO(Conexion conexion) {
        this.conexion = conexion;
        persona = new Persona();
    }

    public PersonaDAO(Persona persona, Conexion conexion) {
        this.conexion = conexion;
        this.persona = persona;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Ejecuta la inserción de los atributos del objeto persona mediante el
     * método insertar del objeto conexión
     *
     * @return persona.getId(), el id de la persona que se ha agregado o un -1 
     * si no se pudo
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            persona.setId(conexion.insertar("persona_bck_rrhh",
                    "direccion, tipo_identificacion, identificacion, estado, "
                    + "telefono1, telefono2, correo1", "'"
                    + persona.getDireccion() + "', '"
                    + persona.getTipoIdentificacion() + "', '"
                    + persona.getIdentificacion() + "', " + persona.isEstado()
                    + ", '" + persona.getTelefono1() + "', '"
                    + persona.getTelefono2() + "', '"
                    + persona.getCorreo() + "'",
                    "id_persona"));
            return persona.getId();
        }
        return -1;
    }

    /**
     * Toma un objeto tipo persona y lo copia al objeto persona propio de la
     * clase
     *
     * @param entity, objeto de tipo persona
     * @return insertar(), manda a ejecutar al otro método insertar
     */
    @Override
    public int insertar(Persona entity) {
        this.persona = entity;
        return insertar();
    }

    /**
     * Ejecuta la actualización mediante le método modificar del objeto conexión
     *
     * @return conexion.modificar(), el id de la persona que se ha modificado o 
     * un -1 si no se pudo
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("persona_bck_rrhh",
                    "direccion = '" + persona.getDireccion()
                    + "', tipo_identificacion = '"
                    + persona.getTipoIdentificacion()
                    + "', identificacion = '" + persona.getIdentificacion()
                    + "', estado = " + persona.isEstado() + ", telefono1 = '"
                    + persona.getTelefono1() + "', telefono2 = '"
                    + persona.getTelefono2() + "', correo1 ='"
                    + persona.getCorreo() + "'", "id_persona = "
                    + persona.getId());
        }
        return -1;
    }

    /**
     * Toma un objeto tipo persona y lo copia al objeto persona propio de la
     * clase
     *
     * @param entity, objeto de tipo persona
     * @return actualizar(), manada a ejecutar al otro método actualizar
     */
    @Override
    public int actualizar(Persona entity) {
        this.persona = entity;
        return actualizar();
    }

    /**
     * Busca por una persona por su id.
     *
     * @param id de la persona a buscar
     * @return lista.get(0), persona encontrada o null en caso de no hallarla
     */
    @Override
    public Persona buscarPorId(Object id) {
        List<Persona> lista = buscar("id_persona = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    /**
     * Lista las persona
     *
     * @return buscar(), ejecuta método buscar
     */
    @Override
    public List<Persona> Listar() {
        return buscar(null, "identificacion ASC");
    }

    /**
     * Lista las provincias en base a los dos parámetros de entrada
     *
     * @param restricciones, las restricciones para la búsqueda
     * @param OrdenarAgrupar forma en que se desea ordenar
     * @return personas, lista con las personas ordenandas
     */
    private List<Persona> buscar(@Nullable String restricciones,
            @Nullable String OrdenarAgrupar) {
        if (conexion.isEstado()) {
            ResultSet result;
            List<Persona> personas;
            try {
                result = conexion.selecionar("persona_bck_rrhh",
                        "id_persona, identificacion, tipo_identificacion, "
                        + "correo1, telefono1, telefono2, direccion, estado",
                        restricciones, OrdenarAgrupar);
                personas = new ArrayList<>();
                while (result.next()) {
                    personas.add(new Persona(
                            result.getInt("id_persona"),
                            result.getString("identificacion"),
                            result.getString("tipo_identificacion"),
                            result.getString("correo1"),
                            result.getString("telefono1"),
                            result.getString("telefono2"),
                            result.getString("direccion"),
                            result.getBoolean("estado")
                    ));
                }
                result.close();
                return personas;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return null;
    }
}
