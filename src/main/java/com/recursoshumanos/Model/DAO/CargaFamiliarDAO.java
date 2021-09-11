/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.CargaFamiliar;
import com.recursoshumanos.Model.Entidad.Empleado;
import com.recursoshumanos.Model.Interfaces.IDAO;
import org.jetbrains.annotations.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 * 
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades
 * para todo lo que tenga que ver con CargaFamiliar.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class CargaFamiliarDAO implements IDAO<CargaFamiliar> {

    private final Conexion conexion;
    private CargaFamiliar cargaFamiliar;

    public CargaFamiliarDAO() {
        conexion = new Conexion();
        cargaFamiliar = new CargaFamiliar();
    }

    public CargaFamiliarDAO(CargaFamiliar cargaFamiliar) {
        conexion = new Conexion();
        this.cargaFamiliar = cargaFamiliar;
    }

    public CargaFamiliarDAO(Conexion conexion) {
        this.conexion = conexion;
        cargaFamiliar = new CargaFamiliar();
    }

    public CargaFamiliarDAO(CargaFamiliar cargaFamiliar, Conexion conexion) {
        this.conexion = conexion;
        this.cargaFamiliar = cargaFamiliar;
    }

    public CargaFamiliar getCargaFamiliar() {
        return cargaFamiliar;
    }

    public void setCargaFamiliar(CargaFamiliar cargaFamiliar) {
        this.cargaFamiliar = cargaFamiliar;
    }

    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    /**
     * Creación del metedo INSERTAR, para registrar
     * los datos recopilados de cargas familiares
     * @return cargaFamiliar Retorna la confirmación
     * de un registro exitoso o erroneo.
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            cargaFamiliar.setId(conexion.insertar("carga_familiar",
                    "id_empleado, cantidad_carga, fecha_cambio, "
                    + "nombre, documento_validacion, detalle",
                    cargaFamiliar.getEmpleado().getId() + ", " 
                    + cargaFamiliar.getFaliares() + ", CURRENT_TIMESTAMP, '"
                    + cargaFamiliar.getConyuge() + "','', '" 
                    + cargaFamiliar.getDetalle() + "'",
                    "id_cargaf"));
            return cargaFamiliar.getId();
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
    public int insertar(CargaFamiliar entity) {
        this.cargaFamiliar = entity;
        return insertar();
    }

    /**
     * A continuación se crea el método actualizar: 
     * @return conexion Objeto con la conexion
     * con los datos correspondientes para su modificación
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("carga_familiar",
                    "id_empleado = " + cargaFamiliar.getEmpleado().getId() 
                  + ", cantidad_carga = " + cargaFamiliar.getFaliares()
                    + ", fecha_cambio = '" + cargaFamiliar.getFechaCambio()
                    + "', nombre = '" + cargaFamiliar.getConyuge()
                    + "', detalle = '" 
                    + cargaFamiliar.getDetalle() + "'",
                    "id_cargaf = " + cargaFamiliar.getId());
        }
        return -1;
    }

    /**
     * A continuación se crea el método actualizar con
     * una CargaFamiliar vacia para controles (validaciones):
     * @param entity Objeto que valida campos vacios
     * @return Objeto que retorna la actualización de o los
     * elementos en la BD.
     */
    @Override
    public int actualizar(CargaFamiliar entity) {
        this.cargaFamiliar = entity;
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
    public CargaFamiliar buscarPorId(Object id) {
        List<CargaFamiliar> lista = buscar("id_cargaf = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return new CargaFamiliar(0,0, new Empleado(), "N/D", "N/D", 
                                 new Date() );
    }

    /**
     * Permite buscar a un empleado en específico 
     * @param empleado Objeto que envia el dato de busquedad
     * mediante la setencia SELECT para obtener el dato.
     * @return CargaFamiliar Objeto con los datos.
     */
    public CargaFamiliar buscar(Empleado empleado) {
        cargaFamiliar = new CargaFamiliar(-1,0, empleado, "N/D", "N/D", 
                                          new Date() );
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("carga_familiar",
                        "id_cargaf, cantidad_carga, fecha_cambio, "
                      + "nombre, detalle",
                        "id_empleado = " + empleado.getId(), null);
                while (result.next()) {
                    cargaFamiliar.setId(result.getInt("id_cargaf"));
                    cargaFamiliar.setFaliares(result.getInt("cantidad_carga"));
                    cargaFamiliar.setConyuge(result.getString("nombre"));
                    cargaFamiliar.setDetalle(result.getString("detalle"));
                    cargaFamiliar.setFechaCambio(result.getDate("fecha_cambio"));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return cargaFamiliar;
    }

    /**
     * Genera un listado de manera DESCENDENTE o
     * ordena el resultado presentado por pantalla.
     * @return buscar Regresa la lista con los datos
     * ordenados para el usuario.
     */
    @Override
    public List<CargaFamiliar> Listar() {
        return buscar(null, "fecha_cambio DESC");
    }

    /**
     * Busca al empleado de acuerdo a su registro
     * @param restricciones Objeto con las restricciones
     * o validaciones de las mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar
     * los resultados.
     * @return List<CargaFamiliar> Muestra una lista.
     */
    private List<CargaFamiliar> buscar(@Nullable String restricciones, 
                                       @Nullable String OrdenarAgrupar) {
        List<CargaFamiliar> cargaFamiliares = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("carga_familiar", "id_cargaf, "
                                           + "id_empleado, cantidad_carga, "
                                           + "fecha_cambio, nombre,  detalle", 
                                            restricciones, OrdenarAgrupar);
                EmpleadoDAO edao = new EmpleadoDAO();
                while (result.next()) {
                    cargaFamiliares.add(new CargaFamiliar(
                            result.getInt("id_cargaf"), 
                            result.getInt("cantidad_carga"),
                            edao.buscarPorId(result.getInt("id_empleado")),
                            result.getString("nombre"), 
                            result.getString("detalle"),
                            result.getDate("fecha_cambio")
                    ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return cargaFamiliares;
    }
}