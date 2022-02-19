/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.HorarioLaboral;
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
 * Clase tipo DAO que se encargará de proporcionar 
 * ciertas funcionalidades
 * para todo lo que tenga que ver con Horario Laboral.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class HorarioLaboralDAO implements IDAO<HorarioLaboral>{
    protected final Conexion conexion;
    private HorarioLaboral horarioLaboral;

    public HorarioLaboralDAO() {
        conexion = new Conexion();
        horarioLaboral = new HorarioLaboral();
    }

    public HorarioLaboralDAO(Conexion conexion) {
        this.conexion = conexion;
        horarioLaboral = new HorarioLaboral();
    }

    public HorarioLaboralDAO(HorarioLaboral horarioLaboral) {
        conexion = new Conexion();
        this.horarioLaboral = horarioLaboral;
    }

    /**
     * Método que usa a la conexion para obtener 
     * los datos de Horario Laboral.
     * @param conexion Objeto que maneja la conexión hacia la
     * Base de Datos.
     * @param horarioLaboral Objeto que administra los datos
     * de los Horarios Laborales
     */
    public HorarioLaboralDAO(Conexion conexion, HorarioLaboral horarioLaboral) {
        this.conexion = conexion;
        this.horarioLaboral = horarioLaboral;
    }

    /**
     * Método GET de la Horario Laboral
     * @return horarioLaboral Retorna el dato de la empresa
     */
    public HorarioLaboral getHorarioLaboral() {
        return horarioLaboral;
    }

    /**
     * Método SET de Horario Laboral
     * @param horarioLaboral Envia los datos del Puesto 
     * Laboral
     */
    public void setHorarioLaboral(HorarioLaboral horarioLaboral) {
        this.horarioLaboral = horarioLaboral;
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
     * los datos recopilados de los Horarios Laborales.
     * @return empresa Retorna la confirmación
     * de un registro exitoso o erroneo.
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            horarioLaboral.setId(conexion.insertar("horario_laboral",
                    "nombre, estado, observaciones, fecha_vigencia",
                    "'" + horarioLaboral.getNombre() + "', "+ horarioLaboral.isEstado() + ",'" + horarioLaboral.getObservaciones()+ "','" + horarioLaboral.getFechaVigencia() + "'",
                    "id_horario_laboral"));
            return horarioLaboral.getId();
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
    public int insertar(HorarioLaboral entity) {
        this.horarioLaboral = entity;
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
            return conexion.modificar("horario_laboral",
                    "nombre= '" + horarioLaboral.getNombre() + "', estado = " + horarioLaboral.isEstado() + ", observaciones = '" + horarioLaboral.getObservaciones()+ "', fecha_vigencia ='" + horarioLaboral.getFechaVigencia() + "'" ,
                    "id_horario_laboral = " + horarioLaboral.getId());
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
    public int actualizar(HorarioLaboral entity) {
        this.horarioLaboral = entity;
        return actualizar();
    }
    
    /**
     * Modifica el estado del Empleado, ya sea
     * ha Activado o Desactivado
     */
    public void cambiarEstado(){
        if (conexion.isEstado()) {
            conexion.modificar("horario_laboral", "estado = NOT estado", "id_horario_laboral = " + horarioLaboral.getId());
        }
    }

    /**
     * Metodo que permite buscar mediante el ID
     * dentro de los registros.
     * @param id Objeto encargado del ID del parametro
     * de busqueda.
     * @return lista Retorna la lista de busqueda
     */
    @Override
    public HorarioLaboral buscarPorId(Object id) {
        List<HorarioLaboral> lista = buscar("id_horario_laboral = " + id, "nombre");
        if(lista != null && !lista.isEmpty()){
                return lista.get(0);
        }
        return null;
    }

    /**
     * Lista por nombre
     * @return buscar Objeto con el nombre de retorno
     */
    @Override
    public List<HorarioLaboral> Listar() {
        return buscar(null, "nombre");
    }
    
    public List<HorarioLaboral> Activos() {
        return buscar("estado = true", "nombre");
    }
    /**
     * Busca el departamento de acuerdo a su registro
     * @param restricciones Objeto con las restricciones
     * o validaciones de las mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar
     * los resultados. 
     * @return List<HorarioLaboral> Muestra la lista.
     */
    private List<HorarioLaboral> buscar( @Nullable String restricciones, @Nullable String OrdenarAgrupar){
        if (conexion.isEstado()) {
            ResultSet result;
            List<HorarioLaboral> horarios;
            try {
                result = conexion.selecionar("horario_laboral", "id_horario_laboral, nombre, estado, observaciones, fecha_vigencia", restricciones, OrdenarAgrupar);
                horarios = new ArrayList<>();
                while (result.next()) {
                    horarios.add(
                            new HorarioLaboral(
                                    result.getInt("id_horario_laboral"),
                                    result.getString("nombre"),
                                    result.getBoolean("estado"),
                                    result.getString("observaciones"),
                                    result.getDate("fecha_vigencia")
                            )
                    );
                }
                result.close();
                return horarios;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return null;
    }
}