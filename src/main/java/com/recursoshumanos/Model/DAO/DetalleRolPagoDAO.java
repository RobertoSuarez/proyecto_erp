/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.DetalleRolPago;
import com.recursoshumanos.Model.Entidad.RolPagos;
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
 * Clase tipo DAO que se encargará de proporcionar ciertas funcionalidades
 * para todo lo que tenga que ver con Detalle Rol dePagos.
 * Y se encarga de administrar las sentencias de la BD, utilizando
 * las clases de los modelos
 */
public class DetalleRolPagoDAO implements IDAO<DetalleRolPago> {

    private final Conexion conexion;
    private DetalleRolPago detalleRolPago;

    public DetalleRolPagoDAO() {
        this.conexion = new Conexion();
        this.detalleRolPago = new DetalleRolPago();
    }

    public DetalleRolPagoDAO(Conexion conexion) {
        this.conexion = conexion;
        this.detalleRolPago = new DetalleRolPago();
    }

    public DetalleRolPagoDAO(DetalleRolPago detalleRolPago) {
        this.conexion = new Conexion();
        this.detalleRolPago = detalleRolPago;
    }

    public DetalleRolPagoDAO(Conexion conexion, DetalleRolPago detalleRolPago) {
        this.conexion = conexion;
        this.detalleRolPago = detalleRolPago;
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
     * Metodo GET de Detalle de Rol de Pagos
     * @return detalleRolPago Objeto que obtiene el dato
     */
    public DetalleRolPago getDetalleRolPago() {
        return detalleRolPago;
    }

    /**
     * Metodo SET de Detalle de Rol de Pagos
     * @param detalleRolPago Objeto que envia el dato
     */
    public void setDetalleRolPago(DetalleRolPago detalleRolPago) {
        this.detalleRolPago = detalleRolPago;
    }

    /**
     * Creación del metedo INSERTAR, para registrar
     * los datos recopilados de la clase de DetalleHorarioDAO.
     * @return conexion.insertar Retorna la confirmación
     * de un registro exitoso o erroneo.
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            return conexion.insertar("detalle_rol", "id_rol, id_rubro, rubro",
                    detalleRolPago.getRolPagos().getId() + "," + detalleRolPago.getTipoRubro().getId() + "," + detalleRolPago.getRubro(),
                    "id_rol");
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
    public int insertar(DetalleRolPago entity) {
        this.detalleRolPago = entity;
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
            return conexion.modificar("detalle_rol",
                    "id_rubro = " + detalleRolPago.getTipoRubro().getId(),
                    "id_rol = " + detalleRolPago.getRolPagos().getId());
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
    public int actualizar(DetalleRolPago entity) {
        this.detalleRolPago = entity;
        return actualizar();
    }

    /**
     * Metodo que permite buscar mediante el ID
     * dentro de los registros.
     * @param id Objeto encargado del ID del parametro
     * de busqueda.
     * @return lista Retorna la lista de busqueda indicada
     */
    @Override
    public DetalleRolPago buscarPorId(Object id) {
        List<DetalleRolPago> lista = buscar("id_rol = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    /**
     * Valida que no exista nulo en la busqueda
     * @return buscar envia la lista del resultado
     */
    @Override
    public List<DetalleRolPago> Listar() {
        return buscar(null, null);
    }

    /**
     * Se encarga de buscar el Rol de Pagos
     * @param rolPagos Objeto identificado como el set que
     * se usara para la cadena de conexión de la BD.
     * @return detalles Retorna el detalle de la busqueda.
     */
    public List<DetalleRolPago> buscar(RolPagos rolPagos) {
        List<DetalleRolPago> detalles = new ArrayList<>();
        this.detalleRolPago.setRolPagos(rolPagos);
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("detalle_rol", "id_rubro, rubro",
                                             "id_rol = " + rolPagos.getId(), null);
                TipoRubroDAO tpdao = new TipoRubroDAO();
                while (result.next()) {
                    detalles.add(new DetalleRolPago(rolPagos, tpdao.buscarPorId(result.getInt("id_rubro")), result.getInt("rubro")));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return detalles;
    }

    /**
     * Busca el DetalleHorario de acuerdo a su registro
     * @param restricciones Objeto con las restricciones
     * o validaciones de las mismas.
     * @param OrdenarAgrupar Objeto encargado de ordenar
     * los resultados. 
     * @return List<DetalleRolPago> Muestra la lista.
     */
    private List<DetalleRolPago> buscar(@Nullable String restricciones, @Nullable String OrdenarAgrupar) {
        if (conexion.isEstado()) {
            ResultSet result;
            List<DetalleRolPago> detalles;
            try {
                result = conexion.selecionar("detalle_rol", "id_rol, id_rubro, rubro", restricciones, OrdenarAgrupar);
                detalles = new ArrayList<>();
                RolPagosDAO rpdao = new RolPagosDAO();
                TipoRubroDAO tpdao = new TipoRubroDAO();
                while (result.next()) {
                    detalles.add(new DetalleRolPago(rpdao.buscarPorId(result.getInt("id_rol")), tpdao.buscarPorId(result.getInt("id_rol")), result.getInt("rubro")));
                }
                result.close();
                return detalles;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        }
        return null;
    }
}