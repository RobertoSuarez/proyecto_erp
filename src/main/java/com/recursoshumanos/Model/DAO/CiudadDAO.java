/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Ciudad;
import com.recursoshumanos.Model.Entidad.Provincia;
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
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades para
 * todo lo que tenga que ver con Ciudades. Y se encarga de administrar las
 * sentencias de la BD, utilizando las clases de los modelos
 */
public class CiudadDAO implements IDAO<Ciudad> {

    private final Conexion conexion;
    private Ciudad ciudad;

    public CiudadDAO() {
        conexion = new Conexion();
        ciudad = new Ciudad();
    }

    public CiudadDAO(Conexion conexion) {
        this.conexion = conexion;
        ciudad = new Ciudad();
    }

    public CiudadDAO(Ciudad ciudad) {
        conexion = new Conexion();
        this.ciudad = ciudad;
    }

    public CiudadDAO(Conexion conexion, Ciudad ciudad) {
        this.conexion = conexion;
        this.ciudad = ciudad;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Llama a la clase conexión.
     *
     * @return conexion Objeto con los datos para validar la conexión.
     */
    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    /**
     * Creación del metedo INSERTAR, para registrar los datos recopilados de las
     * ciudades.
     *
     * @return ciudad Retorna la confirmación de un registro exitoso o erroneo.
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            ciudad.setId(conexion.insertar("ciudad", "id_provincia, "
                    + "nombre, detalle",
                    ciudad.getProvincia().getId() + ",'"
                    + ciudad.getNombre() + "', '" + ciudad.getDetalle() + "'",
                    "id_ciudad"));
            return ciudad.getId();
        }
        return -1;
    }

    /**
     * Metodo que verifica y controla las entidades o registros vacios que se
     * realicen al momento de insertar.
     *
     * @param entity Objeto con la información para la validación
     * correspondiente.
     * @return insertar Objeto con la información.
     */
    @Override
    public int insertar(Ciudad entity) {
        this.ciudad = entity;
        return insertar();
    }

    /**
     * A continuación se crea el método actualizar:
     *
     * @return conexion Objeto con la conexion con los datos correspondientes
     * para su modificación.
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("ciudad",
                    "id_provincia= " + ciudad.getProvincia().getId()
                    + ", nombre = '" + ciudad.getNombre() + "', detalle = '"
                    + ciudad.getDetalle() + "'", "id_ciudad = " + ciudad.getId());
        }
        return -1;
    }

    /**
     * Metodo que verifica y controla las entidades o registros vacios que se
     * realicen al momento de actualizar.
     *
     * @param entity Objeto que valida campos vacios
     * @return actualizar Objeto que retorna la actualización de o los elementos
     * en la BD.
     */
    @Override
    public int actualizar(Ciudad entity) {
        this.ciudad = entity;
        return actualizar();
    }

    /**
     * Metodo que permite buscar mediante el ID dentro de los registros.
     *
     * @param id Objeto encargado del ID del parametro de busqueda.
     * @return lista Retorna la lista de busqueda
     */
    @Override
    public Ciudad buscarPorId(Object id) {
        List<Ciudad> lista = buscar("id_ciudad = " + id, "nombre");
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    /**
     * Valida que no exista nulo en la busqueda
     *
     * @return buscar envia la lista del resultado
     */
    @Override
    public List<Ciudad> Listar() {
        return buscar(null, "nombre");
    }

    /**
     * Valida que no exista nulo en la busqueda y envia mediante un
     * identificador el ID para retornar el dato correspondiente.
     *
     * @param provincia Objeto con el identificador del dato.
     * @return lista retorna la lista de la ciudad.
     */
    public List<Ciudad> Listar(Provincia provincia) {
        List<Ciudad> lista = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("ciudad", "id_ciudad, nombre, "
                        + "detalle",
                        "id_provincia = " + provincia.getId(), "nombre DESC");
                while (result.next()) {
                    lista.add(new Ciudad(
                            result.getInt("id_ciudad"), provincia,
                            result.getString("nombre"),
                            result.getString("detalle")
                    ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return lista;
    }

    /**
     * Busca a la ciudad de acuerdo a su registro
     *
     * @param restricciones Objeto con las restricciones o validaciones de las
     * mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar los resultados.
     * @return List<Ciudad> Muestra la lista.
     */
    private List<Ciudad> buscar(@Nullable String restricciones,
            @Nullable String OrdenarAgrupar) {
        List<Ciudad> lista = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("ciudad", "id_ciudad, id_provincia, "
                        + "nombre, detalle", restricciones, OrdenarAgrupar);
                ProvinciaDAO pdao = new ProvinciaDAO();
                while (result.next()) {
                    lista.add(new Ciudad(
                            result.getInt("id_ciudad"),
                            pdao.buscarPorId(result.getInt(
                                    "id_provincia")),
                            result.getString("nombre"),
                            result.getString("detalle")
                    ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return lista;
    }
}
