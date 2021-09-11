/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Sucursal;
import com.recursoshumanos.Model.Interfaces.IDAO;
import org.jetbrains.annotations.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kestradalp
 * @author rturr
 * @author ClasK7
 * 
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades
 * para todo lo que tenga que ver con Sucursales.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class SucursalDAO implements IDAO<Sucursal> {

    protected final Conexion conexion;
    protected Sucursal sucursal;
    
    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y Sucursal.
     */
    public SucursalDAO() {
        conexion = new Conexion();
        sucursal = new Sucursal();
    }
    
    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y Sucursal.
     * 
     * @param sucursal Objeto de tipo Sucursal con la información de una
     *                 sucursal.
     */
    public SucursalDAO(Sucursal sucursal) {
        conexion = new Conexion();
        this.sucursal = sucursal;
    }

    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y Sucursal.
     * 
     * @param conexion Objeto de tipo Conexion con la información de la
     *                 conexion.
     */
    public SucursalDAO(Conexion conexion) {
        this.conexion = conexion;
        sucursal = new Sucursal();
    }

    /**
     * Constructor que inicia una nueva conexion e
     * indica dos instancias de tipo Conexion y Sucursal.
     * 
     * @param conexion Objeto de tipo Conexion con la información de la
     *                 conexion.
     * @param sucursal Objeto de tipo Sucursal con la información de la
     *                 sucursal.
     */
    public SucursalDAO(Sucursal sucursal, Conexion conexion) {
        this.conexion = conexion;
        this.sucursal = sucursal;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
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
     * @return sucursal.getId() devuelve el ID de la sucursal creada
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            sucursal.setId(conexion.insertar("sucursal", "id_matriz, id_ciudad, "
                    + "direccion, detalle",
                    sucursal.getEmpresa().getId() + "," 
                    + sucursal.getCiudad().getId() + ", '"
                    + sucursal.getDireccion()
                    + "', '" + sucursal.getDetalle() 
                    + "'", "id_sucursal"));
            return sucursal.getId();
        }
        return -1;
    }

    /**
     * A continuación se crea la función insertar
     * @param entity Contiene la información de la entidad Sucursal
     * @return insertar() Devuelve la función con la entidad dentro
     */
    @Override
    public int insertar(Sucursal entity) {
        this.sucursal = entity;
        return insertar();
    }

    /**
     * A continuación se crea la función actualizar
     * @return conexion.modificar() Devuelve el resultado de la función
     *                              modificar para actualizar una sucursal.
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("sucursal", "id_matriz = " 
                    + sucursal.getEmpresa().getId() + ", id_ciudad = "
                    + sucursal.getCiudad().getId() + ", direccion = '" 
                    + sucursal.getDireccion() + "', detalle = '"
                    + sucursal.getDetalle() + "'", "id_sucursal = "
                    + sucursal.getId());
        }
        return -1;
    }

    /**
     * A continuación se crea la función actualizar
     * @param entity Contiene la información de la entidad Sucursal
     * @return actualizar() Devuelve el resultado de la función actualizar
     *                      para modificar la entidad de una sucursal.
     */
    @Override
    public int actualizar(Sucursal entity) {
        this.sucursal = entity;
        return actualizar();
    }

    /**
     * A continuación se crea la función buscarPorId
     * @param id Contiene el id de la sucursal a buscar
     * @return lista.get() Devuelve el resultado de la búsqueda, dada por una
     *                     lista
     */
    @Override
    public Sucursal buscarPorId(Object id) {
        List<Sucursal> lista = buscar("id_sucursal = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }
    
    /**
     * A continuación se crea la función Listar
     * @return buscar Devuelve el resultado de la búsqueda, con una condición
     *                de ordenar el ID de la sucursal de forma ascendente
     */
    @Override
    public List<Sucursal> Listar() {
        return buscar(null, "id_sucursal ASC");
    }

    /**
     * A continuación se crea la función buscar
     * @param restricciones Contiene las restricciones de la búsqueda en la 
     *                      consulta de la BD
     * @param OrdenarAgrupar Contiene la restricción de agrupamiento de la
     *                       búsqueda en la consulta de la BD
     * @return sucursales Devuelve la lista de las sucursales encontradas en la
     *                    búsqueda.
     */
    private List<Sucursal> buscar(@Nullable String restricciones, 
                                  @Nullable String OrdenarAgrupar) {
        if (conexion.isEstado()) {
            ResultSet result;
            List<Sucursal> sucursales;
            try {
                result = conexion.selecionar("sucursal", "id_sucursal, "
                                           + "id_matriz, id_ciudad, direccion, "
                                           + "detalle", restricciones, 
                                           OrdenarAgrupar);
                sucursales = new ArrayList<>();
                EmpresaDAO edao = new EmpresaDAO();
                CiudadDAO cdao = new CiudadDAO();
                while (result.next()) {
                    sucursales.add(new Sucursal(result.getInt("id_sucursal"),
                            edao.buscarPorId(result.getInt("id_matriz")),
                            cdao.buscarPorId(result.getInt("id_ciudad")),
                            result.getString("direccion"),
                            result.getString("detalle")
                    ));
                }
                result.close();
                return sucursales;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return null;
    }
}
