/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.DetalleVenta;
import com.ventas.models.Proforma;
import java.time.format.DateTimeFormatter;
import com.ventas.models.Venta;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;

public class VentaDAO {

    private Venta venta;
    private Conexion con;
    private ResultSet result;

    public VentaDAO() {
        this.con = new Conexion();
    }

    public VentaDAO(Venta venta) {
        this.venta = venta;
        this.con = new Conexion();
    }

    public int GuardarVenta(Venta ventaActual) {
        try {
            ResultSet rs = null;

            this.con.conectar();
            rs = this.con.ejecutarSql("select idventa, secuencia from public.venta order by idventa desc limit 1;");
            int idVenta = 1;
            int secuenciaActual = 1;

            //Asignar los valores de la siguiente venta y secuencia.
            while (rs.next()) {
                idVenta = rs.getInt(1) + 1;
                secuenciaActual = rs.getInt(2) + 1;
            }
            ventaActual.setSecuencia(secuenciaActual);
            ventaActual.setIdVenta(idVenta);
            System.out.println("Venta: " + ventaActual.getIdVenta());

            //Insertar nueva venta
            String query = "INSERT INTO public.venta("
                    + "idventa, idcliente, id_empleado, idformasdepago, idestadodocumento, id_sucursal, fechaventa, puntoemision, secuencia,"
                    + "autorizacion, fechaemision, fechaautorizacion, base12, base0, baseexcentoiva, iva12, ice, totalfactura) "
                    + "VALUES(" + ventaActual.getIdVenta() + ", " + ventaActual.getIdCliente() + ", " + ventaActual.getIdEmpleado() + ", " + ventaActual.getIdFormaPago() + ", 1, 1, '" + ventaActual.getFechaVenta()
                    + "', 1, " + ventaActual.getSecuencia() + ", " + ventaActual.getAutorizacion() + ", '" + ventaActual.getFechaEmision() + "', '" + ventaActual.getFechaAutorizacion()
                    + "', " + ventaActual.getBase12() + ", " + ventaActual.getBase0() + ", 0, " + ventaActual.getIva() + ", " + ventaActual.getIce() + ", " + ventaActual.getTotalFactura() + ")";
            System.out.println(query);
            this.con.ejecutarSql(query);

            //Verificación de forma de pago a crédito
            if (ventaActual.getIdFormaPago() == 1) {
                query = "select ingresar_plan_de_pago(" + ventaActual.getIdVenta() + ", " + ventaActual.getDiasCredito() + ", '" + ventaActual.getFechaVenta() + "', "
                        + ventaActual.getTotalFactura() + ", 0.1)";
                System.out.println(query);
                this.con.ejecutarSql(query);
            } else {
                query = "select ingresar_plan_de_pago_inmediato(" + ventaActual.getIdVenta() + ", " + String.valueOf(0) + ", '" + ventaActual.getFechaVenta() + "', "
                        + ventaActual.getTotalFactura() + ")";
                this.con.ejecutarSql(query);
            }

            this.con.desconectar();

            System.out.println("Venta Guardada exitosamente");

            return ventaActual.getIdVenta();
        } catch (Exception e) {
            if (con.isEstado()) {
                con.desconectar();
            }
            System.out.println(e.getMessage().toString());
        } finally {
            this.con.desconectar();
        }
        return 0;
    }

    public int facturarProforma(Proforma proformaActual, int formaPago, int diasCredito) {
        try {
            ResultSet rs = null;

            this.con.conectar();
            rs = this.con.ejecutarSql("select idventa, secuencia from public.venta order by idventa desc limit 1;");
            int idVenta = 1;
            int secuenciaActual = 1;

            //Asignar los valores de la siguiente venta y secuencia.
            while (rs.next()) {
                idVenta = rs.getInt(1) + 1;
                secuenciaActual = rs.getInt(2) + 1;
            }
            //ventaActual.setSecuencia(secuenciaActual);
            //ventaActual.setIdVenta(idVenta);
            //System.out.println("Venta: " + ventaActual.getIdVenta());

            //Insertar nueva venta
            String query = "INSERT INTO public.venta("
                    + "idventa, idcliente, id_empleado, idformasdepago, idestadodocumento, id_sucursal, fechaventa, puntoemision, secuencia,"
                    + "autorizacion, fechaemision, fechaautorizacion, base12, base0, baseexcentoiva, iva12, ice, totalfactura) "
                    + "VALUES(" + idVenta + ", " + proformaActual.getId_cliente() + ", " + 1 + ", " + formaPago + ", 1, 1, '" + proformaActual.getFecha_autorizacion()
                    + "', 1, " + secuenciaActual + ", " + generarAutorizacion(proformaActual.getId_cliente(), idVenta) + ", '" + proformaActual.getFecha_autorizacion() + "', '" + proformaActual.getFecha_autorizacion()
                    + "', " + proformaActual.getBase12() + ", " + proformaActual.getBase0() + ", 0, " + proformaActual.getIva12() + ", " + proformaActual.getIce() + ", " + proformaActual.getTotalproforma() + ")";
            System.out.println(query);
            this.con.ejecutarSql(query);

            //Verificación de forma de pago a crédito
            if (formaPago == 1) {
                query = "select ingresar_plan_de_pago(" + idVenta + ", " + diasCredito + ", '" + proformaActual.getFecha_autorizacion() + "', "
                        + proformaActual.getTotalproforma() + ", 0.1)";
                System.out.println(query);
                this.con.ejecutarSql(query);
            } else {
                query = "select ingresar_plan_de_pago_inmediato(" + idVenta + ", " + String.valueOf(0) + ", '" + proformaActual.getFecha_autorizacion() + "', "
                        + proformaActual.getTotalproforma() + ")";
                this.con.ejecutarSql(query);
            }

            this.con.desconectar();

            System.out.println("Venta Guardada exitosamente");

            return idVenta;
        } catch (Exception e) {
            if (con.isEstado()) {
                con.desconectar();
            }
            System.out.println(e.getMessage().toString());
        } finally {
            this.con.desconectar();
        }
        return 0;
    }

    private String generarAutorizacion(int idCliente, int idVenta)
    {
        try {
            String autorizacion = "";
            DateFormat df = new SimpleDateFormat("ddMMyyyy"); //Fecha
            autorizacion += df.format(new Date()) + "01"; //Tipo comprobante (factura)
            if (new ClienteVentaDao().BuscarClientePorId(idCliente).getIdentificacion().length() < 13) { //Ruc o identificaicon
                autorizacion += new ClienteVentaDao().BuscarClientePorId(idCliente).getIdentificacion() + "001";
            } else {
                autorizacion += new ClienteVentaDao().BuscarClientePorId(idCliente).getIdentificacion();
            }
            autorizacion += "1001001"; //ambiente y serie
            String secuencia = String.valueOf(idVenta); //Obtiene la secuencia de factura
            for (int i = secuencia.length(); i < 10; i++) {
                autorizacion += "0";
            }
            autorizacion += secuencia; //Secuencia de factura
            String numerico = String.valueOf((int) ((Math.random() * (999999999 - 111111111)) + 111111111)); //Genera un numerico
            autorizacion += numerico;
            autorizacion += "1"; //Tipo de emision (normal)
            autorizacion += modulo11(numerico); //Digito verificador

            return autorizacion;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String modulo11(String numerico) {
        int base = 0;
        for (int i = numerico.length(); i < 9; i++) {
            base += Integer.valueOf(numerico.substring(i, i));
        }
        base = 11 - (base % 11);
        return String.valueOf(base);
    }

    //asiento contable
    public void insertasiento(Venta venta) {
        if (con.isEstado()) {
            try {
                int iddiario = 0;
                String cadena = "select iddiario from diariocontable where descripcion = 'Modulo cuentas por cobrar'";
                result = con.ejecutarSql(cadena);
                while (result.next()) {
                    iddiario = result.getInt("iddiario");
                }
                //JSON asiento contable
                String sentencia1, sentencia;
                sentencia = "{\"idDiario\": \"" + iddiario + "\",\"total\": " + venta.getTotalFactura()
                        + ",\"documento\": \"CPC-VNT-" + venta.getIdVenta() + "\",\"detalle\": "
                        + "\"Cuentas por cobrar cliente\",\"fechaCreacion\": \""
                        + LocalDate.now().format(DateTimeFormatter.ofPattern("d/MM/uuuu")) + "\",\"fechaCierre\":\""
                        + LocalDate.now().plusDays(venta.getDiasCredito()).format(DateTimeFormatter.ofPattern("d/MM/uuuu")) + "\"}";
                System.out.println(sentencia);
                //JSON un solo movimiento

                int formaPago;
                String tipomovimiento;
                switch (venta.getIdFormaPago()) {
                    case 1:
                        formaPago = 77;
                        tipomovimiento = "Ventas con tarifa 12%";
                        break;
                    case 2:
                        formaPago = 1;
                        tipomovimiento = "Caja";
                        break;
                    default:
                        formaPago = 3;
                        tipomovimiento = "Banco";
                        break;
                }

                sentencia1 = "[{\"idSubcuenta\":\"156\",\"debe\":\"" + venta.getTotalFactura()
                        + "\",\"haber\":\"0\",\"tipoMovimiento\":\"Factura de venta\"},"
                        + "{\"idSubcuenta\":\"" + formaPago + "\",\"debe\":\"0\",\"haber\":\"" + venta.getTotalFactura()
                        + "\",\"tipoMovimiento\":\"" + tipomovimiento + "\"}]";
                System.out.println(sentencia1);

                intJson(sentencia, sentencia1);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                con.desconectar();
            }
        }
    }

    public void intJson(String a, String b) {
        if (con.isEstado()) {
            try {
                String cadena = "SELECT public.generateasientocotableexternal('"
                        + a + "','" + b + "')";
                System.out.println(cadena);
                result = con.ejecutarSql(cadena);
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " error en conectarse");
            } finally {
                con.desconectar();
            }
        }
    }

    //Obtener Todas las ventas
    public List<Venta> TodasVentas() throws SQLException {
        List<Venta> ventas = new ArrayList<>();

        this.con.conectar();
        ResultSet rs = this.con.ejecutarSql("select * from public.venta order by fechaventa desc, idventa desc;");

        Venta venta = new Venta();

        while (rs.next()) {
            venta = new Venta();
            venta.setIdVenta(rs.getInt(1));
            venta.setIdCliente(rs.getInt(2));
            venta.setIdEmpleado(rs.getInt(3));
            venta.setIdFormaPago(rs.getInt(4));
            venta.setIdDocumento(rs.getInt(5));
            venta.setSucursal(rs.getInt(6));
            venta.setFechaVenta(rs.getDate(7).toString());
            venta.setPuntoEmision(rs.getInt(8));
            venta.setSecuencia(rs.getInt(9));
            venta.setAutorizacion(rs.getString(10));
            venta.setFechaEmision(rs.getDate(11).toString());
            venta.setFechaAutorizacion(rs.getDate(12).toString());
            venta.setBase12(rs.getDouble(13));
            venta.setBase0(rs.getDouble(14));
            venta.setIva(rs.getDouble(16));
            venta.setIce(rs.getDouble(17));
            venta.setTotalFactura(rs.getFloat(18));

            ClienteVentaDao tempDao = new ClienteVentaDao();
            venta.setCliente(tempDao.BuscarClientePorId(venta.getIdCliente()));

            String fact = "";
            int lon = String.valueOf(venta.getSucursal()).length();
            while (lon < 3) {
                fact += "0";
                lon += 1;
            }
            fact += String.valueOf(venta.getSucursal()) + "-";
            lon = String.valueOf(venta.getPuntoEmision()).length();
            while (lon < 3) {
                fact += "0";
                lon += 1;
            }
            fact += String.valueOf(venta.getPuntoEmision()) + "-";
            lon = String.valueOf(venta.getSecuencia()).length();
            while (lon < 10) {
                fact += "0";
                lon += 1;
            }
            fact += String.valueOf(venta.getSecuencia());
            venta.setFactura(fact);

            ventas.add(venta);

        }

        this.con.desconectar();

        return ventas;
    }

    public int getSiguienteIdVenta() {
        try {
            int id = 0;
            this.result = con.ejecutarSql("select MAX(idventa) + 1 as id from public.venta;");
            while (result.next()) {
                id = result.getInt("id");
            }
            return id;
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        } finally {
            this.con.desconectar();
        }
        return 0;
    }

}
//
//switch (venta.getIdFormaPago()) {
//                    case 2:
//                        sentencia1 = "[{\"idSubcuenta\":\"156\",\"debe\":\""
//                            + venta.getTotalFactura() + "\",\"haber\":\"0\",\"tipoMovimiento\":\"Factura de venta\"},"
//                            + "{\"idSubcuenta\":\"1\",\"debe\":\"0\",\"haber\":\""+ venta.getTotalFactura() 
//                            +"\",\"tipoMovimiento\":\"Caja\"}]";
//                        break;
//                    case 3:
//                        sentencia1 = "[{\"idSubcuenta\":\"156\",\"debe\":\""
//                            + venta.getTotalFactura() + "\",\"haber\":\"0\",\"tipoMovimiento\":\"Factura de venta\"},"
//                            + "{\"idSubcuenta\":\"3\",\"debe\":\"0\",\"haber\":\""+ venta.getTotalFactura() 
//                            +"\",\"tipoMovimiento\":\"Banco\"}]";
//                        break;
//                }
//                    
//                    sentencia1 = "[{\"idSubcuenta\":\"156\",\"debe\":\""
//                            + venta.getTotalFactura() + "\",\"haber\":\"0\",\"tipoMovimiento\":\"Factura de venta\"},"
//                            + "{\"idSubcuenta\":\"77\",\"debe\":\"0\",\"haber\":\""+ venta.getTotalFactura() 
//                            +"\",\"tipoMovimiento\":\"Ventas con tarifa 12%\"}]";
//                    System.out.println(sentencia1);
