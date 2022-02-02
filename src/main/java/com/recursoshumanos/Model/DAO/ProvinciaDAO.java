/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Provincia;
import com.recursoshumanos.Model.Interfaces.IDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/**
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 *
 * Clase tipo DAO con las funciones para la manipulación de provincias
 * Administrar las sentencias de la BD, utilizando las clases de los modelos
 */
public class ProvinciaDAO implements IDAO<Provincia> {

    private final Conexion conexion;
    private Provincia provincia;

    public ProvinciaDAO() {
        conexion = new Conexion();
        provincia = new Provincia();
    }

    public ProvinciaDAO(Conexion conexion) {
        this.conexion = conexion;
        provincia = new Provincia();
    }

    public ProvinciaDAO(Provincia provincia) {
        conexion = new Conexion();
        this.provincia = provincia;
    }

    public ProvinciaDAO(Conexion conexion, Provincia provincia) {
        this.conexion = conexion;
        this.provincia = provincia;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    /**
     * Ejecuta la inserción de los atributos del objeto provincia mediante el
     * método insertar del objeto conexión
     *
     * @return provincia.getId(), el id de la persona que se ha agregado o un -1 si no
     * se pudo
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            provincia.setId(conexion.insertar("provincia", "provincia, detalle ",
                    "'" + provincia.getNombre() + "','" 
                    + provincia.getDetailsProvincia() +"'" , "cod"));
            return provincia.getId();
        }
        return -1;
    }

    /**
     * Toma un objeto tipo provincia y lo copia al objeto provincia propio de la
     * clase
     *
     *
     * @param entity, objeto de tipo provincia
     * @return insertar(), manda a ejecutar al otro método insertar de la clase
     */
    @Override
    public int insertar(Provincia entity) {
        this.provincia = entity;
        return insertar();
    }

    /**
     * Ejecuta la actualización mediante le método modificar del objeto conexión
     *
     * @return conexion.modificar(), el id de la provincia que se ha modificado o un -1 si
     * no se pudo
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("provincia",
                    "provincia = '" + provincia.getNombre() + "',"
                    + "detalle = '" + provincia.getDetailsProvincia()+"'",
                    "cod = " + provincia.getId());
        }
        return -1;
    }

    /**
     * Toma un objeto tipo provincia y lo copia al objeto provincia propio de la
     * clase
     *
     * @param entity, objeto de tipo provincia
     * @return actualizar(), manada a ejecutar al otro método actualizar
     */
    @Override
    public int actualizar(Provincia entity) {
        this.provincia = entity;
        return actualizar();
    }

    /**
     * Busca por una persona por su id.
     *
     * @param id de la provincia a buscar
     * @return lista.get(0), provincia encontrada o null en caso de no hallarla
     */
    @Override
    public Provincia buscarPorId(Object id) {
        List<Provincia> lista = buscar("cod = " + id, "provincia");
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    /**
     * Lista las provincia
     *
     * @return buscar, ejecuta método buscar
     */
    @Override
    public List<Provincia> Listar() {
        return buscar(null, "provincia");
    }

    /**
     * Lista las provincias en base a los dos parámetros de entrada
     *
     * @param restricciones, las restricciones para la búsqueda
     * @param OrdenarAgrupar forma en que se desea ordenar
     * @return lista, lista con las provincias ordenandas
     */
    private List<Provincia> buscar(@Nullable String restricciones,
            @Nullable String OrdenarAgrupar) {
        if (conexion.isEstado()) {
            ResultSet result;
            List<Provincia> lista;
            try {
                result = conexion.selecionar("provincia", "cod, provincia,detalle",
                        restricciones, OrdenarAgrupar);
                lista = new ArrayList<>();
                while (result.next()) {
                    lista.add(new Provincia(
                            result.getInt("cod"),
                            result.getString("provincia"),
                            result.getString("detalle")
                    ));
                }
                result.close();
                return lista;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return null;

    }
}
