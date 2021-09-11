/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Interfaces.IDAO;
import com.recursoshumanos.Model.Entidad.Cargo;
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
 * para todo lo que tenga que ver con Cargo.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class CargoDAO implements IDAO<Cargo>{
    private final Conexion conexion;
    private Cargo cargo;
    
    public CargoDAO() {
        this.conexion = new Conexion();
        this.cargo = new Cargo();
    }

    public CargoDAO(Cargo cargo) {
        this.conexion = new Conexion();
        this.cargo = cargo;
    }

    public CargoDAO(Conexion conexion) {
        this.conexion = conexion;
        this.cargo = new Cargo();
    }

    public CargoDAO(Cargo cargo, Conexion conexion) {
        this.conexion = conexion;
        this.cargo = cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    /**
     * Llama a la clase conexión.
     * @return conexion Objeto con los datos para validar la conexión.
     */
    @Override
    public Conexion obtenerConexion() {
        return this.conexion;
    }

    /**
     * Metodo que verifica y controla las entidades o registros
     * vacios que se realicen al momento de insertar.
     * @param entity Objeto con la información para
     * la validación correspondiente.
     * @return insertar Objeto con la información.
     */
    @Override
    public int insertar(Cargo entity) {
        this.cargo = entity;
      return insertar();
    }

    /**
     * Creación del metedo INSERTAR, para registrar
     * los datos recopilados de los cargos
     * @return cargo Retorna la confirmación
     * de un registro exitoso o erroneo.
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            cargo.setId(conexion.insertar("cargo", "nombre", 
                        "'" + cargo.getNombre() + "'", "id_cargo"));
            return cargo.getId();
        }
        return -1;
    }

    /**
     * A continuación se crea el método actualizar con
     * un Cargo vacia para controles (validaciones):
     * @param entity Objeto que valida campos vacios
     * @return actualizar Objeto que retorna la actualización de o los
     * elementos en la BD.
     */
    @Override
    public int actualizar(Cargo entity) {
        this.cargo = entity;
        return actualizar();
    }

    /**
     * A continuación se crea el método actualizar: 
     * @return conexion Objeto con la conexion
     * con los datos correspondientes para su modificación.
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("cargo", "nombre = '" 
                                      + cargo.getNombre() + "'", "id_cargo = " 
                                      + cargo.getId());
        }
        return -1;
    }

    /**
     * Metodo que permite buscar mediante el ID
     * dentro de los registros.
     * @param id Objeto encargado del ID del parametro
     * de busqueda.
     * @return lista Retorna la lista de busqueda
     */
    @Override
    public Cargo buscarPorId(Object id) {
        List<Cargo> lista = buscar("id_cargo = " + id, "nombre");
        if(lista != null && !lista.isEmpty()){
                return lista.get(0);
        }
        return null;
    }
    
    /**
     * Valida que no exista nulo en la busqueda
     * @return buscar envia la lista del resultado
     */
    @Override
    public List<Cargo> Listar() {
        return buscar(null, "nombre");
    }

    /**
     * Busca al empleado de acuerdo a su registro
     * @param restricciones Objeto con las restricciones
     * o validaciones de las mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar
     * los resultados.
     * @return List<Cargo> Muestra la lista.
     */
    private List<Cargo> buscar(@Nullable String restricciones, 
                               @Nullable String OrdenarAgrupar){
        if (conexion.isEstado()) {
            ResultSet result;
            List<Cargo> cargos;
            try {
                result = conexion.selecionar("cargo", "id_cargo, nombre", 
                                             restricciones, OrdenarAgrupar);
                cargos = new ArrayList<>();
                while (result.next()) {
                    cargos.add(new Cargo(result.getInt("id_cargo"),
                                result.getString("nombre")));
                }
                result.close();
                return cargos;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return null;
    }
}