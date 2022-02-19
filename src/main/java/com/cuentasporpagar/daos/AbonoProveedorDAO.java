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
 * @see AbonoProveedor
 * @see DetalleAbono
 * @see Factura
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

    /**
     * Llenar Datos de los abonos realizados
     *
     * @param sentencia Sentencia para ejecutar que contiene los
     * campos(referencia, idproveedor,fecha,pago,periodo,descripcion,nombre)
     * @return Retorna una lista de pagos registrados en BD
     */
    public List<AbonoProveedor> llenarDatos(String sentencia) {
        conex = new Conexion();
        listaAbono = new ArrayList<>();
        if (conex.isEstado()) {
            try {
                result = conex.ejecutarSql(sentencia);
                while (result.next()) {
                    listaAbono.add(new AbonoProveedor(result.getString("referencia"),
                            result.getInt("idproveedor"), result.getObject("fecha", LocalDate.class),
                            result.getFloat("pago"), result.getString("periodo"),
                            result.getString("descripcion"), result.getString("nombre"), result.getString("ruc")));
                }
                result.close();
                return listaAbono;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.desconectar();
            }
        }
        return listaAbono;
    }

    /**
     * LLena una lista de datos de facturas de dicho proveedor seleccionado
     *
     * @param sentencia Sentencia para ejecutar que contiene los
     * campos(nfactura, importe,pagado,fecha,vencimiento,pendiente)
     * @return Retorna una lista de facturas registrados en BD
     */
    public List<Factura> llenarFacturas(String sentencia) {
        conex = new Conexion();
        listafactura = new ArrayList<>();
        if (conex.isEstado()) {
            try {
                result = conex.ejecutarSql(sentencia);
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
                conex.desconectar();
            }
        }
        return listafactura;
    }

    /**
     * Llena una lista de Todos los proveedores registrado
     *
     * @return Retorna una lista de proveedores registrados en BD
     */
    public List<Proveedor> llenarProveedor() {
        if (conex.isEstado()) {
            try {
                String sentencia = "select x.codigo,x.ruc,x.nombre from (select count(f.idproveedor),f.idproveedor,p.codigo,p.ruc,p.nombre\n"
                        + "from factura f inner join proveedor p on (p.idproveedor=f.idproveedor)\n"
                        + "inner join condiciones c on \n"
                        + "(c.idproveedor = p.idproveedor)\n"
                        + "where f.habilitar=1 and f.estado=1 and f.pagado<f.importe\n"
                        + "group by f.idproveedor,p.codigo,p.ruc,p.nombre) as x";
                System.out.println(sentencia);
                result = conex.ejecutarSql(sentencia);
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
                conex.desconectar();
            }
        }
        return listaProveedor;
    }

    /**
     * Inserta abonoproveedor
     *
     * @param abonoProveedor Datos de la cabecera de un pago
     * @param estado Estado a mostrar(0=hablitado,1=deshabilitado)
     */
    public void Insertar(AbonoProveedor abonoProveedor, int estado) {
        if (conex.isEstado()) {
            try {
                String sentencia = String.format("select insert_abono('%1$s','%2$s',"
                        + "'%3$s','%4$s','%5$s','%6$s','%7$s') as registro",
                        abonoProveedor.getDetalletipoPago(), abonoProveedor.getDetalletipoBanco(),
                        abonoProveedor.getRuc(), abonoProveedor.getReferencia(),
                        abonoProveedor.getFecha(), abonoProveedor.getPeriodo(), estado);
                System.out.println(sentencia);
                result = conex.ejecutarSql(sentencia);
                while (result.next()) {
                    abonoProveedor.setIdAbonoProveedor(result.getInt("registro"));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.desconectar();
            }
        }
    }

    /**
     * Inserta los datos del detalle del pago
     *
     * @param selectedFactura Detalle de las facturas a realizar pago 
     * @param abono Total de cada factura
     * @param opcion Opcion para saber si es deshabilitado y registrado
     * @return Retorna una bandera para verificar el ingreso de los detalle de
     * pago
     */
    public boolean InsertarDetalle(List<Factura> selectedFactura, AbonoProveedor abono, int opcion) {
        if (conex.isEstado()) {
            try {
                for (int i = 0; i < selectedFactura.size(); i++) {
                    String sentencia = String.format("select insert_detalleabono(%1$d,'%2$s','%3$s',%4$d)",
                            abono.getIdAbonoProveedor(), selectedFactura.get(i).getPagado(),
                            selectedFactura.get(i).getNfactura(), opcion);
                    System.out.println(sentencia);
                    result = conex.ejecutarSql(sentencia);
                }
                bandera = result.next();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.desconectar();
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
        } catch (SQLException e) {
            numero = -1;
            return numero;
        } finally {
            conex.desconectar();
        }
        return numero;
    }

    /**
     * Genera un número de pago para la referencia del asiento contable
     *
     * @return Retorna un String (codigo de referncia en asiento)
     */
    private String generateNumeroPago() {
        int num = getCountPago();
        if (num > 8) {
            return "PAGO-0" + (num + 1);
        } else {
            return "PAGO-00" + (num + 1);
        }
    }

    /**
     * Metodo para consultar el id Diario del modulo cuenta por pagar
     *
     * @return Retorna el id del dicho diario
     */
    public int idDiario() {
        int iddiario = 0;
        if (conex.isEstado()) {
            try {
                String cadena = "select iddiario from diariocontable where descripcion = 'Modulo cuentas por pagar'";
                result = conex.ejecutarSql(cadena);
                while (result.next()) {
                    iddiario = result.getInt("iddiario");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.desconectar();
            }
        }
        return iddiario;
    }

    /**
     * Metodo para llenar la sentencia tipo Json
     *
     * @param idSubcuenta Tipo de subcuenta
     * @param abono Total del abono del pago
     * @param accion Tipo de accion(registrar , deshabilitar)
     */
    public void insertasiento(int idSubcuenta, AbonoProveedor abono, int accion) {
        try {
            String sentencia1, sentencia;
            if (accion == 1) {
                sentencia = "{\"idDiario\": \"" + idDiario() + "\",\"total\": " + abono.getImporte()
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
                sentencia = "{\"idDiario\": \"" + idDiario() + "\",\"total\": " + abono.getImporte()
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
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " error en conectarse");
        }
    }

    /**
     * Actualizar el estado en las facturas
     *
     * @param estado EStado de un pago(1=registrado,0=deshabilitado)
     * @param idabono idabono para actualizar el estado
     */
    public void update_abono(int estado, int idabono) {
        conex.desconectar();
        if (conex.isEstado()) {
            try {
                String sentencia = String.format("select update_abono('%1$s','%2$s') as updateAbono",
                        estado, idabono);
                System.out.println(sentencia);
                conex.Ejecutar2(sentencia);
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.desconectar();
            }
        }
    }

    /**
     * Envia los datos tipo json a insertar
     *
     * @param a Sentencia del tipo json (asiento)
     * @param b Sentencia del tipo json (movimiento)
     */
    public void intJson(String a, String b) {
        if (conex.isEstado()) {
            try {
                String cadena = "SELECT public.generateasientocotableexternal('"
                        + a + "','" + b + "')";
                System.out.println(cadena);
                conex.ejecutarSql(cadena);
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.desconectar();
            }
        }
    }

    public int Listaids(String g) {
        int n = 0;
        if (conex.isEstado()) {
            try {
                String cadena = "select idsubcuenta from subcuenta where nombre = '" + g + "'";
                result = conex.ejecutarSql(cadena);
                while (result.next()) {
                    n = result.getInt("idsubcuenta");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.desconectar();
            }
        }
        return n;
    }

    /**
     * metodo para consultar el idabonoproveedor
     *
     * @param importe Valor total
     * @param ruc Ruc del proveerdor
     * @param abonoProveedor datos de los pagos
     */
    public void search_date_payment(float importe, String ruc, AbonoProveedor abonoProveedor) {
        if (conex.isEstado()) {
            try {
                System.out.println(importe + "-" + ruc);
                String sentencia = "select search_date_payment(" + importe + ",'" + ruc + "') as idabono;";
                System.out.println(sentencia);
                result = conex.ejecutarSql(sentencia);
                while (result.next()) {
                    abonoProveedor.setIdAbonoProveedor(result.getInt("idabono"));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                conex.desconectar();
            }
        }
    }

    /**
     * metodo para consultar el proveedor
     *
     * @param idabono Valor del id abono para relacionarlos con proveedor
     */
    public void select_date_payment(int idabono) {
        if (conex.isEstado()) {
            try {
                String sentencia = String.format("select * from select_date_payment(%1$d);", idabono);
                result = conex.ejecutarSql(sentencia);
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
                conex.desconectar();
            }
        }
    }

    /**
     * metodo para consultar la factura
     *
     * @param idabono dato del idabono
     * @return Retorna una lista de dicha factura esta relacionada con el abono
     */
    public List<Factura> select_date_invoice(int idabono) {
        if (conex.isEstado()) {
            try {
                String sentencia = String.format("select * from select_date_invoice(%1$d);", idabono);
                result = conex.ejecutarSql(sentencia);
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
                conex.desconectar();
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
