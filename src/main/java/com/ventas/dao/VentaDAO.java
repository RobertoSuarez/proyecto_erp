/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ventas.dao;

import com.global.config.Conexion;
import com.ventas.models.DetalleVenta;
import com.ventas.models.Venta;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    private Venta venta;
    private Conexion con;

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

            this.con.abrirConexion();
            rs = this.con.consultar("select * from public.venta order by idventa desc limit 1");

            int idVenta = 1;
            int secuenciaActual = 1;
            while (rs.next()) {
                idVenta = rs.getInt(1) + 1;
                secuenciaActual = rs.getInt(2) + 1;
            }
            ventaActual.setSecuencia(secuenciaActual);
            ventaActual.setIdVenta(idVenta);

            System.out.println(ventaActual.getIdVenta());

            String query = "INSERT INTO public.venta("
                    + "idventa, idcliente, id_empleado, idformasdepago, idestadodocumento, id_sucursal, fechaventa, puntoemision, secuencia,"
                    + "autorizacion, fechaemision, fechaautorizacion, base12, base0, baseexcentoiva, iva12, ice, totalfactura) "
                    + "VALUES(" + ventaActual.getIdVenta() + ", " + ventaActual.getIdCliente() + ", " + ventaActual.getIdEmpleado() + ", " + ventaActual.getIdFormaPago() + ", 1, 1, '" + ventaActual.getFechaVenta()
                    + "', 1, " + ventaActual.getSecuencia() + ", " + ventaActual.getAutorizacion() + ", '" + ventaActual.getFechaEmision() + "', '" + ventaActual.getFechaAutorizacion()
                    + "', " + ventaActual.getBase12() + ", " + ventaActual.getBase0() + ", 0, " + ventaActual.getIva() + ", " + ventaActual.getIce() + ", " + ventaActual.getTotalFactura() + ")";
            System.out.println(query);

            this.con.consultar(query);

            if (ventaActual.getIdFormaPago() == 1) {
                query = "select ingresar_plan_de_pago(" + ventaActual.getIdVenta() + ", " + ventaActual.getDiasCredito() + ", '" + ventaActual.getFechaVenta() + "', "
                        + ventaActual.getTotalFactura() + ", 0.1)";
                System.out.println(query);
                this.con.consultar(query);
            }

            this.con.cerrarConexion();

            System.out.println("Venta Guardada exitosamente");

            return ventaActual.getIdVenta();
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        } finally {
            this.con.cerrarConexion();
        }
        return 0;
    }

    //Obtener Todas las ventas
    public List<Venta> TodasVentas() throws SQLException {
        List<Venta> ventas = new ArrayList<>();

        this.con.abrirConexion();
        ResultSet rs = this.con.consultar("select * from public.venta order by fechaventa desc;");

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

        this.con.cerrarConexion();

        return ventas;
    }

}
