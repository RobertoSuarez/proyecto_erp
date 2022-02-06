/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contabilidad.dao;

import com.contabilidad.models.EstadoResultado;
import com.global.config.Conexion;
import com.google.gson.Gson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ebert Guatanga
 */
public class EstadoResultadoDAO {

    /*
     * Inicializamos las variables a utilizar tales como, 
     * la variable que me permitira el acceso a la bd, los parametros
     * necesarios para cada sentencia sql,
     * y un gson que contendra la informacion
     */
    private final Conexion conexion;
    private ResultSet result;
    private final Gson gson;
    private String fecha;
    private String fecha2;

    /**
     * Constructor de la clase en donde inicializamos dos de las variables
     * utilizadas
     *
     */
    public EstadoResultadoDAO() {
        conexion = new Conexion();
        gson = new Gson();
    }
//------------------------SUMA INGRESOS---------------------------\\

    /**
     * Llena una lista de todos los estados resultates de asientos de un grupo
     * espesificamente del grupo #4
     *
     * @return Retorna el calculo de los asientos grupo
     */
    private List<EstadoResultado> getCalculoGrupo() {

        String sql = "select  public.getcalculogrupoer('" + fecha + "', '" + fecha2 + "',4)";
        List<EstadoResultado> listaCalculosGrupo = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculogrupoer");
                EstadoResultado estadoResultante = gson.fromJson(
                        cadenaJSON, EstadoResultado.class);
                listaCalculosGrupo.add(estadoResultante);
            }
            return listaCalculosGrupo;
        } catch (SQLException ex) {
            System.out.println("Error getcalculogrupoer: " + ex.getMessage());
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Llena una lista de todos los estados resultates de asientos de un
     * subgrupo
     *
     * @return Retorna el calculo de los asientos de subgrupo
     */
    private List<EstadoResultado> getCalculoSubGrupo() {
        String sql = "select public.getcalculosubgrupoer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosSubGrupo = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculosubgrupoer");
                EstadoResultado estadoResultante = gson.fromJson(
                        cadenaJSON, EstadoResultado.class);
                listaCalculosSubGrupo.add(estadoResultante);
            }
            return listaCalculosSubGrupo;
        } catch (SQLException ex) {
            System.out.println("Error getcalculosubgrupoer: " + ex.getMessage());
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Llena una lista de todos los estados resultates de asientos de una cuenta
     *
     * @return Retorna el calculo de una cuenta
     */
    private List<EstadoResultado> getCalculoCuenta() {
        String sql = "select public.getcalculocuentaer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosCuenta = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculocuentaer");
                EstadoResultado estadoResultante = gson.fromJson(
                        cadenaJSON, EstadoResultado.class);
                listaCalculosCuenta.add(estadoResultante);
            }
            return listaCalculosCuenta;
        } catch (SQLException ex) {
            System.out.println("Error getcalculocuentaer: " + ex.getMessage());
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Llena una lista de todos los estados resultates de asientos de una
     * subCuenta
     *
     * @return Retorna el calculo de los asientos de una subCuenta
     */
    private List<EstadoResultado> getCalculoSubCuenta() {

        String sql = "select public.getcalculosubcuentaer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosSubCuenta = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculosubcuentaer");
                EstadoResultado estadoResultante = gson.fromJson(
                        cadenaJSON, EstadoResultado.class);
                listaCalculosSubCuenta.add(estadoResultante);
            }
            return listaCalculosSubCuenta;
        } catch (SQLException ex) {
            System.out.println("Error getcalculosubcuentaer: " + ex.getMessage());
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Lista de todos los estados resultates de asientos de un grupo
     *
     * @param fecha
     * @param fecha2
     * @return Retorna el calculo total del assiento #4
     */
    public List<EstadoResultado> generateEstadoResultante(String fecha, String fecha2) {
        this.fecha = fecha;
        this.fecha2 = fecha2;
        List<EstadoResultado> balanceGeneral = new ArrayList<>();
        List<EstadoResultado> calculoGrupo = getCalculoGrupo();
        List<EstadoResultado> calculoSubGrupo = getCalculoSubGrupo();
        List<EstadoResultado> calculoCuenta = getCalculoCuenta();
        List<EstadoResultado> calculoSubCuenta = getCalculoSubCuenta();

        calculoGrupo.forEach(g -> {
            balanceGeneral.add(g);
            calculoSubGrupo.forEach(sg -> {
                if (sg.getParent() == g.getId()) {
                    balanceGeneral.add(sg);

                    calculoCuenta.forEach(c -> {
                        if (c.getParent() == sg.getId()) {
                            balanceGeneral.add(c);

                            calculoSubCuenta.forEach(sc -> {
                                if (sc.getParent() == c.getId()) {
                                    balanceGeneral.add(sc);
                                }
                            });
                        }
                    });
                }
            });
        });
        return balanceGeneral;
    }

    /**
     * Retorna el valor total de los ingresos teniendo como parametros las
     * fechas dadas
     *
     * @param fecha
     * @param fecha2
     * @return Retorna el calculo de los asientos grupo
     */
    public double sumaIngresos(String fecha, String fecha2) {
        String sql = "select sumaingresos('" + fecha + "', '" + fecha2 + "')";
        result = conexion.consultar(sql);
        try {
            if (result.next()) {
                return result.getDouble("sumaingresos");
            }
        } catch (SQLException ex) {
            System.out.println("Error sumaingresos: " + ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return 0;
    }

//------------------------SUMA EGRESOS---------------------------\\
    /**
     * Llena una lista de todos los estados resultates de asientos de un grupo
     * espesificamente del grupo #5
     *
     * @return Retorna el calculo de los asientos grupo
     */
    private List<EstadoResultado> getCalculoGrupoEg() {
        String sql = "select  public.getcalculogrupoer('" + fecha + "', '" + fecha2 + "',5)";
        List<EstadoResultado> listaCalculosGrupo = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculogrupoer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON,
                        EstadoResultado.class);
                listaCalculosGrupo.add(estadoResultante);
            }
            return listaCalculosGrupo;
        } catch (SQLException ex) {
            System.out.println("Error getCalculoGrupoer: " + ex.getMessage());
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Lista de todos los estados resultates de asientos de un grupo
     *
     * @param fecha
     * @param fecha2
     * @return Retorna una lista total del assiento #5
     */
    public List<EstadoResultado> generateEstadoResultadoEg(String fecha, String fecha2) {
        this.fecha = fecha;
        this.fecha2 = fecha2;
        List<EstadoResultado> estadoResultado = new ArrayList<>();
        List<EstadoResultado> calculoGrupo = getCalculoGrupoEg();
        List<EstadoResultado> calculoSubGrupo = getCalculoSubGrupoEg();
        List<EstadoResultado> calculoCuenta = getCalculoCuentaEg();
        List<EstadoResultado> calculoSubCuenta = getCalculoSubCuentaEg();

        calculoGrupo.forEach(g -> {
            estadoResultado.add(g);
            calculoSubGrupo.forEach(sg -> {
                if (sg.getParent() == g.getId()) {
                    estadoResultado.add(sg);

                    calculoCuenta.forEach(c -> {
                        if (c.getParent() == sg.getId()) {
                            estadoResultado.add(c);
                            calculoSubCuenta.forEach(sc -> {
                                if (sc.getParent() == c.getId()) {
                                    estadoResultado.add(sc);
                                }
                            });
                        }
                    });
                }
            });
        });
        return estadoResultado;
    }

    /**
     * Llena una lista de todos los estados resultates de asientos de una
     * subCuenta
     *
     * @return Retorna el calculo de los asientos de una subCuenta
     */
    private List<EstadoResultado> getCalculoSubCuentaEg() {
        String sql = "select public.getcalculosubcuentaer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosSubCuenta = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculosubcuentaer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON,
                        EstadoResultado.class);
                listaCalculosSubCuenta.add(estadoResultante);
            }
            return listaCalculosSubCuenta;
        } catch (SQLException ex) {
            System.out.println("Error getcalculosubcuentaer: " + ex.getMessage());
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Llena una lista de todos los estados resultates de asientos de un
     * subgrupo
     *
     * @return Retorna una lista de los asientos de subgrupo
     */
    private List<EstadoResultado> getCalculoSubGrupoEg() {
        String sql = "select public.getcalculosubgrupoer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosSubGrupo = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculosubgrupoer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON,
                        EstadoResultado.class);
                listaCalculosSubGrupo.add(estadoResultante);
            }
            return listaCalculosSubGrupo;
        } catch (SQLException ex) {
            System.out.println("Error getcalculosubgrupoer: " + ex.getMessage());
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Llena una lista de todos los estados resultates de asientos de una cuenta
     *
     * @return Retorna el calculo de una cuenta
     */
    private List<EstadoResultado> getCalculoCuentaEg() {
        String sql = "select public.getcalculocuentaer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosCuenta = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculocuentaer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON,
                        EstadoResultado.class);
                listaCalculosCuenta.add(estadoResultante);
            }
            return listaCalculosCuenta;
        } catch (SQLException ex) {
            System.out.println("Error getcalculocuentaer: " + ex.getMessage());
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Retorna el valor total de los egresos teniendo como parametros las fechas
     * dadas
     *
     * @param fecha
     * @param fecha2
     * @return Retorna el calculo de los asientos grupo
     */
    public double sumaegresos(String fecha, String fecha2) {
        String sql = "select sumaegresos('" + fecha + "', '" + fecha2 + "')";
        result = conexion.consultar(sql);
        try {
            if (result.next()) {
                return result.getDouble("sumaegresos");
            }
        } catch (SQLException ex) {
            System.out.println("Error sumaegresos: " + ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return 0;
    }
//------------------------SUMA COMPRA---------------------------\\

    /**
     * Llena una lista de todos los estados resultates de asientos de un grupo
     * espesificamente del grupo #6
     *
     * @return Retorna el calculo de los asientos grupo
     */
    private List<EstadoResultado> getCalculoGrupoVen() {
        String sql = "select  public.getcalculogrupoer('" + fecha + "', '" + fecha2 + "',6)";
        List<EstadoResultado> listaCalculosGrupo = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculogrupoer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON,
                        EstadoResultado.class);
                listaCalculosGrupo.add(estadoResultante);
            }
            return listaCalculosGrupo;
        } catch (SQLException ex) {
            System.out.println("Error getCalculoGrupoer: " + ex.getMessage());
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Lista de todos los estados resultates de asientos de un grupo
     *
     * @param fecha
     * @param fecha2
     * @return Retorna una lista total del assiento #6
     */
    public List<EstadoResultado> estadoResultadoVen(String fecha, String fecha2) {
        this.fecha = fecha;
        this.fecha2 = fecha2;
        List<EstadoResultado> estadoResultado = new ArrayList<>();
        List<EstadoResultado> calculoGrupo = getCalculoGrupoVen();
        List<EstadoResultado> calculoSubGrupo = getCalculoSubGrupoVen();
        List<EstadoResultado> calculoCuenta = getCalculoCuentaVen();
        List<EstadoResultado> calculoSubCuenta = getCalculoSubCuentaVen();

        calculoGrupo.forEach(g -> {
            estadoResultado.add(g);
            calculoSubGrupo.forEach(sg -> {
                if (sg.getParent() == g.getId()) {
                    estadoResultado.add(sg);

                    calculoCuenta.forEach(c -> {
                        if (c.getParent() == sg.getId()) {
                            estadoResultado.add(c);

                            calculoSubCuenta.forEach(sc -> {
                                if (sc.getParent() == c.getId()) {
                                    estadoResultado.add(sc);
                                }
                            });
                        }
                    });
                }
            });
        });
        return estadoResultado;
    }

    /**
     * Llena una lista de todos los estados resultates de asientos de una
     * subCuenta
     *
     * @return Retorna el calculo de los asientos de una subCuenta
     */
    private List<EstadoResultado> getCalculoSubCuentaVen() {
        String sql = "select public.getcalculosubcuentaer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosSubCuenta = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculosubcuentaer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON,
                        EstadoResultado.class);
                listaCalculosSubCuenta.add(estadoResultante);
            }
            return listaCalculosSubCuenta;
        } catch (SQLException ex) {
            System.out.println("Error getcalculosubcuentaer: " + ex.getMessage());
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Llena una lista de todos los estados resultates de asientos de un
     * subgrupo
     *
     * @return Retorna el calculo de los asientos de subgrupo
     */
    private List<EstadoResultado> getCalculoSubGrupoVen() {
        String sql = "select public.getcalculosubgrupoer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosSubGrupo = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculosubgrupoer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON,
                        EstadoResultado.class);
                listaCalculosSubGrupo.add(estadoResultante);
            }
            return listaCalculosSubGrupo;
        } catch (SQLException ex) {
            System.out.println("Error getCalculoGrupo: " + ex.getMessage());
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Llena una lista de todos los estados resultates de asientos de una cuenta
     *
     * @return Retorna el calculo de una cuenta
     */
    private List<EstadoResultado> getCalculoCuentaVen() {
        String sql = "select public.getcalculocuentaer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosCuenta = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculocuentaer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON,
                        EstadoResultado.class);
                listaCalculosCuenta.add(estadoResultante);
            }
            return listaCalculosCuenta;
        } catch (SQLException ex) {
            System.out.println("Error getcalculocuentaer: " + ex.getMessage());
            return null;
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Retorna el valor total de los egresos teniendo como parametros las fechas
     * dadas
     *
     * @param fecha
     * @param fecha2
     * @return Retorna el calculo de los asientos grupo
     */
    public double costoventa(String fecha, String fecha2) {
        String sql = "select costoventa('" + fecha + "', '" + fecha2 + "')";
        result = conexion.consultar(sql);
        try {
            if (result.next()) {
                return result.getDouble("costoventa");
            }
        } catch (SQLException ex) {
            System.out.println("Error costoventa: " + ex.getMessage());
        } finally {
            conexion.desconectar();
        }
        return 0;
    }
}
