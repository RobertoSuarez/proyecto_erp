/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recursoshumanos.Model.DAO;

import com.global.config.Conexion;
import com.recursoshumanos.Model.Entidad.Empleado;
import com.recursoshumanos.Model.Entidad.Multa;
import com.recursoshumanos.Model.Interfaces.IDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author kestradalp
 */
public class MultaDAO implements IDAO<Multa>  {

    private final Conexion conexion;
    private Multa multa;

    public MultaDAO() {
        this.conexion = new Conexion();
        this.multa = new Multa();
    }

    public MultaDAO(Conexion conexion) {
        this.conexion = conexion;
        this.multa = new Multa();
    }

    public MultaDAO(Multa multa) {
        this.conexion = new Conexion();
        this.multa = multa;
    }

    public MultaDAO(Conexion conexion, Multa multa) {
        this.conexion = conexion;
        this.multa = multa;
    }

    @Override
    public Conexion obtenerConexion() {
        return conexion;
    }

    @Override
    public int insertar() {
        if (conexion.isEstado()) {
            this.multa.setId(conexion.insertar("multa", "id_empleado, porcentaje, valor, detalle, estado", 
                    multa.getEmpleado().getId()+ "," + multa.getPorcentaje()+ "," + multa.getValor() + ",'"
                    + multa.getDetalle() + "'," + multa.isEstado(), "id_multa"));
            return this.multa.getId();
        }
        return -1;
    }

    @Override
    public int insertar(Multa entity) {
        this.multa = entity;
        return insertar();
    }

    @Override
    public int actualizar() {
        if (conexion.isEstado()) {
            return conexion.modificar("multa",
                    "porcentaje = '" + multa.getPorcentaje()+ "', valor = " + multa.getValor() + ", detalle = '"
                    + multa.getDetalle() + "', estado = " + multa.isEstado(),
                    "id_multa = " + multa.getId() + " AND id_empleado = " + multa.getEmpleado().getId());
        }
        return -1;
    }

    @Override
    public int actualizar(Multa entity) {
        this.multa = entity;
        return actualizar();
    }

    @Override
    public Multa buscarPorId(Object id) {
        List<Multa> lista = buscar("id_multa = " + id, null);
        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    @Override
    public List<Multa> Listar() {
        return buscar(null, null);
    }
    
    public List<Multa> historial(Empleado empleado) {
        List<Multa> multas = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("multa", "id_multa, porcentaje, valor, detalle, estado",
                        "id_empleado = " + empleado.getId(), "id_multa DESC");
                while (result.next()) {
                    multas.add(new Multa(
                            result.getInt("id_multa"),
                            empleado,
                            result.getFloat("porcentaje"),
                            result.getFloat("valor"),
                            result.getString("detalle"),
                            result.getBoolean("estado")
                    ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return multas;
    }
    
    public List<Multa> Listar(Empleado empleado) {
        List<Multa> multas = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("multa", "id_multa, porcentaje, valor, detalle",
                        "id_empleado = " + empleado.getId() + " AND estado = true", "id_multa DESC");
                while (result.next()) {
                    multas.add(new Multa(
                            result.getInt("id_multa"),
                            empleado,
                            result.getFloat("porcentaje"),
                            result.getFloat("valor"),
                            result.getString("detalle"), true));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return multas;
    }
    
    public Multa buacar(Empleado empleado) {
        multa = new Multa(0, empleado, 0, 0, "N/D", true);
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("multa", "id_multa, porcentaje, valor, detalle",
                        "id_empleado = " + empleado.getId() + " AND estado = true", "id_multa DESC");
                while (result.next()) {
                     multa.setId(result.getInt("id_multa"));
                     multa.setPorcentaje(result.getFloat("porcentaje"));
                          multa.setValor(result.getFloat("valor"));
                           multa.setDetalle( result.getString("detalle"));
                           multa.setEstado(true);
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return multa;
    }

    private List<Multa> buscar(@Nullable String restricciones, @Nullable String OrdenarAgrupar) {
        List<Multa> multas = new ArrayList<>();
        if (conexion.isEstado()) {
            ResultSet result;
            try {
                result = conexion.selecionar("multa", "id_multa, id_empleado, tipo, valor, detalle, estado",
                        restricciones, OrdenarAgrupar);
                EmpleadoDAO eDAO = new EmpleadoDAO();
                while (result.next()) {
                    multas.add(new Multa(
                            result.getInt("id_multa"),
                            (multa.getEmpleado().getId()>0? multa.getEmpleado() : eDAO.buscarPorId(result.getInt("id_empleado"))),
                            result.getFloat("porcentaje"),
                            result.getFloat("valor"),
                            result.getString("detalle"),
                            result.getBoolean("estado")));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conexion.desconectar();
            }
        }
        return multas;
    }
}