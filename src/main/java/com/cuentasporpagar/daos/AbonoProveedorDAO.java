/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuentasporpagar.daos;

import com.global.config.Conexion;
import com.cuentasporpagar.models.AbonoProveedor;
import com.cuentasporpagar.models.DetalleAbono;
import com.cuentasporpagar.models.Factura;
import com.cuentasporpagar.models.Proveedor;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author PAOLA
 */
public class AbonoProveedorDAO {

    Conexion conex;
    private AbonoProveedor abono;
    private DetalleAbono detalleAbono;
    private ResultSet result;
    private Factura factura;
    private List<AbonoProveedor> listaAbono;
    private List<Factura> listafactura;
    private List<Proveedor> listaProveedor;
    private boolean bandera;

    public AbonoProveedorDAO() {
        listafactura = new ArrayList<>();
        listaProveedor = new ArrayList<>();
    }

    public AbonoProveedorDAO(AbonoProveedor abono) {
        this.abono = abono;
    }

    public AbonoProveedorDAO(Factura factura) {
        this.factura = factura;
    }

    public AbonoProveedorDAO(DetalleAbono detalleAbono) {
        this.detalleAbono = detalleAbono;
    }
//Llenar Datos de los abonos realizados
    public List<AbonoProveedor> llenarDatos(String sentencia) {
        conex = new Conexion();
        listaAbono = new ArrayList<>();
        if (conex.isEstado()) {
            try {
                result = conex.ejecutarConsulta(sentencia);
                while (result.next()) {
                    listaAbono.add(new AbonoProveedor(result.getString("referencia"),
                            result.getInt("idproveedor"), result.getObject("fecha", LocalDate.class),
                            result.getFloat("pago"), result.getString("periodo"),
                            result.getString("descripcion"), result.getString("nombre")));
                }
                result.close();
                return listaAbono;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.cerrarConexion();
            }
        }
        return listaAbono;
    }
//LLena una lista de datos de facturas de dicho proveedor seleccionado
    public List<Factura> llenarFacturas(String sentencia) {
        conex = new Conexion();
        listafactura = new ArrayList<>();
        if (conex.isEstado()) {
            try {
                result = conex.ejecutarConsulta(sentencia);
                while (result.next()) {
                    listafactura.add(new Factura(result.getString("nfactura"),
                            result.getFloat("importe"), result.getFloat("pagado"),
                            result.getObject("fecha", LocalDate.class), result.getObject("vencimiento", LocalDate.class),
                            result.getFloat("pendiente")));
                }
                result.close();
                return listafactura;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.cerrarConexion();
            }
        }
        return listafactura;
    }
//Llena una lista de Todos los proveedores registrado
    public List<Proveedor> llenarProveedor() {
        if (conex.isEstado()) {
            try {
                String sentencia = "SELECT proveedor.codigo,proveedor.ruc,proveedor.nombre FROM proveedor";
                result = conex.ejecutarConsulta(sentencia);
                while (result.next()) {
                    listaProveedor.add(new Proveedor(
                            result.getString("codigo"),
                            result.getString("ruc"),
                            result.getString("nombre")
                    ));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.cerrarConexion();
            }
        }
        return listaProveedor;
    }
//Inserta abonoproveedor
    public void Insertar(AbonoProveedor abonoProveedor,int estado) {
        if (conex.isEstado()) {
            try {
                String sentencia = String.format("select insert_abono('%1$s','%2$s',"
                        + "'%3$s','%4$s','%5$s','%6$s','%7$s') as registro",
                        abonoProveedor.getDetalletipoPago(), abonoProveedor.getDetalletipoBanco(),
                        abonoProveedor.getRuc(), abonoProveedor.getReferencia(),
                        abonoProveedor.getFecha(), abonoProveedor.getPeriodo(),estado);
                result = conex.ejecutarConsulta(sentencia);
                while (result.next()) {
                    abonoProveedor.setIdAbonoProveedor(result.getInt("registro"));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.cerrarConexion();
            }
        }
    }
//Inserta los datos del detalle del pago
    public boolean InsertarDetalle(List<Factura> selectedFactura, AbonoProveedor abono) {
        if (conex.isEstado()) {
            try {
                for (int i = 0; i < selectedFactura.size(); i++) {
                    String sentencia = String.format("select insert_detalleabono(%1$d,'%2$s','%3$s')",
                            abono.getIdAbonoProveedor(), selectedFactura.get(i).getPagado(),
                            selectedFactura.get(i).getNfactura());
                    result = conex.ejecutarConsulta(sentencia);
                }
                bandera = result.next();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.cerrarConexion();
            }
        }
        return bandera;
    }

    public int getCountPago() {
        String sql = String.format("select count(idabonoproveedor) from public.abonoproveedor");
        int numero = 0;
        try {
            conex.conectar();
            result = conex.ejecutarSql(sql);
            //Llena la lista de los datos
            while (result.next()) {
                numero = result.getInt("count");
            }
        } catch (Exception e) {
            numero = -1;
            return numero;
        } finally {
            conex.desconectar();
        }
        return numero;
    }

    private String generateNumeroPago() {
        int num = getCountPago();
        if (num > 8) {
            return "PAGO-0" + (num + 1);
        } else {
            return "PAGO-00" + (num + 1);
        }
    }

    //asiento contable
    public void insertasiento(int idSubcuenta, AbonoProveedor abono, int accion) {
        if (conex.isEstado()) {
            try {
                int iddiario = 0;
                String cadena = "select iddiario from diariocontable where descripcion = 'Modulo cuentas por pagar'";
                result = conex.ejecutarConsulta(cadena);
                while (result.next()) {
                    iddiario = result.getInt("iddiario");
                }
                String sentencia1, sentencia;

                if (accion == 1) {
                    sentencia = "{\"idDiario\": \"" + iddiario + "\",\"total\": " + abono.getImporte()
                            + ",\"documento\": \"" + generateNumeroPago() + "\",\"detalle\": \"Pago AP:"
                            + abono.getDetalletipoPago() + "\",\"fechaCreacion\": \""
                            + abono.getFecha().format(DateTimeFormatter.ofPattern("d/MM/uuuu")) + "\",\"fechaCierre\":\""
                            + abono.getFecha().plusDays(30).format(DateTimeFormatter.ofPattern("d/MM/uuuu")) + "\"}";
   
                    sentencia1 = "[{\"idSubcuenta\":\"28\",\"debe\":\""
                            + abono.getImporte() + "\",\"haber\":\"0\",\"tipoMovimiento\":\"Pago\"},"
                            + "{\"idSubcuenta\":\"" + idSubcuenta + "\",\"debe\":\"0\",\"haber\":\""
                            + abono.getImporte() + "\",\"tipoMovimiento\":\"Pago\"}]";
                    System.out.println(sentencia1);
                } else {
                    sentencia = "{\"idDiario\": \"" + iddiario + "\",\"total\": " + abono.getImporte()
                            + ",\"documento\": \"" + generateNumeroPago() + " R\",\"detalle\": \"Pago AP:"
                            + abono.getDetalletipoPago() + " R\",\"fechaCreacion\": \""
                            + abono.getFecha().format(DateTimeFormatter.ofPattern("d/MM/uuuu")) + "\",\"fechaCierre\":\""
                            + abono.getFecha().plusDays(30).format(DateTimeFormatter.ofPattern("d/MM/uuuu")) + "\"}";
   
                   sentencia1 = "[{\"idSubcuenta\":\"28\",\"debe\":\"0\",\"haber\":\""
                            + abono.getImporte() + "\",\"tipoMovimiento\":\"Pago\"},"
                            + "{\"idSubcuenta\":\"" + idSubcuenta + "\",\"debe\":\""
                            + abono.getImporte() + "\",\"haber\":\"0\",\"tipoMovimiento\":\"Pago\"}]";
                    System.out.println(sentencia1);
                }
                intJson(sentencia, sentencia1);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.cerrarConexion();
            }
        }
    }

    public void update_abono(int estado) {
        if (conex.isEstado()) {
            try {
                String sentencia = "update abonoproveedor as ap	"
                        + "SET  idasiento= (Select max(idasiento) from asiento),estado="+estado
                        + "WHERE ap.idabonoproveedor=(Select max(idabonoproveedor) from abonoproveedor)";
                conex.Ejecutar2(sentencia);
                System.out.println(sentencia);
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.cerrarConexion();
            }
        }
    }
    public void update_abono(int estado,int idabono) {
        if (conex.isEstado()) {
            try {
                String sentencia = "update abonoproveedor as ap	"
                        + "SET  estado="+estado
                        + "WHERE ap.idabonoproveedor=("+idabono+"-1)";
                System.out.println(sentencia);
                conex.Ejecutar2(sentencia);
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.cerrarConexion();
            }
        }
    }
    public void update_factura(float total,String nfactura) {
        if (conex.isEstado()) {
            try {
                String sentencia = "update factura as f	"
                        + "SET  pagado= select f.pagado -"+total+" from "
                        + "((select f.pagado from factura f where f.nfactura='"+nfactura+"')) as f"
                        + "WHERE f.nfactura='"+nfactura+"''";
                System.out.println(sentencia);
                conex.Ejecutar2(sentencia);
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.cerrarConexion();
            }
        }
    }

    public void intJson(String a, String b) {
        if (conex.isEstado()) {
            try {
                String cadena = "SELECT public.generateasientocotableexternal('"
                        + a + "','" + b + "')";
                System.out.println(cadena);
                conex.ejecutarConsulta(cadena);
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.cerrarConexion();
            }
        }
    }

    public int Listaids(String g) {
        int n = 0;
        if (conex.isEstado()) {
            try {
                String cadena = "select idsubcuenta from subcuenta where nombre = '" + g + "'";
                result = conex.ejecutarConsulta(cadena);
                while (result.next()) {
                    n = result.getInt("idsubcuenta");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.cerrarConexion();
            }
        }
        return n;
    }

    public void search_date_payment(float importe, AbonoProveedor abonoProveedor) {
        if (conex.isEstado()) {
            try {
                System.out.println(importe);
                String sentencia = "select search_date_payment(" + importe + ") as idabono;";
                result = conex.ejecutarConsulta(sentencia);
                while (result.next()) {
                    abonoProveedor.setIdAbonoProveedor(result.getInt("idabono"));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.cerrarConexion();
            }
        }
    }

    public void select_date_payment(int idabono) {
        if (conex.isEstado()) {
            try {
                String sentencia = String.format("select * from select_date_payment(%1$d);", idabono);
                result = conex.ejecutarConsulta(sentencia);
                listaAbono.clear();
                while (result.next()) {
                    listaAbono.add(new AbonoProveedor(result.getInt("idabonopro"),
                            result.getString("referencia"), result.getObject("fecha", LocalDate.class),
                            result.getString("periodo"), result.getString("tipopago"),
                            result.getString("nombre"), result.getString("tipobanco"),
                            result.getString("ruc")));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.cerrarConexion();
            }
        }
    }

    public List<Factura> select_date_invoice(int idabono) {
        if (conex.isEstado()) {
            try {
                String sentencia = String.format("select * from select_date_invoice(%1$d);", idabono);
                result = conex.ejecutarConsulta(sentencia);
                listafactura.clear();
                while (result.next()) {
                    listafactura.add(new Factura(result.getString("nfactura"), result.getFloat("importe"),
                            result.getFloat("pago"), result.getObject("fecha", LocalDate.class),
                            result.getObject("vencimiento", LocalDate.class), result.getFloat("pendiente")));
                }
                result.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.cerrarConexion();
            }
        }
        return listafactura;
    }

    public Conexion getConex() {
        return conex;
    }

    public void setConex(Conexion conex) {
        this.conex = conex;
    }

    public AbonoProveedor getAbono() {
        return abono;
    }

    public void setAbono(AbonoProveedor abono) {
        this.abono = abono;
    }

    public ResultSet getResult() {
        return result;
    }

    public DetalleAbono getDetalleAbono() {
        return detalleAbono;
    }

    public void setDetalleAbono(DetalleAbono detalleAbono) {
        this.detalleAbono = detalleAbono;
    }

    public void setResult(ResultSet result) {
        this.result = result;
    }

    public List<AbonoProveedor> getListaAbono() {
        return listaAbono;
    }

    public void setListaAbono(List<AbonoProveedor> listaAbono) {
        this.listaAbono = listaAbono;
    }

    public List<Factura> getListafactura() {
        return listafactura;
    }

    public void setListafactura(List<Factura> listafactura) {
        this.listafactura = listafactura;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

}
