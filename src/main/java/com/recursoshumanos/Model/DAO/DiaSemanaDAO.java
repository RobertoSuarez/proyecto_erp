/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.DiaSemana;
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
 * para todo lo que tenga que ver con Detalle Rol dePagos.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class DiaSemanaDAO implements IDAO<DiaSemana>{
    protected final Conexion conexion;
    protected DiaSemana diaSemana;

    public DiaSemanaDAO() {
        conexion = new Conexion();
        diaSemana = new DiaSemana();
    }

    public DiaSemanaDAO(DiaSemana diaSemana) {
        conexion = new Conexion();
        this.diaSemana = diaSemana;
    }

    public DiaSemanaDAO(Conexion conexion) {
        this.conexion = conexion;
        diaSemana = new DiaSemana();
    }

    public DiaSemanaDAO(Conexion conexion, DiaSemana diaSemana) {
        this.conexion = conexion;
        this.diaSemana = diaSemana;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
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
     * Metodo que permite buscar mediante el ID
     * dentro de los registros.
     * @param id Objeto encargado del ID del parametro
     * de busqueda.
     * @return lista Retorna la lista de busqueda indicada
     */
    @Override
    public DiaSemana buscarPorId(Object id) {
        List<DiaSemana> lista = buscar("id_dia = " + id, "nombre_dia");
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
    public List<DiaSemana> Listar() {
        return buscar(null, "nombre_dia");
    }

    /**
     * Creación del metedo INSERTAR, para registrar
     * los datos recopilados de la clase de DiaSemanaDAO.
     * @return diaSemana Retorna la confirmación
     * de un registro exitoso o erroneo dependiendo de la correcta
     * ejecución de la sentencia SELECT.
     */
    @Override
    public int insertar() {
           if (conexion.isEstado()) {
            diaSemana.setId(conexion.insertar("dia_semana", "nombre_dia", "'" + diaSemana.getNombre() + "'", "id_dia"));
            return diaSemana.getId();
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
    public int insertar(DiaSemana entity) {
        this.diaSemana = entity;
        return insertar();
    }

    /**
     * A continuación se crea el método actualizar el cual
     * permite modificar los datos en la BD.
     * @return conexion Objeto con la conexion
     * con los datos correspondientes para su modificación.
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("dia_semana", "nombre_dia = '" + diaSemana.getNombre() + "'", "id_dia = " + diaSemana.getId());
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
    public int actualizar(DiaSemana entity) {
        this.diaSemana = entity;
        return actualizar();
    }
        
    /**
     * Busca el Día de la Semana de acuerdo a su registro
     * @param restricciones Objeto con las restricciones
     * o validaciones de las mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar
     * los resultados. 
     * @return List<DiaSemana> Muestra la lista.
     */
    private List<DiaSemana> buscar( @Nullable String restricciones, @Nullable String OrdenarAgrupar){
        if (conexion.isEstado()) {
            ResultSet result;
            List<DiaSemana> diasSemana;
            try {
                result = conexion.selecionar("dia_semana", "id_dia, nombre_dia", restricciones, OrdenarAgrupar);
                diasSemana = new ArrayList<>();
                while (result.next()) {
                    diasSemana.add(
                            new DiaSemana(
                                    result.getInt("id_dia"),
                                    result.getString("nombre_dia"))
                            );
                }
                result.close();
                return diasSemana;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return null;
    }
}