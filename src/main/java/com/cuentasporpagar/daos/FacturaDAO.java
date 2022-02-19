/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.daos;

import com.global.config.Conexion;
import com.cuentasporpagar.models.Factura;
import com.cuentasporpagar.models.Proveedor;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ninat
 */
public class FacturaDAO {

    Conexion conexion = new Conexion();
    private Factura factura;
    private ResultSet result;
    private List<Factura> listaFacturas;
    private List auxlista = new ArrayList<>();

    public FacturaDAO() {
        factura = new Factura();
        conexion = new Conexion();
        listaFacturas = new ArrayList<>();
    }

    public FacturaDAO(Factura factura) {
        conexion = new Conexion();
        this.factura = factura;
    }

    public List<Factura> llenarP(String n) {
        if (conexion.isEstado())
        {
            try
            {
                String sentencia = "SELECT f.idfactura,f.nfactura,f.descripcion,"
                        + "f.importe,f.pagado ,(f.importe - f.pagado) as pendiente,f.fecha,"
                        + "f.vencimiento,f.estado, p.nombre, f.habilitar from factura as f "
                        + "INNER JOIN proveedor as p on (f.idproveedor = p.idproveedor) "
                        + "where (f.importe - f.pagado) != 0 and habilitar = " + n;
                result = conexion.ejecutarSql(sentencia);
                System.out.println("Factura: " + result.toString());
                while (result.next())
                {
                    listaFacturas.add(new Factura(result.getInt("idfactura"),
                            result.getString("nfactura"), result.getString("descripcion"),
                            result.getFloat("importe"), result.getFloat("pagado"),
                            result.getFloat("pendiente"),
                            result.getObject("fecha", LocalDate.class),
                            result.getObject("vencimiento", LocalDate.class),
                            result.getInt("estado"), result.getString("nombre"),
                            result.getInt("habilitar")));
                }

            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
        return listaFacturas;
    }

    public List<Factura> llenar() {
        if (conexion.isEstado())
        {
            try
            {
                String sentencia = "SELECT f.nfactura,f.descripcion,f.importe,"
                        + "f.fecha,f.vencimiento, p.nombre,f.habilitar, "
                        + "f.estado from factura as f INNER JOIN proveedor "
                        + "as p on (f.idproveedor = p.idproveedor) "
                        + "where (f.importe - f.pagado) != 0 and f.habilitar = 1 and f.estado = 0;";
                result = conexion.ejecutarSql(sentencia);
                System.out.println("Factura: " + result.toString());
                while (result.next())
                {
                    listaFacturas.add(new Factura(result.getString("nfactura"),
                            result.getString("descripcion"), result.getFloat("importe"),
                            result.getObject("fecha", LocalDate.class),
                            result.getObject("vencimiento", LocalDate.class),
                            result.getInt("estado"), result.getString("nombre"),
                            result.getInt("habilitar")));
                }
            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
        return listaFacturas;
    }

    public List<Factura> llenarCuentas() {

        System.err.println("LLENAR CUENTAS XD");
        listaFacturas.clear();
        if (conexion.isEstado())
        {
            try
            {
                String sentencia = "select nombre from subcuenta where tiposaldo = 'Deudor';";
                result = conexion.ejecutarSql(sentencia);
                while (result.next())
                {
                    listaFacturas.add(new Factura(result.getString("nombre")));
                }
            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
        System.err.println("LLENAR CUENTA: " + listaFacturas.size());
        return listaFacturas;
    }

    public List<Factura> llenarDetalle(String n) {
        listaFacturas.clear();
        if (conexion.isEstado())
        {
            try
            {
                String sentencia = "select * from detalle_compra where nfactura = '" + n + "';";
                result = conexion.ejecutarSql(sentencia);
                while (result.next())
                {
                    listaFacturas.add(new Factura(result.getFloat("importe"),
                            result.getString("detalle"), result.getString("subcuenta"), result.getString("iddetallecompra")));
                }
            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
        return listaFacturas;
    }

    public int Insertar(Factura factura) {
        int existe = 0;
        if (conexion.isEstado())
        {
            try
            {
                String cadena = "select registrarfactura('" + factura.getNfactura() + "','"
                        + factura.getDescripcion() + "'," + factura.getImporte() + ","
                        + 0 + ",'" + factura.getFecha() + "','"
                        + factura.getVencimiento() + "',(Select idproveedor from proveedor p "
                        + " where p.ruc = '" + factura.getRuc() + "'))";
                result = conexion.ejecutarSql(cadena);
                while (result.next())
                {
                    existe = result.getInt("registrarfactura");
                }
            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
        return existe;
    }

    //Insertar Detalle
    public void insertdetalle(List<Factura> selectedFactura, Factura factura) {
        if (conexion.isEstado())
        {
            try
            {
                for (int i = 0; i < selectedFactura.size(); i++)
                {
                    String cadena = "SELECT public.insertdetallefac('"
                            + factura.getNfactura() + "',"
                            + selectedFactura.get(i).getImporteD() + ", '"
                            + selectedFactura.get(i).getDetalle() + "','"
                            + selectedFactura.get(i).getCuenta() + "')";
                    conexion.Ejecutar2(cadena);
                    System.out.println(cadena);
                }
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
    }

    //asiento contable
    public void insertasiento(List<Factura> selectedFactura, Factura factura) {
        if (conexion.isEstado())
        {
            try
            {
                int iddiario = 0;
                String cadena = "select iddiario from diariocontable where descripcion = 'Modulo cuentas por pagar'";
                result = conexion.ejecutarSql(cadena);
                while (result.next())
                {
                    iddiario = result.getInt("iddiario");
                }
                //JSON asiento contable
                String sentencia1, sentencia;
                sentencia = "{\"idDiario\": \"" + iddiario + "\",\"total\": " + factura.getImporte()
                        + ",\"documento\": \"FAC-" + factura.getNfactura() + "\",\"detalle\": \""
                        + factura.getDescripcion() + "\",\"fechaCreacion\": \""
                        + factura.getFecha().format(DateTimeFormatter.ofPattern("d/MM/uuuu")) + "\",\"fechaCierre\":\""
                        + factura.getVencimiento().format(DateTimeFormatter.ofPattern("d/MM/uuuu")) + "\"}";
                System.out.println(sentencia);
                //JSON un solo movimiento

                if (selectedFactura.size() == 1)
                {
                    sentencia1 = "[{\"idSubcuenta\":\""
                            + Listaids(selectedFactura.get(0).getCuenta()) + "\",\"debe\":\""
                            + selectedFactura.get(0).getImporteD() + "\",\"haber\":\"0\",\"tipoMovimiento\":\""
                            + selectedFactura.get(0).getDetalle() + "\"},"
                            + "{\"idSubcuenta\":\"28\",\"debe\":\"0\",\"haber\":\""
                            + factura.getImporte() + "\",\"tipoMovimiento\":\"Factura de compra\"}]";
                    System.out.println(sentencia1);
                } else
                {
                    sentencia1 = "[";
                    for (int i = 0; i < selectedFactura.size(); i++)
                    {
                        sentencia1 += "{\"idSubcuenta\":\"" + Listaids(selectedFactura.get(i).getCuenta()) + "\",\"debe\":\""
                                + selectedFactura.get(i).getImporteD() + "\",\"haber\":\"0\",\"tipoMovimiento\":\""
                                + selectedFactura.get(i).getDetalle() + "\"},";
                        System.out.println(sentencia1);
                    }
                    sentencia1 += "{\"idSubcuenta\":\"28\",\"debe\":\"0\",\"haber\":\""
                            + factura.getImporte() + "\",\"tipoMovimiento\":\"Factura de compra\"}]";
                }
                intJson(sentencia, sentencia1);
                updateAsiento(factura.getNfactura());
            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
    }

    public void revasiento(List<Factura> selectedFactura, Factura factura) {
        if (conexion.isEstado())
        {
            try
            {
                int iddiario = 0;
                String cadena = "select iddiario from diariocontable where descripcion = 'Modulo cuentas por pagar'";
                result = conexion.ejecutarSql(cadena);
                while (result.next())
                {
                    iddiario = result.getInt("iddiario");
                }

                String sentencia1, sentencia;
                sentencia = "{\"idDiario\": \"" + iddiario + "\",\"total\": " + factura.getImporte()
                        + ",\"documento\": \"FAC-" + factura.getNfactura() + " R\",\"detalle\": \""
                        + factura.getDescripcion() + "\",\"fechaCreacion\": \""
                        + factura.getFecha().format(DateTimeFormatter.ofPattern("d/MM/uuuu")) + "\",\"fechaCierre\":\""
                        + factura.getVencimiento().format(DateTimeFormatter.ofPattern("d/MM/uuuu")) + "\"}";
                System.out.println(sentencia);
                if (selectedFactura.size() == 1)
                {
                    sentencia1 = "[{\"idSubcuenta\":\""
                            + Listaids(selectedFactura.get(0).getCuenta()) + "\",\"debe\":\"0\",\"haber\":\""
                            + selectedFactura.get(0).getImporteD() + "\",\"tipoMovimiento\":\""
                            + selectedFactura.get(0).getDetalle() + "\"},"
                            + "{\"idSubcuenta\":\"28\",\"debe\":\""
                            + factura.getImporte() + "\",\"haber\":\"0\",\"tipoMovimiento\":\"Factura de compra\"}]";
                    System.out.println(sentencia1);
                } //JSON mas de un movimiento
                else
                {
                    sentencia1 = "[";
                    for (int i = 0; i < selectedFactura.size(); i++)
                    {
                        sentencia1 += "{\"idSubcuenta\":\"" + Listaids(selectedFactura.get(i).getCuenta()) + "\",\"debe\":\"0\",\"haber\":\""
                                + selectedFactura.get(i).getImporteD() + "\",\"tipoMovimiento\":\""
                                + selectedFactura.get(i).getDetalle() + "\"},";
                        System.out.println(sentencia1);
                    }
                    sentencia1 += "{\"idSubcuenta\":\"28\",\"debe\":\""
                            + factura.getImporte() + "\",\"haber\":\"0\",\"tipoMovimiento\":\"Factura de compra\"}]";
                }
                intJson(sentencia, sentencia1);
                dhabilitar(factura.getNfactura(), 0);
            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
    }

    public void intJson(String a, String b) {
        if (conexion.isEstado())
        {
            try
            {
                String cadena = "SELECT public.generateasientocotableexternal('"
                        + a + "','" + b + "')";
                System.out.println(cadena);
                conexion.Ejecutar2(cadena);
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
    }

    public void updateAsiento(String n) {
        if (conexion.isEstado())
        {
            try
            {
                String cadena = "update factura set idasiento = "
                        + "(select idasiento from asiento where documento = 'FAC-" + n + "') "
                        + "where nfactura = '" + n + "'";
                System.out.println(cadena);
                conexion.Ejecutar2(cadena);
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
    }

    public int Listaids(String g) {
        int n = 0;
        if (conexion.isEstado())
        {
            try
            {
                String cadena = "select idsubcuenta from subcuenta where nombre = '" + g + "'";
                result = conexion.ejecutarSql(cadena);
                while (result.next())
                {
                    n = result.getInt("idsubcuenta");
                }
            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
        return n;
    }

    //Actualizar factura
    public void Actualizar(Factura factura) {
        if (conexion.isEstado())
        {
            try
            {
                String cadena = "select actualizarfactura('" + factura.getNfactura()
                        + "','" + factura.getDescripcion() + "'," + factura.getImporte()
                        + "," + 0
                        + ",'" + factura.getFecha()
                        + "','" + factura.getVencimiento()
                        + "','" + factura.getRuc() + "')";
                System.out.println(cadena);
                conexion.Ejecutar2(cadena);
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
    }

    public void Actdetalle(List<Factura> selectedFactura) {
        if (conexion.isEstado())
        {
            try
            {
                for (int i = 0; i < selectedFactura.size(); i++)
                {
                    String cadena = "SELECT public.actualizardetallefac('"
                            + selectedFactura.get(i).getId_detalle() + "',"
                            + selectedFactura.get(i).getImporteD() + ",'"
                            + selectedFactura.get(i).getDetalle() + "')";
                    System.out.println(cadena);
                    conexion.Ejecutar2(cadena);
                }
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
    }

    public void dhabilitar(String d, int n) {
        if (conexion.isEstado())
        {
            try
            {
                String cadena = "select habilitarfactura(" + n + ",'" + d + "')";
                System.out.println(cadena);
                conexion.Ejecutar2(cadena);
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
    }

    //Consultas
    public float buscarPagado(String nfactura) {
        float pagado = 0;
        if (conexion.isEstado())
        {
            try
            {
                String sentencia = "select pagado from factura where nfactura= '"
                        + nfactura + "'";
                result = conexion.ejecutarSql(sentencia);
                while (result.next())
                {
                    pagado = result.getFloat("pagado");
                }
            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
        return pagado;
    }

    public String Buscar(String nfactura) {
        String r = "";
        if (conexion.isEstado())
        {
            try
            {
                String sentencia = "SELECT ruc from proveedor where idproveedor ="
                        + "(SELECT  idproveedor from factura where nfactura = '"
                        + nfactura + "')";
                result = conexion.ejecutarSql(sentencia);
                while (result.next())
                {
                    r = result.getString("ruc");
                }
            } catch (SQLException ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
        return r;
    }

//Paola: Funcion para autorizar Pago
    public void AutorizarPago(String d) {
        if (conexion.isEstado())
        {
            try
            {
                String cadena = "update factura set estado = 1 "
                        + " where nfactura = '" + d + "'";
                conexion.Ejecutar2(cadena);
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally
            {
                conexion.desconectar();
            }
        }
    }

    // metodos aux para comunicación con la db
    public static Factura getOneFactura(int id) {
        Factura fac = new Factura();

        Conexion conn = new Conexion();
        String query = "select idfactura, nfactura, descripcion, importe, pagado, fecha, vencimiento, estado, idproveedor, idasiento\n"
                + "from factura\n"
                + "where \"idfactura\"="+id+";";
        try
        {
//            conn.conectar();
//
//            //Statement stmt = conn.conex.createStatement();
//            PreparedStatement stmt = conn.conex.prepareStatement(query);
//            stmt.setInt(1, id);
//
//            ResultSet rs = stmt.executeQuery();
            ResultSet rs = conn.ejecutarSql(query);
            while (rs.next())
            {
                fac.setId(rs.getInt("idfactura"));
                fac.setNfactura(rs.getString("nfactura"));
                fac.setDescripcion(rs.getString("descripcion"));
                fac.setImporte(rs.getFloat("importe"));
                fac.setPagado(rs.getFloat("pagado"));
                fac.setFecha(rs.getObject("fecha", LocalDate.class));
                fac.setVencimiento(rs.getObject("vencimiento", LocalDate.class));
                fac.setEstado(rs.getInt("estado"));
                fac.setIdproveedor(rs.getInt("idproveedor"));
                fac.setIdasiento(rs.getInt("idasiento"));
            }

        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            return null;
        } finally
        {
            conn.desconectar();
        }

        return fac;
    }

    // Trae los datos de la base de datos incluido los de los proveedores
    public static List<Factura> get_fac_pro(LocalDate desde, LocalDate hasta, int opcion) {
        List<Factura> lista = new ArrayList<>();

        Conexion conn = new Conexion();
        String query = "select * from select_fac_pro('"+desde+"','"+hasta+"',"+opcion+");";

        try
        {
            
//            conn.conectar();
//
//            //Statement stmt = conn.conex.createStatement();
//            PreparedStatement stmt = conn.conex.prepareStatement(query);
//            // Establecemos los argumentos.
//            stmt.setDate(1, java.sql.Date.valueOf(desde));
//            stmt.setDate(2, java.sql.Date.valueOf(hasta));
//            stmt.setInt(3, opcion);
//            //stmt.setObject(1, new java.sql.Date());

            ResultSet rs = conn.ejecutarSql(query);
            while (rs.next())
            {
                Factura fac = new Factura();
                fac.setProveedor(new Proveedor());

                fac.setFecha(rs.getObject("fecha", LocalDate.class));
                fac.setId(rs.getInt("id_factura"));
                fac.setIdasiento(rs.getInt("id_asiento"));
                fac.setNfactura(rs.getString("nfactura"));
                fac.setDescripcion(rs.getString("descripcion"));
                fac.setImporte(rs.getFloat("importe"));
                fac.setPagado(rs.getFloat("pagado"));
                fac.setVencimiento(rs.getObject("vencimiento", LocalDate.class));
                fac.setEstado(rs.getInt("estado"));
                fac.setIdproveedor(rs.getInt("id_proveedor"));

                fac.setPor_pagar(fac.getImporte() - fac.getPagado()); // Calculado

                //fac.proveedor.idProveedor = rs.getInt("id_proveedor");
                //fac.proveedor.nombre = rs.getString("nombre");
                fac.getProveedor().setIdProveedor(rs.getInt("id_proveedor"));
                fac.getProveedor().setNombre(rs.getString("nombre"));

                fac.setEstado_string(rs.getString("estado_string"));

                lista.add(fac);
            }

        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            return null;
        } finally
        {
            conn.desconectar();
        }

        return lista;
    }
}
