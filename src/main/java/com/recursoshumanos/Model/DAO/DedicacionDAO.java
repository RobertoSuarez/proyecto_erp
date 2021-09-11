/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Dedicacion;
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
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades
 * para todo lo que tenga que ver con Dedicación, Esta clase 
 * tiene muchas funciones que son reutilizables.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class DedicacionDAO  implements  IDAO<Dedicacion>{
    private final Conexion conexion;
    private Dedicacion dedicacion;
    public DedicacionDAO(){
        conexion = new Conexion();
        dedicacion = new Dedicacion();
    }
    
    public DedicacionDAO(Conexion conexion){
        this.conexion = conexion;
        dedicacion = new Dedicacion();
    }
    
    public DedicacionDAO(Dedicacion dedicacion){
        conexion = new Conexion();
        this.dedicacion = dedicacion;
    }
    
    public DedicacionDAO(Conexion conexion, Dedicacion dedicacion){
        this.conexion = conexion;
        this.dedicacion = dedicacion;
    }

    public Dedicacion getDedicacion() {
        return dedicacion;
    }

    public void setDedicacion(Dedicacion dedicacion) {
        this.dedicacion = dedicacion;
    }

    /**
     * Llama a la clase conexión.
     * @return conexion Objeto con los datos para validar la conexión.
     */
    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    /**
     * Creación del metedo INSERTAR, para registrar
     * los datos recopilados de las ciudades.
     * @return dedicacion Retorna la confirmación
     * de un registro exitoso o erroneo.
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            dedicacion.setId(conexion.insertar("dedicacion", "nombre, porcentaje_utilidad, detalle",
                    "'" + dedicacion.getNombre()+ "','" + dedicacion.getPorcentajeUtilidad()+ "', '" + dedicacion.getDetalle()+ "'",
                    "id_dedicacion"));
            return dedicacion.getId();
        }
        return -1;
    }

    /**
     * Metodo que verifica y controla las entidades o registros
     * vacios que se realicen al momento de insertar.
     * @param entity Objeto con la información para
     * la validación correspondiente.
     * @return insertar Objeto con la información.
     */
    @Override
    public int insertar(Dedicacion entity) {
        this.dedicacion = entity;
        return insertar();
    }

    /**
     * A continuación se crea el método actualizar:
     * @return conexion Objeto con la conexion
     * con los datos correspondientes para su modificación.
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("dedicacion",
                    "nombre = '" + dedicacion.getNombre()+ "', porcentaje_utilidad = '" + dedicacion.getPorcentajeUtilidad()+ "', detalle = '" + dedicacion.getDetalle()+ "'",
                    "id_dedicacion = " + dedicacion.getId());
        }
        return -1;
    }

    /**
     * Metodo que verifica y controla las entidades o registros
     * vacios que se realicen al momento de actualizar.
     * @param entity Objeto que valida campos vacios
     * @return actualizar Objeto que retorna la actualización de o los
     * elementos en la BD.
     */
    @Override
    public int actualizar(Dedicacion entity) {
        this.dedicacion = entity;
        return actualizar();
    }

    /**
     * Metodo que permite buscar mediante el ID
     * dentro de los registros.
     * @param id Objeto encargado del ID del parametro
     * de busqueda.
     * @return List<Dedicacion> Retorna la lista de busqueda
     */
    @Override
    public Dedicacion buscarPorId(Object id) {
        List<Dedicacion> lista = buscar("id_dedicacion = " + id, "nombre");
        if(lista != null && !lista.isEmpty()){
                return lista.get(0);
        }
        return null;
    }

    /**
     * Valida que no exista nulo en la busqueda
     * @return List<Dedicacion> envia la lista del resultado
     */
    @Override
    public List<Dedicacion> Listar() {
        return buscar(null, "nombre");
    }
    
    /**
     * Busca al empleado de acuerdo a su registro
     * @param restricciones Objeto con las restricciones
     * o validaciones de las mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar
     * los resultados. 
     * @return List<Dedicacion> Muestra la lista.
     */
    private List<Dedicacion> buscar( @Nullable String restricciones, @Nullable String OrdenarAgrupar){
        if (conexion.isEstado()) {
            ResultSet result;
            List<Dedicacion> lista;
            try {
                result = conexion.selecionar("dedicacion", "id_dedicacion, nombre, porcentaje_utilidad, detalle", restricciones, OrdenarAgrupar);
                lista = new ArrayList<>();
                while (result.next()) {
                    lista.add(
                            new Dedicacion(
                                    result.getInt("id_dedicacion"), 
                                    result.getString("nombre"),
                                    result.getFloat("porcentaje_utilidad"),
                                    result.getString("detalle")
                            )
                    );
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
