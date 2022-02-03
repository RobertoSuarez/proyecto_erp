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
 * @author User
 */
public class EstadoResultadoDAO {

    private Conexion conexion;
    private ResultSet result;
    private Gson gson;
    private String fecha;
    private String fecha2;

    public EstadoResultadoDAO() {
        conexion = new Conexion();
        gson = new Gson();
    }
//------------------------SUMA INGRESOS---------------------------\\

    private List<EstadoResultado> getCalculoGrupo() {

        String sql = "select  public.getcalculogrupoer('" + fecha + "', '" + fecha2 + "',4)";
        List<EstadoResultado> listaCalculosGrupo = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculogrupoer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON, EstadoResultado.class);
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

    private List<EstadoResultado> getCalculoSubGrupo() {
        String sql = "select public.getcalculosubgrupoer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosSubGrupo = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculosubgrupoer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON, EstadoResultado.class);
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

    private List<EstadoResultado> getCalculoCuenta() {
        String sql = "select public.getcalculocuentaer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosCuenta = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculocuentaer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON, EstadoResultado.class);
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

    private List<EstadoResultado> getCalculoSubCuenta() {

        String sql = "select public.getcalculosubcuentaer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosSubCuenta = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculosubcuentaer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON, EstadoResultado.class);
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

    public List<EstadoResultado> generateBalanceGeneral(String fecha, String fecha2) {
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
    private List<EstadoResultado> getCalculoGrupoe() {
        String sql = "select  public.getcalculogrupoer('" + fecha + "', '" + fecha2 + "',5)";
        List<EstadoResultado> listaCalculosGrupo = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculogrupoer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON, EstadoResultado.class);
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

    public List<EstadoResultado> generateBalanceGenerale(String fecha, String fecha2) {
        this.fecha = fecha;
        this.fecha2 = fecha2;
        List<EstadoResultado> balanceGeneral = new ArrayList<>();
        List<EstadoResultado> calculoGrupo = getCalculoGrupoe();
        List<EstadoResultado> calculoSubGrupo = getCalculoSubGrupoe();
        List<EstadoResultado> calculoCuenta = getCalculoCuentae();
        List<EstadoResultado> calculoSubCuenta = getCalculoSubCuentae();

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

    private List<EstadoResultado> getCalculoSubCuentae() {
        String sql = "select public.getcalculosubcuentaer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosSubCuenta = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculosubcuentaer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON, EstadoResultado.class);
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

    private List<EstadoResultado> getCalculoSubGrupoe() {
        String sql = "select public.getcalculosubgrupoer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosSubGrupo = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculosubgrupoer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON, EstadoResultado.class);
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

    private List<EstadoResultado> getCalculoCuentae() {
        String sql = "select public.getcalculocuentaer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosCuenta = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculocuentaer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON, EstadoResultado.class);
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

    private List<EstadoResultado> getCalculoGrupoes() {
        String sql = "select  public.getcalculogrupoer('" + fecha + "', '" + fecha2 + "',6)";
        List<EstadoResultado> listaCalculosGrupo = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculogrupoer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON, EstadoResultado.class);
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

    public List<EstadoResultado> generateBalanceGenerales(String fecha, String fecha2) {
        this.fecha = fecha;
        this.fecha2 = fecha2;
        List<EstadoResultado> balanceGeneral = new ArrayList<>();
        List<EstadoResultado> calculoGrupo = getCalculoGrupoes();
        List<EstadoResultado> calculoSubGrupo = getCalculoSubGrupoes();
        List<EstadoResultado> calculoCuenta = getCalculoCuentaes();
        List<EstadoResultado> calculoSubCuenta = getCalculoSubCuentaes();

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

    private List<EstadoResultado> getCalculoSubCuentaes() {
        String sql = "select public.getcalculosubcuentaer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosSubCuenta = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculosubcuentaer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON, EstadoResultado.class);
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

    private List<EstadoResultado> getCalculoSubGrupoes() {
        String sql = "select public.getcalculosubgrupoer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosSubGrupo = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculosubgrupoer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON, EstadoResultado.class);
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

    private List<EstadoResultado> getCalculoCuentaes() {
        String sql = "select public.getcalculocuentaer('" + fecha + "', '" + fecha2 + "')";
        List<EstadoResultado> listaCalculosCuenta = new ArrayList<>();
        result = conexion.consultar(sql);
        try {
            while (result.next()) {
                String cadenaJSON = result.getString("getcalculocuentaer");
                EstadoResultado estadoResultante = gson.fromJson(cadenaJSON, EstadoResultado.class);
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
