/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.TipoRubro;
import com.recursoshumanos.Model.Interfaces.IDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author kestradalp
 * @author rturr
 * @author ClasK7
 * 
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades
 * para todo lo que tenga que ver con los Tipos de Rubros.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class TipoRubroDAO implements IDAO<TipoRubro> {

    private final Conexion conexion;
    private TipoRubro tipoRubro;

    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y TipoRubro.
     */
    public TipoRubroDAO() {
        this.conexion = new Conexion();
        this.tipoRubro = new TipoRubro();
    }

    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y TipoRubro.
     * 
     * @param conexion Objeto de tipo Conexion con la información de la
     *                 conexion.
     */
    public TipoRubroDAO(Conexion conexion) {
        this.conexion = conexion;
        this.tipoRubro = new TipoRubro();
    }

    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y TipoRubro.
     * 
     * @param tipoRubro Objeto de tipo TipoRubro con la información de una
     *                   suspencion.
     */
    public TipoRubroDAO(TipoRubro tipoRubro) {
        this.conexion = new Conexion();
        this.tipoRubro = tipoRubro;
    }

    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y TipoRubro.
     * 
     * @param conexion Objeto de tipo Conexion con la información de la
     *                 conexion.
     * @param tipoRubro Objeto de tipo TipoRubro con la información del tipo de
     *                  rubro.
     */
    public TipoRubroDAO(Conexion conexion, TipoRubro tipoRubro) {
        this.conexion = conexion;
        this.tipoRubro = tipoRubro;
    }

    /**
     * El @override nos ayuda a sobre-escribir el método
     * en el que se esta trabajando
     */

    /**
     * Función que devuelve la conexion presente
     * 
     * @return conexion Objeto de tipo Conexion con la información de la
     *                  conexion.
     */
    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    /**
     * A continuación se crea la función insertar
     * @return insertar devuelve el resultado de la funcion
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            return conexion.insertar("tipo_rubro", "nombre, coeficiente", 
                    "'" + tipoRubro.getNombre()+ "'," 
                        + tipoRubro.getCoeficiente(), "id_tipo_rubro");
        }
        return -1;
    }

    /**
     * A continuación se crea la función insertar
     * @param entity Contiene la información de la entidad TipoRubro
     * @return insertar() Devuelve la función con la entidad dentro
     */
    @Override
    public int insertar(TipoRubro entity) {
        this.tipoRubro = entity;
        return insertar();
    }

    /**
     * A continuación se crea la función actualizar
     * @return conexion.modificar() Devuelve el resultado de la función
     *                              modificar para actualizar un TipoDeRubro.
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("tipo_rubro", 
                    "nombre = '" + tipoRubro.getNombre()+ "', coeficiente = " 
                                 + tipoRubro.getCoeficiente(),
                    "id_tipo_rubro = " + tipoRubro.getId());
        }
        return -1;
    }

    /**
     * A continuación se crea la función actualizar
     * @param entity Contiene la información de la entidad TipoRubro.
     * @return actualizar() Devuelve el resultado de la función actualizar
     *                      para modificar la entidad de una suspencion.
     */
    @Override
    public int actualizar(TipoRubro entity) {
        this.tipoRubro = entity;
        return actualizar();
    }

    /**
     * A continuación se crea la función buscarPorId
     * @param id Contiene el id del Tipo de rubro a buscar
     * @return lista.get() Devuelve el resultado de la búsqueda, dada por una
     *                     lista
     */
    @Override
    public TipoRubro buscarPorId(Object id) {
        List<TipoRubro> lista = buscar("id_tipo_rubro = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    /**
     * A continuación se crea la función Listar
     * @return buscar Devuelve el resultado de la búsqueda, con una condición
     *                de ordenar el ID del tipo de rubro de forma descendente
     */
    @Override
    public List<TipoRubro> Listar() {
        return buscar(null, null);
    }

    /**
     * A continuación se crea la función buscar
     * @param restricciones Contiene las restricciones de la búsqueda en la 
     *                      consulta de la BD
     * @param OrdenarAgrupar Contiene la restricción de agrupamiento de la
     *                       búsqueda en la consulta de la BD
     * @return roles Devuelve la lista de los roles
     *                      encontrados en la búsqueda.
     */
    private List<TipoRubro> buscar(@Nullable String restricciones, 
                                   @Nullable String OrdenarAgrupar) {
        if (conexion.isEstado()) {
            ResultSet result;
            List<TipoRubro> roles;
            try {
                result = conexion.selecionar("tipo_rubro", "id_tipo_rubro, "
                                           + "nombre, coeficiente",
                        restricciones, OrdenarAgrupar);
                roles = new ArrayList<>();
                while (result.next()) {
                    roles.add(new TipoRubro(result.getInt("id_tipo_rubro"), 
                                            result.getInt("coeficiente"), 
                                            result.getString("nombre")));
                }
                result.close();
                return roles;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return null;
    }
}