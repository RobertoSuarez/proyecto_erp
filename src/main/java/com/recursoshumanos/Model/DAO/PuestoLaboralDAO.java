/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.PuestoLaboral;
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
 * Clase tipo DAO con las funciones para la manipulación de los pestos laborales
 * Administrar las sentencias de la BD, utilizando las clases de los modelos
 */
public class PuestoLaboralDAO implements IDAO<PuestoLaboral> {

    protected final Conexion conexion;
    protected PuestoLaboral puestoLaboral;

    public PuestoLaboralDAO() {
        conexion = new Conexion();
        puestoLaboral = new PuestoLaboral();
    }

    public PuestoLaboralDAO(PuestoLaboral puestoLaboral) {
        conexion = new Conexion();
        this.puestoLaboral = puestoLaboral;
    }

    public PuestoLaboralDAO(Conexion conexion) {
        this.conexion = conexion;
        puestoLaboral = new PuestoLaboral();
    }

    public PuestoLaboralDAO(PuestoLaboral puestoLaboral, Conexion conexion) {
        this.conexion = conexion;
        this.puestoLaboral = puestoLaboral;
    }

    public PuestoLaboral getPuestoLaboral() {
        return puestoLaboral;
    }

    public void setPuestoLaboral(PuestoLaboral puestoLaboral) {
        this.puestoLaboral = puestoLaboral;
    }

    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    /**
     * Ejecuta la inserción de los atributos del objeto puestoLaboral mediante
     * el método insertar del objeto conexión
     *
     * @return puestoLaboral.getId(), el id del puesto laboral que se ha 
     * agregado o un -1 si no se pudo
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            puestoLaboral.setEstado(true);
            puestoLaboral.setId(conexion.insertar("puesto_laboral",
                    "id_cargo, id_departamento, fecha_creacion, estado, "
                    + "descripcion", puestoLaboral.getCargo().getId() + ","
                    + puestoLaboral.getDepartamento().getId()
                    + ", CURRENT_DATE, " + puestoLaboral.isEstado() + ", '"
                    + puestoLaboral.getDescripcion() + "'",
                    "id_puesto_laboral"));
            return puestoLaboral.getId();
        }
        return -1;
    }

    /**
     * Toma un objeto tipo puestoLaboral y lo copia al objeto puestoLaboral
     * propio de la clase
     *
     *
     * @param entity, objeto de tipo puestoLaboral
     * @return insertar(), manda a ejecutar al otro método insertar de la clase
     */
    @Override
    public int insertar(PuestoLaboral entity) {
        this.puestoLaboral = entity;
        return insertar();
    }

    /**
     * Ejecuta la actualización mediante le método modificar del objeto conexión
     *
     * @return conexion.modificar(), el id del puesto laboral que se ha 
     * modificado o un -1 si no se pudo
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("puesto_laboral",
                    "id_cargo= " + puestoLaboral.getCargo().getId()
                    + ", id_departamento = "
                    + puestoLaboral.getDepartamento().getId()
                    + ", descripcion = '" + puestoLaboral.getDescripcion()
                    + "', estado = " + puestoLaboral.isEstado(),
                    "id_puesto_laboral = " + puestoLaboral.getId());
        }
        return -1;
    }

    /**
     * Toma un objeto tipo PuestoLaboral y lo copia al objeto provincia propio
     * de la clase
     *
     * @param entity, objeto de tipo PuestoLaboral
     * @return actualizar(), manada a ejecutar al otro método actualizar
     */
    @Override
    public int actualizar(PuestoLaboral entity) {
        this.puestoLaboral = entity;
        return actualizar();
    }

    /**
     * Busca por una PuestoLaboral por su id.
     *
     * @param id de la PuestoLaboral a buscar
     * @return lista.get(0), PuestoLaboral encontrado o null en aso de no hallarla
     */
    @Override
    public PuestoLaboral buscarPorId(Object id) {
        List<PuestoLaboral> lista = buscar("id_puesto_laboral = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    /**
     * Modifica el estado de un puesto laboral
     */
    public void cambiarEstado() {
        if (conexion.isEstado()) {
            conexion.modificar("puesto_laboral", "estado = NOT estado",
                    "id_puesto_laboral = " + puestoLaboral.getId());
        }
    }

    /**
     * Lista los PuestoLaborales
     *
     * @return buscar(), ejecuta método buscar
     */
    @Override
    public List<PuestoLaboral> Listar() {
        return buscar(null, null);
    }

    /**
     * Lista los PuestoLaborales activos
     *
     * @return buscar(), ejecuta método buscar especificando que solo sean los 
     * de estado activo
     */
    public List<PuestoLaboral> Activos() {
        return buscar("estado = true", null);
    }

    /**
     * Lista los puestos laborales  en base a los dos parámetros de entrada
     *
     * @param restricciones, las restricciones para la búsqueda
     * @param OrdenarAgrupar forma en que se desea ordenar
     * @return puestoLaborales, lista con las puestoLaborales ordenandos
     */
    private List<PuestoLaboral> buscar(@Nullable String restricciones,
            @Nullable String OrdenarAgrupar) {
        if (conexion.isEstado()) {
            ResultSet result;
            List<PuestoLaboral> puestoLaborales;
            try {
                result = conexion.selecionar("puesto_laboral",
                        "id_puesto_laboral, id_cargo, id_departamento, "
                        + "fecha_creacion, estado, descripcion",
                        restricciones, OrdenarAgrupar);
                puestoLaborales = new ArrayList<>();
                DepartamentoDAO ddao = new DepartamentoDAO();
                CargoDAO cdao = new CargoDAO();
                while (result.next()) {
                    puestoLaborales.add(new PuestoLaboral(
                            result.getInt("id_puesto_laboral"),
                            cdao.buscarPorId(result.getInt("id_cargo")),
                            ddao.buscarPorId(result.getInt("id_departamento")),
                            result.getDate("fecha_creacion"),
                            result.getBoolean("estado"),
                            result.getString("descripcion")));
                }
                result.close();
                return puestoLaborales;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return null;
    }
}
