/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Interfaces.IDAO;
import com.recursoshumanos.Model.Entidad.Departamento;
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
 * para todo lo que tenga que ver con Ciudades, Esta clase 
 * tiene muchas funciones que son reutilizables.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class DepartamentoDAO implements IDAO<Departamento> {

    protected final Conexion conexion;
    protected Departamento departamento;

    public DepartamentoDAO() {
        conexion = new Conexion();
        departamento = new Departamento();
    }

    public DepartamentoDAO(Departamento departamento) {
        conexion = new Conexion();
        this.departamento = departamento;
    }

    public DepartamentoDAO(Conexion conexion) {
        this.conexion = conexion;
        departamento = new Departamento();
    }

    public DepartamentoDAO(Departamento departamento, Conexion conexion) {
        this.conexion = conexion;
        this.departamento = departamento;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
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
     * los datos recopilados de los departamentos.
     * @return ciudad Retorna la confirmación
     * de un registro exitoso o erroneo.
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            departamento.setEstado(true);
            departamento.setId(conexion.insertar("departamento",
                    "nombre, estado, fecha_creacion, descripcion",
                    "'" + departamento.getNombre() + "', " + departamento.isEstado() + ", CURRENT_TIMESTAMP , '" + departamento.getDescripcion() + "'",
                    "id_departamento"));
            return departamento.getId();
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
    public int insertar(Departamento entity) {
        this.departamento = entity;
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
            return conexion.modificar("departamento",
                    "nombre = '" + departamento.getNombre() + "', estado = " + departamento.isEstado() + ", descripcion = '" + departamento.getDescripcion() + "'",
                    "id_departamento = " + departamento.getId());
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
    public int actualizar(Departamento entity) {
        this.departamento = entity;
        return actualizar();
    }

    /**
     * Metodo que permite buscar mediante el ID
     * dentro de los registros.
     * @param id Objeto encargado del ID del parametro
     * de busqueda.
     * @return List<Departamento> Retorna la lista de busqueda
     */
    @Override
    public Departamento buscarPorId(Object id) {
        List<Departamento> lista = buscar("id_departamento = " + id, "nombre");
        if(lista != null && !lista.isEmpty()){
                return lista.get(0);
        }
        return null;
    }

    /**
     * Valida que no exista nulo en la busqueda
     * @return List<Departamento> envia la lista del resultado
     */
    @Override
    public List<Departamento> Listar() {
        return buscar(null, "nombre");
    }

    /**
     * Busca el departamento de acuerdo a su registro
     * @param restricciones Objeto con las restricciones
     * o validaciones de las mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar
     * los resultados. 
     * @return List<Departamento> Muestra la lista.
     */
    private List<Departamento> buscar( @Nullable String restricciones, @Nullable String OrdenarAgrupar){
        if (conexion.isEstado()) {
            ResultSet result;
            List<Departamento> departamentos;
            try {
                result = conexion.selecionar("departamento", "id_departamento, nombre, estado, fecha_creacion, descripcion", restricciones, OrdenarAgrupar);
                departamentos = new ArrayList<>();
                while (result.next()) {
                    departamentos.add(new Departamento(result.getInt("id_departamento"),
                            result.getString("nombre"),
                            result.getBoolean("estado"),
                            result.getDate("fecha_creacion"),
                            result.getString("descripcion")
                    ));
                }
                result.close();
                return departamentos;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return null;
    }

    /**
     * Modifica el estado del Departamento, ya sea
     * ha Activado o Desactivado
     */
    public void cambiarEstado() {
        if (conexion.isEstado()) {
            conexion.modificar("departamento", "estado = NOT estado","id_departamento = " + departamento.getId());
        }
    }
}