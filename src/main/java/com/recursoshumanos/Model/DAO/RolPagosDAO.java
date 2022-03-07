/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.contabilidad.dao.CuentaDAO;
import com.contabilidad.dao.DiarioDAO;
import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Empleado;
import com.recursoshumanos.Model.Entidad.RolPagos;
import com.recursoshumanos.Model.Interfaces.IDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.jetbrains.annotations.Nullable;

/**
 * @author kestradalp
 * @author ClasK7
 * @author rturr
 *
 * Clase tipo DAO con las funciones para la manipulación de Rol Pagos Y se
 * encarga de administrar las sentencias de la BD, utilizando las clases de los
 * modelos
 */
public class RolPagosDAO implements IDAO<RolPagos> {

    private final Conexion conexion;
    private final DiarioDAO diarioDAO;
    private final CuentaDAO cuentaDAO;
    private RolPagos rolPagos;

    public RolPagosDAO() {
        this.conexion = new Conexion();
        this.rolPagos = new RolPagos();
        diarioDAO = new DiarioDAO();
        cuentaDAO = new CuentaDAO();
    }

    public RolPagosDAO(Conexion conexion) {
        this.conexion = conexion;
        this.rolPagos = new RolPagos();
        diarioDAO = new DiarioDAO();
        cuentaDAO = new CuentaDAO();
    }

    public RolPagosDAO(RolPagos rolPagos) {
        this.conexion = new Conexion();
        this.rolPagos = rolPagos;
        diarioDAO = new DiarioDAO();
        cuentaDAO = new CuentaDAO();
    }

    public RolPagosDAO(Conexion conexion, RolPagos rolPagos) {
        this.conexion = conexion;
        this.rolPagos = rolPagos;
        diarioDAO = new DiarioDAO();
        cuentaDAO = new CuentaDAO();
    }

    public RolPagos getRolPagos() {
        return rolPagos;
    }

    public void setRolPagos(RolPagos rolPagos) {
        this.rolPagos = rolPagos;
    }

    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    /**
     * Ejecuta la inserción de los atributos del objeto rolPagos mediante el
     * método insertar del objeto conexion
     *
     * @return rolPagos.getId(), el id del rol de pagos que se ha agregado o un 
     * -1 si no se pudo
     */
    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            rolPagos.setId(conexion.insertar("rol_de_pagos",
                    "id_empleado, generado_por, fecha_generado, detalle, "
                    + "estado, horas_laboradas, horas_suplemt, valor, codigo",
                    rolPagos.getEmpleado().getId() + ",'"
                    + rolPagos.getUsuario() + "','"
                    + rolPagos.getFechaGenerado() + "','"
                    + rolPagos.getDetalle() + "', '" + rolPagos.getEstado()
                    + "', " + rolPagos.getHorasLaboradas() + ","
                    + rolPagos.getHorasSuplemetarias() + ","
                    + rolPagos.getValor() + ",'" + rolPagos.getCodigo() + "'",
                    "id_rol"));
        }
        return rolPagos.getId();
    }

    /**
     * Toma un objeto tipo RolPagos y lo copia al objeto persona propio de la
     * clase
     *
     * @param entity, objeto de tipo RolPagos
     * @return insertar(), manda a ejecutar al otro método insertar
     */
    @Override
    public int insertar(RolPagos entity) {
        this.rolPagos = entity;
        return insertar();
    }

    /**
     * Ejecuta la actualización mediante le método modificar del objeto conexión
     *
     * @return conexion.modificar(), el id dol rol de pagos que se ha modificado 
     * o un -1 si no se pudo
     */
    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("rol_de_pagos", "estado = '"
                    + rolPagos.getFechaGenerado() + "'",
                    "id_rol = " + rolPagos.getId() + " AND id_empleado = "
                    + rolPagos.getEmpleado().getId() + " AND generado_por = '"
                    + rolPagos.getUsuario() + " AND codigo = '"
                    + rolPagos.getCodigo() + "'");
        }
        return -1;
    }

    /**
     * Toma un objeto tipo RolPagos y lo copia al objeto rolPagos propio de la
     * clase
     *
     * @param entity, objeto de tipo RolPagos
     * @return actualizar(), manada a ejecutar al otro método actualizar
     */
    @Override
    public int actualizar(RolPagos entity) {
        this.rolPagos = entity;
        return actualizar();
    }

    /**
     * Busca por un rol d pagos por su id.
     *
     * @param id de la rol de pagos a buscar
     * @return rolPagos, rol de pagos encontrado o null en caso de no hallarlo
     */
    @Override
    public RolPagos buscarPorId(Object id) {
        List<RolPagos> lista = buscar("id_rol = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            rolPagos = lista.get(0);
            return rolPagos;
        }
        return null;
    }

    public RolPagos buscar(int id, Empleado empleado) {
        rolPagos = new RolPagos(empleado);
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("rol_de_pagos",
                        "fecha_generado, fecha_aprobacion, fecha_pago, detalle, "
                        + "estado, horas_laboradas, horas_suplemt, valor, codigo",
                        "id_empleado = " + empleado.getId()
                        + " AND generado_por = 'ADMINISTRADOR' AND id_rol = "
                        + id, null);
                while (result.next()) {
                    rolPagos.setId(id);
                    rolPagos.setFechaGenerado(result.getDate("fecha_generado"));
                    rolPagos.setFechaAprobacion(result.getDate("fecha_aprobacion"));
                    rolPagos.setFechaPago(result.getDate("fecha_pago"));
                    rolPagos.setDetalle(result.getString("detalle"));
                    rolPagos.setEstado(result.getString("estado"));
                    rolPagos.setHorasLaboradas(result.getFloat("horas_laboradas"));
                    rolPagos.setHorasSuplemetarias(result.getFloat("horas_suplemt"));
                    rolPagos.setValor(result.getFloat("valor"));
                    rolPagos.setCodigo(result.getString("codigo"));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return rolPagos;
    }

     /**
     * Obtiene el valor de decimo tercer sueldo
     *
     * @return decimo, valor del decimo tercer sueldo
     */
    public float obtenerDecicmoTercero() {
        float decimo = 0;
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.ejecutarSql(
                        "SELECT round(COALESCE(SUM(valor)/12, 0)::numeric, 2) "
                        + "AS decimo FROM rol_de_pagos "
                        + "WHERE id_empleado = "
                        + rolPagos.getEmpleado().getId()
                        + " AND fecha_generado between CONCAT(EXTRACT("
                        + "YEAR FROM NOW())-1, '-12-01')::timestamp AND CONCAT"
                        + "(EXTRACT(YEAR FROM NOW()), '-11-30')::timestamp");
                while (result.next()) {
                    decimo = result.getFloat("decimo");
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return decimo;
    }

     /**
     * Obtiene la cantidad de horas laboradas 
     *
     * @return horas, nùmero de horas laboradas
     */
    public int obtenerHorasLaboradas() {
        int horas = 0;
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.ejecutarSql("SELECT COALESCE("
                        + "horas_laboradas((SELECT "
                        + "empleado_puesto.id_empleado_puesto FROM "
                        + "empleado_puesto " + "WHERE "
                        + "empleado_puesto.id_empleado = "
                        + rolPagos.getEmpleado().getId() + " AND estado = true),"
                        + " " + (rolPagos.getFechaGenerado().getMonth() + 1)
                        + "), 0) AS horas;");
                while (result.next()) {
                    horas = result.getInt("horas");
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return horas;
    }

    /**
     * Obtiene la cantidad de horas sumplementarias
     *
     * @return decimo, nùmero de horas suplementarias
     */
    public int obtenerHorasSuplementarias() {
        int decimo = 0;
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.ejecutarSql("SELECT COALESCE("
                        + "horas_sumplementarias((SELECT empleado_puesto."
                        + "id_empleado_puesto FROM empleado_puesto "
                        + "WHERE empleado_puesto.id_empleado = "
                        + rolPagos.getEmpleado().getId() + " AND estado = true), "
                        + "" + (rolPagos.getFechaGenerado().getMonth() + 1)
                        + "), 0) AS horas;");
                while (result.next()) {
                    decimo = result.getInt("horas");
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return decimo;
    }

    /**
     * Obtiene valor del decimo cuarto sueldo
     *
     * @return decimo, valor del decimo cuarto sueldo
     */
    public float obtenerDecicmoCuarto() {
        float decimo = 0;
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.ejecutarSql("SELECT round("
                        + "horas_sumplementarias('"
                        + rolPagos.getFechaGenerado()
                        + "'::DATE, " + rolPagos.getEmpleado().getId()
                        + ", 400)::numeric, 2) AS decimo;");
                while (result.next()) {
                    decimo = result.getFloat("decimo");
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return decimo;
    }

    /**
     * Lista los roles de pago
     *
     * @return buscar(), ejecuta método buscar
     */
    @Override
    public List<RolPagos> Listar() {
        return buscar(null, null);
    }

    /**
     * Lista los roles de pago de un empleado
     * @param empleado, empleado a listar los roles de pago
     * @return roles, lista de los roldes del empleado
     */
    public List<RolPagos> buscar(Empleado empleado) {
        List<RolPagos> roles = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("rol_de_pagos",
                        "id_rol, generado_por, fecha_generado, "
                        + "fecha_aprobacion, fecha_pago, detalle, estado, "
                        + "horas_laboradas, horas_suplemt, valor, codigo",
                        "id_empleado = " + empleado.getId(), null);
                while (result.next()) {
                    roles.add(new RolPagos(result.getInt("id_rol"),
                            empleado, result.getString("detalle"),
                            result.getDate("fecha_generado"),
                            result.getDate("fecha_aprobacion"),
                            result.getDate("fecha_pago"),
                            result.getString("estado"),
                            result.getFloat("horas_laboradas"),
                            result.getFloat("horas_suplemt"),
                            result.getFloat("valor"),
                            result.getString("codigo")));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return roles;
    }

    /**
     * Lista los roles de pago en base a los dos parámetros de entrada
     *
     * @param restricciones, las restricciones para la búsqueda
     * @param OrdenarAgrupar forma en que se desea ordenar
     * @return roles, lista con las roles ordenandos y filtrados
     */
    private List<RolPagos> buscar(@Nullable String restricciones,
            @Nullable String OrdenarAgrupar) {
        if (conexion.isEstado()) {
            ResultSet result;
            List<RolPagos> roles;
            try {
                result = conexion.selecionar("rol_de_pagos",
                        "id_rol, id_empleado, generado_por, fecha_generado, "
                        + "fecha_aprobacion, fecha_pago, detalle, estado, "
                        + "horas_laboradas, horas_suplemt, valor, codigo",
                        restricciones, OrdenarAgrupar);
                roles = new ArrayList<>();
                EmpleadoDAO eDAO = new EmpleadoDAO();
                while (result.next()) {
                    roles.add(new RolPagos(result.getInt("id_rol"),
                            eDAO.buscarPorId(result.getInt("id_empleado")),
                            result.getString("detalle"),
                            result.getDate("fecha_generado"),
                            result.getDate("fecha_aprobacion"),
                            result.getDate("fecha_pago"),
                            result.getString("estado"),
                            result.getFloat("horas_laboradas"),
                            result.getFloat("horas_suplemt"),
                            result.getFloat("valor"),
                            result.getString("codigo")));
                }
                result.close();
                return roles;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return null;
    }

    public void insertarAsiento(RolPagos rolPagos) {
        try {
            String sentencia1, sentencia;
                sentencia = "{\"idDiario\": \"" + this.diarioDAO.obtenerDiarioByNombre("Modulo cuentas por pagar").getIdDiario() + "\",\"total\": " + rolPagos.getValor()
                        + ",\"documento\": \"ROL-" + rolPagos.getCodigo().split("-")[0] + "\",\"detalle\": \"Rol de pago: "
                        + rolPagos.getEmpleado().nombreCompleto() + "\",\"fechaCreacion\": \""
                        + DateFormatUtils.format(rolPagos.getFechaGenerado(), "dd-MM-yyyy") + "\",\"fechaCierre\":\""
                        + DateFormatUtils.format(rolPagos.getFechaGenerado(), "dd-MM-yyyy") + "\"}";

                sentencia1 = "[{\"idSubcuenta\":\"28\",\"debe\":\""
                        + rolPagos.getValor() + "\",\"haber\":\"0\",\"tipoMovimiento\":\"Pago\"},"
                        + "{\"idSubcuenta\":\"" + this.cuentaDAO.ObtenerIdSubCuentaPorNombre("Cuentas y documentos por Pagar Personal") + "\",\"debe\":\"0\",\"haber\":\""
                        + rolPagos.getValor() + "\",\"tipoMovimiento\":\"Pago\"}]";
                System.out.println(sentencia1);
            intJson(sentencia, sentencia1);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " error en conectarse");
        }
    }

    public void deshabilitarAsiento(RolPagos rolPagos) {
        try {
            String sentencia1, sentencia;
                sentencia = "{\"idDiario\": \"" + this.diarioDAO.obtenerDiarioByNombre("Modulo cuentas por pagar").getIdDiario() + "\",\"total\": " + rolPagos.getValor()
                        + ",\"documento\": \"ROL-" + rolPagos.getCodigo().split("-")[0] + " R\",\"detalle\": \"Rol de pago: :"
                        + rolPagos.getEmpleado().nombreCompleto() + " R\",\"fechaCreacion\": \""
                        + DateFormatUtils.format(rolPagos.getFechaGenerado(), "dd-MM-yyyy")  + "\",\"fechaCierre\":\""
                        + DateFormatUtils.format(rolPagos.getFechaGenerado(), "dd-MM-yyyy") + "\"}";

                sentencia1 = "[{\"idSubcuenta\":\"28\",\"debe\":\"0\",\"haber\":\""
                        + rolPagos.getValor() + "\",\"tipoMovimiento\":\"Pago\"},"
                        + "{\"idSubcuenta\":\"" + this.cuentaDAO.ObtenerIdSubCuentaPorNombre("Cuentas y documentos por Pagar Personal") + "\",\"debe\":\""
                        + rolPagos.getValor() + "\",\"haber\":\"0\",\"tipoMovimiento\":\"Pago\"}]";
                System.out.println(sentencia1);
            intJson(sentencia, sentencia1);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " error en conectarse");
        }
    }

    /**
     * Envia los datos tipo json a insertar
     *
     * @param a Sentencia del tipo json (asiento)
     * @param b Sentencia del tipo json (movimiento)
     */
    public void intJson(String a, String b) {
        if (conexion.isEstado()) {
            try {
                String cadena = "SELECT public.generateasientocotableexternal('"
                        + a + "','" + b + "')";
                System.out.println(cadena);
                conexion.ejecutarSql(cadena);
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conexion.desconectar();
            }
        }
    }
}
