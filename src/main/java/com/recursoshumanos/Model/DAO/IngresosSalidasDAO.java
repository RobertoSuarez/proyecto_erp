/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.IngresosSalidas;
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
public class IngresosSalidasDAO implements IDAO<IngresosSalidas>{
    private final Conexion conexion;
    private IngresosSalidas ingresosSalidas;

    public IngresosSalidasDAO() {
        conexion = new Conexion();
        ingresosSalidas = new IngresosSalidas();
    }

    public IngresosSalidasDAO(Conexion conexion) {
        this.conexion = conexion;
        ingresosSalidas = new IngresosSalidas();
    }

    public IngresosSalidasDAO(IngresosSalidas ingresosSalidas) {
        conexion = new Conexion();
        this.ingresosSalidas = ingresosSalidas;
    }

    public IngresosSalidasDAO(Conexion conexion, IngresosSalidas ingresosSalidas) {
        this.conexion = conexion;
        this.ingresosSalidas = ingresosSalidas;
    }

    public IngresosSalidas getIngresosSalidas() {
        return ingresosSalidas;
    }

    public void setIngresosSalidas(IngresosSalidas ingresosSalidas) {
        this.ingresosSalidas = ingresosSalidas;
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
     * los datos recopilados de los Ingresos y Salidas.
     * @return empresa Retorna la confirmación
     * de un registro exitoso o erroneo.
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            ingresosSalidas.setId(conexion.insertar("ingresos_salidas",
                    "hora_ingreso, hora_salida, observaciones",
                    "'" + ingresosSalidas.getHoraIngreso()+ "','" + ingresosSalidas.getHoraSalida() + "', '" + ingresosSalidas.getObservaciones()+ "'",
                    "id_ingreso_salida"));
            return ingresosSalidas.getId();
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
    public int insertar(IngresosSalidas entity) {
        this.ingresosSalidas = entity;
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
            return conexion.modificar("ingresos_salidas",
                    "hora_ingreso= '" + ingresosSalidas.getHoraIngreso()+ "', hora_salida = '" + ingresosSalidas.getHoraSalida()+ "', observaciones = '" + ingresosSalidas.getObservaciones() + "'",
                    "id_ingreso_salida = " + ingresosSalidas.getId());
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
    public int actualizar(IngresosSalidas entity) {
        this.ingresosSalidas = entity;
        return actualizar();
    }

    /**
     * Metodo que permite buscar mediante el ID
     * dentro de los registros.
     * @param id Objeto encargado del ID del parametro
     * de busqueda.
     * @return lista Retorna la lista de busqueda
     */
    @Override
    public IngresosSalidas buscarPorId(Object id) {
        List<IngresosSalidas> lista = buscar("id_ingreso_salida = " + id, "hora_ingreso, hora_salida");
        if(lista != null && !lista.isEmpty()){
                return lista.get(0);
        }
        return null;
    }

    /**
     * Lista por hora de salida e ingreso
     * @return buscar Objeto con el nombre de retorno
     */
    @Override
    public List<IngresosSalidas> Listar() {
       return buscar(null, "hora_ingreso, hora_salida");
    }
    
    /**
     * Busca el departamento de acuerdo a su registro
     * @param restricciones Objeto con las restricciones
     * o validaciones de las mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar
     * los resultados. 
     * @return List<IngresosSalidas> Muestra la lista.
     */
    private List<IngresosSalidas> buscar( @Nullable String restricciones, @Nullable String OrdenarAgrupar){
        if (conexion.isEstado()) {
            ResultSet result;
            List<IngresosSalidas> lista;
            try {
                result = conexion.selecionar("ingresos_salidas", "id_ingreso_salida, hora_ingreso, hora_salida, observaciones", restricciones, OrdenarAgrupar);
                lista = new ArrayList<>();
                while (result.next()) {
                    lista.add(
                            new IngresosSalidas(
                                    result.getInt("id_ingreso_salida"), 
                                    result.getString("hora_ingreso"),
                                    result.getString("hora_salida"),
                                    result.getString("observaciones")
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